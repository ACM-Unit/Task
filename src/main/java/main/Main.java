package main;

import model.ArrayTaskList;
import model.Task;
import model.TaskIO;
import model.TaskList;
import org.apache.log4j.Logger;
import view.MainFrame;
import view.MainPanel;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

/**
 * Main class with tracking thread to search the nearest date
 * @autor koshchii slava
 */
public class Main {
    public static final Object MONITOR = new Object();
    public static final String TASKFILE = "sources/tasks.tsk";
    public static final String PRINTTASKFILE = "sources/printtasks.tsk";
    public static final URL urlSound = Thread.currentThread().getContextClassLoader().getResource("sounds/sound.wav");
    public static InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("sources/tasks.tsk");
    public static final URL urlPrint = Thread.currentThread().getContextClassLoader().getResource("sources/printtasks.tsk");
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
                        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(urlSound)){
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
                            Object[] options = {"завершить",
                                    "Отложить на 5 минут"};
                            int n = JOptionPane.showOptionDialog(dialog,
                                    foundTask.getTitle(),
                                    "Оповещение",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,     //do not use a custom Icon
                                    options,  //the titles of buttons
                                    options[0]);
                            if (n == 0) {
                                clip.stop();
                                if(foundTask.getTitle().contains(" (повтор)")){
                                    tasks.remove(foundTask);
                                }
                                Main.frame.revalidate();
                                Main.frame.repaint();
                                MainPanel newpanel=new MainPanel(Main.frame);
                                Main.frame.setContentPane(newpanel);
                                Main.frame.setBounds(300,180, newpanel.getWidth(), newpanel.getHeight());
                            } else if (n == 1) {
                                clip.stop();
                                Calendar date = Calendar.getInstance();
                                long t = date.getTimeInMillis();
                                Task repeatTask = null;
                                if(foundTask.getTitle().contains(" (повтор)")){
                                    tasks.remove(foundTask);
                                    repeatTask = new Task(foundTask.getTitle(), new Date(t + 5 * 60 * 1000));
                                }else {
                                    repeatTask = new Task(foundTask.getTitle() + " (повтор)", new Date(t + 5 * 60 * 1000));
                                }
                                repeatTask.setActive(true);
                                tasks.add(repeatTask);
                                Main.frame.revalidate();
                                Main.frame.repaint();
                                MainPanel newpanel=new MainPanel(Main.frame);
                                Main.frame.setContentPane(newpanel);
                                Main.frame.setBounds(300,180, newpanel.getWidth(), newpanel.getHeight());
                        }
                    }
                }
            }
        });

        try {
            TaskIO.readBinary(tasks, new File(new File(System.getProperty("java.class.path")).getAbsoluteFile().getParentFile()+"/tasks.tsk"));
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
