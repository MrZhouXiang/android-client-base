package puyuntech.com.beihai.ui.adapter.renwu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nicodelee.utils.ListUtils;
import com.nicodelee.utils.StringUtils;
import com.nicodelee.view.DefineListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import puyuntech.com.beihai.R;
import puyuntech.com.beihai.model.UserModel;

/**
 * 作者：zx
 * 时间：2016-01-27 上午 11:29
 * 描述：回复adapter
 */
public class HuiFuListAdapter extends BaseAdapter {
    List<UserModel> list;
    private LayoutInflater inflater;
    Context context;

    public HuiFuListAdapter(Context context, List<UserModel> huiFuList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = huiFuList;
    }

    /**
     * 显示一个人的回复
     */
    private void showHuiFuList(UserModel user, DefineListView huifu_lv) {
        View headerView = inflater.inflate(R.layout.item_huifu_one_head, null);
        TextView name_tv = (TextView) headerView.findViewById(R.id.name_tv);
        name_tv.setText(StringUtils.getNotNullStr(user.name));//姓名显示
        huifu_lv.addHeaderView(headerView);
        HuiFuAdapter adapter = new HuiFuAdapter(context, user.huiFuList);
        huifu_lv.setAdapter(adapter);


    }

    @Override
    public int getCount() {
        return ListUtils.isEmpty(list) ? 1 : ListUtils.getSize(list);
    }

    @Override
    public int getItemViewType(int position) {
        return ListUtils.isEmpty(list) ? NONE_TYPE : HAS_DATA_TYPE;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private final static int NONE_TYPE = 0;

    private final static int HAS_DATA_TYPE = 1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        int viewType = getItemViewType(position);
        switch (viewType) {
            case NONE_TYPE:
                convertView = inflater.inflate(R.layout.no_data, parent, false);
                break;
            case HAS_DATA_TYPE:
                convertView = inflater.inflate(R.layout.item_huifu, parent, false);
                holder = new ViewHolder(convertView);
                final UserModel user = list.get(position);
                //头像显示
                Glide.with(context).load(StringUtils.getNotNullStr(user.url)).placeholder(R.mipmap.renwu_default_head).into(holder.head_iv);
                showHuiFuList(user, holder.huifu_lv);

                break;
        }

        return convertView;
    }

//    public class ViewHolder {
//    }

    class ViewHolder {
        @ViewInject(R.id.head_iv)
        ImageView head_iv;
        @ViewInject(R.id.huifu_lv)
        DefineListView huifu_lv;//回复列表

        View root;

        public ViewHolder(View view) {
            root = view;
            x.view().inject(this, view);
        }
    }
}
