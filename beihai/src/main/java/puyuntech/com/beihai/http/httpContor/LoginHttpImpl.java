package puyuntech.com.beihai.http.httpContor;

import android.content.Context;
import android.widget.Toast;

import com.nicodelee.utils.JsonUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class LoginHttpImpl extends BaseHttpImpl {
    private static LoginHttpImpl mHttpImpl;//单例的接口处理类

    private LoginHttpImpl(Context con) {
        super(con);
        mContext = con;
    }

    /**
     * 获取接口处理类
     *
     * @param con
     * @return
     */
    public static LoginHttpImpl getMHttpImpl(Context con) {
        if (mHttpImpl == null) {
            mHttpImpl = new LoginHttpImpl(con);
        }
        return mHttpImpl;
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     */
    public void login(String account, String password, final HttpAfterExpand afterHttp) {
        RequestParams params = new RequestParams(URLUtils.LOGIN);
//        params.setSslSocketFactory(""); // 设置ssl
        params.addQueryStringParameter("account", account);
        params.addQueryStringParameter("password", password);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                final Result resultBean = JsonUtils.readValue(result, Result.class);
                String code = resultBean.getCode();
                switch (Integer.valueOf(code)) {
                    case URLUtils.RESULT_SUCCESS:
                        afterHttp.afterSuccess(resultBean);
                        break;
                    case URLUtils.RESULT_FAILED:
                        afterHttp.afterFail(resultBean);
                        //请求数据失败
                        showToast(resultBean.getReason());
                        break;
                    case URLUtils.RESULT_ERROR:
                        afterHttp.afterError(resultBean);
                        //服务器崩溃
                        showServerErrorMsg(resultBean);
                        break;
                    default:
                        // 其他情况处理，一般弹出原因
                        showOtherErrorMsg(resultBean);
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

}
