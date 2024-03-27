package gamecodeschool.com.subhunter;

public class SubHunterGame {

    private static GameBoard gameBoard;
    private Player player;
    private int gridWidth;
    private int gridHeight;

    public SubHunterGame(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.gameBoard = new GameBoard(gridWidth, gridHeight);
        this.player = new Player();
    }

    public static GameBoard getGameBoard() {
        return gameBoard;
    }

    public void newGame() {
        gameBoard.placeSubmarineRandomly();
        player.resetShotsTaken();
    }

    public void takePlayerShot(float touchX, float touchY) {
        player.takeShot(touchX, touchY);
    }

    public boolean isGameOver() {
        return  gameBoard.isHit();
    }

    public int getShotsTaken() {
        return player.getShotsTaken();
    }

    public int getDistanceFromSub() {
        return gameBoard.getDistanceFromSub();
    }


}
