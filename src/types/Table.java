package types;

import exceptions.FileScriptRuntimeException;

import java.util.HashMap;

// Associative array class, loosely inspired by Lua tables
// There are two tables to handle string and integer keys respectively

public class Table {
    private final HashMap<String, FileScriptValue<?>> stringTable = new HashMap<>();
    private final HashMap<Integer, FileScriptValue<?>> integerTable = new HashMap<>();
    // if the table is being used as an array, stores the current table index
    private int intTableIndex = 0;

    public void put(FileScriptValue<?> key, FileScriptValue<?> val) throws FileScriptRuntimeException {
        Object keyObj = key.getValue();
        if (keyObj instanceof String) {
            stringTable.put((String) keyObj, val);
        } else if (keyObj instanceof Integer) {
            integerTable.put((Integer) keyObj, val);
        } else {
            throw new FileScriptRuntimeException("Table key must be of type String or Integer");
        }
    }

    // Search for the next available slot in the hashmap, incrementing the current table index
    // When a free slot is found, insert the value with the current table index as its key.
    public void push(FileScriptValue<?> val) {
        while (integerTable.get(intTableIndex) != null) {
            intTableIndex++;
        }
        integerTable.put(intTableIndex, val);
    }

    // Removes the value at the end of the integer table
    public FileScriptValue<?> pop() {
        FileScriptValue<?> val = integerTable.remove(intTableIndex);
        intTableIndex--;
        return val;
    }

    public FileScriptValue<?> get(FileScriptValue<?> key) throws FileScriptRuntimeException {
        Object keyObj = key.getValue();
        if (keyObj instanceof String) {
            return stringTable.get((String) keyObj);
        } else if (keyObj instanceof Integer) {
            return integerTable.get((Integer) keyObj);
        } else {
            throw new FileScriptRuntimeException("Table key must be of type String or Integer");
        }
    }

    public FileScriptValue<Integer> len() {
        return new FileScriptValue<>(stringTable.size() + integerTable.size());
    }
}
