package SGame;


import android.content.Context;
import android.widget.Button;
import android.widget.FrameLayout;
/**
 * OverlayButton class represents a custom button that can be overlaid on other views.
 * It extends the Button class.
 */

public class OverlayButton extends Button {

    private String title;
    private FrameLayout.LayoutParams params;

    private boolean clickable = true;

    /**
     * Constructs an OverlayButton object with the specified context.
     *
     * @param context The context of the application.
     */
    public OverlayButton(Context context) {
        super(context);

        // Set button properties
        setText("Overlay Button");

        // Set button position and size
       params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(0, 0, 0, 0); // Adjust margins as needed
        setLayoutParams(params);

        // Make button non-clickable
        setClickable(clickable);

    }

    /**
     * Retrieves the title of the button.
     *
     * @return The title of the button.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the button.
     *
     * @param title The title to be set for the button.
     */
    public void setTitleInt(String title) {
        this.title = title;
        setText(title);
    }

    /**
     * Placeholder for handling button click events.
     */
    public void onClickInt() {

    }

    /**
     * Retrieves the layout parameters of the button.
     *
     * @return The layout parameters of the button.
     */
    public FrameLayout.LayoutParams getParams() {
        return params;
    }

    /**
     * Sets the layout parameters of the button.
     *
     * @param params The layout parameters to be set for the button.
     */
    public void setParams(FrameLayout.LayoutParams params) {
        this.params = params;
    }

    /**
     * Sets the location of the button within its parent view.
     *
     * @param left   The left margin of the button.
     * @param top    The top margin of the button.
     * @param right  The right margin of the button.
     * @param bottom The bottom margin of the button.
     */
    public void setLocation(int left, int top,int right,int bottom){
        params.setMargins(left, top, right, bottom);
    }

}

