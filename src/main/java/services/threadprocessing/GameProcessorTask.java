package services.threadprocessing;

import services.dtos.MoveDto;
import services.parser.PgnParser;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class GameProcessorTask implements Runnable {
    private final BlockingQueue<String[]> blockingQueue2;
    private final String[] POISON_PILL_GAME;

    public GameProcessorTask(BlockingQueue<String[]> blockingQueue2, String[] POISON_PILL_GAME) {
        this.blockingQueue2 = blockingQueue2;
        this.POISON_PILL_GAME = POISON_PILL_GAME;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String[] game = blockingQueue2.take(); // Take game from queue

                if (game == POISON_PILL_GAME) { // Check for poison pill
                    break;
                }

                // Process the game
                String tags = game[0];
                String moves = game[1];
                List<MoveDto> moveDtoList = new PgnParser().parse(tags, moves);

                moveDtoList.forEach(System.out::println);

                // Add your game processing logic here
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}