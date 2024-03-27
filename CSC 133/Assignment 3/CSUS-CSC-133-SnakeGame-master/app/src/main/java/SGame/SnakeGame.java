package SGame;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.sgame.R;

import java.io.IOException;

/**
 * SnakeGame class represents the main game logic and rendering of the Snake game.
 * It extends the Android SurfaceView class and implements the Runnable interface for game loop.
 */
class SnakeGame extends SurfaceView implements Runnable{

    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Is the game currently playing and or paused?
    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;

    // for playing sound effects
    private SoundPool mSP;
    private int mEat_ID = -1;
    private int mCrashID = -1;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;

    // How many points does the player have
    private int mScore;

    private Typeface customFont;

    // Objects for drawing
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;

    // A snake ssss
    private Snake mSnake;
    // And an apple
    private Apple mApple;

    //Game difficulty adder
    private int gameSpeed;

    private Bitmap sgBitmap;
    private int sizeX;
    private int sizeY;


    /** This is the constructor method that gets called from SnakeActivity **/
    public SnakeGame(Context context, Point size) {
        super(context);

        AssetManager assetManager = context.getAssets();
        customFont = Typeface.createFromAsset(assetManager,"CustomFont.otf");

        this.sizeX = size.x;
        this.sizeY = size.y;
        // Work out how many pixels each block is
        int blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;

        // Initialize the SoundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("get_apple.ogg");
            mEat_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_death.ogg");
            mCrashID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            // Error
        }

        // Initialize the drawing objects
        mSurfaceHolder = getHolder();
        mPaint = new Paint();

        mPaint.setTypeface(customFont);

        // Call the constructors of our two game objects
        mApple = new Apple(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);

        mSnake = new Snake(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);

