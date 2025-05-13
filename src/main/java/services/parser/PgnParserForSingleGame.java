package services.parser;

import lombok.Getter;
import services.dtos.MoveDto;
import services.parser.validators.MoveValidator;
import services.parser.validators.TagValidator;

import java.io.IOException;
import java.util.List;

public class PgnParserForSingleGame {
    private final TagValidator tagValidator;
    private final MoveValidator moveValidator;

    @Getter
    private final SyntaxErrorTracker errorTracker;

    public PgnParserForSingleGame() throws IOException {
        this.tagValidator = new TagValidator();
        this.moveValidator = new MoveValidator();
        this.errorTracker = new SyntaxErrorTracker();
    }

    public List<MoveDto> parse(String tags, String moves) throws IOException {

        // Validate tags
        errorTracker.getErrors().addAll(tagValidator.validateTags(tags).values());

        return moveValidator.validateMoves(moves, errorTracker.getErrors());
    }
}