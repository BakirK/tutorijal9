package ba.unsa.etf.rs.tutorijal8;

import java.time.LocalDate;
import java.util.Date;

public class Driver {
    private int id;
    private String ime;
    private  String  prezime;
    private int jmb;
    private LocalDate datumRodjenja;
    private LocalDate datumZaposlenja;

    public Driver() {

    }

    public Driver(String ime, String prezime, int jmb, LocalDate datumRodjenja, LocalDate datumZaposlenja) {
        this.ime = ime;
        this.prezime = prezime;
        this.jmb = jmb;
        this.datumRodjenja = datumRodjenja;
        this.datumZaposlenja = datumZaposlenja;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public int getJmb() {
        return jmb;
    }

    public void setJmb(int jmb) {
        this.jmb = jmb;
    }
    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public LocalDate getDatumZaposlenja() {
        return datumZaposlenja;
    }

    public void setDatumZaposlenja(LocalDate datumZaposlenja) {
        this.datumZaposlenja = datumZaposlenja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
