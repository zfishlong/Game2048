package com.ilmare.game2048.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import com.ilmare.game2048.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============================
 * 作者: ilmare:
 * 创建时间：5/17/2016 9:52 PM
 * 版本号： 1.0
 * 版权所有(C) 5/17/2016
 * 描述：
 * ===============================
 */

public class GameView extends GridLayout implements View.OnTouchListener {

    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCardGrid();
    }

    private int startX;  //
    private int startY;  //
    private int cardWidth; //卡片的宽度
    private CardView[][] cardMap = new CardView[4][4]; //保存卡片信息
    private List<Point> emptyPoints = new ArrayList<Point>();
    boolean isFirstChange = true;

    private void initCardGrid() {

        setBackgroundColor(0xffbbada0);
        setColumnCount(4);

        this.setOnTouchListener(this);


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        if (isFirstChange) {
            int width = getWidth();
            int height = getHeight();


            cardWidth = (Math.min(width, height) - 10) / 4;


            addCards(cardWidth);

            startGame();

            isFirstChange = false;
        }


    }

    public void startGame() {

        MainActivity.getMainActivity().clearScore();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cardMap[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum() {  //添加随机生成的数字
        emptyPoints.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardMap[x][y].getNum() == 0) {
                    Point point = new Point(x, y);
                    emptyPoints.add(point);
                }
            }
        }

        Point point = emptyPoints.get((int) (Math.random() * emptyPoints.size()));
        cardMap[point.x][point.y].setNum(Math.random() > 0.1 ? 2 : 4);

    }


    //添加卡片
    private void addCards(int cardWidth) {
        CardView cardView;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cardView = new CardView(getContext());
                cardView.setNum(0);
                this.addView(cardView, cardWidth, cardWidth);
                cardMap[x][y] = cardView;
            }
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                int deltaX = (int) (event.getX() - startX);
                int deltaY = (int) (event.getY() - startY);

                if (Math.abs(deltaX) > Math.abs(deltaY)) { //左右划
                    if (deltaX > 5) {  //右划
                        swipeRight();
                    } else if (deltaX < -5) { //左划
                        swipeLeft();
                    }
                } else {  //上下滑
                    if (deltaY > 5) {
                        swipeDown();
                    } else if (deltaY < -5) {
                        swipeUp();
                    }
                }
                break;
        }
        return true;
    }


    private void swipeLeft() {


        boolean isMerge=false;

        //向左滑动
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardMap[x][y1].getNum() > 0) {

                        if (cardMap[x][y].getNum() <= 0) {

                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            isMerge=true;
                            y--;

                        } else if (cardMap[x][y].equals(cardMap[x][y1])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            isMerge=true;
                        }

                        break;
                    }
                }
            }
        }


        if(isMerge){
            addRandomNum();
            checkComplete();
        }


    }

    private void swipeRight() {

        boolean isMerge=false;


        for (int x = 3; x >= 0; x--) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {

                    if (cardMap[x][y1].getNum() > 0) {

                        if (cardMap[x][y].getNum() <= 0) {
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            y++;
                            isMerge=true;
                        } else if (cardMap[x][y].equals(cardMap[x][y1])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());

                            isMerge=true;
                        }

                        break;
                    }
                }
            }
        }

        if(isMerge){
            addRandomNum();
            checkComplete();
        }

    }

    private void swipeUp() {

        boolean isMerge=false;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardMap[x1][y].getNum() > 0) {

                        if (cardMap[x][y].getNum() <= 0) {

                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            isMerge=true;
                            x--;

                        } else if (cardMap[x][y].equals(cardMap[x1][y])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());

                            isMerge=true;
                        }

                        break;
                    }
                }
            }
        }



        if(isMerge){
            addRandomNum();
            checkComplete();
        }
    }

    private void swipeDown() {

        boolean isMerge=false;

        for (int y = 3; y >= 0; y--) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardMap[x1][y].getNum() > 0) {

                        if (cardMap[x][y].getNum() <= 0) {

                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            isMerge=true;
                            x++;

                        } else if (cardMap[x][y].equals(cardMap[x1][y])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());

                            isMerge=true;
                        }

                        break;
                    }
                }
            }
        }

        if(isMerge){
            addRandomNum();
            checkComplete();
        }

    }


    /**
     * 检查游戏是否结束
     */
    private void checkComplete(){

        boolean complete = true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                if(cardMap[x][y].getNum()==2048){  //提前完成新游戏
                    new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("恭喜你获得了胜利").setPositiveButton("再来一局", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startGame();
                        }
                    }).show();

                    return ;
                }


                if (cardMap[x][y].getNum()==0||
                        (x>0&&cardMap[x][y].equals(cardMap[x-1][y]))||
                        (x<3&&cardMap[x][y].equals(cardMap[x+1][y]))||
                        (y>0&&cardMap[x][y].equals(cardMap[x][y-1]))||
                        (y<3&&cardMap[x][y].equals(cardMap[x][y+1]))) {

                    complete = false;
                    break ALL;
                }
            }
        }

        if (complete) { //
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }

    }

}
