import java.sql.*;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Task5 {
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

        int vId = Integer.parseInt(sc.nextLine());

        PreparedStatement selectVillain = connection.prepareStatement(
                "SELECT `name` FROM `villains` WHERE `id` = ?");
        selectVillain.setInt(1, vId);
        ResultSet vSet = selectVillain.executeQuery();

        if (!vSet.next()) {
            System.out.println("No such villain was found");
            return;
        }

        String vName = vSet.getString("name");

        PreparedStatement selectAllVillainMinions = connection.prepareStatement(
                "SELECT COUNT(DISTINCT `minion_id`) as m_count" +
                        " FROM `minions_villains` WHERE `villain_id` = ?");
        selectAllVillainMinions.setInt(1, vId);
        ResultSet minionsCountSet = selectAllVillainMinions.executeQuery();
        minionsCountSet.next();

        int countFreedMs = minionsCountSet.getInt("m_count");

        connection.setAutoCommit(false);

        try {
            PreparedStatement deleteMinionsVillains = connection.prepareStatement(
                    "DELETE FROM `minions_villains` WHERE `villain_id` = ?");
            deleteMinionsVillains.setInt(1, vId);
            deleteMinionsVillains.executeUpdate();

            PreparedStatement deleteVillain = connection.prepareStatement(
                    "DELETE FROM `villains` WHERE `id` = ?");
            deleteVillain.setInt(1, vId);
            deleteVillain.executeUpdate();

            connection.commit();

            System.out.println(vName + " was deleted");
            System.out.println(countFreedMs + " minions released");

        } catch (SQLException e) {
            e.printStackTrace();

            connection.rollback();
        }
        connection.close();
    }
}
