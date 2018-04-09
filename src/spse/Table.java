package spse;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Table implements Initializable{
//todo rename

    public JFXHamburger hamburger;
    public JFXDrawer drawer;
    @FXML
    private JFXTreeTableView<Subnet> table;
    private static IPv4[] items;

    public static void setItems(IPv4[] finalSubnets) {
        items = finalSubnets;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

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


//todo do SK !

        JFXTreeTableColumn netNameColumn = new JFXTreeTableColumn("Sub,net name");
        netNameColumn.setPrefWidth(100);
        netNameColumn.setSortable(false);
        netNameColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().netName);

        JFXTreeTableColumn neededSizeColumn = new JFXTreeTableColumn("Needed size");
        neededSizeColumn.setPrefWidth(100);
        neededSizeColumn.setSortable(false);
        neededSizeColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().neededSize);

        JFXTreeTableColumn allocatedSizeColumn = new JFXTreeTableColumn("Allocated size");
        allocatedSizeColumn.setPrefWidth(100);
        allocatedSizeColumn.setSortable(false);
        allocatedSizeColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().allocatedSize);

        JFXTreeTableColumn nwAddressColumn = new JFXTreeTableColumn("NW address");
        nwAddressColumn.setPrefWidth(150);
        nwAddressColumn.setSortable(false);
        nwAddressColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().nwAddress);

        JFXTreeTableColumn prefixColumn = new JFXTreeTableColumn("Prefix");
        prefixColumn.setPrefWidth(100);
        prefixColumn.setSortable(false);
        prefixColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().prefix);

        JFXTreeTableColumn maskColumn = new JFXTreeTableColumn("Mask");
        maskColumn.setPrefWidth(150);
        maskColumn.setSortable(false);
        maskColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().mask);

        JFXTreeTableColumn rangeColumn = new JFXTreeTableColumn("Range");
        rangeColumn.setPrefWidth(200);
        rangeColumn.setSortable(false);
        rangeColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().range);

        JFXTreeTableColumn bcAddressColumn = new JFXTreeTableColumn("BC address");
        bcAddressColumn.setPrefWidth(150);
        bcAddressColumn.setSortable(false);
        bcAddressColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().bcAddress);



        ObservableList<Subnet> subnets = FXCollections.observableArrayList();

        for (IPv4 item : items) {
            subnets.add(new Subnet(item.getName(),item.getNeededSize(), item.getAllocatedSize(), item.getDecNW(), item.getDecBC(), item.getPrefix(), item.getDecMask(), item.getDecFirstAddress() + " - " + item.getDecLastAddress()));
        }


        final TreeItem<Subnet> root = new RecursiveTreeItem<>(subnets, RecursiveTreeObject::getChildren);

        //TODO pridat stlpec!!
        table.getColumns().setAll(netNameColumn, neededSizeColumn, allocatedSizeColumn, nwAddressColumn, bcAddressColumn, prefixColumn, maskColumn, rangeColumn);
        table.setRoot(root);
        table.setShowRoot(false);
    }

    class Subnet extends RecursiveTreeObject<Subnet>{

        StringProperty netName, nwAddress, bcAddress, prefix, neededSize, allocatedSize, mask, range;


        public Subnet(String netName, int neededSize, int allocatedSize, String nwAddress, String bcAddress, String prefix, String mask, String range)
        {
            this.netName = new SimpleStringProperty(netName);
            this.neededSize = new SimpleStringProperty(Integer.toString(neededSize));
            this.allocatedSize = new SimpleStringProperty(Integer.toString(allocatedSize - 2));
            this.nwAddress = new SimpleStringProperty(nwAddress);
            this.bcAddress  = new SimpleStringProperty(bcAddress);
            this.prefix = new SimpleStringProperty("/" + prefix);
            this.mask = new SimpleStringProperty(mask);
            this.range = new SimpleStringProperty(range);

        }

    }

}
