package puyuntech.com.beihai.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import de.greenrobot.event.EventBus;
import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.APP;
import puyuntech.com.beihai.app.AppDataUtils;
import puyuntech.com.beihai.model.AdsModel;
import puyuntech.com.beihai.model.DataItem;
import puyuntech.com.beihai.model.MainViewItem;
import puyuntech.com.beihai.model.RenWuMod;
import puyuntech.com.beihai.ui.activity.addrenkou.AddRenKouActivity;
import puyuntech.com.beihai.ui.activity.renwu.AddRenWuActivity;
import puyuntech.com.beihai.ui.activity.renwu.RenWuActivity;
import puyuntech.com.beihai.ui.activity.shijian.ShiJianActivity;

/**
 * Created by hiro on 7/27/15.
 */
public abstract class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    protected List<MainViewItem> data;
    protected List<AdsModel> adsList;

    public MainAdapter(List l, List l2) {
        data = l;
        adsList = l2;
    }

    Context con;

    @SuppressLint("InflateParams")
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        con = viewGroup.getContext();
        int redId = R.layout.item_main_v2;
//        int type = getItemViewType(position);
        switch (type) {
            case 0:
                redId = R.layout.item_main_v3;
                break;
            case 1:
                redId = R.layout.item_main_v3_2;
                break;
            case 2:
                redId = R.layout.item_main_v3_3;
                break;
            default:
                break;
        }
        View v = LayoutInflater.from(con).inflate(redId, null);
        return new MainViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(MainViewHolder mainViewHolder, int position) {

        DataItem left = data.get(position).left;
        DataItem right = data.get(position).right;
        switch (position) {
            case 0:
                if (APP.user.type == AppDataUtils.USER_TYPE_1) {
                    //网格员不显示任务下派
                    mainViewHolder.left.setVisibility(View.GONE);
                } else {
                    mainViewHolder.left.setVisibility(View.VISIBLE);
                }
                mainViewHolder.left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        T.showShort(con, "任务下派");
                        //清除任务数据
                        EventBus.getDefault().removeStickyEvent(RenWuMod.class);

                        Intent i = new Intent(con, AddRenWuActivity.class);
                        con.startActivity(i);
                    }
                });
                mainViewHolder.right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(con, AddRenKouActivity.class);
                        con.startActivity(i);
//                        T.showShort(con, "人口登记");
                    }
                });
//                Glide.with(con).load(left.resId).placeholder(R.mipmap.ic_launcher).into(mainViewHolder.imageView);
                break;
            case 1:
                mainViewHolder.left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(con, RenWuActivity.class);
                        con.startActivity(i);
                    }
                });
                mainViewHolder.right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(con, ShiJianActivity.class);
                        con.startActivity(i);
                    }
                });
//                Glide.with(con).load(left.resId).placeholder(R.mipmap.ic_launcher).into(mainViewHolder.imageView);
                break;
            case 2:
                //显示广告
                showAds(mainViewHolder.convenientBanner, adsList);
                break;
            default:
                break;
        }
    }

    /**
     * 显示商品图片
     *
     * @param imageList
     */
    private void showAds(ConvenientBanner convenientBanner, List<AdsModel> imageList) {
//        DefaultTransformer //无动画
//                AccordionTransformer//折叠式
//                BackgroundToForegroundTransformer //滑入时候变大
//                ForegroundToBackgroundTransformer// 滑出时候变小
//                CubeInTransformer//滑入立方体
//                CubeOutTransformer//滑出立方体
//                FlipHorizontalTransformer//水平中心轴旋转
//                FlipVerticalTransformer//竖直中心轴旋转
//                DepthPageTransformer//有深度卡片堆叠方式
//                StackTransformer//堆栈[和DepthPageTransformer差不多，没有深度]
//                RotateDownTransformer//旋转下半圆
//                RotateUpTransformer//旋转上半圆
//                TabletTransformer//
//                ZoomInTransformer//缩小-放大
//                ZoomOutSlideTransformer//横向缩放
//                ZoomOutTranformer//放大-缩小
        //好用的动画广告插件
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, imageList)
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})//设置小圆点
                .setPointViewVisible(true)//设置小圆点可见
                .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);//设置动画效果
//                .setOnItemClickListener(new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int position) {
//
//                    }
//                });//点击事件
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected abstract void onClickItem(View v, int position);

    public class MainViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.image)
        public ImageView imageView;
        public View root;
        @ViewInject(R.id.convenientBanner)
        ConvenientBanner convenientBanner;//广告
        @ViewInject(R.id.left)
        View left;//左边
        @ViewInject(R.id.right)
        View right;//右边

        public MainViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this, itemView);
            this.root = itemView;
//            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(v, getAdapterPosition());
                }
            });
        }


    }

}
