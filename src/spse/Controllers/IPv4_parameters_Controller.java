package spse.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import spse.Models.IPv4;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IPv4_parameters_Controller implements Initializable {

    public StackPane stackPane;
    public JFXButton clearButton;
    public VBox infoVBox;

    public JFXTextField firstOctet;
    public JFXTextField secondOctet;
    public JFXTextField thirdOctet;
    public JFXTextField fourthOctet;
    public JFXTextField prefix;

    public Label BCaddressLabel;
    public Label prefixLabel;
    public Label firstIPLabel;
    public Label lastIPLabel;
    public Label NWaddressLabel;
    public Label addressCountLabel;
    public Label typeIPLabel;
    public Label maskAddressLabel;
    public Label classIPLabel;
    public Label addressOrderLabel;
    public Label wildcardAddressLabel;
    public Pane content;
    
    public AnchorPane anchorPane;
    public JFXDrawer drawer;
    public JFXHamburger hamburger;

    private boolean validated = false;
    private IPv4 iPv4;

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

    @FXML
    void getInputIP() throws Exception
    {
        int[] ipv4IP = new int[4];

        String textFieldPrefix = prefix.getText().trim();

        String textField0 = firstOctet.getText().trim();
        String textField1 = secondOctet.getText().trim();
        String textField2 = thirdOctet.getText().trim();
        String textField3 = fourthOctet.getText().trim();

        validated = isNumeric(textField0) && isNumeric(textField1) && isNumeric(textField2) && isNumeric(textField3) && isNumeric(textFieldPrefix);

            if (validated)
            {
                validated = false;
                int prefixint = Integer.parseInt(textFieldPrefix);
                ipv4IP[0] = Integer.parseInt(textField0);
                ipv4IP[1] = Integer.parseInt(textField1);
                ipv4IP[2] = Integer.parseInt(textField2);
                ipv4IP[3] = Integer.parseInt(textField3);

                validateIPandPrefix(prefixint, ipv4IP);

                iPv4 = new IPv4(ipv4IP, prefixint);
                iPv4.calculateAllIPinformations();
            }
    }

    private void setParameters()
    {
        NWaddressLabel.setText(iPv4.getDecNW());
        prefixLabel.setText(iPv4.getPrefix());
        BCaddressLabel.setText(iPv4.getDecBC());
        maskAddressLabel.setText(iPv4.getDecMask());
        wildcardAddressLabel.setText(iPv4.getDecWildcard());
        firstIPLabel.setText(iPv4.getDecFirstAddress());
        lastIPLabel.setText(iPv4.getDecLastAddress());

        String addressCount = String.format("%,d", iPv4.getAddressCount());
        addressCount = addressCount.replaceAll(",", " ");
        addressCountLabel.setText(addressCount);

        String addressOrder = String.format("%,d", iPv4.getDecOrder());
        addressOrder = addressOrder.replaceAll(",", " ");
        addressOrderLabel.setText(addressOrder + ".");

        classIPLabel.setText(iPv4.getClassIP());
        typeIPLabel.setText(iPv4.getTypeIP());

        infoVBox.setVisible(true);
        validated = false;
    }

    @FXML
    void handleSubmit()
    {
        try
        {
            getInputIP();
            setParameters();
        }
        catch (Exception e)
        {
            JFXDialogLayout content  = new JFXDialogLayout();
            content.setHeading(new Text("Chyba!"));
            content.setBody(new Text("Zadaný vstup nie je správny.\n" +
                                     "IP adresa nesmie obsahovať písmená a čísla väčšie ako 255!\n" +
                                     "Prefix musí byť menší alebo rovný 32.\n" +
                                     "Takisto nesmie byť pole na zadanie IP adresy a prefixu prázdne."));

            JFXDialog errorDialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton closeBtn = new JFXButton("Dobre");
            closeBtn.setOnAction(event -> errorDialog.close());
            content.setActions(closeBtn);
            errorDialog.show();
        }
    }

    // https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
    private static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    private void validateIPandPrefix(int prefix, int[] ipv4Address) throws Exception
    {
        if (prefix > 32)
            throw new Exception();

        for (int i = 0; i < ipv4Address.length; i++)
            if (ipv4Address[i] > 255)
                throw new Exception();
    }

    public void clearAll()
    {
        prefix.clear();

        firstOctet.clear();
        secondOctet.clear();
        thirdOctet.clear();
        fourthOctet.clear();

        NWaddressLabel.setText("");
        prefixLabel.setText("");
        BCaddressLabel.setText("");
        maskAddressLabel.setText("");
        wildcardAddressLabel.setText("");
        firstIPLabel.setText("");
        lastIPLabel.setText("");
        addressCountLabel.setText("");
        addressOrderLabel.setText("");
        classIPLabel.setText("");
        typeIPLabel.setText("");
        iPv4 = null;
        infoVBox.setVisible(false);
    }
}
