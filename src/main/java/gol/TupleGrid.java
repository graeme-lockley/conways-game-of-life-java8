package gol;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static gol.CellState.ALIVE;

public class TupleGrid implements Grid {
    private Set<Coordinate> bindings;

    private TupleGrid(Set<Coordinate> bindings) {
        this.bindings = new HashSet<>(bindings);
    }

    @Override
    public int numberOfAliveNeighbours(Coordinate coordinate) {
        return isAlive(coordinate.add(-1, -1)) +
                isAlive(coordinate.add(0, -1)) +
                isAlive(coordinate.add(1, -1)) +

                isAlive(coordinate.add(-1, 0)) +
                isAlive(coordinate.add(1, 0)) +

                isAlive(coordinate.add(-1, 1)) +
                isAlive(coordinate.add(0, 1)) +
                isAlive(coordinate.add(1, 1));
    }

    private int isAlive(Coordinate coordinate) {
        return at(coordinate) == CellState.ALIVE ? 1 : 0;
    }

    @Override
    public CellState at(Coordinate coordinate) {
        return bindings.contains(coordinate) ? CellState.ALIVE : CellState.DEAD;
    }

    @Override
    public Grid forEachRelevantCell(Function<Coordinate, CellState> cellMap) {
        Set<Coordinate> coordinates = new HashSet<>();

        bindings.stream().forEach(c -> {
            addCell(coordinates, c.add(-1, -1), cellMap.apply(c.add(-1, -1)));
            addCell(coordinates, c.add(0, -1), cellMap.apply(c.add(0, -1)));
            addCell(coordinates, c.add(1, -1), cellMap.apply(c.add(1, -1)));
            addCell(coordinates, c.add(-1, 0), cellMap.apply(c.add(-1, 0)));
            addCell(coordinates, c.add(0, 0), cellMap.apply(c.add(0, 0)));
            addCell(coordinates, c.add(1, 0), cellMap.apply(c.add(1, 0)));
            addCell(coordinates, c.add(-1, 1), cellMap.apply(c.add(-1, 1)));
            addCell(coordinates, c.add(0, 1), cellMap.apply(c.add(0, 1)));
            addCell(coordinates, c.add(1, 1), cellMap.apply(c.add(1, 1)));
        });

        return new TupleGrid(coordinates);
    }

    private void addCell(Set<Coordinate> coordinates, Coordinate coordinate, CellState cellState) {
        if (cellState == CellState.ALIVE) {
            coordinates.add(coordinate);
        }
    }

    @Override
    public boolean isEmpty() {
        return bindings.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TupleGrid tupleGrid = (TupleGrid) o;

        return bindings.equals(tupleGrid.bindings);

    }

    @Override
    public int hashCode() {
        return bindings.hashCode();
    }

    public static TupleGridBuilder builder() {
        return new TupleGridBuilder();
    }

    public static class TupleGridBuilder implements GridBuilder {
        private Set<Coordinate> coordinates = new HashSet<>();

        @Override
        public GridBuilder at(Coordinate coordinate) {
            return at(coordinate,  ALIVE);
        }

        @Override
        public GridBuilder at(Coordinate coordinate, CellState state) {
            if (state == ALIVE) {
                coordinates.add(coordinate);
            }
            return this;
        }

        @Override
        public Grid build() {
            return new TupleGrid(coordinates);
        }
    }
}
