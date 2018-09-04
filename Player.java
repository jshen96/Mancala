/****************************************************************
 * Player.java
 * Mancala plater
 */

public abstract class Player {
    protected int move;  //stores the current best move for the player
    private String screenName;
    private int whichPlayer;

    Player() {
	move = 0;
	screenName = new String("");
	whichPlayer = 1;
    }

    /**
     * Callback method to tell the player that it is its turn to move.
     * It can always assume that the player will have at least one valid move.
     *
     * @param context the current position in the game
     *
     * @return the index of the bin to move from.
     */
    public abstract void move(GameState context);

    /**
     * Initializes the internal state of the player.
     *
     * @param screenName the name of the player that will appear on the game display
     * @param whichPlayer either 1 or 2 to indicate if the player is the one who goes first.
     * If other than 1 or 2 it defaults to 1
     */

    public void initialize(String screenName, int whichPlayer) {
	this.screenName = screenName;
	if (whichPlayer > 2 || whichPlayer < 1) {
	    this.whichPlayer = 1;
	}
	else {
	    this.whichPlayer = whichPlayer;
	}
    }

    /**
     * Retrieves a display name for the player.
     *
     * @return a readable name for this player
     */
    public String getDisplayName() {
	return screenName;
    }

    /**
     * @returns the latest move chosen by the player after the latest search.
     * The move must be a valid one according to the latest GameContext sent to the player.
     */
    public int getMove() {
	return move;
    }
}
