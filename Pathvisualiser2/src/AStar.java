import java.util.Arrays;
import java.util.PriorityQueue;

public class AStar {
    NodeGrid nodeGrid;
    boolean oneRoundRunning;
    PriorityQueue<Node> verticesToProcess;

    public AStar(NodeGrid nodeGrid){
        this.nodeGrid = nodeGrid;
        this.oneRoundRunning = true;
        this.verticesToProcess = new PriorityQueue<>();
        this.nodeGrid.resetGrid();
    }

    public void resetAStar(){
        while(!verticesToProcess.isEmpty()){
            verticesToProcess.remove();
        }
        this.oneRoundRunning = true;
    }

    public int heuristic(Node node){
        return nodeGrid.manhattanDistance(node.point.getRowCoordinate(), node.point.getColumnCoordinate());
    }

    public void runAStar() {
        Node start = nodeGrid.getStart();
        Node end = nodeGrid.getEnd();
        start.setLocalGoal(0);
        start.setGlobalGoal(heuristic(start));
        verticesToProcess.add(start);
        //start.setVisited();
        while (verticesToProcess.size() > 0 && !nodeGrid.reachedEnd) {
            while (this.oneRoundRunning) {
                Node currentNode = verticesToProcess.poll();
                if (currentNode != null) {
                    if (currentNode.equals(end)) {
                        nodeGrid.reachedEnd = true;
                        break;
                    } else if (!currentNode.visited) {
                        //else{
                        System.out.println(Arrays.toString(verticesToProcess.toArray()));
                        System.out.println(verticesToProcess.size());
                        exploreNeighbours(currentNode);
                    }
                    System.out.println("Neighbours explored");
                }
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
        //currentNode.setVisited();
        for (int i = 0; i < 4; i++){
            int newRow = currentNode.point.getRowCoordinate() + nodeGrid.gridRowMoves()[i];
            int newColumn = currentNode.point.getColumnCoordinate() + nodeGrid.gridColumnMoves()[i];
            if (nodeGrid.isInGridBounds(newRow, newColumn)) {
                Node nextNode = nodeGrid.nodeGrid[newRow][newColumn];
                if (!nextNode.obstacle){
                    if (currentNode.localGoal + 1 < nextNode.localGoal) {
                        nextNode.localGoal = currentNode.localGoal + 1;
                        nextNode.globalGoal = nextNode.localGoal + heuristic(nextNode);
                        nodeGrid.parentTree[newRow * nodeGrid.columns + newColumn] = currentNode.point.getRowCoordinate() * nodeGrid.columns + currentNode.point.getColumnCoordinate();
                        nodeGrid.coordinatesToPaint[newRow][newColumn] = true;
                    }
                    if (!nextNode.visited){
                        verticesToProcess.add(nextNode);
                    }
                }
                /*if (!nextNode.visited && !nextNode.obstacle) {
                    verticesToProcess.add(nextNode);
                    if (currentNode.localGoal + 1 < nextNode.localGoal) {
                        nextNode.localGoal = currentNode.localGoal + 1;
                        nextNode.globalGoal = nextNode.localGoal + heuristic(nextNode);
                        nodeGrid.parentTree[newRow * nodeGrid.columns + newColumn] = currentNode.point.getRowCoordinate() * nodeGrid.columns + currentNode.point.getColumnCoordinate();
                        nodeGrid.coordinatesToPaint[newRow][newColumn] = true;
                    }
                }*/
            }
        }
        currentNode.setVisited();
        //this.oneRoundRunning = false;
    }

}