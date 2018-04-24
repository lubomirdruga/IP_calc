package sk.spse.pcoz.druga_olsavsky.Controllers;

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
import sk.spse.pcoz.druga_olsavsky.Models.IPv4;
import sk.spse.pcoz.druga_olsavsky.Models.IPv4VLSM;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class IPv4_VLSM_Table_Controller implements Initializable
{

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
        netNameColumn.setSortable(false);
        netNameColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().netName);

        JFXTreeTableColumn neededSizeColumn = new JFXTreeTableColumn("Potrebná veľkosť");
        neededSizeColumn.setPrefWidth(140);
        neededSizeColumn.setSortable(false);
        neededSizeColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().neededSize);

        JFXTreeTableColumn allocatedSizeColumn = new JFXTreeTableColumn("Alokovaná veľkosť");
        allocatedSizeColumn.setPrefWidth(140);
        allocatedSizeColumn.setSortable(false);
        allocatedSizeColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().allocatedSize);

        JFXTreeTableColumn nwAddressColumn = new JFXTreeTableColumn("Sieťová adresa");
        nwAddressColumn.setPrefWidth(140);
        nwAddressColumn.setSortable(false);
        nwAddressColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().nwAddress);

        JFXTreeTableColumn prefixColumn = new JFXTreeTableColumn("Prefix");
        prefixColumn.setPrefWidth(66);
        prefixColumn.setSortable(false);
        prefixColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().prefix);

        JFXTreeTableColumn maskColumn = new JFXTreeTableColumn("Maska");
        maskColumn.setPrefWidth(150);
        maskColumn.setSortable(false);
        maskColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().mask);

        JFXTreeTableColumn rangeColumn = new JFXTreeTableColumn("Použiteľný rozsah");
        rangeColumn.setPrefWidth(250);
        rangeColumn.setSortable(false);
        rangeColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().range);

        JFXTreeTableColumn bcAddressColumn = new JFXTreeTableColumn("Broadcastová adresa");
        bcAddressColumn.setSortable(false);
        bcAddressColumn.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures<Subnet, String>, ObservableValue<String>>) param -> param.getValue().getValue().bcAddress);


        ObservableList<Subnet> subnets = FXCollections.observableArrayList();

        for (IPv4 item : items)
            subnets.add(new Subnet(item.getName(),item.getNeededSize(), item.getAllocatedSize(), item.getDecNW(), item.getDecBC(), item.getPrefix(), item.getDecMask(), item.getDecFirstAddress() + " - " + item.getDecLastAddress()));


        final TreeItem<Subnet> root = new RecursiveTreeItem<>(subnets, RecursiveTreeObject::getChildren);

        table.getColumns().setAll(netNameColumn, neededSizeColumn, allocatedSizeColumn, nwAddressColumn, bcAddressColumn, prefixColumn, maskColumn, rangeColumn);
        table.setRoot(root);
        table.setShowRoot(false);

        supernetAddressLabel.setText(vlsm.getSuperNetAddress() + "/" + vlsm.getSuperNetPrefix());

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

    public void showSupernetInfo() {

        String[] address = vlsm.getSuperNetAddress().split(Pattern.quote("."));
        int[] decAddress = new int[4];

        for (int i = 0; i < decAddress.length; i++)
            decAddress[i] = Integer.parseInt(address[i]);


        IPv4 params = new IPv4(decAddress, vlsm.getSuperNetPrefix());

        params.calculateAllIPinformations();

        HBox[] hBox = new HBox[11];

        Label[] labelsLeft = new Label[11];

        labelsLeft[0] = new Label("NW adresa: ");
        labelsLeft[1] = new Label("Prefix: ");
        labelsLeft[2] = new Label("BC adresa: ");
        labelsLeft[3] = new Label("Maska: ");
        labelsLeft[4] = new Label("Wildcard: ");
        labelsLeft[5] = new Label("Prvá použiteľná IP: ");
        labelsLeft[6] = new Label("Posledná použiteľná IP: ");
        labelsLeft[7] = new Label("Počet adries: ");
        labelsLeft[8] = new Label("Poradie adresy: ");
        labelsLeft[9] = new Label("Trieda IP adresy: ");
        labelsLeft[10] = new Label("Typ IP adresy: ");

        Label[] labelsRight = new Label[11];

        labelsRight[0] = new Label(params.getDecNW());
        labelsRight[1] = new Label(params.getPrefix());
        labelsRight[2] = new Label(params.getDecBC());
        labelsRight[3] = new Label(params.getDecMask());
        labelsRight[4] = new Label(params.getDecWildcard());
        labelsRight[5] = new Label(params.getDecFirstAddress());
        labelsRight[6] = new Label(params.getDecLastAddress());

        String addressCount = String.format("%,d", params.getAddressCount());
        addressCount = addressCount.replaceAll(",", " ");
        labelsRight[7] = new Label(addressCount);

        String addressOrder = String.format("%,d", params.getDecOrder());
        addressOrder = addressOrder.replaceAll(",", " ");
        labelsRight[8] = new Label(addressOrder + ".");

        labelsRight[9] = new Label(params.getClassIP());
        labelsRight[10] = new Label(params.getTypeIP());


        for (int i = 0; i < labelsLeft.length; i++) {

            labelsLeft[i].setPrefSize(172,41);
            labelsRight[i].setPrefSize(260, 41);

            hBox[i] = new HBox();
            hBox[i].getChildren().addAll(labelsLeft[i], labelsRight[i]);
        }

        VBox ipInfoBox = new VBox();
        ipInfoBox.getChildren().addAll(hBox);

        JFXDialogLayout content  = new JFXDialogLayout();
        content.setHeading(new Text("Parametre adresy " + vlsm.getSuperNetAddress() + " /" + vlsm.getSuperNetPrefix()));
        content.setBody(ipInfoBox);

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
