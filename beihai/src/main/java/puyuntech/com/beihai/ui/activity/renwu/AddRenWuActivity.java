package puyuntech.com.beihai.ui.activity.renwu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nicodelee.utils.ListUtils;
import com.nicodelee.utils.StringUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.APP;
import puyuntech.com.beihai.app.AppDataUtils;
import puyuntech.com.beihai.app.BaseAct;
import puyuntech.com.beihai.model.RenWuMod;
import puyuntech.com.beihai.model.UserModel;
import puyuntech.com.beihai.ui.camera.CameraOrAlbumActivity;

/**
 * @描述: 任务下派
 * @作者: zx
 * @时间: 2016/1/23 11:17
 * @修改描述:
 * @修改时间: 2016/1/23 11:17
 **/
@ContentView(R.layout.activity_add_ren_wu)
public class AddRenWuActivity extends BaseAct {

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;//头部

    @ViewInject(R.id.username_tv)
    TextView username_tv;
    @ViewInject(R.id.img_ll)
    LinearLayout img_ll;
    @ViewInject(R.id.img1_iv)
    ImageView img1_iv;

    @ViewInject(R.id.biaoti_tv)
    TextView biaoti_tv;
    @ViewInject(R.id.send_member_tv)
    TextView send_member_tv;
    @ViewInject(R.id.type_tv)
    TextView type_tv;
    @ViewInject(R.id.content_tv)
    TextView content_tv;
    @ViewInject(R.id.beizhu_tv)
    TextView beizhu_tv;

    @ViewInject(R.id.level_tv)
    CheckBox level_tv;

    RenWuMod model = null;
    private boolean isEidt = false;//是否是编辑下派

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitle();
        getData();
        showView();
        setListener();
    }

    private void getData() {
        //获取传递过来的model
        model = (RenWuMod) getIntent().getSerializableExtra("mod");
        if (model == null) {
            //如果没有传递过来model,新建一个任务
            model = new RenWuMod(AppDataUtils.REN_WU_SEND_TYPE);//新建一个下派任务
            isEidt = false;
        } else {
            isEidt = true;
        }
        //清空下派对象
        model.sendToUsers = new ArrayList<>();
    }


    private void setListener() {
        //紧急程度
        level_tv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.level = isChecked;
            }
        });
    }

    private void showView() {
        username_tv.setText(APP.user.name);//编辑人
        if (isEidt) {
            //传递的参数展示
            biaoti_tv.setText(StringUtils.getNotNullStr(model.title));
            content_tv.setText(StringUtils.getNotNullStr(model.content));//任务内容
            beizhu_tv.setText(StringUtils.getNotNullStr(model.content_bz));//任务备注
            for (int i = 0; i < model.paths.size(); i++) {
                showOneImage(model.paths.get(i));
            }
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
        getSupportActionBar().setTitle("任务下派");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_return);
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

    /**
     * 输入
     *
     * @param view
     */
    @Event({R.id.biaoti_tv, R.id.minzu_tv, R.id.content_tv, R.id.beizhu_tv, R.id.beizhu_tv})
    private void insertInfoEvent(final View view) {
        switch (view.getId()) {
            case R.id.biaoti_tv:
                showEdDialog("请输入标题", model.title, EditorInfo.TYPE_CLASS_TEXT, -1, new AfterInsert() {
                    @Override
                    public void after(String text) {
                        model.title = text;
                        ((TextView) view).setText(text);
                        ((TextView) view).setError(null);

                    }
                });
                break;
            case R.id.content_tv:
                showEdDialog("请输入任务描述", model.content, EditorInfo.TYPE_CLASS_TEXT, -1, new AfterInsert() {
                    @Override
                    public void after(String text) {
                        model.content = text;
                        ((TextView) view).setText(text);
                        ((TextView) view).setError(null);

                    }
                });
                break;
            case R.id.beizhu_tv:
                showEdDialog("请输入备注", model.content_bz, EditorInfo.TYPE_CLASS_TEXT, -1, new AfterInsert() {
                    @Override
                    public void after(String text) {
                        model.content_bz = text;
                        ((TextView) view).setText(text);
                        ((TextView) view).setError(null);
                    }
                });
                break;
        }


    }

    //    /**
//     * 选择
//     *
//     * @param view
//     */
    @Event({R.id.type_tv, R.id.send_member_tv, R.id.zongjiao_tv})
    private void selectInfoEvent(final View view) {
        switch (view.getId()) {
//            case R.id.level_tv:
//                showSLDialog("请选择紧急程度", AppDataUtils.JIN_JI_CHENG_DU, new AfterSelect() {
//                    @Override
//                    public void after(int position, String text) {
//                        model.level = text;
//                        ((TextView) view).setText(text);
//                    }
//                });
//                break;
            case R.id.type_tv:
                showSLDialog("请选择任务类型", AppDataUtils.REN_WU_LEI_XING, new AfterSelect() {
                    @Override
                    public void after(int position, String text) {
                        model.content_type = text;
                        ((TextView) view).setText(text);
                        ((TextView) view).setError(null);

                    }
                });
                break;
            case R.id.send_member_tv:

                for (int i = 0; i < AppDataUtils.XIA_PAI_DUI_XIANG.length; i++) {
                    // TODO: 2016/1/26 0026  显示文字，已选项
                    choose_names[i] = AppDataUtils.XIA_PAI_DUI_XIANG[i].name;
                }

                showCHDialog("请选择派发对象", choose_names, choose_flags, new AfterCheck() {
                    @Override
                    public void after(boolean[] list2) {
                        if (model.sendToUsers == null) {
                            model.sendToUsers = new ArrayList<UserModel>();
                        }
                        List<String> nameShow = new ArrayList<String>();
                        //设置已选项
                        for (int i = 0; i < list2.length; i++) {
                            // TODO: 2016/1/26 0026  已选项
                            choose_flags[i] = list2[i];
                            if (list2[i]) {//选中
                                model.sendToUsers.add(AppDataUtils.XIA_PAI_DUI_XIANG[i]);
                                nameShow.add(AppDataUtils.XIA_PAI_DUI_XIANG[i].name);
//                                if (i < list2.length - 1)
//                                    //不是最后一项
//                                    nameShow += AppDataUtils.XIA_PAI_DUI_XIANG[i].name + ",";//设置显示的用户
//                                else
//                                    //最后一项
//                                    nameShow += AppDataUtils.XIA_PAI_DUI_XIANG[i].name;//设置显示的用户

                            }
                        }

                        ((TextView) view).setText(nameShow.toString());
                        ((TextView) view).setError(null);

                    }
                });
                break;
        }

    }

    boolean[] choose_flags = new boolean[AppDataUtils.XIA_PAI_DUI_XIANG.length];
    String[] choose_names = new String[AppDataUtils.XIA_PAI_DUI_XIANG.length];

    private static final int CROP = 2;//裁剪成功

    /**
     * 点击
     *
     * @param view
     */
    @Event({R.id.img1_iv, R.id.ok_tv})
    private void clickEvent(final View view) {
        switch (view.getId()) {
            case R.id.img1_iv:
                Intent intent = new Intent(this,
                        CameraOrAlbumActivity.class);
                intent.putExtra("aspectX", 1);//裁剪比例
                intent.putExtra("aspectY", 1);//裁剪比例
                intent.putExtra("outputX", 300);//输出比例
                intent.putExtra("outputY", 300);//输出比例
                startActivityForResult(intent, CROP);
                break;
            case R.id.ok_tv:
                submit();
                break;
        }
    }

    private void submit() {
        if (StringUtils.isEmpty(model.title)) {
            biaoti_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (ListUtils.isEmpty(model.sendToUsers)) {
            send_member_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.content_type)) {
            type_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.content)) {
            content_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.content_bz)) {
            beizhu_tv.setError(getString(R.string.null_inster));
            return;
        }

        showToast("标题：" + model.title + "紧急：" + model.level + "对象：" + "" + "描述：" + model.content + "备注：" + model.content_bz);
    }

