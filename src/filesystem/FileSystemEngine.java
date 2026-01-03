package filesystem;

import exceptions.FileScriptIOException;
import types.FileScriptValue;
import types.Table;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

// With a file system request (which can be created from the interface package)
// perform the file system request and get a response object back (sent to the interpreter)
public class FileSystemEngine {

    // Creates and responds with a FileSystemResponse object
    // after performing request call
    public static FileSystemResponse perform(FileSystemRequest obj) throws FileScriptIOException {
        Path filePath = Paths.get(obj.getPath());
        try {
            BasicFileAttributes attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
            Table subs = new Table();
            // get the sub files/directories if needed
            if (attributes.isDirectory()) {
                try (Stream<Path> walk = Files.walk(filePath, 1)) {
                    List<Path> subPaths = new ArrayList<>(walk.toList());
                    Collections.sort(subPaths);
                    for (Path path : subPaths) {
                        if (path != filePath) {
                            subs.push(new FileScriptValue<>(path.toString()));
                        }
                    }
                }
            }
            return new FileSystemResponse(filePath.toString(), (int) attributes.size(), attributes.lastModifiedTime().toString(), attributes.isDirectory(), subs);
        } catch (IOException e) {
            throw new FileScriptIOException("Could not read the file from the specified path");
        }
    }
}
