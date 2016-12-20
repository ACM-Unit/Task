package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * class with name "Task" which describe the task
 *
 * @autor koshchii slava
 */
public class Task implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;

    /**
     * constructor with two parametrs, title and time
     *
     * @param title - name of the new task
     * @param time  - date of execution task
     */

    public Task(String title, Date time) {
        this.title = title;
        this.time = time;
        this.start = new Date(0);
        this.end = new Date(0);
        this.interval = 0;
        this.active = false;
    }

    /**
     * constructor with four parametrs - title, start, end and interval
     *
     * @param title    - name of the new task
     * @param start    - start date of a period of execution repeatable task
     * @param end      - end date of a period of execution repeatable task
     * @param interval - The interval value between executed tasks
     * @throws NumberFormatException - exception which will, when one of Date or numeric parameters less 0
     */
    public Task(String title, Date start, Date end, int interval) throws IllegalArgumentException {
        if (end.compareTo(start) < 0 || interval < 0) {
            throw new IllegalArgumentException();
        } else {
            this.title = title;
            this.start = start;
            this.end = end;
            this.interval = interval * 1000;
            this.active = false;
            this.time = new Date();
            this.time.setTime(0);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTime() {
        return (int) time.getTime();
    }

    public Date getDateTime() {
        return time;
    }

    public Date getDateStart() {
        return start;
    }

    public Date getDateEnd() {
        return end;
    }

    public void setTime(int time) {
        this.time = new Date(time * 1000);
        start.setTime(0);
        end.setTime(0);
        interval = 0;
    }

    public void setTime(Date time) {
        this.time = time;
        start.setTime(0);
        end.setTime(0);
        interval = 0;
    }

    public void setTime(Date start, Date end, int interval) {
        if (end.compareTo(start) < 0 || interval <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.start = start;
            this.end = end;
            this.interval = interval * 1000;
            active = false;
            time = new Date(0);
        }
    }

    public int getStartTime() {
        return (int) this.start.getTime();
    }

    public int getEndTime() {
        return (int) end.getTime();
    }

    public int getRepeatInterval() {
        return interval;
    }

    public boolean isRepeated() {
        return interval == 0 ? false : true;
    }

    /**
     * this method of searching for the first date after the current date
     *
     * @param current - the date, after which searches first task execution
     * @return - this method returns value of Date format
     */
    public Date nextTimeAfter(Date current) {
        if (current == null || start == null || end == null) {
        }
        Date next = new Date(-1);
        if (isActive()) {
            if (!isRepeated()) {
                if (time.after(current)) {
                    return time;
                } else {
                    return new Date(-1);
                }
            } else {
                for (long i = start.getTime(); i <= end.getTime(); i = i + interval) {
                    next.setTime(i);
                    if (next.after(current) || next.equals(current)) {
                        return next;
                    } else {
                        next.setTime(-1);
                    }
                }
            }
        }
        return next;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this == null) return false;
        if (this == o) return true;
        try {
            Task task = (Task) o;
            if (getTime() == task.getTime() && getStartTime() == task.getStartTime() && getEndTime() == task.getEndTime() && getRepeatInterval() == task.getRepeatInterval() && isActive() == task.isActive() && getTitle().equals(task.getTitle())) {
                return true;
            }
        } catch (ClassCastException e) {
            return o.equals(this) ? true : false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (int) time.getTime();
        result = 31 * result + (int) start.getTime();
        result = 31 * result + (int) end.getTime();
        result = 31 * result + interval;
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public Task clone() {
        Task taskClone;
        if (this.interval == 0) {
            taskClone = new Task(getTitle(), (Date) time.clone());
        } else {
            taskClone = new Task(getTitle(), (Date) start.clone(), (Date) end.clone(), interval / 1000);
        }
        return taskClone;
    }

    @Override
    public String toString() {
        SimpleDateFormat sf = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss.SSS]");
        String a = "";
        if (active == false) {
            a = " inactive ";
        }
        if (!isRepeated()) {

            return "\"" + title + "\" at " + sf.format(time) + a;
        } else {
            return "\"" + title + "\" from " + sf.format(start) + " to " + sf.format(end) + " every " + intervalstr(interval) + a;
        }
    }

    private String intervalstr(int interval) {
        String year;
        String month;
        String day;
        String hour;
        String minute;
        String second;
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTimeInMillis(interval);
        if (cal.get(Calendar.YEAR) - 1970 > 1) {
            year = cal.get(Calendar.YEAR) - 1970 + " years, ";
        } else if (cal.get(Calendar.YEAR) - 1970 == 1) {
            year = cal.get(Calendar.YEAR) - 1970 + " year, ";
        } else {
            year = "";
        }
        if (cal.get(Calendar.MONTH) > 1) {
            month = cal.get(Calendar.MONTH) + " months, ";
        } else if (cal.get(Calendar.MONTH) == 1) {
            month = cal.get(Calendar.MONTH) + " month, ";
        } else {
            if (year != "") {
                year = year.substring(0, year.length() - 2);
            }
            month = "";
        }
        if (cal.get(Calendar.DAY_OF_MONTH) - 1 > 1) {
            day = (cal.get(Calendar.DAY_OF_MONTH) - 1) + " days, ";
        } else if (cal.get(Calendar.DAY_OF_MONTH) - 1 == 1) {
            day = (cal.get(Calendar.DAY_OF_MONTH) - 1) + " day, ";
        } else {
            if (month != "") {
                month = month.substring(0, month.length() - 2);
            }
            day = "";
        }
        if ((cal.get(Calendar.HOUR_OF_DAY) - 2) > 1) {
            hour = (cal.get(Calendar.HOUR_OF_DAY) - 2) + " hours, ";
        } else if ((cal.get(Calendar.HOUR_OF_DAY) - 2) == 1) {
            hour = (cal.get(Calendar.HOUR_OF_DAY) - 2) + " hour, ";
        } else {
            if (day != "") {
                day = day.substring(0, day.length() - 2);
            }
            hour = "";
        }
        if (cal.get(Calendar.MINUTE) > 1) {
            minute = cal.get(Calendar.MINUTE) + " minutes, ";
        } else if (cal.get(Calendar.MINUTE) == 1) {
            minute = cal.get(Calendar.MINUTE) + " minute, ";
        } else {
            if (hour != "") {
                hour = hour.substring(0, hour.length() - 2);
            }
            minute = "";
        }
        if (cal.get(Calendar.SECOND) > 1) {
            second = cal.get(Calendar.SECOND) + " seconds";
        } else if (cal.get(Calendar.SECOND) == 1) {
            second = cal.get(Calendar.SECOND) + " second";
        } else {
            if (minute != "") {
                minute = minute.substring(0, minute.length() - 2);
            }
            second = "";
        }
        return year + month + day + hour + minute + second;
    }
}
