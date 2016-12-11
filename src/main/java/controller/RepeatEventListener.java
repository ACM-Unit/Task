package controller;

import org.apache.log4j.Logger;
import view.AddPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener class which runs when user changes repeat properties of editable task
 */
public class RepeatEventListener implements ActionListener {
    private static final Logger LOGGER=Logger.getLogger(RepeatEventListener.class);
    public RepeatEventListener(){
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox checkInterval=(JCheckBox)e.getSource();
        AddPanel panel=(AddPanel)checkInterval.getParent();
        if(checkInterval.isSelected()){
            panel.getTime().setVisible(false);
            panel.getInterval().setVisible(true);
            panel.getStart().setVisible(true);
            panel.getEnd().setVisible(true);
            panel.getTimeLabel().setVisible(false);
            panel.getIntervalLabel().setVisible(true);
            panel.getStartLabel().setVisible(true);
            panel.getEndLabel().setVisible(true);
        }else{
            panel.getTime().setVisible(true);
            panel.getInterval().setVisible(false);
            panel.getStart().setVisible(false);
            panel.getEnd().setVisible(false);
            panel.getTimeLabel().setVisible(true);
            panel.getIntervalLabel().setVisible(false);
            panel.getStartLabel().setVisible(false);
            panel.getEndLabel().setVisible(false);
        }
    }
}
