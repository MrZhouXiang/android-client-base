package puyuntech.com.beihai.model;

import java.util.List;

/**
 * 作者：zx
 * 时间：2016-01-21 下午 16:49
 * 描述：
 */
public class ShiJianMod extends BaseModel{
    public int type;//任务类型[下达，收到]
    public String id;//任务编号
    public String title;//任务标题
    public String time;//下达时间
    public String username;//编辑人员
    public boolean level = false;//紧急程度
    public boolean isComplete = false;//是否完成
    public boolean hasRead = false;//已读未读？
    public String content_type;//任务类型
    public String content;//任务描述
    public String content_bz;//任务备注

    public List<String> paths;//任务附件地址
    public List<UserModel> sendToUsers;//下派对象

    public ShiJianMod(int type) {
        this.type = type;
    }

    public ShiJianMod() {
    }
}
