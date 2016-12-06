package view;


import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Admin on 29.11.2016.
 */
public class MainFrame extends JFrame {
private static final Logger LOGGER = Logger.getLogger(MainFrame.class);
    private Font font;
    public MainFrame(){
        super("Task manager");
        this.setBounds(300,180,650,350);
        this.setResizable(false);
        ImageIcon imageIcon = new ImageIcon("Logo.png");
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new MainPanel());
    }
}
