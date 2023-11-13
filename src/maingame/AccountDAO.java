package maingame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AccountDAO {
  // データベース接続に使用する情報
  static String name = TitleView.name.getText();
  static Timestamp time = new Timestamp(System.currentTimeMillis());

  public static void Insert() {
	        String url = "jdbc:mysql://localhost:3306/ikiri";
	        String user = "muku";
	        String password = "muku";

	        try (Connection con = DriverManager.getConnection(url, user, password);
	                PreparedStatement preStatement = con.prepareStatement("insert into playdate values(?, ?);")) {
	           
	            preStatement.setString(1, name);
	            preStatement.setTimestamp(2, time);
	            int count = preStatement.executeUpdate();
	        }catch (SQLException e) {
	            e.printStackTrace();
	        }finally {
	            System.out.println("名前と時間を追加しました。");
	        }
	    }
	}



