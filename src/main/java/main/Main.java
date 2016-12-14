package main;

import model.ArrayTaskList;
import model.Task;
import model.TaskIO;
import model.TaskList;
import org.apache.log4j.Logger;
import view.MainFrame;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Main class with tracking thread to search the nearest date
 * @autor koshchii slava
 */
public class Main {
    public static final Object MONITOR = new Object();
    public static final String TASKFILE = "sources/tasks.tsk";
    public static final String PRINTTASKFILE = "sources/printtasks.tsk";
    public static final String SOUNDFILE = "sounds/sound.wav";
    public static Thread myThread;
    public static boolean mon=true;
    public static volatile TaskList tasks=new ArrayTaskList();
    public static MainFrame frame;
    private static final long TWENTYFORHOURS=1000*60*60*24;
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        myThread = new Thread(new Runnable(){
            @Override
            public void run() {
                LOGGER.info("Tracking thread has been started");
                while(true) {
                    mon=false;
                    Task foundTask=null;
                    try {
                        foundTask = tasks.getTask(0);
                    }catch(ArrayIndexOutOfBoundsException e){
                        try {
                            synchronized( MONITOR )
                            {
                                MONITOR.wait();
                            }
                        } catch (InterruptedException e1) {
                            LOGGER.error(e);
                        }
                    }
                    long millis=TWENTYFORHOURS;
                    for(Task task:tasks){
                        long m=task.nextTimeAfter(new Date()).getTime()-new Date().getTime();
                        if(m<millis && m>0){
                            millis=m;
                            foundTask=task;
                            mon=true;
                        }
                    }
                    try {
                        synchronized( MONITOR )
                        {
                            MONITOR.wait(millis);
                        }
                    } catch (InterruptedException e) {
                        LOGGER.error(e);
                    }
                    if(mon) {
                        String soundName = SOUNDFILE;
                        Clip clip=null;
                        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile())){
                            clip= AudioSystem.getClip();
                            clip.open(audioInputStream);
                            clip.setFramePosition(0);
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                            clip.start();
                        } catch (UnsupportedAudioFileException e) {
                            LOGGER.error("Unsupported audio file exception"+e);
                        } catch (IOException e) {
                            LOGGER.error("Stream error"+e);
                        } catch (LineUnavailableException e) {
                            LOGGER.error("Line unavailable exception"+e);
                        }
                        final JOptionPane optionPane = new JOptionPane();
                        final JDialog dialog = new JDialog();
                        dialog.setAlwaysOnTop(true);
                        optionPane.showMessageDialog(dialog, foundTask.getTitle());
                        if ((int)optionPane.getMessageType()==-1) {
                            clip.stop();
                        }
                    }
                }
            }
        });

        try {
            TaskIO.readBinary(tasks, new File(TASKFILE));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Нет сохраненных задач");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Нет сохраненных задач");
        }
        frame = new MainFrame();
        frame.setVisible(true);
        myThread.start();
    }
}