        //create back ground bitmap resource
        sgBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.wp7595297);
        //create scaled image of bg to take up entire screen
        sgBitmap = Bitmap.createScaledBitmap(sgBitmap, size.x, size.y, false);

    }


    /**
     * Starts a new game by resetting game components and initializing game parameters.
     * - Resets the snake to its initial position.
     * - Spawns a new apple.
     * - Resets the game score to zero.
     * - Sets the game speed to the default value.
     * - Updates the next frame time to ensure proper game updates.
     */
    public void newGame() {

        // reset the snake
        mSnake.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        // Get the apple ready for dinner
        mApple.spawn();

        // Reset the mScore
        mScore = 0;
        gameSpeed = 5;

        // Setup mNextFrameTime so an update can triggered
        mNextFrameTime = System.currentTimeMillis();
    }


    /**
     * Handles the game loop, continuously updating and rendering the game while it is playing.
     * The method runs in a separate thread and loops until the game is stopped.
     * Within the loop:
     * - Checks if the game is not paused, and if so, proceeds to update the game state.
     * - Invokes the update method if an update is required, ensuring updates occur approximately 10 times per second.
     * - Calls the draw method to render the game state on the screen.
     */
    @Override
    public void run() {
        while (mPlaying) {
            if(!mPaused) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }

            draw();
        }
    }


    /**
     * Checks whether it is time to perform a game update based on the target frame rate.
     * The method determines if an update is required to maintain the desired frame rate.
     * It calculates the time elapsed since the last update and compares it to the target time interval
     * corresponding to the desired frame rate (given by gameSpeed).
     * If the elapsed time exceeds the interval, an update is deemed necessary.
     *
     * @return true if an update is required, false otherwise.
     */
    public boolean updateRequired() {

        // Run at 10 frames per second
        final long TARGET_FPS = gameSpeed;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(mNextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }


    /**
     * Updates the game state by performing various actions such as moving the snake,
     * checking for collisions, updating the score, and adjusting the game speed.
     * This method is called repeatedly during the game loop to update all game objects.
     * - Moves the snake to its next position.
     * - Checks if the snake's head has eaten the apple, and if so, spawns a new apple,
     *   increases the score, and plays an eating sound effect.
     * - Checks if the snake has collided with itself or the game boundaries, and if so,
     *   pauses the game to prepare for restarting.
     * - Increases the snake's speed incrementally based on the current score.
     */
    public void update() {

        // Move the snake
        mSnake.move();

        // Did the head of the snake eat the apple?
        if(mSnake.checkDinner(mApple.getLocation())){
            // This reminds me of Edge of Tomorrow.
            // One day the apple will be ready!
            mApple.spawn();

            // Add to  mScore
            mScore = mScore + 1;

            // Play a sound
            mSP.play(mEat_ID, 1, 1, 0, 0, 1);
        }

        // Did the snake die?
        if (mSnake.detectDeath()) {
            // Pause the game ready to start again
            mSP.play(mCrashID, 1, 1, 0, 0, 1);

            mPaused =true;
        }

        //increase snake speed the higher the score by increments of 5
        if (gameSpeed % 5 == 0 && mScore >= 5){
            gameSpeed++;
        }

    }

    /**
     * Draws all the game elements on the screen including the background, score, apple, snake, and messages.
     * This method is responsible for rendering the game visuals on the canvas.
     * - Locks the canvas to prevent concurrent access.
     * - Fills the screen with a specified color and draws the background bitmap.
     * - Draws the current score and any additional text, such as student names.
     * - Renders the apple and snake objects on the canvas.
     * - Displays appropriate messages when the game is paused or over.
     * - Unlocks the canvas to update the displayed graphics.
     */
    public void draw() {
        // Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();

            // Fill the screen with a color
            mCanvas.drawColor(Color.argb(255, 0,0,0));
            //Draw Background for the game
            mCanvas.drawBitmap(sgBitmap, 0, 0, null);
            // Set the size and color of the mPaint for the text
            mPaint.setColor(Color.argb(255, 255,69,0));
            mPaint.setTextSize(120);


            // Draw the score
            mCanvas.drawText("" + mScore, 80, 130, mPaint);
            //Draw Student Names
            //TODO INPUT GROUP NAMES OF WHO WORKED ON PROJECT BELOW
            mCanvas.drawText("Alex Fails and Brian Hert", sizeX - 1500, 130, mPaint);


            //call method names to draw students names to screen
            //mCanvas.drawText();

            // Draw the apple and the snake
            mApple.draw(mCanvas, mPaint);
            mSnake.draw(mCanvas, mPaint);

            // Draw some text while paused
            if(mPaused && mPlaying){
                drawPlayMessage();
            } else if (mPaused && !mPlaying){
                drawPauseMessage();
            }
            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    /**
     * Draws the "Game Paused" message on the canvas when the game is paused.
     * This method sets the size and color of the paint for the text, then draws the message.
     * The message is positioned at a specific location on the canvas.
     *
     * @see #draw() Method where this message is drawn when the game is paused.
     */
    private void drawPauseMessage() {
        // Set the size and color of the mPaint for the text
        mPaint.setColor(Color.argb(255, 255,69,0));
        mPaint.setTextSize(250);

        // Draw the message
        // We will give this an international upgrade soon
        //mCanvas.drawText("Tap To Play!", 200, 700, mPaint);
        mCanvas.drawText(getResources().
                        getString(R.string.Game_Paused),
                sizeX / 4, 700, mPaint);
    }

    /**
     * Draws the "Tap to Play" message on the canvas when the game is paused and not currently playing.
     * This method sets the size and color of the paint for the text, then draws the message.
     * The message is positioned at a specific location on the canvas.
     *
     * @see #draw() Method where this message is drawn when the game is paused and not currently playing.
     */
    private void drawPlayMessage(){

        // Set the size and color of the mPaint for the text
        mPaint.setColor(Color.argb(255, 255,69,0));
        mPaint.setTextSize(250);

        // Draw the message
        // We will give this an international upgrade soon
        //mCanvas.drawText("Tap To Play!", 200, 700, mPaint);
        mCanvas.drawText(getResources().
                        getString(R.string.tap_to_play),
                sizeX / 4 + 100, 700, mPaint);
    }

    /**
     * Handles touch events on the game screen.
     * Overrides the default behavior of onTouchEvent in SurfaceView.
     * It responds to MotionEvent.ACTION_UP events, primarily used for tapping.
     * If the game is paused and the screen is tapped, it resumes the game by calling newGame().
     * Otherwise, it delegates input handling to the Snake class to change the snake's heading.
     *
     * @param motionEvent The MotionEvent representing the touch event.
     * @return true to indicate that the event has been consumed and no further processing is necessary.
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (mPaused) {
                    mPaused = false;
                    newGame();

                    // Don't want to process snake direction for this tap
                    return true;
                }

                // Let the Snake class handle the input
                mSnake.switchHeading(motionEvent);
                break;

            default:
                break;

        }
        return true;
    }


    /**
     * Pauses the game by stopping the game loop thread.
     * Sets the mPlaying flag to false to halt the game loop execution.
     * Invokes the join() method on the game thread to wait for its termination.
     *
     * @throws InterruptedException if the current thread is interrupted while waiting for the game thread to terminate.
     */
    public void pause() {
        mPlaying = false;
        try {
            mThread.join();

        } catch (InterruptedException e) {
            // Error
        }
    }

    /**
     * Resumes the game by starting the game loop thread.
     * Sets the mPlaying flag to true to indicate that the game is running.
     * Creates a new thread for the game loop and starts it.
     */
    public void resume() {
        mPlaying = true;
        mThread = new Thread(this);
        mThread.start();
    }

    /**
     * Checks if the game is currently paused.
     *
     * @return true if the game is paused, false otherwise.
     */
    public boolean ismPaused() {
        return mPaused;
    }

    /**
     * Sets the pause state of the game.
     *
     * @param mPaused true to pause the game, false to resume.
     */
    public void setmPaused(boolean mPaused) {
        this.mPaused = mPaused;
    }
}
