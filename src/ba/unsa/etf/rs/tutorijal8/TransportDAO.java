package ba.unsa.etf.rs.tutorijal8;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransportDAO {
    private static TransportDAO instance = null;
    private Connection conn;
    private PreparedStatement addDriverStatement, latestDriverId,
            deleteDriverStatement, deleteBusStatement, addBusStatement, latestBusId;



    public static TransportDAO getInstance () {
        if (instance == null) {
            instance = new TransportDAO();
        }
        return instance;
    }

    private TransportDAO() {
        //TODO
        try {
            conn = DriverManager.getConnection("\"jdbc:sqlite:proba.db\"");
            Class.forName("org.sqlite.JDBC");
            latestDriverId = conn.prepareStatement("SELECT max(id) FROM drivers");
            latestBusId = conn.prepareStatement("SELECT max(id) FROM buses");
            addDriverStatement = conn.prepareStatement("INSERT INTO drivers VALUES(?,?,?,?,?,?)");
            addBusStatement = conn.prepareStatement("INSERT INTO buses VALUES(?, ?, ?, ?)");
            deleteDriverStatement = conn.prepareStatement("DELETE FROM Drivers WHERE id = ?");
            deleteBusStatement = conn.prepareStatement("DELETE FROM buses WHERE id = ?");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void removeInsance() {
        if (instance != null) {
            try {
                instance.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }

    // TODO

    public ArrayList<Bus>  getBusses() {

    }

    public ArrayList<Driver> getDrivers() {

    }


    public void addDriver(String name, String surname, int jmb, LocalDate dateOfBirth, LocalDate hireDate) {
        try {
            ResultSet result = latestDriverId.executeQuery();
            result.next();
            addDriverStatement.setInt(1, result.getInt(1));
            addDriverStatement.setString(2, name);
            addDriverStatement.setString(3, surname);
            addDriverStatement.setInt(4, jmb);
            addDriverStatement.setDate(5, Date.valueOf(dateOfBirth));
            addDriverStatement.setDate(6, Date.valueOf(hireDate));
            addDriverStatement.executeUpdate();

            /*
            SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
            String paramDateAsString = "2007-12-25";
            Date myDate = null;

            myDate = textFormat.parse(paramDateAsString);
             */

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDriver(Driver driver) {
        try {
            deleteDriverStatement.setInt(1, driver.getId());
            deleteDriverStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBus(Bus bus) {
        try {
            ResultSet result = latestBusId.executeQuery();
            result.next();
            addBusStatement.setInt(1, result.getInt(1));
            addBusStatement.setString(2, bus.getMaker());
            addBusStatement.setString(3, bus.getSeries());
            addBusStatement.setInt(4, bus.getSeatNumber());
            addBusStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void deleteBus(Bus bus) {
    }

    public void dodijeliVozacuAutobus(Driver driver, Bus bus, int which) {
    }

    public void addDriver(Driver driver) {
    }

    public void resetDatabase() {
    }
}
