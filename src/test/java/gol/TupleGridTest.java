package gol;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import za.co.no9.pbt.AbstractGenerator;
import za.co.no9.pbt.BooleanGenerator;
import za.co.no9.pbt.Generator;
import za.co.no9.pbt.IntegerGenerator;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static gol.CellState.ALIVE;
import static gol.CellState.DEAD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static za.co.no9.pbt.Gen.forAll;

@RunWith(Parameterized.class)
public class TupleGridTest {
    private final Generator<Coordinate> COORDINATES = new AbstractGenerator<Coordinate>() {
        private Generator<Integer> INTEGERS = IntegerGenerator.from();

        public Coordinate next() {
            return new Coordinate(INTEGERS.next(), INTEGERS.next());
        }
    };

    private static final Supplier<GridBuilder> RECURSIVE_GRID_BUILDER_SUPPLIER = RecursiveGrid::builder;
    private static final Supplier<GridBuilder> TUPLE_GRID_BUILDER_SUPPLIER = TupleGrid::builder;

    private final Supplier<GridBuilder> builder;

    @Parameterized.Parameters
    public static Collection builders() {
        return Arrays.asList(new Object[][]{
                {RECURSIVE_GRID_BUILDER_SUPPLIER},
                {TUPLE_GRID_BUILDER_SUPPLIER}
        });
    }

    public TupleGridTest(Supplier<GridBuilder> builder) {
        this.builder = builder;
    }

    @Test
    public void given_a_blank_grid_every_coordinate_should_return_the_default_value() throws Exception {
        forAll(COORDINATES, c -> assertEquals(DEAD, builder.get().build().cellState(c)));
    }

    @Test
    public void given_a_blank_grid_every_set_coordinate_should_return_its_set_value() throws Exception {
        forAll(COORDINATES, c -> {
            final Grid grid = builder.get().at(c).build();
            assertEquals(ALIVE, grid.cellState(c));
        });
    }

    @Test
    public void give_a_cell_with_neighbours_should_accurately_calculate_the_number_of_neighbours() throws Exception {
        final Generator<List<Boolean>> listGenerator = BooleanGenerator.from().list(8, 8);
        forAll(COORDINATES, c -> {
            final List<Boolean> booleans = listGenerator.next();

            final Grid grid = builder.get()
                    .at(c.add(-1, -1), CellState.from(booleans.get(0)))
                    .at(c.add(0, -1), CellState.from(booleans.get(1)))
                    .at(c.add(1, -1), CellState.from(booleans.get(2)))
                    .at(c.add(-1, 0), CellState.from(booleans.get(3)))
                    .at(c.add(1, 0), CellState.from(booleans.get(4)))
                    .at(c.add(-1, 1), CellState.from(booleans.get(5)))
                    .at(c.add(0, 1), CellState.from(booleans.get(6)))
                    .at(c.add(1, 1), CellState.from(booleans.get(7)))
                    .build();

            final long liveCount = booleans.stream().filter(cell -> cell).count();

            assertEquals(liveCount, grid.numberOfAliveNeighbours(c));
        });
    }

    @Test
    public void given_a_blank_grid_no_relevant_cells() throws Exception {
        final Grid newGrid = builder.get().build().forEachRelevantCell(c -> ALIVE);

        assertTrue(newGrid.isEmpty());
    }

    @Test
    public void given_a_grid_with_a_point_then_the_relevant_cells_are_the_neighbouring_cells() throws Exception {
        forAll(COORDINATES, c -> {
            Grid grid = builder.get().at(c).build();

            final Grid newGrid = grid.forEachRelevantCell(cell -> ALIVE);

            assertEquals(8, newGrid.numberOfAliveNeighbours(c));
            assertEquals(ALIVE, newGrid.cellState(c));
        });
    }
}