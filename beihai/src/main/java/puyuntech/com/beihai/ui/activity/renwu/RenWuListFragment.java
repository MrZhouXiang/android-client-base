
package puyuntech.com.beihai.ui.activity.renwu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.nicodelee.utils.ListUtils;
import com.nicodelee.utils.WeakHandler;
import com.nicodelee.view.MySwipeRefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.AppDataUtils;
import puyuntech.com.beihai.app.BaseAct;
import puyuntech.com.beihai.app.BaseFragment;
import puyuntech.com.beihai.model.HuiFuModel;
import puyuntech.com.beihai.model.RenWuMod;
import puyuntech.com.beihai.model.UserModel;
import puyuntech.com.beihai.ui.adapter.SpacesItemDecoration;
import puyuntech.com.beihai.ui.adapter.renwu.RenWuAdapter;


/**
 * 任务列表承载Fragment
 */
@ContentView(R.layout.fragment_renwu_list)
public class RenWuListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.recyclerview)
    RecyclerView rv;

    @ViewInject(R.id.swipe_container)
    MySwipeRefreshLayout mSwipeLayout;

    private ArrayList<RenWuMod> mylist;
    private RenWuAdapter adapter;
    private boolean isHasMore = true;
    private LinearLayoutManager mLayoutManager;


    int fragment_key;//

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mylist = new ArrayList<>();
        getData();
        initView();

        //获取网络数据
        getNetData(0, fragment_key, AppDataUtils.pageSize, 0);//首次加载数据
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
//                T.showShort(getActivity(), "here");
                mylist.get(position).hasRead = true;//已读
                adapter.notifyItemChanged(position);//刷新当前item
//                EventBus.getDefault().postSticky(mylist.get(position));
                Intent intent = new Intent(getActivity(), RenWuDeatilActivity.class);
                intent.putExtra("mod", mylist.get(position));
                startActivityForResult(intent, BaseAct.OK_CODE);
//                skipIntent(RenWuDeatilActivity.class, false);//跳转页面
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {

            //付款返回
            //裁剪返回
            switch (requestCode) {
                case BaseAct.OK_CODE:
                    showToast("刷新界面");
                    break;
                default:
                    break;
            }


        } else {
        }
    }

    /**
     * 加载数据
     *
     * @param page 本地分页标志
     * @param type 任务类型【下达，收到】
     */
    private void getNetData(final int page, int type, int pageSize, int count) {
        String userId = "";//未登录用户id
        mSwipeLayout.setRefreshing(true);
        ArrayList<RenWuMod> netlist = new ArrayList<>();//模拟网络数据
        RenWuMod mod;
        for (int i = 0; i < 10; i++) {
            mod = new RenWuMod(type);
            mod.content = "我家有只猫，谁帮我喂喂啊";
            mod.title = i + "、我家有只猫";
            mod.time = "2015.10.15 10:30";
            mod.level = i % 2 == 0 ? true : false;
            mod.isComplete = (i > 5 ? true : false);
            mod.hasRead = (i > 1 ? true : false);
            for (int j = 0; j < 3; j++) {
                if (mod.paths == null) {
                    mod.paths = new ArrayList<>();
                }
                mod.paths.add("http://pic14.nipic.com/20110522/7411759_164157418126_2.jpg");
            }
            for (int j = 0; j < 3; j++) {
                if (mod.sendToUsers == null) {
                    mod.sendToUsers = new ArrayList<>();
                }
                UserModel user = new UserModel();
                List<HuiFuModel> huifuList = new ArrayList();
                for (int k = 0; k < 10; k++) {
                    huifuList.add(new HuiFuModel(k, "测试:" + i, "2015-10-15 10:20", k));
                }
                user.huiFuList = huifuList;
                mod.sendToUsers.add(user);
            }
            netlist.add(mod);//任务
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
                getNetData(0, fragment_key, AppDataUtils.pageSize, 0);//首次获取猜你喜欢数据
            }
        }, 300);
    }
}
