import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Task2 {
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
        connection.close();
    }
}

