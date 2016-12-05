package main;

import model.ArrayTaskList;
import model.TaskIO;
import model.TaskList;
import view.MainFrame;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
public static volatile TaskList tasks=new ArrayTaskList();
    public static void main(String[] args) {
        try {
            TaskIO.readBinary(tasks, new File("2.txt"));
        } catch (FileNotFoundException e) {
            tasks=new ArrayTaskList();
        }
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}
