package ba.unsa.etf.rs.tutorijal8;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {
    public TableView driversTable;
    public TableColumn tableRodjenje;
    public TableColumn tableIme;
    public TableColumn tablePrezime;
    public TableColumn tableJmb;
    public TableColumn tableZaposlenje;

    private TransportDAO transportModel;

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
    @FXML
    private Button updateDriverButton, addDriverButton, deleteDriverButton;


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
        transportModel.setCurrentDriver(transportModel.getDrivers().get(0));
        setTextPropetryBind();
        driversTable.setItems(transportModel.getDriversList());


//metode koje vrse update text field date pickera pri promjeni datuma
        vozacDatumRodjenja.getEditor().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1) {
                    LocalDate temp = LocalDate.parse(vozacDatumRodjenja.getEditor().getText(), DateTimeFormatter.ofPattern("M/d/yyyy"));
                    vozacDatumRodjenja.setValue(temp);
                }
            }
        });

        vozacDatumZaposljenja.getEditor().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1) {
                    LocalDate temp = LocalDate.parse(vozacDatumZaposljenja.getEditor().getText(), DateTimeFormatter.ofPattern("M/d/yyyy"));
                    vozacDatumZaposljenja.setValue(temp);
                }
            }
        });




        driversTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Driver>() {
            @Override
            public void changed(ObservableValue<? extends Driver> observableValue, Driver osobaOld, Driver osobaNew) {
                if (osobaOld != null) {
                    setTextPropetryUnbind();
                }
                if (osobaNew == null) {
                    vozacImeText.setText("");
                    vozacPrezimeText.setText("");
                    vozacJmbText.setText("");
                    vozacDatumZaposljenja.setValue(LocalDate.of(1900,1,1));
                    vozacDatumRodjenja.setValue(LocalDate.of(1900,1,1));
                } else {
                    updateSelectedUser();
                }
                driversTable.refresh();
            }
        });

        driversTable.requestFocus();
        driversTable.getSelectionModel().selectFirst();

    }

    @FXML
    private void updateSelectedUser() {
        if(transportModel.getCurrentDriver() == null) {
            System.out.println("NULL driver");
            //transportModel.setCurrentDriver(new Driver(-1,"Niko","Niko","-1", LocalDate.of(1900,1,1), LocalDate.of(1900,1,1)));
        }
        Driver d = (Driver) driversTable.getSelectionModel().getSelectedItem();
        setTextPropetryUnbind();
        //System.out.println(tabelaOsobe.getSelectionModel().getSelectedItem());
        transportModel.setCurrentDriver(d);
        setTextPropetryBind();
        driversTable.setItems(transportModel.getDriversList());
        driversTable.refresh();
    }

    //TODO baza ne sacuva promjene nakon update-a
    @FXML
    private void updateDriver(javafx.event.ActionEvent mouseEvent) {
        if(driversTable.getSelectionModel().getSelectedItem() == null) {
        } else {

            System.out.println(transportModel.getCurrentDriver().getId());
            transportModel.updateDriver(new Driver(transportModel.getCurrentDriver().getId(),
                    vozacImeText.getText(), vozacPrezimeText.getText(), vozacJmbText.getText(),
                    vozacDatumRodjenja.getValue(), vozacDatumZaposljenja.getValue()));
            int index = driversTable.getSelectionModel().getSelectedIndex();
            driversTable.getItems().clear();
            transportModel.ucitajVozace();
            driversTable.setItems(transportModel.getDriversList());
            driversTable.requestFocus();
            driversTable.getSelectionModel().select(index);
        }
    }



    @FXML
    private void addDriver(javafx.event.ActionEvent mouseEvent) {

    }

    @FXML
    private void deleteDriver(javafx.event.ActionEvent mouseEvent) {
        setTextPropetryUnbind();
        int index = driversTable.getSelectionModel().getSelectedIndex();
        if ( index != -1) {
            transportModel.deleteDriver(transportModel.getCurrentDriver());
            driversTable.refresh();
            driversTable.requestFocus();
        }
    }


    private void setTextPropetryBind() {
        vozacImeText.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentDriver().getName()));
        vozacPrezimeText.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentDriver().getSurname()));
        vozacJmbText.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentDriver().getJmb()));
        vozacDatumRodjenja.valueProperty().bindBidirectional(transportModel.getCurrentDriver().birthdayProperty());
        vozacDatumZaposljenja.valueProperty().bindBidirectional(transportModel.getCurrentDriver().hireDateProperty());
    }

    private void setTextPropetryUnbind() {
        vozacImeText.textProperty().unbindBidirectional(transportModel.getCurrentDriver().getName());
        vozacPrezimeText.textProperty().unbindBidirectional(transportModel.getCurrentDriver().getSurname());
        vozacJmbText.textProperty().unbindBidirectional(transportModel.getCurrentDriver().getJmb());
        vozacDatumRodjenja.valueProperty().unbindBidirectional(transportModel.getCurrentDriver().birthdayProperty());
        vozacDatumZaposljenja.valueProperty().unbindBidirectional(transportModel.getCurrentDriver().hireDateProperty());
    }
}
