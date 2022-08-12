public class Todo extends Task {
    Todo(String title, boolean status) {
        super(title, status);
    }

    @Override
    public Todo setStatus(boolean status) {
        return new Todo(this.getTitle(), status);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}