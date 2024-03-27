package gamecodeschool.com.subhunter;

import android.graphics.Point;

import java.util.Random;

public class Submarine {

    private final Point placement;
    private final int gridWidth;
    private final int gridHeight;

    int subHorizontalPlacement;
    int subVerticalPlacement;

    public Submarine(int gridWidth, int gridHeight) {
        this.placement = new Point();
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    public void placeRandomly() {
        Random random = new Random();
        subHorizontalPlacement = random.nextInt(gridWidth);
        subVerticalPlacement = random.nextInt(gridHeight);
    }

    public int getVerticalPlacement() {
        return subVerticalPlacement;
    }

    public int getSubHorizontalPlacement() {
        return subHorizontalPlacement;
    }
}