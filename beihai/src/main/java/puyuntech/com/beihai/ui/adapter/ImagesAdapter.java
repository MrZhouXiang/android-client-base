package puyuntech.com.beihai.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;
import puyuntech.com.beihai.R;
import puyuntech.com.beihai.model.DataItem;

/**
 * Created by hiro on 7/27/15.
 */
public abstract class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    protected List<DataItem> data;

    public ImagesAdapter(List l) {
        data = l;
    }

    Context con;

    @SuppressLint("InflateParams")
    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        con = viewGroup.getContext();
        View v = LayoutInflater.from(con).inflate(R.layout.item_main_v2, null);
        return new ImagesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder imagesViewHolder, int i) {
        Glide.with(con).load(data.get(i).resId).placeholder(R.mipmap.ic_launcher).into(imagesViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected abstract void onClickItem(View v, int position);

    public class ImagesViewHolder extends AnimateViewHolder {

        public ImageView imageView;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(v, getAdapterPosition());
                }
            });
        }

        @Override
        public void animateRemoveImpl(ViewPropertyAnimatorListener listener) {
            ViewCompat.animate(itemView)
                    .translationY(-itemView.getHeight() * 0.3f)
                    .alpha(0)
                    .setDuration(300)
                    .setListener(listener)
                    .start();
        }

        @Override
        public void preAnimateAddImpl() {
            ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
            ViewCompat.setAlpha(itemView, 0);
        }

        @Override
        public void animateAddImpl(ViewPropertyAnimatorListener listener) {
            ViewCompat.animate(itemView)
                    .translationY(0)
                    .alpha(1)
                    .setDuration(300)
                    .setListener(listener)
                    .start();
        }
    }

}
