package SGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.example.sgame.R;

import java.util.ArrayList;

/**
 * Snake class represents the snake object in the game.
 * It implements the GameObject interface.
 */
class Snake implements GameObject {

    // The location in the grid of all the segments
    private ArrayList<Point> segmentLocations;

    // How big is each segment of the snake?
    private int mSegmentSize;

    // How big is the entire grid
    private Point mMoveRange;

    // Where is the centre of the screen
    // horizontally in pixels?
    private int halfWayPoint;

    // For tracking movement Heading
    private enum Heading {
        UP, RIGHT, DOWN, LEFT
    }

    // Start by heading to the right
    private Heading heading = Heading.RIGHT;

    // A bitmap for each direction the head can face
    private Bitmap mBitmapHeadRight;
    private Bitmap mBitmapHeadLeft;
    private Bitmap mBitmapHeadUp;
    private Bitmap mBitmapHeadDown;

    // A bitmap for the body
    private Bitmap mBitmapBody;

    /**
     * Constructs a Snake object with the specified context, move range, and segment size.
     *
     * @param context The context of the application.
     * @param mr      The move range of the snake.
     * @param ss      The size of each segment of the snake.
     */
    Snake(Context context, Point mr, int ss) {

        // Initialize our ArrayList
        segmentLocations = new ArrayList<>();

        // Initialize the segment size and movement
        // range from the passed in parameters
        mSegmentSize = ss;
        mMoveRange = mr;

        // Create and scale the bitmaps
        mBitmapHeadRight = BitmapFactory
                .decodeResource(context.getResources(),
                        R.mipmap.head);

        // Create 3 more versions of the head for different headings
        mBitmapHeadLeft = BitmapFactory
                .decodeResource(context.getResources(),
                        R.mipmap.head);

        mBitmapHeadUp = BitmapFactory
                .decodeResource(context.getResources(),
                        R.mipmap.head);

        mBitmapHeadDown = BitmapFactory
                .decodeResource(context.getResources(),
                        R.mipmap.head);

        // Modify the bitmaps to face the snake head
        // in the correct direction
        mBitmapHeadRight = Bitmap
                .createScaledBitmap(mBitmapHeadRight,
                        ss, ss, false);

        // A matrix for scaling
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);

