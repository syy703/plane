package com.example.administrator.app9;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by Administrator on 2017/5/23.
 */
public class MySurface extends Activity {
    public static int screenWidth;
    public static int screenHeight;//屏幕宽度，高度
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏显示窗口
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new MyView(this));
    }
    public class MyView extends View {
        private int random;
        private int random1;
        private int random2;
        private GameBg gameBg;
        private Timer timer;//定时器
        private Timer timer1;
        private Timer timer2;
        private Timer timer3;
        private TimerTask task;
        private TimerTask task1;
        private TimerTask task2;
        private TimerTask task3;
        private Bitmap pic1;//子弹
        private Bitmap pic2;//按钮
        private Bitmap pic3;//控制器
        private Bitmap pic4;//飞机
        private Bitmap pic5;//被射击的飞机
        private Bitmap pic6;//敌机子弹
        private Bitmap bmpBackGround;//背景图
        private boolean isDrawBullet = false;//是否已有子弹发射,初始值为flase
        private Rect upArea = new Rect(69, 1010, 110, 1060);//玩家控制区域
        private Rect leftArea = new Rect(17, 1071, 69, 1104);//玩家控制区域
        private Rect rightArea = new Rect(110, 1063, 165, 1108);//玩家控制区域
        private Rect downArea = new Rect(69, 1108, 110, 1163);//玩家控制区域
        private Rect button = new Rect(670, 1089, 763, 1178);//玩家控制区域
        private int x = 0, y = 0;//飞机移动的距离
        private int X = 0;//子弹移动的距离
        private int X1 = -200;//敌机子弹移动的距离
        private int Y = 0;//敌机1的纵向移动距离
        private int Z = 0;//被射击的飞机2的纵向移动距离
        private int a = 0;//控制子弹从飞机正中心发射
        private int b = 0;//控制子弹从飞机正中心发射
        private int score = 0;//初始分数
        private int hp = 3;//初始生命
        Bitmap bitmap[] = new Bitmap[6];//存放爆炸图的数组

        public MyView(Context context) {
            super(context);
            //图片加载
            bitmap[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bomb_enemy_0);
            bitmap[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bomb_enemy_1);
            bitmap[2] = BitmapFactory.decodeResource(getResources(), R.drawable.bomb_enemy_2);
            bitmap[3] = BitmapFactory.decodeResource(getResources(), R.drawable.bomb_enemy_3);
            bitmap[4] = BitmapFactory.decodeResource(getResources(), R.drawable.bomb_enemy_4);
            bitmap[5] = BitmapFactory.decodeResource(getResources(), R.drawable.bomb_enemy_4);
            pic1 = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
            pic2 = BitmapFactory.decodeResource(getResources(), R.drawable.button_1);
            pic3 = BitmapFactory.decodeResource(getResources(), R.drawable.controller_2);
            pic4 = BitmapFactory.decodeResource(getResources(), R.drawable.player);
            pic5 = BitmapFactory.decodeResource(getResources(), R.drawable.player1);
            pic6 = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_0);
            bmpBackGround = BitmapFactory.decodeResource(getResources(), R.drawable.map_3);
            gameBg = new GameBg(bmpBackGround);
            random = new Random().nextInt(300);//敌机1坐标
            random1 = new Random().nextInt(350) + 350;//敌机2坐标
            random2 = new Random().nextInt(700) + 30;//子弹坐标
            //定时器启动
            timer1 = new Timer();
            task1 = new TimerTask() {
                public void run() {//敌机1的移动轨迹
                    if (Y < screenHeight) {
                        Y = Y + 5;
                    } else {
                        Y = 0;
                    }
                    postInvalidate();
                }
            };
            timer3 = new Timer();
            task3 = new TimerTask() {
                @Override
                public void run() {
                    if (X1 < screenHeight) {
                        X1 = X1 + 10;
                    } else {
                        X1 = 0;
                    }
                    postInvalidate();
                }
            };
            timer2 = new Timer();
            task2 = new TimerTask() {
                public void run() {//敌机2的移动轨迹
                    if (Z < screenHeight) {
                        Z = Z + 5;
                    } else {
                        Z = 0;
                    }
                    postInvalidate();
                }
            };
            timer1.schedule(task1, 1000, 25);
            timer2.schedule(task2, 1000, 25);
            timer3.schedule(task3, 2000, 25);
            setBackgroundColor(Color.WHITE);
        }

        protected void onSizeChanged(int w, int h, int oldw, int oldh) {//获取屏幕宽度，高度
            screenWidth = w;
            screenHeight = h;
            super.onSizeChanged(w, h, oldw, oldh);

        }

        public boolean onTouchEvent(MotionEvent event) {
            int touchX = (int) event.getX();   //  用户触摸位置的X轴坐标
            int touchY = (int) event.getY();   //  用户触摸位置的Y轴坐标
            //System.out.println(touchX + ", " + touchY);
            //System.out.println(screenWidth+" "+screenHeight);
            if (upArea.contains(touchX, touchY)) {//向上移动
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (screenHeight - 100 - y >= 0) {
                        y = y + 50;
                    }
                    if (!isDrawBullet) {
                        b = y;
                    }
                    postInvalidate();
                }
            }
            if (downArea.contains(touchX, touchY)) {//向下移动
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (screenHeight - 100 - y < screenHeight - 100) {
                        y = y - 50;
                    }
                    if (!isDrawBullet) {
                        b = y;
                    }
                    postInvalidate();
                }
            }
            if (leftArea.contains(touchX, touchY)) {//向左移动
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (screenWidth / 2 - x >= 0) {
                        x = x + 50;
                    }
                    if (!isDrawBullet) {
                        a = x;
                    }
                    postInvalidate();
                }
            }
            if (rightArea.contains(touchX, touchY)) {//向右移动
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (screenWidth / 2 - x < screenWidth - 100) {
                        x = x - 50;
                    }
                    if (!isDrawBullet) {
                        a = x;
                    }
                    postInvalidate();
                }
            }
            if (button.contains(touchX, touchY)) {//发射按钮
                if (!isDrawBullet) {
                    a = x;
                    b = y;
                    isDrawBullet = true;
                    timer = new Timer();
                    task = new TimerTask() {
                        public void run() {
                            X = X + 15;
                            postInvalidate();
                        }

                    };
                    timer.schedule(task, 0, 25);
                }
            }
            return true;
        }

        protected void onDraw(Canvas canvas) {
            Paint paint = new Paint();
            gameBg.draw(canvas, paint);//背景重绘
            gameBg.logic();//背景循环移动
            paint.setTextSize(35);//设置字体
            canvas.drawBitmap(pic3, 0, screenHeight - 180, paint);//方向盘
            canvas.drawBitmap(pic4, screenWidth / 2 - x, screenHeight - 100 - y, paint);//自己操控的飞机
            canvas.drawBitmap(pic2, screenWidth - 100, screenHeight - 100, paint);//按钮
            canvas.drawBitmap(pic5, random, Y, paint);//敌机1
            canvas.drawBitmap(pic6, random2, X1, paint);//敌机子弹
            canvas.drawBitmap(pic5, random1, Z, paint);//敌机2
            canvas.drawText("你目前的得分是:" + score, 450, 30, paint);//分数板
            canvas.drawText("你目前的生命:" + hp, 150, 30, paint);//生命值
            switch (hp) {
                case (3):
                    break;
                case (2):
                    break;
                case (1):
                    break;
                case (0):
                    paint.setTextSize(100);
                    canvas.drawColor(Color.WHITE);
                    canvas.drawText("GAME OVER", screenWidth / 2 - 250, screenHeight / 2, paint);
                default:
                    paint.setTextSize(100);
                    canvas.drawColor(Color.WHITE);
                    canvas.drawText("GAME OVER", screenWidth / 2 - 250, screenHeight / 2, paint);
            }
            if (screenWidth / 2 - a - random > -20 && screenWidth / 2 - a - random < 80) {//子弹射中飞机1爆炸
                if (screenHeight - 100 - b - X - Y > -20 && screenHeight - 100 - b - X - Y < 80) {
                    score++;
                    for (int i = 0; i < 6; i++) {
                        canvas.drawBitmap(bitmap[i], random, Y, paint);
                    }
                    Y = -180;
                    random = new Random().nextInt(300);
                }
            }
            if (screenWidth / 2 - a - random1 > -20 && screenWidth / 2 - a - random1 < 80) {//子弹射中飞机2爆炸
                if (screenHeight - 100 - b - X - Z > -20 && screenHeight - 100 - b - X - Z < 80) {
                    score++;
                    for (int i = 0; i < 6; i++) {
                        canvas.drawBitmap(bitmap[i], random1, Z, paint);
                    }
                    Z = -180;
                    random1 = new Random().nextInt(350) + 350;
                }
            }
            if (random2 - (screenWidth / 2 - x) > -40 && random2 - (screenWidth / 2 - x) < 80) {//不明子弹射中自己的飞机
                if (X1 - (screenHeight - 100 - y) > -40 && X1 - (screenHeight - 100 - y) < 80) {
                    hp--;
                    for (int i = 0; i < 6; i++) {
                        canvas.drawBitmap(bitmap[i], screenWidth / 2 - x, screenHeight - 100 - y, paint);
                    }
                    a = x = 0;
                    b = y = 0;
                    X1 = -500;
                    canvas.drawBitmap(pic4, screenWidth / 2, screenHeight - 100, paint);
                    random2 = new Random().nextInt(700) + 30;
                }
            }
            if (screenWidth / 2 - x - random > -80 && screenWidth / 2 - x - random < 90) {//两架飞机1撞击
                if (screenHeight - 100 - y - Y > -80 && screenHeight - 100 - y - Y < 90) {
                    hp--;
                    for (int i = 0; i < 6; i++) {
                        canvas.drawBitmap(bitmap[i], screenWidth / 2 - x, screenHeight - 100 - y, paint);
                    }
                    a = x = 0;
                    b = y = 0;
                    Y = -180;

                    canvas.drawBitmap(pic4, screenWidth / 2, screenHeight - 100, paint);

                }
            }
            if (screenWidth / 2 - x - random1 > -80 && screenWidth / 2 - x - random1 < 90) {//两架飞机2撞击
                if (screenHeight - 100 - y - Z > -80 && screenHeight - 100 - y - Z < 90) {
                    hp--;
                    for (int i = 0; i < 6; i++) {
                        canvas.drawBitmap(bitmap[i], screenWidth / 2 - x, screenHeight - 100 - y, paint);
                    }
                    a = x = 0;
                    b = y = 0;
                    Z = -180;
                    canvas.drawBitmap(pic4, screenWidth / 2, screenHeight - 100, paint);
                }

            }
            if (isDrawBullet) {//判断是否已有子弹发射
                if (screenHeight - 100 - b - X <= 0 || Y == -180 || Z == -180) {
                    X = 0;
                    timer.cancel();
                    isDrawBullet = false;

                }
            }
            if (isDrawBullet) {//飞机发射子弹
                canvas.drawBitmap(pic1, screenWidth / 2 - a + 25, screenHeight - 100 - b - X, paint);
            }
        }

    }
}



