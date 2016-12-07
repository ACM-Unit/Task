package view;

import controller.RedirectEventListener;
import main.Main;
import model.Task;
import model.Tasks;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.*;

/**
 * Created by Admin on 06.12.2016.
 */
public class MainPanel extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(MainPanel.class);
    public MainPanel(MainFrame frame){
        JLabel headlabel = new JLabel("Задачи на ближайшие сутки");
        JButton addbutton = new JButton();
        JButton printbutton = new JButton();
        ImageIcon image = new ImageIcon("LogoHead.png");
        JLabel label = new JLabel("", image, JLabel.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add( label, BorderLayout.CENTER );
        panel.setSize(414, 70);
        panel.setLocation(10, 10);
        this.setSize(660,360);
        this.setLayout(null);
        this.add(headlabel);
        this.add(addbutton);
        this.add(printbutton);
        this.add(panel);
        this.setVisible(true);
        headlabel.setSize(250,25);
        headlabel.setLocation(50, 90);
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
        JPanel head=new JPanel();
        head.setLayout(null);
        head.setLocation(5,110);
        head.setSize(this.getWidth()-15,210);
        head.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        if(calendar.size()>3){
            head.setSize(this.getWidth()-15,440);
            frame.setBounds(300,180,660,560);
            this.setSize(660,560);
            if(calendar.size()>6){
                JScrollPane pane = new JScrollPane();
                head.add(pane);
            }
        }
        int x=20;
        int y=5;
        int n=1;
        while(iter.hasNext()) {
            Map.Entry<Date, Set<Task>> cal = (Map.Entry<Date, Set<Task>>)iter.next();
            head.add(new CalendarPanel(cal, x, y));
            if(n%3==0){
                x=20;
                y+=205;
            }else {
                x += 205;
            }
            n++;
        }
        this.add(head);
    }

}
