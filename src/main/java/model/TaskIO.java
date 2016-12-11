package model;


import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 19.09.2016.
 */
public class TaskIO {
    public static void write(TaskList tasks, OutputStream out) throws IOException{
        try (ObjectOutputStream dos = new ObjectOutputStream(out)){
            dos.writeInt(tasks.size());
            for(Task task:tasks) {
                dos.writeObject(task);
            }
        } catch (IOException e) {
            System.out.println("error writing object");
        }
    }
    public static void read(TaskList tasks, InputStream in)  throws IOException {
        try(ObjectInputStream dos = new ObjectInputStream(in)) {
            inStream(tasks, dos);
        } catch (IOException e) {
            throw new IOException("error reading object");
        }
    }
    public static void writeBinary(TaskList tasks, File file) throws IOException {
        try (ObjectOutputStream dos = new ObjectOutputStream(new FileOutputStream(file))) {
            dos.writeInt(tasks.size());
            for(Task task:tasks) {
                dos.writeObject(task);
            }
        } catch (IOException e) {
            throw new IOException("error writing object");
        }
    }
    public static void readBinary(TaskList tasks, File file) throws IOException {
        try(ObjectInputStream dos = new ObjectInputStream(new FileInputStream(file))) {
            inStream(tasks, dos);
        } catch (IOException e) {
            throw new IOException("error reading object");
        }
    }
    private static void inStream(TaskList tasks, ObjectInputStream dos) throws IOException {
        int length = dos.readInt();
        for(int i=0; i<length; i++){
            try {
                Task task = (Task)dos.readObject();
                System.out.println(task.getTitle());
                tasks.add(task);
            } catch (ClassNotFoundException e) {
                System.out.println("objects class not found");
            }
        }
    }
    public static void write(TaskList tasks, Writer out){
        try(BufferedWriter writer = new BufferedWriter(out)) {
            Iterator iter=tasks.iterator();
            while(iter.hasNext()) {
                Task task = (Task) iter.next();
                writer.write(task.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("error writing chars");
        }
    }
    public static void read(TaskList tasks, Reader in) throws ParseException {
        try(BufferedReader reader = new BufferedReader(in)) {
            String stream;
            while((stream=reader.readLine())!=null){
                tasks.add(parsingTask(stream));
            }
        }catch(IOException e){
            System.out.println("error reading chars");
        }
    }
    private static Task parsingTask(String stringTask) throws ParseException {
        String title="";
        Date time=new Date();
        Date start=new Date();
        Date end=new Date();
        int interval=0;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
        Pattern regexp = Pattern.compile("\".+\"");
        Matcher m = regexp.matcher(stringTask);
        if(m.find()) {
            title=stringTask.substring(m.start()+1, m.end()-1);
        }
        regexp = Pattern.compile("every ");
        m = regexp.matcher(stringTask);
        if(m.find()) {
            Calendar cal = Calendar.getInstance();
            String intervalStr=stringTask.substring(m.end(), stringTask.length());
            regexp = Pattern.compile(" second");
            m = regexp.matcher(intervalStr);
            if(m.find()) {
                interval=interval+ Integer.parseInt(intervalStr.split(" second")[0].split(", ")[intervalStr.split(" second")[0].split(", ").length-1]);
            }
            regexp = Pattern.compile(" minute");
            m = regexp.matcher(intervalStr);
            if(m.find()) {
                interval=interval+ Integer.parseInt(intervalStr.split(" minute")[0].split(", ")[intervalStr.split(" minute")[0].split(", ").length-1])*60;
            }
            regexp = Pattern.compile(" hour");
            m = regexp.matcher(intervalStr);
            if(m.find()) {
                interval=interval+ Integer.parseInt(intervalStr.split(" hour")[0].split(", ")[intervalStr.split(" hour")[0].split(", ").length-1])*60*60;
            }
            regexp = Pattern.compile(" day");
            m = regexp.matcher(intervalStr);
            if(m.find()) {
                interval=interval+ Integer.parseInt(intervalStr.split(" day")[0].split(", ")[intervalStr.split(" day")[0].split(", ").length-1])*60*60*24;
            }
            regexp = Pattern.compile(" month");
            m = regexp.matcher(intervalStr);
            if(m.find()) {
                interval=interval+ Integer.parseInt(intervalStr.split(" month".split(", ")[intervalStr.split(" month")[0].split(", ").length-1])[0])*60*60*24*30;
            }
            regexp = Pattern.compile(" year");
            m = regexp.matcher(intervalStr);
            if(m.find()) {
                interval=interval+ Integer.parseInt(intervalStr.split(" year")[0].split(", ")[intervalStr.split(" year")[0].split(", ").length-1])*60*60*24*30*12;
            }
        }
        regexp = Pattern.compile("from");
        m = regexp.matcher(stringTask);
        if(m.find()) {
            time=new Date(0);
            regexp = Pattern.compile("\\[[^to]+\\]");
            m = regexp.matcher(stringTask);
            int i=0;
            while(m.find()) {
                if(i==0) {
                    start=sf.parse(stringTask.substring(m.start()+1, m.end()-1));
                    i++;
                }else{
                    end=sf.parse(stringTask.substring(m.start()+1, m.end()-1));
                }
            }
            regexp = Pattern.compile(" inactive ");
            m = regexp.matcher(stringTask);
            Task task=new Task(title, start, end, (int)interval);
            if(!m.find()) {
                task.setActive(true);
            }
            return task;
        }else{
            regexp = Pattern.compile("\\[.+\\]");
            m = regexp.matcher(stringTask);
            if(m.find()) {
                time = sf.parse(stringTask.substring(m.start() + 1, m.end() - 1));
            }
            regexp = Pattern.compile(" inactive ");
            m = regexp.matcher(stringTask);
            Task task=new Task(title, time);
            if(!m.find()) {
                task.setActive(true);
            }
            return task;
        }
    }
     public static void writeText(TaskList tasks, File file) {
        try(Writer stream = new FileWriter(file)){
            write(tasks, stream);
        } catch (IOException e) {
            System.out.println("error writing chars");
        }

    }
    public static void readText(TaskList tasks, File file) throws ParseException {
        try(Reader stream = new FileReader(file)) {
            read(tasks, stream);
        }catch(IOException e){
            System.out.println("error reading chars");
        }
    }

}
