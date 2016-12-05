package model;


import java.util.*;

/**
 * utility class for operation with collections of tasks
 * @autor koshchii slava
 *
 */
public class Tasks {

    /**
     *This method searches for all tasks in the period between the dates that is the parameters in this method - "from" and "to"
     * @param tasks is a collection of tasks
     * @param from is first date in this period
     * @param to is end date in this period
     * @return collection of tasks
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, Date from, Date to) {
        TaskList incomingList =new ArrayTaskList();
        Iterator iter=tasks.iterator();
        while(iter.hasNext()) {
            Task task=(Task)iter.next();
            if((task.nextTimeAfter(from).before(to) || task.nextTimeAfter(from).equals(to)) && task.nextTimeAfter(from).getTime()!=-1){
                incomingList.add(task);
            }
        }
        return incomingList;
    }

    /**
     * This method searches for all tasks in the period between the dates that is the parameters in this method - "from" and "to" and sorts these by dates
     * @param tasks is a collection of tasks
     * @param start is first date in this period
     * @param end is end date in this period
     * @return collection with keys, which are Dates, and values, which are sets of task
     */
    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end)  throws IllegalArgumentException {
        if(end.compareTo(start)<=0) {
            throw new IllegalArgumentException();
        }
        SortedMap<Date, Set<Task>> calendar1=new TreeMap<Date, Set<Task>>();
        Iterable<Task> taskList = incoming(tasks, start, end);
        Iterator iter=taskList.iterator();
        while(iter.hasNext()) {
            Task task=(Task)iter.next();
            if(new Date(task.getEndTime()).after(new Date(task.getStartTime()))){
            //System.out.println("calendar - " + task.getTitle());
            Date date=start;
            while((date.before(end) || date.equals(end)) && date.getTime()!=-1 && date.compareTo(start)>=0) {
                if(date.equals(task.nextTimeAfter(date)) && date.getTime()!=-1 ){
                     Set<Task> taskSet=new HashSet<Task>();
                     if(calendar1.containsKey(date)){
                         taskSet=calendar1.get(date);
                     }
                     taskSet.add(task);
                     calendar1.put((Date) date.clone(), taskSet);
                     date.setTime(date.getTime()+task.getRepeatInterval());
                    
                }else{
                   date = task.nextTimeAfter(date);
                    Set<Task> taskSet=new HashSet<Task>();
                if(calendar1.containsKey(date)){
                    taskSet=calendar1.get(date);
                }
                taskSet.add(task);
                calendar1.put((Date) date.clone(), taskSet);
                }
               
            }
            }
        }

        return calendar1;
    }
}
