package g54516.luckynumbers.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    /* =====================
         Tests for start()
       ===================== */

 /* --- test related to the state --- */
    @Test
    public void start_when_game_not_started_ok() {
        game.start(4);
    }

    @Test
    public void start_when_game_over_ok() {
        fullPlay();
        game.start(2);
    }

    /* Play a game till the end */
    private void fullPlay() {
        game.start(2);
        int value = 1;
        int line = 0;
        int col = 0;
        for (int turn = 1; turn < game.getBoardSize() * game.getBoardSize(); turn++) {
            for (int player = 0; player < game.getPlayerCount(); player++) {
                game.pickTile(value);
                game.putTile(new Position(line, col));
                game.nextPlayer();
            }
            value++;
            col++;
            if (col == game.getBoardSize()) {
                col = 0;
                line++;
            }
        }
        game.pickTile(20);
        game.putTile(new Position(line, col));
    }

    @Test
    public void start_when_game_in_progress_ISE() {
        game.start(4);
        assertThrows(IllegalStateException.class,
                () -> game.start(1));
    }

    @Test
    public void start_state_changed_to_PICK_TILE() {
        game.start(3);
        assertEquals(State.PICK_TILE, game.getState());
    }

    /* --- tests related to the parameter --- */
    @Test
    public void start_playerCount_too_small_Exception() {
        assertThrows(IllegalArgumentException.class,
                () -> game.start(1));
    }

    @Test
    public void start_playerCount_minimum_accepted() {
        game.start(2);
    }

    @Test
    public void start_playerCount_maximum_accepted() {
        game.start(4);
    }

    @Test
    public void start_playerCount_too_big_Exception() {
        assertThrows(IllegalArgumentException.class,
                () -> game.start(5));
    }

    /* -- tests related to fields initialization --- */
    @Test
    public void start_playerCount_initialized() {
        game.start(4);
        assertEquals(4, game.getPlayerCount());
    }

    @Test
    public void start_current_player_is_player_0() {
        game.start(4);
        assertEquals(0, game.getCurrentPlayerNumber());
    }

    /* === À vous de compléter... === */
 /* === Testing method canTileBePut() === */
    @Test
    public void can_tile_be_put_false() {
        game.start(2);
        game.pickTile(5);
        game.putTile(new Position(0, 1));
        game.pickTile(10);
        assertFalse(game.canTileBePut(new Position(0, 0)));
    }

    @Test
    public void can_tile_be_put_true() {
        game.start(2);
        game.pickTile(5);
        game.putTile(new Position(0, 0));
        game.pickTile(10);
        assertTrue(game.canTileBePut(new Position(0, 1)));
    }

    @Test
    public void can_tile_be_put_state_not_PLACE_TILE() {
        game.start(2);
        assertThrows(IllegalStateException.class,
                () -> game.canTileBePut(new Position(0, 0)));
    }

    /* === Testing method isInside() === */
    @Test
    public void position_is_inside_true() {
        game.start(2);
        assertTrue(game.isInside(new Position(0, 0)));
    }

    @Test
    public void position_is_inside_false() {
        game.start(2);
        assertFalse(game.isInside(new Position(-1, 3)));
    }

    @Test
    public void position_is_inside_false_out_of_bound_1() {
        game.start(2);
        assertFalse(game.isInside(new Position(1000, 1000)));
    }

    @Test
    public void position_is_inside_false_out_of_bound_2() {
        game.start(2);
        assertFalse(game.isInside(new Position(-5897, -625)));
    }

    @Test
    public void position_is_inside_false_out_of_bound_3() {
        game.start(2);
        assertFalse(game.isInside(new Position(-50, 2)));
    }

    @Test
    public void position_is_inside_false_out_of_bound_4() {
        game.start(2);
        assertFalse(game.isInside(new Position(1, -9)));
    }

    /* === Testing method getWinner() === */
    @Test
    public void player_is_winner() {
        fullPlay();
        assertEquals(0, game.getWinner());
    }

    @Test
    public void get_player_winner_state_not_GAME_OVER() {
        game.start(2);
        assertThrows(IllegalStateException.class,
                () -> game.getWinner());
    }

    /* === Testing method getPickedTile() === */
    @Test
    public void pick_tile_4_true() {
        game.start(2);
        game.pickTile(4);
        assertEquals(4, game.getPickedTile().getValue());
    }

    @Test
    public void pick_tile_5_true() {
        Tile tile;
        game.start(2);
        tile = game.pickTile(5);
        assertEquals(tile, game.getPickedTile());
    }

    @Test
    public void pick_tile_state_not_PLACE_TILE() {
        assertThrows(IllegalStateException.class,
                () -> game.pickTile());
    }

    /* === Testing method putTile and getTile() === */
    @Test
    public void get_tile_from_board_0() {
        game.start(2);
        game.pickTile(2);
        game.putTile(new Position(0, 0));
        assertEquals(2, game.getTile(0, new Position(0, 0)).getValue());
    }

    @Test
    public void get_tile_state_NOT_STARTED() {
        assertThrows(IllegalStateException.class,
                () -> game.getTile(0, new Position(0, 0)));
    }

    @Test
    public void put_tile_on_board_out_of_bound_negative_row() {
        game.start(2);
        game.pickTile();
        assertThrows(IllegalArgumentException.class,
                () -> game.putTile(new Position(-1, 0)));
    }

    @Test
    public void put_tile_on_board_out_of_bound_negative_column() {
        game.start(2);
        game.pickTile();
        assertThrows(IllegalArgumentException.class,
                () -> game.putTile(new Position(0, -1)));
    }

    @Test
    public void put_tile_on_board_out_of_bound_negative_both() {
        game.start(2);
        game.pickTile();
        assertThrows(IllegalArgumentException.class,
                () -> game.putTile(new Position(-1, -5)));
    }

    @Test
    public void put_tile_on_board_out_of_bound_positive_row() {
        game.start(2);
        game.pickTile();
        assertThrows(IllegalArgumentException.class,
                () -> game.putTile(new Position(5, 0)));
    }

    @Test
    public void put_tile_on_board_out_of_bound_positive_column() {
        game.start(2);
        game.pickTile();
        assertThrows(IllegalArgumentException.class,
                () -> game.putTile(new Position(0, 5)));
    }

    @Test
    public void put_tile_on_board_out_of_bound_positive_both() {
        game.start(2);
        game.pickTile();
        assertThrows(IllegalArgumentException.class,
                () -> game.putTile(new Position(5, 5)));
    }

    @Test
    public void put_tile_on_board_not_PLACE_TILE() {
        game.start(2);
        assertThrows(IllegalStateException.class,
                () -> game.putTile(new Position(0, 0)));
    }

    /* === Testing method nextPlayer() and getCurrentPlayerNumber() === */
    @Test
    public void next_player() {
        game.start(2);
        game.pickTile();
        game.putTile(new Position(0, 0));
        game.nextPlayer();
        assertEquals(1, game.getCurrentPlayerNumber());
    }

    @Test
    public void next_player_0() {
        game.start(2);
        // player 0
        game.pickTile();
        game.putTile(new Position(0, 0));
        // player 1
        game.nextPlayer();
        game.pickTile();
        game.putTile(new Position(0, 0));
        // player 0
        game.nextPlayer();
        assertEquals(0, game.getCurrentPlayerNumber());
    }

    @Test
    public void get_current_player_number_state_NOT_STARTED_GAME_OVER() {
        assertThrows(IllegalStateException.class,
                () -> game.getCurrentPlayerNumber());
    }

    @Test
    public void next_player_state_not_TURN_END() {
        game.start(2);
        assertThrows(IllegalStateException.class,
                () -> game.nextPlayer());
    }

    /* === Testing method getState() === */
    @Test
    public void get_state_PICK_TILE() {
        game.start(2);
        assertEquals(State.PICK_TILE, game.getState());
    }

    @Test
    public void get_state_PLACE_TILE() {
        game.start(2);
        game.pickTile();
        assertEquals(State.PLACE_TILE, game.getState());
    }

    @Test
    public void get_state_TURN_END() {
        game.start(2);
        game.pickTile();
        game.putTile(new Position(0, 0));
        assertEquals(State.TURN_END, game.getState());
    }

    @Test
    public void get_state_GAME_OVER() {
        fullPlay();
        assertEquals(State.GAME_OVER, game.getState());
    }

    /* === Testing method getPlayerCount() === */
    @Test
    public void get_player_count_state_not_NOT_STARTED() {
        assertThrows(IllegalStateException.class,
                () -> game.getPlayerCount());
    }

    @Test
    public void get_player_count_2_players() {
        game.start(2);
        assertEquals(2, game.getPlayerCount());
    }

}
