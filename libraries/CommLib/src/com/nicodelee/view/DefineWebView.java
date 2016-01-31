package com.nicodelee.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.GridView;

public class DefineWebView extends WebView {

    public DefineWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public DefineWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public DefineWebView(Context context) {
        super(context); 
    } 
    
    @Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
        int expandSpec = MeasureSpec.makeMeasureSpec(  
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
 
    } 
} 