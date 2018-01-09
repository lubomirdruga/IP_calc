package spse;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Controller {

    public AnchorPane anchorPane;
    public StackPane stackPane;
    public JFXButton clearButton;
    public VBox infoVBox;
    public Label addressOrderLabel;
    @FXML
    private JFXButton submitButton;
    @FXML
    private Label wildcardAddressLabel;
    @FXML
    private JFXTextField fourthOctet;
    @FXML
    private Label BCaddressLabel;
    @FXML
    private JFXTextField prefix;
    @FXML
    private Label prefixLabel;
    @FXML
    private JFXTextField thirdOctet;
    @FXML
    private Label firstIPLabel;
    @FXML
    private Label lastIPLabel;
    @FXML
    private Label NWaddressLabel;
    @FXML
    private Label addressCountLabel;
    @FXML
    private JFXTextField secondOctet;
    @FXML
    private Pane NWLabel;
    @FXML
    private Label typeIPLabel;
    @FXML
    private Label maskAddressLabel;
    @FXML
    private Label classIPLabel;
    @FXML
    private JFXTextField firstOctet;

    private boolean validated = false;
    private IPv4 iPv4;



    //todo ku submit btn pridat clear tlacidlo
    //format address count
    //todo show/hide vbox


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

    void setParameters()
    {
        NWaddressLabel.setText(iPv4.getDecNW());
        prefixLabel.setText(iPv4.getPrefix());
        BCaddressLabel.setText(iPv4.getDecBC());
        maskAddressLabel.setText(iPv4.getDecMask());
        wildcardAddressLabel.setText(iPv4.getDecWildcard());
        firstIPLabel.setText(iPv4.getDecFirstAddress());
        lastIPLabel.setText(iPv4.getDecLastAddress());
        addressCountLabel.setText(iPv4.getAddressCount());
        addressOrderLabel.setText(iPv4.getDecOrder());
        classIPLabel.setText(iPv4.getClassIP());
        //todo
        typeIPLabel.setText("");
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


    public static boolean isNumeric(String str)
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

    public void validateIPandPrefix(int prefix, int[] ipv4Address) throws Exception
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
