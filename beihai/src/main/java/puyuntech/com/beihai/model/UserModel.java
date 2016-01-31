package puyuntech.com.beihai.model;

import java.util.List;

/**
 * 作者：zx
 * 时间：2016-01-21 下午 13:28
 * 描述：登录人员
 */
public class UserModel extends BaseModel{
    public int id;
    public String name;
    public String url;
    public int type;
    public String account;
    public String password;
    public List<HuiFuModel> huiFuList;

    public UserModel() {
    }

    public UserModel(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public UserModel(int id, String name) {
        this.name = name;
        this.id = id;
    }
}
