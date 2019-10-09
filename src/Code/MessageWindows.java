package Code;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MessageWindows {

    public static boolean alertWindow(ActionEvent actionEvent, String title, String headerText, String contentText, String button1, String button2){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        ButtonType buttonTypeOne = new ButtonType(button1);
        ButtonType buttonTypeTwo = new ButtonType(button2);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            alert.close();
            return false;
        } else if (result.get() == buttonTypeTwo) {
            alert.close();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            return true;
        }
        return false;
    }
}


