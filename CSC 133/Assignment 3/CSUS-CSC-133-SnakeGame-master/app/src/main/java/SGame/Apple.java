package SGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.sgame.R;

import java.util.Random;

/**
 * Apple class represents the apple object in the game.
 * It implements the GameObject interface.
 */
class Apple implements GameObject{

    // The location of the apple on the grid
    // Not in pixels
    private Point location = new Point();

    // The range of values we can choose from
    // to spawn an apple
    private Point mSpawnRange;
    private int mSize;

    // An image to represent the apple
    private Bitmap mBitmapApple;

    /**
     * Constructs an Apple object with the specified context, spawn range, and size.
     *
     * @param context The context of the application.
     * @param sr The spawn range of the apple.
     * @param s The size of the apple.
     */
    Apple(Context context, Point sr, int s){

        // Make a note of the passed in spawn range
        mSpawnRange = sr;
        // Make a note of the size of an apple
        mSize = s;
        // Hide the apple off-screen until the game starts
        location.x = -10;

        // Load the image to the bitmap
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.mipmap.apple);

        // Resize the bitmap
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, s, s, false);
    }

    /**
     * Spawn method is called every time an apple is eaten to place a new apple in the game.
     */
    void spawn(){
        // Choose two random values and place the apple
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    /**
     * Retrieves the location of the apple.
     *
     * @return The location of the apple.
     */
    Point getLocation(){
        return location;
    }

    /**
     * Draws the apple on the canvas.
     *
     * @param canvas The canvas to draw the apple on.
     * @param paint The paint used to draw the apple.
     */
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapApple,
                location.x * mSize, location.y * mSize, paint);

    }

}