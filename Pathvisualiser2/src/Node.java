public class Node implements Comparable<Node> {
    Point point;
    boolean obstacle;
    boolean visited;
    int localGoal;
    int globalGoal;


    public Node(Point point){
        this.point = point;
        this.visited = false;
        this.localGoal = (int) Integer.MAX_VALUE;
        this.globalGoal = (int) Integer.MAX_VALUE;
    }

    public void resetNode(){
        this.obstacle = false;
        this.visited = false;
        this.localGoal = (int) Integer.MAX_VALUE;
        this.globalGoal = (int) Integer.MAX_VALUE;
    }

    public void setVisited() { this.visited = true; }
    public void setObstacle(){ this.obstacle = true; }
    public void setLocalGoal(int localGoal) { this.localGoal = localGoal; }
    public void setGlobalGoal(int globalGoal) { this.globalGoal = globalGoal; }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.globalGoal, o.globalGoal);
    }

    @Override
    public String toString(){
        return this.point.toString();
    }
}