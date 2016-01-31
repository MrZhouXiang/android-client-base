package puyuntech.com.beihai.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bumptech.glide.Glide;

import de.greenrobot.event.EventBus;
import puyuntech.com.beihai.R;
import puyuntech.com.beihai.model.AdsModel;
import puyuntech.com.beihai.ui.activity.main.ADSDeatilActivity;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements CBPageAdapter.Holder<AdsModel> {
    View view;
    private ImageView imageView;
    private TextView titleView;
    private TextView textView;

    @Override
    public View createView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.item_main_v3_3_item, null);
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = (ImageView) view.findViewById(R.id.image);
        titleView = (TextView) view.findViewById(R.id.text_title);
        textView = (TextView) view.findViewById(R.id.text);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return view;
    }

    @Override
    public void UpdateUI(final Context context, final int position, final AdsModel data) {
//        ImageLoader.getInstance().displayImage(data.getMedium(), imageView);
        Glide.with(context).load(data.url).placeholder(R.mipmap.ic_launcher).into(imageView);

        titleView.setText(data.title);
        textView.setText(data.content);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setEnabled(false);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, 400);
                //点击事件
                EventBus.getDefault().postSticky(data);
                ADSDeatilActivity.launch((Activity) context, imageView, null, data.url);

                //Toast.makeText(view.getContext(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
