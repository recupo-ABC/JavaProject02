package maingame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDAO {
  // データベース接続に使用する情報
  static String name = TitleView.name.getText();
  static String result = ClientMain.frame.getText();
  public static void Insert() {
	        String url = "jdbc:mysql://172.16.0.212/ikiridb";
	        String user = "JavaC";
	        String password = "JavaC";

	        try (Connection con = DriverManager.getConnection(url, user, password);
	                PreparedStatement preStatement = con.prepareStatement("insert into playlog (name,result) values(?,?);")) {
	           
	            preStatement.setString(1, name);
	            preStatement.setString(2,result );
	        }catch (SQLException e) {
	            e.printStackTrace();
	        }finally {

	        }
	    }
	}



