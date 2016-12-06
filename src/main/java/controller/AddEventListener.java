package controller;

import main.Main;
import model.Task;
import model.TaskIO;
import org.apache.log4j.Logger;
import util.DateTimePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Admin on 29.11.2016.
 */
public class AddEventListener implements ActionListener {
    private static final Logger LOGGER=Logger.getLogger(AddEventListener.class);
    private Task removedtask;
    private JTextField title;
    private DateTimePicker start;
    private DateTimePicker end;
    private DateTimePicker time;
    private JTextField interval;
    private JCheckBox active;
    private JCheckBox checkRepeat;
    private JLabel titleLabel;
    private JLabel startLabel;
    private JLabel endLabel;
    private JLabel timeLabel;
    private JLabel intervalLabel;
    private JLabel activeLabel;
    public AddEventListener(Map<JLabel, JComponent> map, JCheckBox checkRepeat, Task task){
        removedtask=task;
        this.checkRepeat=checkRepeat;
        Iterator<Map.Entry<JLabel, JComponent>> iter=map.entrySet().iterator();
        int i=0;
        while(iter.hasNext()){
            Map.Entry<JLabel, JComponent> entry=iter.next();
            if(i==0){
                this.title=(JTextField)entry.getValue();
                this.titleLabel=entry.getKey();
            }else if(i==1){
                this.start=(DateTimePicker)entry.getValue();
                this.startLabel=entry.getKey();
            }else if(i==2){
                this.end=(DateTimePicker)entry.getValue();
                this.endLabel=entry.getKey();
            }else if(i==3){
                this.time=(DateTimePicker)entry.getValue();
                this.timeLabel=entry.getKey();
            }else if(i==4){
                this.interval=(JTextField)entry.getValue();
                this.intervalLabel=entry.getKey();
            }else if(i==5){
                this.active=(JCheckBox)entry.getValue();
            }
            i++;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Task task;
        if(checkRepeat.isSelected()){
            if(title.getText()==null || start.getDate()==null || end.getDate()==null || interval.getText()==null){
                JOptionPane.showMessageDialog(null, "Заполните все поля");
                return;
            }else {
                task = new Task(title.getText(), start.getDate(), end.getDate(), Integer.parseInt(interval.getText()));
                task.setActive(active.isSelected());
            }
        }else{
            if(title.getText()==null || time.getDate()==null){
                JOptionPane.showMessageDialog(null, "Заполните все поля");
                return;
            }else {
                task = new Task(title.getText(), time.getDate());
                task.setActive(active.isSelected());
            }
        }
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Сохранить изменения?",
                "Подтвердите действие",
                JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION) {
            Main.tasks.remove(removedtask);
            removedtask=task;
            Main.tasks.add(task);
            try {
                TaskIO.writeBinary(Main.tasks, new File("2.txt"));
                TaskIO.writeText(Main.tasks, new File("3.txt"));
                LOGGER.info("New Task was added");
            } catch (FileNotFoundException e1) {
                LOGGER.error(e1);
            }
        }else{
                //break;
        }

    }
}
