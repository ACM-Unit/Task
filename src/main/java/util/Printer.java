package util;

import model.Task;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

import static main.Main.tasks;

/**
 * Class: Printer
 * This class to print list of tasks
 * @autor koshchii slava
 */
public class Printer implements Printable {
    private static final Logger LOGGER=Logger.getLogger(Printer.class);


    public Printer() {

        PrinterJob printJob = PrinterJob.getPrinterJob();

        printJob.setPrintable(this);

        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (Exception PrintException) {
                PrintException.printStackTrace();
            }
        }

    }

    /**
     * Method: print
     * This method converts all task from text file to format for printing
     *
     * @param g a value of type Graphics
     * @param pf a value of type PageFormat
     * @param pagenum a value of type int
     * @return a value of type int
     */
    public int print(Graphics g, PageFormat pf, int pagenum) {
        if (pagenum > 0) {
            return NO_SUCH_PAGE;
        }
        int interline = 12;
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        int x =  (int) pf.getImageableX()+20;
        int y = (int) pf.getImageableY()+60;
        int i=1;
        g2.drawString("Task manager", 220, 20);
        g2.setFont(new Font("CourierThai", Font.ITALIC, 10));
        for(Task task: tasks) {
            y += interline;
            g2.drawString(i+"."+task.toString(), x, y);
            i++;
        }
        return Printable.PAGE_EXISTS;
    }

}