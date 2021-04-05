package g54516.luckynumbers.model;

/**
 * Represents the tile in the game.
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public class Tile {

    private int value;
    private boolean faceUp;

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

    /**
     * Getter method of the class Tile.
     *
     * @return the face of the tile
     */
    public boolean isFaceUp() {
        return faceUp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.value;
        hash = 29 * hash + (this.faceUp ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tile other = (Tile) obj;
        if (this.value != other.value) {
            return false;
        }
        if (this.faceUp != other.faceUp) {
            return false;
        }
        return true;
    }

    /**
     * Turn the attribute faceUp to true.
     */
    void flipFaceUp() {
        this.faceUp = true;
    }

}
