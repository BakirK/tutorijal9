package ba.unsa.etf.rs.tutorijal8;

public class Bus {
    private Integer id = null;
    private String maker;
    private String series;
    private int seatNumber;
    private Driver driverOne = null;
    private Driver driverTwo = null;

    public Bus(String maker, String series, int seatNumber) {
        this.maker = maker;
        this.series = series;
        this.seatNumber = seatNumber;
    }

    public Bus(int id, String maker, String series,
               int seatNumber, Driver driverOne, Driver driverTwo ) {
        this.id = id;
        this.maker = maker;
        this.series = series;
        this.seatNumber = seatNumber;
        this.driverOne = driverOne;
        this.driverTwo = driverTwo;
    }


    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    private int getId() {
        return id;
    }


    private Driver getDriverOne() {
        return driverOne;
    }

    private void setDriverOne(Driver driverOne) {
        this.driverOne = driverOne;
    }

    private Driver getDriverTwo() {
        return driverTwo;
    }

    private void setDriverTwo(Driver driverTwo) {
        this.driverTwo = driverTwo;
    }
}
