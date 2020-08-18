package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PriceController {
	@FXML Button load;
    @FXML private TextField price1000;

    @FXML
    public void onLoadP(ActionEvent e) {
    	price1000.setText("1.5");
    	System.out.println("aaa")
;    }

}
