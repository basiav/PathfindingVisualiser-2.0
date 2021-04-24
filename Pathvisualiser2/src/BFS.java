import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    NodeGrid nodeGrid;
    boolean oneRoundRunning;
    Queue<Node> verticesQueue = new LinkedList<>();

    public BFS(NodeGrid nodeGrid){
        this.nodeGrid = nodeGrid;
        this.oneRoundRunning = true;
        this.nodeGrid.resetGrid();
    }

    public void resetBFS(){
        while (!this.verticesQueue.isEmpty()){
            verticesQueue.remove();
        }
        this.oneRoundRunning = true;
    }

    public void runBFS(){
        int sourceRow = nodeGrid.start.getRowCoordinate();
        int sourceColumn = nodeGrid.start.getColumnCoordinate();
        Node source = nodeGrid.nodeGrid[sourceRow][sourceColumn];
        int endRow = nodeGrid.end.getRowCoordinate();
        int endColumn = nodeGrid.end.getColumnCoordinate();
        Node end = nodeGrid.nodeGrid[endRow][endColumn];
        verticesQueue.add(source);
        source.setVisited();
        while (verticesQueue.size() > 0 && !nodeGrid.reachedEnd) {
            while(this.oneRoundRunning) {
                Node currentNode = verticesQueue.poll();
                if (currentNode != null) {
                    if (currentNode.equals(end)) {
                        nodeGrid.reachedEnd = true;
                        break;
                    } else
                        exploreNeighbours(currentNode);
                    System.out.println("Neighbours explored");
                }
                //Else the end was unreachable
                else
                    break;
            }
            if (this.oneRoundRunning) System.out.println("IsRunning");
            else System.out.println("Not running");
        }
        if (nodeGrid.reachedEnd)
            System.out.println("You have successfully reached the end");
        else
            System.out.println("The end was not reachable");
    }

    public void exploreNeighbours(Node currentNode){
        for (int i = 0; i < 4; i++){
            int newRow = currentNode.point.getRowCoordinate() + nodeGrid.gridRowMoves()[i];
            int newColumn = currentNode.point.getColumnCoordinate() + nodeGrid.gridColumnMoves()[i];
            if (nodeGrid.isInGridBounds(newRow, newColumn)) {
                Node nextNode = nodeGrid.nodeGrid[newRow][newColumn];
                if (!nextNode.visited && !nextNode.obstacle) {
                    verticesQueue.add(nextNode);
                    nextNode.setVisited();
                    this.nodeGrid.coordinatesToPaint[newRow][newColumn] = true;
                    nodeGrid.parentTree[newRow * nodeGrid.columns + newColumn] =
                            currentNode.point.getRowCoordinate() * nodeGrid.columns + currentNode.point.getColumnCoordinate();
                }
            }
        }
        //this.oneRoundRunning = false;
    }

}