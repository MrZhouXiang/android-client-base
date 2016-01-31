package puyuntech.com.beihai.http.httpContor;

/**
 * 作者：zx
 * 时间：2015-12-13 16:39
 * 描述：请求后处理，扩展，增加：无论成功失败之后都会调用的方法afferHttp
 */
public interface HttpAfterExpand extends HttpAfter {
    /**
     * 请求之后处理，无论成功失败之后都会调用
     */
    void afferHttp();
}
