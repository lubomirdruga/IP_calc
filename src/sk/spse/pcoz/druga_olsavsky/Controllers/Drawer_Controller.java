package sk.spse.pcoz.druga_olsavsky.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Drawer_Controller
{
    public VBox sidePanel;

    public void handleIPv4Params() throws IOException {
        setScene("../Views/IPv4_parameters.fxml");
    }

    public void handleIPv4VLSM() throws IOException {
        setScene("../Views/IPv4_VLSM.fxml");
    }

    public void handleIPv4Info() throws IOException {
        setScene("../Views/IPv4_info.fxml");
    }

    public void handleIPv6Params() throws IOException {
        setScene("../Views/IPv6_parameters.fxml");
    }

    public void handleIPv6subnetting() throws IOException {
        setScene("../Views/IPv6_subnetting.fxml");
    }

    public void handleIPv6Info() throws IOException {
        setScene("../Views/IPv6_info.fxml");
    }

    public void handleConverter() throws IOException {
        setScene("../Views/Converter.fxml");
    }

    public void handleAbout() {
//todo dorobit
        Stage about = new Stage();
        about.setTitle("O aplikacií");

        Text text = new Text("Aplikácia bola vytvorená ako PČOZ maturitnej skúšky študentmi\n" +
                "Ľubomír Druga\n" +
                "Martin Oľšavský\n" +
                "IV.SB 2017/2018");


        VBox vBox = new VBox();
        vBox.getChildren().addAll(text);

        Scene scene = new Scene(vBox);

        about.setScene(scene);
        about.show();


//        JFXDialogLayout aboutContent  = new JFXDialogLayout();
//        aboutContent.setHeading(new Text("O aplikacií"));
//        aboutContent.setBody(new Text("Aplikácia bola vytvorená ako PČOZ maturitnej skúšky študentmi\n" +
//                "Ľubomír Druga\n" +
//                "Martin Oľšavský\n" +
//                "IV.SB 2017/2018"));
//
//        Stage stage = (Stage) sidePanel.getScene().getWindow();
//
//
//        JFXDialog aboutDialog = new JFXDialog(stage, aboutContent, JFXDialog.DialogTransition.CENTER);
//        JFXButton closeBtn = new JFXButton("Zavrieť");
//        closeBtn.setOnAction(event -> aboutDialog.close());
//        aboutContent.setActions(closeBtn);
//        aboutDialog.show();
    }

    public void handleExitBtn() {
        System.exit(0);
    }

    private void setScene(String file) throws IOException {
        Stage stage = (Stage) sidePanel.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(file));
        stage.setScene(new Scene(root));
    }
}
