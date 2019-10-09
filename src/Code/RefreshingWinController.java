package Code;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Code.JDBC.stmt;

//TODO: Refresh database in new window with progress bar and check is link actual, or add new links from www.otodom.com

public class RefreshingWinController {
    @FXML
    private ProgressBar statusBar;

    public boolean isActual(String url) throws IOException {
        try {
            Connection.Response response = Jsoup.connect(url).followRedirects(true).execute();
            if (response.url().toString().endsWith("#from404"))
                return false;
            return true;
        } catch (org.jsoup.HttpStatusException e) {
            return false;
        }
    }

    public void goButton() throws SQLException, IOException {
        JDBC jdbc = new JDBC();
        int max = jdbc.count();
        float progress = 0.0f;
        String link = null;
        try {
            jdbc.connect();
            String strSelect = "select Link from new_schema.Plots";
            ResultSet rset = stmt.executeQuery(strSelect);
            float rowCount = 0;
            while (rset.next()) {

                  String URL = rset.getString("Link");
//                String City = rset.getString("City");
//                if(City.contains(","))
//                    City = City.substring(0, City.indexOf(','));
//
//                String UPDATE = "UPDATE new_schema.Plots ";
//                String SET = "SET " +
//                        "City='" + City + "' ";
//                String WHERE = "WHERE Link = '" + URL + "';";
//                stmt.executeUpdate(UPDATE + SET + WHERE);
//                link = URL;
                if (!isActual(URL)) {
                    jdbc.deleteFromBase(URL);
                    System.out.println("[deleted] " + URL);
                } else
                    System.out.println("[is actual] " + URL);

                progress = rowCount/max;
                System.out.println(progress);
                rowCount++;
            }
        }catch (java.lang.IllegalArgumentException e){
            jdbc.deleteFromBase(link);
        }
    }
}
