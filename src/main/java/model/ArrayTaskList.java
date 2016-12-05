package model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * class with name "ArrayTaskList" describing the collection of tasks, which based on array
 * @autor koshchii slava
 */
public class ArrayTaskList extends TaskList {
    private static final long serialVersionUID = 1L;
    private Task[] arrayTask=new Task[10];
    private int size=0;
    /**
     * iterator for this collection
     */
    @Override
    public Iterator<Task> iterator() {
        return new Iterator(){
             private int count = size;
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < count;
            }

            @Override
            public Task next() {
                if (index < count) {
                    return arrayTask[index++];
                } else {
                    throw new NoSuchElementException("No such element.");
                }
            }

            @Override
            public void remove() {
            if(index>0 && index-1<count){
                Task[] afterRemove = new Task[arrayTask.length];
                int j=0;
                for(int i=0; i<count; i++){
                    if(i==index-1){
                        size--;
                    }else{
                        afterRemove[j++]=arrayTask[i];
                    }
                }
                index--;
                arrayTask=afterRemove;
            }else{
                 throw new IllegalStateException();
            }
            }
        };
    }
    @Override
    public int size(){
        return this.size;
    }
    /**
     * method, which adds new task in this collection
     * @param task new task
     */
    @Override
    public void add(Task task) {
    if(task!=null){
        if(this.arrayTask.length-1==this.size){
            Task[] newArray=new Task[this.arrayTask.length+(int)(this.arrayTask.length*0.2)];
            for(int i=0; i<this.size; i++) {
                    newArray[i]=this.arrayTask[i];
            }
            this.arrayTask=newArray;
        }
        this.arrayTask[this.size++]=task;
        }
    }
    /**
     * method, which removes task in this collection
     * @param task removable task
     * @throws NullPointerException  - exception which will, when parameter - "task" equals null
     */
    @Override
    public boolean remove(Task task) {
        int j=0;
        int size=this.size;
        boolean found=false;
        Task[] newArray=new Task[this.arrayTask.length];
        for(int i=0; i<size; i++){
            if(this.arrayTask[i].equals(task) && !found){
                found=true;
                this.size--;
            }else{
                newArray[j]=this.arrayTask[i];
                j++;
            }

        }
        if(found) this.arrayTask=newArray;
        return found;
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
        return this.arrayTask[i];
    }
    @Override
    public TaskList clone(){
          TaskList newArray = new ArrayTaskList();
        for(int i=0; i<size; i++){
            newArray.add(this.arrayTask[i].clone());
        }
       return newArray;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || this == null) return false;
        ArrayTaskList tasks = (ArrayTaskList) o;
        Iterator iterator=this.iterator();
        Iterator iterator1=tasks.iterator();
        while(iterator.hasNext()) {
        Task task=(Task)iterator.next();
        Task task1=(Task)iterator1.next();
            if (!task.equals(task1)) {
            System.out.println(task);
                return false;
            }
        }
        return true;
    }

   @Override
    public int hashCode() {
        int result=0;
        Iterator iterator=this.iterator();
        while(iterator.hasNext()) {
            result = 31 * result + iterator.next().hashCode();
        }
        return result;
    }
   @Override
    public String toString() {
        return "ArrayTaskList{" +
                "arrayTask=" + Arrays.toString(arrayTask) +
                '}';
    }
}
