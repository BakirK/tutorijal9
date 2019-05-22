package ba.unsa.etf.rs.tutorijal8;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class Controller {
    public TableColumn tableRodjenje;
    public TableColumn tableIme;
    public TableColumn tablePrezime;
    public TableColumn tableJmb;
    public TableColumn tableZaposlenje;
    private TransportDAO transportModel;


    public TableView driversTable;
    public Button deleteDriverButton;
    public Button addDriverButton;


    public Controller(TransportDAO t) {
        transportModel = t;
    }



    @FXML
    public void initialize() {
        tableIme.setCellValueFactory(new PropertyValueFactory<Driver, String>("Name"));
        tablePrezime.setCellValueFactory(new PropertyValueFactory<Driver, String>("Surname"));
        tableJmb.setCellValueFactory(new PropertyValueFactory<Driver, String>("jmb"));
        tableZaposlenje.setCellValueFactory(new PropertyValueFactory<Driver, LocalDate>("hireDate"));
        tableRodjenje.setCellValueFactory(new PropertyValueFactory<Driver, LocalDate>("birthday"));

        System.out.println(transportModel.getDrivers().size());
        driversTable.setItems(transportModel.getDriversList());

    }
}
