package dbj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import db.SqliteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ngwordModel;
import model.priceModel;
import model.productionModel;
import model.searchIdModel;
import model.textModel;

public class SqliteDBJ {
	// 登録
	  public static void insertData (String asin, String name, String url, String memo, String price, String category, String maker, String bland ,String date, String categoryId, String yCategory, String imageName) throws SQLException, ClassNotFoundException {
		Logger logger = Logger.getLogger("GB");
	    String insertStmt;
	    // SQL文の作成
	    logger.log(Level.INFO,"insert開始");
	    insertStmt = "INSERT INTO productionTbl" +
	                  "(asin, name, url, memo, price, category, maker ,bland, date, categoryId, yCategory, image)" +
	                  "VALUES('" + asin + "','" + name + "','" + url + "','" + memo + "','" + price + "','" + category + "','" + maker + "','" + bland + "','" +   date + "','" +  categoryId + "','" + yCategory +"','" + imageName +"')";
	    logger.log(Level.INFO,"insert完了");
	    try {
	      // SqliteDBクラスへSQLを発行
	      SqliteDB.dbUpdate(insertStmt);
	    } catch (SQLException ex) {
	        System.out.println("登録処理に失敗しました!。\n" + ex );
	        throw ex;
	    }
	  }

	 //NGword登録
	  public static void insertDataNg(String word, String level) throws ClassNotFoundException, SQLException {
		  String insertStmt;
		  insertStmt = "INSERT INTO ngWordTbl" +
				  		"(word, level)" +
				  		"VALUES('" + word + "','" + level +"')";
	    try {
		      // SqliteDBクラスへSQLを発行
		      SqliteDB.dbUpdate(insertStmt);
		    } catch (SQLException ex) {
		        System.out.println("登録処理に失敗しました!。\n" + ex );
		        throw ex;
		    }
	  }

	//NGword削除
	  public static void deleteDataNg(Integer id) throws ClassNotFoundException, SQLException {
		  String insertStmt;
		  insertStmt = "DELETE FROM ngWordTbl WHERE id="+ id +";";
	    try {
		      // SqliteDBクラスへSQLを発行
		      SqliteDB.dbUpdate(insertStmt);
		    } catch (SQLException ex) {
		        System.out.println("削除処理に失敗しました!。\n" + ex );
		        throw ex;
		    }
	  }

	  public static void updataPrice(Map<Integer, String> priceLists) throws ClassNotFoundException, SQLException {
		  String insertStmtDel;
		  String insertStmtUp;
		  insertStmtDel = "DELETE FROM priceTbl;";
		  try {
		      // SqliteDBクラスへSQLを発行
		      SqliteDB.dbUpdate(insertStmtDel);
		  	} catch (SQLException ex) {
		        System.out.println("削除処理に失敗しました!。\n" + ex );
		        throw ex;
		  	}
		  for(Map.Entry<Integer, String> inputList : priceLists.entrySet()) {
			 insertStmtUp = "INSERT INTO priceTbl(price, callPrice) values('" + inputList.getKey() + "','" + Float.parseFloat(inputList.getValue()) + "');";
			 try {
			      // SqliteDBクラスへSQLを発行
			      SqliteDB.dbUpdate(insertStmtUp);
			 } catch (SQLException ex) {
			        System.out.println("削除処理に失敗しました!。\n" + ex );
			        throw ex;
			   }
		  }

	  }

	  private static ObservableList<productionModel> searchAllDataList(ResultSet rs) throws SQLException, ClassNotFoundException {
		    ObservableList<productionModel> data = FXCollections.observableArrayList();
		    while (rs.next()){
		      data.add(new productionModel(
		      rs.getInt("id"),
		      rs.getString("asin"),
		      rs.getString("name"),
		      rs.getString("memo"),
		      rs.getString("url"),
		      rs.getString("price"),
		      rs.getString("category"),
		      rs.getString("maker"),
		      rs.getString("bland"),
		      rs.getString("date"),
		      rs.getString("categoryId"),
		      rs.getString("yCategory"),
		      rs.getString("image")));
		    }
		      return data;
		  }

