package controller;

import main.Main;
import model.Task;
import org.apache.log4j.Logger;
import view.AddPanel;
import view.AllTaskPanel;
import view.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Admin on 04.12.2016.
 */
public class RedirectEventListener implements ActionListener {
    private static final Logger LOGGER = Logger.getLogger(RedirectEventListener.class);
    private String panel;
    private Task task;
    public RedirectEventListener(String panel, Task task){
        this.panel=panel;
        this.task=task;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //Main.frame.removeAll();
        Main.frame.revalidate();
        Main.frame.repaint();
        JPanel newpanel=null;
        if(panel.equals("Add")){
            newpanel=new AddPanel(task);
        }else if(panel.equals("Main")){
            newpanel=new MainPanel(Main.frame);
        }else if(panel.equals("All")){
            newpanel=new AllTaskPanel();
        }
        Main.frame.setContentPane(newpanel);
        Main.frame.setBounds(300,180, newpanel.getWidth(), newpanel.getHeight());
        /*thisPanel.setVisible(false);
        thatPanel.setVisible(true);*/
    }
}
