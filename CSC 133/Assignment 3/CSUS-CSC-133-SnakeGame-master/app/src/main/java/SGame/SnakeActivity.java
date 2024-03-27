
package SGame;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;

/**
 * SnakeActivity class represents the main activity of the Snake game.
 * It extends the Android Activity class.
 */
public class SnakeActivity extends Activity {

    private SnakeGame mSnakeGame;

    /**
     * Called when the activity is first created. Sets up the game and overlay button.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGame();
        setupOverlayButton();
    }

    /**
     * Called when the activity is resumed. Resumes the game.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mSnakeGame.resume();
    }

    /**
     * Called when the activity is paused. Pauses the game.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mSnakeGame.pause();
    }

    /**
     * Sets up the game by initializing the SnakeGame instance and setting its content view.
     */
    private void setupGame() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mSnakeGame = new SnakeGame(this, size);
        setContentView(mSnakeGame);
    }

    /**
     * Sets up the overlay button on the game screen.
     */
    private void setupOverlayButton() {
        OverlayButton overlayButton = new OverlayButton(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        params.setMargins(size.x - 400, 270, 0, 0);
        overlayButton.setAlpha(0.7f);
        overlayButton.setTextSize(24);
        overlayButton.setTitleInt("Pause");
        overlayButton.setLayoutParams(params);
        FrameLayout gameLayout = findViewById(android.R.id.content);
        gameLayout.addView(overlayButton);

        overlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClick();
            }
        });
    }

    /**
     * Handles the click event of the overlay button.
     * Pauses or resumes the game based on its current state.
     */
    private void handleButtonClick() {
        if (!mSnakeGame.ismPaused()) {
            mSnakeGame.pause();
            mSnakeGame.setmPaused(true);
            mSnakeGame.draw();
        } else {
            mSnakeGame.setmPaused(false);
            mSnakeGame.resume();
        }
    }

}