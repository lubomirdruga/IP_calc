package spse.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
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

    public void handleIPv4Info() {

    }

    public void handleIPv6Params() throws IOException {
        setScene("../Views/IPv6_parameters.fxml");

    }

    public void handleIPv6subnetting() throws IOException {
        setScene("../Views/IPv6_subnetting.fxml");

    }

    public void handleIPv6Info() {

    }

    public void handleConverter() throws IOException {
        setScene("../Views/Converter.fxml");
    }

    public void handleAbout() {

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
