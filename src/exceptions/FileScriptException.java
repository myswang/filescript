package exceptions;

public class FileScriptException extends Exception {
    public FileScriptException(String message) {
        super(message);
    }

    public FileScriptException(String message, Throwable cause) {
        super(message, cause);
    }
}
