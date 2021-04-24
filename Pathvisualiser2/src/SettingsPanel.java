import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

public class SettingsPanel extends JPanel implements ActionListener {
    public static final int WIDTH  = 600;
    public static final int HEIGHT = 600;

    private JTextField boardRowsNumber;
    private JTextField boardColumnsNumber;
    private JTextField delay;
    private JTextField algorithmChoice;
    private JTextField generateObstaclesRandomly;
    private JTextField obstaclesNumber;
    private JTextField startRow;
    private JTextField startColumn;
    private JTextField endRow;
    private JTextField endColumn;

    private JLabel boardRowsNumberLabel;
    private JLabel boardColumnsNumberLabel;
    private JLabel delayLabel;
    private JLabel algorithmChoiceLabel;
    private JLabel generateObstaclesRandomlyLabel;
    private JLabel obstaclesNumberLabel;
    private JLabel startRowLabel;
    private JLabel startColumnLabel;
    private JLabel endRowLabel;
    private JLabel endColumnLabel;

    private JButton startButton;

    public SettingsPanel(){

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        startButton = new JButton("Start pathfinding");
        startButton.addActionListener(this);

        boardRowsNumberLabel = new JLabel("Number of rows on board:  ");
        boardColumnsNumberLabel = new JLabel("Number of columns on board:   ");
        delayLabel = new JLabel("Refresh time in ms:    ");
        algorithmChoiceLabel = new JLabel("Pathfinding algorithm to use:    ");
        generateObstaclesRandomlyLabel = new JLabel("Generate obstacles randomly?   ");
        obstaclesNumberLabel = new JLabel("Number of obstacles:     ");
        startRowLabel = new JLabel("Start row:      ");
        startColumnLabel = new JLabel("Start column:     ");
        endRowLabel = new JLabel("End row:      ");
        endColumnLabel = new JLabel("End column:    ");

        int fieldWidth = 20;
        boardRowsNumber = new JTextField();
        boardRowsNumber.setColumns(fieldWidth);
        boardRowsNumber.setText("50");

        boardColumnsNumber = new JTextField();
        boardColumnsNumber.setColumns(fieldWidth);
        boardColumnsNumber.setText("50");

        delay = new JTextField();
        delay.setColumns(fieldWidth);
        delay.setText("1");

        algorithmChoice = new JTextField();
        algorithmChoice.setColumns(fieldWidth);
        algorithmChoice.setText("DFS or BFS or AStar");

        generateObstaclesRandomly = new JTextField();
        generateObstaclesRandomly.setColumns(fieldWidth);
        generateObstaclesRandomly.setText("Yes (1) or No (0)");

        obstaclesNumber = new JTextField();
        obstaclesNumber.setColumns(fieldWidth);
        obstaclesNumber.setText("100");

        startRow = new JTextField();
        startRow.setColumns(fieldWidth);
        startRow.setText("10");

        startColumn = new JTextField();
        startColumn.setColumns(fieldWidth);
        startColumn.setText("10");

        endRow = new JTextField();
        endRow.setColumns(fieldWidth);
        endRow.setText("30");

        endColumn = new JTextField();
        endColumn.setColumns(fieldWidth);
        endColumn.setText("30");

        boardRowsNumberLabel.setLabelFor(boardRowsNumber);
        boardColumnsNumberLabel.setLabelFor(boardColumnsNumber);
        delayLabel.setLabelFor(delay);
        algorithmChoiceLabel.setLabelFor(algorithmChoice);
        generateObstaclesRandomlyLabel.setLabelFor(generateObstaclesRandomly);
        obstaclesNumberLabel.setLabelFor(obstaclesNumber);
        startRowLabel.setLabelFor(startRow);
        startColumnLabel.setLabelFor(startColumn);
        endRowLabel.setLabelFor(endRow);
        endColumnLabel.setLabelFor(endColumn);

        JPanel l1 = new JPanel();
        JPanel l2 = new JPanel();
        JPanel l3 = new JPanel();
        JPanel l4 = new JPanel();
        JPanel l5 = new JPanel();
        JPanel l6 = new JPanel();
        JPanel l7 = new JPanel();
        JPanel l8 = new JPanel();
        JPanel l9 = new JPanel();
        JPanel l10 = new JPanel();

        l1.add(boardRowsNumberLabel);
        l2.add(boardColumnsNumberLabel);
        l3.add(delayLabel);
        l4.add(algorithmChoiceLabel);
        l5.add(generateObstaclesRandomlyLabel);
        l6.add(obstaclesNumberLabel);
        l7.add(startRowLabel);
        l8.add(startColumnLabel);
        l9.add(endRowLabel);
        l10.add(endColumnLabel);

        l1.add(boardRowsNumber);
        l2.add(boardColumnsNumber);
        l3.add(delay);
        l4.add(algorithmChoice);
        l5.add(generateObstaclesRandomly);
        l6.add(obstaclesNumber);
        l7.add(startRow);
        l8.add(startColumn);
        l9.add(endRow);
        l10.add(endColumn);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);

