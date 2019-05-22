package ba.unsa.etf.rs.tutorijal8;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class Controller {
    private TransportDAO transportModel;


    public TableView driversTable;
    public Button deleteDriverButton;
    public Button addDriverButton;


    public Controller(TransportDAO t) {
        transportModel = t;
    }



    @FXML
    public void initialize() {
       transportModel.getInstance();
       driversTable.setItems(transportModel.getDriversList());

    }
}
