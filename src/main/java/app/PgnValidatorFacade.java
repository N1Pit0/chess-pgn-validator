package app;

import services.dtos.MoveDto;
import services.parser.PgnParserForSingleGame;
import services.utils.filereader.FileReaderUtil;
import services.utils.filereader.FileReaderUtilImpl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;


public class PgnValidatorFacade {

    public PgnValidatorFacade() {
        String resourcePath = "plays/";

        try {
            URI resourceUri = Objects.requireNonNull(
                    PgnValidatorFacade.class.getClassLoader().getResource(resourcePath)
            ).toURI();

            File directoryList = new File(resourceUri);
            File[] files = directoryList.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".pgn")) {
                        FileReaderUtil fileReader = new FileReaderUtilImpl(file);
                        while (!fileReader.isFileFullyRead()) {
                            String[] game = fileReader.readSingleGameFromFile();
                            PgnParserForSingleGame singleGame = new PgnParserForSingleGame();
                            List<MoveDto> moveDtoList = singleGame.parse(game[0], game[1]);
                            moveDtoList.forEach(System.out::println);
                            System.out.println("List of syntax errors");
                            singleGame.getErrorTracker().getErrors().forEach(System.out::println);
                            System.out.println("----------------------");
                        }
                    }
                }
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
