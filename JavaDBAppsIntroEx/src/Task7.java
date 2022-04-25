import java.sql.*;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Task7 {
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


        connection.close();
    }
}
