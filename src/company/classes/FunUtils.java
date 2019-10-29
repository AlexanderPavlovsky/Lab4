package company.classes;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class function utils
 */
public class FunUtils {
    /**
     * Input data
     */
    private static final Scanner str = new Scanner(System.in);

    /**
     * Is this number int and positive?
     *
     * @return This number is int and positive
     */
    public static int getInt() {
        int num = 0;
        boolean check = false;
        do {
            try {
                String number = str.nextLine();
                num = Integer.parseInt(number);
                if (num > 0)
                    check = true;
            } catch (InputMismatchException exc) {
                System.out.println(exc.getLocalizedMessage());
            } catch (Exception exc) {
                System.out.println(exc.getLocalizedMessage());
            }
        } while (!check);
        return num;
    }


    /**
     * Is this number flight?
     *
     * @return This is number flight
     */
    public static String getNumberFlight() {
        String line;
        boolean check = false;
        do {
            line = str.nextLine();
            if (!line.matches("(([A-Z]{2}|[A-Z][0-9])-[0-9]{3,5})")) {
                System.out.println("That isn't a number flight! Example: S7-206, EK-7892");
            } else {
                check = true;
            }
        } while (!check);
        return line;
    }


    /**
     * Is this number baggage?
     *
     * @return his is number baggage
     */
    public static String getNumberBaggage() {
        String line;
        boolean check = false;
        do {
            line = str.nextLine();
            if (!line.matches("([A-Z][0-9]{7})")) {
                System.out.println("That isn't a number baggage! Example: S5896234, F1523465");
            } else {
                check = true;
            }
        } while (!check);
        return line;
    }
}
