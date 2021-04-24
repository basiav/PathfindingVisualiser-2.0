import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SimulationFrame implements ActionListener{
    BoardPanel boardPanel;
    NodeGrid nodeGrid;
    public int delay;
    public JFrame frame;
    public Timer timer;
    PathAlgo pathAlgo;
    BFS bfs;
    DFS dfs;
    AStar aStar;
    public boolean simulationHasStarted;
    public JButton simulateButton;
    public JButton refreshButton;
    public JPanel buttonPanel;
    public LegendPanel legendPanel;
    public Point clickedSquare;

    public SimulationFrame(NodeGrid nodeGrid, int delay, PathAlgo pathAlgo){
        this.nodeGrid = nodeGrid;
        this.delay = delay;
        this.pathAlgo = pathAlgo;

        this.simulationHasStarted = false;

        bfs = new BFS(this.nodeGrid);
        dfs = new DFS(this.nodeGrid);
        aStar = new AStar(this.nodeGrid);
        this.timer = new Timer(delay, this);

        this.boardPanel = new BoardPanel(this.nodeGrid, this);

        buttonPanel = new JPanel();

        legendPanel = new LegendPanel(this);

        simulateButton = new JButton("Simulate!");
        refreshButton = new JButton("Refresh");

        simulateButton.addActionListener(this);
        refreshButton.addActionListener(this);

        buttonPanel.add(simulateButton);
        buttonPanel.add(refreshButton);

        frame = new JFrame("Pathfinder Visualiser");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (boardPanel.findClickedSquare(e.getPoint().x, e.getPoint().y)){
                    nodeGrid.setObstacle(clickedSquare.getRowCoordinate(), clickedSquare.getColumnCoordinate());
                    boardPanel.repaint();
                    System.out.println("Obstacle placed");
                }
                else{
                    System.out.println("Out of grid");
                }
            }
        });

        boardPanel.add(legendPanel);
        boardPanel.add(buttonPanel);
        frame.add(this.boardPanel);
    }

    public void startSimulation(){

        this.timer.start();
        switch (this.pathAlgo){
            case BFS:
                bfs.runBFS();
                break;
            case DFS:
                dfs.runDFS();
                break;
            case AStar:
                aStar.runAStar();
                break;
        }
    }

    public void showPrimalBoard(){
        this.boardPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e){

        Object source = e.getSource();

        if (source == simulateButton){
            if (!simulationHasStarted){
                simulationHasStarted = true;
                startSimulation();
            }
        }

        if (source == refreshButton){
            timer.stop();
            nodeGrid.resetGrid();
            showPrimalBoard();
            simulationHasStarted = false;
            switch (this.pathAlgo) {
                case BFS:
                    this.bfs.resetBFS();
                    break;
                case AStar:
                    this.aStar.resetAStar();
            }
        }

        if (simulationHasStarted) {
            this.boardPanel.repaint();
            switch (this.pathAlgo) {
                case BFS:
                    this.bfs.oneRoundRunning = true;
                    break;
                case DFS:
                    this.dfs.oneRoundRunning = true;
                    break;
                case AStar:
                    this.aStar.oneRoundRunning = true;
            }
        }
    }

}
