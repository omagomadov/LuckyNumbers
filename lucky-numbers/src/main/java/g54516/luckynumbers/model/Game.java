package g54516.luckynumbers.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private Deck deck;

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
        this.deck = new Deck(playerCount);
        this.SetTilesOnDiagonal();
    }

    @Override
    public int getBoardSize() {
        return this.boards[0].getSize();
    }

    @Override
    public void putTile(Position pos) {
        if (this.state != State.PLACE_TILE
                && this.state != State.PLACE_OR_DROP_TILE) {
            throw new IllegalStateException("State is not PLACE_TILE "
                    + " or PLACE_OR_DROP_TILE");
        }
        // Verify if the position respect the rules
        if (this.canTileBePut(pos) && this.isInside(pos)) {
            // Verify if there is a tile at this position
            if (this.boards[this.currentPlayerNumber].getTile(pos) != null) {
                this.state = State.TURN_END;
                deck.putBack(this.boards[this.currentPlayerNumber].getTile(pos));
                this.boards[this.currentPlayerNumber].put(this.pickedTile, pos);
            } else {
                // Put a tile at this position because there is no tile
                this.state = State.TURN_END;
                this.boards[this.currentPlayerNumber].put(this.pickedTile, pos);
            }
        } else {
            throw new IllegalArgumentException("the tile can't be put on that "
                    + "position (position outside of the "
                    + "board or position not allowed by the rules)");
        }
        // Verify if the player completed his board
        if (this.boards[this.currentPlayerNumber].isFull()) {
            this.state = State.GAME_OVER;
        }
    }

    @Override
    public void nextPlayer() {
        if (this.state != State.TURN_END) {
            throw new IllegalStateException("State is not TURN_END");
        } else if (this.faceDownTileCount() == 0) {
            this.state = State.GAME_OVER;
        } else {
            this.state = State.PICK_TILE;
            this.currentPlayerNumber = (this.currentPlayerNumber + 1)
                    % this.playerCount;
        }
    }

    @Override
    public int getPlayerCount() {
        if (this.state == State.NOT_STARTED) {
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
        if (this.state == State.NOT_STARTED
                || this.state == State.GAME_OVER) {
            throw new IllegalStateException("State is NOT_STARTED or GAME_OVER");
        }
        return this.currentPlayerNumber;
    }

    @Override
    public Tile getPickedTile() {
        if (this.state != State.PLACE_TILE
                && this.state != State.PLACE_OR_DROP_TILE) {
            throw new IllegalStateException("State is not PLACE_TILE or"
                    + " PLACE_OR_DROp_TILE");
        }
        return this.pickedTile;
    }

    @Override
    public boolean isInside(Position pos) {
        return this.boards[this.currentPlayerNumber].isInside(pos);
    }

    @Override
    public boolean canTileBePut(Position pos) {
        if (this.state != State.PLACE_TILE
                && this.state != State.PLACE_OR_DROP_TILE) {
            throw new IllegalStateException("State is not PLACE_TILE "
                    + " or PLACE_OR_DROP_TILE");
        } else if (!this.isInside(pos)) {
            throw new IllegalArgumentException("The position is outside the board");
        } else {
            return this.boards[this.currentPlayerNumber].canBePut(this.pickedTile, pos);
        }
    }

    @Override
    public Tile getTile(int playerNumber, Position pos) {
        if (this.state == State.NOT_STARTED) {
            throw new IllegalStateException("State is NOT_STARTED");
        } else if (!this.isInside(pos) || (playerNumber >= this.playerCount
                || playerNumber < 0)) {
            throw new IllegalArgumentException("the position is outside "
                    + " the board or the playerNumber is outside of range");
        }
        return this.boards[playerNumber].getTile(pos);
    }

    @Override
    public List<Integer> getWinners() {
        int player = 0;
        int tiles = 0;
        List<Integer> winners = new ArrayList<>();
        if (this.state != State.GAME_OVER) {
            throw new IllegalStateException("State is not GAME_OVER");
        } else if (this.faceDownTileCount() == 0) {
            // for each players
            while (player < this.playerCount) {
                int numberOfTile = 0;
                // count the number of tiles on board of 'player'
                for (int lg = 0; lg < this.getBoardSize(); lg++) {
                    for (int col = 0; col < this.getBoardSize(); col++) {
                        if (this.boards[player].getTile(new Position(lg, col))
                                != null) {
                            numberOfTile++;
                        }
                    }
                }
                // if the number of tiles are equal to 'tiles'. Add the 'player'
                // on the list
                if (numberOfTile == tiles) {
                    winners.add(player);
                } // else the player have more tiles than 'tiles'. Clean the list
                // and add the new winner in the list
                else if (numberOfTile > tiles) {
                    winners.clear();
                    winners.add(player);
                    tiles = numberOfTile;
                }
                player++;
            }
        }
        return winners;
    }

    @Override
    public Tile pickFaceDownTile() {
        if (this.state != State.PICK_TILE) {
            throw new IllegalStateException("State is not PICK_TILE");
        }
        this.pickedTile = deck.pickFaceDown();
        this.state = State.PLACE_OR_DROP_TILE;
        return this.pickedTile;
    }

    @Override
    public void pickFaceUpTile(Tile tile) {
        if (this.state != State.PICK_TILE) {
            throw new IllegalStateException("State is not PICK_TILE");
        }
        if (deck.hasFaceUp(tile)) {
            this.state = State.PLACE_TILE;
            this.deck.pickFaceUp(tile);
            this.pickedTile = tile;
        } else {
            throw new IllegalArgumentException("Tile doesn't exist");
        }
    }

    @Override
    public void dropTile() {
        if (this.state != State.PLACE_OR_DROP_TILE) {
            throw new IllegalStateException("State is not PLACE_OR_DROP_TILE");
        }
        this.state = State.TURN_END;
        this.deck.putBack(this.pickedTile);
    }

    @Override
    public int faceDownTileCount() {
        if (this.state == State.NOT_STARTED) {
            throw new IllegalStateException("State is NOT_STARTED");
        }
        return this.deck.faceDownCount();
    }

    @Override
    public int faceUpTileCount() {
        if (this.state == State.NOT_STARTED) {
            throw new IllegalStateException("State is NOT_STARTED");
        }
        return this.deck.faceUpCount();
    }

    @Override
    public List<Tile> getAllfaceUpTiles() {
        if (this.state == State.NOT_STARTED) {
            throw new IllegalStateException("State is NOT_STARTED");
        }
        List<Tile> faceUpTiles;
        faceUpTiles = this.deck.getAllFaceUp();
        faceUpTiles = Collections.unmodifiableList(faceUpTiles);
        return faceUpTiles;
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
     * Gives the number of tiles on the board.
     *
     * @param player a player
     * @return the number of tiles
     */
    private int numberOfTiles(int player) {
        int count = 0;
        for (int row = 0; row < this.getBoardSize(); row++) {
            for (int column = 0; column < this.getBoardSize(); column++) {
                if (this.boards[player].getTile(new Position(row, column))
                        != null) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Puts tiles on the diagonal for each players.
     */
    private void SetTilesOnDiagonal() {
        int count = 0;
        int player = 0;
        int index = 0;

        // Picks 4*playerCount tiles and put it face up
        while (count < 4 * this.playerCount) {
            Tile tile = this.deck.pickFaceDown();
            this.deck.putBack(tile);
            count++;
        }

        // Sort face up tiles in order from smallest to largest
        Collections.sort(this.deck.getAllFaceUp(),
                Comparator.comparing(tiles -> tiles.getValue()));

        // Puts sorted face up tiles on diagonal for each players
        while (player < this.playerCount) {
            for (int i = 0; i < this.getBoardSize(); i++) {
                this.boards[player].put(this.deck.getAllFaceUp().get(index),
                        new Position(i, i));
                index++;
            }
            player++;
        }

        // Cleans the deck after putting tiles on the diagonal for each players
        this.deck.getAllFaceUp().clear();
    }

}
