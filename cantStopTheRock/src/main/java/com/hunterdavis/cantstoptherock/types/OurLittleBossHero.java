package com.hunterdavis.cantstoptherock.types;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.hunterdavis.cantstoptherock.R;
import com.hunterdavis.gameutils.rendering.renderMath;

import java.util.Random;

/**
 * Created by hunter on 11/30/14.
 */
public class OurLittleBossHero extends OurLittleHero{

    public static final int DEFAULT_HEALTH_PER_BALLOON = 2;
    public static final int TOTAL_HEALTH = 200;

    int xPosNotToGoPast = 200;
    public int health = TOTAL_HEALTH;
    int healthPerBalloon = DEFAULT_HEALTH_PER_BALLOON;

    Bitmap ourBmp;

    Rect src;
    Rect dest;

    // constantly moving destination point
    private int xDest;
    private int yDest;

    Random random;

    int screenHeight = 200;

    public OurLittleBossHero(Context context, int x, int y, int siz, int dontGoPastThisXPos, int healthPerBalloonHit, int screenHeight) {
        super(x, y, siz);
        xPosNotToGoPast = dontGoPastThisXPos;
        healthPerBalloon = healthPerBalloonHit;

        xDest = x;
        yDest = y;

        // random
        random = new Random();

        // pre-calculate and cache our graphics
        int bossSize = 100;
        ourBmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.dragon),bossSize,bossSize,true);
        dest  = new Rect(xLocation-bossSize/2,yLocation-bossSize/2,xLocation+bossSize/2, yLocation+bossSize/2);
    }


    @Override
    public void moveHero(Balloon[] balloons) {

        if(xLocation < xDest) {
            xLocation += heroMoveSpeed;
        }else if (xLocation > xDest) {
            xLocation -= heroMoveSpeed;
        }

        if(yLocation < yDest) {
            yLocation += heroMoveSpeed;
        }else if (yLocation > yDest) {
            yLocation -= heroMoveSpeed;
        }

        if((xLocation == xDest) && (yLocation == yDest)) {
            xDest = random.nextInt(xPosNotToGoPast);
            yDest = random.nextInt(screenHeight);
        }
}


    @Override
    public boolean tryToPopABalloon(Balloon[] balloons) {
        boolean poppedOne = super.tryToPopABalloon(balloons);
        if(poppedOne) {
            health-=healthPerBalloon;
        }
        return poppedOne;
    }


    @Override
    public void drawHero(Canvas canvas, Paint paint) {

        // draw our boss hero
        canvas.drawBitmap(ourBmp,null,dest,null);
    }

}
