import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Packet {
    String sourceIP;
    String destIP;
    String protocol;
    String payload;

    public Packet(String sourceIP, String destIP, String protocol, String payload) {
        this.sourceIP = sourceIP;
        this.destIP = destIP;
        this.protocol = protocol;
        this.payload = payload;
    }
}

public class SnortIDSSimulator {

    public static void runSnifferMode(List<Packet> packets) {
        System.out.println("\n=== [MODE 1] RUNNING SNORT IN SNIFFER MODE (snort -vd) ===");
        for (Packet p : packets) {
            System.out.println("--------------------------------------------------");
            System.out.println("Protocol: " + p.protocol);
            System.out.println("Source IP: " + p.sourceIP + " -> Dest IP: " + p.destIP);
            System.out.println("Payload (Application Data): " + p.payload);
        }
    }

    public static void runLoggerMode(List<Packet> packets) {
        System.out.println("\n=== [MODE 2] RUNNING SNORT IN PACKET LOGGER MODE ===");

        String dirPath = "./snort_logs";
        File directory = new File(dirPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) System.out.println("[INFO] Created log directory: " + dirPath);
        }

        String logFilePath = dirPath + "/packet_log.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath))) {
            for (int i = 0; i < packets.size(); i++) {
                Packet p = packets.get(i);
                writer.write("Packet #" + (i + 1) + " | " + p.protocol + " | "
                             + p.sourceIP + " -> " + p.destIP + " | Payload: " + p.payload + "\n");
            }
            System.out.println("[SUCCESS] Successfully logged packets to: " + logFilePath);
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to write logs: " + e.getMessage());
        }
    }

    public static void runNIDSMode(List<Packet> packets) {
        System.out.println("\n=== [MODE 3] RUNNING SNORT IN NETWORK INTRUSION DETECTION SYSTEM (NIDS) MODE ===");

        String alertPattern = "password";

        for (int i = 0; i < packets.size(); i++) {
            Packet p = packets.get(i);

            if (p.payload.toLowerCase().contains(alertPattern)) {
                System.out.println("!!! [ALERT] POLICY VIOLATION DETECTED IN PACKET #" + (i + 1) + " !!!");
                System.out.println("    [Reason]: Suspicious plain-text '" + alertPattern + "' found in Application Layer.");
                System.out.println("    [Source]: " + p.sourceIP + " | [Protocol]: " + p.protocol);
            } else {
                System.out.println("[PASS] Packet #" + (i + 1) + " verified safe.");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("EX NO: 8(b) - DEMONSTRATE INTRUSION DETECTION SYSTEM USING SNORT");
        System.out.println("=========================================================");

        List<Packet> networkTraffic = new ArrayList<>();
        networkTraffic.add(new Packet("192.168.1.5", "10.0.0.1", "TCP", "GET /index.html HTTP/1.1"));
        networkTraffic.add(new Packet("192.168.1.12", "10.0.0.5", "ICMP", "Ping Request"));
        networkTraffic.add(new Packet("172.16.0.4", "10.0.0.1", "TCP", "login_user=admin&password=MaliciousPass123"));

        runSnifferMode(networkTraffic);
        runLoggerMode(networkTraffic);
        runNIDSMode(networkTraffic);
    }
}