package view;

import controller.AddEventListener;
import controller.IntervalEventListener;
import main.Main;
import model.Task;
import org.apache.log4j.Logger;
import util.DateTimePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Admin on 29.11.2016.
 */
public class AddFrame extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(AddFrame.class);
    public AddFrame(Task task){
        super("Task");
        this.setBounds(300,180,345,310);
        ImageIcon imageIcon = new ImageIcon("Logo.png");
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame)e.getSource();
                frame.setVisible(false);
                frame.dispose();
                Main.frame.setVisible(true);
                Main.frame.setEnabled(true);
                }
        });
        Container container = this.getContentPane();
        container.setSize(345,310);
        container.setLayout(null);
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
        container.add(headlabel);
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
        container.add(chekInterval);
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
            entry.getKey().setLocation(10, i);
            entry.getValue().setSize(200,30);
            entry.getValue().setLocation(120, i);
            container.add(entry.getKey());
            container.add(entry.getValue());
            i+=32;
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
        container.add(backButton);
        container.add(addButton);
    }

}
