package interpreter;

import dispatch.FunctionDispatch;
import exceptions.FileScriptException;
import exceptions.FileScriptRuntimeException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import types.FileScriptValue;
import types.Table;

// Context/state of the program as it accumulates
// Keeps track of the runtime environment of the program
// "ProgramState" is equivalent to "Context" in the context of an
// accumulator for the AST Evaluator

public class ProgramState {
    Map<String, FileScriptValue<?>> symbolTable;
    FunctionDispatch dispatch;

    public ProgramState() {
        this.symbolTable = new HashMap<>();
        this.dispatch = new FunctionDispatch();
        // Register the standard library API functions to the method dispatch
        StandardLibrary.registerAll(dispatch);
    }

    // Runs a new function using the dispatcher
    public FileScriptValue<?> runFunction(String name, List<FileScriptValue<?>> args) throws FileScriptException {
        return dispatch.dispatch(name, args);
    }

    public void setVariable(String name, FileScriptValue<?> value) {
        symbolTable.put(name, value);
    }

    public void setVariableArrayIndex(String name, FileScriptValue<?> key, FileScriptValue<?> value) throws FileScriptRuntimeException {
        FileScriptValue<?> array = symbolTable.get(name);
        Table table = TypeChecker.unwrapAndTypeCheck(array, Table.class);
        table.put(key, value);
        symbolTable.put(name, new FileScriptValue<>(table));
    }

    public FileScriptValue<?> getVariable(String name) throws FileScriptRuntimeException {
        if (!symbolTable.containsKey(name)) {
            throw new FileScriptRuntimeException("Variable " + name + " does not exist");
        }
        return symbolTable.get(name);
    }

    public FileScriptValue<?> getVariableArrayIndex(String name, FileScriptValue<?> index) throws FileScriptRuntimeException {
        FileScriptValue<?> array = symbolTable.get(name);
        Table table = TypeChecker.unwrapAndTypeCheck(array, Table.class);
        return table.get(index);
    }
}
