package g54516.luckynumbers.controller;

import g54516.luckynumbers.model.Model;
import g54516.luckynumbers.model.Position;
import g54516.luckynumbers.model.Tile;
import g54516.luckynumbers.view.View;
import java.util.Scanner;

/**
 * Controller of the game.
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public class Controller {

    private View view;
    private Model game;

    /**
     * Constructor who initialize the attribute model and view.
     *
     * @param model the model
     * @param view the view
     */
    public Controller(Model model, View view) {
        this.game = model;
        this.view = view;
    }

    /**
     * Method who start the game.
     */
    public void play() {
        // initialize variables
        Scanner kbd = new Scanner(System.in);
        String endGame = "";

        // display welcome message
        view.displayWelcome();

        while (true) {
            switch (game.getState()) {

                // If current state is 'NOT_STARTED'
                case NOT_STARTED:
                    // Asks number of player
                    int playerCount = view.askPlayerCount();
                    // Start the game
                    game.start(playerCount);
                    break;

                // If current state is 'PICK_TILE'
                case PICK_TILE:
                    this.pickUpOrDown();
                    break;

                // If the state is 'PLACE_OR_DROP_TILE'
                case PLACE_OR_DROP_TILE:
                    this.dropOrPut();
                    break;

                // If the current state is 'PLACE_TILE'
                // (if the player picks face up tile)
                case PLACE_TILE:
                    this.putTile();
                    break;

                // If the current state is 'TURN_END'
                case TURN_END:
                    // The next player is the current player
                    game.nextPlayer();
                    break;

                // If the current state is 'GAME_OVER'
                case GAME_OVER:
                    // Displays the winners or the winner
                    view.displayWinner();
                    // Asks player if he want to retry or quit the game
                    endGame = view.askQuitOrRetry();
                    // If player answer 'r' it mean that he want to restart the
                    // game
                    if (endGame.contains("r")) {
                        playerCount = view.askPlayerCount();
                        game.start(playerCount);
                    }
                    break;
            }
            // If the player answer 'q' it mean that he want to quit the game
            if (endGame.contains("q")) {
                // Displays end message
                view.displayEnd();
                // the 'break' is to stop/break/exit the while condition
                break;
            }
        }
    }

    /**
     * Asks player if he want to pick face up or face down tile.
     */
    private void pickUpOrDown() {
        Scanner kbd = new Scanner(System.in);

        // Displays the deck
        view.displayDeck();
        // If there is face up tiles
        if (game.faceUpTileCount() != 0) {
            // Asks player which action he want do :
            // Pick face down or pick face up
            String choice = view.askAction();
            // Displays the deck
            view.displayDeck();
            // If player answer 'u' it mean that he want
            // pick face up tile
            if (choice.contains("u")) {
                game.pickFaceUpTile(new Tile(view.askWhichFaceUpTile()));
                // Else he pick face down tile
            } else {
                game.pickFaceDownTile();
            }
            // Else there is not face up tile the player pick
            // automatically face down tile
        } else {
            game.pickFaceDownTile();
        }
    }

    /**
     * Asks player if he want to drop or put the tile.
     */
    private void dropOrPut() {
        Scanner kbd = new Scanner(System.in);
        Position pos;

        // Displays the board
        view.displayGame();
        // Asks player if he want put his tile or drop it
        String dropOrPut = view.askDropOrPut();
        // if the player answer 'd' it mean that he want drop 
        // the tile
        if (dropOrPut.contains("d")) {
            game.dropTile();
            // Else he must put the tile on his board
        } else {
            // Asks player in which position he want to put his
            // tile
            pos = view.askPosition();
            // If the given position by the player is wrong
            // (don't respect rules or outside the board)
            // display an error message
            if (!game.canTileBePut(pos)) {
                view.displayError("Cannot put a tile on that position\n");
                // Else put the tile on the given position
            } else {
                game.putTile(pos);
            }
        }
    }

    /**
     * Asks player the position where he want to put a tile.
     */
    private void putTile() {
        Scanner kbd = new Scanner(System.in);
        Position pos;

        // Displays the board
        view.displayGame();
        // Asks player in which position he want put his tile
        pos = view.askPosition();
        // If the given position by the player is wrong
        // (don't respect rules or outside the board)
        // display an error message
        if (!game.canTileBePut(pos)) {
            view.displayError("Cannot put a tile on that position\n");
        } else {
            // Else put the tile on the given position
            game.putTile(pos);
        }
    }

}
