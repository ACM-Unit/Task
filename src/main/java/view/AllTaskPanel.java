package view;

import controller.PrintEventListener;
import controller.RedirectEventListener;
import controller.RemoveEventListener;
import main.Main;
import model.Task;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.net.URL;
import java.util.Date;

/**
 * Class which displays all tasks
 * @autor koshchii slava
 */
public class AllTaskPanel extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(AllTaskPanel.class);
    private JPanel menu=new JPanel();
    private JButton backButton = new JButton("назад");
    private JButton addbutton = new JButton();
    private JButton printbutton = new JButton();
    private final URL urlAdd = Thread.currentThread().getContextClassLoader().getResource("images/add.png");
    private final URL urlPrint = Thread.currentThread().getContextClassLoader().getResource("images/printer.png");
    private JPanel panelIn = new JPanel();
    /**
     * Constructor without parameters
     */
    public AllTaskPanel(){
        int index = 5;
        this.setSize(255, 450);
        this.setLayout(null);
        JPanel head=new JPanel();
        head.setLocation(5,50);
        head.setSize(this.getWidth()-15,this.getHeight()-80);
        Integer i=0;
        for(Task task: Main.tasks){
            JPanel panel=new JPanel();
            panel.setLayout(null);
            panel.setLocation(5,index);
            panel.setSize(this.getWidth()-25,30);
            panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            JLabel label=new JLabel(task.getTitle());
            JButton del = new JButton();
            JButton edit = new JButton();
            try {
                del.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/del.png")));
                edit.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/edit.png")));
            } catch (Exception ex) {
                LOGGER.error(ex);
            }
            label.setLocation(10,5);
            label.setSize(150,20);
            panel.add(label);
            del.setLocation(170,5);
            del.addActionListener(new RemoveEventListener(task, "All"));
            del.setSize(20,20);
            panel.add(del);
            edit.setLocation(192,5);
            edit.setSize(20,20);
            edit.addActionListener(new RedirectEventListener("All","Add", task));
            panel.add(edit);
            index+=30;
            i++;
            panelIn.add(panel);
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
        backButton.addActionListener(new RedirectEventListener("All","Main"));
        menu.add(backButton);
        menu.add(addbutton);
        menu.add(printbutton);
        addbutton.setSize(30,30);
        addbutton.setLocation(155, 5);
        addbutton.setIcon(new ImageIcon(urlAdd));
        addbutton.addActionListener(new RedirectEventListener("All","Add", new Task("", new Date())));
        printbutton.setSize(30,30);
        printbutton.setLocation(190, 5);
        printbutton.setIcon(new ImageIcon(urlPrint));
        printbutton.addActionListener(new PrintEventListener());
        this.add(menu);
    }
}
