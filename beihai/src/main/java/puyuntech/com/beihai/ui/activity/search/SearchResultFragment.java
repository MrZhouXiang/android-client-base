package puyuntech.com.beihai.ui.activity.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.nicodelee.utils.ListUtils;
import com.nicodelee.utils.T;
import com.nicodelee.utils.WeakHandler;
import com.nicodelee.view.MySwipeRefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.AppDataUtils;
import puyuntech.com.beihai.app.BaseFragment;
import puyuntech.com.beihai.model.RenWuMod;
import puyuntech.com.beihai.ui.adapter.SpacesItemDecoration;
import puyuntech.com.beihai.ui.adapter.renwu.RenWuAdapter;

@ContentView(R.layout.fragment_search_result)
public class SearchResultFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.recyclerview)
    RecyclerView rv;

    @ViewInject(R.id.swipe_container)
    MySwipeRefreshLayout mSwipeLayout;

    private ArrayList<RenWuMod> mylist;
    private RenWuAdapter adapter;
    private boolean isHasMore = true;
    private LinearLayoutManager mLayoutManager;


    public void searchResult(String key) {
        //获取网络数据
        getNetData(0, fragment_key, AppDataUtils.pageSize, 0);//首次加载数据
    }

    int fragment_key;//

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mylist = new ArrayList<>();
        getData();
        initView();
        //获取网络数据
//        getNetData(0, fragment_key, AppDataUtils.pageSize, 0);//首次加载数据
    }

    private void getData() {
        //获取数据
        if (getArguments() != null) {
            fragment_key = getArguments().getInt("more_fragment_key");
        }
    }

    private void initView() {
        adapter = new RenWuAdapter(mylist) {
            @Override
            protected void onClickItem(View v, int position) {
                T.showShort(getActivity(), "here" + position);
            }
        };

        //动画效果
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setFirstOnly(false);//是否第一次
        alphaAdapter.setInterpolator(new OvershootInterpolator());//重力效果

        mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(alphaAdapter);


        //加入分割线
        SpacesItemDecoration decoration = new SpacesItemDecoration(6);
        rv.addItemDecoration(decoration);
        mSwipeLayout.setOnRefreshListener(this);

    }


    /**
     * 加载数据
     *
     * @param page 本地分页标志
     * @param type 任务类型【下达，收到】
     */
    private void getNetData(final int page, int type, int pageSize, int count) {
        String userId = "";//未登录用户id
//        mSwipeLayout.setRefreshing(true);
        ArrayList<RenWuMod> netlist = new ArrayList<>();//模拟网络数据
        for (int i = 0; i < 10; i++) {
            netlist.add(new RenWuMod(type));//任务
        }
        refresh(0, netlist);

    }

    private void refresh(int page, List mMods) {
//        //page = 0 首次 >0 加载更多
        if (page == 0) {
            //首次加载不需要判断,因为adapter需要判断是否有数据,用于显示没有数据的样式
            mylist.clear();
            mylist.addAll(mMods);
            adapter.notifyDataSetChanged();
            isHasMore = true;
        } else if (page > 0) {
            if (ListUtils.isEmpty(mMods) || ListUtils.getSize(mMods) < AppDataUtils.pageSize) {
                //当请求数量小于每页数量,也表示加载完毕
                showToast("全部加载完毕");
                isHasMore = false;
                return;
            }
            mylist.addAll(mMods);
            adapter.notifyItemInserted(mylist.size());
        }
        mSwipeLayout.setRefreshing(false);

    }

    @Override
    public void onRefresh() {
        new WeakHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                getNetData(0, fragment_key, AppDataUtils.pageSize, 0);//首次获取猜你喜欢数据
                mSwipeLayout.setRefreshing(false);

            }
        }, 300);
    }

    public void clearResult() {
        mylist.clear();
        adapter.notifyDataSetChanged();
    }
}
