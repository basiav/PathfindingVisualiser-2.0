import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    public NodeGrid nodeGrid;
    public SimulationFrame simulationFrame;

    public BoardPanel(NodeGrid nodeGrid, SimulationFrame simulationFrame){
        this.nodeGrid = nodeGrid;
        this.simulationFrame = simulationFrame;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setSize((int) (simulationFrame.frame.getWidth()), (int) (simulationFrame.frame.getHeight()));
        int width = this.getWidth();
        int height = this.getHeight();
        int widthScale = width / nodeGrid.getWidth();
        int heightScale = height / nodeGrid.getHeight();

        //draw Board
        g.setColor(Color.decode("#588c7e"));
        g.fillRect(0, 0, width, height);

        //draw BoardChessSquares
        for (int i = 0; i < nodeGrid.rows; i++){
            for (int j = 0; j < nodeGrid.columns; j++){
                if (i%2 == 0 && j%2 == 0){
                    g.setColor(Color.decode("#587e76"));
                    g.fillRect(i*widthScale,j*heightScale,widthScale,heightScale);
                }
                if (i % 2 != 0 && j % 2 != 0) {
                    g.setColor(Color.decode("#587e76"));
                    g.fillRect(i*widthScale,j*heightScale,widthScale,heightScale);
                }
                if (i%2 == 0 && j%2 != 0){
                    g.setColor(Color.decode("#588c7e"));
                    g.fillRect(i*widthScale,j*heightScale,widthScale,heightScale);
                }
                if (i%2 != 0 && j%2 == 0){
                    g.setColor(Color.decode("#588c7e"));
                    g.fillRect(i*widthScale,j*heightScale,widthScale,heightScale);
                }
            }
        }


        //draw Obstacles
        g.setColor(Color.black);
        for (int i = 0; i < this.nodeGrid.rows; i++) {
            for (int j = 0; j < this.nodeGrid.columns; j++) {
                if (this.nodeGrid.nodeGrid[i][j].obstacle){
                    g.fillOval(i * widthScale, j * heightScale, widthScale, heightScale);
                }
            }
        }

        //draw squares where we moved recently
        g.setColor(Color.yellow);
        for (int i = 0; i < this.nodeGrid.rows; i++){
            for (int j = 0; j < this.nodeGrid.columns; j++){
                if (this.nodeGrid.coordinatesToPaint[i][j]){
                    int x = i * widthScale;
                    int y = j * heightScale;
                    g.fillRect(x,y,widthScale, heightScale);
                }
            }
        }

        //draw Start and End
        g.setColor(Color.MAGENTA);
        int startRow = this.nodeGrid.start.getRowCoordinate() * widthScale;
        int startColumn = this.nodeGrid.start.getColumnCoordinate() * heightScale;
        int endRow = this.nodeGrid.end.getRowCoordinate() * widthScale;
        int endColumn = this.nodeGrid.end.getColumnCoordinate() * heightScale;
        g.fillRect(startRow, startColumn, widthScale, heightScale);
        g.fillRect(endRow, endColumn, widthScale, heightScale);


        //draw ParentPath
        g.setColor(Color.PINK);
        int end = nodeGrid.end.getRowCoordinate() * nodeGrid.columns + nodeGrid.end.getColumnCoordinate();
        int parent = nodeGrid.parentTree[end];
        while (parent > 0) {
            int column = parent % nodeGrid.columns;
            int row = (parent - column)/ nodeGrid.columns;
            g.fillOval(row * widthScale, column * heightScale, widthScale, heightScale);
            parent = nodeGrid.parentTree[row * nodeGrid.columns + column];
        }
        g.setColor(Color.MAGENTA);
        g.fillRect(startRow, startColumn, widthScale, heightScale);
    }

    public boolean findClickedSquare(int x, int y){
        int width = this.getWidth();
        int height = this.getHeight();
        int widthScale = width / nodeGrid.getWidth();
        int heightScale = height / nodeGrid.getHeight();

        int positionX = (int) x/widthScale;
        int positionY = (int) y/heightScale;

        if (positionX >= 0 && positionX < nodeGrid.rows && positionY >= 0 && positionY < nodeGrid.columns){
            simulationFrame.clickedSquare = new Point(positionX, positionY);
            return true;
        }
        return false;
    }

}
