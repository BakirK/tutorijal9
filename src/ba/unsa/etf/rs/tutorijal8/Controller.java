package ba.unsa.etf.rs.tutorijal8;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.NumberStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {
    @FXML
    public TableView driversTable, busesTable;
    public TableColumn tableRodjenje, tableIme, tablePrezime, tableJmb, tableZaposlenje;
    private TransportDAO transportModel;
    @FXML
    private TextField vozacImeText, vozacPrezimeText, vozacJmbText;
    @FXML
    private DatePicker vozacDatumRodjenja, vozacDatumZaposljenja;

    //podaci za buseve
    @FXML
    private TableColumn tableProizvodjac, tableSerija, tableBrojSjedista;
    @FXML
    private TextField proizvodjacText, serijaText, brojSjedistaText;
    @FXML
    private ComboBox driverOneComboBox, driverTwoComboBox;

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

        //buses
        tableProizvodjac.setCellValueFactory(new PropertyValueFactory<Bus, String>("maker"));
        tableSerija.setCellValueFactory(new PropertyValueFactory<Bus, String>("series"));
        tableBrojSjedista.setCellValueFactory(new PropertyValueFactory<Bus, Integer>("seatNumber"));
        setTextPropetryBind();
        driversTable.setItems(transportModel.getDriversList());
        busesTable.setItems(transportModel.getBusesList());


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
                    updateSelectedDriver();
                }
                driversTable.refresh();
            }
        });

        driversTable.requestFocus();
        driversTable.getSelectionModel().selectFirst();



        busesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldBus, Object newBus) {
                if (oldBus != null) {
                    setTextPropetryUnbind();
                }
                if (newBus == null) {
                    proizvodjacText.setText("");
                    serijaText.setText("");
                    brojSjedistaText.setText("");
                } else {
                    updateSelectedBus();
                }
                busesTable.refresh();
            }
        });


        driverOneComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

            }
        });
        updateComboBox();
    }

    private  void updateComboBox() {
        driverOneComboBox.setItems(transportModel.getDriversList());
        driverTwoComboBox.setItems(transportModel.getDriversList());
        driverOneComboBox.getSelectionModel().select(transportModel.getCurrentBus().getDriverOne());
        driverTwoComboBox.getSelectionModel().select(transportModel.getCurrentBus().getDriverTwo());
    }


    @FXML
    private void updateSelectedBus() {
        if(transportModel.getCurrentBus() == null) {
            System.out.println("NULL driver");
        }
        Bus b = (Bus) busesTable.getSelectionModel().getSelectedItem();
        setTextPropetryUnbind();
        transportModel.setCurrentBus(b);
        setTextPropetryBind();
        busesTable.setItems(transportModel.getBusesList());
        busesTable.refresh();
        driverOneComboBox.setItems(transportModel.getDriversList());
        driverTwoComboBox.setItems(transportModel.getDriversList());
        driverOneComboBox.getSelectionModel().select(transportModel.getCurrentDriver());
        driverTwoComboBox.getSelectionModel().select(transportModel.getCurrentDriver());
    }

    @FXML
    private void updateSelectedDriver() {
        if(transportModel.getCurrentDriver() == null) {
            System.out.println("NULL driver");
        }
        Driver d = (Driver) driversTable.getSelectionModel().getSelectedItem();
        setTextPropetryUnbind();
        //System.out.println(tabelaOsobe.getSelectionModel().getSelectedItem());
        transportModel.setCurrentDriver(d);
        setTextPropetryBind();
        driversTable.setItems(transportModel.getDriversList());
        driversTable.refresh();
    }




    private void updateTableView() {
        int index = driversTable.getSelectionModel().getSelectedIndex();
        driversTable.getItems().clear();
        transportModel.ucitajVozace();
        driversTable.setItems(transportModel.getDriversList());
        driversTable.requestFocus();
        driversTable.getSelectionModel().select(index);

        index = busesTable.getSelectionModel().getSelectedIndex();
        busesTable.getItems().clear();
        transportModel.ucitajBuseve();
        busesTable.setItems(transportModel.getBusesList());
        busesTable.requestFocus();
        busesTable.getSelectionModel().select(index);
        updateComboBox();
    }


    @FXML
    private void updateDriver(javafx.event.ActionEvent mouseEvent) {
        if(driversTable.getSelectionModel().getSelectedItem() == null) {
        } else {

            System.out.println(transportModel.getCurrentDriver().getId());
            transportModel.updateDriver(new Driver(transportModel.getCurrentDriver().getId(),
                    vozacImeText.getText(), vozacPrezimeText.getText(), vozacJmbText.getText(),
                    vozacDatumRodjenja.getValue(), vozacDatumZaposljenja.getValue()));
            updateTableView();
        }
    }

    @FXML
    private void addDriver(javafx.event.ActionEvent mouseEvent) {
        transportModel.addDriver(new Driver());
        updateTableView();
        driversTable.getSelectionModel().selectLast();


    }

    @FXML
    private void deleteDriver(javafx.event.ActionEvent mouseEvent) {
        setTextPropetryUnbind();
        int index = driversTable.getSelectionModel().getSelectedIndex();
        if ( index != -1) {
            transportModel.deleteCurrentDriver();
            updateTableView();
            if (index == driversTable.getItems().size()) {
                driversTable.getSelectionModel().selectLast();
            }
        }
    }


    @FXML
    private void deleteBus(ActionEvent actionEvent) {
        setTextPropetryUnbind();
        int index = busesTable.getSelectionModel().getSelectedIndex();
        if ( index != -1) {
            transportModel.deleteBus(transportModel.getCurrentBus());
            updateTableView();
            if (index == busesTable.getItems().size()) {
                busesTable.getSelectionModel().selectLast();
            }
        }
    }

    @FXML
    private void addBus(ActionEvent actionEvent) {
        transportModel.addBus(new Bus());
        updateTableView();
        busesTable.getSelectionModel().selectLast();
    }

    @FXML
    private void updateBus(ActionEvent actionEvent) {
        if(busesTable.getSelectionModel().getSelectedItem() == null) {
        } else {
            System.out.println(transportModel.getCurrentDriver().getId());
            transportModel.updateBus(new Bus(transportModel.getCurrentBus().getId(), proizvodjacText.getText(), serijaText.getText(),
                    Integer.parseInt(brojSjedistaText.getText())));
            updateTableView();
        }
    }













    private void setTextPropetryBind() {
        vozacImeText.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentDriver().getName()));
        vozacPrezimeText.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentDriver().getSurname()));
        vozacJmbText.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentDriver().getJmb()));
        vozacDatumRodjenja.valueProperty().bindBidirectional(transportModel.getCurrentDriver().birthdayProperty());
        vozacDatumZaposljenja.valueProperty().bindBidirectional(transportModel.getCurrentDriver().hireDateProperty());

        //buses
        proizvodjacText.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentBus().getMaker()));
        serijaText.textProperty().bindBidirectional(new SimpleStringProperty(transportModel.getCurrentBus().getSeries()));
        brojSjedistaText.textProperty().bindBidirectional(new SimpleIntegerProperty(transportModel.getCurrentBus().getSeatNumber()),
                new NumberStringConverter());
    }

    private void setTextPropetryUnbind() {
        vozacImeText.textProperty().unbindBidirectional(transportModel.getCurrentDriver().getName());
        vozacPrezimeText.textProperty().unbindBidirectional(transportModel.getCurrentDriver().getSurname());
        vozacJmbText.textProperty().unbindBidirectional(transportModel.getCurrentDriver().getJmb());
        vozacDatumRodjenja.valueProperty().unbindBidirectional(transportModel.getCurrentDriver().birthdayProperty());
        vozacDatumZaposljenja.valueProperty().unbindBidirectional(transportModel.getCurrentDriver().hireDateProperty());

        //buses
        proizvodjacText.textProperty().unbindBidirectional(transportModel.getCurrentBus().getMaker());
        serijaText.textProperty().unbindBidirectional(transportModel.getCurrentBus().getSeries());
        brojSjedistaText.textProperty().unbindBidirectional(transportModel.getCurrentBus().getSeatNumber());
    }


}
