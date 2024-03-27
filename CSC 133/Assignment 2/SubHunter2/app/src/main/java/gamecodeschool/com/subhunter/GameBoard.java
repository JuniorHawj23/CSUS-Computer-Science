package gamecodeschool.com.subhunter;
import android.graphics.Point;
import android.graphics.Paint;

public class GameBoard {

    private Submarine submarine;
    private int distanceFromSub;
    private boolean hit;

    public GameBoard(int gridWidth, int gridHeight) {
        this.submarine = new Submarine(gridWidth, gridHeight);
    }

    public void placeSubmarineRandomly() {
        submarine.placeRandomly();
        distanceFromSub = 0;
        hit = false;
    }

    public boolean isHit() {

        return hit;
    }

    public int getDistanceFromSub() {
        return distanceFromSub;
    }

    public void checkHit(Point shot) {

        if(shot.x == submarine.getSubHorizontalPlacement() && shot.y ==submarine.getVerticalPlacement()) {
            hit= true;
        }
        else {

            int horizontalGap = shot.x - submarine.getSubHorizontalPlacement();
            int verticalGap = shot.y - submarine.getVerticalPlacement();
            distanceFromSub = (int) Math.sqrt(((horizontalGap * horizontalGap) + (verticalGap * verticalGap)));

        }
    }
}