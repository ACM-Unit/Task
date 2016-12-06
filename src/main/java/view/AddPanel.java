package view;

import controller.AddEventListener;
import controller.IntervalEventListener;
import controller.RedirectEventListener;
import model.Task;
import util.DateTimePicker;

import javax.swing.*;
import java.text.DateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Admin on 06.12.2016.
 */
public class AddPanel extends JPanel {
    public AddPanel(Task task){
        this.setSize(345,310);
        this.setLayout(null);
        DateTimePicker start = new DateTimePicker();
        start.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        start.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        DateTimePicker end = new DateTimePicker();
        end.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        end.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        DateTimePicker time = new DateTimePicker();
        time.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        time.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        JLabel headlabel=new JLabel();
        headlabel.setSize(200, 20);
        headlabel.setLocation(150,5);
        this.add(headlabel);
        JTextField title=new JTextField(task.getTitle());
        JTextField interval=new JTextField("");
        JCheckBox active=new JCheckBox("Активировать", task.isActive());
        JCheckBox chekInterval=new JCheckBox("Цикличная", task.isRepeated());
        JLabel titleLabel=new JLabel("Название");
        JLabel startLabel=new JLabel("Время начала");
        JLabel endLabel=new JLabel("Время конца");
        JLabel timeLabel=new JLabel("Время");
        JLabel intervalLabel=new JLabel("Интервал");
        chekInterval.setSize(200,30);
        chekInterval.setLocation(120, 5);
        this.add(chekInterval);
        Map<JLabel, JComponent> addMap=new LinkedHashMap<>();
        addMap.put(titleLabel, title);
        addMap.put(startLabel, start);
        addMap.put(endLabel, end);
        addMap.put(timeLabel, time);
        addMap.put(intervalLabel, interval);
        addMap.put(new JLabel(""), active);
        int i=40;
        for(Map.Entry<JLabel, JComponent> entry:addMap.entrySet()){
            entry.getKey().setSize(100,30);
            entry.getValue().setSize(200,30);
            entry.getKey().setLocation(10, i);
            entry.getValue().setLocation(120, i);
            this.add(entry.getKey());
            this.add(entry.getValue());
            if(i==104) {
                i += 1;
            }else{
                i += 32;
            }
        }
        if(task.isRepeated()) {
            start.setDate(task.getDateStart());
            end.setDate(task.getDateEnd());
            interval.setText(String.valueOf(task.getRepeatInterval()));
            start.setVisible(true);
            end.setVisible(true);
            interval.setVisible(true);
            time.setVisible(false);
            startLabel.setVisible(true);
            endLabel.setVisible(true);
            intervalLabel.setVisible(true);
            timeLabel.setVisible(false);
        }else if(task.getTitle().equals("")){
            start.setVisible(false);
            end.setVisible(false);
            interval.setVisible(false);
            time.setVisible(true);
            startLabel.setVisible(false);
            endLabel.setVisible(false);
            intervalLabel.setVisible(false);
            timeLabel.setVisible(true);
        }else{
            time.setDate(task.getDateTime());
            start.setVisible(false);
            end.setVisible(false);
            interval.setVisible(false);
            time.setVisible(true);
            startLabel.setVisible(false);
            endLabel.setVisible(false);
            intervalLabel.setVisible(false);
            timeLabel.setVisible(true);
        }
        chekInterval.addActionListener(new IntervalEventListener(addMap));
        JButton backButton = new JButton("назад");
        backButton.setSize(150, 30);
        backButton.setLocation(10, 232);
        JButton addButton = new JButton("сохранить");
        addButton.setSize(150, 30);
        addButton.setLocation(170, 232);
        addButton.addActionListener(new AddEventListener(addMap, chekInterval, task));
        backButton.addActionListener(new RedirectEventListener("Main", null));
        this.add(backButton);
        this.add(addButton);
    }
}
