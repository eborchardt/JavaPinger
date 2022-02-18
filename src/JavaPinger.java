import java.io.BufferedReader;
import java.io.InputStreamReader;

public class JavaPinger {

    public static void runSystemCommand(String command) {

        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            String s = "";
            // reading output stream of the command
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
            runSystemCommand("ping -n " + qty + " " + ip);
            }
    }
}