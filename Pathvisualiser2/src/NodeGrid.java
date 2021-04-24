public class NodeGrid {
    public int rows;
    public int columns;
    public Node[][] nodeGrid;
    public boolean reachedEnd;
    public Point start;
    public Point end;
    public boolean[][] coordinatesToPaint;
    public int[] parentTree;

    public NodeGrid(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.nodeGrid = new Node[rows][columns];
        this.reachedEnd = false;
        this.coordinatesToPaint = new boolean[rows][columns];
        this.parentTree = new int[rows * columns];
    }

    public void initialise(){
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                nodeGrid[i][j] = new Node(new Point(i,j));
                this.nodeGrid[i][j].obstacle = false;
                this.nodeGrid[i][j].visited = false;
                this.coordinatesToPaint[i][j] = false;
                this.parentTree[i*this.columns + j] = -1;
                nodeGrid[i][j].localGoal = (int) Integer.MAX_VALUE;
                nodeGrid[i][j].globalGoal = (int) Integer.MAX_VALUE;
            }
        }
    }

    public void resetGrid(){
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                nodeGrid[i][j].visited = false;
                nodeGrid[i][j].localGoal = (int) Integer.MAX_VALUE;
                nodeGrid[i][j].globalGoal = (int) Integer.MAX_VALUE;
                this.coordinatesToPaint[i][j] = false;
                this.parentTree[i * columns + j] = -1;
            }
        }
        this.reachedEnd = false;
    }

    public void deepGridReset(){
        resetGrid();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                nodeGrid[i][j].obstacle = false;
            }
        }
    }

    public void setStartAndEnd(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public void setObstacle(int row, int column){
        if (row != start.getRowCoordinate() && row != end.getRowCoordinate()
        || column != start.getColumnCoordinate() && column != end.getColumnCoordinate()){
            this.nodeGrid[row][column].setObstacle();
        }
    }

    public boolean isInGridBounds(int row, int column) {
        return (row >= 0 && row < this.rows && column >= 0 && column < this.columns);
    }

    public void printSolution(int square){
        if (square == end.getRowCoordinate() * columns + end.getColumnCoordinate())
            System.out.println("End: " + (end.getRowCoordinate() * columns + end.getColumnCoordinate()));
        if (parentTree[square] == -1) {
            System.out.println("That's the source");
            return;
        }
        System.out.println(" <- " + parentTree[square]);
        printSolution(parentTree[square]);
    }

    public int manhattanDistance(int row, int column){
        int dx = Math.abs(row - end.getRowCoordinate());
        int dy = Math.abs(column - end.getColumnCoordinate());
        return (dx + dy);
    }

    public int euclideanDistance(int row, int column){
        int dx = Math.abs(row - end.getRowCoordinate());
        int dy = Math.abs(column - end.getColumnCoordinate());
        return (int) Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));
    }

    public boolean[][] getCoordinatesToPaint(){ return this.coordinatesToPaint; }

    public int[] gridRowMoves() {
        return new int[]{0, 1, 0, -1};
    }
    public int[] gridColumnMoves() {
        return new int[] {1,0,-1,0};
    }

    public Node getStart(){ return this.nodeGrid[start.getRowCoordinate()][start.getColumnCoordinate()]; }
    public Node getEnd(){ return this.nodeGrid[end.getRowCoordinate()][end.getColumnCoordinate()]; }

    public int getWidth() { return this.rows; }
    public int getHeight(){ return this.columns; }
}