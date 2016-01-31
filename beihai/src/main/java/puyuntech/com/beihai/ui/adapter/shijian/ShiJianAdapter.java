package puyuntech.com.beihai.ui.adapter.shijian;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicodelee.utils.StringUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import puyuntech.com.beihai.R;
import puyuntech.com.beihai.model.ShiJianMod;

/**
 * Created by hiro on 7/27/15.
 */
public abstract class ShiJianAdapter extends RecyclerView.Adapter<ShiJianAdapter.MyViewHolder> {

    protected List<ShiJianMod> data;

    public ShiJianAdapter(List l) {
        data = l;
    }

    Context con;

    @SuppressLint("InflateParams")
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        con = viewGroup.getContext();
        View v = LayoutInflater.from(con).inflate(R.layout.item_renwu, null);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int i) {
        final ShiJianMod mod = data.get(i);
//        holder.no_tv.setText("任务编号:" + StringUtils.getNotNullStr(mod.no));
        holder.time_tv.setText(StringUtils.getNotNullStr(mod.time));
        holder.title_tv.setText(StringUtils.getNotNullStr(mod.title));
//        holder.username_tv.setText("下达人员:" + StringUtils.getNotNullStr(mod.username));
//        holder.level_tv.setText(StringUtils.getNotNullStr(mod.level ? "紧急" : "不紧急"));
        if (mod.level) {
            holder.level_tv.setText("紧急");
            holder.level_tv.setVisibility(View.VISIBLE);
        } else {
//            holder.level_tv.setVisibility(View.VISIBLE);//
            holder.level_tv.setVisibility(View.GONE);
        }
        if (mod.isComplete) {
            holder.complete_tv.setText("完成");
            holder.complete_tv.setVisibility(View.VISIBLE);
        } else {
//            holder.complete_tv.setVisibility(View.VISIBLE);//
            holder.complete_tv.setVisibility(View.GONE);
        }

        if (mod.hasRead) {
            // TODO: 2016/1/26 0026 已读
            holder.has_read_iv.setVisibility(View.INVISIBLE);

        } else {
            // TODO: 2016/1/26 0026 未读
            holder.has_read_iv.setVisibility(View.VISIBLE);

        }
//        holder.content_type_tv.setText("任务类型:" + StringUtils.getNotNullStr(mod.content_type));
        holder.content_tv.setText("任务内容:" + StringUtils.getNotNullStr(mod.content));
//        holder.content_bz_tv.setText("任务备注:" + StringUtils.getNotNullStr(mod.content_bz));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected abstract void onClickItem(View v, int position);

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.has_read_iv)
        public ImageView has_read_iv;
        @ViewInject(R.id.time_tv)
        public TextView time_tv;
        //        @ViewInject(R.id.username_tv)
        public View root;
        //        @ViewInject(R.id.no_tv)
//        public TextView no_tv;
        @ViewInject(R.id.title_tv)
        public TextView title_tv;
        @ViewInject(R.id.content_tv)
        public TextView content_tv;
        //        @ViewInject(R.id.content_type_tv)
//        public TextView content_type_tv;
//        @ViewInject(R.id.content_bz_tv)
//        public TextView content_bz_tv;
        @ViewInject(R.id.level_tv)
        public TextView level_tv;
        @ViewInject(R.id.complete_tv)
        public TextView complete_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            x.view().inject(this, itemView);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(v, getAdapterPosition());
                }
            });
        }

    }

}
