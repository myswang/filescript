package interpreter;

import dispatch.FunctionDispatch;
import exceptions.FileScriptException;
import filesystem.FileSystemEngine;
import filesystem.FileSystemRequest;
import filesystem.FileSystemResponse;
import types.FileScriptValue;
import types.Table;

import java.util.List;

// Creates built-in functions and registers
// them to the dispatcher
public class StandardLibrary {
    public StandardLibrary() {
        throw new UnsupportedOperationException("Cannot initialize standard library class (only static methods permitted)");
    }

    public static void registerAll(FunctionDispatch dispatcher) {
        dispatcher.register("print", StandardLibrary::print);
        dispatcher.register("test", StandardLibrary::test);
        dispatcher.register("open", StandardLibrary::open);
        dispatcher.register("len", StandardLibrary::len);
        dispatcher.register("push", StandardLibrary::push);
        dispatcher.register("pop", StandardLibrary::pop);
    }

    private static FileScriptValue<?> print(List<FileScriptValue<?>> args) {
        // Supports printing multiple arguments, each separated by a space
        for (FileScriptValue<?> arg : args) {
            System.out.print(arg.getValue() + " ");
        }
        System.out.println();
        // null return type for "void" functions
        // the context is such that every function returns a ValueNode type
        // regardless
        return new FileScriptValue<>(null);
    }

    // Usage: open(path) -> list(file)
    // TODO: make error handling more robust
    private static FileScriptValue<?> open(List<FileScriptValue<?>> args) throws FileScriptException {
        String filePath = (String) args.get(0).getValue();
        FileSystemResponse response = FileSystemEngine.perform(new FileSystemRequest(filePath));
        Table table = new Table();
        table.put(new FileScriptValue<>("name"), new FileScriptValue<>(response.name));
        table.put(new FileScriptValue<>("size"), new FileScriptValue<>(response.size));
        table.put(new FileScriptValue<>("last_modified"), new FileScriptValue<>(response.lastModified));
        table.put(new FileScriptValue<>("is_dir"), new FileScriptValue<>(response.isDirectory));
        table.put(new FileScriptValue<>("subs"), new FileScriptValue<>(response.subs));

        return new FileScriptValue<>(table);
    }

    // Usage: len(arr) -> int
    private static FileScriptValue<?> len(List<FileScriptValue<?>> args) {
        Table table = (Table) args.get(0).getValue();
        return table.len();
    }

    // Usage: push(arr, val)
    private static FileScriptValue<?> push(List<FileScriptValue<?>> args) {
        Table table = (Table) args.get(0).getValue();
        Object val = args.get(1).getValue();
        table.push(new FileScriptValue<>(val));
        return new FileScriptValue<>(null);
    }

    // Usage: pop(arr) -> val
    private static FileScriptValue<?> pop(List<FileScriptValue<?>> args) {
        Table table = (Table) args.get(0).getValue();
        return table.pop();
    }

    // for testing purposes
    private static FileScriptValue<?> test(List<FileScriptValue<?>> args) {
        System.out.println("Test function is working");
        return new FileScriptValue<>(null);
    }
}
