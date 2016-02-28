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
        int numberOfNeighbours = generation.numberOfAliveNeighbours(coordinate);
        switch (generation.at(coordinate)) {
            case ALIVE:
                if (numberOfNeighbours < 2) {
                    return DEAD;
                } else if (numberOfNeighbours == 2 || numberOfNeighbours == 3) {
                    return ALIVE;
                } else {
                    return DEAD;
                }
            default:
            case DEAD:
                if (numberOfNeighbours == 3) {
                    return ALIVE;
                } else {
                    return DEAD;
                }
        }
    }
}
