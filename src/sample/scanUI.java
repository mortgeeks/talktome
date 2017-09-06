package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.net.SocketException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class scanUI implements Initializable{

    NetworkManupulation nm;
    String ip;
    ObservableList<String> iplist;
    @FXML
    private VBox interfacesbox;
    Map<Integer,String> map;
    ToggleGroup interfaceGroup;
    int selectedInterface;
    @FXML
    private ListView scanlist;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        interfaceGroup = new ToggleGroup();
        try {
            nm = new NetworkManupulation();
            map = nm.getIndex();
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }
        for (Map.Entry e : map.entrySet()){
            RadioButton radioButton= new RadioButton(e.getValue().toString());
            System.out.println(e.getValue().toString());
            radioButton.setUserData(e.getValue().toString());
            radioButton.setToggleGroup(interfaceGroup);
            interfacesbox.getChildren().add(radioButton);
        }

        interfaceGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if (interfaceGroup.getSelectedToggle() != null){
                    for (Map.Entry e : map.entrySet()){
                        if (interfaceGroup.getSelectedToggle().getUserData().toString() == e.getValue().toString()){
                            selectedInterface = Integer.parseInt(e.getKey().toString());
                        }
                    }
                }
            }
        });
        scanlist.itemsProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {

            }
        });

    }
    @FXML
    private void scan(ActionEvent e) throws SocketException {
        ip = nm.getSubNet(selectedInterface);
        iplist = nm.Scan(ip);
        scanlist.setItems(iplist);
    }
    public ObservableList<String> getiplist(){
        return this.iplist;
    }
    public void selectAll(ActionEvent e){

    }
    public void clear(ActionEvent e){
        iplist.removeAll();
        scanlist.setItems(iplist);
    }
    public void stopScan(ActionEvent event){
        nm.ScanStop();
    }

}