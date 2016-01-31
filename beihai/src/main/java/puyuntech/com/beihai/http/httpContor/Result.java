package puyuntech.com.beihai.http.httpContor;

/**
 * 网络返回结果对象 用于解析
 *
 * @author zx
 * @version [版本号, 2015-1-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Result {
    private String code;// 响应代码 1 0 500
    private Object result;// 响应数据
    private String reason;//原因


    public Result() {
        super();
    }

    public Result(String code, String reason, String failmassage, String errormessage, Object result) {
        super();
        this.code = code;
        this.result = result;
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
