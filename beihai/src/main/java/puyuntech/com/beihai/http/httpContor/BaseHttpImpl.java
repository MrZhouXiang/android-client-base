package puyuntech.com.beihai.http.httpContor;

import android.content.Context;
import android.widget.Toast;

import puyuntech.com.beihai.R;


/**
 * 作者：zx
 * 时间：2015-11-20 上午 10:31
 * 描述：请求处理层BASE
 */
public class BaseHttpImpl {
    public Context mContext;

    public BaseHttpImpl(Context con) {
        mContext = con;
    }

    /**
     * @param resId
     * @return
     */
    protected String getString(int resId) {
        return mContext.getResources().getString(resId);
    }


    protected void showServerErrorMsg(Result resultBean) {
        //服务器崩溃
        showToast(getString(R.string.server_error) + resultBean.getReason());
    }

    protected void showServerBusyMsg() {
        //服务器链接失败
        showToast(getString(R.string.server_busy));
    }

    protected void showOtherErrorMsg(Result resultBean) {
        //错误码未知
        showToast(resultBean.getCode() + resultBean.getReason());
    }


    /**
     * @param message
     */
    protected void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
