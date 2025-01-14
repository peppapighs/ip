package duke.task;

import duke.common.DukeException;

import java.time.format.DateTimeParseException;

/**
 * A task for the application
 *
 * @author Pontakorn Prasertsuk
 */
public abstract class Task implements Comparable<Task> {
    protected final String title;
    protected boolean isDone;

    /**
     * Constructs a new Task instance
     *
     * @param title the name of the task
     * @param isDone whether the task is completed or not
     */
    public Task(String title, boolean isDone) {
        this.title = title;
        this.isDone = isDone;
    }

    /**
     * Decodes the string from the file
     *
     * @param line the string to be decoded
     * @return the decoded task
     */
    public static Task decode(String input) throws DukeException {
        String[] inputs = input.trim().split("\\s+\\|\\s+");

        try {
            switch (inputs[0]) {
            case Todo.SYMBOL:
                return new Todo(inputs[2], inputs[1].equals("1"));
            case Deadline.SYMBOL:
                return new Deadline(inputs[2], inputs[1].equals("1"), inputs[3]);
            case Event.SYMBOL:
                return new Event(inputs[2], inputs[1].equals("1"), inputs[3]);
            default:
                throw new DukeException("Invalid task format!");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Invalid task format!");
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date format!");
        }
    }

    /**
     * Sets task status
     *
     * @param isDone the status to be set
     * @return the task after changing status
     */
    public Task setStatus(boolean isDone) {
        this.isDone = isDone;

        return this;
    }

    /**
     * Encodes the task for saving into the file
     *
     * @return the string to be saved into the file
     */
    public abstract String encode();

    @Override
    public int compareTo(Task rhs) {
        if (this.isDone == rhs.isDone) {
            return this.title.compareTo(rhs.title);
        }

        return this.isDone ? 1 : -1;
    }

    @Override
    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "] " + this.title;
    }
}
