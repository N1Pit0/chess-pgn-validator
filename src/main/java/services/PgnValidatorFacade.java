package services;

import services.dtos.MoveDto;
import services.parser.PgnParser;
import services.utils.filereader.FileReaderUtil;
import services.utils.filereader.FileReaderUtilImpl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;


public class PgnValidatorFacade {

    private final Semaphore semaphore;
    private final ExecutorService executorService;
    private final String resourcePath;
    private final AtomicInteger activeTaskCounter; // Dynamic task tracker

    public PgnValidatorFacade(String resourcePath, ExecutorService executorService, int maxTasks) {
        this.semaphore = new Semaphore(maxTasks);
        this.executorService = executorService;
        this.resourcePath = resourcePath;
        this.activeTaskCounter = new AtomicInteger(0); // Initialize counter to 0

        validatePgn();
    }

    private void validatePgn() {
        try {
            URI resourceUri = Objects.requireNonNull(
                    PgnValidatorFacade.class.getClassLoader().getResource(resourcePath)
            ).toURI();

            File directoryList = new File(resourceUri);
            File[] files = directoryList.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".pgn")) {
                        semaphore.acquireUninterruptibly();
                        activeTaskCounter.incrementAndGet();
                        executorService.submit(()-> {
                            FileReaderUtil fileReaderUtil = null;
                            try {
                                fileReaderUtil = new FileReaderUtilImpl(file);
                                processFile(executorService, fileReaderUtil);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }finally {
                                semaphore.release();
                                activeTaskCounter.decrementAndGet();
                            }

                        });
                    }
                }
            }
        } catch (URISyntaxException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            waitForCompletion(); // Wait for all tasks to finish
            executorService.shutdown(); // Shutdown the ExecutorService
        }

    }

    private void processFile(ExecutorService executorService, FileReaderUtil fileReaderUtil) {
        try {
            while (!fileReaderUtil.isFileFullyRead()) {
                String[] sections = fileReaderUtil.readSingleGameFromFile();
                semaphore.acquire();
                activeTaskCounter.incrementAndGet(); // Increment active task counter
                executorService.submit(() -> {
                    try {
                        System.out.println("------------------------");
                        System.out.println("New Game");
                        System.out.println("------------------------");
                        PgnParser pgnParser = new PgnParser();
                        List<MoveDto> moveDtoList = pgnParser.parse(sections[0], sections[1]);
                        moveDtoList.forEach(System.out::println);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                        activeTaskCounter.decrementAndGet(); // Decrement active task counter
                    }
                });
            }
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private void waitForCompletion() {
        try {
            while (activeTaskCounter.get() > 0) { // Wait until all tasks are completed
                Thread.sleep(100); // Sleep for a short duration to avoid busy waiting
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
