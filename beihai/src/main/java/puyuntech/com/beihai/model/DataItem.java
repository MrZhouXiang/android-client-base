package puyuntech.com.beihai.model;

/**
 * Created by hiro on 7/27/15.
 */
public class DataItem extends BaseModel{
    public static final int MAIN_FUNCTION_TYPE_0 = 0;//主页功能类型【0任务下派,1人口登记,2任务列表，3事件列表，4、新闻】
    public static final int MAIN_FUNCTION_TYPE_1 = 1;//主页功能类型【0任务下派,1人口登记,2任务列表，3事件列表，4、新闻】
    public static final int MAIN_FUNCTION_TYPE_2 = 2;//主页功能类型【0任务下派,1人口登记,2任务列表，3事件列表，4、新闻】
    public static final int MAIN_FUNCTION_TYPE_3 = 3;//主页功能类型【0任务下派,1人口登记,2任务列表，3事件列表，4、新闻】
    public static final int MAIN_FUNCTION_TYPE_4 = 4;//主页功能类型【0任务下派,1人口登记,2任务列表，3事件列表，4、新闻】
    public int resId;
    public int height;
    public int type = MAIN_FUNCTION_TYPE_0;

    public DataItem(int resId, int height) {
        this.resId = resId;
        this.height = height;
    }

    public DataItem(int resId, int height, int type) {
        this.resId = resId;
        this.height = height;
        this.type = type;
    }
}
