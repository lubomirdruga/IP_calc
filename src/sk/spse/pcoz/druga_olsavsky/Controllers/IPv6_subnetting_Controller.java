package sk.spse.pcoz.druga_olsavsky.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sk.spse.pcoz.druga_olsavsky.Models.IPv6;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IPv6_subnetting_Controller implements Initializable{
    public JFXTextField ipv6AddressInput;
    public JFXTextField prefixInput;
    public JFXTextField subnetsCountInput;

    //v pripade nevyuzivania scrollpane premennej odstranit
    public ScrollPane scrollPane;
    public VBox vBoxContent;
    public StackPane stackPane;
    public JFXDrawer drawer;
    public JFXHamburger hamburger;
    private String [] finalAllSubnets;
    private IPv6 IPv6Subnetting;

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
            if(drawer.isShown())
            {
                drawer.close();
            }else
                drawer.open();
        });
    }



    public void handleSubmit() {

//        clearAll();
        vBoxContent.setVisible(true);

        try{

            int prefix = Integer.parseInt(prefixInput.getText().trim());
            if (prefix < 48)
                throw new IOException();

            int subnetsCount = Integer.parseInt(subnetsCountInput.getText());

            IPv6Subnetting = new IPv6(ipv6AddressInput.getText().toUpperCase(),prefix, subnetsCount);
            IPv6Subnetting.validateIPv6Address();
            finalAllSubnets = IPv6Subnetting.subnetting();

            createSubnetRows();

        }  catch (IllegalArgumentException e){
            showErrorDialog("Nesprávny tvar IPv6 adresy!");
        } catch (IOException e) {
            showErrorDialog("Subnetovať je možné v rozsahu prefixu 48 až 64!");

        } catch (Exception e){
            showErrorDialog("Prefix hľadaných podsietí nemôže byť väčší ako 64!\n[Prefixy podsietí presahujú časť SUBNET ID časť,\n" +
                    "kde sa nesubnetuje.]\n" +
                    "Zadajte prosím, menší prefix.");
        }
    }



    public void createSubnetRows()
    {
        int subnetsCount = 0;

        try {

            //  2001:ACAD:1000:0000:0000:0000:0000:0000 59 8

            for (int exponent = 0; Integer.parseInt(subnetsCountInput.getText().trim()) > subnetsCount ; exponent++) {
                subnetsCount = (int) Math.pow(2, exponent);
                System.out.println(subnetsCount);
            }
            System.out.println(subnetsCount);


            if (subnetsCount <= 0)
                throw new NumberFormatException();

            HBox[] subnetsRows = new HBox[subnetsCount];


            JFXTextField[] subnetName = new JFXTextField[subnetsCount];
            JFXTextField[] subnetAddress = new JFXTextField[subnetsCount];
            Separator[] separators = new Separator[subnetsCount];


            for (int i = 0; i < subnetsRows.length; i++) {


                subnetName[i] = new JFXTextField(str(i));
                subnetName[i].setMinSize(77,30);
                subnetName[i].setMaxSize(77,30);
                subnetName[i].setEditable(false);

                subnetAddress[i] = new JFXTextField(finalAllSubnets[i]);
                subnetAddress[i].setMinSize(333,30);
                subnetAddress[i].setMaxSize(333,30);
                subnetAddress[i].setEditable(false);



                separators[i] = new Separator(Orientation.VERTICAL);
                separators[i].setPrefSize(10,30);

                subnetsRows[i] = new HBox();
                subnetsRows[i].getChildren().addAll(subnetName[i], separators[i], subnetAddress[i]);
            }

            subnetName[0].setPromptText("Názov siete");
            subnetName[0].setLabelFloat(true);

            subnetAddress[0].setPromptText("Adresa subnetu");
            subnetAddress[0].setLabelFloat(true);



            subnetsRows[0].setPadding(new Insets(10,0,0,0));
            vBoxContent.getChildren().clear();
            vBoxContent.getChildren().addAll(subnetsRows);

        }
        catch(NumberFormatException nfe) {
            showErrorDialog("Zlý vstup!");
        }

    }



    public void clearAll() {

        vBoxContent.setVisible(false);

        ipv6AddressInput.clear();
        prefixInput.clear();
        subnetsCountInput.clear();

        IPv6Subnetting = null;
        finalAllSubnets = null;

    }

    private String str(int i) {
        return i < 0 ? "" : str((i / 26) - 1) + (char)(65 + i % 26);
    }

    private void showErrorDialog(String body)
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
}
