package puyuntech.com.beihai.app;

import puyuntech.com.beihai.model.UserModel;

/**
 * 作者：zx
 * 时间：2016-01-21 下午 16:50
 * 描述：
 */
public class AppDataUtils {
    public final static int pageSize = 20;
    public static final int USER_TYPE_0 = 0;//用户权限：领导
    public static final int USER_TYPE_1 = 1;//用户权限：网格员
    public final static int REN_WU_GET_TYPE = 1;//获得的任务
    public final static int REN_WU_SEND_TYPE = 2;//下派的任务

    public final static int SHI_JIAN_GET_TYPE = 1;//获得的事件
    public final static int SHI_JIAN_SEND_TYPE = 2;//发送

    public static final String[] JIN_JI_CHENG_DU = {"加急", "紧急", "普通"};
    //    public static final String[] XIA_PAI_DUI_XIANG = {"张三", "李四", "王五", "周大大", "周小小"};
    public static final UserModel[] XIA_PAI_DUI_XIANG = {new UserModel(1, "张三"), new UserModel(2, "李四"), new UserModel(3, "王五"), new UserModel(4, "周大大")};
    public static final String[] REN_WU_LEI_XING = {"社会治安类（1）", "人口管理类（2）", "未知（0）"};
    public static final String[] ZHENG_ZHI_MIAN_MAO = {"党员", "群众"};
    public static final String[] WEN_HUA_CHENG_DU = {"博士", "硕士", "本科", "高中", "初中", "小学", "无"};
    public static final String[] HUN_YIN_ZHUANG_KUANG = {"已婚", "未婚"};
    public static final String[] ZONG_JIAO_XING_YANG = {"无", "伊斯兰", "基督教", "佛教", "其他"};

}
