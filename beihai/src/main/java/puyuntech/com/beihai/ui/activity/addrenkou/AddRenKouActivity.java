package puyuntech.com.beihai.ui.activity.addrenkou;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.nicodelee.utils.StringUtils;
import com.nicodelee.utils.T;
import com.tianruiworkroomocr.Native;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;
import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.BaseAct;
import puyuntech.com.beihai.model.RenKouModel;
import puyuntech.com.beihai.ui.camera.CameraOrAlbumActivity;

@ContentView(R.layout.activity_add_ren_kou)
public class AddRenKouActivity extends BaseAct {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;//头部
    private static final int CROP = 2;//裁剪成功
    @ViewInject(R.id.shenfenzhenghao_tv)
    TextView shenfenzhenghao_tv;
    @ViewInject(R.id.name_tv)
    TextView name_tv;

    @Event(R.id.get_photo)
    private void gotoCarmreEvent(View view) {
        Intent intent = new Intent(AddRenKouActivity.this,
                CameraOrAlbumActivity.class);
        startActivityForResult(intent, CROP);
    }

    String photo_path;//身份证本地地址
    RenKouModel model = null;

    @Event(R.id.next)
    private void nextEvent(View view) {
        if (model == null) {
            showShortToast("model cannoy to be null");
            return;
        }
        if (StringUtils.isEmpty(model.cardNum)) {
            shenfenzhenghao_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.name)) {
            name_tv.setError(getString(R.string.null_inster));
            return;
        }
        EventBus.getDefault().postSticky(model);
        skipIntent(AddRenKou2Activity.class, null, OK_CODE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initReadORC();
        showTitle();
        model = new RenKouModel();
    }

    /**
     * 输入
     *
     * @param view
     */
    @Event({R.id.shenfenzhenghao_tv, R.id.name_tv})
    private void insertInfoEvent(final View view) {
        switch (view.getId()) {
            case R.id.shenfenzhenghao_tv:
                showEdDialog("请输入身份证号码", model.cardNum, EditorInfo.TYPE_CLASS_NUMBER, 0, new AfterInsert() {
                    @Override
                    public void after(String text) {
                        model.cardNum = text;
                        ((TextView) view).setText(text);
                        ((TextView) view).setError(null);

                    }
                });
                break;
            case R.id.name_tv:
                showEdDialog("请输入姓名", model.name, EditorInfo.TYPE_CLASS_TEXT, 1, new AfterInsert() {
                    @Override
                    public void after(String text) {
                        model.name = text;
                        ((TextView) view).setText(text);
                        ((TextView) view).setError(null);

                    }
                });
                break;
        }


    }

