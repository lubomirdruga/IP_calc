package spse;

import javafx.event.ActionEvent;
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
        setScene("IPv4_parameters.fxml");
    }

    public void handleIPv4VLSM() throws IOException {
        setScene("IPv4_VLSM.fxml");
    }

    public void handleIPv4Info()
    {

    }

    public void handleIPv6Params()
    {

    }

    public void handleIPv6VLSM()
    {

    }

    public void handleIPv6Info()
    {

    }

    public void handleConverter() throws IOException {
        setScene("Converter.fxml");
    }

    public void handleAbout()
    {

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
