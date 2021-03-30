package g54516.luckynumbers.model;

/**
 * Class Game for implements the Model.
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public class Game implements Model {

    private int playerCount;
    private int currentPlayerNumber;
    private Board[] boards;
    private Tile pickedTile;
    private State state;

    /**
     * Constructor who initialize attribute state of the class Game.
     */
    public Game() {
        this.state = State.NOT_STARTED;
    }

    @Override
    public void start(int playerCount) {
        if (this.state != State.NOT_STARTED
                && this.state != State.GAME_OVER) {
            throw new IllegalStateException("State must be NOT_STARTED "
                    + "nor GAME_OVER");
        } else if (playerCount < 2 || playerCount > 4) {
            throw new IllegalArgumentException("the number of players is not between 2 and 4 "
                    + "(both included)");
        }
        this.boards = new Board[playerCount];
        for (int i = 0; i < this.boards.length; i++) {
            this.boards[i] = new Board();
        }
        this.playerCount = playerCount;
        this.currentPlayerNumber = 0;
        this.state = State.PICK_TILE;
    }

    @Override
    public int getBoardSize() {
        return this.boards[0].getSize();
    }

    @Override
    public Tile pickTile() {
        if (this.state != State.PICK_TILE) {
            throw new IllegalStateException("State is not PICK_TILE");
        }
        this.state = State.PLACE_TILE;
        this.pickedTile = randomTile();
        return this.pickedTile;
    }

    @Override
    public void putTile(Position pos) {
        if (this.state != State.PLACE_TILE) {
            throw new IllegalStateException("State is not PLACE_TILE");
        }
        if (this.canTileBePut(pos) && this.isInside(pos)) {
            this.boards[this.currentPlayerNumber].put(this.pickedTile, pos);
            this.state = State.TURN_END;
            if (this.boards[this.currentPlayerNumber].isFull()) {
                this.state = State.GAME_OVER;
            }
        } else {
            throw new IllegalArgumentException("the tile can't be put on that "
                    + "position (position outside of the "
                    + "board or position not allowed by the rules)");
        }
    }

    @Override
    public void nextPlayer() {
        if (this.state != State.TURN_END) {
            throw new IllegalStateException("State is not TURN_END");
        }
        this.state = State.PICK_TILE;
        this.currentPlayerNumber = (this.currentPlayerNumber + 1)
                % this.playerCount;
    }

    @Override
    public int getPlayerCount() {
        if (this.state == state.NOT_STARTED) {
            throw new IllegalStateException("State is NOT_STARTED");
        }
        return this.playerCount;
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public int getCurrentPlayerNumber() {
        if (this.state == state.NOT_STARTED
                || this.state == state.GAME_OVER) {
            throw new IllegalStateException("State is NOT_STARTED or GAME_OVER");
        }
        return this.currentPlayerNumber;
    }

    @Override
    public Tile getPickedTile() {
        if (this.state != state.PLACE_TILE) {
            throw new IllegalStateException("State is not PLACE_TILE");
        }
        return this.pickedTile;
    }

    @Override
    public boolean isInside(Position pos) {
        return this.boards[this.currentPlayerNumber].isInside(pos);
    }

    @Override
    public boolean canTileBePut(Position pos) {
        if (this.state != state.PLACE_TILE) {
            throw new IllegalStateException("State is not PLACE_TILE");
        } else if (!this.isInside(pos)) {
            throw new IllegalArgumentException("The position is outside the board");
        } else {
            return this.boards[this.currentPlayerNumber].canBePut(this.pickedTile, pos);
        }
    }

    @Override
    public Tile getTile(int playerNumber, Position pos) {
        if (this.state == state.NOT_STARTED) {
            throw new IllegalStateException("State is NOT_STARTED");
        } else if (!this.isInside(pos) || (playerNumber >= this.playerCount
                || playerNumber < 0)) {
            throw new IllegalArgumentException("the position is outside "
                    + " the board or the playerNumber is outside of range");
        }
        return this.boards[playerNumber].getTile(pos);
    }

    @Override
    public int getWinner() {
        if (this.state != State.GAME_OVER) {
            throw new IllegalStateException("State is not GAME_OVER");
        }
        return this.currentPlayerNumber;
    }

    /**
     * Pick a tile with the given value. Should be used only for the JUnit
     * tests.
     *
     * @param value
     * @return the picked tile
     */
    Tile pickTile(int value) {
        this.state = State.PLACE_TILE;
        this.pickedTile = new Tile(value);
        return this.pickedTile;
    }

    /**
     * Give a random Tile between 1 and 20.
     *
     * @return a tile
     */
    private Tile randomTile() {
        return new Tile((int) (Math.random() * ((20 - 1) + 1)) + 1);
    }

}
