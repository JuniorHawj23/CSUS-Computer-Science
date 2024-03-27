package gamecodeschool.com.subhunter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;


public class SubHunter extends Activity {

    int numberHorizontalPixels;
    int numberVerticalPixels;
    int blockSize;
    int gridWidth = 40;
    int gridHeight;
    float horizontalTouched = -100;
    float verticalTouched = -100;

    boolean debugging = false;

    ImageView gameView;
    Bitmap blankBitmap;
    Canvas canvas;
    Paint paint;

    private SubHunterGame subHunterGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
        blockSize = numberHorizontalPixels / gridWidth;
        gridHeight = numberVerticalPixels / blockSize;

        blankBitmap = Bitmap.createBitmap(numberHorizontalPixels, numberVerticalPixels, Bitmap.Config.ARGB_8888);

        canvas = new Canvas(blankBitmap);
        gameView = new ImageView(this);
        paint = new Paint();

        setContentView(gameView);

        Log.d("Debugging", "In onCreate");
        subHunterGame = new SubHunterGame(gridWidth, gridHeight);
        newGame();
        draw();
    }

    void newGame() {
        subHunterGame.newGame();
    }

    public void draw() {
        gameView.setImageBitmap(blankBitmap);
        canvas.drawColor(Color.argb(255, 255, 255, 255));
        paint.setColor(Color.argb(255, 0, 0, 0));

        for (int i = 0; i < gridWidth; i++) {
            canvas.drawLine(blockSize * i, 0,
                    blockSize * i, numberVerticalPixels,
                    paint);
        }

        for (int i = 0; i < gridHeight; i++) {
            canvas.drawLine(0, blockSize * i, numberHorizontalPixels, blockSize * i,
                    paint);
        }

        canvas.drawRect(horizontalTouched * blockSize,
                verticalTouched * blockSize,
                (horizontalTouched * blockSize) + blockSize,
                (verticalTouched * blockSize) + blockSize,
                paint);

        paint.setTextSize(blockSize * 2);
        paint.setColor(Color.argb(255, 0, 0, 255));
        canvas.drawText(
                "Shots Taken: " + subHunterGame.getShotsTaken() +
                        "  Distance: " + subHunterGame.getDistanceFromSub(),
                blockSize, blockSize * 1.75f,
                paint);

        Log.d("Debugging", "In draw");
        if (debugging) {
            printDebuggingText();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.d("Debugging", "In onTouchEvent");
        if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            takeShot(motionEvent.getX(), motionEvent.getY());
        }
        return true;
    }

    public void takeShot(float touchX, float touchY) {
         horizontalTouched= (int) (touchX / blockSize);
        verticalTouched = (int) (touchY / blockSize);

        subHunterGame.takePlayerShot(horizontalTouched, verticalTouched);
        if (subHunterGame.isGameOver()) {
           boom();
        } else {

            draw();
        }
    }


    public void boom(){
        gameView.setImageBitmap(blankBitmap);
        // Wipe the screen with a red color
        canvas.drawColor(Color.argb(255, 255, 0, 0));
        // Draw some huge white text
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(blockSize * 10);


        canvas.drawText("BOOM!", blockSize * 4,
                blockSize * 14, paint);


        // Draw some text to prompt restarting
        paint.setTextSize(blockSize * 2);
        canvas.drawText("Take a shot to start again",
                blockSize * 8,
                blockSize * 18, paint);
        newGame();
    }


    public void printDebuggingText() {
        paint.setTextSize(blockSize);
        canvas.drawText("numberHorizontalPixels = "
                        + numberHorizontalPixels,
                50, blockSize * 3, paint);
        canvas.drawText("numberVerticalPixels = "
                        + numberVerticalPixels,
                50, blockSize * 4, paint);
        canvas.drawText("blockSize = " + blockSize,
                50, blockSize * 5, paint);
        canvas.drawText("gridWidth = " + gridWidth,
                50, blockSize * 6, paint);
        canvas.drawText("gridHeight = " + gridHeight,
                50, blockSize * 7, paint);
        canvas.drawText("horizontalTouched = " +
                        horizontalTouched, 50,
                blockSize * 8, paint);
        canvas.drawText("verticalTouched = " +
                        verticalTouched, 50,
                blockSize * 9, paint);



        canvas.drawText("shotsTaken = " +
                        subHunterGame.getShotsTaken(),
                50, blockSize * 13, paint);
        canvas.drawText("debugging = " + debugging,
                50, blockSize * 14, paint);
    }
}

