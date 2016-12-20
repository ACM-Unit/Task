package controller;

import main.Main;
import model.Task;
import model.TaskIO;
import org.apache.log4j.Logger;
import view.AllTaskPanel;
import view.AllTaskPerDayPanel;
import view.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Listener class which runs when user wants remove task, him implements ActionListener and one method
 * @autor koshchii slava
 */
public class RemoveEventListener implements ActionListener {
    private static final Logger LOGGER=Logger.getLogger(RemoveEventListener.class);

    /**
     * Constructor with two parameters
     * @param task - Task
     * @param panel - panel for repaint
     */
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
            TaskIO.writeBinary(Main.tasks, new File(new File(System.getProperty("java.class.path")).getAbsoluteFile().getParentFile()+"/tasks.tsk"));
            Main.frame.revalidate();
            Main.frame.repaint();
            JPanel newpanel=null;
            if(panel.equals("Main")){
                newpanel=new MainPanel(Main.frame);
            }else if(panel.equals("All")){
                newpanel=new AllTaskPanel();
            }else if(panel.equals("AllDay")){
                newpanel=new AllTaskPerDayPanel();
            }
            Main.frame.setContentPane(newpanel);
            Main.frame.setBounds(300,180, newpanel.getWidth(), newpanel.getHeight());
            Main.mon=false;
            synchronized( Main.MONITOR  ) {
                Main.MONITOR.notify();
            }
            LOGGER.info("Task with name "+task.getTitle()+" was removed");
        } catch (IOException e1) {
            LOGGER.error(e1);
            JOptionPane.showMessageDialog(null, "При удалении возникла непредвиденная ошибка");
        }
    }
}
