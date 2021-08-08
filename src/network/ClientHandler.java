package network;

import application.Main;
import application.Message;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler extends Listener {
    static Client client;
    static int udpPort = 54555, tcpPort = 54777;
    public static String ip = "127.0.0.1";
    private static DateTimeFormatter dft = DateTimeFormatter.ofPattern("HH:mm");

    public static void start(){
        try {
            client = new Client();
            Register.register(client.getKryo());
            client.start();
            client.connect(5000, ip, tcpPort,udpPort ); //5000 = 5 sekunden
            client.addListener(new ClientHandler());
            Main.g.taChat.append("["+ LocalTime.now().format(dft) + " Connected as Client]\n");
            Main.isConnected = true;
            Main.g.btnHost.setEnabled(false);
            Main.g.btnClient.setEnabled(false);
            Main.g.btnDisconnect.setEnabled(true);


        } catch (IOException e) {
            Main.g.taChat.append("["+ LocalTime.now().format(dft) + " No Host found]\n");
            Main.g.btnHost.setEnabled(true);
            Main.g.btnClient.setEnabled(true);
            Main.g.btnDisconnect.setEnabled(false);
            e.printStackTrace();

        }
    }

    public static void send(Object o){
        if(o instanceof Message){
            Message m = (Message) o;
            Main.g.taChat.append(m.getMessage());
            client.sendTCP(m); //UDP ist eher für Videoübertragung deshalb wegen geringe serverauslastung tcp
        }
    }
    public void received (Connection c, Object o){
        if(o instanceof Message){
            Message m = (Message) o;
            Main.g.taChat.append(m.getMessage());
        }
    }

    public static void close(){
        Main.g.taChat.append("[Disconnected]\n");
        client.close();
    }
}
