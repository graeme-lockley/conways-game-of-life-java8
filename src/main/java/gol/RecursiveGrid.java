package gol;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static gol.CellState.ALIVE;

public class RecursiveGrid {
    private RecursiveGrid() {
    }

    public static RecursiveGridBuilder builder() {
        return new RecursiveGridBuilder();
    }

    public static class RecursiveGridBuilder implements GridBuilder {
        private Grid current = new EmptyRecursiveGrid();

        @Override
        public GridBuilder at(Coordinate coordinate) {
            return at(coordinate, ALIVE);
        }

        @Override
        public GridBuilder at(Coordinate coordinate, CellState state) {
            if (state == ALIVE) {
                current = new CoordinateRecursiveGrid(coordinate, current);
            }
            return this;
        }

        @Override
        public Grid build() {
            return current;
        }
    }

    public static class CoordinateRecursiveGrid implements Grid {
        private Grid parent;
        private Coordinate coordinate;

        public CoordinateRecursiveGrid(Coordinate coordinate, Grid parent) {
            this.coordinate = coordinate;
            this.parent = parent;
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
            return cellState(coordinate) == ALIVE ? 1 : 0;
        }

        @Override
        public Grid forEachRelevantCell(Function<Coordinate, CellState> cellMap) {
            final List<Coordinate> coordinates = Arrays.asList(
                    coordinate.add(-1, -1),
                    coordinate.add(0, -1),
                    coordinate.add(1, -1),
                    coordinate.add(-1, 0),
                    coordinate.add(0, 0),
                    coordinate.add(1, 0),
                    coordinate.add(-1, 1),
                    coordinate.add(0, 1),
                    coordinate.add(1, 1));

            Grid accumulator = parent.forEachRelevantCell(cellMap);
            for (Coordinate c : coordinates) {
                accumulator = addCell(accumulator, c, cellMap.apply(c));
            }
            return accumulator;

        }

        private Grid addCell(Grid grid, Coordinate coordinate, CellState cellState) {
            if (cellState == ALIVE) {
                return new CoordinateRecursiveGrid(coordinate, grid);
            } else {
                return grid;
            }
        }

        @Override
        public CellState cellState(Coordinate coordinate) {
            return this.coordinate.equals(coordinate) ? ALIVE : parent.cellState(coordinate);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    public static class EmptyRecursiveGrid implements Grid {
        @Override
        public int numberOfAliveNeighbours(Coordinate coordinate) {
            return 0;
        }

        @Override
        public Grid forEachRelevantCell(Function<Coordinate, CellState> cellMap) {
            return new EmptyRecursiveGrid();
        }

        @Override
        public CellState cellState(Coordinate coordinate) {
            return CellState.DEAD;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }
}
