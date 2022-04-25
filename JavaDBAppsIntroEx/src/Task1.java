import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Task1 {
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

        connection.close();
    }
}
