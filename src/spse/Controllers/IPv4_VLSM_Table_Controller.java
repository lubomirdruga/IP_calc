package spse.Controllers;

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
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import spse.Models.IPv4;
import spse.Models.IPv4VLSM;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IPv4_VLSM_Table_Controller implements Initializable
{
//todo rename

    public JFXHamburger hamburger;
    public JFXDrawer drawer;
    public JFXButton guiBtn;
    public StackPane stackPane;
    public Label supernetAddressLabel;
    public Label supernetAvailableCountLabel;
    public Label neededAddressesLabel;
    @FXML
    private JFXTreeTableView<Subnet> table;
    private static IPv4[] items;
    private static IPv4VLSM vlsm;

    public static void setVlsm(IPv4VLSM vlsmObj) {
        vlsm = vlsmObj;
    }

    public static void setItems(IPv4[] finalSubnets) {
        items = finalSubnets;
    }

    //TODO skontrolovat tabulku alokovana a potrebna velkost, MUSI sediet s vysledkami hore


    // https://drive.google.com/drive/folders/0B_nK3WmoczMgTFhPWmZfby1pQ0k
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

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


        JFXTreeTableColumn netNameColumn = new JFXTreeTableColumn("Názov siete");
//        netNameColumn.setPrefWidth(100);
        netNameColumn.setSortable(false);
        netNameColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().netName);

        JFXTreeTableColumn neededSizeColumn = new JFXTreeTableColumn("Potrebná veľkosť");
        neededSizeColumn.setSortable(false);
        neededSizeColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().neededSize);

        JFXTreeTableColumn allocatedSizeColumn = new JFXTreeTableColumn("Alokovaná veľkosť");
        allocatedSizeColumn.setSortable(false);
        allocatedSizeColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().allocatedSize);

        JFXTreeTableColumn nwAddressColumn = new JFXTreeTableColumn("Sieťová adresa");
//        nwAddressColumn.setPrefWidth(150);
        nwAddressColumn.setSortable(false);
        nwAddressColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().nwAddress);

        JFXTreeTableColumn prefixColumn = new JFXTreeTableColumn("Prefix");
        prefixColumn.setSortable(false);
        prefixColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().prefix);

        JFXTreeTableColumn maskColumn = new JFXTreeTableColumn("Maska");
//        maskColumn.setPrefWidth(150);
        maskColumn.setSortable(false);
        maskColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().mask);

        JFXTreeTableColumn rangeColumn = new JFXTreeTableColumn("Použiteľný rozsah");
//        rangeColumn.setPrefWidth(200);
        rangeColumn.setSortable(false);
        rangeColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().range);

        JFXTreeTableColumn bcAddressColumn = new JFXTreeTableColumn("Broadcastová adresa");
//        bcAddressColumn.setPrefWidth(150);
        bcAddressColumn.setSortable(false);
        bcAddressColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().bcAddress);


        ObservableList<Subnet> subnets = FXCollections.observableArrayList();

        for (IPv4 item : items)
            subnets.add(new Subnet(item.getName(),item.getNeededSize(), item.getAllocatedSize(), item.getDecNW(), item.getDecBC(), item.getPrefix(), item.getDecMask(), item.getDecFirstAddress() + " - " + item.getDecLastAddress()));


        final TreeItem<Subnet> root = new RecursiveTreeItem<>(subnets, RecursiveTreeObject::getChildren);

        table.getColumns().setAll(netNameColumn, neededSizeColumn, allocatedSizeColumn, nwAddressColumn, bcAddressColumn, prefixColumn, maskColumn, rangeColumn);
        table.setRoot(root);
        table.setShowRoot(false);
        //todo predsa len prerobit na prefWidth
        table.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);
        neededSizeColumn.setPrefWidth(130);
        allocatedSizeColumn.setPrefWidth(130);
        prefixColumn.setPrefWidth(85);


        supernetAddressLabel.setText(vlsm.getSuperNetAddress());

        String supernetCount = String.format("%,d", vlsm.getSupernetHostsCount());
        supernetCount = supernetCount.replaceAll(",", " ");
        supernetAvailableCountLabel.setText(supernetCount);

        String needAddCount = String.format("%,d", vlsm.getSubnetsSum());
        needAddCount = needAddCount.replaceAll(",", " ");
        neededAddressesLabel.setText(needAddCount);
    }

    public void showPieChart() {

        HBox supernet = new HBox();
        supernet.setPrefSize(400,400);

        PieChart pieChart = new PieChart();

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        for (IPv4 item : items) {
            String name = item.getName();
            double value = item.getAllocatedSize();
            data.addAll(new PieChart.Data(name, value));
        }

        data.add(new PieChart.Data("nevyužité adresy", (vlsm.getSupernetHostsCount() - vlsm.getSpaceNeeded())));

        pieChart.setData(data);
        pieChart.setLegendSide(Side.BOTTOM);

        supernet.getChildren().add(pieChart);

        JFXDialogLayout content  = new JFXDialogLayout();
        content.setHeading(new Text("Grafické znázornenie využitia supernet siete"));
        content.setBody(supernet);

        JFXDialog pieChartDialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton closeBtn = new JFXButton("Dobre");
        closeBtn.setOnAction(event -> pieChartDialog.close());
        content.setActions(closeBtn);
        pieChartDialog.show();
    }

    public void showSupernetInfo() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../Views/IPv4_parameters.fxml"));




        JFXDialogLayout content  = new JFXDialogLayout();
//        content.setHeading(new Text("Grafické znázornenie využitia supernet siete"));
        content.setBody(new SubScene(root, 460,720));


        int[] supenetIP = vlsm.getSupernetIPv4().clone();

        IPv4_parameters_Controller ctrl = new IPv4_parameters_Controller();

        ctrl.firstOctet = new JFXTextField(Integer.toString(supenetIP[0]));
//        ctrl.secondOctet.setText(Integer.toString(supenetIP[1]));
//        ctrl.thirdOctet.setText(Integer.toString(supenetIP[2]));
//        ctrl.fourthOctet.setText(Integer.toString(supenetIP[3]));
//        ctrl.prefix.setText(Integer.toString(vlsm.getSuperNetPrefix()));
//
//        ctrl.handleSubmit();

        JFXDialog supernetParametersDialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton closeBtn = new JFXButton("Dobre");
        closeBtn.setOnAction(event -> supernetParametersDialog.close());
        content.setActions(closeBtn);
        supernetParametersDialog.show();
    }

    class Subnet extends RecursiveTreeObject<Subnet>
    {
        StringProperty netName, nwAddress, bcAddress, prefix, neededSize, allocatedSize, mask, range;

        public Subnet(String netName, int neededSize, int allocatedSize, String nwAddress, String bcAddress, String prefix, String mask, String range)
        {
            this.netName = new SimpleStringProperty(netName);
            this.neededSize = new SimpleStringProperty(Integer.toString(neededSize));
            this.allocatedSize = new SimpleStringProperty(Integer.toString(allocatedSize));
            this.nwAddress = new SimpleStringProperty(nwAddress);
            this.bcAddress  = new SimpleStringProperty(bcAddress);
            this.prefix = new SimpleStringProperty("/" + prefix);
            this.mask = new SimpleStringProperty(mask);
            this.range = new SimpleStringProperty(range);
        }
    }
}
