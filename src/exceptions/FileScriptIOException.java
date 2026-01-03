package exceptions;

public class FileScriptIOException extends FileScriptException {
    public FileScriptIOException(String message) {
        super(message);
    }

    public FileScriptIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
