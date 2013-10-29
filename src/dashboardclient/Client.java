package dashboardclient;

import org.apache.commons.net.telnet.TelnetClient;
import java.lang.Thread;
import java.net.Socket;

public class Client {
    Thread clientThread = new Thread();
    Socket sock = new Socket();
    TelnetClient client = new TelnetClient();
    
    Client() {
    }
}
