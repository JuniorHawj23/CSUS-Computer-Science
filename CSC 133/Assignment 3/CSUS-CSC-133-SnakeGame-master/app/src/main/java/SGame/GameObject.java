package SGame;

import android.graphics.Canvas;
import android.graphics.Paint;
/**
 * GameObject interface represents an object in the game that can be drawn on the canvas.
 * Classes implementing this interface must provide an implementation for the draw method.
 */
public interface GameObject {

    /**
     * Draws the game object on the canvas using the provided paint.
     *
     * @param canvas The canvas to draw the game object on.
     * @param paint The paint used to draw the game object.
     */
    void draw(Canvas canvas, Paint paint);
}
