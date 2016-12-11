package controller;

import main.Main;
import model.Task;
import model.TaskIO;
import org.apache.log4j.Logger;
import view.AddPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static main.Main.PRINTTASKFILE;
import static main.Main.TASKFILE;

/**
 * Listener class which runs when user saves added task, him implements ActionListener and one him method
 */
public class AddEventListener implements ActionListener {
    private static final Logger LOGGER=Logger.getLogger(AddEventListener.class);
    private Task removedtask;
    public AddEventListener(Task task){
        removedtask=task;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component source = (Component)e.getSource();
        AddPanel panel = (AddPanel)source.getParent();
        Task task;
        int inter =0;
        try {
            inter = Integer.parseInt(panel.getInterval().getDays().getText()) * 24 * 60 * 60 + Integer.parseInt(panel.getInterval().getHours().getText()) * 60 * 60 + Integer.parseInt(panel.getInterval().getMinutes().getText()) * 60;
        }catch(NumberFormatException e1){
            JOptionPane.showMessageDialog(null, "Неправильно заполнен интервал. В поля интервала можно вводить только цифры");
            return;
        }
        if(panel.getChekRepeat().isSelected()){
            if(panel.getTitle().getText()==null || panel.getStart().getDate()==null || panel.getEnd().getDate()==null) {
                JOptionPane.showMessageDialog(null, "Заполните все поля");
                return;
            }else if(inter==0 || panel.getStart().getDate().equals(panel.getEnd().getDate())){
                int selectedOption = JOptionPane.showConfirmDialog(null,
                        "Задача будет не повторяемой?",
                        "Подтвердите действие",
                        JOptionPane.YES_NO_OPTION);
                if (selectedOption == JOptionPane.YES_OPTION) {
                    task = new Task(panel.getTitle().getText(), panel.getStart().getDate());
                    task.setActive(panel.getActive().isSelected());
                }else{
                    return;
                }
            }else {
                try {
                    task = new Task(panel.getTitle().getText(), panel.getStart().getDate(), panel.getEnd().getDate(), inter);
                    task.setActive(panel.getActive().isSelected());
                }catch(IllegalArgumentException e1){
                    JOptionPane.showMessageDialog(null, "Время конца должно быть после времени начала");
                    return;
                }

            }
        }else{
            if(panel.getTitle().getText()==null || panel.getTime().getDate()==null){
                JOptionPane.showMessageDialog(null, "Заполните все поля");
                return;
            }else {
                task = new Task(panel.getTitle().getText(), panel.getTime().getDate());
                task.setActive(panel.getActive().isSelected());
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
                TaskIO.writeBinary(Main.tasks, new File(TASKFILE));
                TaskIO.writeText(Main.tasks, new File(PRINTTASKFILE));
                Main.mon=false;
                synchronized( Main.MONITOR  ) {
                    Main.MONITOR.notify();
                }
                LOGGER.info("New Task was added");
            } catch (IOException e1) {
                LOGGER.error(e1);
            }
        }

    }
}
