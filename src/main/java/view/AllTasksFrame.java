package view;

import controller.RemoveEventListener;
import main.Main;
import model.Task;
import org.apache.log4j.Logger;

import javax.swing.*;

/**
 * Created by Admin on 29.11.2016.
 */
public class AllTasksFrame extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(AllTasksFrame.class);
    public AllTasksFrame(){
        super("All Task");
        this.setBounds(300,180,345,310);
        ImageIcon imageIcon = new ImageIcon("Logo.png");
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        JPanel container = (JPanel)this.getContentPane();
        container.setSize(345,310);
        container.setLayout(null);
        int i=10;
        for(Task task: Main.tasks){
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
            container.add(label);
            del.setLocation(150,i);
            del.addActionListener(new RemoveEventListener(task, "All"));
            del.setSize(20,20);
            container.add(del);
            edit.setLocation(172,i);
            edit.setSize(20,20);
           /* edit.addActionListener(new RedirectEventListener(this, new AddPanel(task)));*/
            container.add(edit);
            i+=30;
        }
    }
}
