import java.io.BufferedReader;
import java.io.InputStreamReader;

public class JavaPinger {

    public static void runSystemCommand(String command) {

        try {
            System.out.println("Executing command: " + command);
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            // reading output stream of the command
            String s;
            while ((s = inputStream.readLine()) != null) {
                System.out.println(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {
        if (args.length < 2) {
            System.out.println("You must specify a target and number of pings");
            System.out.println("JavaPinger <ip> <number of pings>");
            } else {
            String ip = args[0];
            String qty = args[1];
            // Use -n for Windows, -c for Linux/Mac
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("win")) {
                runSystemCommand("ping -n " + qty + " " + ip);
            } else {
                runSystemCommand("ping -c " + qty + " " + ip);
            }
            }
    }
}
