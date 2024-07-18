import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
      int boardWidth=600;
      int boardHeight=boardWidth;
      JFrame frame=new JFrame("Snake Game");
      frame.setVisible(true);
      frame.setSize(boardWidth, boardHeight);
      frame.setLocationRelativeTo(null);
      frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      Snakegame snakegame=new Snakegame(boardWidth, boardHeight);
      frame.add(snakegame);
      frame.pack();
      snakegame.requestFocus();
    }
}
