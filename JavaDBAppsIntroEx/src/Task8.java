import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Task8 {
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

        connection.close();
    }
}
