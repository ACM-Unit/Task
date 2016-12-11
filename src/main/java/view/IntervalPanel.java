package view;

import javax.swing.*;

/**
 * Created by Admin on 08.12.2016.
 */
public class IntervalPanel extends JPanel {
    private JFormattedTextField days;
    private JFormattedTextField hours;
    private JFormattedTextField minutes;
    private JLabel ldays=new JLabel("дней");
    private JLabel lhours=new JLabel("часов");
    private JLabel lminutes=new JLabel("минут");
    public IntervalPanel(int day, int hour, int minute){
        days = new javax.swing.JFormattedTextField();
        days.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        days.setColumns(2);
        hours = new javax.swing.JFormattedTextField();
        hours.setColumns(2);
        hours.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        minutes = new javax.swing.JFormattedTextField();
        minutes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        minutes.setColumns(2);
        days.setSize(30, 20);
        hours.setSize(30, 20);
        minutes.setSize(30, 20);
        ldays.setSize(30, 20);
        lhours.setSize(30, 20);
        lminutes.setSize(30, 20);
        days.setLocation(5,5);
        ldays.setLocation(40,5);
        hours.setLocation(75,5);
        lhours.setLocation(110,5);
        minutes.setLocation(145,5);
        lminutes.setLocation(180,5);
        days.setText(String.valueOf(day));
        hours.setText(String.valueOf(hour));
        minutes.setText(String.valueOf(minute));
        this.add(days);
        this.add(ldays);
        this.add(hours);
        this.add(lhours);
        this.add(minutes);
        this.add(lminutes);

    }

    public JFormattedTextField getDays() {
        return days;
    }

    public JFormattedTextField getHours() {
        return hours;
    }

    public JFormattedTextField getMinutes() {
        return minutes;
    }

    public void setDays(JFormattedTextField days) {
        this.days = days;
    }

    public void setHours(JFormattedTextField hours) {
        this.hours = hours;
    }

    public void setMinutes(JFormattedTextField minutes) {
        this.minutes = minutes;
    }

    public JLabel getLdays() {
        return ldays;
    }

    public void setLdays(JLabel ldays) {
        this.ldays = ldays;
    }

    public JLabel getLhours() {
        return lhours;
    }

    public void setLhours(JLabel lhours) {
        this.lhours = lhours;
    }

    public JLabel getLminutes() {
        return lminutes;
    }

    public void setLminutes(JLabel lminutes) {
        this.lminutes = lminutes;
    }
}
