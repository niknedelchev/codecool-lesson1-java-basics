package org.nn;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class VehicleDataPoint {
    public static final int DISTANCE = 10;
    public static final double HOUR_IN_MILIS = 3600000.00;

    private String plateNumber;
    private LocalTime entryTime;
    private LocalTime exitTime;

    public VehicleDataPoint(String dataLine) {

        String[] lineArr = dataLine.split(" ");

        this.plateNumber = lineArr[0];
        this.entryTime = getTime(1, lineArr);
        this.exitTime = getTime(5, lineArr);
    }

    private LocalTime getTime(int start, String[] lineArr) {
        int hour = Integer.parseInt(lineArr[start]);
        int minute = Integer.parseInt(lineArr[++start]);
        int second = Integer.parseInt(lineArr[++start]);
        int nanoSecond = Integer.parseInt(lineArr[++start]);

        return LocalTime.of(hour, minute, second, nanoSecond);
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public LocalTime getEntryTime() {
        return entryTime;
    }

    public LocalTime getExitTime() {
        return exitTime;
    }

    public int getAverageSpeed() {
        double elapsedTime = ChronoUnit.MILLIS.between(entryTime, exitTime) / HOUR_IN_MILIS;
        return (int) (DISTANCE / elapsedTime);
    }
}