    /**
     * //初始化头部
     */
    private void showTitle() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("人口登记");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_return);
    }

    public int mOpenSetLangFlg;
    public static final String mstrFilePathForDat = Environment.getExternalStorageDirectory().toString() + "/tianrui";

    public void initReadORC() {
        mOpenSetLangFlg = 0;
        int rlt = Native.openOcrEngine(mstrFilePathForDat); // step 1: open OCR engine
        rlt = Native.setOcrLanguage(Native.TIANRUI_LANGUAGE_CHINESE_MIXED); // step 2: set recognition language
        if (rlt == 1) {
            mOpenSetLangFlg = 1;
        }
    }

    public static Bitmap mBmppp;
    private static final int DECODEING_PROCESS_FINISH = 2001;
    private static final int DECODEING_PROCESS_FAILD = 2002;
    private static final int INITLANGUAGE_PROCESS_FAILD = 2003;
    public static String[] mwholeWord;//
    public static String[] mwholeTextLine;//
    public static int[] mwholeWordRect;//
    public static int[] mwholdTextLineRect;//
    public String tempImagePathDir;
    public static String mImgFilePath;//

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {
            //裁剪返回
            switch (requestCode) {
                case CROP:
                    Uri headurl = data.getParcelableExtra("ChooseUri");
                    // 处理头像上传
                    photo_path = headurl.getPath();
                    if (photo_path == null) {
                        return;
                    }
                    mImgFilePath = photo_path;
                    int rotateDegree = readPicDegree(mImgFilePath);


                    if (true) {
                        int iRatio = 1;
                        BitmapFactory.Options opts2 = new BitmapFactory.Options();
                        opts2.inJustDecodeBounds = true;
                        mBmppp = BitmapFactory.decodeFile(mImgFilePath, opts2);
                        int picw = opts2.outWidth;
                        int pich = opts2.outHeight;
                        int oldPich = pich;

                        if (picw > 2000 && pich > 2000) {
                            if (picw > pich) {
                                iRatio = picw / 2000 + 1;
                            } else {
                                iRatio = pich / 2000 + 1;
                            }
                        }

                        opts2.inSampleSize = iRatio;
                        opts2.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        opts2.inJustDecodeBounds = false;
                        mBmppp = BitmapFactory.decodeFile(mImgFilePath, opts2);
                    } else {
                        mBmppp = BitmapFactory.decodeFile(mImgFilePath);
                    }
                    //
                    if (rotateDegree != 0) {
                        mBmppp = rotateBitmap(rotateDegree, mBmppp);
                    }

                    if (mBmppp != null) {
                        startDecodeingThread();
                    } else {
//                        failedDialog1.create();
                        T.showShort(AddRenKouActivity.this, "识别失败");

                    }
//                        uploadUserIcon(path, "1");
                    break;
                case OK_CODE:
                    finish();
                    break;
                default:
                    break;
            }


        } else {
//            showToast("放弃上传");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //返回建设置事件
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public Handler mHandler = new MainHandler();

    private class MainHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case DECODEING_PROCESS_FAILD:
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                    showToast("解析失败");

                    break;
//
                case INITLANGUAGE_PROCESS_FAILD:

                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }

                    showToast("解析失败");

                    break;
                case DECODEING_PROCESS_FINISH:
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                    // TODO: 2016/1/28 0028 展示 身份信息
                    if (mwholeTextLine != null) {
                        for (int i = 0; i < mwholeTextLine.length; i++) {
                            if (i == 0) {
                                //TODO 姓名显示
                                name_tv.setText("" + getName(mwholeTextLine[i]));
                            }
                            if (i == mwholeTextLine.length - 1) {
                                //TODO 身份证显示
                                shenfenzhenghao_tv.setText("" + getNum(mwholeTextLine[i]));
                            }

                        }
                    }

                    break;
