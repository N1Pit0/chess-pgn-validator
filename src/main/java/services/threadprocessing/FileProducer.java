package services.threadprocessing;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class FileProducer implements Runnable {
    private final BlockingQueue<File> blockingQueue1;
    private final String directoryPath;
    private final File POISON_PILL_FILE;

    public FileProducer(BlockingQueue<File> blockingQueue1, String directoryPath, File POISON_PILL_FILE) {
        this.blockingQueue1 = blockingQueue1;
        this.directoryPath = directoryPath;
        this.POISON_PILL_FILE = POISON_PILL_FILE;
    }

    @Override
    public void run() {
        try {
            File directory = new File(directoryPath);
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".pgn")) {
                        blockingQueue1.put(file); // Add file to queue
                        System.out.println("File added to queue: " + file.getName());
                    }
                }
            }
            // Signal end of file production by adding the poison pill
                blockingQueue1.put(POISON_PILL_FILE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}