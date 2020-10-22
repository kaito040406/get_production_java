package application;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import csvOutput.csvOutput;
import db.SqliteDB;
import dbj.SqliteDBJ;
import fileDelete.imageDelete;
import getPage.Detail;
import getPage.Scra;
import getPage.TableData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.productionModel;

public class SampleController {
	@FXML private TextField urlData;

	@FXML private TextField page;

	@FXML private TextField number;

	@FXML Button button;

    @FXML
    private TableView<TableData> productiontable;
//ASINが不要になったので消去
//    @FXML
//    private TableColumn asin;
    @FXML
    private TableColumn title;
    @FXML
    private TableColumn text;
    @FXML
    private TableColumn price;
    @FXML
    private TableColumn accessurl;
    @FXML
    private ObservableList<TableData> data;

	@SuppressWarnings("unchecked")

	@FXML
	public void onClick(ActionEvent e) throws IOException, InterruptedException, ClassNotFoundException, SQLException {
		Logger logger = Logger.getLogger("GB");
		//前回分のデータを削除　開始
		try {
			logger.log(Level.INFO,"前回データ削除開始");
	        SqliteDBJ.dataDelete();
	        imageDelete fileDel = new imageDelete();
			fileDel.delete();
//			logger.log(Level.INFO,"前回データ削除終了");
	      } catch (SQLException | ClassNotFoundException ex) {
	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	      }
		//前回分のデータを削除　終了

		String url = urlData.getText();
		String regex_num = "^[0-9]+$";//数字の正規表現
		Pattern p1 = Pattern.compile(regex_num);//数字とのマッチ
		Matcher m1 = p1.matcher(page.getText());//ページ数が数字とマッチしている
		Matcher m2 = p1.matcher(number.getText());//在庫数が数字とマッチしている
		boolean result1 = m1.matches();//取得したページ数が数字である
		boolean result2 = m2.matches();//取得した在庫数が数字である
		if(url.contains("https://www.amazon.co.jp/")) {//amazonのURL以外を除去
			if(result1 && result2) {//ページ数と在庫数が数字以外の時に除去
				//正しい値が入力された場合の処理
//				logger.log(Level.INFO,"情報取得を開始します。");
				int count = Integer.parseInt(page.getText());//取得したページを数字型に変更
				int getNumber = Integer.parseInt(number.getText());//取得した在庫数を数字型に変更
				//実行前に前回のデータを削除　開始
				logger.log(Level.INFO,"商品データをリセットします");
				SqliteDB.dbUpdate("delete from productionTbl;");
				logger.log(Level.INFO,"商品データをリセット完了");
				//実行前に前回のデータを削除　終了

				//ページのURLを取得　開始
				logger.log(Level.INFO,"URL情報を取得いたします");
				Scra pageUrls = new Scra(url, count, getNumber);
				logger.log(Level.INFO,"URL情報の取得が完了しました");
				//ページのURLを取得　終了

				//詳細ページURLを取得　開始
				logger.log(Level.INFO,"詳細情報の取得を開始します");
				Detail purodDetail = new Detail(pageUrls.accessUrl2);
				logger.log(Level.INFO,"詳細情報の取得が完了しました");
				//詳細ページURLを取得　終了

				data = FXCollections.observableArrayList();
				productiontable.itemsProperty().setValue(data);
				productiontable.setItems(data);

				//表作成　開始
//ASIN不要のため削除
//				asin.setCellValueFactory(new PropertyValueFactory<TableData, String>("asin"));
				title.setCellValueFactory(new PropertyValueFactory<TableData, String>("name"));
				text.setCellValueFactory(new PropertyValueFactory<TableData, String>("text"));
				price.setCellValueFactory(new PropertyValueFactory<TableData, String>("price"));
				accessurl.setCellValueFactory(new PropertyValueFactory<TableData, String>("url"));
				//表作成　終了

				//データベースから詳細データの取得　開始
				ObservableList<productionModel> searchDatas = SqliteDBJ.searchAllData();
				for(productionModel searchData :searchDatas) {
					data.addAll(new TableData(searchData.getAsin(),searchData.getName(),searchData.getMemo(),searchData.getPrice(),searchData.getUrl()));
				}
//				logger.log(Level.INFO,"データの取得が完了しました");
				Parent parent = FXMLLoader.load(getClass().getResource("Finish.fxml"));//終了画面の表示
				Scene scene = new Scene(parent,300,100);//画面サイズ
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.show();
			}else {
				//数字が不正の場合
//				logger.log(Level.INFO,"入力が不正です。");
				Parent parent = FXMLLoader.load(getClass().getResource("CountError.fxml"));
				Scene scene = new Scene(parent,300,100);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.show();
			}
		}else {
			//正しいURLが入力されていない場合
//			logger.log(Level.INFO,"無効なURLです。");
			Parent parent = FXMLLoader.load(getClass().getResource("Error.fxml"));
			Scene scene = new Scene(parent,300,100);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		}
	}

//設定画面の表示
	@FXML
	private void nextPage(ActionEvent event) {
		try {
			settingWindow();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void settingWindow() throws IOException, ClassNotFoundException, SQLException{
		Parent parent = FXMLLoader.load(getClass().getResource("Setting.fxml"));
		Scene scene = new Scene(parent,370,650);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();

	}

//出力完了画面の表示
	@FXML
	private void exportData(ActionEvent event) throws IOException, ClassNotFoundException, SQLException{
		csvOutput.output();
		System.out.println("出力完了");
	}

}