//
//                default:
//                    break;

            }
        }

    }

    /**
     * 解析出姓名
     *
     * @param a
     * @return
     */
    public String getName(String a) {
        return a.replaceAll(" ", "").trim();
    }

    /**
     * 解析出身份证号码
     *
     * @param a
     * @return
     */
    public String getNum(String a) {
        a = a.replaceAll("O", "0").replaceAll("o", "0").replaceAll("D", "0").replaceAll("I", "1").replaceAll("l", "1").replaceAll(";", "1").replaceAll("L", "1");
//        String a = "love23next234csdn3423javaeye";
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
//        System.out.println(m.replaceAll("").trim());
        return m.replaceAll("").trim();
    }

    /**
     * 读取数据
     */
    public void startDecodeingThread() {
        loadingDialog.show();
        Thread decodeingThread = new Thread(new Runnable() {
            public void run() {

                int picw = mBmppp.getWidth();
                int pich = mBmppp.getHeight();
                int[] pix = new int[picw * pich];

                mBmppp.getPixels(pix, 0, picw, 0, 0, picw, pich);
                int rlt = 0;
                //String path = "file:///android_asset/myKeyedDatNew.dat";
                if (mOpenSetLangFlg == 1)//语言？
                {
                    rlt = Native.recognizeImage(pix, picw, pich); // step 3: recognize one image
                    if (rlt != 1) {
                        mHandler.sendMessage(mHandler.obtainMessage(DECODEING_PROCESS_FAILD, rlt, 0));
                    } else {
                        mwholeWord = Native.getWholeWordResult();
                        mwholeTextLine = Native.getWholeTextLineResult();
                        mwholeWordRect = Native.getWholeWordRect();
                        mwholdTextLineRect = Native.getWholeTextLineRect();

                        drawTextlineRectAndSaveNewBitmap();
                        mHandler.sendMessage(mHandler.obtainMessage(DECODEING_PROCESS_FINISH, rlt, 0));
                    }
                } else {
                    //
                    mHandler.sendMessage(mHandler.obtainMessage(INITLANGUAGE_PROCESS_FAILD, rlt, 0));
                }
            }
        });
        decodeingThread.setName("decodeingThread");
        decodeingThread.start();
    }


    public void drawTextlineRectAndSaveNewBitmap() {

        int iRatio = 2;

        int picw = mBmppp.getWidth();
        int pich = mBmppp.getHeight();
        int oldPich = pich;
      /*BitmapFactory.Options opts2 = new BitmapFactory.Options();
      opts2.inJustDecodeBounds = true;
      BitmapFactory.decodeFile(mImgFilePath, opts2);
      if (picw > 1000 || pich > 1000)
      {
      	if (picw > pich)
			{
				iRatio = picw / 1000 + 1;
			}
			else
			{
				iRatio = pich / 1000 + 1;
			}
      }

       opts2.inSampleSize = iRatio;
       opts2.inPreferredConfig = Bitmap.Config.ARGB_8888;
       opts2.inJustDecodeBounds = false;
       mBmppp = BitmapFactory.decodeFile(mImgFilePath, opts2);
       picw = mBmppp.getWidth();
	   pich = mBmppp.getHeight();*/

        double dRatio = (oldPich * 1.0) / (pich * 1.0);

        dRatio = 1;
        int[] pix = new int[picw * pich];
        mBmppp.getPixels(pix, 0, picw, 0, 0, picw, pich);


        Bitmap dstBitmap = Bitmap.createBitmap(picw, pich, Bitmap.Config.ARGB_8888);
        dstBitmap.setPixels(pix, 0, picw, 0, 0, picw, pich);


        //Bitmap dstBitmap = Bitmap.createBitmap(picw, pich, Config.ARGB_8888);
        //dstBitmap.setPixels(pix, 0, picw, 0, 0, picw, pich);

        Canvas canvas = new Canvas(dstBitmap);

        Paint p = new Paint();
        p.setColor(Color.GREEN);//
        p.setAntiAlias(true);//
        p.setStyle(Paint.Style.STROKE);//

        int i = 0;
        if (mwholdTextLineRect != null) {
            for (i = 0; i < mwholdTextLineRect.length; i += 4) {
                int left, right, top, bottom;
                left = (int) (mwholdTextLineRect[i] / dRatio);
                top = (int) (mwholdTextLineRect[i + 1] / dRatio);
                right = (int) (mwholdTextLineRect[i + 2] / dRatio);
                bottom = (int) (mwholdTextLineRect[i + 3] / dRatio);
                canvas.drawRect(left, top, right, bottom, p);
            }
        }

        tempImagePathDir = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/data/myOCRTempData/";

        File mWorkingPath = new File(tempImagePathDir);
        // if this directory does not exists, make one.
        int mCreatFolderFlg = 1;
        if (!mWorkingPath.exists()) {
            if (!mWorkingPath.mkdirs()) {
                mCreatFolderFlg = 0;
            }
        }

        if (mCreatFolderFlg == 1) {
            long dateTaken = System.currentTimeMillis();
            String datetime = DateFormat.format("yyyyMMddkkmmss", dateTaken).toString();
            try {
                FileOutputStream out = new FileOutputStream(tempImagePathDir + datetime);
                dstBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

                mImgFilePath = tempImagePathDir + datetime;

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取照片角度
     *
     * @param path
     * @return
     */
    public static int readPicDegree(String path) {
        int degree = 0;

        // 读取图片文件信息的类ExifInterface
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }

        return degree;
    }

    /**
     * 旋转角度
     *
     * @param degree
     * @param bitmap
     * @return
     */
    public static Bitmap rotateBitmap(int degree, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);

        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return bm;
    }
}
