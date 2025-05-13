import services.PgnValidatorFacade;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    public static void main(String[] args) {
        ExecutorService executorService = null;

        executorService = Executors.newFixedThreadPool(6);
        new PgnValidatorFacade("plays/", executorService, 100);

    }
}
