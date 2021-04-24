public class DFS  {
    NodeGrid nodeGrid;
    boolean oneRoundRunning;
    boolean waiting;
    public Colour[] boardSquaresColours;
    Node source;
    Node end;

    public DFS(NodeGrid nodeGrid){
        this.nodeGrid = nodeGrid;
        this.oneRoundRunning = true;
        this.waiting = true;
        this.boardSquaresColours = new Colour[this.nodeGrid.rows * this.nodeGrid.columns];
        this.nodeGrid.resetGrid();
    }

    public void runDFS(){

        //DFS colouring white
        for (int i = 0; i < this.nodeGrid.rows; i++){
            for (int j = 0; j < this.nodeGrid.columns; j++){
                this.boardSquaresColours[i*this.nodeGrid.columns + j] = Colour.WHITE;
            }
        }

        //Start DFS visit from Source and continue until you reach the End
        int sourceRow = nodeGrid.start.getRowCoordinate();
        int sourceColumn = nodeGrid.start.getColumnCoordinate();
        int endRow = nodeGrid.end.getRowCoordinate();
        int endColumn = nodeGrid.end.getColumnCoordinate();
        this.source = nodeGrid.nodeGrid[sourceRow][sourceColumn];
        this.end = nodeGrid.nodeGrid[endRow][endColumn];
        DFSVisit(this.source);

        //Pathfinding result
        if (this.nodeGrid.reachedEnd) {
            System.out.println("You have successfully reached the end");
            //Path from the end to the source
            this.nodeGrid.printSolution(endRow * nodeGrid.columns + endColumn);
        }
        else
            System.out.println("The end is unreachable");
    }

    public void DFSVisit(Node currentNode) {
        if (currentNode.equals(end)){
            this.nodeGrid.reachedEnd = true;
            return;
        }
        if (nodeGrid.reachedEnd)
            return;
        System.out.println(oneRoundRunning);
        int currentRow = currentNode.point.getRowCoordinate();
        int currentColumn = currentNode.point.getColumnCoordinate();
        boardSquaresColours[currentRow * this.nodeGrid.columns + currentColumn] = Colour.GRAY;
        while(waiting){
            while(oneRoundRunning) {
                for (int i = 0; i < 4; i++) {
                    int newRow = currentRow + nodeGrid.gridRowMoves()[i];
                    int newColumn = currentColumn + nodeGrid.gridColumnMoves()[i];
                    if (nodeGrid.isInGridBounds(newRow, newColumn)) {
                        Node nextNode = nodeGrid.nodeGrid[newRow][newColumn];
                        if (!nextNode.obstacle) {
                            if (this.boardSquaresColours[newRow * this.nodeGrid.columns + newColumn] == Colour.WHITE) {
                                //Set parent path
                                this.nodeGrid.parentTree[newRow * this.nodeGrid.columns + newColumn] = currentRow * this.nodeGrid.columns + currentColumn;
                                //Set squares to colour in this round
                                this.nodeGrid.coordinatesToPaint[newRow][newColumn] = true;
                                //OneRound
                                //this.oneRoundRunning = false;
                                //Explore
                                DFSVisit(nextNode);
                                if (nodeGrid.reachedEnd)
                                    return;
                            }
                        }
                    }
                    //We are out of possible moves in this layer, we need to take a step back
                    if (i == 3)
                        return;
                }
            }
            System.out.println("Waiting");
        }
        //This vertex has been processed, colouring it black
        boardSquaresColours[currentRow * this.nodeGrid.columns + currentColumn] = Colour.BLACK;
    }

}
