package tasks;

public class Deadline extends Task {
    private String dueDate;
    
    public Deadline(String desc, String dueDate) {
        super(desc);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.dueDate + ")"; 
    }

    public String getDueDate() {
        return this.dueDate;
    }
}
