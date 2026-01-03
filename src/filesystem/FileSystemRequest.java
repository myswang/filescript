package filesystem;

// Request object that can be used to define an operation that should go to the file system
// (coming from the interpreter)
public class FileSystemRequest {
    private final String filePath;

    public FileSystemRequest(String filePath) {
        this.filePath = filePath;
    }

    public String getPath() {
        return filePath;
    }
}
