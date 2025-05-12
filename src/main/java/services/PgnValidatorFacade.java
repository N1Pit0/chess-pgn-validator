package services;

import services.dtos.MoveDto;
import services.parser.PgnParser;
import services.parser.SyntaxErrorTracker;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;


public class PgnValidatorFacade {

    public PgnValidatorFacade() {
        String resourcePath = "plays/";

        try {
            Path resourceDir = Paths.get(
                    Objects.requireNonNull(PgnValidatorFacade.class.getClassLoader().getResource(resourcePath)).toURI()
            );

            try (Stream<Path> paths = Files.walk(resourceDir)) {
                paths.filter(Files::isRegularFile)
                        .forEach(file -> {
                            try {
                                PgnParser pgnParser = new PgnParser(file.toString());

                                List<MoveDto> moveDtoList = pgnParser.parse();

                                SyntaxErrorTracker syntaxErrors = pgnParser.getErrorTracker();

                                moveDtoList.forEach(System.out::println);

                                if (syntaxErrors.hasErrors()) {
                                    System.out.println("Syntax errors found:");
                                    syntaxErrors.getErrors().forEach(System.out::println);
                                }

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
        } catch (URISyntaxException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
