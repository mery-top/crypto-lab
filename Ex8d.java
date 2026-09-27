import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebVulnerabilityScanner {

    private static final String TARGET_URL = "https://example.com";

    private static final String[] COMMON_PATHS = {
        "/admin",
        "/login.jsp",
        "/config.php",
        "/.git/",
        "/backup.zip",
        "/robots.txt"
    };

    private static final String[] SECURITY_HEADERS = {
        "X-Frame-Options",
        "X-XSS-Protection",
        "Content-Security-Policy",
        "Strict-Transport-Security"
    };

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("STARTING AUTOMATED PENETRATION SCAN FOR: " + TARGET_URL);
        System.out.println("==================================================");

        checkSecurityHeaders(TARGET_URL);
        discoverCommonPaths(TARGET_URL);

        System.out.println("\n==================================================");
        System.out.println("SCAN COMPLETED SUCCESSFULLY.");
        System.out.println("==================================================");
    }

    private static void checkSecurityHeaders(String target) {
        System.out.println("\n[+] Scanning for Security Headers...");
        try {
            URL url = new URL(target);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            for (String header : SECURITY_HEADERS) {
                String value = connection.getHeaderField(header);
                if (value == null) {
                    System.out.println(" [!] VULNERABILITY FOUND: Missing security header -> " + header);
                } else {
                    System.out.println(" [OK] Secured: " + header + " is present (" + value + ")");
                }
            }
        } catch (IOException e) {
            System.out.println(" [X] Error connecting to target for header analysis: " + e.getMessage());
        }
    }

    private static void discoverCommonPaths(String target) {
        System.out.println("\n[+] Crawling Common Directories & Hidden Files...");
        for (String path : COMMON_PATHS) {
            String fullPath = target + path;
            try {
                URL url = new URL(fullPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println(" [!] ALERT: Discovered exposed path -> " + fullPath + " (HTTP 200 OK)");
                } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                    System.out.println(" [*] Restrictive access on -> " + fullPath + " (HTTP 403 Forbidden)");
                }
            } catch (IOException e) {
                // Ignore connection errors for specific paths to keep output clean
            }
        }
    }
}