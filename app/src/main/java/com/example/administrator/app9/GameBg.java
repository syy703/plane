package com.example.administrator.app9;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Administrator on 2017/6/14.
 */
public class GameBg {
    // 游戏背景的图片资源
    // 为了循环播放，这里定义两个位图对象，
    // 其资源引用的是同一张图片
    private Bitmap bmpBackGround1;
    private Bitmap bmpBackGround2;
    private int speed = 5;
    // 游戏背景坐标
    private int bg1x, bg1y, bg2x, bg2y;



    public GameBg(Bitmap bmpBackGround) {

        this.bmpBackGround1 = bmpBackGround;
        this.bmpBackGround2 = bmpBackGround;
        // 首先让第一张填满屏幕
        //bg1y = -Math.abs(bmpBackGround.getHeight() - MyView.screenHeight);
        bg1y=0;
        bg2y = bg1y - bmpBackGround1.getHeight() +250;


    }

   public void draw(Canvas canvas,Paint paint){

       canvas.drawBitmap(bmpBackGround1, bg1x, bg1y, paint);
       canvas.drawBitmap(bmpBackGround2, bg2x, bg2y, paint);

    }
    public void logic(){

        bg1y +=speed;
        bg2y +=speed;

        if(bg1y > MySurface.screenHeight){
            bg1y = bg2y - bmpBackGround1.getHeight() +250;
        }
        if(bg2y > MySurface.screenHeight){
            bg2y = bg1y - bmpBackGround1.getHeight() +250;
        }

    }

}

