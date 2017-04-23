package court.hack.jedi.services;

import java.util.Timer;
import java.util.TimerTask;

public class ReminderWorker {

    static {
        Timer timer = new Timer();
        timer.schedule(new SayHello(), 0, 30000);
    }

    private static class SayHello extends TimerTask {
        public void run() {
            System.out.println("Hello World!");
        }
    }

}
