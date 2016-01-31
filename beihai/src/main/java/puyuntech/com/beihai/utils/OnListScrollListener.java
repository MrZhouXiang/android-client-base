package puyuntech.com.beihai.utils;

/**
 * 作者：zx
 * 时间：2015-12-24 下午 13:51
 * 描述：
 */

import android.widget.AbsListView;

import java.util.List;


/**
 * listview监听滑动到底部
 *
 * @author zx
 *         creat at 2015/12/24 0024 下午 14:08
 **/
public class OnListScrollListener implements OnBottomListener, AbsListView.OnScrollListener {
    List list;

    public OnListScrollListener(List l) {
        list = l;
    }

    private String TAG = getClass().getSimpleName();
    int lastItem = 0;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (list != null && lastItem == list.size() && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            //滑动到底部，加载数据
            onBottom();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItem = firstVisibleItem + visibleItemCount;
    }


    @Override
    public void onBottom() {
        //Log.d(TAG, "is onBottom");
    }

}