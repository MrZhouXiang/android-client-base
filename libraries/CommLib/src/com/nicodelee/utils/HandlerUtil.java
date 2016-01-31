package com.nicodelee.utils;

import android.os.Handler;

/**
 * UIhandler 单例
 * 
 * @author 姓名
 * @version [版本号, 2015-5-14]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HandlerUtil
{
    private static Handler UIHandler;
    
    public static Handler getUIHandler()
    {
        if (HandlerUtil.UIHandler == null)
        {
            HandlerUtil.UIHandler = new Handler();
        }
        return UIHandler;
    }
    
}
