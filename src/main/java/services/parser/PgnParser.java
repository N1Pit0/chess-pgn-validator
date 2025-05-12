package services.parser;

import lombok.Getter;
import services.dtos.MoveDto;
import services.parser.validators.MoveValidator;
import services.parser.validators.TagValidator;
import services.utils.filereader.FileReaderUtil;
import services.utils.filereader.FileReaderUtilImpl;

import java.io.IOException;
import java.util.List;

public class PgnParser {
    private final FileReaderUtil fileReaderService;
    private final TagValidator tagValidator;
    private final MoveValidator moveValidator;

    @Getter
    private final SyntaxErrorTracker errorTracker;

    public PgnParser(String path) {
        this.fileReaderService = new FileReaderUtilImpl(path);
        this.tagValidator = new TagValidator();
        this.moveValidator = new MoveValidator();
        this.errorTracker = new SyntaxErrorTracker();
    }

    public List<MoveDto> parse() throws IOException {
        String[] sections = fileReaderService.readFile();
        String tags = sections[0];
        String moves = sections[1];

        // Validate tags
        errorTracker.getErrors().addAll(tagValidator.validateTags(tags).values());

        return moveValidator.validateMoves(moves, errorTracker.getErrors());
    }
}