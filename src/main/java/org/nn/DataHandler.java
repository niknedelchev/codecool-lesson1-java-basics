package org.nn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.nn.VehicleDataPoint.DISTANCE;

public class DataHandler {
    public static final int VALID_ROW_DATA_PIECES_COUNT = 9;
    private Path filePath;
    private List<VehicleDataPoint> dataPoints;

    public DataHandler(Path filePath) {
        this.filePath = filePath;
        dataPoints = getDataPoints(filePath);
    }

    /**
     * Task 1: Read and store the data of file measurements.txt.
     */
    private List<VehicleDataPoint> getDataPoints(Path filePath) {
        try {
            return Files.lines(filePath)
                    .filter(line -> line.split(" ").length == VALID_ROW_DATA_PIECES_COUNT)
                    .map(VehicleDataPoint::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Task 2: Display the number of vehicles whose data were recorded in the measurement on the screen.
     */
    public void displayRecordedVehicleNumber() {
        System.out.println(String.format("Excercise 2.\nThe data of %d vehicles were recorded in the measurement.\n", dataPoints.size()));
    }

    /**
     * Task 3: From the available data, determine the number of vehicles that passed the exit point of the section
     */
    public void vehiclePassedBeforeNineOClock() {
        long count = dataPoints.stream()
                .filter(dataPoint -> dataPoint.getExitTime()
                        .isBefore(LocalTime.of(9, 0))).count();

        System.out.println(String.format("Excercise 3.\nBefore 9 o'clock %d vehicles passed the exit point recorder.\n", count));
    }

    /**
     * Task 4: Request a time given in hour minute form from the user.
     */
    public LocalTime getHourMinuteInputFromUser() {
        System.out.println("Excercise 4.");
        Scanner scanner = new Scanner(System.in);
        String hourMin = "";

        do {
            System.out.println("Enter an hour and minute value:");
            hourMin = scanner.nextLine().trim();
        } while (!hourMin.matches("\\d+\\s\\d+"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H m");
        return LocalTime.parse(hourMin, formatter);
    }

    /**
     * Task 4.a: Determine the number of vehicles that passed the entry point in the given minute.
     * If in the given minute no vehicle passed the entry point, then display value 0.
     */
    public void displayNumberOfVehiclesThatPassedEntryPoint(LocalTime time) {
        long count = dataPoints.stream()
                .filter(dataPoint -> dataPoint.getEntryTime().getHour() == time.getHour()
                        && dataPoint.getEntryTime().getMinute() == time.getMinute())
                .count();
        System.out.println(String.format("a. The number of vehicle that passed the entry point recorder: %d", count));
    }

    /**
     * Task 4.b: Determine the traffic intensity, which is the number of vehicle on the road section in the minute that starts at the given minute
     * (e.g. if the given hour minute is 08:09, then between 08:09:00.000-08:09:59.999) divided by the length of the road section.
     * Display the value in decimal fraction format.
     */
    public void displayTraficIntensity(LocalTime time) {
        double trafficIntensity = (double) (dataPoints.stream()
                .filter(dataPoint -> dataPoint.getExitTime().isAfter(time)
                        && dataPoint.getEntryTime().isBefore(time.plusMinutes(1))))
                .count() / DISTANCE;
        System.out.println("b. The traffic intensity: " + String.format("%.2f", trafficIntensity) + "\n");
    }

    /**
     * Task 5: Find the speed of the vehicle with the highest average speed and the number of vehicles overtaken by it in the measured section.
     * If there are several highest average speeds, it is enough to display only one of them.
     * Display the license plate number of the vehicle, the average speed as an integer and the number of overtaken vehicles.
     */
    public void displayDataOfVehicleWithHighestSpeed() {
        System.out.println("Excercise 5.");

        VehicleDataPoint vehicle = dataPoints.stream()
                .sorted(Comparator.comparing(VehicleDataPoint::getAverageSpeed).reversed())
                .findFirst()
                .get();
        int highestAverageSpeed = vehicle.getAverageSpeed();
        long overpassedVehicles = dataPoints.stream()
                .filter(dataPoint -> dataPoint.getEntryTime().isBefore(vehicle.getEntryTime())
                        && dataPoint.getExitTime().isAfter(vehicle.getExitTime()))
                .count();

        System.out.println(String.format("""
                The data of the vehicle with the highest speed are
                license plate number: %s
                average speed: %d km/h
                number of overtaken vehicles: %d
                                """, vehicle.getPlateNumber(), highestAverageSpeed, overpassedVehicles));
    }

    public void displayPercentOfVehiclesThatWereSpeeding() {
        System.out.println("Excercise 6.");
        double percentSpeedingVehicles = (double) dataPoints.stream()
                .filter(vehicleDataPoint -> vehicleDataPoint.getAverageSpeed() >= 90)
                .count() / dataPoints.size() * 100;

        System.out.println(String.format("%.2f", percentSpeedingVehicles) + "% percent of the vehicles were speeding.");
    }

}
