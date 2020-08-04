package application;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import dbj.SqliteDBJ;
import getPage.Scra;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SampleController {
	@FXML Button button;

	@FXML
	public void onClick(ActionEvent e) throws IOException, InterruptedException {
		try {
	        SqliteDBJ.dataDelete();
	      } catch (SQLException | ClassNotFoundException ex) {
	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	      }
		String url = "https://www.amazon.co.jp/s?bbn=2111178051&rh=n%3A2017304051%2Cn%3A%212017305051%2Cn%3A2111178051%2Cp_76%3A2227292051&dc&fst=as%3Aoff&qid=1595300246&rnid=2227291051&ref=lp_2111178051_nr_p_76_0";
		
		int count = 2;
		Scra pageUrls = new Scra(url, count);
		System.out.println(pageUrls.accessUrl);

	}

}
