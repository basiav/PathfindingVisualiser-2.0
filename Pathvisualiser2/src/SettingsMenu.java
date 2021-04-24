import javax.swing.*;

public class SettingsMenu {
    public JFrame menuFrame;

    public SettingsMenu(){
        menuFrame = new JFrame("Pathfinding Visualiser Settings");
        menuFrame.setSize(500, 500);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);
    }

    public void menuInAction(){
        menuFrame.add(new SettingsPanel());
        menuFrame.setVisible(true);
    }
}