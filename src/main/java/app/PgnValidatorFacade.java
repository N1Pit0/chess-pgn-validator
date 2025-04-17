package app;

import app.service.SyntaxMatcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;


public class PgnValidatorFacade {

    public PgnValidatorFacade() {
        String resourcePath = "plays/";

        try {
            Path resourceDir = Paths.get(
                    PgnValidatorFacade.class.getClassLoader().getResource(resourcePath).toURI()
            );

            try (Stream<Path> paths = Files.walk(resourceDir)) {
                paths.filter(Files::isRegularFile)
                        .forEach(file -> {
                            try {
                                System.out.println("Processing file: " + file.toString());
                                new SyntaxMatcher(file.toString()).validatePgn();
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
        } catch (URISyntaxException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
