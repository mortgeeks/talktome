package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {
    @FXML
    private Button exit;
    @FXML
    private Button login;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private void exit(ActionEvent event){
        System.exit(0);
    }
    @FXML
    private Boolean login(ActionEvent event) throws IOException {
        boolean stat = false;
        if (checkUP(userName.getText(),password.getText())==true){
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Stage DashBStage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            DashBStage.setTitle("DashBoard : "+userName.getText());
            DashBStage.setScene(scene);
            Stage stage = (Stage) login.getScene().getWindow();
            stage.close();
            DashBStage.show();
            stat=true;
        }
        return stat;
    }
    public boolean checkUP(String user,String password){
        if (user.equals("sam") && password.equals("rano")){
            return true;
        }else {
            return false;
        }
    }

}
