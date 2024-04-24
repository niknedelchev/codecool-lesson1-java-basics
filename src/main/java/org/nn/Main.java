package org.nn;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalTime;

public class Main {


    public static void main(String[] args) {

        Path filePath = new File("src/main/resources/measurements.txt").toPath();

        //Task 1: Read and store the data of file measurements.txt.
        DataHandler dataHandler = new DataHandler(filePath);

        //Task 2: Display the number of vehicles whose data were recorded in the measurement on the screen.
        dataHandler.displayRecordedVehicleNumber();

        //Task 3: From the available data, determine the number of vehicles that passed the exit point
        dataHandler.vehiclePassedBeforeNineOClock();

        //Task 4: Request a time given in hour minute form from the user.
        LocalTime userTime = dataHandler.getHourMinuteInputFromUser();

        //Task 4.a: Determine the number of vehicles that passed the entry point in the given minute.
        dataHandler.displayNumberOfVehiclesThatPassedEntryPoint(userTime);

        // Task 4.b: Determine the traffic intensity
        dataHandler.displayTraficIntensity(userTime);

        //Task 5: Find the speed of the vehicle with the highest average speed and the number of vehicles overtaken by it.
        dataHandler.displayDataOfVehicleWithHighestSpeed();

        //Task 6: Determine what percent of the vehicles exceeded the maximum speed limit on the measured section (90 km/h) with their average speed. Display the result in decimal number format according to the example.
        dataHandler.displayPercentOfVehiclesThatWereSpeeding();

    }


}
