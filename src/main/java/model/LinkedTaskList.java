package model;

import java.io.Serializable;
import java.util.Iterator;

/**
 * class with name "LinkedTaskList" describing the collection of tasks, which based on double linked list
 * @autor koshchii slava
 */
public class LinkedTaskList extends TaskList {
    private static final long serialVersionUID = 1L;
    private LinkedEntry last;
    private LinkedEntry first;
    private int size=0;
    /**
     * iterator for this collection
     */
    @Override
    public Iterator<Task> iterator() {
            return  new Iterator()  {
            private LinkedEntry iterEntry=first;
            private boolean isNext=false;
            @Override
            public boolean hasNext() {
                if(!this.iterEntry.equals(last)){
                    return true;
                }
                return false;
            }

            @Override
            public Task next() {
            if(!isNext){
                isNext=true;
                this.iterEntry=first;
                return first.getTask();
            }else{
                this.iterEntry=this.iterEntry.getNext();
                return this.iterEntry.getTask();
            }
            }

           @Override
            public void remove() throws IllegalStateException {
                if(isNext && this.iterEntry!=null){
                    if(this.iterEntry.equals(first)){
                        first=this.iterEntry.getNext();
                        last.setNext(first);
                    }
                    if(this.iterEntry.equals(last)){
                        last=this.iterEntry.getPrev();
                        first.setPrev(last);
                    }
                    this.iterEntry.getNext().setPrev(this.iterEntry.getPrev());
                    this.iterEntry.getPrev().setNext(this.iterEntry.getNext());
                    size--;
                } else {
                    throw new IllegalStateException();
                }
            }
        };
    }

    /**
     * inline class for describe one element with links on next and previous element
     */
    private class LinkedEntry implements Serializable {
        private LinkedEntry prev;
        private LinkedEntry next;
        private Task task;
        private LinkedEntry(Task task){
            this.task=task;
        }

        public void setPrev(LinkedEntry prev) {
            this.prev = prev;
        }

        public void setNext(LinkedEntry next) {
            this.next = next;
        }

        public LinkedEntry getPrev() {
            return prev;
        }

        public LinkedEntry getNext() {
            return next;
        }

        public Task getTask() {
            return task;
        }
    }
    @Override
    public int size(){
        return this.size;
    }
    /**
     * method, which adds new task in this collection
     * @param task new task
     * @throws NullPointerException  - exception which will, when parameter - "task" equals null
     */
    @Override
    public void add(Task task) {
        LinkedEntry entity=new LinkedEntry(task);
        if(this.first==null){
            this.first=entity;
            this.last=entity;
        }
        entity.setPrev(this.last);
        entity.setNext(this.first);
        this.last.setNext(entity);
        this.first.setPrev(entity);
        this.last=entity;
        this.size++;
    }
    /**
     * method, which removes task in this collection
     * @param task removable task
     * @throws NullPointerException  - exception which will, when parameter - "task" equals null
     */
    @Override
    public boolean remove(Task task) {
        LinkedEntry iterEntry=first;
        for(int i=0; i<this.size(); i++) {
            if(iterEntry.getTask().equals(task)) {
                   if(size==1){
                    last=null;
                    first=null;
                }
            if(iterEntry.equals(first)){
                    first=iterEntry.getNext();
                }
                if(iterEntry.equals(last)){
                    last=iterEntry.getPrev();
                }
                size--;
                iterEntry.getNext().setPrev(iterEntry.getPrev());
                iterEntry.getPrev().setNext(iterEntry.getNext());
                return true;
            }
            iterEntry=iterEntry.getNext();
        }
        return false;
    }

    /**
     * method, which get task from this collection by number
     * @param i number of task
     * @throws ArrayIndexOutOfBoundsException  - exception which will, when parameter - "i" more than size this collection or less than 0
     */
    @Override
    public Task getTask(int i) throws ArrayIndexOutOfBoundsException {
        if(i<0 || i>=this.size){
            throw new ArrayIndexOutOfBoundsException();
        }
        LinkedEntry entity=first;
            for (int j = 0; j < this.size; j++) {
                if (j == i) {
                    return entity.getTask();
                }
                entity = entity.getNext();
            }

        return entity.getTask();
    }
    @Override
    public TaskList clone(){
        TaskList newArray = new LinkedTaskList();
        Iterator iterator=this.iterator();
        while(iterator.hasNext()) {
            Task task=(Task)iterator.next();
            newArray.add(task.clone());
        }
        return newArray;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass() || this == null) return false;
        LinkedTaskList tasks = (LinkedTaskList) o;
        Iterator iterator=this.iterator();
        Iterator iterator1=tasks.iterator();
        while(iterator.hasNext()) {
            Task task=(Task)iterator.next();
            Task task1=(Task)iterator1.next();
            if (!task.equals(task1)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public int hashCode() {
        int result = 0;
        Iterator iterator=this.iterator();
        while(iterator.hasNext()) {
            result = 31 * result + iterator.next().hashCode();
        }
        return result;
    }
    @Override
    public String toString() {
    String linked="LinkedTaskList{ ";
    for (int i = 0; i < this.size; i++) {
           if(i==this.size-1){
               linked=linked+this.getTask(i)+"}";
           }else{
               linked=linked+this.getTask(i)+", ";
           }
           }
        return linked;
    }

}
