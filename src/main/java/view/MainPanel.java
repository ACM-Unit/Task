package view;

import controller.RedirectEventListener;
import main.Main;
import model.Task;
import model.Tasks;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Admin on 06.12.2016.
 */
public class MainPanel extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(MainPanel.class);
    public MainPanel(){
        JLabel headlabel = new JLabel("Задачи на ближайшие сутки");
        JButton addbutton = new JButton();
        JButton printbutton = new JButton();
        ImageIcon image = new ImageIcon("LogoHead.png");
        JLabel label = new JLabel("", image, JLabel.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add( label, BorderLayout.CENTER );
        panel.setSize(414, 70);
        panel.setLocation(10, 10);
        this.setSize(650,350);
        this.setLayout(null);
        this.add(headlabel);
        this.add(addbutton);
        this.add(printbutton);
        this.add(panel);
        this.setVisible(true);
        headlabel.setSize(250,25);
        headlabel.setLocation(50, 80);
        addbutton.setSize(30,30);
        addbutton.setLocation(this.getWidth()-75, 5);
        addbutton.setIcon(new ImageIcon("add.png"));
        addbutton.addActionListener(new RedirectEventListener("Add", new Task("", new Date())));
        printbutton.setSize(30,30);
        printbutton.setLocation(this.getWidth()-40, 5);
        printbutton.setIcon(new ImageIcon("printer.png"));
        printbutton.addActionListener(new RedirectEventListener("All", null));
        Date nowDate=new Date();
        Calendar calEnd = Calendar.getInstance();
        calEnd.add(Calendar.DATE, 1);
        Date endDate=calEnd.getTime();
        LOGGER.info(nowDate+"--------"+endDate);
        SortedMap<Date, Set<Task>> calendar= Tasks.calendar(Main.tasks, nowDate, endDate);
        Iterator iter=calendar.entrySet().iterator();
        int i=20;
        while(iter.hasNext()) {
            Map.Entry<Date, Set<Task>> cal = (Map.Entry<Date, Set<Task>>)iter.next();
            this.add(new CalendarPanel(cal, i));
            i+=205;
        }
    }
}
