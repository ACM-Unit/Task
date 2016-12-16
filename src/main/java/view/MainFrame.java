package view;


import org.apache.log4j.Logger;

import javax.swing.*;

/**
 * Class main form
 * @autor koshchii slava
 */
public class MainFrame extends JFrame {
private static final Logger LOGGER = Logger.getLogger(MainFrame.class);

    /**
     * Constructor without parameters
     */
    public MainFrame(){
        super("Task manager");
        this.setBounds(300,180,643,370);
        this.setResizable(false);
        ImageIcon imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/Logo.png"));
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new MainPanel(this));
    }
}
