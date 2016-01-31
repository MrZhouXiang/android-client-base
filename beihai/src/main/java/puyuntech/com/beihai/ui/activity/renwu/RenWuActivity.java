package puyuntech.com.beihai.ui.activity.renwu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.APP;
import puyuntech.com.beihai.app.AppDataUtils;
import puyuntech.com.beihai.app.BaseAct;
import puyuntech.com.beihai.ui.activity.search.SearchResultFragment;
import puyuntech.com.beihai.ui.adapter.MainTabPageAdapter;

@ContentView(R.layout.activity_ren_wu)
public class RenWuActivity extends BaseAct {

    @ViewInject(R.id.viewpager)
    protected ViewPager viewPager;//viewPager控件
    @ViewInject(R.id.toolbar)
    protected Toolbar toolbar;//头部控件
    @ViewInject(R.id.tabs)
    protected TabLayout tabLayout;//滑动切换tab控件
    MainTabPageAdapter adapter;
    @ViewInject(R.id.search_view)
    private MaterialSearchView searchView;
    @ViewInject(R.id.searchicon)
    private ImageView searchicon;
    @ViewInject(R.id.fl_content)
    private FrameLayout fl_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initSearch();
    }

    private Fragment searchResultFragment;

    public void initSearch() {
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setBackgroundColor(getResources().getColor(R.color.white));
        searchView.setBackIcon(getResources().getDrawable(R.mipmap.ic_return));
        searchView.setCloseIcon(getResources().getDrawable(R.drawable.ic_action_navigation_close));
        searchView.setHintTextColor(getResources().getColor(R.color.gray));
        searchView.setHint("请输入关键字");
        searchView.setTextColor(getResources().getColor(R.color.black));


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.clearFocus();
                ((SearchResultFragment) searchResultFragment).searchResult(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (searchResultFragment == null) {
                    searchResultFragment = new SearchResultFragment();
                    transaction.add(R.id.fl_content, searchResultFragment);
                }
//                transaction.hide(homeFragment);
                transaction.show(searchResultFragment);
                transaction.commit();

                fl_content.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.show(homeFragment);
                if (searchResultFragment != null) {
                    transaction.hide(searchResultFragment);
                    ((SearchResultFragment) searchResultFragment).clearResult();
                }
                transaction.commit();
//                ll_bottombar.setVisibility(View.VISIBLE);


                fl_content.setVisibility(View.GONE);

            }
        });

        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchView.showSearch();

            }
        });
    }

    private void initView() {
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_return);
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("任务管理");

        if (viewPager != null) {
            setupViewPager(viewPager);
        }


        if (APP.user.type == AppDataUtils.USER_TYPE_1) {
            //网格员
            tabLayout.setVisibility(View.GONE);
        } else {
            //领导
            tabLayout.setupWithViewPager(viewPager);
        }
    }


    /**
     * 设置主页内容
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        if (APP.user.type == AppDataUtils.USER_TYPE_1) {
            //网格员
            adapter = new MainTabPageAdapter(getSupportFragmentManager());
            RenWuListFragment mMoreListFragment2 = new RenWuListFragment();
            Bundle b2 = new Bundle();
            b2.putInt("more_fragment_key", AppDataUtils.REN_WU_GET_TYPE);
            mMoreListFragment2.setArguments(b2);
            adapter.addFragment(mMoreListFragment2, "已接受");
            viewPager.setAdapter(adapter);
        } else {
            //领导
            adapter = new MainTabPageAdapter(getSupportFragmentManager());
            RenWuListFragment mMoreListFragment1 = new RenWuListFragment();
            RenWuListFragment mMoreListFragment2 = new RenWuListFragment();
            Bundle b1 = new Bundle();
            b1.putInt("more_fragment_key", AppDataUtils.REN_WU_SEND_TYPE);
            Bundle b2 = new Bundle();
            b2.putInt("more_fragment_key", AppDataUtils.REN_WU_GET_TYPE);
            mMoreListFragment1.setArguments(b1);
            mMoreListFragment2.setArguments(b2);
            adapter.addFragment(mMoreListFragment1, "已下派");
            adapter.addFragment(mMoreListFragment2, "已接受");
            viewPager.setAdapter(adapter);
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

}
