package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.*;
import java.util.*;

import static java.lang.System.out;

public class NetworkManupulation{
    private boolean ScanStat=false;
    public NetworkManupulation() throws SocketException {

    }
    public String getSubNet(int index) throws SocketException {
        String subnet = getMachineIP(index);
        int points = 0;
        char[] sub = new char[15];
        for (int i=0;i<subnet.length();i++){
            if (subnet.charAt(i)=='.'){
                points++;
                if (points==3){
                    subnet = subnet.substring(0,i+1);
                    break;
                }

            }
        }
        return subnet;
    }
    public String getMachineIP(int index) throws SocketException {
        NetworkInterface networkInterface = NetworkInterface.getByIndex(index);
        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            if(inetAddress instanceof Inet4Address)
                return String.valueOf(inetAddress).substring(1);

    }
    return "";
    }
    public Map<Integer, String> getIndex() throws SocketException {
        Map<Integer,String> map = new HashMap<Integer,String>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface networkInterface : Collections.list(interfaces)){
            if (networkInterface.isUp()){
                System.out.println(networkInterface.getIndex()+" "+networkInterface.getName());
                map.put(networkInterface.getIndex(),networkInterface.getName());
            }
        }
        return map;
    }

    public ObservableList<String> Scan(String subnet){
        ScanStat=true;
        ArrayList<String> list = new ArrayList<>();
        ObservableList<String> iplist = FXCollections.observableArrayList();
        for (int i = 1; i <= 255; i++) {
            if (ScanStat==true){
                String host = subnet + i;

                try {
                    InetAddress inetAddress = InetAddress.getByName(host);
                    if (inetAddress.isReachable(10)){
                        //list.add(inetAddress.toString().substring(1));
                        iplist.add(inetAddress.toString().substring(1));
                    }

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }


        }
        return iplist;
    }
    public void ScanStop(){
        if (ScanStat==true){
            ScanStat=false;
        }
    }
}