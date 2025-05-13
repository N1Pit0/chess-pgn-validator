package services;

import services.dtos.MoveDto;
import services.parser.PgnParser;
import services.parser.SyntaxErrorTracker;
import services.threadprocessing.FileProducer;
import services.threadprocessing.FileReaderTask;
import services.threadprocessing.GameProcessorTask;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Stream;


public class PgnValidatorFacade {

    public PgnValidatorFacade() {
        String directoryPath = "plays/";

        startApplication(directoryPath);
    }

    private void startApplication(String directoryPath){
        int maxThreads = Runtime.getRuntime().availableProcessors();

        // Blocking queues for pipeline stages
        BlockingQueue<File> blockingQueue1 = new LinkedBlockingQueue<>(50); // Files queue
        BlockingQueue<String[]> blockingQueue2 = new LinkedBlockingQueue<>(200); // Games queue

        // Special marker objects (poison pills)
        File POISON_PILL_FILE = new File("POISON_PILL");
        String[] POISON_PILL_GAME = new String[]{"POISON_PILL", "POISON_PILL"};

        // Executor services for each stage
        ExecutorService fileProducerExecutor = Executors.newSingleThreadExecutor(); // Single thread for file producer
        ExecutorService fileReaderExecutor = Executors.newFixedThreadPool(maxThreads / 2); // Half threads for file reading
        ExecutorService gameProcessorExecutor = Executors.newFixedThreadPool(maxThreads / 2); // Half threads for game processing

        // Start the file producer
        fileProducerExecutor.submit(new FileProducer(blockingQueue1, directoryPath, POISON_PILL_FILE));

        // Start the file readers
        for (int i = 0; i < maxThreads / 2; i++) {
            fileReaderExecutor.submit(new FileReaderTask(blockingQueue1, blockingQueue2, POISON_PILL_FILE, POISON_PILL_GAME));
        }

        // Start the game processors
        for (int i = 0; i < maxThreads / 2; i++) {
            gameProcessorExecutor.submit(new GameProcessorTask(blockingQueue2, POISON_PILL_GAME));
        }

        // Shutdown executors gracefully
        fileProducerExecutor.shutdown();
        fileReaderExecutor.shutdown();
        gameProcessorExecutor.shutdown();
    }
}
