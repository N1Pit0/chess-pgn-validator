package services;

import services.dtos.MoveDto;
import services.parser.PgnParser;
import services.parser.SyntaxErrorTracker;
import services.utils.filereader.FileReaderUtil;
import services.utils.filereader.FileReaderUtilImpl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;


public class PgnValidatorFacade {

    private final Semaphore semaphore;
    private final ExecutorService executorService;
    private FileReaderUtil fileReaderUtil;

    public PgnValidatorFacade(int maxTasks, int maxThreads) {
        this.semaphore = new Semaphore(maxTasks);
        this.executorService = Executors.newFixedThreadPool(maxThreads);
        String resourcePath = "plays/";

        validatePgn(resourcePath);

    }

    public void validatePgn(String resourcePath){

        try {
            URI resourceUri = Objects.requireNonNull(
                    PgnValidatorFacade.class.getClassLoader().getResource(resourcePath)
            ).toURI();

            File directoryList = new File(resourceUri);
            File[] files = directoryList.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".pgn")) {
                        try {
                            semaphore.acquire(); // Acquire a permit before submitting a task
                            executorService.submit(() -> {
                                try {
                                    try {
                                        fileReaderUtil = new FileReaderUtilImpl(file);
                                        processFile(executorService, semaphore);
                                    } catch (InterruptedException | IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                } finally {
                                    semaphore.release(); // Release the permit after task completion
                                }
                            });
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }


        } catch (URISyntaxException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void processFile(ExecutorService executorService,
                             Semaphore semaphore) throws InterruptedException, IOException {

        while(!fileReaderUtil.isFileFullyRead()){
            String[] sections = fileReaderUtil.readSingleGameFromFile();

            semaphore.acquire();
            executorService.submit(()->{
                try {
                    try {
                        PgnParser pgnParser = new PgnParser();
                        List<MoveDto> moveDtoList = pgnParser.parse(sections[0], sections[1]);
                        moveDtoList.forEach(System.out::println);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }finally {
                    semaphore.release();
                }
            });
        }
    }
}
