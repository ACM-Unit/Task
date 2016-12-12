package controller;

import util.Printer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Admin on 11.12.2016.
 */
public class PrintEventListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Printer printer = new Printer();
    }
}
