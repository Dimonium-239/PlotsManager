package Code;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


abstract class MyService extends Application{}


public class MainWinController implements Initializable{

    private static String urlForDeleting = null;

    private static Plot plotForEditing = null;

    private int[] PRICES;

    private int[] AREAS;

    private String[] CITIES;

    private static long MAX_PRICE = 0;

    private static long MAX_AREA = 0;

    private  static long slidValPrice = 0;

    private static long slidValArea = 0;

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
    private ScatterChart<Integer, Integer> scatterChart;

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
    private Label priceLabelShow;

    @FXML
    private Label areaLabelShow;

    @FXML
    private Button refresh;

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

    private int[] setIntArr(String field) throws SQLException {
        return new JDBC().getIntElement(field);
    }

    private String[] setStringArr() throws SQLException {
        return new JDBC().getStrElement("City");
    }

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
        try {
            numOfElem = new JDBC().count();
            MAX_AREA = new JDBC().max_area();
            MAX_PRICE = new JDBC().max_price();
            PRICES = setIntArr("Price");
            AREAS = setIntArr("Area");
            CITIES = setStringArr();
        } catch (SQLException ignored) {}

        sliderSettings(priceSlider, MAX_PRICE, MAX_PRICE, MAX_PRICE/2,
                MAX_PRICE/4);

        sliderSettings(areaSlider, MAX_AREA, MAX_AREA, MAX_AREA/2,
                MAX_AREA/4);

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

        priceLabelShow.setText(String.valueOf(MAX_PRICE));
        areaLabelShow.setText(String.valueOf(MAX_AREA));

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
        scatterChart.getData().clear();
        PlotsBase.setItems(new JDBC().getFromBase());
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
                        long tmpPrice, tmpArea;
                        if(enableFiltres.isSelected()){
                            tmpPrice = slidValPrice;
                            tmpArea = slidValArea;
                        }else{
                            tmpPrice = MAX_PRICE;
                            tmpArea = MAX_AREA;
                        }
                        for(int i = 0; i < numOfElem; i++) {
                            if (PRICES[i] < tmpPrice && AREAS[i] < tmpArea) {
                                if (_city.equals("All") || CITIES[i].contains(_city))
                                    dataSeriesCity.getData().add(new XYChart.Data(PRICES[i], AREAS[i]));
                            }
                        }
                        scatterChart.getData().addAll(dataSeriesCity);
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
        //System.out.println(scatterChart.getData().isEmpty());
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
        if(allCitiesBox.isSelected())
            checkBoxSwitcher(false, true);
        else{
            checkBoxSwitcher(false, false);
            katowiceBox.setDisable(true);
        }
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
}