package sk.spse.pcoz.druga_olsavsky;
// todo rename package

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    //todo poriesit dedenie init metody z Drawer_Controller!

    //TODO> prezriet vsetky metody, ktore mam z netu, a pridat k nim do komentov linky kvoli dokumentaicii!


    @Override
    public void start(Stage primaryStage) throws IOException {

//        Parent root = FXMLLoader.load(getClass().getResource("IPv4_parameters.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("Views/IPv4_VLSM.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("Converter.fxml"));

        primaryStage.setTitle("IP subnetting");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

}
