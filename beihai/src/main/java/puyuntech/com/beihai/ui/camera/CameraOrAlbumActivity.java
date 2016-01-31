package puyuntech.com.beihai.ui.camera;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import puyuntech.com.beihai.R;

public class CameraOrAlbumActivity extends Activity {

    private ArrayList<String> picList = new ArrayList<String>();

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs;

    int aspectX = 75;//裁剪长
    int aspectY = 54;//裁剪高
    int outputX = 850;//输出长
    int outputY = 540;//输出高

    private GridView mGirdView;
    private ListAdapter mAdapter;
    String imgPath;//照相机返回的照片路径
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if (filename.endsWith(".jpg"))
                        return true;
                    return false;
                }
            }));
            /**
             * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
             */
            mAdapter = new PhonePicAdapter(getApplicationContext(), mImgs,
                    mImgDir.getAbsolutePath(), picList);
            mGirdView.setAdapter(mAdapter);
        }

        ;
    };
    static int CAMEAR_BACK = 11;

    static int PIC_CROP_BACK = 33;
    Uri ChooseUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getXY();
        Button imageView1 = (Button) findViewById(R.id.plugin_image_pickmulti_btn_back);
        imageView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mGirdView = (GridView) findViewById(R.id.activity_camera_gridview);
        getImages();
        mGirdView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                if (arg2 == 0) {//打开照相机
                    imgPath = Environment.getExternalStorageDirectory() + "/flashtag/" + System.currentTimeMillis() + ".jpg";


                    File vFile = new File(imgPath);
                    if (!vFile.exists()) {
                        File vDirPath = vFile.getParentFile(); //new File(vFile.getParent());
                        vDirPath.mkdirs();
                    }
                    Uri uri = Uri.fromFile(vFile);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//
                    startActivityForResult(intent, CAMEAR_BACK);
                } else {      //打开裁减
                    openCropView(picList.get(arg2), false);
                }
            }
        });
    }

    /**
     * 获取裁剪比例和高宽
     */
    private void getXY() {
        if (getIntent() != null) {
            aspectX = getIntent().getIntExtra("aspectX", aspectX);
            aspectY = getIntent().getIntExtra("aspectX", aspectY);
            outputX = getIntent().getIntExtra("outputX", outputX);
            outputY = getIntent().getIntExtra("outputY", outputY);
        }
    }

//  


    public boolean copyFile(String oldPath, String newPath) {
        boolean isok = true;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时 
                InputStream inStream = new FileInputStream(oldPath); //读入原文件 
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小 
                    //System.out.println(bytesum); 
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            } else {
                isok = false;
            }
        } catch (Exception e) {
            // System.out.println("复制单个文件操作出错"); 
            // e.printStackTrace(); 
            isok = false;
        }
        return isok;

    }

    /**
     * 打开系统自带裁减方法
     *
     * @param url
     */
    private void openCropView(String url, Boolean isCamera) {
        //获取屏幕宽度
        WindowManager wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (isCamera) {//相机图片不用另外保存
            File vFile = new File(url);
            ChooseUri = Uri.fromFile(vFile);
        } else {
            String tempUrl = Environment.getExternalStorageDirectory() + "/flashtag/" + System.currentTimeMillis() + ".jpg";
            File vFile = new File(tempUrl);
            if (!vFile.exists()) {
                File vDirPath = vFile.getParentFile(); //new File(vFile.getParent());
                vDirPath.mkdirs();
            }
            if (copyFile(url, tempUrl)) {
                ChooseUri = Uri.fromFile(vFile);
            }
        }


        intent.setDataAndType(ChooseUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);// 裁剪框比例
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);// 输出图片大小
        intent.putExtra("outputY", outputY);
        Log.v("dd", Integer.toString(width));
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ChooseUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, PIC_CROP_BACK);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMEAR_BACK) {
                openCropView(imgPath, true);
            }
            if (requestCode == PIC_CROP_BACK) {
//                data.setData(ChooseUri);
                data.putExtra("ChooseUri", ChooseUri);
                setResult(RESULT_OK, data);
//                Intent intent =new Intent(CameraOrAlbumActivity.this, PersonalDataActivity.class);
//                intent.setData(ChooseUri);  
//                startActivity(intent);
                finish();
            }
        }
    }


    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条


        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = CameraOrAlbumActivity.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED + " DESC");

                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    picList.add(path);
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    String dirPath = parentFile.getAbsolutePath();

                    //利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg"))
                                return true;
                            return false;
                        }
                    }).length;
                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();
                //扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;
                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }
}
