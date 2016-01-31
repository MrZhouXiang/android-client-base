package com.nicodelee.view;

import android.content.Context; 
import android.util.AttributeSet; 
import android.widget.GridView; 
 
public class DefineGridView extends GridView{ 
 
    public DefineGridView(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
    } 
    public DefineGridView(Context context, AttributeSet attrs) { 
        super(context, attrs); 
    } 
    public DefineGridView(Context context) { 
        super(context); 
    } 
    
    @Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
        int expandSpec = MeasureSpec.makeMeasureSpec(  
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
 
    } 
} 