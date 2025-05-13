package services.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class MoveDto {
    private int moveNumber;
    private String whiteMove;
    private String blackMove;
    private String winner;
}