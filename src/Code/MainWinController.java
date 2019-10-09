package Code;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.xml.crypto.NodeSetData;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;


abstract class MyService extends Application{}

public class MainWinController implements Initializable{

    private static String urlForDeleting = null;

    private static Plot plotForEditing = null;

    private Plot[] PLOTS;

    private static long MAX_PRICE = 0;

    private static long MAX_AREA = 0;

    private static float MAX_KM_FROM = 0;

    private static long MAX_PRICE_PER_METRE = 0;

    private  static long slidValPrice = 0;

    private static long slidValArea = 0;

    private static long slideValPerM = 0;

    private static long slideValKmFrom = 0;

    private int numOfElem = 0;

    @FXML
    public Label kmFromLab;

    @FXML
    private Hyperlink hyperlink;

    @FXML
    private TextField cityField;

    @FXML
    private TextField districtField;

    @FXML
    private TextField streetField;

    @FXML
    public TextField priceField;

    @FXML
    private TextField areaField;

    @FXML
    private TextField pricePerField;

    @FXML
    private TextField kmFromField;

    @FXML
    private TableView<Plot> PlotsBase;

    @FXML
    public TableColumn<Plot, String> URL;

    @FXML
    public TableColumn<Plot, String> cityCol;

    @FXML
    public TableColumn<Plot, Integer> price;

    @FXML
    public TableColumn<Plot, Integer> area;

    @FXML
    private ScatterChart<Number, Number> scatterChart;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private CheckBox allCitiesBox;

    @FXML
    private CheckBox warszawaBox;

    @FXML
    private CheckBox krakowBox;

    @FXML
    private CheckBox lodzBox;

    @FXML
    private CheckBox lublinBox;

    @FXML
    private CheckBox bydgoszczBox;

    @FXML
    private CheckBox katowiceBox;

    @FXML
    private CheckBox poznanBox;

    @FXML
    private CheckBox bialystokBox;

    @FXML
    private CheckBox gdyniaBox;

    @FXML
    private CheckBox gdanskBox;

    @FXML
    private CheckBox wroclawBox;

    @FXML
    private CheckBox szecinBox;

    @FXML
    private CheckBox enableFiltres;

    @FXML
    private CheckBox checkAllBox;

    @FXML
    private Slider priceSlider;

    @FXML
    private Slider areaSlider;

    @FXML
    private Slider pricePerMetreSlider;

    @FXML
    private Slider kmFromSlider;

    @FXML
    private Label priceLabelShow;

    @FXML
    private Label areaLabelShow;

    @FXML
    private Label pricePerMetrShow;

    @FXML
    private Label kmFromShow;

    @FXML
    private Label pricePerMetreSliderLabel;

    @FXML
    private Label kmFromSliderLabel;

    @FXML
    private Label areaSliderLabel;

    @FXML
    private Label priceSliderLabel;

    @FXML
    private Button refresh;

    @FXML
    private RadioButton radioArea;

    @FXML
    private RadioButton radioPricePow;

    private ToggleGroup group = new ToggleGroup();

//    private CheckBox cityCheckBoxGroup[] = {
//        warszawaBox,
//        krakowBox,
//        lodzBox,
//        lublinBox,
//        bydgoszczBox,
//        katowiceBox,
//        poznanBox,
//        bialystokBox,
//        gdyniaBox,
//        gdanskBox,
//        wroclawBox,
//        szecinBox
//};

    private XYChart.Series dataSeriesAllC = new XYChart.Series();
    private XYChart.Series dataSeriesLodz = new XYChart.Series();
    private XYChart.Series dataSeriesKrakow = new XYChart.Series();
    private XYChart.Series dataSeriesBialystok = new XYChart.Series();
    private XYChart.Series dataSeriesGdynia = new XYChart.Series();
    private XYChart.Series dataSeriesKatowice = new XYChart.Series();
    private XYChart.Series dataSeriesLublin = new XYChart.Series();
    private XYChart.Series dataSeriesWarszawa = new XYChart.Series();
    private XYChart.Series dataSeriesGdansk = new XYChart.Series();
    private XYChart.Series dataSeriesBydgoszcz = new XYChart.Series();
    private XYChart.Series dataSeriesPoznan = new XYChart.Series();
    private XYChart.Series dataSeriesWroclaw = new XYChart.Series();
    private XYChart.Series dataSeriesSzczecin = new XYChart.Series();


