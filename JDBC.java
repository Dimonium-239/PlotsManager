package Code;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class JDBC {

    public static Connection conn;

    public static Statement stmt;

    public JDBC() throws SQLException {
        connect();
    }

    public void connect() throws SQLException {
        conn = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/PlotsBase",
                "root", "2000");
        stmt = conn.createStatement();

        // Connection is ready to use
        DatabaseMetaData meta = conn.getMetaData();
        /*System.out.println("Server name: "
                + meta.getDatabaseProductName());
        System.out.println("Server version: "
                + meta.getDatabaseProductVersion());

         */

    }

    public String[] getStrElement(String field) throws SQLException {
        String elementsArr[] = new String[count()];
        connect();
        String strSelect = "select " + field +" from new_schema.Plots";
        ResultSet rset = stmt.executeQuery(strSelect);
        int rowCounter = 0;
        while (rset.next()) {
            String elements = rset.getString(field);
            elementsArr[rowCounter] = elements;
            rowCounter++;
        }
        return elementsArr;
    }

    public int[] getIntElement(String field) throws SQLException {
        int elementsArr[] = new int[count()];
        connect();
        String strSelect = "select " + field +" from new_schema.Plots";
        System.out.println(strSelect);
        ResultSet rset = stmt.executeQuery(strSelect);
        int rowCounter = 0;
        while (rset.next()) {
            Integer elements = rset.getInt(field);
            elementsArr[rowCounter] = elements;
            rowCounter++;
        }
        return elementsArr;
    }

    public void addToBase(Plot plot) throws SQLException {

       String priceStr, areaStr, kmStr;

       String url = plot.getURL();
       String city = plot.getCity();
       String district = plot.getDistrict();
       String street = plot.getStreet();
       priceStr = String.valueOf(plot.getPrice());
       areaStr = String.valueOf(plot.getArea());
       kmStr = String.valueOf(plot.getKmFromCentre());

        String VALUES = "VALUES ( '" + url + "', '" + city + "', '" + district + "', '" + street + "', " + priceStr + ", " + areaStr + ", " + kmStr + " )";

        stmt.executeUpdate("INSERT INTO new_schema.Plots (Link, City, District, Street, Price, Area, KmFrom) " + VALUES);
    }

    public void deleteFromBase(String url) throws SQLException{
        connect();
        Statement st = conn.createStatement();
        st.executeUpdate("DELETE FROM new_schema.Plots WHERE Link= '"+ url +"';");
        conn.close();
    }

    public void editInBase(String URLKey, Plot newPlot) throws SQLException{

        String UPDATE = "UPDATE new_schema.Plots ";
        String SET = "SET " +
                "Link='" + newPlot.getURL() +
                "', City='" + newPlot.getCity() +
                "', District='" + newPlot.getDistrict() +
                "', Street='" + newPlot.getStreet() +
                "', Price=" + newPlot.getPrice() +
                ", Area=" + newPlot.getArea() +
                ", KmFrom=" + newPlot.getKmFromCentre() + " ";
        String WHERE = "WHERE Link = '" + URLKey + "';";
        System.out.println(UPDATE + SET + WHERE);
        stmt.executeUpdate(UPDATE + SET + WHERE);
        conn.close();


    }

    public int count() throws SQLException {
        connect();
        int numberOfRows = 0;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM new_schema.Plots;");
        while(rs.next())
            numberOfRows = rs.getInt("COUNT(*)");
        conn.close();
        return numberOfRows;
    }

    public int max_price() throws SQLException {
        connect();
        int max = 0;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT MAX(Price) FROM new_schema.Plots;");
        while(rs.next()) {
            max = rs.getInt("MAX(Price)");
        }
        return max;
    }

    public int max_area() throws SQLException {
        connect();
        int max = 0;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT MAX(Area) FROM new_schema.Plots;");
        while(rs.next()) {
            max = rs.getInt("MAX(Area)");
        }
        return max;
    }

    public ObservableList<Plot> getFromBase() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (Exception ex) {}

        List<Plot> tempPlots = new ArrayList();

        try {
            connect();
            String strSelect = "select Link, Price, City, District, Street, Area, KmFrom from new_schema.Plots";
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                String URL = rset.getString("Link");
                String city = rset.getString("City");
                String district = rset.getString("District");
                String street = rset.getString("Street");
                Integer price = rset.getInt("Price");
                Integer area = rset.getInt("Area");
                Float kmFrom = rset.getFloat("KmFrom");
                tempPlots.add(new Plot(URL, city, district, street, price, area, kmFrom));
            }



            conn.close();
            if (conn.isClosed())
                System.out.println("Connection closed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(tempPlots);
    }
}



