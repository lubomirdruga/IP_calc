package sk.spse.pcoz.druga_olsavsky.Controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sk.spse.pcoz.druga_olsavsky.Models.IPv6;
import sun.security.x509.IPAddressName;

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
    public Label compatibleIPv4Label;
    public Label loopbackAddressLabel;
    public JFXDrawer drawer;
    public JFXHamburger hamburger;
    public JFXTextField macAddressInput;
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

    public void getInputIP() throws UnknownHostException {
        //todo tu si vytiahnes zadane hodnoty z inputov, overis ich, a tak dalej
        //todo v podstate, navrh toho je na tebe, odporucam pozriet triedu 'IPv4_parameters_Controller' zjednodusis si to :D

        //todo toto je len na ukazku ako vyberat a nastavit hodnoty do vyslednej podoby ;)

        String ipv6Address = ipv6AddressInput.getText();
        String prefix = prefixInput.getText();



        // TODO: 18. 4. 2018 mam tam nechat null alebo vytvorit novy konstruktor? 
        IPv6 ipv6 = new IPv6 (ipv6AddressInput.getText(),Integer.parseInt(prefixInput.getText()),0);

//        try {
//            IPv6.getCompressedAddress(ipv6AddressInput.getText());
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
        IPv6AllParams = ipv6.allIPv6Param(ipv6AddressInput.getText(),Integer.parseInt(prefixInput.getText()));
        for (int i = 0; i < IPv6AllParams.length ; i++) {
            System.out.println(IPv6AllParams[i]);
        }

        setParameters(IPv6AllParams);
    }

    //todo tie vstupy metody su len na teraz, ty si tam udaje das jednosduchsie
    private void setParameters(String[] IPv6AllParams)
    {
        shortenAddressLabel.setText(this.IPv6AllParams[0]);
        nwAddressLabel.setText(this.IPv6AllParams[1]);
        prefixLabel.setText(this.IPv6AllParams[2]);
        unicastAddressLabel.setText(this.IPv6AllParams[3]);
        linkLocalAddressLabel.setText(this.IPv6AllParams[4]);
        siteLocalAddressLabel.setText(this.IPv6AllParams[5]);
        // TODO: 19. 4. 2018  IPV6 compatibile som nedal, lebo nie som si isty ako to vypocitat
        //compatibleIPv4Label.setText(ipv6);
        loopbackAddressLabel.setText(this.IPv6AllParams[6]);

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
        compatibleIPv4Label.setText("");
        loopbackAddressLabel.setText("");

        infoVBox.setVisible(false);

    }

    public void handleSubmit() throws UnknownHostException {
        getInputIP();
        infoVBox.setVisible(true);


    }
}
