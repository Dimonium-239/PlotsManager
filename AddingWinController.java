package Code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;


public class AddingWinController {

    @FXML
    TextField Link;

    @FXML
    TextField City;

    @FXML
    TextField District;

    @FXML
    TextField Street;

    @FXML
    TextField Price;

    @FXML
    TextField Area;

    @FXML
    TextField KmFrom;

    public void accept(ActionEvent actionEvent) throws SQLException {

        if(Link.getText() == null || Link.getText().trim().isEmpty() ||
                City.getText() == null || City.getText().trim().isEmpty() ||
                District.getText() == null || District.getText().trim().isEmpty() ||
                Street.getText() == null || Street.getText().trim().isEmpty() ||
                Price.getText() == null || Price.getText().trim().isEmpty() ||
                Area.getText() == null || Area.getText().trim().isEmpty()
        ){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Fill all fields!!!");
            alert.setContentText("Maybe some of fields is empty");

            alert.showAndWait();
        }
        Plot tempPlot = new Plot(Link.getText(), City.getText(), District.getText(), Street.getText(), Integer.valueOf(Price.getText()), Integer.valueOf(Area.getText()));
        if(KmFrom.getText() == null || KmFrom.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("You living empty field 'Km to centre'\n Value of this field will be '0.0'");
            alert.setContentText("In this version of program\n You cant change anything!");

            alert.showAndWait();
            tempPlot.setKmFromCentre(.0f);
        }
        else
            tempPlot.setKmFromCentre(Float.valueOf(KmFrom.getText()));

        System.out.println(tempPlot.getKmFromCentre());
        new JDBC().addToBase(tempPlot);
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void discard(ActionEvent actionEvent) {
        Link.setText("");
        City.setText("");
        District.setText("");
        Street.setText("");
        Price.setText("");
        Area.setText("");
        KmFrom.setText("");

        if(MessageWindows.alertWindow(actionEvent,"Message", "Text fields are clear.\nDo you want filling them again or close window?",
                "Choose your option.", "Continue", "Close"))
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
