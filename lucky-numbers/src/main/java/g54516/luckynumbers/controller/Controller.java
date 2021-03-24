package g54516.luckynumbers.controller;

import g54516.luckynumbers.model.Model;
import g54516.luckynumbers.model.Position;
import g54516.luckynumbers.view.View;

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
        view.displayWelcome();
        while (true) {
            switch (game.getState()) {
                case NOT_STARTED:
                    int playerCount = view.askPlayerCount();
                    game.start(playerCount);
                    break;
                case PICK_TILE:
                    game.pickTile();
                    break;
                case PLACE_TILE:
                    view.displayGame();
                    Position pos = view.askPosition();
                    game.putTile(pos);
                    break;
                case TURN_END:
                    game.nextPlayer();
                    break;
                case GAME_OVER:
                    view.displayWinner();
                    playerCount = view.askPlayerCount();
                    game.start(playerCount);
                    break;
            }
        }
    }

}
