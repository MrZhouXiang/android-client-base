package puyuntech.com.beihai.http;

import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 作者：zx
 * 时间：2016-01-28 上午 10:36
 * 描述：
 */
public class HttpTest {
    public static void testHttp() {
        RequestParams params = new RequestParams("http://172.21.1.142:8033/WSMain.asmx/login");
//        params.setSslSocketFactory(""); // 设置ssl
        params.addQueryStringParameter("account", "admin");
        params.addQueryStringParameter("password", "aaaaaa");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
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
