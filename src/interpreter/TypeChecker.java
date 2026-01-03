package interpreter;

import exceptions.FileScriptRuntimeException;
import types.FileScriptValue;

// A dynamic type checker for "FileScriptValues" within our language
// Ensures that the object type within the container matches the specified object type
public class TypeChecker {
    public static <T> boolean checkObj(Object value, Class<T> c) throws FileScriptRuntimeException {
        if (value == null) {
            // a null type does not complete type checks
            throw new FileScriptRuntimeException("Value was unexpectedly null");
        }
        return c.isInstance(value);
    }

    public static <T> boolean isType(FileScriptValue<?> fileScriptValue, Class<T> c) throws FileScriptRuntimeException {
        Object value = fileScriptValue.getValue();
        return checkObj(value, c);
    }

    // Unwraps value outside "FileScriptValue" container, returning the value or throwing a FileScriptRuntimeException
    // if it is not matching the corresponding type
    // Note: Unwrap is inspired by Rust, feel free to check that out if there is confusion
    public static <T> T unwrapAndTypeCheck(FileScriptValue<?> fileScriptValue, Class<T> c) throws FileScriptRuntimeException {
        if (fileScriptValue == null) {
            throw new FileScriptRuntimeException("Null value provided");
        }
        Object value = fileScriptValue.getValue();
        if (!checkObj(value, c)) {
            // type mismatch. throw an exception
            throw new FileScriptRuntimeException("Type mismatch: expected " + c.getSimpleName() + " but received " + value.getClass().getSimpleName());
        } else {
            return (T) value;
        }
    }

    // Skips type check - only use once you are confident that the types are correct
    public static <T> T unwrap(FileScriptValue<?> fileScriptValue, Class<T> c) throws FileScriptRuntimeException {
        if (fileScriptValue == null) {
            throw new FileScriptRuntimeException("Null value provided");
        }
        Object value = fileScriptValue.getValue();
        return (T) value;
    }
}
