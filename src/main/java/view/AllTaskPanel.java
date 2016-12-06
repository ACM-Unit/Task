package view;

import controller.RedirectEventListener;
import controller.RemoveEventListener;
import main.Main;
import model.Task;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.util.Date;

/**
 * Created by Admin on 06.12.2016.
 */
public class AllTaskPanel extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(AllTaskPanel.class);
    public AllTaskPanel(){
        this.setSize(245,80);
        this.setLayout(null);
        int i=50;
        for(Task task: Main.tasks){
            this.setSize(245, this.getHeight()+30);
            JPanel head=new JPanel();
            head.setLayout(null);
            head.setLocation(5,i);
            head.setSize(this.getWidth()-15,30);
            head.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            JLabel label=new JLabel(task.getTitle());
            JButton del = new JButton();
            JButton edit = new JButton();
            try {
                del.setIcon(new ImageIcon("del.png"));
                edit.setIcon(new ImageIcon("edit.png"));
            } catch (Exception ex) {
                LOGGER.error(ex);
            }
            label.setLocation(10,5);
            label.setSize(180,20);
            head.add(label);
            del.setLocation(180,5);
            del.addActionListener(new RemoveEventListener(task, "All"));
            del.setSize(20,20);
            head.add(del);
            edit.setLocation(202,5);
            edit.setSize(20,20);
            edit.addActionListener(new RedirectEventListener("Add", task));
            head.add(edit);
            i+=30;
            this.add(head);
        }
        JPanel menu=new JPanel();
        menu.setLayout(null);
        menu.setLocation(5,5);
        menu.setSize(this.getWidth()-15,40);
        menu.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        JButton backButton = new JButton("назад");
        backButton.setSize(100, 30);
        backButton.setLocation(5, 5);
        backButton.addActionListener(new RedirectEventListener("Main", null));
        menu.add(backButton);
        JButton addbutton = new JButton();
        JButton printbutton = new JButton();
        menu.add(addbutton);
        menu.add(printbutton);
        addbutton.setSize(30,30);
        addbutton.setLocation(155, 5);
        addbutton.setIcon(new ImageIcon("add.png"));
        addbutton.addActionListener(new RedirectEventListener("Add", new Task("", new Date())));
        printbutton.setSize(30,30);
        printbutton.setLocation(190, 5);
        printbutton.setIcon(new ImageIcon("printer.png"));
        printbutton.addActionListener(new RedirectEventListener("All", null));
        this.add(menu);
    }
}
