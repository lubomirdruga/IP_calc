package spse;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Converter_Controller implements Initializable
{
    public JFXTextField decimalInput;
    public JFXTextField binaryInput;
    public JFXTextField hexadecimalInput;
    public JFXHamburger hamburger;
    public JFXDrawer drawer;
    public AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("Drawer.fxml"));
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

    }


    public void convertFromDecimal() {

        try {
            int decimal = Integer.parseInt(decimalInput.getText());
            decimalInput.setFocusColor(Color.rgb(63, 90, 168, 1));

            binaryInput.setText(Converter.decToBin(decimal));

            //TODO: Pytat od Mata spravny konverter!!
            // nefunguje od 16 - 99! pri nule hadze error celkovo chybne -_-
//           todo fix hexadecimalInput.setText(Converter.decToHex(Integer.toString(decimal)));



        } catch (NumberFormatException e){

            doCacthErrorBlock(decimalInput);
        }


    }

    public void convertFromBinary() {

        try {
            String input = binaryInput.getText().trim();

            if (input.matches("[01]+"))
            {
                binaryInput.setFocusColor(Color.rgb(63, 90, 168, 1));
                decimalInput.setText(Integer.toString(Converter.binToDEC(input)));
                hexadecimalInput.setText(Converter.binToHex(input).toUpperCase());
            }
            else
                throw new NumberFormatException();

        }
        catch (NumberFormatException e) {

           doCacthErrorBlock(binaryInput);
        }
    }

    public void convertFromHexadecimal() throws Exception {

        try
        {
            String input = hexadecimalInput.getText().trim().toUpperCase();
            if (input.matches("-?[0-9a-fA-F]+"))
            {
                hexadecimalInput.setFocusColor(Color.rgb(63, 90, 168, 1));

                decimalInput.setText(Converter.hexToDec(input)); //todo zistit ci nehadze errory nahodou
                binaryInput.setText(Converter.hexToBin(input));
            }
            else
                throw new NumberFormatException();

        }
        catch (NumberFormatException e)
        {
           doCacthErrorBlock(hexadecimalInput);

        }
    }

    public void clearInputs() {

        decimalInput.clear();
        binaryInput.clear();
        hexadecimalInput.clear();

        decimalInput.setFocusColor(Color.rgb(63, 90, 168, 1));
        binaryInput.setFocusColor(Color.rgb(63, 90, 168, 1));
        hexadecimalInput.setFocusColor(Color.rgb(63, 90, 168, 1));
    }

    private void showSnackBar()
    {
        JFXSnackbar snackbar = new JFXSnackbar(anchorPane);
        snackbar.show("Zadávaný vstup nie je správny", 3000);
    }

    private void doCacthErrorBlock(JFXTextField input)
    {
        if (input.getText().isEmpty())
        {
            input.setFocusColor(Color.rgb(63, 90, 168, 1));
            clearInputs();
        }
        else
        {
            input.setFocusColor(Color.RED);
            showSnackBar();
        }
    }
}
