import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter password default (empty):");
        String password = sc.nextLine().trim();
        System.out.println();

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        String countryName = sc.nextLine();

        PreparedStatement updateTownNames = connection.prepareStatement(
                "UPDATE `towns` SET `name` = UPPER(`name`) WHERE `country` = ?;"
        );
        updateTownNames.setString(1, countryName);

        int townCount = updateTownNames.executeUpdate();

        if (townCount == 0) {
            System.out.println("No town names were affected.");
            return;
        }

        System.out.println(townCount + " town names were affected.");

        PreparedStatement selectAllTowns = connection.prepareStatement(
                "SELECT `name` FROM `towns` WHERE `country` = ?"
        );
        selectAllTowns.setString(1, countryName);
        ResultSet tSet = selectAllTowns.executeQuery();

        List<String> towns = new ArrayList<>();

        while (tSet.next()) {
            String townName = tSet.getString("name");
            towns.add(townName);
        }

        System.out.println(towns);

        connection.close();
    }
}
