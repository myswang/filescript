package exceptions;

public class FileScriptParseException extends FileScriptException {
    public FileScriptParseException(String message) {
        super(message);
    }

    public FileScriptParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
