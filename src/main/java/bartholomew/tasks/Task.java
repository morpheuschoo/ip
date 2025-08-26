package bartholomew.tasks;

public class Task {
    private String desc;
    private boolean completed;

    public Task(String desc) {
        this.desc = desc;
        this.completed = false;
    }

    @Override
    public String toString() {
        return "[" + (this.completed ? "X" : " ") + "] " + this.desc;
    }

    public void markTask() {
        this.completed = true;
    }

    public void unmarkTask() {
        this.completed = false;
    }

    public String getDescription() {
        return this.desc;
    }

    public boolean isDone() {
        return this.completed;
    }
}
