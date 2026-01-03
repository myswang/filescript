package helpers;

public class SizeConversions {
    private SizeConversions() {
        throw new UnsupportedOperationException("Utility class, do not instantiate");
    }

    // Bytes -> Kilobytes
    public static double bytesToKB(long bytes) {
        return bytes / 1024.0;
    }

    // Bytes -> Megabytes
    public static double bytesToMB(long bytes) {
        return bytes / (1024.0 * 1024);
    }
}
