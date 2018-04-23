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

import java.io.IOException;
import java.net.URL;
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
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            VBox box = FXMLLoader.load(getClass().getResource("../Views/Drawer.fxml"));
            drawer.setSidePane(box);
        }
        catch (IOException ex)
        {
            System.out.println("File 'Drawer.fxml' not found");
        }

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->
        {
            if(drawer.isShown())
            {
                drawer.close();
            }
            else
                drawer.open();
        });
    }

    public void getInputIP()
    {
        try
        {
            IPv6 ipv6;

            String ipv6Address = ipv6AddressInput.getText().trim().toUpperCase();
            int prefix = Integer.parseInt(prefixInput.getText().trim());
            String macAddress = macAddressInput.getText().trim().toUpperCase();

            if(!macAddressInput.getText().trim().isEmpty())
            {
                validateMACAddress(macAddress);

                ipv6 = new IPv6 (ipv6Address,prefix, macAddress);
                ipv6.validateIPv6Address();

                IPv6AllParams = ipv6.allIPv6Param();
            }
            else
            {
                ipv6 = new IPv6 (ipv6Address,prefix, -1);
                ipv6.validateIPv6Address();

                IPv6AllParams = ipv6.allIPv6Param();
            }
            setParameters();
        }
        catch (NumberFormatException e)
        {
            showErrorDialog("Chybne zadaný prefix!");
        }
        catch (IllegalArgumentException e)
        {
            showErrorDialog("Nesprávny tvar IPv6 adresy!");
        }
        catch (IllegalStateException e)
        {
            showErrorDialog("Nesprávny tvar MAC adresy!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setParameters()
    {
        shortenAddressLabel.setText(IPv6AllParams[0]);
        nwAddressLabel.setText(IPv6AllParams[1]);
        prefixLabel.setText(IPv6AllParams[2]);
        unicastAddressLabel.setText(IPv6AllParams[3]);
        linkLocalAddressLabel.setText(IPv6AllParams[4]);
        siteLocalAddressLabel.setText(IPv6AllParams[5]);
        infoVBox.setVisible(true);
    }

    public void clearAll()
    {
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

    public void handleSubmit()
    {
        getInputIP();
    }

    private void showErrorDialog(String error)
    {
        JFXDialogLayout content  = new JFXDialogLayout();
        content.setHeading(new Text("Chyba!"));
        content.setBody(new Text(error));

        JFXDialog errorDialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton closeBtn = new JFXButton("Dobre");

        closeBtn.setOnAction(event -> errorDialog.close());
        content.setActions(closeBtn);
        errorDialog.show();
    }


    private void validateMACAddress(String macAddress)
    {
        if (!macAddress.matches("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$"))
        {
            throw new IllegalStateException();
        }
    }
}
