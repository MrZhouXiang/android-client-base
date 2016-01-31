package puyuntech.com.beihai.ui.activity.renwu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nicodelee.utils.HandlerUtil;
import com.nicodelee.utils.ListUtils;
import com.nicodelee.view.DefineListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.BaseAct;
import puyuntech.com.beihai.model.RenWuMod;
import puyuntech.com.beihai.ui.adapter.renwu.HuiFuListAdapter;

/**
 * 任务详情
 */
@ContentView(R.layout.activity_renwu_deatil)
public class RenWuDeatilActivity extends BaseAct {
    @ViewInject(R.id.title_tv)
    TextView title_tv;
    @ViewInject(R.id.name_tv)
    TextView name_tv;
    @ViewInject(R.id.time_tv)
    TextView time_tv;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;

    @ViewInject(R.id.content_tv)
    TextView content_tv;

    @ViewInject(R.id.iv_ll)
    LinearLayout iv_ll;//
    @ViewInject(R.id.huifu_lv)
    DefineListView huifu_lv;//回复
//    @ViewInject(R.id.iv2)
//    ImageView iv2;//附件图片2
//
//    @ViewInject(R.id.iv3)
//    ImageView iv3;//附件图片3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog.show();
        showTitle();//标题栏初始化
        getData();
        initView();
    }

    private void getData() {
        mod = (RenWuMod) getIntent().getSerializableExtra("mod");
    }

    private void initView() {
        showView();
    }

    /**
     * //初始化头部
     */
    private void showTitle() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_return);
        getSupportActionBar().setTitle("任务详情");

    }

    RenWuMod mod;

    public void showView() {
        title_tv.setText(mod.title);
        time_tv.setText(mod.time);
        name_tv.setText(mod.username);
        content_tv.setText(mod.content);//任务内容
        //使用handler防止跳转时候的卡顿
//        HandlerUtil.getUIHandler().post(new Runnable() {
//            @Override
//            public void run() {
        showImages();//显示图片
//            }
//        });
        //使用handler防止跳转时候的卡顿
        HandlerUtil.getUIHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showHuiFu();//显示回复内容
                loadingDialog.dismiss();
            }
        }, 100);
    }


    /**
     * 显示所有回复
     */
    private void showHuiFu() {
        HuiFuListAdapter adapter = new HuiFuListAdapter(this, mod.sendToUsers);
        huifu_lv.setAdapter(adapter);

    }


    /**
     * 展示附件图片
     */
    private void showImages() {
        if (ListUtils.isNotEmpty(mod.paths)) {
            ImageView iv;
            for (int i = 0; i < mod.paths.size(); i++) {
                iv = new ImageView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.dp_100), getResources().getDimensionPixelOffset(R.dimen.dp_100));
                lp.setMargins(0, 0, getResources().getDimensionPixelOffset(R.dimen.dp_14), 0);
                iv.setLayoutParams(lp);
                Glide.with(RenWuDeatilActivity.this).load(mod.paths.get(i)).placeholder(R.mipmap.img_loading).into(iv);
                iv_ll.addView(iv);
            }
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

//    String msg = "";//回复类容

    @Event({R.id.xiapai_tv, R.id.huifu_tv, R.id.ok_tv})
    private void clickEvent(final View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.xiapai_tv:
                //下派
                intent = new Intent(this, AddRenWuActivity.class);
                intent.putExtra("mod", mod);
                startActivity(intent);
                break;
            case R.id.huifu_tv:
                //回复
//                showEdDialog("请输入回复类容", msg, EditorInfo.TYPE_CLASS_PHONE, 0, new AfterInsert() {
//                    @Override
//                    public void after(String text) {
//                        msg = text;
//                        //todo 发送回复
//                        showToast("send " + msg + " to this man");
//                    }
//                });
                intent = new Intent(this, HuiFuActivity.class);
                intent.putExtra("mod", mod);
                startActivity(intent);
                break;
            case R.id.ok_tv:
                //完成
//                showToast("完成");
                // TODO: 2016/1/27 0027 关闭当前页面 ，刷新前一个页面
                finishOK();
                break;
        }
    }
}
