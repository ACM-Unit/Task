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
import java.net.URL;
import java.text.DateFormat;
import java.util.*;

/**
 * Created by Admin on 18.12.2016.
 */
public class AllTaskPerDayPanel extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(AllTaskPanel.class);
    private JPanel menu=new JPanel();
    private JButton backButton = new JButton("назад");
    private JButton addbutton = new JButton();
    private JPanel panelIn = new JPanel();
    private final URL urlAdd = Thread.currentThread().getContextClassLoader().getResource("images/add.png");
    private final URL urlPrint = Thread.currentThread().getContextClassLoader().getResource("images/printer.png");
    /**
     * Constructor without parameters
     */
    public AllTaskPerDayPanel(){

        Date nowDate=new Date();
        Calendar calEnd = Calendar.getInstance();
        calEnd.add(Calendar.DATE, 1);
        Date endDate=calEnd.getTime();
        SortedMap<Date, Set<Task>> calendar=null;
        try {
           calendar = Tasks.calendar(Main.tasks, nowDate, endDate);
        } catch (InstantiationException e) {
            LOGGER.error(e);
            JOptionPane.showMessageDialog(null, "Непредвиденная ошибка");
            return;
        } catch (IllegalAccessException e) {
            LOGGER.error(e);
            JOptionPane.showMessageDialog(null, "Непредвиденная ошибка");
            return;
        }
        Iterator iter=calendar.entrySet().iterator();
        int index = 5;
        this.setSize(255, 450);
        this.setLayout(null);
        JPanel head=new JPanel();
        head.setLocation(5,50);
        head.setSize(this.getWidth()-15,this.getHeight()-80);
        Integer i=0;
        while(iter.hasNext()) {
            Map.Entry<Date, Set<Task>> cal = (Map.Entry<Date, Set<Task>>) iter.next();
            addPanel(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(cal.getKey()), null, index);
            index+=30;
            i++;
            for (Task task : cal.getValue()) {
                addPanel(task.getTitle(), task, index);
                index+=30;
                i++;
            }
        }
        panelIn.setLayout(null);
        panelIn.setPreferredSize(new Dimension(240,30*i+10));
        panelIn.setSize(this.getWidth()-15,370);
        JScrollPane scroll = new JScrollPane(panelIn);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        head.setLayout(new BorderLayout());
        head.add(scroll);
        this.add(head);
        menu.setLayout(null);
        menu.setLocation(5,5);
        menu.setSize(this.getWidth()-15,40);
        menu.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        backButton.setSize(100, 30);
        backButton.setLocation(5, 5);
        backButton.addActionListener(new RedirectEventListener("AllDay","Main"));
        menu.add(backButton);
        menu.add(addbutton);
        addbutton.setSize(30,30);
        addbutton.setLocation(190, 5);
        addbutton.setIcon(new ImageIcon(urlAdd));
        addbutton.addActionListener(new RedirectEventListener("AllDay","Add", new Task("", new Date())));
        this.add(menu);

    }
    public void addPanel(String title, Task task, int index){
        JPanel head = new JPanel();
        head.setLayout(null);
        head.setLocation(5, index);
        head.setSize(this.getWidth() - 25, 30);
        head.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        JLabel label = new JLabel(title);
        label.setLocation(10, 5);
        label.setSize(150, 20);
        head.add(label);
        if(task!=null) {
            JButton del = new JButton();
            JButton edit = new JButton();
            del.addActionListener(new RemoveEventListener(task, "AllDay"));
            edit.addActionListener(new RedirectEventListener("AllDay", "Add", task));
            try {
                del.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/del.png")));
                edit.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/edit.png")));
            } catch (Exception ex) {
                LOGGER.error(ex);
            }
            del.setLocation(170, 5);
            del.setSize(20, 20);
            head.add(del);
            edit.setLocation(192, 5);
            edit.setSize(20, 20);
            head.add(edit);
        }
        panelIn.add(head);
    }
}