    public void hyper() {
        openURL();
    }

    private void openURL() {
        hyperlink.setOnAction(e -> {
            MyService myService = new MyService() {
                @Override
                public void start(Stage stage) {
                }
            };
            myService.getHostServices().showDocument(hyperlink.getText());
        });
    }
    /*
    private int[] setIntArr(String field) throws SQLException {
        return new JDBC().getIntElement(field);
    }

    private float[] setKmAtr() throws SQLException{
        return new JDBC().getFloatElement("KmFrom");
    }

    private String[] setStringArr() throws SQLException {
        return new JDBC().getStrElement("City");
    }
*/
    private void sliderSettings(Slider slider, long max, long value, long majUnit, long inc){
            slider.setMin((long) 0);
            slider.setMax(max);
            slider.setValue(value);
            slider.setShowTickLabels(true);
            slider.setMajorTickUnit(majUnit);
            slider.setShowTickMarks(true);
            //slider.setMinorTickCount((int) minUnit);
            slider.setBlockIncrement(inc);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh.setStyle("  -fx-border-style: none; -fx-border-width: 3px; -fx-border-insets: 0; -fx-font-size:4px; -fx-background-image: url('FXML/Styles/Refresh-JPG-Picture.jpg')");
        xAxis.setLabel("Price");
        yAxis.setLabel("Area");
        yAxis.setTickLabelRotation(-90);
        scatterChart.setAnimated(true);

        dataSeriesLodz.setName("Łódż");
        dataSeriesBialystok.setName("Białystok");
        dataSeriesGdynia.setName("Gdynia");
        dataSeriesKatowice.setName("Katowice");
        dataSeriesKrakow.setName("Kraków");
        dataSeriesLublin.setName("Lublin");
        dataSeriesWarszawa.setName("Warszawa");
        dataSeriesGdansk.setName("Gdańsk");
        dataSeriesBydgoszcz.setName("Bydgoszcz");
        dataSeriesPoznan.setName("Poznań");
        dataSeriesWroclaw.setName("Wrocław");
        dataSeriesSzczecin.setName("Szczcin");

        priceField.setTooltip(new Tooltip(priceField.getText()));

        try {
            PLOTS = new JDBC().getFromBase().toArray(new Plot[0]);
            numOfElem = new JDBC().count();
            MAX_AREA = new JDBC().max_area();
            MAX_PRICE = new JDBC().max_price();
            MAX_KM_FROM = new JDBC().max_kmFrom();

            double tmpPrcPerMetre[] = new double[PLOTS.length];
            for(int i = 0; i < PLOTS.length; i++){
                tmpPrcPerMetre[i] = PLOTS[i].getPricePerMSquare();
            }
            MAX_PRICE_PER_METRE = (long) Arrays.stream(tmpPrcPerMetre).max().getAsDouble();

        } catch (SQLException ignored) {}

        sliderSettings(priceSlider, MAX_PRICE, MAX_PRICE, MAX_PRICE/2,
                MAX_PRICE/4);

        sliderSettings(areaSlider, MAX_AREA, MAX_AREA, MAX_AREA/2,
                MAX_AREA/4);

        sliderSettings(pricePerMetreSlider, MAX_PRICE_PER_METRE, MAX_PRICE_PER_METRE, MAX_PRICE_PER_METRE/2,
                MAX_PRICE_PER_METRE/4);

        sliderSettings(kmFromSlider, (long) MAX_KM_FROM, (long) MAX_KM_FROM, (long) MAX_KM_FROM/2, (long) MAX_KM_FROM/4);

        priceSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    priceSlider.setValue(Math.round(newValue.doubleValue()));
                    slidValPrice = Math.round(newValue.doubleValue());
                });

        areaSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    areaSlider.setValue(Math.round(newValue.doubleValue()));
                    slidValArea = Math.round(newValue.doubleValue());
                });

        pricePerMetreSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    pricePerMetreSlider.setValue(Math.round(newValue.doubleValue()));
                    slideValPerM= Math.round(newValue.doubleValue());
                });

        kmFromSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    kmFromSlider.setValue(Math.round(newValue.doubleValue()));
                    slideValKmFrom= Math.round(newValue.doubleValue());
                });

        priceLabelShow.setText(String.valueOf(MAX_PRICE));
        areaLabelShow.setText(String.valueOf(MAX_AREA));
        kmFromShow.setText(String.valueOf(MAX_KM_FROM));
        pricePerMetrShow.setText(String.valueOf(MAX_PRICE_PER_METRE));

        radioArea.setToggleGroup(group);
        radioArea.setSelected(true);
        radioPricePow.setToggleGroup(group);

        pricePerMetreSlider.setVisible(false);
        kmFromSlider.setVisible(false);
        pricePerMetreSliderLabel.setVisible(false);
        kmFromSliderLabel.setVisible(false);
        pricePerMetrShow.setVisible(false);
        kmFromShow.setVisible(false);

        URL.setCellValueFactory(new PropertyValueFactory<>("URL"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("City"));
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        area.setCellValueFactory(new PropertyValueFactory<>("Area"));

        URL.setCellFactory(TooltippedTableCell.forTableColumn());
        cityCol.setCellFactory(TooltippedTableCell.forTableColumn());
        StringConverter<Integer> strConv = new StringConverter<>() {
            @Override
            public String toString(Integer integer) {
                return String.valueOf(integer);
            }

            @Override
            public Integer fromString(String s) {
                return Integer.valueOf(s);
            }
        };
        price.setCellFactory(TooltippedTableCell.forTableColumn(strConv));
        area.setCellFactory(TooltippedTableCell.forTableColumn(strConv));

        try {
            PlotsBase.setItems(new JDBC().getFromBase());
        } catch (SQLException ignored) {}

        PlotsBase.setRowFactory(tv -> {
            TableRow<Plot> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Plot rowData = row.getItem();
                    fillingFields(rowData);
                } else if (!row.isEmpty() && event.getButton()==MouseButton.SECONDARY
                        && event.getClickCount() == 1) {
                    Plot rowData = row.getItem();
                    plotForEditing = rowData;
                    urlForDeleting = rowData.getURL();
                }
            });
            return row;
        });
    }

    private void fillingFields(Plot rowTemp) {
        hyperlink.setText(rowTemp.getURL());
        cityField.setText(String.valueOf(rowTemp.getCity()));
        districtField.setText(String.valueOf(rowTemp.getDistrict()));
        streetField.setText(String.valueOf(rowTemp.getStreet()));
        priceField.setText(String.valueOf(rowTemp.getPrice()));
        areaField.setText(String.valueOf(rowTemp.getArea()));
        pricePerField.setText(String.valueOf((float)rowTemp.getPrice()/rowTemp.getArea()));
        if (rowTemp.getKmFromCentre() != 0) {
            kmFromField.setText(String.valueOf(rowTemp.getKmFromCentre()));
            kmFromField.setVisible(true);
            kmFromLab.setVisible(true);
        } else {
            kmFromField.setText("");
            kmFromField.setVisible(false);
            kmFromLab.setVisible(false);
        }
    }

    public void openAddingWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("FXML/addingWin.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 250, 244);
            Stage stage = new Stage();
            stage.setTitle("Add new plot");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ignored) {
        }
    }

    public void refresh() throws SQLException {
        System.out.println("refreshing");
        checkBoxSwitcher(false, false);
        allCitiesBox.setSelected(false);
        PlotsBase.setItems(new JDBC().getFromBase());
        System.out.println(PLOTS.equals(new JDBC().getFromBase().toArray(new Plot[0])));
        PLOTS = new JDBC().getFromBase().toArray(new Plot[0]);
        scatterChart.getData().clear();
    }

    public void deleteRow() throws SQLException {
        new JDBC().deleteFromBase(urlForDeleting);
    }

    public void closeProgram() {
        System.exit(0);
    }

    public void editRow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("FXML/editingWin.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 250, 244);
            Stage stage = new Stage();
            stage.setTitle("Edit plot");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ignored) {
        }
    }

    static Plot getPlotForEditing(){
        return plotForEditing;
    }

    public void refreshDB(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("FXML/refreshingWin.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 325, 300);
            Stage stage = new Stage();
            stage.setTitle("Refreshing");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ignored) {
        }
    }

    private void dataNavigator(CheckBox cityBox, XYChart.Series dataSeriesCity, String _city){
                try {
                    dataSeriesCity.getData().clear();
                    if (cityBox.isSelected()) {
                        long tmpPrice, tmpArea, tmpKmFrom, tmpPricePerM;
                        if (enableFiltres.isSelected()) {
                            tmpPrice = slidValPrice;
                            tmpArea = slidValArea;
                            tmpKmFrom = slideValKmFrom;
                            tmpPricePerM = slideValPerM;
                        } else {
                            tmpPrice = MAX_PRICE;
                            tmpArea = MAX_AREA;
                            tmpKmFrom = (long) MAX_KM_FROM;
                            tmpPricePerM = MAX_PRICE_PER_METRE;
                        }
                        for (int i = 0; i < numOfElem; i++) {
                            if (radioPricePow.equals(group.getSelectedToggle())) {
                                if (PLOTS[i].getKmFromCentre() != 0) {
                                    if (PLOTS[i].getKmFromCentre() < tmpKmFrom && (PLOTS[i].getPricePerMSquare()) < tmpPricePerM) {
                                        if (_city.equals("All") || PLOTS[i].getCity().contains(_city)) {
                                            dataSeriesCity.getData().add(new XYChart.Data(PLOTS[i].getPricePerMSquare(), PLOTS[i].getKmFromCentre(), PLOTS[i]));
                                        }
                                    }
                                }
                            } else if (radioArea.equals(group.getSelectedToggle())) {
                                if (PLOTS[i].getPrice() < tmpPrice && PLOTS[i].getArea() < tmpArea) {
                                    if (_city.equals("All") || PLOTS[i].getCity().contains(_city)) {
                                        dataSeriesCity.getData().add(new XYChart.Data(PLOTS[i].getPrice(), PLOTS[i].getArea(), PLOTS[i]));
                                    }
                                }
                            }
                        }
                        scatterChart.getData().addAll(dataSeriesCity);
                        scatterChart.setCursor(Cursor.CROSSHAIR);
                    }
                    for (XYChart.Series<Number, Number> s : scatterChart.getData()) {
                        for (XYChart.Data<Number, Number> d : s.getData()) {

                            d.getNode().setOnMouseEntered(event -> {
                                d.getNode().getStyleClass().add("onHover");
                                Plot selectedPlot = (Plot) d.getExtraValue();
                                plotForEditing = selectedPlot;
                                fillingFields(selectedPlot);
                                System.out.println(d);
                            });

                            d.getNode().setOnMouseClicked(mouseEvent -> {
                                if (mouseEvent.getButton().equals(MouseButton.SECONDARY) && mouseEvent.getClickCount() == 2) {
                                    editRow();
                                }
                                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                                    MyService myService = new MyService() {
                                        @Override
                                        public void start(Stage stage) {
                                        }
                                    };
                                    myService.getHostServices().showDocument(plotForEditing.getURL());
                                }
                            });

                            d.getNode().setOnMouseExited(event -> {
                                d.getNode().getStyleClass().remove("onHover");
                            });
                        }
                    }
                    if (!cityBox.isSelected())
                        scatterChart.getData().remove(dataSeriesCity);
        }catch (IllegalArgumentException | NullPointerException ignored){}
    }


    private void allCitiesSetSelected(boolean isSelected, boolean isDisable){
        checkBoxSwitcher(isSelected, isDisable);
        warszawaBox();
        krakowBox();
        lodzBox();
        bialystokBox();
        katowiceBox();
        poznanBox();
        bialystokBox();
        gdyniaBox();
        gdanskBox();
        wroclawBox();
        szecinBox();
        lublinBox();

    }

    public void checkAll() {
        if(checkAllBox.isSelected()) {
            allCitiesSetSelected(true, false);
        }else if(!checkAllBox.isSelected()){
            allCitiesSetSelected(false, false);
        }
    }

    public void clearScatter() {
        System.out.println(slidValArea);
        System.out.println(slidValPrice);
    }

    public void warszawaBox() { dataNavigator(warszawaBox, dataSeriesWarszawa, "Warszawa"); }

    public void krakowBox() { dataNavigator(krakowBox, dataSeriesKrakow, "Krak"); }

    public void lodzBox() { dataNavigator(lodzBox, dataSeriesLodz, "Łód"); }

    public void bydgoszczBox() { dataNavigator(bydgoszczBox, dataSeriesBydgoszcz, "Bydgoszcz"); }

    public void katowiceBox() { dataNavigator(katowiceBox, dataSeriesKatowice, "Katowice"); }

    public void poznanBox() { dataNavigator(poznanBox, dataSeriesPoznan, "Pozna"); }

    public void bialystokBox() { dataNavigator(bialystokBox, dataSeriesBialystok, "Białystok"); }

    public void gdyniaBox() { dataNavigator(gdyniaBox, dataSeriesGdynia, "Gdynia"); }

    public void gdanskBox() { dataNavigator(gdanskBox, dataSeriesGdansk, "Gda"); }

    public void wroclawBox() { dataNavigator(wroclawBox, dataSeriesWroclaw, "Wroc"); }

    public void szecinBox() { dataNavigator(szecinBox, dataSeriesSzczecin, "Szczecin"); }

    public void lublinBox() { dataNavigator(lublinBox, dataSeriesLublin, "Lublin");}

    private void checkBoxSwitcher(boolean isSelect, boolean isDisable){
        warszawaBox.setSelected(isSelect);
        warszawaBox.setDisable(isDisable);
        krakowBox.setSelected(isSelect);
        krakowBox.setDisable(isDisable);
        lodzBox.setSelected(isSelect);
        lodzBox.setDisable(isDisable);
        gdyniaBox.setSelected(isSelect);
        gdyniaBox.setDisable(isDisable);
        gdanskBox.setSelected(isSelect);
        gdanskBox.setDisable(isDisable);
        wroclawBox.setSelected(isSelect);
        wroclawBox.setDisable(isDisable);
        bydgoszczBox.setSelected(isSelect);
        bydgoszczBox.setDisable(isDisable);
        szecinBox.setSelected(isSelect);
        szecinBox.setDisable(isDisable);
        katowiceBox.setSelected(isSelect);
        katowiceBox.setDisable(isDisable);
        poznanBox.setSelected(isSelect);
        poznanBox.setDisable(isDisable);
        lublinBox.setSelected(isSelect);
        lublinBox.setDisable(isDisable);
        bialystokBox.setSelected(isSelect);
        bialystokBox.setDisable(isDisable);
        checkAllBox.setSelected(isSelect);
        checkAllBox.setDisable(isDisable);

    }

    public void allCitiesBox() {
        dataNavigator(allCitiesBox, dataSeriesAllC, "All");
    }

    public void priceSliderDrag() {
        priceLabelShow.setText(String.valueOf(slidValPrice));
        if(slidValPrice < MAX_PRICE/90)
            priceSlider.setMax(MAX_PRICE/90);
        else if(slidValArea >= MAX_PRICE/90)
            priceSlider.setMax(MAX_PRICE);
    }

    public void areaSliderDrag() {
        areaLabelShow.setText(String.valueOf(slidValArea));
        if(slidValArea < MAX_AREA/100)
            areaSlider.setMax(MAX_AREA/100);
        else if(slidValArea >= MAX_AREA/100)
            areaSlider.setMax(MAX_AREA);
    }

    private void hideByRaidio(boolean switcher){
        priceSlider.setVisible(switcher);
        areaSlider.setVisible(switcher);
        priceSliderLabel.setVisible(switcher);
        areaSliderLabel.setVisible(switcher);
        priceLabelShow.setVisible(switcher);
        areaLabelShow.setVisible(switcher);

        pricePerMetreSlider.setVisible(!switcher);
        kmFromSlider.setVisible(!switcher);
        pricePerMetreSliderLabel.setVisible(!switcher);
        kmFromSliderLabel.setVisible(!switcher);
        pricePerMetrShow.setVisible(!switcher);
        kmFromShow.setVisible(!switcher);

        enableFiltres.setSelected(false);

    }

    public void pricePowSelected() throws SQLException {
        xAxis.setLabel("Price per m^2");
        yAxis.setLabel("Km from centre");

        hideByRaidio(false);
        checkBoxSwitcher(false, false);
        allCitiesBox.setSelected(false);
        refresh();
    }

    public void areaSelected() throws SQLException {
        xAxis.setLabel("Price");
        yAxis.setLabel("Area");

        hideByRaidio(true);
        checkBoxSwitcher(false, false);
        allCitiesBox.setSelected(false);

        refresh();
    }

    public void pricePerMSliderDrag() {
        pricePerMetrShow.setText(String.valueOf(slideValPerM));
    }

    public void kmFromSliderDrag() {
        kmFromShow.setText(String.valueOf(slideValKmFrom));
    }

//    public void getPoint(MouseEvent mouseEvent) {
//        for (final Object series : dataSeriesLodz.getData()) {
//                Tooltip tooltip = new Tooltip();
//                tooltip.setText(series.toString());
//                Tooltip.install((Node) series, tooltip);
//            }
//        }
}
