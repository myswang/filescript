This folder contains an implementation for different exceptions that can be returned directly as the output of FileScript. 

The error messages can be defined specifically for each exception as the output of the program and we can tailor the different types of errors.

Structure: 
 
FileScriptException.java -> Base exception class

FileScriptIOException -> I/O errors with file system

FileScriptParseException -> For parsing errors

FileScriptRuntimeException -> dynamic type checking and general runtime issues

Part of the idea behind this would to clearly separate between an exception that internal to our implementation of the interpreter and an exception that is the result of an invalid FileScript program. Anything labelled as an exception with `FileScript` labelled in the name should be communicated with the user and isn't an internal implementation error (like other exceptions may be)