package util;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static main.Main.PRINTTASKFILE;

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

        try {
            FileReader fr = new FileReader(new File(PRINTTASKFILE));
            BufferedReader br = new BufferedReader(fr);
            String s;
            int i=1;
            g2.drawString("Task manager", 220, 20);
            g2.setFont(new Font("CourierThai", Font.ITALIC, 10));
            while ((s = br.readLine()) != null) {
                y += interline;
                g2.drawString(i+"."+s, x, y);
                i++;
            }
        } catch (IOException e) {
            LOGGER.error("File to print does not exist!");
        }
        return Printable.PAGE_EXISTS;
    }

}