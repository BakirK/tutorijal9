package ba.unsa.etf.rs.tutorijal8;

import javafx.beans.property.SimpleObjectProperty;

import java.sql.Date;
import java.time.LocalDate;

public class Driver {
    private Integer id = -1;
    private String name;
    private String surname;
    private String jmb;
    private SimpleObjectProperty<LocalDate> birthday = new SimpleObjectProperty<>();
    private SimpleObjectProperty<LocalDate> hireDate = new SimpleObjectProperty<>();


    public Driver() {
        name = "NULL";
        surname = "NULL";
        jmb = "NULL";
        birthday.set(LocalDate.of(1,1,1));
        hireDate.set(LocalDate.of(1,1,1));

    }

    public Driver(String name, String surname, String jmb, LocalDate birthday, LocalDate hireDate) {
        this.name = name;
        this.surname = surname;
        this.jmb = jmb;
        setBirthday(birthday);
        setHireDate(hireDate);
    }

    public Driver(Integer idDriver, String name, String surname, String jmb, LocalDate birthday, LocalDate hireDate) {
        this.id = idDriver;
        this.name = name;
        this.surname = surname;
        this.jmb = jmb;
        setBirthday(birthday);
        setHireDate(hireDate);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getJmb() {
        return jmb;
    }



    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  " - (" + this.getName() + " " + this.getSurname() + " ( " + this.getJmb() + " ))";
    }

    public boolean equals(Driver d) {
        return (d.getJmb().equals(this.getJmb()));
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public SimpleObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    private void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public LocalDate getHireDate() {
        return hireDate.get();
    }

    public SimpleObjectProperty<LocalDate> hireDateProperty() {
        return hireDate;
    }

    private void setHireDate(LocalDate hireDate) {
        this.hireDate.set(hireDate);
    }
}
