package com.hunterdavis.cantstoptherock.types;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.hunterdavis.gameutils.rendering.Effects;
import com.hunterdavis.gameutils.rendering.renderMath;

// TODO: Auto-generated Javadoc
/**
 * The Class Balloon.
 */
public class Balloon {
	
	/** The x location. */
    public int xLocation;
	
	/** The y location. */
    public int yLocation;
	
	/** The size. */
    public int size;
	
	/** The age. */
    public int age;
	
	/** The color. */
    public int color;
	
	/** The drawable rect. */
	RectF drawableRect;
	
	/** The tail length. */
	int tailLength;
	
	/** The left tail. */
	Boolean leftTail;
	
	/** The popping frames remaining. */
	int poppingFramesRemaining;
	
	/** The popped. */
    public Boolean popped;
	
	/** The random. */
	Random random;

	/**
	 * Instantiates a new baloon.
	 *
	 * @param xLoc the x loc
	 * @param yLoc the y loc
	 * @param initColor the init color
	 * @param initSize the init size
	 */
    public Balloon(int xLoc, int yLoc, int initColor, int initSize) {
		xLocation = xLoc;
		yLocation = yLoc;
		age = 0;
		color = initColor;
		size = initSize;
		drawableRect = new RectF(xLoc - size, yLoc + size, xLoc + size, yLoc
				- size);
		tailLength = initSize * 3;
		leftTail = new Random().nextBoolean();
		poppingFramesRemaining = 3;
		popped = false;
		random = new Random();
	}

	/**
	 * Pop.
	 */
	public void pop() {
		popped = true;
	}

	// if the given point is within the baloon's bounding box
	/**
	 * Checks if is point within baloon.
	 *
	 * @param xLoc the x loc
	 * @param yLoc the y loc
	 * @return the boolean
	 */
	public Boolean isPointWithinBaloon(float xLoc, float yLoc) {

		float overallDistance = renderMath.fdistance(xLoc, yLoc, xLocation,
				yLocation);
		if (overallDistance > size) {
			return false;
		} else {
			return true;
		}
	}

    public boolean isBalloonActive() {
        if (popped) {
            return false;
        }else if (size == 0) {
            return false;
        }

        return true;
    }

	/**
	 * Update xand y loc.
	 *
	 * @param xLoc the x loc
	 * @param yLoc the y loc
	 */
	public void updateXandYLoc(int xLoc, int yLoc) {
		xLocation = xLoc;
		yLocation = yLoc;
		drawableRect = new RectF(xLoc - size, yLoc + size, xLoc + size, yLoc
				- size);
	}

	/**
	 * Update size.
	 *
	 * @param initSize the init size
	 */
	public void updateSize(int initSize) {
		size = initSize;
		drawableRect = new RectF(xLocation - size, yLocation + size, xLocation
				+ size, yLocation - size);
	}

	/**
	 * Should this baloon die.
	 *
	 * @return true, if successful
	 */
	public boolean shouldThisBaloonDie() {
		if (popped == true) {
			poppingFramesRemaining -= 1;
			updateSize(size + 5);
		}
		if (poppingFramesRemaining <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * Draw baloon.
	 *
	 * @param canvas the canvas
	 * @param paint the paint
	 */
	public void drawBaloon(Canvas canvas, Paint paint) {


		// draw the baloon oval
		if ((popped == false) || (poppingFramesRemaining >= 1)) {
			paint.setColor(color);
			paint.setStyle(Style.FILL);
			canvas.drawOval(drawableRect, paint);
		}

		// draw second from last frame
		if (poppingFramesRemaining == 1) {
			drawBaloonCracks(canvas, 10);
		}

	}

    public void drawBalloonWithText(Canvas canvas, Paint paint, String text) {
        drawBaloon(canvas, paint);

        paint.setColor(Color.YELLOW);
        canvas.drawText(text, xLocation,yLocation+(size/2),paint);
    }


	/**
	 * Draw baloon cracks.
	 *
	 * @param canvas the canvas
	 * @param numCracks the num cracks
	 */
	private void drawBaloonCracks(Canvas canvas, int numCracks) {
		Effects.drawCracks(canvas, xLocation - size, xLocation + size,
				yLocation + size, yLocation - size, numCracks, random);
	}
}
