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
        //todo tu si vytiahnes zadane hodnoty z inputov, overis ich, a tak dalej
        //todo v podstate, navrh toho je na tebe, odporucam pozriet triedu 'IPv4_parameters_Controller' zjednodusis si to :D

        //todo toto je len na ukazku ako vyberat a nastavit hodnoty do vyslednej podoby ;)

        String ipv6Address = ipv6AddressInput.getText();
        String prefix = prefixInput.getText();


        setParameters(ipv6Address, prefix);
        // TODO: 18. 4. 2018 mam tam nechat null alebo vytvorit novy konstruktor? 
        IPv6 ipv6 = new IPv6 (ipv6AddressInput.getText(),Integer.parseInt(prefixInput.getText()),0);

        try {
            IPv6.getCompressedAddress(ipv6AddressInput.getText());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        IPv6.Nw(ipv6AddressInput.getText(),Integer.parseInt(prefixInput.getText()));


    }

    //todo tie vstupy metody su len na teraz, ty si tam udaje das jednosduchsie
    private void setParameters(String ipv6, String prefix)
    {
        shortenAddressLabel.setText(ipv6);
        nwAddressLabel.setText(ipv6);
        prefixLabel.setText(prefix);
        unicastAddressLabel.setText(ipv6);
        linkLocalAddressLabel.setText(ipv6);
        siteLocalAddressLabel.setText(ipv6);
        compatibleIPv4Label.setText(ipv6);
        loopbackAddressLabel.setText("áno/nie");


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

    public void handleSubmit() {
        getInputIP();
        infoVBox.setVisible(true);


    }
}
