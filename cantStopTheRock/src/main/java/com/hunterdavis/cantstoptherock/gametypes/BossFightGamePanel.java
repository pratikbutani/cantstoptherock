package com.hunterdavis.cantstoptherock.gametypes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.AttributeSet;

import com.hunterdavis.cantstoptherock.R;
import com.hunterdavis.cantstoptherock.types.OurLittleBossHero;
import com.hunterdavis.cantstoptherock.types.OurLittleHero;
import com.hunterdavis.cantstoptherock.util.AudioUtils;

/**
 * Created by hunter on 11/30/14.
 */
public class BossFightGamePanel extends BaseStopTheRockPanel {

    Bitmap heartBmp;
    Bitmap halfHeartBmp;
    Bitmap emptyHeartBmp;
    private int heartSize;
    private int heartPadding;

    private Paint paint;

    public BossFightGamePanel(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void initGameState() {
        super.initGameState();
        heartSize = mWidth / 12;
        heartPadding = mWidth / 60;

        // inflate our bitmaps
        emptyHeartBmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.heartempty), heartSize, heartSize, true);

        halfHeartBmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.hearthalf), heartSize, heartSize, true);

        heartBmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.heartfull), heartSize, heartSize, true);
    }

    @Override
    public OurLittleHero getHero() {
        return new OurLittleBossHero(mContext,150,150,heroSize, mWidth/2, 64/gameTickSpeed, mHeight);
    }



    protected void updateCurrentRockPositionTick() {
        if(hero.updateCurrentPositionAndPopOverlapsAndPlayNotes(balloons)) {

            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    AudioUtils.playPoppingSound(mContext, audioManager);
                    return null;
                }
            }.execute();


            if(((OurLittleBossHero)hero).health < 1) {
                gameOver = true;
            }

           };
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the balloons and hero
        if ((!gameOver) && (firstRun == false) && gameStarted) {
            drawBossHealth(canvas);
        }


    }

    public void drawBossHealth(Canvas canvas) {

        canvas.drawRect(0,0,mWidth,(2* heartPadding) + heartSize,paint);

        RectF heartRect = new RectF();
        heartRect.top = heartPadding;
        heartRect.bottom = heartPadding + heartSize;

        for(int i = 0; i < 10; i++) {
            heartRect.right = (i+1) * (heartSize + heartPadding);
            heartRect.left = heartRect.right - heartSize;

            // use -5 to give some wiggle room around heart display
            if(((OurLittleBossHero)hero).health > (((i+1) * (OurLittleBossHero.TOTAL_HEALTH/10))-5) ) {
                canvas.drawBitmap(heartBmp,null,heartRect,null);
            }else if (((OurLittleBossHero)hero).health > (i * (OurLittleBossHero.TOTAL_HEALTH/10))-5) {
                canvas.drawBitmap(halfHeartBmp,null,heartRect,null);
            }else {
                canvas.drawBitmap(emptyHeartBmp,null,heartRect,null);
            }
        }
    }

    @Override
    public String getStartGameText() {
        return "Bomb The Boss!";
    }

    @Override
    public String getGameOverText() {
        return "You Rocked That Boss!";
    }

}