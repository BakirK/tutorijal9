package ba.unsa.etf.rs.tutorijal8;

import org.sqlite.JDBC;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransportDAO {
    private static TransportDAO instance = null;
    private Connection conn;
    private PreparedStatement addDriverStatement, latestDriverId,
            deleteDriverStatement, deleteBusStatement, addBusStatement, latestBusId, getBusesStatement,
            getDodjelaVozaci, getDriversStatement, deleteDodjelaBus, deleteDodjelaDriver, truncateDB,
            resetAutoIncrement, dodijeliVozacuAutobusStatement;



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
            //DriverManager.registerDriver(new JDBC());
            latestDriverId = conn.prepareStatement("SELECT max(id) + 1 FROM drivers");
            latestBusId = conn.prepareStatement("SELECT max(id) + 1 FROM buses");
            addDriverStatement = conn.prepareStatement("INSERT INTO drivers(id, name, surname, jmb, birth, hire_date)" +
                    " VALUES(?,?,?,?,?,?)");
            addBusStatement = conn.prepareStatement("INSERT INTO buses(id, proizvodjac, serija, broj_sjedista)" +
                    " VALUES(?, ?, ?, ?)");


            getBusesStatement = conn.prepareStatement("SELECT id, proizvodjac, serija, broj_sjedista" +
                    " FROM buses b");
            getDodjelaVozaci = conn.prepareStatement("SELECT dr.id, dr.name, dr.surname, dr.jmb, dr.birth, dr.hire_date" +
                    " FROM dodjela d INNER JOIN drivers dr ON (d.driver_id = dr.id) WHERE d.bus_id=?");
            getDriversStatement = conn.prepareStatement("SELECT id, name, surname, jmb, birth, hire_date" +
                    " FROM drivers");

            deleteDodjelaBus = conn.prepareStatement("DELETE FROM dodjela WHERE bus_id = ?");
            deleteDodjelaDriver = conn.prepareStatement("DELETE FROM dodjela WHERE driver_id = ?");
            deleteDriverStatement = conn.prepareStatement("DELETE FROM Drivers WHERE id = ?");
            deleteBusStatement = conn.prepareStatement("DELETE FROM buses WHERE id = ?");
            truncateDB = conn.prepareStatement("DELETE FROM dodjela; DELETE FROM buses; DELETE FROM drivers;");
            resetAutoIncrement = conn.prepareStatement("DELETE FROM SQLITE_SEQUENCE WHERE name='dodjela'; " +
                    "DELETE FROM SQLITE_SEQUENCE WHERE name='drivers'; DELETE FROM SQLITE_SEQUENCE WHERE name='buses';");
            //todo
            dodijeliVozacuAutobusStatement = conn.prepareStatement("DELETE FROM dodjela where ");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Nije pronadjen driver za konekciju na bazu");
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


    public ArrayList<Bus> getBusses() {
        ArrayList<Bus> buses = new ArrayList<>();
        try {
            ResultSet result = getBusesStatement.executeQuery();
            while(result.next()) {
                Integer id = result.getInt(1);
                String maker = result.getString(2);
                String series = result.getString(3);
                int seatNumber = result.getInt(4);
                getDodjelaVozaci.setInt(1, id);
                //uzimam samo prva 2 vozaca jer bus svakako nebi smio imati vise od iako nema
                //neki constraint u bazi da to zabrani sto bi mogo iz pomoc funkcije
                //count i triggera pri umetanju podataka
                ResultSet result2 = getDodjelaVozaci.executeQuery();
                int i = 0;
                Driver v1;
                ArrayList<Driver> drivers = new ArrayList<>();
                while (result2.next()) {
                    if (i == 3) {
                        break;
                    }
                    Integer idDriver = result2.getInt(1);
                    String name = result2.getString(2);
                    String surname = result2.getString(3);
                    String jmb = result2.getString(4);
                    Date birthDate = result2.getDate(5);
                    Date hireDate = result2.getDate(5);
                    drivers.add(new Driver(idDriver, name, surname, jmb, birthDate.toLocalDate(), hireDate.toLocalDate() ));
                    i++;
                }
                buses.add(new Bus(id, maker, series, seatNumber, drivers.get(0), drivers.get(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;

    }

    //TODO zamjena za ponavljanje koda
/*
    private Driver getOneDriver(ResultSet result) {
        try {
            Integer idDriver = result.getInt(1);
            String name = result.getString(2);
            String surname = result.getString(3);
            String jmb = result.getString(4);
            Date birthDate = result.getDate(5);
            Date hireDate = result.getDate(5);
            return new Driver(idDriver, name, surname, jmb, birthDate.toLocalDate(), hireDate.toLocalDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Driver();
    }*/




    public ArrayList<Driver> getDrivers() {
        ArrayList<Driver> drivers = new ArrayList<>();
        try {
            ResultSet result = getDriversStatement.executeQuery();
            while (result.next()) {
                Integer idDriver = result.getInt(1);
                String name = result.getString(2);
                String surname = result.getString(3);
                String jmb = result.getString(4);
                Date birthDate = result.getDate(5);
                Date hireDate = result.getDate(5);
                drivers.add(new Driver(idDriver, name, surname, jmb, birthDate.toLocalDate(), hireDate.toLocalDate()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
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


    public void addDriver(Driver driver) {
    }


    //todo code duplicates
    public void deleteDriver(Driver driver) {
        try {
            deleteDodjelaDriver.setInt(1, driver.getId());
            deleteDodjelaDriver.executeUpdate();
            deleteDriverStatement.setInt(1, driver.getId());
            deleteDriverStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteBus(Bus bus) {
        try {
            deleteDodjelaBus.setInt(1, bus.getId());
            deleteDodjelaBus.executeUpdate();
            deleteBusStatement.setInt(1, bus.getId());
            deleteBusStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dodijeliVozacuAutobus(Driver driver, Bus bus, int which) {
        if (which == 1) {

        }

    }



    public void resetDatabase() {
        try {
            truncateDB.executeUpdate();
            resetAutoIncrement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