//    @Event(value = R.id.level_tv, method = "CompoundButton.OnCheckedChangeListener.class")
//    private void checkEvent(final View view) {
//        switch (view.getId()) {
//            case R.id.level_tv:
//
//                break;
//        }
//
//    }


    List<String> path = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {

            //付款返回
            //裁剪返回
            switch (requestCode) {
                case CROP:
                    final Uri headurl = data.getParcelableExtra("ChooseUri");
                    // 处理头像上传
                    String photo_path = headurl.getPath();
                    showOneImage(photo_path);
                    break;
            }


        } else {
//            showToast("放弃上传");
        }
    }

    private void showOneImage(final String photo_path) {
        path.add(photo_path);
        ImageView img = new ImageView(this);
        int width = getResources().getDimensionPixelOffset(R.dimen.dp_100);
        int height = getResources().getDimensionPixelOffset(R.dimen.dp_100);
        int margin = getResources().getDimensionPixelOffset(R.dimen.dp_10);
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(width, height);
        l.setMargins(margin, 0, 0, 0);
        img.setLayoutParams(l);
        Glide.with(this).load(photo_path).placeholder(R.mipmap.ic_launcher).into(img);
        img_ll.addView(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO: 2016/1/25 0025 提示是否删除
                new AlertDialog.Builder(AddRenWuActivity.this).setTitle("是否删除？")
                        .setMessage("确定删除当前已选图片?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                path.remove(photo_path);
                                img_ll.removeView(v);
                                if (path.size() == 3) {
                                    img1_iv.setVisibility(View.GONE);
                                } else {
                                    img1_iv.setVisibility(View.VISIBLE);
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();

            }
        });
        if (path.size() == 3) {
            img1_iv.setVisibility(View.GONE);
        } else {
            img1_iv.setVisibility(View.VISIBLE);

        }
    }


}
