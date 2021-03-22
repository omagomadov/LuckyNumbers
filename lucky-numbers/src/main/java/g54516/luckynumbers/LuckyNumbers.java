package g54516.luckynumbers;

import g54516.luckynumbers.controller.Controller;
import g54516.luckynumbers.model.Game;
import g54516.luckynumbers.model.Model;
import g54516.luckynumbers.view.MyView;

/**
 * Main class to execute the game.
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public class LuckyNumbers {

    public static void main(String[] args) {
        Model game = new Game();
        Controller controller = new Controller(game, new MyView(game));
        controller.play();
    }
}
