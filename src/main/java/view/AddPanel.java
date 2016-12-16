package view;

import controller.AddEventListener;
import controller.RepeatEventListener;
import controller.RedirectEventListener;
import model.Task;
import org.apache.log4j.Logger;
import util.DateTimePicker;

import javax.swing.*;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class which displays field for create new task
 * @autor koshchii slava
 */
public class AddPanel extends JPanel {
    private static final Logger LOGGER=Logger.getLogger(AddPanel.class);
    private DateTimePicker start = new DateTimePicker();
    private DateTimePicker end = new DateTimePicker();
    private DateTimePicker time = new DateTimePicker();
    private JTextField title;
    private IntervalPanel interval;
    private JCheckBox active;
    private JCheckBox chekRepeat;
    private JButton backButton = new JButton("назад");
    private JButton addButton = new JButton("сохранить");
    private JLabel titleLabel=new JLabel("Название");
    private JLabel startLabel=new JLabel("Время начала");
    private JLabel endLabel=new JLabel("Время конца");
    private JLabel timeLabel=new JLabel("Время");
    private JLabel intervalLabel=new JLabel("Интервал");
    private String back;
    /**
     * Constructor with two parameters Task and String types
     * @param task
     * @param back
     */
    public AddPanel(Task task, String back){
        this.back=back;
        this.setSize(395,310);
        this.setLayout(null);
        start.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        start.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        end.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        end.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        time.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        time.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
        title=new JTextField(task.getTitle());
        if(task.isRepeated()){
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.setTimeInMillis(task.getRepeatInterval());
            int days=cal.get(Calendar.DAY_OF_YEAR)-1;
            int hours=cal.get(Calendar.HOUR_OF_DAY)-2;
            int minutes=cal.get(Calendar.MINUTE);
            LOGGER.info(days+" "+hours+" "+minutes);
            interval=new IntervalPanel(days, hours, minutes);
        }else{
            interval=new IntervalPanel(0, 0, 0);
        }
        active=new JCheckBox("Активировать", task.isActive());
        chekRepeat=new JCheckBox("Цикличная", task.isRepeated());
        chekRepeat.setSize(200,30);
        chekRepeat.setLocation(120, 5);
        Map<JLabel, JComponent> addMap=new LinkedHashMap<>();
        addMap.put(titleLabel, title);
        addMap.put(startLabel, start);
        addMap.put(endLabel, end);
        addMap.put(timeLabel, time);
        addMap.put(intervalLabel, interval);
        addMap.put(new JLabel(""), active);
        int i=40;
        for(Map.Entry<JLabel, JComponent> entry:addMap.entrySet()){
            entry.getKey().setSize(100,30);
            entry.getValue().setSize(250,30);
            entry.getKey().setLocation(10, i);
            entry.getValue().setLocation(120, i);
            this.add(entry.getKey());
            this.add(entry.getValue());
            if(i==104) {
                i += 1;
            }else{
                i += 32;
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTimeInMillis(task.getRepeatInterval());
        if(task.isRepeated()) {
            start.setDate(task.getDateStart());
            end.setDate(task.getDateEnd());
            start.setVisible(true);
            end.setVisible(true);
            interval.setVisible(true);
            time.setVisible(false);
            startLabel.setVisible(true);
            endLabel.setVisible(true);
            intervalLabel.setVisible(true);
            timeLabel.setVisible(false);
        }else if(task.getTitle().equals("")){
            start.setVisible(false);
            end.setVisible(false);
            interval.setVisible(false);
            time.setVisible(true);
            startLabel.setVisible(false);
            endLabel.setVisible(false);
            intervalLabel.setVisible(false);
            timeLabel.setVisible(true);
        }else{
            time.setDate(task.getDateTime());
            start.setVisible(false);
            end.setVisible(false);
            interval.setVisible(false);
            time.setVisible(true);
            startLabel.setVisible(false);
            endLabel.setVisible(false);
            intervalLabel.setVisible(false);
            timeLabel.setVisible(true);
        }
        backButton.setSize(150, 30);
        backButton.setLocation(10, 232);
        addButton.setSize(150, 30);
        addButton.setLocation(170, 232);
        chekRepeat.addActionListener(new RepeatEventListener());
        addButton.addActionListener(new AddEventListener(task));
        backButton.addActionListener(new RedirectEventListener("Add", back, null));
        this.add(chekRepeat);
        this.add(backButton);
        this.add(addButton);
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public DateTimePicker getStart() {
        return start;
    }

    public void setStart(DateTimePicker start) {
        this.start = start;
    }

    public DateTimePicker getEnd() {
        return end;
    }

    public void setEnd(DateTimePicker end) {
        this.end = end;
    }

    public DateTimePicker getTime() {
        return time;
    }

    public void setTime(DateTimePicker time) {
        this.time = time;
    }

    public JTextField getTitle() {
        return title;
    }

    public void setTitle(JTextField title) {
        this.title = title;
    }

    public IntervalPanel getInterval() {
        return interval;
    }

    public void setInterval(IntervalPanel interval) {
        this.interval = interval;
    }

    public JCheckBox getActive() {
        return active;
    }

    public void setActive(JCheckBox active) {
        this.active = active;
    }

    public JCheckBox getChekRepeat() {
        return chekRepeat;
    }

    public void setChekRepeat(JCheckBox chekRepeat) {
        this.chekRepeat = chekRepeat;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(JLabel titleLabel) {
        this.titleLabel = titleLabel;
    }

    public JLabel getStartLabel() {
        return startLabel;
    }

    public void setStartLabel(JLabel startLabel) {
        this.startLabel = startLabel;
    }

    public JLabel getEndLabel() {
        return endLabel;
    }

    public void setEndLabel(JLabel endLabel) {
        this.endLabel = endLabel;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }

    public JLabel getIntervalLabel() {
        return intervalLabel;
    }

    public void setIntervalLabel(JLabel intervalLabel) {
        this.intervalLabel = intervalLabel;
    }
}