        add(new JLabel("Board properties"));
        add(l1);
        add(l2);
        add(l7);
        add(l8);
        add(l9);
        add(l10);
        add(new JLabel("Pathfinding properties"));
        add(l3);
        add(l4);
        add(l5);
        add(l6);

        add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        NodeGrid nodeGrid = new NodeGrid((int) Integer.parseInt(boardRowsNumber.getText()),
            (int) Integer.parseInt(boardColumnsNumber.getText()));
        nodeGrid.initialise();

        Point start = new Point((int) Integer.parseInt(startRow.getText()), (int) Integer.parseInt(startColumn.getText()));
        Point end = new Point((int) Integer.parseInt(endRow.getText()), (int) Integer.parseInt(endColumn.getText()));

        nodeGrid.setStartAndEnd(start, end);

        int obstaclesNo = (int) Integer.parseInt(obstaclesNumber.getText());

        if ((int) Integer.parseInt(generateObstaclesRandomly.getText()) == 1){
            generateObstaclesRandomly(nodeGrid, obstaclesNo);
        }
        else {
            nodeGrid.setObstacle(1, 1);
            nodeGrid.setObstacle(40, 1);
            nodeGrid.setObstacle(9, 9);
            nodeGrid.setObstacle(40, 9);
            nodeGrid.setObstacle(1, 5);
            nodeGrid.setObstacle(8, 1);
            nodeGrid.setObstacle(20, 20);
            nodeGrid.setObstacle(39,39);
        }

        String chosenAlgorithm = algorithmChoice.getText();
        PathAlgo algorithmToUse = PathAlgo.BFS;
        switch(chosenAlgorithm){
            case "BFS":
                algorithmToUse = PathAlgo.BFS;
                break;
            case "DFS":
                algorithmToUse = PathAlgo.DFS;
                break;
            case "AStar":
                algorithmToUse = PathAlgo.AStar;
                break;
        }

        SimulationFrame simulationFrame = new SimulationFrame(nodeGrid, Integer.parseInt(delay.getText()), algorithmToUse);
        simulationFrame.showPrimalBoard();

    }

    public void generateObstaclesRandomly(NodeGrid nodeGrid, int obstaclesNumber){
        for (int i = 0; i < obstaclesNumber; i++){
            int randomRow = ThreadLocalRandom.current().nextInt(nodeGrid.rows);
            int randomColumn = ThreadLocalRandom.current().nextInt(nodeGrid.columns);
            if (nodeGrid.isInGridBounds(randomRow, randomColumn) && randomRow != nodeGrid.start.getRowCoordinate()
                    && randomColumn != nodeGrid.start.getColumnCoordinate() && randomRow != nodeGrid.end.getRowCoordinate()
                    && randomColumn != nodeGrid.end.getColumnCoordinate() && !nodeGrid.nodeGrid[randomRow][randomColumn].obstacle){
                nodeGrid.setObstacle(randomRow, randomColumn);
            }
        }
    }


}