package controller;

import main.Main;
import model.Task;
import model.TaskIO;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Admin on 29.11.2016.
 */
public class RemoveEventListener implements ActionListener {
    private static final Logger LOGGER=Logger.getLogger(RemoveEventListener.class);
    public RemoveEventListener(Task task){
        this.task=task;
    }
    private Task task;
    @Override
    public void actionPerformed(ActionEvent e) {
        Main.tasks.remove(task);
        try {
            TaskIO.writeBinary(Main.tasks, new File("2.txt"));
            LOGGER.info("Task with name "+task.getTitle()+" was removed");
        } catch (FileNotFoundException e1) {
            LOGGER.error(e1);
        }
    }
}