        mBitmapHeadLeft = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, ss, ss, matrix, true);

        // A matrix for rotating
        matrix.preRotate(-90);
        mBitmapHeadUp = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, ss, ss, matrix, true);

        // Matrix operations are cumulative
        // so rotate by 180 to face down
        matrix.preRotate(180);
        mBitmapHeadDown = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, ss, ss, matrix, true);

        // Create and scale the body
        mBitmapBody = BitmapFactory
                .decodeResource(context.getResources(),
                        R.mipmap.body);

        mBitmapBody = Bitmap
                .createScaledBitmap(mBitmapBody,
                        ss, ss, false);

        // The halfway point across the screen in pixels
        // Used to detect which side of screen was pressed
        halfWayPoint = mr.x * ss / 2;
    }


    /**
     * Resets the snake for a new game with the specified width and height.
     *
     * @param w The width of the game grid.
     * @param h The height of the game grid.
     */
    void reset(int w, int h) {

        // Reset the heading
        heading = Heading.RIGHT;

        // Delete the old contents of the ArrayList
        segmentLocations.clear();

        // Start with a single snake segment
        segmentLocations.add(new Point(w / 2, h / 2));
    }

    /**
     * Moves the snake based on its current heading.
     */
    void move() {
        // Move the body
        // Start at the back and move it
        // to the position of the segment in front of it
        for (int i = segmentLocations.size() - 1; i > 0; i--) {

            // Make it the same value as the next segment
            // going forwards towards the head
            segmentLocations.get(i).x = segmentLocations.get(i - 1).x;
            segmentLocations.get(i).y = segmentLocations.get(i - 1).y;
        }

        // Move the head in the appropriate heading
        // Get the existing head position
        Point p = segmentLocations.get(0);

        // Move it appropriately
        switch (heading) {
            case UP:
                p.y--;
                break;

            case RIGHT:
                p.x++;
                break;

            case DOWN:
                p.y++;
                break;

            case LEFT:
                p.x--;
                break;
        }

    }

    /**
     * Detects if the snake has died by hitting the screen edges or itself.
     *
     * @return True if the snake has died, false otherwise.
     */
    boolean detectDeath() {
        // Has the snake died?
        boolean dead = false;

        // Hit any of the screen edges
        if (segmentLocations.get(0).x == -1 ||
                segmentLocations.get(0).x > mMoveRange.x ||
                segmentLocations.get(0).y == -1 ||
                segmentLocations.get(0).y > mMoveRange.y) {

            dead = true;
        }

        // Eaten itself?
        for (int i = segmentLocations.size() - 1; i > 0; i--) {
            // Have any of the sections collided with the head
            if (segmentLocations.get(0).x == segmentLocations.get(i).x &&
                    segmentLocations.get(0).y == segmentLocations.get(i).y) {

                dead = true;
            }
        }
        return dead;
    }

    /**
     * Checks if the snake has eaten its dinner.
     *
     * @param l The location of the dinner (apple).
     * @return True if the snake has eaten the dinner, false otherwise.
     */
    boolean checkDinner(Point l) {
        //if (snakeXs[0] == l.x && snakeYs[0] == l.y) {
        if (segmentLocations.get(0).x == l.x &&
                segmentLocations.get(0).y == l.y) {

            // Add a new Point to the list
            // located off-screen.
            // This is OK because on the next call to
            // move it will take the position of
            // the segment in front of it
            segmentLocations.add(new Point(-10, -10));
            return true;
        }
        return false;
    }

    /**
     * Draws the snake on the canvas using the provided paint.
     *
     * @param canvas The canvas to draw the snake on.
     * @param paint  The paint used to draw the snake.
     */
    @Override
    public void draw(Canvas canvas, Paint paint) {

        // Don't run this code if ArrayList has nothing in it
        if (!segmentLocations.isEmpty()) {
            // All the code from this method goes here
            // Draw the head
            switch (heading) {
                case RIGHT:
                    canvas.drawBitmap(mBitmapHeadRight,
                            segmentLocations.get(0).x
                                    * mSegmentSize,
                            segmentLocations.get(0).y
                                    * mSegmentSize, paint);
                    break;

                case LEFT:
                    canvas.drawBitmap(mBitmapHeadLeft,
                            segmentLocations.get(0).x
                                    * mSegmentSize,
                            segmentLocations.get(0).y
                                    * mSegmentSize, paint);
                    break;

                case UP:
                    canvas.drawBitmap(mBitmapHeadUp,
                            segmentLocations.get(0).x
                                    * mSegmentSize,
                            segmentLocations.get(0).y
                                    * mSegmentSize, paint);
                    break;

                case DOWN:
                    canvas.drawBitmap(mBitmapHeadDown,
                            segmentLocations.get(0).x
                                    * mSegmentSize,
                            segmentLocations.get(0).y
                                    * mSegmentSize, paint);
                    break;
            }

            // Draw the snake body one block at a time
            for (int i = 1; i < segmentLocations.size(); i++) {
                canvas.drawBitmap(mBitmapBody,
                        segmentLocations.get(i).x
                                * mSegmentSize,
                        segmentLocations.get(i).y
                                * mSegmentSize, paint);
            }
        }
    }


    // Handle changing direction
    void switchHeading(MotionEvent motionEvent) {

        // Is the tap on the right hand side?
        if (motionEvent.getX() >= halfWayPoint) {
            switch (heading) {
                // Rotate right
                case UP:
                    heading = Heading.RIGHT;
                    break;
                case RIGHT:
                    heading = Heading.DOWN;
                    break;
                case DOWN:
                    heading = Heading.LEFT;
                    break;
                case LEFT:
                    heading = Heading.UP;
                    break;

            }
        } else {
            // Rotate left
            switch (heading) {
                case UP:
                    heading = Heading.LEFT;
                    break;
                case LEFT:
                    heading = Heading.DOWN;
                    break;
                case DOWN:
                    heading = Heading.RIGHT;
                    break;
                case RIGHT:
                    heading = Heading.UP;
                    break;
            }
        }
    }
}
