public class Point {
    private int rowCoordinate;
    private int columnCoordinate;

    public Point(int rowCoordinate, int columnCoordinate) {
        this.rowCoordinate = rowCoordinate;
        this.columnCoordinate = columnCoordinate;
    }

    public int getRowCoordinate() { return rowCoordinate; }
    public void setRowCoordinate(int rowCoordinate) { this.rowCoordinate = rowCoordinate; }
    public int getColumnCoordinate() { return columnCoordinate; }
    public void setColumnCoordinate(int columnCoordinate) { this.columnCoordinate = columnCoordinate; }

    @Override
    public String toString(){
        return ("(" + getRowCoordinate() + ", " + getColumnCoordinate() + ")");
    }

}
