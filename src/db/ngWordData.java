package db;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteException;

public class ngWordData {
	private static Connection con = null;

	public static void startDbCon() throws SQLException, ClassNotFoundException{
		Statement stmt = null;
		int i = 0;
	     try {
	       // sqlite JDBCドライバーの読み込み
	       Class.forName("org.sqlite.JDBC");
	     } catch (ClassNotFoundException ex) {
	       System.out.println("JDBCドライバーが見つかりません?。\n" + ex);
	       throw ex;
	     }
	      try {
	    	  //データベース接続
	    	  con = DriverManager.getConnection("jdbc:sqlite:production.DB");
	    	// ステートメントの生成
	          stmt = con.createStatement();
	          // テーブル作成
	          try {
		          stmt.executeUpdate("create table ngWordTbl(id integer primary key, word text, level text)");
		          try {
			    	 File f = new File("inputData/ngWord.csv");
			    	 BufferedReader br = new BufferedReader(new FileReader(f));

			    	 String line;
			    	 while ((line = br.readLine()) != null) {
			    		 String[] data = line.split(",", 0);
			    		 i = i + 1;
			    		 try {
			    			 stmt.executeUpdate("insert into ngWordTbl (id, word, level) " + "values (" + "'" + i + "'"  + "," + "'"  + data[0] + "'" + ","  + "'" + data[1] + "'" + ");");
			    		 }catch(SQLException ex) {
			    			 System.out.println(ex);
			    		 }
			    	    }
			    	 br.close();
				     }catch (IOException e) {
				         System.out.println(e);
				     }
	          }catch ( SQLiteException e ) {
	        	  System.out.println(e);
	          }
	      }catch (SQLException ex) {
             System.out.println("DB接続に失敗しました！。\n" + ex);
             throw ex;
	      }
	}
}
