import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SimpleHoneypot {
    private static final int HONEYPOT_PORT = 8080;

    public static void main(String[] args) {
        System.out.println("[+] KFSensor Simulator: Initializing Honeypot...");
        System.out.println("[+] Active Monitoring started on Port: " + HONEYPOT_PORT);
        System.out.println("[+] Waiting for unauthorized traffic/DoS attacks...\n");

        try (ServerSocket serverSocket = new ServerSocket(HONEYPOT_PORT)) {
            int alertCount = 0;

            while (true) {
                Socket clientSocket = serverSocket.accept();
                alertCount++;

                String attackerIP = clientSocket.getInetAddress().getHostAddress();
                int attackerPort = clientSocket.getPort();

                System.out.println("====== ALERT: HONEYPOT TRIGGERED ======");
                System.out.println("Timestamp   : " + new Date());
                System.out.println("Alert ID    : KF-ALERT-00" + alertCount);
                System.out.println("Attacker IP : " + attackerIP);
                System.out.println("Source Port : " + attackerPort);
                System.out.println("Target Port : " + HONEYPOT_PORT);
                System.out.println("Severity    : HIGH (Potential Port Scan/DoS Attempt)");
                System.out.println("=======================================\n");

                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("[-] Error running the Honeypot server: " + e.getMessage());
        }
    }
}