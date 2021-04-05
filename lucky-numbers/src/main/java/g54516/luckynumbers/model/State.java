package g54516.luckynumbers.model;

/**
 * Represents the state of the game.
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public enum State {
    /**
     * Represents that the game is not started.
     */
    NOT_STARTED,
    /**
     * Represents that the player must pick a tile.
     */
    PICK_TILE,
    /**
     * Represents that the player must place a tile.
     */
    PLACE_TILE,
    /**
     * Represents that the player finished his action and next player is
     * selected.
     */
    TURN_END,
    /**
     * Represents that a player has completed his board.
     */
    GAME_OVER,
    /**
     * Represents that a player have the choice to place or drop a tile.
     */
    PLACE_OR_DROP_TILE;
}
