package spse;
// todo rename package

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {




    @Override
    public void start(Stage primaryStage) throws Exception{

//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("IPcalc");
//        primaryStage.setScene(new Scene(root, 500, 700));
//        primaryStage.show();


//        IPv4 VLSM just for testing!
//
        //Scanner sc = new Scanner(System.in);
//        System.out.println("Zadaj supernet + prefix");
//        String supernet = sc.nextLine();

//        String supernet = " 192 . 168 . 20 . 0 / 22 ";
        String supernet = " 10 . 10 . 10 . 0 / 8 ";

        int[] subnetHostsCount = {32,34,54,56,2,33};
        IPv4VLSM vlsm = new IPv4VLSM(supernet, subnetHostsCount.length);
        vlsm.magic();



        System.exit(0);
////



    }


    public static void main(String[] args)
    {
        launch(args);
    }

}
