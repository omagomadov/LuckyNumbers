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
        Scanner kbd = new Scanner(System.in);
        view.displayWelcome();
        String endGame = "";
        Position pos;
        while (true) {
            switch (game.getState()) {
                case NOT_STARTED:
                    int playerCount = view.askPlayerCount();
                    game.start(playerCount);
                    break;
                case PICK_TILE:
                    view.displayDeck();
                    if (game.faceUpTileCount() != 0) {
                        String choice = view.askAction();
                        view.displayDeck();
                        if (choice.contains("u")) {
                            game.pickFaceUpTile(new Tile(view.askWhichFaceUpTile()));
                        } else {
                            game.pickFaceDownTile();
                        }
                    } else {
                        game.pickFaceDownTile();
                    }
                    break;
                case PLACE_OR_DROP_TILE:
                    view.displayGame();
                    String dropOrPut = view.askDropOrPut();
                    if (dropOrPut.contains("d")) {
                        game.dropTile();
                    } else {
                        pos = view.askPosition();
                        if (!game.canTileBePut(pos)) {
                            view.displayError("Cannot put a tile on that position\n");
                        } else {
                            game.putTile(pos);
                        }
                    }
                    break;
                case PLACE_TILE:
                    view.displayGame();
                    pos = view.askPosition();
                    if (!game.canTileBePut(pos)) {
                        view.displayError("Cannot put a tile on that position\n");
                    } else {
                        game.putTile(pos);
                    }
                    break;
                case TURN_END:
                    game.nextPlayer();
                    break;
                case GAME_OVER:
                    view.displayWinner();
                    System.out.println("Do you want (r)estart or (q)uit the game?");
                    endGame = kbd.next();
                    if (endGame.contains("r")) {
                        playerCount = view.askPlayerCount();
                        game.start(playerCount);
                    }
                    break;
            }
            if (endGame.contains("q")) {
                System.out.println("Goodbye ! :-) ");
                break;
            }
        }
    }

}
