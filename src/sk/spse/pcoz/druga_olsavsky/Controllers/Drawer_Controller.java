package sk.spse.pcoz.druga_olsavsky.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

        Stage about = new Stage();
        about.initModality(Modality.APPLICATION_MODAL);
        about.setTitle("O aplikacií");

        Text text = new Text("Aplikácia bola vytvorená ako PČOZ maturitnej skúšky študentmi\n\n" +
                "Ľubomír Druga\n" +
                "Martin Oľšavský\n" +
                "IV.SB 2017/2018\n\n" +
                "Aplikácia je open-source, zdrojový kód nájdete na stránke\n" +
                "\n\t\thttps://github.com/lubomirdruga/IP_calc");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.getChildren().addAll(text);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);

        about.setScene(scene);
        about.show();
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
