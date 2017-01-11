import java.awt.*;
import javax.swing.*;

public class JavaCalculator {
    public static void main(String[] args) {
       EventQueue.invokeLater(new Runnable() {

           public void run() {
               CalculatorFrame frame = new CalculatorFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);;
           }
       });

    }
}
class CalculatorFrame extends JFrame {
      public CalculatorFrame(){
       setTitle("Java Calculator");
          CalculatorPlatform platform = new CalculatorPlatform();
          add(platform);
          pack();
      }
}
class CalculatorPlatform extends JPanel {
        public CalculatorPlatform(){
            setLayout(new BorderLayout()); // we set a manager BorderLayout

        }
    }


