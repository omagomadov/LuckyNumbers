package g54516.luckynumbers.model;

/**
 * Represents the position in the game.
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public class Position {

    private int row;
    private int column;

    /**
     * Constructor who initialize attributes of the class Position.
     *
     * @param row a row
     * @param column a column
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Getter method of the class Position.
     *
     * @return the row of the Position
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter method of the class Position.
     *
     * @return the column of the Position
     */
    public int getColumn() {
        return column;
    }

}
