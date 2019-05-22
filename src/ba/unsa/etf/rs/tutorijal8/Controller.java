package ba.unsa.etf.rs.tutorijal8;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    @FXML
    private TextField vozacImeText;
    @FXML
    private TextField vozacPrezimeText;
    @FXML
    private TextField vozacJmbText;
    @FXML
    private DatePicker vozacDatumRodjenja;
    @FXML
    private DatePicker vozacDatumZaposljenja;


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
        driversTable.setItems(transportModel.getDriversList());
        setTextPropetryBind();



    }




    private void setTextPropetryBind() {
        vozacImeText.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentDriver().getName()));
        vozacPrezimeText.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentDriver().getSurname()));
        vozacJmbText.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentDriver().getJmb()));
        //vozacDatumRodjenja.valueProperty().bindBidirectional(transportModel.getCurrentDriver().getBirthday(), new LocalDateEnhancedStringConverter());
        //vozacDatumZaposljenja.valueProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentDriver()));
    }

    private void setTextPropetryUnbind() {
        vozacImeText.textProperty().unbindBidirectional(transportModel.getCurrentDriver().getName());
        vozacPrezimeText.textProperty().unbindBidirectional(transportModel.getCurrentDriver().getSurname());
        vozacJmbText.textProperty().unbindBidirectional(transportModel.getCurrentDriver().getJmb());
        //vozacDatumRodjenja.valueProperty().unbindBidirectional(transportModel.getCurrentDriver().getBirthday());
        //vozacDatumZaposljenja.valueProperty().unbindBidirectional(transportModel.getCurrentDriver().getHireDate());
    }
}
