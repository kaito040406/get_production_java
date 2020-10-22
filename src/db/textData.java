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

public class textData {
	private static Connection con = null;

	public static void startDbCon() throws SQLException, ClassNotFoundException{
		Statement stmt = null;
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
		          stmt.executeUpdate("create table textTbl(text1 String, text2 String, text3 String)");
		          try {
			    	 File f = new File("inputData/text.csv");
			    	 BufferedReader br = new BufferedReader(new FileReader(f));

			    	 String line;
			    	 while ((line = br.readLine()) != null) {
			    		 String[] data = line.split(",", 0);
			    		 try {
			    			 stmt.executeUpdate("insert into textTbl (text1, text2, text3) " + "values (" + "'"  + data[0] + "'" + ","  + "'" + data[1] + "'" + ","  + "'" + data[2] + "'" + ");");
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
