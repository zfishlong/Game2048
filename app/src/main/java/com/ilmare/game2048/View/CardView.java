package com.ilmare.game2048.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * ===============================
 * 作者: ilmare:
 * 创建时间：5/17/2016 10:37 PM
 * 版本号： 1.0
 * 版权所有(C) 5/17/2016
 * 描述：
 * ===============================
 */

public class CardView extends FrameLayout {

    private TextView label;
    private int num;
    private LayoutParams params;

    public CardView(Context context) {
        this(context, null);
    }

    public CardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initCardView(context);
    }

    private void initCardView(Context context) {

        label = new TextView(context);
        label.setBackgroundColor(0x33ffffff);
        label.setTextSize(32);
        label.setGravity(Gravity.CENTER);


        params = new FrameLayout.LayoutParams(-1,-1);
        params.setMargins(10, 10, 0, 0);
        addView(label, params);

        setNum(0);
    }



    public int getNum() {
        return num;
    }


    public void setNum(int num) {
        this.num = num;

        switch (num) {
            case 0:
                label.setBackgroundColor(0x33ffffff);
                break;
            case 2:
                label.setBackgroundColor(0xffeee4da);
                break;
            case 4:
                label.setBackgroundColor(0xffede0c8);
                break;
            case 8:
                label.setBackgroundColor(0xfff2b179);
                break;
            case 16:
                label.setBackgroundColor(0xfff59563);
                break;
            case 32:
                label.setBackgroundColor(0xfff67c5f);
                break;
            case 64:
                label.setBackgroundColor(0xfff65e3b);
                break;
            case 128:
                label.setBackgroundColor(0xffedcf72);
                break;
            case 256:
                label.setBackgroundColor(0xffedcc61);
                break;
            case 512:
                label.setBackgroundColor(0xffedc850);
                break;
            case 1024:
                label.setBackgroundColor(0xffedc53f);
                break;
            case 2048:
                label.setBackgroundColor(0xffedc22e);
                break;
            default:
                label.setBackgroundColor(0xff3c3a32);
                break;
        }

        if(num==0){
            this.label.setText("");
        }else{

            this.label.setText(num+"");
        }

    }


    public boolean equals(Object o) {
        return this.num==((CardView) o).getNum();
    }
}
