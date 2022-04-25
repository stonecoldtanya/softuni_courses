import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Task3 {
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

        String[] mInfo = sc.nextLine().split( " ");
        String mName = mInfo[1];
        int mAge = Integer.parseInt(mInfo[2]);
        String mTown = mInfo[3];

        String vName = sc.nextLine().split(" ")[1];

        //checking if the town exists and adding the new ones to the db
        PreparedStatement selectTown = connection.prepareStatement(
                "SELECT `id` FROM `towns` WHERE `name` = ?");
        selectTown.setString(1, mTown);

        ResultSet townSet = selectTown.executeQuery();

        int townId = 0;
        if (!townSet.next()) {
            PreparedStatement insertTown = connection.prepareStatement(
                    "INSERT INTO `towns`(`name`) VALUES (?);");
            insertTown.setString(1, mTown);
            insertTown.executeUpdate();

            ResultSet insertedTownSet = selectTown.executeQuery();
            insertedTownSet.next();
            townId = insertedTownSet.getInt("id");
            System.out.printf("Town %s was added to the database.%n", mTown);
        } else {
            townId = townSet.getInt("id");
        }

        //checking if the villain exists and adding the new ones to the db
        PreparedStatement selectVillain = connection.prepareStatement(
                "SELECT `id` FROM `villains` WHERE `name` = ?");
        selectVillain.setString(1, vName);

        ResultSet villainSet = selectVillain.executeQuery();

        int vId = 0;
        if (!villainSet.next()) {
            PreparedStatement insertVillain = connection.prepareStatement(
                    "INSERT INTO `villains`(`name`, `evilness_factor`) VALUES(?, ?)");
            insertVillain.setString(1, vName);
            insertVillain.setString(2, "evil");

            insertVillain.executeUpdate();

            ResultSet insertedVillainSet = selectVillain.executeQuery();
            insertedVillainSet.next();
            vId = insertedVillainSet.getInt("id");
            System.out.printf("Villain %s was added to the database.%n", vName);
        } else {
            vId = villainSet.getInt("id");
        }

        //executing the task aka adding the new minions
        PreparedStatement insertMinion = connection.prepareStatement(
                "INSERT INTO `minions`(`name`, `age`, `town_id`) VALUES(?, ?, ?)");
        insertMinion.setString(1, mName);
        insertMinion.setInt(2, mAge);
        insertMinion.setInt(3, townId);
        insertMinion.executeUpdate();

        PreparedStatement getLastMinion = connection.prepareStatement(
                "SELECT `id` FROM `minions` ORDER BY `id` DESC LIMIT 1");
        ResultSet lastMinionSet = getLastMinion.executeQuery();
        lastMinionSet.next();
        int lastMinionId = lastMinionSet.getInt("id");

        PreparedStatement insertMinionsVillains = connection.prepareStatement(
                "INSERT INTO `minions_villains` VALUES (?, ?)"
        );
        insertMinionsVillains.setInt(1, lastMinionId);
        insertMinionsVillains.setInt(2, vId);
        insertMinionsVillains.executeUpdate();

        System.out.printf("Successfully added %s to be minion of %s.%n",
                mName, vName);

        connection.close();
    }
}
