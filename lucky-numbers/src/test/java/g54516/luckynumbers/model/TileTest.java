package g54516.luckynumbers.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Oumar Magomadov <54516@etu.he2b.be>
 */
public class TileTest {

    @Test
    public void tile_is_not_visible() {
        Tile tile = new Tile(1);
        assertEquals(false, tile.isFaceUp());
    }

    @Test
    public void tile_is_visible() {
        Tile tile = new Tile(1);
        tile.flipFaceUp();
        assertEquals(true, tile.isFaceUp());
    }

}
