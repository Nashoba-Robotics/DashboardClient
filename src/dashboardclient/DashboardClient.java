package dashboardclient;

import org.apache.commons.net.telnet.TelnetClient;
import java.lang.Thread;
import java.net.Socket;

/**
 *
 * @author alexbrinister
 */
public class DashboardClient {
    TelnetClient client = new TelnetClient();
    Socket sock = new Socket();
    Thread execThread = new Thread();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
