package controller;

import model.TaskIO;
import util.PrintJobWatcher;

import javax.print.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static main.Main.PRINTTASKFILE;
import static main.Main.tasks;

/**
 * Created by Admin on 11.12.2016.
 */
public class PrintEventListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        String testData = "hello world \r\n have a nice day.";
        OutputStream os=null;
        TaskIO.writeText(tasks, new File(PRINTTASKFILE));
        InputStream is = null;
        try {
            is = new FileInputStream(PRINTTASKFILE);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        DocFlavor flavor =  DocFlavor.INPUT_STREAM.AUTOSENSE   ;


        //DocFlavor flavor = DocFlavor.STRING.TEXT_PLAIN;
        //DocFlavor flavor = DocFlavor.CHAR_ARRAY.TEXT_PLAIN;

        // Find the default service

        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        System.out.println(service);

        // Create the print job
        DocPrintJob job = service.createPrintJob();
        Doc doc= new SimpleDoc(is, flavor, null);

        PrintJobWatcher pjDone = new PrintJobWatcher(job);

        // Print it
        try {
            job.print(doc, null);
        } catch (PrintException e1) {
            e1.printStackTrace();
        }

        pjDone.waitForDone();

        // It is now safe to close the input stream
        try {
            is.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
