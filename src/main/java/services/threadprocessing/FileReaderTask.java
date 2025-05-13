package services.threadprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;

public class FileReaderTask implements Runnable {
    private final BlockingQueue<File> blockingQueue1;
    private final BlockingQueue<String[]> blockingQueue2;
    private final File POISON_PILL_FILE;
    private final String[] POISON_PILL_GAME;

    public FileReaderTask(BlockingQueue<File> blockingQueue1, BlockingQueue<String[]> blockingQueue2, File POISON_PILL_FILE, String[] POISON_PILL_GAME) {
        this.blockingQueue1 = blockingQueue1;
        this.blockingQueue2 = blockingQueue2;
        this.POISON_PILL_FILE = POISON_PILL_FILE;
        this.POISON_PILL_GAME = POISON_PILL_GAME;
    }

    @Override
    public void run() {
        try {
            while (true) {
                File file = blockingQueue1.take(); // Take file from queue

                if (file.equals(POISON_PILL_FILE)) { // Check for poison pill
                    blockingQueue2.put(POISON_PILL_GAME); // Pass poison pill to the next stage
                    break;
                }

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder tags = new StringBuilder();
                    StringBuilder moves = new StringBuilder();
                    String line;
                    boolean isMoveSection = false;

                    while ((line = reader.readLine()) != null) {
                        line = line.trim();

                        if (isMoveSection && line.isEmpty()) {
                            // Add the game to the games queue
                            blockingQueue2.put(new String[]{tags.toString(), moves.toString()});
                            tags.setLength(0);
                            moves.setLength(0);
                            isMoveSection = false;
                            continue;
                        }

                        if (line.isEmpty()) {
                            isMoveSection = true;
                        } else if (isMoveSection) {
                            moves.append(line).append(" ");
                        } else {
                            tags.append(line).append("\n");
                        }
                    }

                    // Add the last game in the file
                    if (!tags.isEmpty() || !moves.isEmpty()) {
                        blockingQueue2.put(new String[]{tags.toString(), moves.toString()});
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}