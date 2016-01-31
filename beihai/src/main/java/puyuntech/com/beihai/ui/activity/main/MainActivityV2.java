package puyuntech.com.beihai.ui.activity.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.github.clans.fab.FloatingActionMenu;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.MainBase;
import puyuntech.com.beihai.model.DataItem;
import puyuntech.com.beihai.ui.adapter.ImagesAdapter;
import puyuntech.com.beihai.ui.adapter.SpacesItemDecoration;

/**
 *
 */
@Deprecated
@ContentView(R.layout.activity_main_v2)
public class MainActivityV2 extends MainBase {
    @ViewInject(R.id.content_rv)
    RecyclerView content_rv;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.fl_menu)
    FloatingActionMenu fl_menu;
    ImagesAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    List data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        initData();
        intiView();


    }

    private void intiView() {
        //动画效果
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setFirstOnly(false);//是否第一次
        alphaAdapter.setInterpolator(new OvershootInterpolator());

        //列数为两列
        int spanCount = 2;
//        mLayoutManager = new StaggeredGridLayoutManager(
//                spanCount,
//                StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager = new LinearLayoutManager(this);
        content_rv.setLayoutManager(mLayoutManager);
        content_rv.setAdapter(alphaAdapter);

        //加入分割线
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        content_rv.addItemDecoration(decoration);

        // 设置item动画
//        content_rv.setItemAnimator(new DefaultItemAnimator());

        initFL();

    }


    private void initData() {
        data = new ArrayList<>();
        data.add(new DataItem(R.mipmap.pic_1, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_2, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_3, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_4, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_5, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_6, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_7, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_8, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_9, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_10, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_1, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_2, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_3, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_4, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_5, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_6, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_7, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_8, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_9, (int) (100 + Math.random() * 300)));
        data.add(new DataItem(R.mipmap.pic_10, (int) (100 + Math.random() * 300)));

        adapter = new ImagesAdapter(data) {
            @SuppressLint("NewApi")
            @Override
            protected void onClickItem(View v, int position) {
                ADSDeatilActivity.launch(MainActivityV2.this, v, fl_menu, data.get(position).resId);
            }
        };
    }

    private void initFL() {
        fl_menu.setClosedOnTouchOutside(true);
        fl_menu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {

                }
            }
        });
    }

    @Event({R.id.fb_rengwu, R.id.fb_event})
    private void clickEvent(View v) {
        switch (v.getId()) {
            case R.id.fb_rengwu:
//                showShortToast("新建任务");
//
                fl_menu.close(true);
                removeData(3);
                break;
            case R.id.fb_event:
//                showShortToast("新建事件");
                fl_menu.close(true);
                addData(5);
                break;
            default:
                break;
        }

    }

    public void addData(int position) {
        data.add(position, new DataItem(R.mipmap.ic_launcher, (int) (100 + Math.random() * 300)));
        adapter.notifyItemInserted(position);
    }

    public void removeData(int position) {
        data.remove(position);
        adapter.notifyItemRemoved(position);
    }


}
