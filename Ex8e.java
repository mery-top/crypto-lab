Ex 8e
Here is the Java code that replicates the infinite application-opening behavior described in your lab manual experiment (Defeating Malware: Building Trojans), along with an explanation of its execution output.
## Java Source Code

import java.io.IOException;
public class TrojanSimulation {
    public static void main(String[] args) {
        System.out.println("Executing Trojan simulation... Press Ctrl+C in the console to stop.");
        
        try {
            // Infinite loop to repeatedly launch system applications
            while (true) {
                // Opens Notepad
                Runtime.getRuntime().exec("notepad.exe");
                
                // Opens MS Paint
                Runtime.getRuntime().exec("mspaint.exe");
                
                // Opens File Explorer
                Runtime.getRuntime().exec("explorer.exe");
                
                // Opens Command Prompt
                Runtime.getRuntime().exec("cmd.exe /c start");
                
                // A brief delay (e.g., 500ms) is recommended to prevent immediate system crash
                Thread.sleep(500); 
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Execution interrupted: " + e.getMessage());
        }
    }
}

------------------------------
## Program Output## 1. Console Text Output
When you run the program, the standard console window will display:

Executing Trojan simulation... Press Ctrl+C in the console to stop.

## 2. Expected System Behavior (Visual Output)

* Window Flooding: Every half-second, new separate windows of Notepad, MS Paint, File Explorer, and Command Prompt will continuously pop up on your screen.
* Resource Exhaustion: Because the while(true) loop runs infinitely, hundreds of windows will quickly saturate the desktop, consuming the system's CPU and RAM until the computer becomes unresponsive or requires a restart (matching Step 5 in the procedure).

Would you like to know how to safely limit the number of windows generated so you can test it without crashing your operating system?

   1. Yes, show me a version with a fixed iteration limit.
   2. No, this covers what I need for the lab record.

Please reply with 1 or 2 to proceed!