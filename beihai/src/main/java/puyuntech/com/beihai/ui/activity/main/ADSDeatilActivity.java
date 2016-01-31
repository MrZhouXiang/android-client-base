package puyuntech.com.beihai.ui.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.BaseAct;
import puyuntech.com.beihai.model.AdsModel;

/**
 * 广告详情
 */
@ContentView(R.layout.activity_ads_deatil)
public class ADSDeatilActivity extends BaseAct {
    public static void launch(Activity activity, View transitionView, View transitionView2, int resId) {
        Intent intent = new Intent(activity, ADSDeatilActivity.class);
        intent.putExtra("resId", resId);
//        ActivityOptionsCompat options = ActivityOptionsCompat
//                .makeSceneTransitionAnimation(activity, Pair.create(transitionView, "image"), Pair.create(transitionView2, "fab"));

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, Pair.create(transitionView, "image"));
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @ViewInject(R.id.image)
    ImageView imageView;

    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
//    @ViewInject(R.id.fl_menu)
//    FloatingActionMenu fl_menu;

    @ViewInject(R.id.content_tv)
    TextView content_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitle();//标题栏初始化
        initView();


    }

    private void initView() {
        imageView.setImageResource(getIntent().getIntExtra("resId", R.mipmap.pic_1));
        ViewCompat.setTransitionName(imageView, "image");
        //        ViewCompat.setTransitionName(fl_menu, "fab");

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
    }

    public void onEvent(AdsModel event) {
        content_tv.setText(event.content);
        getSupportActionBar().setTitle(event.title);

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
}
