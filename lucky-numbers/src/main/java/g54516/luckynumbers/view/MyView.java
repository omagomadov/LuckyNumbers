package g54516.luckynumbers.view;

import g54516.luckynumbers.model.Model;
import g54516.luckynumbers.model.Position;
import g54516.luckynumbers.model.Tile;
import java.util.List;
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
    public void displayEnd() {
        System.out.println("Goodbye ! :-) ");
    }

    @Override
    public void displayGame() {
        System.out.println("Current player : "
                + (model.getCurrentPlayerNumber() + 1));
        this.displayBoard();
        System.out.println("Chosen tile : " + model.getPickedTile().getValue()
                + "\n");

    }

    @Override
    public void displayWinner() {
        List<Integer> winners = model.getWinners();
        System.out.println("#########################");
        System.out.println("The winner(s) are/is : ");
        for (int player = 0; player < winners.size(); player++) {
            System.out.println("Player : "
                    + (winners.get(player) + 1));
        }
        System.out.println("#########################");
    }

    @Override
    public int askPlayerCount() {
        Scanner kbd = new Scanner(System.in);
        int numberOfPlayers;
        System.out.println("How many players participate in the game : ");
        numberOfPlayers = kbd.nextInt();
        while (numberOfPlayers < 2 || numberOfPlayers > 4) {
            this.displayError("the number of players is not between 2 and 4 "
                    + "(both included)\n");
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

        // Asks player a row and column
        System.out.println("Row : ");
        row = kbd.nextInt() - 1;
        System.out.println("Column : ");
        column = kbd.nextInt() - 1;

        // Asks player row and column while is not correct
        while (!model.isInside(new Position(row, column))) {
            System.out.println("");
            this.displayError("Position outside the board\n");
            System.out.println("Row : ");
            row = kbd.nextInt() - 1;
            System.out.println("Column : ");
            column = kbd.nextInt() - 1;
        }
        System.out.println("");

        // Create a Position with the given row and column
        position = new Position(row, column);
        return position;
    }

    @Override
    public void displayError(String message) {
        System.out.println("Error : " + message);
    }

    @Override
    public String askAction() {
        Scanner kbd = new Scanner(System.in);
        String choice = "";

        System.out.println("Current player : "
                + (model.getCurrentPlayerNumber() + 1));
        // Asks player which action he want to do
        System.out.println("Which action do you want to do : "
                + "pick face (d)own tile or pick face (u)p tile ?");
        choice = kbd.nextLine();

        // Asks while the action is wrong or no tiles on the deck
        while (!(choice.contains("d")) && (!(choice.contains("u"))
                || model.faceUpTileCount() == 0)) {
            this.displayError("Wrong action or no face up tiles on the deck");
            System.out.println("Which action do you want to do : "
                    + "pick face (d)own tile or pick face (u)p tile ?");
            choice = kbd.nextLine();
        }
        System.out.println("");

        return choice;
    }

    @Override
    public int askWhichFaceUpTile() {
        Scanner kbd = new Scanner(System.in);
        int choice;

        // Asks player which face up tile he want to pick
        System.out.println("Which face up tile do you want to pick ?");
        choice = kbd.nextInt();

        // Asks player while this tile doesn't exist
        while (!(model.getAllfaceUpTiles().contains(new Tile(choice)))) {
            this.displayError("Tile doesn't exist !");
            System.out.println("Which face up tile do you want to pick ?");
            choice = kbd.nextInt();
        }
        System.out.println("");

        return choice;
    }

    @Override
    public String askQuitOrRetry() {
        Scanner kbd = new Scanner(System.in);
        String choice;

        System.out.println("Do you want (r)estart or (q)uit the game?");
        choice = kbd.next();
        while (!choice.contains("r") && !choice.contains("q")) {
            System.out.println("Wrong action");
            System.out.println("Do you want (r)estart or (q)uit the game?");
            choice = kbd.next();
        }

        return choice;
    }

    @Override
    public void displayDeck() {
        System.out.println("####################################");
        // Displays remaining face down tiles
        System.out.println("Remaining face down tiles : "
                + model.faceDownTileCount());

        // Displays face up tiles on the deck or "no tiles" message
        System.out.print("Current face up tiles on the deck : ");
        // If no face up tiles just displays a message 'no tiles'
        if (model.getAllfaceUpTiles().isEmpty()) {
            System.out.print("(no tiles)\n");
        } else {
            for (Tile tile : model.getAllfaceUpTiles()) {
                System.out.print(tile.getValue() + " ");
            }
            System.out.println("");
        }
        System.out.println("####################################");
        System.out.println("");
    }

    @Override
    public String askDropOrPut() {
        Scanner kbd = new Scanner(System.in);
        String choice = "";

        // Asks player if he want to drop or put a tile
        System.out.println("Which action do you want to do : "
                + "(d)rop the tile or (p)ut the tile ?");
        choice = kbd.nextLine();

        // Asks player while the action is wrong
        while (!(choice.contains("d")) && !(choice.contains("p"))) {
            this.displayError("Wrong action !");
            System.out.println("Which action do you want to do : "
                    + "(d)rop the tile or (p)ut the tile ?");
            choice = kbd.nextLine();
        }
        System.out.println("");

        return choice;
    }

    /**
     * Displays the board of each players.
     */
    private void displayBoard() {
        int count = 1;

        // Displays the horizontal bar on the top of the board
        displayHorizontal_barTop();

        // Displays the board and the vertical bar
        for (int row = 0; row < model.getBoardSize(); row++) {
            System.out.print(count + "|");
            for (int column = 0; column < model.getBoardSize(); column++) {
                if (model.getTile(model.getCurrentPlayerNumber(), new Position(row, column))
                        == null) {
                    System.out.printf("%4s", ".");
                } else {
                    System.out.printf("%4s", model.getTile(model.getCurrentPlayerNumber(),
                            new Position(row, column)).getValue());
                }
            }
            count++;
            System.out.println("");
        }

        // Displays the horizontal bar on the down of the board
        displayHorizontal_barDown();
    }

    /**
     * Display horizontal bar on the top of the board.
     */
    private void displayHorizontal_barTop() {
        System.out.print("  ");
        for (int i = 1; i <= model.getBoardSize(); i++) {
            System.out.printf("%4s", i);
        }
        System.out.println("");
        for (int i = 0; i < model.getBoardSize() * 4 + 4; i++) {
            System.out.print("=");
        }
        System.out.println("");
    }

    /**
     * Display horizontal bar on the down of the board.
     */
    private void displayHorizontal_barDown() {
        for (int i = 0; i < model.getBoardSize() * 4 + 4; i++) {
            System.out.print("=");
        }
        System.out.println("");
    }

}
