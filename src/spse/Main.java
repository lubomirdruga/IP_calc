package spse;
// todo rename package

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {




    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("IPv4_parameters.fxml"));
        primaryStage.setTitle("IPcalc");
        primaryStage.setScene(new Scene(root, 500, 700));
        primaryStage.show();




//        IPv4 VLSM just for testing!

//
//        String supernet = " 10 . 10 . 10 . 0 / 8 ";
//
//        int[] subnetHostsCount = {32,34,54,56,2,33};
//        IPv4VLSM vlsm = new IPv4VLSM(supernet, subnetHostsCount.length);
//        vlsm.start();
//        System.exit(0);



    }


    public static void main(String[] args)
    {
        launch(args);
    }

}
