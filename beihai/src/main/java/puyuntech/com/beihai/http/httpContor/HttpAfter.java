package puyuntech.com.beihai.http.httpContor;


/**
 * 作者：zx
 * 时间：2015-11-20 上午 11:39
 * 描述：请求后处理
 */
public interface HttpAfter {

    /**
     * 成功之后处理
     */
    void afterSuccess(Result resultBean);

    /**
     * 失败之后处理
     */
    void afterFail(Result resultBean);

    /**
     * 异常之后处理
     */
    void afterError(Result resultBean);
}
