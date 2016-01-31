package puyuntech.com.beihai.ui.activity.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nicodelee.view.CircularImage;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.APP;
import puyuntech.com.beihai.app.MainBase;
import puyuntech.com.beihai.model.AdsModel;
import puyuntech.com.beihai.model.DataItem;
import puyuntech.com.beihai.model.MainViewItem;
import puyuntech.com.beihai.ui.adapter.MainAdapter;
import puyuntech.com.beihai.ui.adapter.SpacesItemDecoration;

@ContentView(R.layout.activity_main_v3)
public class MainActivityV3 extends MainBase {

    @ViewInject(R.id.content_rv)
    RecyclerView content_rv;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.user_icon)
    CircularImage user_icon;
    @ViewInject(R.id.user_name)
    TextView user_name;
    MainAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    List data;

    List<AdsModel> adsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        initData();
        intiView();
    }

    private void initTitle() {
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("");
        user_name.setText("欢迎，" + APP.user.name);
        Glide.with(this).load(APP.user.url).placeholder(R.mipmap.ic_launcher).into(user_icon);

    }

    private void intiView() {
        //动画效果
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setFirstOnly(false);//是否第一次
        alphaAdapter.setInterpolator(new OvershootInterpolator());

        //列数为两列
        int spanCount = 1;
        mLayoutManager = new StaggeredGridLayoutManager(
                spanCount,
                StaggeredGridLayoutManager.VERTICAL);
        ((StaggeredGridLayoutManager) mLayoutManager).setSpanCount(1);
//        mLayoutManager = new LinearLayoutManager(this);
        content_rv.setLayoutManager(mLayoutManager);
        content_rv.setAdapter(alphaAdapter);

        //加入分割线
        SpacesItemDecoration decoration = new SpacesItemDecoration(0);
        content_rv.addItemDecoration(decoration);

        // 设置item动画
//        content_rv.setItemAnimator(new DefaultItemAnimator());


    }

    private void initData() {
        data = new ArrayList<>();
        data.add(new MainViewItem(new DataItem(R.mipmap.pic_1, (int) (100 + Math.random() * 300), DataItem.MAIN_FUNCTION_TYPE_0), new DataItem(R.mipmap.pic_2, (int) (100 + Math.random() * 300), DataItem.MAIN_FUNCTION_TYPE_1)));
        data.add(new MainViewItem(new DataItem(R.mipmap.pic_3, (int) (100 + Math.random() * 300), DataItem.MAIN_FUNCTION_TYPE_3), new DataItem(R.mipmap.pic_4, (int) (100 + Math.random() * 300), DataItem.MAIN_FUNCTION_TYPE_4)));
        data.add(new MainViewItem(new DataItem(R.mipmap.pic_5, (int) (100 + Math.random() * 300)), null));
        adsList = new ArrayList<>();
        adsList.add(new AdsModel(R.mipmap.pic_1, "新闻一", "我家有只猫"));
        adsList.add(new AdsModel(R.mipmap.pic_8, "新闻二", "我家有只猪"));
        adsList.add(new AdsModel(R.mipmap.pic_9, "新闻三", "我家有只狗"));
        adapter = new MainAdapter(data, adsList) {
            @SuppressLint("NewApi")
            @Override
            protected void onClickItem(View v, int position) {
            }
        };


    }


}
