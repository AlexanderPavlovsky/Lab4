package company.classes;


import java.io.*;
import java.util.ArrayList;

/**
 * Class Passengers
 */
public class Passengers {
    /**
     * Array Passenger
     */
    private ArrayList<Passenger> passengers;

    /**
     * Construct Passengers
     *
     * @param length Quantity of passengers
     */
    public Passengers(final int length) {
        this.passengers = new ArrayList<>(length);
    }

    /**
     * Push back to array passengers
     *
     * @param passenger Object Passenger
     */
    public void pushBack(final Passenger passenger) {
        this.passengers.add(passenger);
    }

    /**
     * Passengers have all mass of baggage over 30
     *
     * @return get passenger have mass over 30
     */
    public String massOver30() {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).getAllMassOfBaggage() > 30) {
                result.append(i + 1).append(") ").append(passengers.get(i).toString()).append("\n");
            }
        }
        return result.toString();
    }

    /**
     * All mass of baggage of everybody passenger
     *
     * @return get all mass of baggage of everybody passenger
     */
    public String allMassOfBaggage() {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < passengers.size(); i++) {
            result.append(i + 1).append(") Passenger ").append(passengers.get(i).getAllMassOfBaggage()).append("\n");
        }
        return result.toString();
    }

    /**
     * Location of baggage (number flight)
     *
     * @param numberBaggage number baggage
     * @return number flight
     */
    public String locationOfBaggage(final String numberBaggage) {
        String result = "";
        for (final Passenger passenger : passengers) {
            if (passenger.getNumberBaggage().equals(numberBaggage)) {
                result = "Number Flight: " + passenger.getNumberFlight();
            } else {
                result = "Number of baggage doesn't exist";
            }
        }
        return result;
    }

    /**
     * Remove passenger by last name
     *
     * @param lastName Last name
     * @return remove passenger
     */
    public Passenger removeByLastName(final String lastName) {
        int numberOfPassenger;
        Passenger passenger = null;
        for (numberOfPassenger = 0; numberOfPassenger < passengers.size(); numberOfPassenger++) {
            if (passengers.get(numberOfPassenger).getLastName().equals(lastName)) {
                passenger = passengers.get(numberOfPassenger);
                passengers.remove(numberOfPassenger);
                break;
            }
        }
        return passenger;
    }

    public void Save() {
        if (passengers.size() != 0) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("passengers.txt");
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                out.writeObject(passengers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void Load() {
        try {
            FileInputStream fileInputStream = new FileInputStream("passengers.txt");
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            this.passengers = (ArrayList<Passenger>)in.readObject();
            in.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void BackUp() {
        if (passengers.size() != 0) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("backup/BKpassengers.txt");
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                out.writeObject(passengers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        if (passengers.size() != 0) {
            for (int i = 0; i < passengers.size(); i++) {
                result.append(i + 1).append(") ").append(passengers.get(i).toString()).append("\n");
            }
        } else {
            result.append("Passengers doesn't exist");
        }
        return result.toString();
    }

}
