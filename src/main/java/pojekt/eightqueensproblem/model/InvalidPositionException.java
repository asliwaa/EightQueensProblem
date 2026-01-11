package pojekt.eightqueensproblem.model;

/**
 * Exception thrown when an invalid position is provided or a game rule is violated.
 * Examples include out-of-bounds coordinates or placing a queen on an occupied square.
 *
 * @author Adam
 * @version 5.0
 */
public class InvalidPositionException extends Exception {

    /**
     * Constructs a new InvalidPositionException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public InvalidPositionException(String message) {
        super(message);
    }
}