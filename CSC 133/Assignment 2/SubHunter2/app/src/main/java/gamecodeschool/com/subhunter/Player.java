package gamecodeschool.com.subhunter;

import android.graphics.Point;

public class Player {

    private int shotsTaken;

    public void takeShot(float touchX, float touchY) {
        shotsTaken++;
        SubHunterGame.getGameBoard().checkHit(new Point((int) touchX, (int) touchY));
    }
    public int getShotsTaken() {
        return shotsTaken;
    }

    public void resetShotsTaken() {
        shotsTaken = 0;
    }


}
