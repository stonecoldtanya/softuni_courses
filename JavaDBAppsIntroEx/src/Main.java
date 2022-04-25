import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {
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

        // Task 1
        /**
         PreparedStatement statement = connection.prepareStatement("SELECT `name`, COUNT(DISTINCT mv.`minion_id`) as `minion_count` FROM `villains` as v " +
                " JOIN `minions_villains` as mv " +
                " on mv.`villain_id` = v.`id`" +
                " GROUP BY v.`name`" +
                " HAVING `minion_count` > 15" +
                " ORDER BY `minion_count` DESC;");

        ResultSet rs = statement.executeQuery();

        while(rs.next()){
            String villainName = rs.getString("name");
            int minion_count  = rs.getInt("minion_count");
            System.out.println(villainName + " " + minion_count);
        }

         */

        // Task 2
        /**
        int villainId = Integer.parseInt(sc.nextLine());

        PreparedStatement vStatement = connection.prepareStatement(
                "SELECT name FROM villains WHERE id = ?");
        vStatement.setInt(1, villainId);

        ResultSet vs = vStatement.executeQuery();

        if (!vs.next()) {
            System.out.printf("No villain with ID %d exists in the database.", villainId);
            return;
        }

        String villainName = vs.getString("name");
        System.out.println("Villain: " + villainName);

        PreparedStatement mStatement = connection.prepareStatement(
                "SELECT `name`, `age`" +
                        " FROM `minions` AS m" +
                        " JOIN `minions_villains` AS mv ON mv.`minion_id` = m.`id`" +
                        " WHERE mv.`villain_id` = ?;");
        mStatement.setInt(1, villainId);

        ResultSet ms = mStatement.executeQuery();

        int id = 1;
        while(ms.next()){
            String mName = ms.getString("name");
            int mAge  = ms.getInt("age");
            System.out.println(id + ". " + mName + " " + mAge);

            id++;
        }
        */

        // Task 3
        /**
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
    */
        // Task 4
        /**
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
    */

        //Task 5
        /**
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
        */
        //task 7
        /**
        String[] mIds = sc.nextLine().split(" ");

        for (String mId : mIds) {
            PreparedStatement updateMinions = connection.prepareStatement(
                        " UPDATE `minions`" +
                            " SET `name` = LOWER(`name`)," +
                            " `age` = `age` + 1" +
                            " WHERE `id` = ?;");
            updateMinions.setInt(1, Integer.parseInt(mId));
            updateMinions.executeUpdate();
        }

        PreparedStatement selectMinionsStatement = connection.prepareStatement(" SELECT `name`, `age` " +
                                                                                   " FROM `minions` ORDER BY `id`;");

        ResultSet mSet = selectMinionsStatement.executeQuery();

        while (mSet.next()) {
            String mName = mSet.getString("name");
            int mAge = mSet.getInt("age");
            System.out.printf("%s %s\n", mName, mAge);
        }

    */
        //Task 8
        /**
        int mId = Integer.parseInt(sc.nextLine());

        PreparedStatement createProcedureStatement = connection.prepareStatement(
                " CREATE PROCEDURE `usp_get_older`(`minionId` INT)" +
                        " BEGIN" +
                        " UPDATE `minions` SET `age` = `age` + 1 WHERE `id`=`minionId`;" +
                        " END");

        createProcedureStatement.execute();

        PreparedStatement selectMinionsStatement = connection.prepareStatement("SELECT `name`,`age` FROM `minions` WHERE `id`=?");
        selectMinionsStatement.setInt(1, mId);

        ResultSet rSet = selectMinionsStatement.executeQuery();

        while(rSet.next()){
            System.out.printf("%s %s%n", rSet.getString("name"), rSet.getString("age"));
        }

        PreparedStatement dropProcedureStatement = connection.prepareStatement("DROP PROCEDURE `usp_get_older` ");
        dropProcedureStatement.execute();
    */
    connection.close();
    }
}