package view;

import controller.PrintEventListener;
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
    private JLabel headlabel = new JLabel("Задачи на ближайшие сутки");
    private JButton addbutton = new JButton();
    private JButton printbutton = new JButton();
    private JButton allbutton = new JButton();
    private ImageIcon image = new ImageIcon("images/LogoHead.png");
    private JLabel label = new JLabel("", image, JLabel.CENTER);
    private JPanel panel = new JPanel(new BorderLayout());
    private JPanel head=new JPanel();
    private JPanel panelIn = new JPanel();
    public MainPanel(MainFrame frame){
        panel.add( label, BorderLayout.CENTER );
        panel.setSize(414, 70);
        panel.setLocation(10, 10);
        this.setSize(643,370);
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
        addbutton.setIcon(new ImageIcon("images/add.png"));
        addbutton.addActionListener(new RedirectEventListener("Main", "Add", new Task("", new Date())));
        printbutton.setSize(30,30);
        printbutton.setLocation(this.getWidth()-40, 5);
        printbutton.setIcon(new ImageIcon("images/printer.png"));
        printbutton.addActionListener(new PrintEventListener());
        allbutton.setSize(30,30);
        allbutton.setLocation(this.getWidth()-75, 5);
        allbutton.setIcon(new ImageIcon("images/list.png"));
        allbutton.addActionListener(new RedirectEventListener("Main","All", null));
        Date nowDate=new Date();
        Calendar calEnd = Calendar.getInstance();
        calEnd.add(Calendar.DATE, 1);
        Date endDate=calEnd.getTime();
        SortedMap<Date, Set<Task>> calendar= Tasks.calendar(Main.tasks, nowDate, endDate);
        Iterator iter=calendar.entrySet().iterator();
        head.setLocation(5,110);
        head.setSize(this.getWidth()-15,210);
        panelIn.setLayout(null);
        panelIn.setPreferredSize(new Dimension(210*calendar.size(),200));
        panelIn.setSize(this.getWidth()-15,210);
        JScrollPane scroll = new JScrollPane(panelIn);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        head.setLayout(new BorderLayout());
        head.add(scroll);
        this.add(head);
        int x=10;
        if(calendar.size()>3){
            panelIn.setSize(this.getWidth()-15,230);
            head.setSize(this.getWidth()-15,230);
        }
        while(iter.hasNext()) {
            Map.Entry<Date, Set<Task>> cal = (Map.Entry<Date, Set<Task>>)iter.next();
            panelIn.add(new CalendarPanel(cal, x, 5));
            x += 205;
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
}
