package exceptions;

public class FileScriptRuntimeException extends FileScriptException {
    public FileScriptRuntimeException(String message) {
        super(message);
    }

    public FileScriptRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
