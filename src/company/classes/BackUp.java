package company.classes;

import company.Main;

/**
 * Class BackUp
 */
public class BackUp extends Thread {

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                Main.BackUp();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
