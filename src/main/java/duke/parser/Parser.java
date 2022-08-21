package duke.parser;

import duke.command.*;
import duke.common.DukeException;
import duke.task.*;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Parser {

  private static final Scanner scanner = new Scanner(System.in);

  public static String readCommand() {
    return scanner.nextLine();
  }

  public static Command parse(String fullCommand) throws DukeException {
    String[] inputs = fullCommand.trim().split("\\s+", 2);

    try {
      switch (inputs[0]) {
        case "bye":
          return new ExitCommand();
        case "list":
          return new ListCommand();
        case "find":
          return new FindCommand(inputs[1]);
        case "mark":
          return (new MarkCommand(Integer.parseInt(inputs[1]) - 1));
        case "unmark":
          return (new UnmarkCommand(Integer.parseInt(inputs[1]) - 1));
        case "delete":
          return (new DeleteCommand(Integer.parseInt(inputs[1]) - 1));
        case "todo":
          return new AddCommand(new Todo(inputs[1], false));
        case "deadline":
          String[] deadlineArgs = inputs[1].split("\\s+/by\\s+", 2);
          return new AddCommand(
            new Deadline(deadlineArgs[0], false, deadlineArgs[1])
          );
        case "event":
          String[] eventArgs = inputs[1].split("\\s+/at\\s+", 2);
          return new AddCommand(new Event(eventArgs[0], false, eventArgs[1]));
        default:
          throw new DukeException(
            "Sorry, I don't know what do you mean by that."
          );
      }
    } catch (IndexOutOfBoundsException e) {
      throw new DukeException("Invalid input format!");
    } catch (NumberFormatException e) {
      throw new DukeException("Invalid input format!");
    } catch (DateTimeParseException e) {
      throw new DukeException("Invalid date format!");
    }
  }
}
