import javax.swing.*;
import java.awt.*;

public class LegendPanel extends JPanel {
    private SimulationFrame simulationFrame;

    LegendPanel(SimulationFrame simulationFrame){
        this.simulationFrame = simulationFrame;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setSize((int) (400), (int) (100));
        this.setLocation(10, 700);
        int width = this.getWidth();
        int height = this.getHeight();
        int widthScale = width / 25;
        int heightScale = height / 15;

        g.setColor(Color.BLACK);
        g.drawString("LEGEND", width/25,height/25 + 10);

        g.setColor(Color.black);
        g.fillOval(widthScale, (height / 25 + 10) * 2, widthScale, heightScale);
        g.drawString(" - Obstacle", widthScale + widthScale,(height / 25 + 10) * 2 + heightScale);

        g.setColor(Color.MAGENTA);
        g.fillRect(width/25, (height/25 + 10) * 3, widthScale, heightScale);
        g.setColor(Color.BLACK);
        g.drawString(" - Start Point & End Point", width/25 + widthScale,(height/25 + 10) * 3 + heightScale);

        g.setColor(Color.YELLOW);
        g.fillRect(width/25, (height/25 + 10) * 4, widthScale, heightScale);
        g.setColor(Color.BLACK);
        g.drawString(" - Area affected by pathfinding", width/25 + widthScale,(height/25 + 10) * 4 + heightScale);

        g.setColor(Color.PINK);
        g.fillOval(width/25, (height/25 + 10) * 5, widthScale, heightScale);
        g.setColor(Color.BLACK);
        g.drawString(" - In DFS: a path we found, In BFS and AStar: the shortest path", width/25 + widthScale,(height/25 + 10) * 5 + heightScale);

        g.setFont(new Font ("TimesRoman", Font.BOLD, 15));
        g.drawString("Place obstacles with your mouse, if you wish.", width/25,(height/25 + 10) * 6 + 2*heightScale);
    }
}
