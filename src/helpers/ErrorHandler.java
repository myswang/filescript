package helpers;

import exceptions.FileScriptIOException;
import exceptions.FileScriptParseException;
import exceptions.FileScriptRuntimeException;

// Utility program to print errors out to the user
// The purpose of this is to catch any FileSystemExceptions and display them to the user at the final
// point of execution in the program
public class ErrorHandler {
    private ErrorHandler() {
        throw new UnsupportedOperationException("Utility class, do not instantiate");
    }

    // Static method to handle exceptions
    public static void handle(Exception e) {
        if (e instanceof FileScriptIOException) {
            printError("I/O Error", e.getMessage());
        } else if (e instanceof FileScriptRuntimeException) {
            printError("Runtime Error", e.getMessage());
        } else if (e instanceof FileScriptParseException) {
            printError("Parse Error", e.getMessage());
        } else {
            // For unhandled exceptions
            printError("Unknown Error", e.getMessage());
        }
        System.exit(1);
    }

    // Helper method to print formatted error
    private static void printError(String errorType, String message) {
        System.err.println("[" + errorType + "]: " + message);
    }
}
