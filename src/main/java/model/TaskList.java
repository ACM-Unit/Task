package model;



import java.io.Serializable;

/**
 * abstract class with name "TaskList" describing all collection of tasks
 * @autor koshchii slava
 */
public abstract class TaskList implements Iterable<Task>, Serializable {
    public abstract int size();
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int i);
    public abstract TaskList clone();

}
