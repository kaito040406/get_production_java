package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class SqliteDB {
	private static Connection con = null;

	public static void dbCon() throws SQLException, ClassNotFoundException{
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
	          stmt.executeUpdate("create table if not exists productionTbl(id integer primary key,asin text,name text,url text,memo text,price text,category text,maker text,bland text,date timestamp NOT NULL default (datetime(CURRENT_TIMESTAMP,'localtime')), categoryId, yCategory)");

	      }catch (SQLException ex) {
              System.out.println("DB接続に失敗しました！。\n" + ex);
              throw ex;
	      }
	}

	//検索
	public static ResultSet dataQuery(String queryStmt) throws SQLException, ClassNotFoundException {
	     // CachedRowSetはDBから取得したデータを、メモリ上に格納した状態でもデータの処理が可能。
	     RowSetFactory rowSetFactory = RowSetProvider.newFactory();
	     CachedRowSet rowSet = rowSetFactory.createCachedRowSet();

	     Statement stmt = null;
	     ResultSet resultSet = null;
	     try {
	       dbCon();
	       stmt = con.createStatement();
	       resultSet = stmt.executeQuery(queryStmt);
	       // ResultSetオブジェクトのデータをpopulateメソットを使用して読み込みます。
	       System.out.println(resultSet);
	       rowSet.populate(resultSet);
	     } catch (SQLException ex) {
	         System.out.println("検索に失敗しました！。\n" + ex);
	         throw ex;
	     } finally {
	         if (resultSet != null) {
	           resultSet.close();
	         }
	         if (stmt != null) {
	           stmt.close();
	         }
	         if (con != null) {
	           con.close();
	         }
	     }
	     return rowSet;
	   }
	public static void dbUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
	      Statement stmt = null;
	      try {
	         dbCon();
	         stmt = con.createStatement();
	         stmt.executeUpdate(sqlStmt);
	      } catch (SQLException ex) {
	            System.out.println("データの処理に失敗しました！。\n" + ex);
	      } finally {
	           if (stmt != null) {
	              stmt.close();
	           }
	           if (con != null) {
	              con.close();
	           }
	      }
	  }
}
