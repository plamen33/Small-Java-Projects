import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

            result = 0;
            lastCommand = "=";
            start=true;
            display = new JButton("0");
            display.setEnabled(false);
            add(display, BorderLayout.NORTH);

            ActionListener insertAction = new InsertAction();
            ActionListener commandAction = new CommandAction();

            panel = new JPanel();
            panel.setLayout(new GridLayout(4,4));


            addButton("1", insertAction);
            addButton("2", insertAction);
            addButton("3", insertAction);
            addButton("*", commandAction);

            addButton("4", insertAction);
            addButton("5", insertAction);
            addButton("6", insertAction);
            addButton("/", commandAction);

            addButton("7", insertAction);
            addButton("8", insertAction);
            addButton("9", insertAction);
            addButton("+", commandAction);

            addButton("0", insertAction);
            addButton(".", insertAction);
            addButton("=", insertAction);
            addButton("-", commandAction);

            add(panel, BorderLayout.CENTER);
        }

    private void addButton(String digitOrAction, ActionListener actionType) {
        JButton button = new JButton(digitOrAction);
        button.addActionListener(actionType);
        panel.add(button);
    }
    private class InsertAction implements ActionListener {

        public void actionPerformed(ActionEvent event) {

        }
    }

    private class CommandAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {

        }
    }

    private JButton display;
    private JPanel panel;
    private double result;
    private String lastCommand;
    private boolean start;



}// end of CalculatorPlatform class


