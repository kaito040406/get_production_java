package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ErrorController {
	@FXML
	private Button close;
	@FXML
    public void onClick(ActionEvent e){
		close.getScene().getWindow().hide();
    }
}
