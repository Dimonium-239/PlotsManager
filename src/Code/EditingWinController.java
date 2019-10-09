package Code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.sql.SQLException;

import static Code.MainWinController.getPlotForEditing;

public class EditingWinController {

    private String URLKey = null;

    private Plot plotForEditing = new Plot(); //Link.getText(), City.getText(), District.getText(), Street.getText(), Integer.valueOf(Price.getText()), Integer.valueOf(Area.getText())

    @FXML
    private TextField linkEdField;

    @FXML
    private TextField cityEdField;

    @FXML
    private TextField districtEdField;

    @FXML
    private TextField streetEdField;

    @FXML
    private TextField priceEdField;

    @FXML
    private TextField areaEdField;

    @FXML
    private TextField kmFromEdField;

    public void initialize() {
        plotForEditing = getPlotForEditing();
        URLKey = plotForEditing.getURL();
        fillingFields();
    }

    private void fillingFields() {
        linkEdField.setText(plotForEditing.getURL());
        cityEdField.setText(plotForEditing.getCity());
        districtEdField.setText(plotForEditing.getDistrict());
        streetEdField.setText(plotForEditing.getStreet());
        priceEdField.setText(String.valueOf(plotForEditing.getPrice()));
        areaEdField.setText(String.valueOf(plotForEditing.getArea()));
        kmFromEdField.setText(String.valueOf(plotForEditing.getKmFromCentre()));
    }

    public void discard(ActionEvent actionEvent) {
        fillingFields();
        MessageWindows.alertWindow(actionEvent, "Message", "Do you want filling textfields\nagain or close window?",
                "Choose your option.", "Continue", "Close");
    }

    public void accept(ActionEvent actionEvent) throws SQLException {
        Plot finalPlot = new Plot(linkEdField.getText(), cityEdField.getText(), districtEdField.getText(), streetEdField.getText(), Integer.valueOf(priceEdField.getText()), Integer.valueOf(areaEdField.getText()));

        if(linkEdField.getText() == null || linkEdField.getText().trim().isEmpty())
            finalPlot.setURL(plotForEditing.getURL());
        else finalPlot.setURL(linkEdField.getText());

        if(cityEdField.getText() == null || cityEdField.getText().trim().isEmpty())
            finalPlot.setCity(plotForEditing.getCity());
        else finalPlot.setCity(cityEdField.getText());

        if(districtEdField.getText() == null || districtEdField.getText().trim().isEmpty())
            finalPlot.setDistrict(plotForEditing.getDistrict());
        else finalPlot.setDistrict(districtEdField.getText());

        if(streetEdField.getText() == null || streetEdField.getText().trim().isEmpty())
            finalPlot.setStreet(plotForEditing.getStreet());
        else finalPlot.setStreet(streetEdField.getText());

        if(priceEdField.getText() == null || priceEdField.getText().trim().isEmpty())
            finalPlot.setPrice(plotForEditing.getPrice());
        else finalPlot.setPrice(Integer.parseInt(priceEdField.getText()));

        if(areaEdField.getText() == null || areaEdField.getText().trim().isEmpty())
            finalPlot.setArea(plotForEditing.getArea());
        else finalPlot.setArea(Integer.parseInt(areaEdField.getText()));

        if(kmFromEdField.getText() == null || kmFromEdField.getText().trim().isEmpty())
            finalPlot.setKmFromCentre(.0f);
        else finalPlot.setKmFromCentre(Float.parseFloat(kmFromEdField.getText()));

        new JDBC().editInBase(URLKey, finalPlot);
        System.out.println(finalPlot.getURL() + " " + finalPlot.getCity() + " " + finalPlot.getKmFromCentre());

        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
