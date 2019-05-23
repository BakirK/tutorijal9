package ba.unsa.etf.rs.tutorijal8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        TransportDAO model = TransportDAO.getInstance();
        model.ucitajBuseve();
        model.ucitajVozace();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MVCFXML.fxml"));
        loader.setController(new Controller(model));
        Parent root = loader.load();
        primaryStage.setTitle("Biblioteka");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

















/*

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static TransportDAO dao ;

    public static void main(String[] args) {
	// write your code here
        dao = TransportDAO.getInstance();
        Scanner tok = new Scanner(System.in);
        String result="";
        while (tok.hasNextLine()) {
            result = tok.nextLine();
            switch (result){
                case "dodaj vozaca":
                    dodajVozaca(tok);
                    break;
                case "dodaj autobus":
                    dodajAutobus(tok);
                    break;
                case "otpusti vozaca":
                    otpustiVozaca(tok);
                    break;
                case "ukloni autobus":
                    ukloniAutobus(tok);
                    break;
                case "Dodijeli vozaca autobusu":
                    dodijeliAutobusVozacu(tok);
                    break;
                case "Ispisi autobuse":
                    ispisiAutobuse();
                    break;
            }
        }
    }

    private static void ispisiAutobuse() {
        ArrayList<Bus> busses = dao.getBusses();
        for (int i = 0; i < busses.size(); i++) {
            System.out.println((i+1)+". "+busses.get(i).toString());
        }
    }

    private static void dodijeliAutobusVozacu(Scanner tok) {
        System.out.println("Odaberite vozača: ");
        for (int i = 0; i < dao.getDrivers().size(); i++) {
            System.out.println((i+1)+". "+dao.getDrivers().get(i));
        }
        System.out.print("Index: ");
        int driverIndex = tok.nextInt()-1;
        ba.unsa.etf.rs.tutorijal8.Driver driver = dao.getDrivers().get(driverIndex);
        System.out.println("Odaberite autobus: ");
        for (int i = 0; i < dao.getBusses().size(); i++) {
            System.out.println((i+1)+". "+dao.getBusses().get(i).toString());
        }
        System.out.print("Index: ");
        int busIndex = tok.nextInt()-1;
        Bus bus = dao.getBusses().get(busIndex);
        int which = 1;
        if(bus.getDriverOne()!=null && bus.getDriverTwo()!=null){
            System.out.print("Umjesto kojeg vozača želite postaviti trenutnog(1 ili 2): ");
            which = tok.nextInt();
        }else if(bus.getDriverOne()!=null){
            which = 2;
        }
        dao.dodijeliVozacuAutobus(driver,bus,which);
    }

    private static void ukloniAutobus(Scanner tok) {
        for (int i = 0; i < dao.getBusses().size(); i++) {
            System.out.println((i+1)+". "+dao.getBusses().get(i));
        }
        int index = tok.nextInt()-1;
        Bus bus = dao.getBusses().get(index);
        dao.deleteBus(bus);
    }

    private static void otpustiVozaca(Scanner tok) {
        for (int i = 0; i < dao.getDrivers().size(); i++) {
            System.out.println((i+1)+". " + dao.getDrivers().get(i));
        }
        int index = tok.nextInt()-1;
        ba.unsa.etf.rs.tutorijal8.Driver driver = dao.getDrivers().get(index);
        dao.deleteDriver(driver);
    }

    private static void dodajAutobus(Scanner tok) {
        String maker = tok.nextLine();
        String series = tok.nextLine();
        int seatNumber = tok.nextInt();
        Bus bus = new Bus(maker, series, seatNumber);
        dao.addBus(bus);
    }

    private static void dodajVozaca(Scanner tok){
        Scanner stream = tok;
        String name = stream.nextLine();
        String surname = stream.nextLine();
        String umcn = stream.nextLine();
        LocalDate birthday = LocalDate.parse(stream.nextLine(), DateTimeFormatter.ofPattern("d.M.yyyy"));
        LocalDate hireDate = LocalDate.parse(stream.nextLine(), DateTimeFormatter.ofPattern("d.M.yyyy"));
        dao.addDriver(new Driver(name, surname, umcn, birthday, hireDate));
    }
}
*/