package puyuntech.com.beihai.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Field;

import puyuntech.com.beihai.R;

/**
 * 作者：zx
 * 时间：2016-01-22 上午 10:23
 * 描述：
 */
public class MyUtils {
    /**
     * 设置弹出框是否可以消失
     *
     * @param dialog
     * @param flag
     */
    public static void setCanel(DialogInterface dialog, boolean flag) {
        try {
            Field field = dialog.getClass()
                    .getSuperclass().getDeclaredField(
                            "mShowing");
            field.setAccessible(true);
            //   将mShowing变量设为false，表示对话框已关闭
            field.set(dialog, flag);
            dialog.dismiss();

        } catch (Exception e) {

        }
    }

    public static Drawable getErrorIcon(Context context, int res) {
        //获取到自定义图标
        Drawable errorIcon = context.getResources().getDrawable(res);
        // 设置图片大小
        errorIcon.setBounds(new Rect(0, 0, errorIcon.getIntrinsicWidth(),
                errorIcon.getIntrinsicHeight()));
        return errorIcon;
    }
}