	//NGワードの全検索
	  private static ObservableList<ngwordModel> searchAllNgwordList(ResultSet rs) throws SQLException, ClassNotFoundException {
	    ObservableList<ngwordModel> data = FXCollections.observableArrayList();
	    while (rs.next()){
	      data.add(new ngwordModel(
	      rs.getInt("id"),
	      rs.getString("word"),
	      rs.getString("level")));
	    }
	      return data;
	  }
	//サーチリンクの全検索
	  private static ObservableList<searchIdModel> searchAllLinkList(ResultSet rs) throws SQLException, ClassNotFoundException {
		  ObservableList<searchIdModel> data = FXCollections.observableArrayList();
		    while (rs.next()){
		      data.add(new searchIdModel(
		      rs.getString("amazonCategory"),
		      rs.getString("yahooCategory"),
		      rs.getString("categoryId")));
		    }
		      return data;
	  }
	//price設定情報の前検索
	  private static ObservableList<priceModel> searchAllPriceList(ResultSet rs) throws SQLException, ClassNotFoundException {
		  ObservableList<priceModel> data = FXCollections.observableArrayList();
		    while (rs.next()){
		      data.add(new priceModel(
		      rs.getInt("price"),
		      rs.getFloat("callPrice")));
		    }
		      return data;
	  }

	  public static boolean dataDelete() throws SQLException, ClassNotFoundException{
		  String delSql;
		  delSql = "DELETE FROM productionTbl";
		  try {
			  SqliteDB.dbUpdate(delSql);
		  }catch(SQLException ex) {
			  throw ex;
		  }
		  return false;
	  }

	  public static ObservableList<productionModel> searchAllData() throws SQLException, ClassNotFoundException {
		    String sql = "SELECT * FROM productionTbl";
		    try {
		      ResultSet rs = SqliteDB.dataQuery(sql);
		      ObservableList<productionModel> data = searchAllDataList(rs);
		      return data;
		    } catch (SQLException ex) {
		        System.out.println("全件の検索に失敗しました!。\n" + ex);
		        throw ex;
		    }
		  }

	  //NGワードの全検索
	  public static ObservableList<ngwordModel> searchAllDataNg() throws SQLException, ClassNotFoundException {
		    String sql = "SELECT * FROM ngWordTbl";
		    try {
		      ResultSet rs = SqliteDB.dataQuery(sql);
		      ObservableList<ngwordModel> data = searchAllNgwordList(rs);
		      return data;
		    } catch (SQLException ex) {
		        System.out.println("全件の検索に失敗しました!。\n" + ex);
		        throw ex;
		    }
		  }
	  //サーチリンクの全検索
	  public static ObservableList<searchIdModel> searchAllDataLink() throws SQLException, ClassNotFoundException {
		  String sql = "SELECT * FROM searchLinkTbl";
		  try {
			  ResultSet rs = SqliteDB.dataQuery(sql);
		      ObservableList<searchIdModel> data = searchAllLinkList(rs);
		      return data;
		  } catch (SQLException ex) {
		        System.out.println("全件の検索に失敗しました!。\n" + ex);
		        throw ex;
		    }
	  }
	  //商品の前検索
	  public static ObservableList<priceModel> searchAllDataPrice() throws SQLException, ClassNotFoundException {
		  String sql = "SELECT * FROM priceTbl order by price asc";
		  try {
			  ResultSet rs = SqliteDB.dataQuery(sql);
		      ObservableList<priceModel> data = searchAllPriceList(rs);
		      return data;
		  } catch (SQLException ex) {
		        System.out.println("全件の検索に失敗しました!。\n" + ex);
		        throw ex;
		    }
	  }
	  //テキストの前検索
	  public static ObservableList<textModel> searchAllDataText() throws SQLException, ClassNotFoundException {
		  String sql = "SELECT * FROM textTbl";
		  try {
			  ResultSet rs = SqliteDB.dataQuery(sql);
		      ObservableList<textModel> data = searchAllTextList(rs);
		      return data;
		  } catch (SQLException ex) {
		        System.out.println("全件の検索に失敗しました!。\n" + ex);
		        throw ex;
		    }
	  }
	  private static ObservableList<textModel> searchAllTextList(ResultSet rs) throws SQLException, ClassNotFoundException {
		  ObservableList<textModel> data = FXCollections.observableArrayList();
		    while (rs.next()){
		      data.add(new textModel(
		      rs.getString("text1"),
		      rs.getString("text2"),
		      rs.getString("text3")));
		    }
		      return data;
	  }
}
