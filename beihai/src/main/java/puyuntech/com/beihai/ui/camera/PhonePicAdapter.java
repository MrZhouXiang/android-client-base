package puyuntech.com.beihai.ui.camera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import puyuntech.com.beihai.R;

/*
import com.beyondnet.flashtag.R;
import com.beyondnet.flashtag.utils.BitmapHelp;
import com.beyondnet.flashtag.utils.ImageLoader;
import com.beyondnet.flashtag.utils.ImageLoader.Type;
import com.beyondnet.flashtag.utils.LogUtil;
import com.beyondnet.flashtag.utils.LoginUtil;*/
//import com.puyuntech.sixcontry.R;
//import com.puyuntech.sixcontry.utils.ImageLoader;


public class PhonePicAdapter extends BaseAdapter {


    private Context mContext;
    private List<String> mData;
    private String mDirPath;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;

    private ArrayList<String> picList = new ArrayList<String>();

    public PhonePicAdapter(Context context, List<String> mData, String dirPath, ArrayList<String> tempPicList) {
        this.mContext = context;
        this.mData = mData;
        this.mDirPath = dirPath;
        mInflater = LayoutInflater.from(mContext);
        this.picList = tempPicList;
        mImageLoader = ImageLoader.getInstance(3, ImageLoader.Type.LIFO);
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public Object getItem(int position) {
        return picList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_camera_image, parent,
                    false);
            holder.mImageView = (ImageView) convertView
                    .findViewById(R.id.item_camera_image_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //		holder.mImageView
        //		.setImageResource(R.drawable.ic_launcher);
        //使用Imageloader去加载图片
        //	mImageLoader.loadImage(mDirPath + "/" + mData.get(position),
        //			holder.mImageView);
        holder.mImageView.setImageResource(android.R.drawable.ic_menu_gallery);


        holder.mImageView.setTag(position);
        //正常调用
        if (position != 0) {
            mImageLoader.loadImage(picList.get(position),
                    holder.mImageView, position, true);
        } else {
            holder.mImageView.setImageResource(R.mipmap.btn_new_photo);
        }


        //			mImageLoader.loadImage(picList.get(position),
        //					holder.mImageView,0,true);


        return convertView;
    }

    private final class ViewHolder {
        ImageView mImageView;
    }

}
