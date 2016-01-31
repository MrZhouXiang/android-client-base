package puyuntech.com.beihai.model;

/**
 * Created by hiro on 7/27/15.
 */
public class MainViewItem extends BaseModel{
    public DataItem left;
    public DataItem right;

    public MainViewItem(DataItem dataItem, DataItem dataItem1) {
        left = dataItem;
        right = dataItem1;

    }
}
