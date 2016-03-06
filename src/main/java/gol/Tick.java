package gol;

import java.util.function.Function;

import static gol.CellState.ALIVE;
import static gol.CellState.DEAD;

public class Tick implements Function<Grid, Grid> {
    @Override
    public Grid apply(Grid generation) {
        return generation.forEachRelevantCell(c -> tickCell(generation, c));
    }

    private CellState tickCell(Grid generation, Coordinate coordinate) {
        final int numberOfAliveNeighbours = generation.numberOfAliveNeighbours(coordinate);
        switch (generation.at(coordinate)) {
            case ALIVE:
                if (numberOfAliveNeighbours < 2) {
                    return DEAD;
                } else if (numberOfAliveNeighbours == 2 || numberOfAliveNeighbours == 3) {
                    return ALIVE;
                } else {
                    return DEAD;
                }
            default:
            case DEAD:
                if (numberOfAliveNeighbours == 3) {
                    return ALIVE;
                } else {
                    return DEAD;
                }
        }
    }
}
