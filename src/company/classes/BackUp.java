package company.classes;

import company.Main;

/**
 * Class BackUp
 */
public class BackUp extends Thread {
    static private Passengers passengers;

    public static void backUp(Passengers passengers) {
        BackUp.passengers = passengers;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                if (passengers.Size() != 0)
                {
                    Main.BackUp();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
