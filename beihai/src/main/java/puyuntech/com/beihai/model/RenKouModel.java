package puyuntech.com.beihai.model;

/**
 * 作者：zx
 * 时间：2016-01-21 下午 13:28
 * 描述：人口登记对象
 */
public class RenKouModel extends BaseModel{
    public int id;
    public String name;//姓名
    public String lxfs;//联系方式
    public String mz;//名族
    public String zhzhmm;//政治面貌
    public String jg;//籍贯
    public String xjdd;//现居地址
    public String bzh;//备注
    public String cardNum;//身份证
    public String whchd;//文化程度
    public String hyzhk;//婚姻状况
    public String zjxy;//宗教信仰
    public String sspq = "江苏省南京市栖霞区栖霞街道";//所属片区

    public RenKouModel() {
    }

    public RenKouModel(String name, String cardNum) {
        this.name = name;
        this.cardNum = cardNum;
    }
}
