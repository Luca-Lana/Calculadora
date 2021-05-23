package Calculadora.visao;

import javax.swing.*;
import java.awt.*;


public class Display extends JPanel {

    private final JLabel label;


    public Display() {

      label = new JLabel("1234,56");
      setBackground(new Color(46,49,50));
      label.setForeground(Color.WHITE);
      label.setFont(new Font("courier",Font.PLAIN,30));

      setLayout(new FlowLayout(FlowLayout.RIGHT,10,25));

      add(label);
    }
}
