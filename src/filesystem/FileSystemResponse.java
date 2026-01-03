package filesystem;

import helpers.SizeConversions;
import types.Table;

// Response object from a file system object
public class FileSystemResponse {
    public String name;
    // NOTE: Size is in bytes unless listed otherwise
    public int size;
    public String lastModified;
    public boolean isDirectory;
    public Table subs;

    public FileSystemResponse(String name, int size, String lastModified, boolean isDirectory, Table subs) {
        if (size < 0) {
            throw new IllegalArgumentException("File size cannot be negative");
        }
        this.name = name;
        this.size = size;
        this.lastModified = lastModified;
        this.isDirectory = isDirectory;
        this.subs = subs;
    }

    public long getSizeInBytes() {
        return size;
    }

    public double getSizeInKB() {
        return SizeConversions.bytesToKB(size);
    }

    public double getSizeInMB() {
        return SizeConversions.bytesToMB(size);
    }
}
