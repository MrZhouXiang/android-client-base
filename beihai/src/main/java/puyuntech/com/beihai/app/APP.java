package puyuntech.com.beihai.app;

import android.app.Application;

import org.xutils.x;

import puyuntech.com.beihai.model.UserModel;


public class APP extends Application {
    private static APP app;

    public static APP getInstance() {
        return app;
    }

//    public static int type = 0;//领导0，网格员1
    public static UserModel user = null;//当前用户

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //xUtils初始化
        x.Ext.init(this);


    }

}
