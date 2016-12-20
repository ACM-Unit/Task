package view;

import controller.PrintEventListener;
import controller.RedirectEventListener;
import main.Main;
import model.Task;
import model.Tasks;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.*;

/**
 * Class main panel which displays daily calendar and application logo
 * @autor koshchii slava
 */
public class MainPanel extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(MainPanel.class);
    private JLabel headlabel = new JLabel("Задачи на ближайшие сутки");
    private JButton addbutton = new JButton();
    private JButton printbutton = new JButton();
    private JButton allbutton = new JButton();
    private final URL url = Thread.currentThread().getContextClassLoader().getResource("images/LogoHead.png");
    private final URL urlAdd = Thread.currentThread().getContextClassLoader().getResource("images/add.png");
    private final URL urlAll = Thread.currentThread().getContextClassLoader().getResource("images/list.png");
    private final URL urlPrint = Thread.currentThread().getContextClassLoader().getResource("images/printer.png");
    private ImageIcon image = new ImageIcon(url);
    private JLabel label = new JLabel("", image, JLabel.CENTER);
    private JPanel panel = new JPanel(new BorderLayout());
    private JPanel head=new JPanel();
    private JPanel panelIn = new JPanel();
    private SortedMap calendar;

    /**
     * Constructor with one parameter
     * @param frame - Main frame
     */
    public MainPanel(MainFrame frame){
        panel.add( label, BorderLayout.CENTER );
        panel.setSize(414, 70);
        panel.setLocation(10, 10);
        this.setSize(643,390);
        this.setLayout(null);
        this.add(headlabel);
        this.add(addbutton);
        this.add(printbutton);
        this.add(allbutton);
        this.add(panel);
        this.setVisible(true);
        headlabel.setSize(250,25);
        headlabel.setLocation(50, 90);
        addbutton.setSize(30,30);
        addbutton.setLocation(this.getWidth()-110, 5);
        addbutton.setIcon(new ImageIcon(urlAdd));
        addbutton.addActionListener(new RedirectEventListener("Main", "Add", new Task("", new Date())));
        printbutton.setSize(30,30);
        printbutton.setLocation(this.getWidth()-40, 5);
        printbutton.setIcon(new ImageIcon(urlPrint));
        printbutton.addActionListener(new PrintEventListener());
        allbutton.setSize(30,30);
        allbutton.setLocation(this.getWidth()-75, 5);
        allbutton.setIcon(new ImageIcon(urlAll));
        allbutton.addActionListener(new RedirectEventListener("Main","All"));
        Date nowDate=new Date();
        Calendar calEnd = Calendar.getInstance();
        calEnd.add(Calendar.DATE, 1);
        Date endDate=calEnd.getTime();
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
        head.setLocation(5,110);
        head.setSize(this.getWidth()-15,212);
        panelIn.setLayout(null);
        panelIn.setPreferredSize(new Dimension(208*3,202));
        panelIn.setSize(this.getWidth()-15,210);
        JScrollPane scroll = new JScrollPane(panelIn);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        head.setLayout(new BorderLayout());
        head.add(scroll);
        this.add(head);
        int x=10;
        if(calendar.size()>3){
            JButton morebutton = new JButton();
            morebutton.setSize(300,30);
            morebutton.setLocation(this.getWidth()-311, 325);
            morebutton.setText("показать все задачи на ближайшие сутки");
            morebutton.addActionListener(new RedirectEventListener("Main", "AllDay"));
            this.add(morebutton);
        }
        int i=0;
        while(i<3 && iter.hasNext()) {
            Map.Entry<Date, Set<Task>> cal = (Map.Entry<Date, Set<Task>>)iter.next();
            panelIn.add(new CalendarPanel(cal, x, 5));
            x += 205;
            i++;
        }

    }

    public JLabel getHeadlabel() {
        return headlabel;
    }

    public void setHeadlabel(JLabel headlabel) {
        this.headlabel = headlabel;
    }

    public JButton getAddbutton() {
        return addbutton;
    }

    public void setAddbutton(JButton addbutton) {
        this.addbutton = addbutton;
    }

    public JButton getPrintbutton() {
        return printbutton;
    }

    public void setPrintbutton(JButton printbutton) {
        this.printbutton = printbutton;
    }

    public JButton getAllbutton() {
        return allbutton;
    }

    public void setAllbutton(JButton allbutton) {
        this.allbutton = allbutton;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JPanel getHead() {
        return head;
    }

    public void setHead(JPanel head) {
        this.head = head;
    }

    public JPanel getPanelIn() {
        return panelIn;
    }

    public void setPanelIn(JPanel panelIn) {
        this.panelIn = panelIn;
    }

    public SortedMap getCalendar() {
        return calendar;
    }

    public void setCalendar(SortedMap calendar) {
        this.calendar = calendar;
    }
}
