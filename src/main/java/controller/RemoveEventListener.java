package controller;

import main.Main;
import model.Task;
import model.TaskIO;
import org.apache.log4j.Logger;
import view.AllTaskPanel;
import view.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Admin on 29.11.2016.
 */
public class RemoveEventListener implements ActionListener {
    private static final Logger LOGGER=Logger.getLogger(RemoveEventListener.class);
    public RemoveEventListener(Task task, String panel){
        this.task=task;
        this.panel=panel;
    }
    private Task task;
    private String panel;
    @Override
    public void actionPerformed(ActionEvent e) {
        Main.tasks.remove(task);
        try {
            TaskIO.writeBinary(Main.tasks, new File("2.txt"));
            Main.frame.revalidate();
            Main.frame.repaint();
            JPanel newpanel=null;
            if(panel.equals("Main")){
                newpanel=new MainPanel(Main.frame);
            }else if(panel.equals("All")){
                newpanel=new AllTaskPanel();
            }
            Main.frame.setContentPane(newpanel);
            Main.frame.setBounds(300,180, newpanel.getWidth(), newpanel.getHeight());
            LOGGER.info("Task with name "+task.getTitle()+" was removed");
        } catch (FileNotFoundException e1) {
            LOGGER.error(e1);
        }
    }
}
