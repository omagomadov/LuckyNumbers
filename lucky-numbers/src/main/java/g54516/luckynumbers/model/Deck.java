package g54516.luckynumbers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the deck of the game.
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public class Deck {

    private List<Tile> faceUpTiles;
    private List<Tile> faceDownTiles;

    /**
     * Constructor who initialize attributes of the class Deck.
     *
     * @param playerCount number of players
     */
    public Deck(int playerCount) {
        this.faceUpTiles = new ArrayList<>();
        this.faceDownTiles = new ArrayList<>();

        for (int i = 0; i < playerCount; i++) {
            for (int value = 1; value <= 20; value++) {
                this.faceDownTiles.add(new Tile(value));
            }
        }
    }

    /**
     * Gives the number of hidden tiles.
     *
     * @return the number of hidden tiles
     */
    public int faceDownCount() {
        return this.faceDownTiles.size();
    }

    /**
     * Pick a hidden tile randomly.
     *
     * @return a hidden tile
     */
    public Tile pickFaceDown() {
        int random = randomTile();
        Tile tile = this.faceDownTiles.get(random);
        this.faceDownTiles.remove(random);
        return tile;
    }

    /**
     * Gives the number of visible tiles.
     *
     * @return the number of visible tiles
     */
    public int faceUpCount() {
        return this.faceUpTiles.size();
    }

    /**
     * Gives the list of visible tiles.
     *
     * @return the list of visible tiles
     */
    public List<Tile> getAllFaceUp() {
        return this.faceUpTiles;
    }

    /**
     * Checks if the given tile in parameter exist and is visible.
     *
     * @param tile a tile
     * @return true if the tile exist and is visible else false
     */
    public boolean hasFaceUp(Tile tile) {
        return this.faceUpTiles.contains(tile);
    }

    /**
     * Picks the given tile in parameter from the deck.
     *
     * @param tile a tile
     */
    public void pickFaceUp(Tile tile) {
        this.faceUpTiles.remove(tile);
    }

    /**
     * Puts back the given tile in parameter in the deck.
     *
     * @param tile a tile
     */
    public void putBack(Tile tile) {
        this.faceUpTiles.add(tile);
    }

    /**
     * Gives a random number between the numbers of face down tiles and 0.
     *
     * @return a random number
     */
    private int randomTile() {
        return (int) (Math.random() * ((this.faceDownCount() - 1) - 0) + 1) + 0;
    }

    /**
     * Gives the list of face down tiles from the deck.
     *
     * @return list of face down tiles
     */
    List<Tile> getAllFaceDown() {
        return this.faceDownTiles;
    }

}
