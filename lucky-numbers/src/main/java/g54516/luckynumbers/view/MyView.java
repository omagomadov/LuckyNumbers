package g54516.luckynumbers.view;

import g54516.luckynumbers.model.Model;
import g54516.luckynumbers.model.Position;
import java.util.Scanner;

/**
 * Class MyView for implements the View.
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public class MyView implements View {

    private Model model;

    /**
     * Constructor who initialize the attribute model.
     *
     * @param model the model
     */
    public MyView(Model model) {
        this.model = model;
    }

    @Override
    public void displayWelcome() {
        System.out.println("##########################");
        System.out.println("#     Coded by Oumar     #");
        System.out.println("#           V1.0         #");
        System.out.println("##########################\n");
        System.out.println("Welcome in Lucky Numbers");
        System.out.println("========================\n");
    }

    @Override
    public void displayGame() {
        System.out.println("Current player : " + model.getCurrentPlayerNumber());
        this.displayBoard();
        System.out.println("Chosen tile : " + model.getPickedTile().getValue()
                + "\n");

    }

    @Override
    public void displayWinner() {
        System.out.println("The winner is : " + model.getWinner());
    }

    @Override
    public int askPlayerCount() {
        Scanner kbd = new Scanner(System.in);
        int numberOfPlayers;
        System.out.println("How many players participate in the game : ");
        numberOfPlayers = kbd.nextInt();
        while (numberOfPlayers < 2 || numberOfPlayers > 4) {
            System.out.println("the number of players is not between 2 and 4 "
                    + "(both included) !\n");
            System.out.println("How many players participate in the game : ");
            numberOfPlayers = kbd.nextInt();
        }

        return numberOfPlayers;
    }

    @Override
    public Position askPosition() {
        Scanner kbd = new Scanner(System.in);
        int row, column;
        Position position;

        System.out.println("Row : ");
        row = kbd.nextInt();
        System.out.println("Column : ");
        column = kbd.nextInt();
        while (!model.isInside(new Position(row, column))
                || !model.canTileBePut(new Position(row, column))) {
            System.out.println("Incorrect position or position"
                    + " not allowed by the rule");
            System.out.println("please retry !");
            System.out.println("Row : ");
            row = kbd.nextInt();
            System.out.println("Column : ");
            column = kbd.nextInt();
        }
        System.out.println("");

        position = new Position(row, column);
        return position;
    }

    @Override
    public void displayError(String message) {
        System.out.println("Error : " + message);
    }

    /**
     * Displays the board of each players.
     */
    private void displayBoard() {
        for (int row = 0; row < model.getBoardSize(); row++) {
            for (int column = 0; column < model.getBoardSize(); column++) {
                if (model.getTile(model.getCurrentPlayerNumber(), new Position(row, column))
                        == null) {
                    System.out.printf("%4s", "." + " ");
                } else {
                    System.out.printf("%4s", model.getTile(model.getCurrentPlayerNumber(),
                            new Position(row, column)).getValue());
                }
            }
            System.out.println("");
        }
    }

}
