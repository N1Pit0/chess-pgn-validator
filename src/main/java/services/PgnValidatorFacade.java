package services;

import services.dtos.MoveDto;
import services.parser.SyntaxMatcher;

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
                                SyntaxMatcher syntaxMatcher = new SyntaxMatcher(file.toString());
                                
                                List<MoveDto> moveDtoList = syntaxMatcher.validatePgn();

                                Map<Integer, String> syntaxErrorMap = syntaxMatcher.getTagSyntaxErrorMap();
                                syntaxErrorMap.forEach((key, value) -> {
                                    System.out.println(key + ": " + value);
                                });

                                List<MoveDto> moveErrors = syntaxMatcher.getMoveSyntaxErrors();

//                                moveDtoList.forEach(System.out::println);
                                moveErrors.forEach(System.out::println);
                                
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
