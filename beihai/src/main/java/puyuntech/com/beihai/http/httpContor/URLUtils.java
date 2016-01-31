package puyuntech.com.beihai.http.httpContor;


/**
 *
 */
public class URLUtils {

    public final static int RESULT_SUCCESS = 1;//请求成功
    public final static int RESULT_FAILED = 0;//请求失败
    public final static int RESULT_ERROR = 500;//请求失败，服务器崩溃

    public static final String BASE_URL_APP = "http://172.21.1.142:8033/WSMain.asmx/";//服务器地址

    public static final String LOGIN = BASE_URL_APP + "login";//首页今日抢


}
