package g54516.luckynumbers.view;

import g54516.luckynumbers.model.Position;

/**
 * Interface for the MyView class.
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public interface View {

    /**
     * Displays a welcome message.
     */
    public void displayWelcome();

    /**
     * Displays the game.
     * <ul>
     * <li>Displays current player</li>
     * <li>Displays the board of the current player</li>
     * <li>Displays the tile of the current player</li>
     * </ul>
     */
    public void displayGame();

    /**
     * Displays the number of the winner.
     */
    public void displayWinner();

    /**
     * Asks how many players players are participating in the game.
     *
     * @return number of players
     */
    public int askPlayerCount();

    /**
     * Asks user row number and column number.
     *
     * @return a position
     */
    public Position askPosition();

    /**
     * Displays a error message given in paramater.
     *
     * @param message the error message
     */
    public void displayError(String message);

    /**
     * Asks which action the player want to do.
     * <ul>
     * <li>Asks if the player want pick a face down tile</li>
     * <li>Asks if the player want to pick a face up tile</li>
     * </ul>
     *
     * @return the action of the player
     */
    public String askAction();

    /**
     * Asks player which face up tile he want to pick.
     *
     * @return the value of the tile
     */
    public int askWhichFaceUpTile();

    /**
     * Displays the deck.
     * <ul>
     * <li>Displays remaining face down tiles</li>
     * <li>Displays face up tiles</li>
     * </ul>
     */
    public void displayDeck();

}
