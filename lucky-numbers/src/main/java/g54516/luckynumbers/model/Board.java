package g54516.luckynumbers.model;

/**
 * Represents the board of the game.
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public class Board {

    private Tile[][] tiles;

    /**
     * Constructor who initialize a 2 dimensional array 4*4 of Tile.
     */
    public Board() {
        this.tiles = new Tile[4][4];
    }

    /**
     * Give the size of the board.
     *
     * @return the size of the board
     */
    public int getSize() {
        return this.tiles.length;
    }

    /**
     * Checks if the position given in parameter is on the board.
     *
     * @param pos a position
     * @return true if the position is on the board
     */
    public boolean isInside(Position pos) {
        return (pos.getRow() >= 0 && pos.getRow() <= this.getSize() - 1)
                && (pos.getColumn() >= 0 && pos.getColumn() <= this.getSize() - 1);
    }

    /**
     * Gives the tile at the position given in parameter.
     *
     * @param pos a position
     * @return the tile at the position on the board
     */
    public Tile getTile(Position pos) {
        return this.tiles[pos.getRow()][pos.getColumn()];
    }

    /**
     * Checks if the tile can be put at the position on the board.
     *
     * @param tile a tile
     * @param pos a position
     * @return true if the tile can be put at the position on the board
     */
    public boolean canBePut(Tile tile, Position pos) {
        return tileCanBePut(tile, pos);
    }

    /**
     * Put the tile at the position on the board.
     *
     * @param tile a tile
     * @param pos a position
     */
    public void put(Tile tile, Position pos) {
        this.tiles[pos.getRow()][pos.getColumn()] = tile;
    }

    /**
     * Checks if the board of tiles is full.
     *
     * @return true if the board of tiles is full
     */
    public boolean isFull() {
        for (Tile[] row : this.tiles) {
            for (Tile column : row) {
                if (column == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the tile is the lowest at given position on the board.
     *
     * @param tile a tile
     * @param pos a position
     * @return true if the tile is lower than those on the right and down and
     * higher than those on the left and up on the board
     */
    private boolean tileCanBePut(Tile tile, Position pos) {
        if (isTileLowerDown(tile, pos) && isTileLowerRight(tile, pos)
                && isTileHigherUp(tile, pos) && isTileHigherLeft(tile, pos)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Loop and check if the tile is the lowest than those on down.
     *
     * @param tile a tile
     * @param pos a position
     * @return true if the tile is the lowest than those on down
     */
    private boolean isTileLowerDown(Tile tile, Position pos) {
        int row = pos.getRow();
        int column = pos.getColumn();

        while (row < this.getSize() - 1) {
            row++;
            if (this.tiles[row][column] != null) {
                if (tile.getValue() >= this.tiles[row][column].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Loop and checks if the tile is the lowest than those on right.
     *
     * @param tile a tile
     * @param pos a position
     * @return true if the tile is the lowest than those on right
     */
    private boolean isTileLowerRight(Tile tile, Position pos) {
        int row = pos.getRow();
        int column = pos.getColumn();

        while (column < this.getSize() - 1) {
            column++;
            if (this.tiles[row][column] != null) {
                if (tile.getValue() >= this.tiles[row][column].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Loop and checks if the tile is the highest than those on up.
     *
     * @param tile a tile
     * @param pos a position
     * @return true if the tile is the highest than those on up
     */
    private boolean isTileHigherUp(Tile tile, Position pos) {
        int row = pos.getRow();
        int column = pos.getColumn();

        while (row > 0) {
            row--;
            if (this.tiles[row][column] != null) {
                if (tile.getValue() <= this.tiles[row][column].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Loop and checks if the tile is the highest than those on left.
     *
     * @param tile a tile
     * @param pos a position
     * @return true if the tile is the highest than those on left
     */
    private boolean isTileHigherLeft(Tile tile, Position pos) {
        int row = pos.getRow();
        int column = pos.getColumn();

        while (column > 0) {
            column--;
            if (this.tiles[row][column] != null) {
                if (tile.getValue() <= this.tiles[row][column].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }
}
