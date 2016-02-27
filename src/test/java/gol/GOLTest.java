package gol;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GOLTest {
    private static final Tick TICK = new Tick();

    private static final Grid BLINKER = TupleGrid.builder().at(new Coordinate(0, 0)).at(new Coordinate(0, 1)).at(new Coordinate(0, 2)).build();
    private static final Grid BLOCK = TupleGrid.builder().at(new Coordinate(0, 0)).at(new Coordinate(0, 1)).at(new Coordinate(1, 0)).at(new Coordinate(1, 1)).build();

    @Test
    public void given_a_blinker_should_blink_every_second_generation() throws Exception {
        assertEquals(TupleGrid.builder().at(new Coordinate(-1, 1)).at(new Coordinate(0, 1)).at(new Coordinate(1, 1)).build(), TICK.apply(BLINKER));
        assertEquals(BLINKER, TICK.apply(TICK.apply(BLINKER)));
    }

    @Test
    public void given_a_block_should_stay_still() throws Exception {
        assertEquals(BLOCK, TICK.apply(BLOCK));
    }
}