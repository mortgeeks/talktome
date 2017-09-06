package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class Dashboard extends Service<String> implements Initializable {


    @FXML
    private Button send;
    @FXML
    private Button search;
    @FXML
    private ListView<String> ContactlistView;
    @FXML
    private ListView<String> MessageslistView;
    @FXML
    private TextField searchText;
    @FXML
    private TextField message;
    String mes;
    NetworkManupulation nm;
    scanList scanList;
    public ObservableList<String> iplist;
    @FXML
    private void exitM(ActionEvent event){
        System.exit(0);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SqlLiteManager sql = new SqlLiteManager();
        sql.createNewDatabase("database.sqlite");
        sql.connect();
        //sql.createNewTable();

        try {
            Server server = new Server();
        } catch (UnknownHostException e) {

        } catch (SocketException e) {

        }
        ContactlistView.itemsProperty().addListener(new ChangeListener<ObservableList<String>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<String>> observableValue, ObservableList<String> strings, ObservableList<String> t1) {

            }
        });

    }
    @FXML
    private void help(ActionEvent event){

    }

    public void scanInterface(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scanInterface.fxml"));
        Stage scanUI = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scanUI.setTitle(" Scan ");
        scanUI.setScene(scene);
        scanUI.show();

    }

    public ObservableList getIPLIST() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scanInterface.fxml"));
        loader.load();
        scanUI tmp = loader.getController();
        ContactlistView.setItems(tmp.iplist);
        return tmp.getiplist();
    }

    @Override
    protected Task<String> createTask() {


        return new Task<String>() {
            @Override
            protected String call() throws Exception {

                return null;
            }
        };
    }
}