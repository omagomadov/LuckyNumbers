package g54516.luckynumbers.model;

/**
 * Represents the tile in the game.
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public class Tile {

    private int value;

    /**
     * Constructor who initialize attributes of the class Tile.
     *
     * @param value a value of the tile
     */
    public Tile(int value) {
        this.value = value;
    }

    /**
     * Getter method of the class Tile.
     *
     * @return the value of the tile
     */
    public int getValue() {
        return value;
    }

}
