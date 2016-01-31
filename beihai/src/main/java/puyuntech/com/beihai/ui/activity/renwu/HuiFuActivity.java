package puyuntech.com.beihai.ui.activity.renwu;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.BaseAct;

/**
 * 作者：zx
 * 时间：2016-01-27 下午 16:45
 * 描述：
 */
@ContentView(R.layout.activity_hui_fu)
public class HuiFuActivity extends BaseAct {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;//头部

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitle();
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
        getSupportActionBar().setTitle("回复任务");

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
