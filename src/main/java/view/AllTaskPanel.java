package view;

import controller.PrintEventListener;
import controller.RedirectEventListener;
import controller.RemoveEventListener;
import main.Main;
import model.Task;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
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

    /**
     * Constructor without parameters
     */
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
                del.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/del.png")));
                edit.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/edit.png")));
            } catch (Exception ex) {
                LOGGER.error(ex);
            }
            label.setLocation(10,5);
            label.setSize(150,20);
            head.add(label);
            del.setLocation(180,5);
            del.addActionListener(new RemoveEventListener(task, "All"));
            del.setSize(20,20);
            head.add(del);
            edit.setLocation(202,5);
            edit.setSize(20,20);
            edit.addActionListener(new RedirectEventListener("All","Add", task));
            head.add(edit);
            i+=30;
            this.add(head);
        }
        menu.setLayout(null);
        menu.setLocation(5,5);
        menu.setSize(this.getWidth()-15,40);
        menu.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        backButton.setSize(100, 30);
        backButton.setLocation(5, 5);
        backButton.addActionListener(new RedirectEventListener("All","Main", null));
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
