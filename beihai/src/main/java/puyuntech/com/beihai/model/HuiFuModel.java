package puyuntech.com.beihai.model;

/**
 * 作者：zx
 * 时间：2016-01-27 上午 11:31
 * 描述：
 */
public class HuiFuModel extends BaseModel{
    public int renwuId;
    public String msg;
    public String time;
    public int userId;

    public HuiFuModel(int renwuId, String msg, int userId) {
        this.renwuId = renwuId;
        this.msg = msg;
        this.userId = userId;
    }

    public HuiFuModel(int renwuId, String msg, String time, int userId) {
        this.renwuId = renwuId;
        this.msg = msg;
        this.time = time;
        this.userId = userId;
    }

    public HuiFuModel() {
    }
}
