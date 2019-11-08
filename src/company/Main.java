package company;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import company.classes.BackUp;
import company.classes.Passenger;
import company.classes.Passengers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;

import static company.classes.FunUtils.*;


/**
 * Class Main
 */
public class Main {
    /**
     * Object Passengers
     */
    private static Passengers passengers;

    /**
     * Main function
     */
    public static void main(final String[] args) {
        final Scanner str = new Scanner(System.in);
        int menu, length;
        boolean exit = false;
        passengers = new Passengers();
        final BackUp backUp = new BackUp();
        backUp.start();
        while (!exit) {
            System.out.println("1) Add passenger\n2) Show all passengers\n3) Show all passengers` sum mass of baggage\n" +
                    "4) Location of baggage\n5) Remove passenger by last name\n6) Save data about passengers\n" +
                    "7) Load data about passengers\n8) Load backup data about passengers\n9) Exit");
            menu = getInt();
            switch (menu) {
                case 1:
                    System.out.print("Enter quantity of add passengers: ");
                    length = getInt();
                    System.out.println("Enter the information of all the add passengers: ");
                    createPassenger(passengers, str, length);
                    System.out.println(passengers.massOver30());
                    break;
                case 2:
                    System.out.println(passengers.toString());
                    break;
                case 3:
                    System.out.println(passengers.allMassOfBaggage());
                    break;
                case 4:
                    System.out.print("Enter number baggage: ");
                    System.out.println(passengers.locationOfBaggage(str.next()));
                    break;
                case 5:
                    System.out.print("Enter last name: ");
                    System.out.println("\n" + passengers.removeByLastName(str.next()) + "\n");
                    break;
                case 6:
                    save();
                    break;
                case 7:
                    load();
                    break;
                case 8:
                    loadBackUp();
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Error!!!");
                    break;
            }
        }
    }

    private static void createPassenger(final Passengers passengers, final Scanner str, final int length) {
        String name, lastName, patronymic, numberFlight, numberBaggage;
        int quantityPlace, sumMassOfBaggage = 0, numberLevel, numberLevelBaggage;
        LevelOfPlace levelOfPlace;
        LevelOfBaggage levelOfBaggage;
        for (int i = 0; i < length; i++) {
            System.out.println((i + 1) + ") Passenger: ");
            System.out.print("Name: ");
            name = str.next();
            System.out.print("Last name: ");
            lastName = str.next();
            System.out.print("Patronymic: ");
            patronymic = str.next();
            System.out.print("Number flight: ");
            numberFlight = getNumberFlight();
            System.out.println("Level of place:\n1) First class\n2) Business class\n3)Eco class");
            numberLevel = getInt();
            switch (numberLevel) {
                case 1:
                    levelOfPlace = LevelOfPlace.FIRST;
                    break;
                case 2:
                    levelOfPlace = LevelOfPlace.BUSINESS;
                    break;
                case 3:
                    levelOfPlace = LevelOfPlace.ECONOMY;
                    break;
                default:
                    System.out.println("Error!!! Your class is eco class");
                    levelOfPlace = LevelOfPlace.ECONOMY;
                    break;
            }
            System.out.println(levelOfPlace.toString());
            System.out.print("Number baggage: ");
            numberBaggage = getNumberBaggage();
            System.out.println("Level of baggage:\n1) Hand luggage\n2) In luggage");
            numberLevelBaggage = getInt();
            switch (numberLevelBaggage) {
                case 1:
                    levelOfBaggage = LevelOfBaggage.HANDLUGGAGE;
                    break;
                case 2:
                    levelOfBaggage = LevelOfBaggage.INLUGGAGE;
                    break;
                default:
                    System.out.println("Error!!! Your level of baggage is In luggage");
                    levelOfBaggage = LevelOfBaggage.INLUGGAGE;
                    break;
            }
            System.out.print("Quantity place: ");
            quantityPlace = getInt();
            for (int j = 0; j < quantityPlace; j++) {
                System.out.print((j + 1) + ") Mass of baggage: ");
                sumMassOfBaggage += getInt();
            }
            passengers.pushBack(new Passenger(name, lastName, patronymic, numberFlight, levelOfPlace.toString(), numberBaggage, levelOfBaggage.toString(), quantityPlace, sumMassOfBaggage));
        }
    }

    /**
     * Enum Level of place
     */
    enum LevelOfPlace {
        FIRST,
        BUSINESS,
        ECONOMY
    }

    /**
     * Enum Level of baggage
     */
    enum LevelOfBaggage {
        HANDLUGGAGE,
        INLUGGAGE
    }

    private static void save() {
        if (passengers.Size() != 0) {
            try {
                final ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                objectMapper.writeValue(new File("passengers.json"), passengers);
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
    }

    private static void load() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            passengers = objectMapper.readValue(new File("passengers.json"), Passengers.class);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public static void BackUp() {
        final String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(Calendar.getInstance().getTime());
        if (passengers.Size() != 0) {
            System.out.println("===BackUp===");
            try {
                final ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                if (Objects.requireNonNull(new File("backup").listFiles()).length > 2) {
                    final File[] name = Objects.requireNonNull(new File("backup").listFiles());
                    final boolean delete = name[0].delete();
                    if (delete) {
                        objectMapper.writeValue(new File("backup/BackUp " + timeStamp + ".json"), passengers);
                    }
                } else {
                    objectMapper.writeValue(new File("backup/BackUp " + timeStamp + ".json"), passengers);
                }
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
    }

    private static void loadBackUp() {
        final File[] name = Objects.requireNonNull(new File("backup").listFiles());
        System.out.println("Select backup:");
        for (int i = 0; i < name.length; i++) {
            System.out.println((i + 1) + ") " + name[i].toString());
        }
        try {

            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            passengers = objectMapper.readValue(new File(name[getInt()].toString()), Passengers.class);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
