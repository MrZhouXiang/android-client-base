package puyuntech.com.beihai.model;

import puyuntech.com.beihai.R;

/**
 * 广告duixiang
 *
 * @author zx
 *         creat at 2016/1/21 0021 下午 12:49
 **/
public class AdsModel extends BaseModel{

    public int id;
    public int url = R.mipmap.pic_1;
    public String title;
    public String content;

    public AdsModel() {

    }

    public AdsModel(int url, String title, String content) {
        this.url = url;
        this.title = title;
        this.content = content;

    }
}
