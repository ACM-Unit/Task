package controller;

import org.apache.log4j.Logger;
import util.DateTimePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Admin on 06.12.2016.
 */
public class IntervalEventListener implements ActionListener {
    private static final Logger LOGGER=Logger.getLogger(IntervalEventListener.class);
    JTextField title;
    DateTimePicker start;
    DateTimePicker end;
    DateTimePicker time;
    JTextField interval;
    JCheckBox active;
    JLabel titleLabel;
    JLabel startLabel;
    JLabel endLabel;
    JLabel timeLabel;
    JLabel intervalLabel;
    JLabel activeLabel;
    public IntervalEventListener(Map<JLabel, JComponent> map){
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
        JCheckBox checkInterval=(JCheckBox)e.getSource();
        if(checkInterval.isSelected()){
            time.setVisible(false);
            interval.setVisible(true);
            start.setVisible(true);
            end.setVisible(true);
            timeLabel.setVisible(false);
            intervalLabel.setVisible(true);
            startLabel.setVisible(true);
            endLabel.setVisible(true);
        }else{
            time.setVisible(true);
            interval.setVisible(false);
            start.setVisible(false);
            end.setVisible(false);
            timeLabel.setVisible(true);
            intervalLabel.setVisible(false);
            startLabel.setVisible(false);
            endLabel.setVisible(false);
        }
    }
}
