package controller;

import main.Main;
import model.Task;
import model.TaskIO;
import org.apache.log4j.Logger;
import view.AddPanel;
import view.AllTaskPanel;
import view.AllTaskPerDayPanel;
import view.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Listener class which runs when user saves added task, him implements ActionListener and one method
 * @autor koshchii slava
 */
public class AddEventListener implements ActionListener {
    private static final Logger LOGGER=Logger.getLogger(AddEventListener.class);
    private Task removedtask;

    /**
     * Constructor with one parameter
     * @param task - Task
     */
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
        if(panel.getTitle().getText().length()>30) {
            JOptionPane.showMessageDialog(null, "Название слишком длинное, оно должно быть не более 30 символов");
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
                TaskIO.writeBinary(Main.tasks, new File(new File(System.getProperty("java.class.path")).getAbsoluteFile().getParentFile()+"/tasks.tsk"));
                Main.mon=false;
                synchronized( Main.MONITOR  ) {
                    Main.MONITOR.notify();
                }
                LOGGER.info("New Task with name "+task.getTitle()+" was added");
            } catch (IOException e1) {
                LOGGER.error(e1);
                File file=new File(new File(System.getProperty("java.class.path")).getAbsoluteFile().getParentFile()+"/tasks.tsk");
                JOptionPane.showMessageDialog(null, " При создании новой задачи возникла непредвиденная ошибка");
            }
            Main.frame.revalidate();
            Main.frame.repaint();
            JPanel newpanel=null;
            if(panel.getBack().equals("Main")){
                newpanel=new MainPanel(Main.frame);
            }else if(panel.getBack().equals("All")){
                newpanel=new AllTaskPanel();
            }else if(panel.getBack().equals("AllDay")){
                newpanel=new AllTaskPerDayPanel();
            }
            Main.frame.setContentPane(newpanel);
            Main.frame.setBounds(300,180, newpanel.getWidth(), newpanel.getHeight());
        }

    }
}
