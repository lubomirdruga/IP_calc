package sk.spse.pcoz.druga_olsavsky.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sk.spse.pcoz.druga_olsavsky.Models.IPv4;
import sk.spse.pcoz.druga_olsavsky.Models.IPv4VLSM;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class IPv4_VLSM_Controller implements Initializable
{

    public JFXTextField supernetInput;
    public JFXTextField subnetsCountInput;
    public VBox vBoxContent;
    public JFXHamburger hamburger;
    public JFXDrawer drawer;
    public AnchorPane anchorPane;
    public StackPane stackPane;
    public ScrollPane scrollPane;
    private JFXTextField[] netSizeInput, netNameInput;
    private IPv4[] finalSubnets;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            VBox box = FXMLLoader.load(getClass().getResource("../Views/Drawer.fxml"));
            drawer.setSidePane(box);
        } catch (IOException ex) {
            System.out.println("File 'Drawer.fxml' not found");
        }

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{

            if(drawer.isShown()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });


        try {
            changeSubnets();
        }catch (Exception ignored){}

    }

    public void changeSubnets()
    {
        int subnetsCount;

        try {

            subnetsCount = Integer.parseInt(subnetsCountInput.getText().trim());

            if (subnetsCount <= 0)
                throw new NumberFormatException();

            HBox[] inputRows = new HBox[subnetsCount];
            netNameInput = new JFXTextField[subnetsCount];
            netSizeInput = new JFXTextField[subnetsCount];
            Separator[] separators = new Separator[subnetsCount];


            for (int i = 0; i < inputRows.length; i++) {

                netNameInput[i] = new JFXTextField(str(i));
                netNameInput[i].setPromptText("Názov siete");
                netNameInput[i].setMinSize(235,40);
                netNameInput[i].setMaxSize(235,40);

                netSizeInput[i] = new JFXTextField();
                netSizeInput[i].setPromptText("Počet zariadení");
                netSizeInput[i].setMinSize(235,40);
                netSizeInput[i].setMaxSize(235,40);


                separators[i] = new Separator(Orientation.VERTICAL);
                separators[i].setPrefSize(10,40);

                inputRows[i] = new HBox();
                inputRows[i].getChildren().addAll(netNameInput[i], separators[i], netSizeInput[i]);
            }
            netNameInput[0].setLabelFloat(true);
            netSizeInput[0].setLabelFloat(true);

            inputRows[0].setPadding(new Insets(25,0,0,0));
            vBoxContent.getChildren().clear();
            vBoxContent.getChildren().addAll(inputRows);

        }
        catch(NumberFormatException nfe) {
            showToast("Zlý vstup!");
        }
    }

    public void submitVLSM()
    {
        try {
            int[] subnetsArray = new int[netSizeInput.length];
            String[] subnetNames = new String[netNameInput.length];

            for (int i = 0; i < netSizeInput.length; i++) {
                subnetsArray[i] = Integer.parseInt(netSizeInput[i].getText().trim());
                subnetNames[i] = netNameInput[i].getText().trim();

            }

            IPv4VLSM vlsm = new IPv4VLSM(supernetInput.getText(), Integer.parseInt(subnetsCountInput.getText()), subnetsArray, subnetNames);
            vlsm.start();
            finalSubnets = vlsm.getSubnet();

            IPv4_VLSM_Table_Controller.setItems(finalSubnets);
            IPv4_VLSM_Table_Controller.setVlsm(vlsm);

            Stage stage = (Stage) supernetInput.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../Views/IPv4_VLSM_Table.fxml"));
            stage.setScene(new Scene(root));

        }
        catch (IOException io){

            showToast("Zadané číslo (oktet IPv4) nie je z rozsahu IPv4 (0-255)!");
        }
        catch (ArithmeticException ae)
        {
            JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
            snackbar.show("Nie je možné subnetovať, supernet sieť je príliš malá", 3000);
        }
        catch (Exception e) {
            showToast("Zadaný vstup nie je správny!");
        }
    }

    private void showToast(String body)
    {
        JFXDialogLayout content  = new JFXDialogLayout();
        content.setHeading(new Text("Chyba!"));
        content.setBody(new Text(body));

        JFXDialog errorDialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton closeBtn = new JFXButton("Dobre");
        closeBtn.setOnAction(event -> errorDialog.close());
        content.setActions(closeBtn);
        errorDialog.show();
    }

    // https://stackoverflow.com/questions/8710719/generating-an-alphabetic-sequence-in-java
    private String str(int i) {
        return i < 0 ? "" : str((i / 26) - 1) + (char)(65 + i % 26);
    }
}
