package com.lutong.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lutong.R;


/**标题栏工具
 * @description
 * @param
 * @return
 * @author lutong
 * @time 2021/8/9 8:57
 */

public class TitleBar extends RelativeLayout {
    public TitleBar(Context context) {
        this(context,null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //找到布局
        View view = inflate(context, R.layout.title_toolbar, null);
        ImageView title_image = view.findViewById(R.id.iv_settings);
        TextView title_name = view.findViewById(R.id.tv_title);

        //使用context获取 加载属性的方法 styleable
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        CharSequence text = array.getText(R.styleable.TitleBar_titleText);
        float title_size = array.getDimension(R.styleable.TitleBar_titleSize,15);
        int title_color = array.getColor(R.styleable.TitleBar_titleColor,0);


        title_name.setText(text);//设置字体
        title_name.setTextColor(title_color);//设置字体颜色
        title_name.setTextSize(title_size);//设置字体大小


        array.recycle();
        //添加view设置和xml布局绑定
        addView(view);
    }
}
