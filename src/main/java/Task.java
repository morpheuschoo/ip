public class Task {
    private String desc;
    private boolean completed;

    public Task(String desc) {
        this.desc = desc;
        this.completed = false;
    }

    public String toString() {
        return "[" + (this.completed ? "X" : " ") + "] " + this.desc;
    }

    public void markTask() {
        this.completed = true;
    }

    public void unmarkTask() {
        this.completed = false;
    }
}
