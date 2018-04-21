package sk.spse.pcoz.druga_olsavsky.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sk.spse.pcoz.druga_olsavsky.Models.IPv6;
import sun.jvm.hotspot.debugger.AddressException;
import sun.net.util.IPAddressUtil;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class IPv6_parameters_Controller implements Initializable{
    public JFXTextField ipv6AddressInput;
    public JFXTextField prefixInput;
    //todo set infoBox to NOT visible
    public VBox infoVBox;
    public Label shortenAddressLabel;
    public Label nwAddressLabel;
    public Label prefixLabel;
    public Label unicastAddressLabel;
    public Label linkLocalAddressLabel;
    public Label siteLocalAddressLabel;
    public JFXDrawer drawer;
    public JFXHamburger hamburger;
    public JFXTextField macAddressInput;
    public StackPane stackPane;
    private String [] IPv6AllParams;


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

    public void getInputIP() {

        try{
            IPv6 ipv6;

            String ipv6Address = ipv6AddressInput.getText().trim().toUpperCase();
            int prefix = Integer.parseInt(prefixInput.getText().trim());
            String macAddress = macAddressInput.getText().trim().toUpperCase();

            validateIPv6Address(ipv6Address);

            if(!macAddressInput.getText().trim().isEmpty())
            {
                ipv6 = new IPv6 (ipv6Address,prefix, macAddress);

                //todo ak sa nemylim, do metody allIPv6Param() nepotrebujes zas odosielat vstup adresa, prefix, a macadresa nakolko si ich nastavil v konstruktore o riadok vyssie
                IPv6AllParams = ipv6.allIPv6Param(ipv6Address, prefix, macAddress);
                validateMACAddress(macAddress);

            }
            else {
                ipv6 = new IPv6 (ipv6Address,prefix, -1);
                //todo poznamka k todo vyssie, v konstruktoroch si nastav this.mac = null; proste, predid chybam :D
                IPv6AllParams = ipv6.allIPv6Param(ipv6Address,prefix,null);

            }
            setParameters(IPv6AllParams);

        } catch (NumberFormatException e){
            showErrorDialog("Chybne zadaný prefix!");
        } catch (IllegalArgumentException e){
            showErrorDialog("Nesprávny tvar IPv6 adresy!");
        } catch (IllegalStateException e){
            showErrorDialog("Nesprávny tvar MAC adresy!");
        } catch (Exception e){
            e.printStackTrace();
        }


        //todo toto ti treba ? vypis asi nie, a to skryvanie poriesim
//        for (int i = 0; i < IPv6AllParams.length ; i++) {
//            System.out.println(IPv6AllParams[i]);
//        }
//
//        if (IPv6AllParams[4].isEmpty())
//        {
//            linkLocalAddressLabel.setVisible(false);
//        }

    }

    //todo je 'this.' nutne ? vstupuje ti to do metody, ak nepotrebujes posielat pole do metody, zmaz vstup a taisto aj this. :)
    private void setParameters(String[] IPv6AllParams)
    {
        shortenAddressLabel.setText(this.IPv6AllParams[0]);
        //todo skratit sietovu adresu
        nwAddressLabel.setText(this.IPv6AllParams[1]);
        prefixLabel.setText(this.IPv6AllParams[2]);
        unicastAddressLabel.setText(this.IPv6AllParams[3]);
        //todo "FE80::" vlozit pred link local adresu
        linkLocalAddressLabel.setText(this.IPv6AllParams[4]);
        siteLocalAddressLabel.setText(this.IPv6AllParams[5]);

    }

    public void clearAll() {

        ipv6AddressInput.clear();
        prefixInput.clear();
        shortenAddressLabel.setText("");
        nwAddressLabel.setText("");
        prefixLabel.setText("");
        unicastAddressLabel.setText("");
        linkLocalAddressLabel.setText("");
        siteLocalAddressLabel.setText("");

        infoVBox.setVisible(false);

    }

    public void handleSubmit() {
        getInputIP();
        infoVBox.setVisible(true);


    }

    private void showErrorDialog(String error){
        JFXDialogLayout content  = new JFXDialogLayout();
        content.setHeading(new Text("Chyba!"));
        content.setBody(new Text(error));

        JFXDialog errorDialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton closeBtn = new JFXButton("Dobre");

        closeBtn.setOnAction(event -> errorDialog.close());
        content.setActions(closeBtn);
        errorDialog.show();
    }

    private void validateIPv6Address(String ipv6Address){

        if (!IPAddressUtil.isIPv6LiteralAddress(ipv6Address)){
            throw new IllegalArgumentException();
        }
    }

    private void validateMACAddress(String macAddress){

        if (!macAddress.matches("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$")){
            throw new IllegalStateException();
        }
    }
}
