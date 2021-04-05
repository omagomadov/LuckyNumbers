package g54516.luckynumbers.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public class DeckTest {

    private Deck deck;

    /* Create a deck with 2 players */
    @BeforeEach
    public void setUp() {
        deck = new Deck(2);
    }

    /* === Testing method faceDownCount() === */
    @Test
    public void face_down_count_default() {
        assertEquals(40, deck.faceDownCount());
    }

    @Test
    public void face_down_count_3_players() {
        Deck deck = new Deck(3);
        assertEquals(60, deck.faceDownCount());
    }

    @Test
    public void face_down_count_4_players() {
        Deck deck = new Deck(4);
        assertEquals(80, deck.faceDownCount());
    }

    /* === Testing method faceUpCount() === */
    @Test
    public void face_up_count_default() {
        assertEquals(0, deck.faceUpCount());
    }

    @Test
    public void face_up_count_1_tile() {
        deck.putBack(new Tile(1));
        assertEquals(1, deck.faceUpCount());
    }

    @Test
    public void face_up_count_4_tiles() {
        deck.putBack(new Tile(1));
        deck.putBack(new Tile(2));
        deck.putBack(new Tile(3));
        deck.putBack(new Tile(4));
        assertEquals(4, deck.faceUpCount());
    }

    /* === Testing method getAllFaceUp() === */
    @Test
    public void get_all_face_up_default_empty() {
        List<Tile> list = new ArrayList<>();
        assertEquals(list, deck.getAllFaceUp());
    }

    @Test
    public void get_all_face_up_5_tiles() {
        List<Tile> list = new ArrayList<>();
        list.add(new Tile(1));
        list.add(new Tile(2));
        list.add(new Tile(3));
        list.add(new Tile(4));
        list.add(new Tile(5));

        deck.putBack(new Tile(1));
        deck.putBack(new Tile(2));
        deck.putBack(new Tile(3));
        deck.putBack(new Tile(4));
        deck.putBack(new Tile(5));
        assertEquals(list, deck.getAllFaceUp());
    }

    @Test
    public void get_all_face_up_2_tiles() {
        List<Tile> list = new ArrayList<>();
        list.add(new Tile(1));
        list.add(new Tile(2));

        deck.putBack(new Tile(1));
        deck.putBack(new Tile(2));
        assertEquals(list, deck.getAllFaceUp());
    }

    /* === Testing method hasFaceUp() === */
    @Test
    public void has_face_up_default_true() {
        Tile tile = new Tile(1);
        deck.putBack(tile);
        assertTrue(deck.hasFaceUp(tile));
    }

    @Test
    public void has_face_up_false() {
        Tile tile = new Tile(1);
        deck.putBack(tile);
        assertFalse(deck.hasFaceUp(new Tile(2)));
    }

    /* === Testing method pickFaceUp() === */
    @Test
    public void pick_face_up_false_has_taken() {
        Tile tile = new Tile(1);
        // puts a tile in the deck
        deck.putBack(tile);
        // picks it back from the deck
        deck.pickFaceUp(tile);
        assertFalse(deck.hasFaceUp(tile));
    }

    /* === Testing method putBack() === */
    @Test
    public void put_back_tile_true() {
        deck.putBack(new Tile(1));
        assertTrue(deck.hasFaceUp(new Tile(1)));
    }

    @Test
    public void put_back_tile_false() {
        deck.putBack(new Tile(2));
        assertFalse(deck.hasFaceUp(new Tile(1)));
    }
}
