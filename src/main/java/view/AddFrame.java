package view;

import controller.AddEventListener;
import model.Task;
import util.DateTimePicker;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Admin on 29.11.2016.
 */
public class AddFrame extends JFrame {
    private Task task;
    private Font font;
    public AddFrame(){
        super("Task");
        System.out.println("hello");
        InputStream myStream = null;
        try {
            myStream = new BufferedInputStream(
                    new FileInputStream("electronik.ttf"));
            Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            font = ttfBase.deriveFont(Font.PLAIN, 34);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Font not loaded.");
        }
        this.setBounds(300,180,345,310);
        ImageIcon imageIcon = new ImageIcon("Logo.png");
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setSize(345,310);
        container.setLayout(null);
        DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        dateTimePicker.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        DateTimePicker dateTimePicker1 = new DateTimePicker();
        dateTimePicker1.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        dateTimePicker1.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        DateTimePicker dateTimePicker2 = new DateTimePicker();
        dateTimePicker2.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        dateTimePicker2.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        JLabel headlabel=new JLabel();
        headlabel.setSize(200, 20);
        headlabel.setLocation(150,5);
        container.add(headlabel);
        Map<JLabel, JComponent> addMap=new LinkedHashMap<>();
        addMap.put(new JLabel("Название"), new JTextField(""));
        addMap.put(new JLabel("Время начала"), dateTimePicker);
        addMap.put(new JLabel("Время конца"), dateTimePicker1);
        addMap.put(new JLabel("Время"), dateTimePicker2);
        addMap.put(new JLabel("Интервал"), new JTextField(""));
        addMap.put(new JLabel(""), new JCheckBox("Активировать", false));
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
        JButton backButton = new JButton("назад");
        backButton.setSize(150, 30);
        backButton.setLocation(10, 232);
        JButton addButton = new JButton("сохранить");
        addButton.setSize(150, 30);
        addButton.setLocation(170, 232);
        addButton.addActionListener(new AddEventListener(addMap));
        container.add(backButton);
        container.add(addButton);
    }
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
