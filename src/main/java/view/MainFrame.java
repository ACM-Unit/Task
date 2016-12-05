package view;


import controller.RedirectEventListener;
import controller.RemoveEventListener;
import main.Main;
import model.Task;
import model.Tasks;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Admin on 29.11.2016.
 */
public class MainFrame extends JFrame {
private static final Logger LOGGER = Logger.getLogger(MainFrame.class);
    private Font font;
    public MainFrame(){
        super("Task manager");
        JLabel headlabel = new JLabel("Задачи на ближайшие сутки");
        JButton addbutton = new JButton();
        JButton printbutton = new JButton();
        ImageIcon image = new ImageIcon("LogoHead.png");
        JLabel label = new JLabel("", image, JLabel.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add( label, BorderLayout.CENTER );
        panel.setSize(414, 70);
        panel.setLocation(10, 10);
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
        this.setBounds(300,180,900,350);
        ImageIcon imageIcon = new ImageIcon("Logo.png");
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setSize(650,350);
        container.setLayout(null);
        container.add(headlabel);
        container.add(addbutton);
        container.add(printbutton);
        container.add(panel);
        container.setVisible(true);
        headlabel.setSize(150,25);
        headlabel.setLocation(50, 80);
        addbutton.setSize(30,30);
        addbutton.setLocation(10, 5);
        addbutton.setIcon(new ImageIcon("add.png"));
        addbutton.addActionListener(new RedirectEventListener(this, new AddFrame(), new Task(" ", 0, 0, 0)));
        printbutton.setSize(30,30);
        printbutton.setLocation(45, 5);
        printbutton.setIcon(new ImageIcon("printer.png"));
        printbutton.addActionListener(new RedirectEventListener(this, new AddFrame(), new Task(" ", 0, 0, 0)));
        Date nowDate=new Date();
        Calendar calEnd = Calendar.getInstance();
        calEnd.add(Calendar.DATE, 1);
        Date endDate=calEnd.getTime();
        SortedMap<Date, Set<Task>> calendar= Tasks.calendar(Main.tasks, nowDate, endDate);
        Iterator iter=calendar.entrySet().iterator();
        int i=20;
        while(iter.hasNext()) {
            Map.Entry<Date, Set<Task>> cal = (Map.Entry<Date, Set<Task>>)iter.next();
            Date first = cal.getKey();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Set<Task> taskSet = cal.getValue();
            container.add(timeContainer(cal, i));
            i+=205;
        }
    }
    public JPanel timeContainer(Map.Entry<Date, Set<Task>> cal, int x){
        Date date = cal.getKey();
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Set<Task> taskSet = cal.getValue();
        JPanel panel= new JPanel();
        panel.setLayout(null);
        panel.setLocation(x,100);
        panel.setSize(200,200);
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        JLabel datelabel=new JLabel(dateFormat.format(date));
        JLabel timelabel=new JLabel(timeFormat.format(date));
        datelabel.setLocation(10,10);
        datelabel.setSize(180,20);
        timelabel.setLocation(10, 40);
        timelabel.setSize(180,50);
        timelabel.setFont(font);
        timelabel.setForeground(Color.green);
        timelabel.setBackground(Color.black);
        panel.add(timelabel);
        panel.add(datelabel);
        int i=80;
        for(Task task:cal.getValue()){
            JLabel label=new JLabel(task.getTitle());
            JButton del = new JButton();
            JButton edit = new JButton();
            try {
                del.setIcon(new ImageIcon("del.png"));
                edit.setIcon(new ImageIcon("edit.png"));
            } catch (Exception ex) {
               LOGGER.error(ex);
            }
            label.setLocation(10,i);
            label.setSize(180,20);
            panel.add(label);
            del.setLocation(150,i);
            del.addActionListener(new RemoveEventListener(task));
            del.setSize(20,20);
            panel.add(del);
            edit.setLocation(172,i);
            edit.setSize(20,20);
            //edit.addActionListener(new EditEventListener(task));
            panel.add(edit);
            i+=20;
        }
        return panel;
    }

}
