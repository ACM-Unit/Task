package controller;

import util.Printer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener class to print list af all tasks
 * @autor koshchii slava
 */
public class PrintEventListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Printer printer = new Printer();
    }
}
