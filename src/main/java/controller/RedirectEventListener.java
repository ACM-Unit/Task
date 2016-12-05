package controller;

import model.Task;
import view.AddFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Admin on 04.12.2016.
 */
public class RedirectEventListener implements ActionListener {
    private JFrame thisFrame;
    private JFrame thatFrame;
    private Task task;
    public RedirectEventListener(JFrame thisFrame, JFrame thatFrame, Task task){
        this.task=task;
        this.thisFrame=thisFrame;
        this.thatFrame=thatFrame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ku-ku");
        thisFrame.dispose();
        thatFrame.setVisible(true);
        if(thisFrame.getClass().equals(AddFrame.class)){
            AddFrame add = (AddFrame)thisFrame;
            add.setTask(task);
        }
    }
}
