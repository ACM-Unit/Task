package controller;

import main.Main;
import model.Task;
import model.TaskIO;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created by Admin on 29.11.2016.
 */
public class AddEventListener implements ActionListener {
    private static final Logger LOGGER=Logger.getLogger(AddEventListener.class);
    private Map<JLabel, JComponent> map;
    private Task task;
    public AddEventListener(Map<JLabel, JComponent> map){
        this.map=map;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
       /* addMap.get("Название").getToolTipText(), addMap.get("Время начала").getToolTipText(), addMap.get("Время конца").getToolTipText(), addMap.get("Время").getToolTipText(), addMap.get("Интервал").getToolTipText(), addMap.get("").getToolTipText()
        JTextField title=(JTextField)map.get("Название");
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
        }*/
        Main.tasks.remove(task);
        Main.tasks.add(task);
        try {
            TaskIO.writeBinary(Main.tasks, new File("2.txt"));
            LOGGER.info("New Task was added");
        } catch (FileNotFoundException e1) {
            LOGGER.error(e1);
        }
    }
}
