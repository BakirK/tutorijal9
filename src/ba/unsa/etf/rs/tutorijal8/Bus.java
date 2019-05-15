package ba.unsa.etf.rs.tutorijal8;

import java.util.ArrayList;
import java.util.Arrays;

public class Bus {
    private String proizvodjac;
    private String serija;
    private int brojSjedista;
    //private final int[] vozaci = {1,2,3};
    //private ArrayList<Integer> vozaci = new ArrayList<>(Arrays.asList(1,2,3));

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public String getSerija() {
        return serija;
    }

    public void setSerija(String serija) {
        this.serija = serija;
    }

    public int getBrojSjedista() {
        return brojSjedista;
    }

    public void setBrojSjedista(int brojSjedista) {
        this.brojSjedista = brojSjedista;
    }

}
