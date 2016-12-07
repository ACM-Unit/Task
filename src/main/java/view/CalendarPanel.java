package view;

import controller.RedirectEventListener;
import controller.RemoveEventListener;
import model.Task;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by Admin on 06.12.2016.
 */
public class CalendarPanel extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(CalendarPanel.class);
    private Map.Entry<Date, Set<Task>> cal;
    private int x;
    private int y;
    private Font font;
    public CalendarPanel(Map.Entry<Date, Set<Task>> cal, int x, int y){
        InputStream myStream = null;
        try {
            myStream = new BufferedInputStream(new FileInputStream("electronik.ttf"));
            Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            font = ttfBase.deriveFont(Font.PLAIN, 34);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Font not loaded.");
        }
        Date date = cal.getKey();
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        this.setLayout(null);
        this.setLocation(x,y);
        this.setSize(200,200);
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        JLabel datelabel=new JLabel(dateFormat.format(date));
        JLabel timelabel=new JLabel(timeFormat.format(date));
        datelabel.setLocation(10,10);
        datelabel.setSize(180,20);
        timelabel.setLocation(10, 40);
        timelabel.setSize(180,50);
        timelabel.setFont(font);
        timelabel.setForeground(Color.green);
        timelabel.setBackground(Color.black);
        this.add(timelabel);
        this.add(datelabel);
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
            this.add(label);
            del.setLocation(150,i);
            del.addActionListener(new RemoveEventListener(task, "Main"));
            del.setSize(20,20);
            this.add(del);
            edit.setLocation(172,i);
            edit.setSize(20,20);
            edit.addActionListener(new RedirectEventListener("Add", task));
            this.add(edit);
            i+=20;
        }
    }
}
