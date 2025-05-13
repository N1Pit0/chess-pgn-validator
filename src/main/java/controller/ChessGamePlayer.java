package controller;

import services.dtos.MoveDto;

import java.util.List;

public interface ChessGamePlayer {
    void replayGame(List<MoveDto> moveDtos);
}
