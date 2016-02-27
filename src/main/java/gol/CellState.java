package gol;

public enum CellState {
    ALIVE, DEAD;

    public static CellState from(Boolean value) {
        return value ? ALIVE : DEAD;
    }
}
