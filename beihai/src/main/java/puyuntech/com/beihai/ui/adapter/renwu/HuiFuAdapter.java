package puyuntech.com.beihai.ui.adapter.renwu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nicodelee.utils.ListUtils;
import com.nicodelee.utils.StringUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import puyuntech.com.beihai.R;
import puyuntech.com.beihai.model.HuiFuModel;

/**
 * 作者：zx
 * 时间：2016-01-27 上午 11:29
 * 描述：回复adapter
 */
public class HuiFuAdapter extends BaseAdapter {
    List<HuiFuModel> list;
    private LayoutInflater inflater;
    Context context;

    public HuiFuAdapter(Context context, List<HuiFuModel> huiFuList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = huiFuList;
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
        ViewHolder holder;
        int viewType = getItemViewType(position);
        switch (viewType) {
            case NONE_TYPE:
                convertView = inflater.inflate(R.layout.no_data, parent, false);

                break;
            case HAS_DATA_TYPE:
                convertView = inflater.inflate(R.layout.item_huifu_one, parent, false);
                holder = new ViewHolder(convertView);
                if (position == ListUtils.getSize(list) - 1) {//最后一行隐藏line
                    holder.line_show.setVisibility(View.GONE);
                } else {
                    holder.line_show.setVisibility(View.VISIBLE);
                }
                HuiFuModel model = list.get(position);
                holder.huifu_tv.setText(StringUtils.getNotNullStr(model.msg));
                holder.time_lv.setText(StringUtils.getNotNullStr(model.time));
                break;
        }

        return convertView;
    }

    public class ViewHolder {
        @ViewInject(R.id.line_show)
        View line_show;
        @ViewInject(R.id.huifu_tv)
        TextView huifu_tv;
        @ViewInject(R.id.time_lv)
        TextView time_lv;
        View root;

        public ViewHolder(View view) {
            root = view;
            x.view().inject(this, view);
        }
    }
}
