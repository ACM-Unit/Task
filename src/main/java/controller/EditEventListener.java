package controller;


import main.Main;
import model.Task;
import model.TaskIO;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 29.11.2016.
 */
public class EditEventListener implements ActionListener {
    private static final Logger LOGGER=Logger.getLogger(RemoveEventListener.class);
    private String[] items;
    private Task task;
    public EditEventListener(String... items){
        this.items=items;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for(String item: items){
            if(item==null || item==""){
                LOGGER.debug("user input empty item");
                return;
            }
        }
        DateFormat formatter = new SimpleDateFormat("d-MMM-yyyy,HH:mm:ss aaa");
        try {
            if(items.length==3){
                Date date = formatter.parse(items[1]);
                boolean active=Boolean.parseBoolean(items[2]);
                task=new Task(items[0], date);
                task.setActive(active);
            }else {
                Date start=formatter.parse(items[1]);
                Date end=formatter.parse(items[2]);
                int interval=Integer.parseInt(items[3]);
                boolean active=Boolean.parseBoolean(items[4]);
                task=new Task(items[0], start, end, interval);
                task.setActive(active);
            }
        } catch (ParseException e2) {
            LOGGER.error("wrong date"+ e2);
            return;
        }
         Main.tasks.remove(task);
         Main.tasks.add(task);
        try {
            TaskIO.writeBinary(Main.tasks, new File("2.txt"));
            LOGGER.info("Task with name "+task.getTitle()+" was edited");
        } catch (FileNotFoundException e1) {
            LOGGER.error(e1);
        }
    }
}
