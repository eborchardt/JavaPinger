import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JavaPinger {

    private static final int DEFAULT_TIMEOUT = 2000; // 2 seconds timeout

    /**
     * Performs a ping to the specified host and returns the response time in milliseconds.
     * Returns -1 if the host is unreachable.
     */
    public static long ping(String host, int timeout) {
        try {
            InetAddress address = InetAddress.getByName(host);
            Instant start = Instant.now();
            boolean reachable = address.isReachable(timeout);
            Instant end = Instant.now();
            
            if (reachable) {
                return Duration.between(start, end).toMillis();
            } else {
                return -1; // Host is unreachable
            }
        } catch (IOException e) {
            System.err.println("Error pinging host: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * Performs multiple pings to the specified host and displays statistics.
     */
    public static void pingMultiple(String host, int count) {
        System.out.println("Pinging " + host + " with Java native ping:");
        System.out.println("Sending " + count + " ICMP Echo Requests");
        
        List<Long> responseTimes = new ArrayList<>();
        int successful = 0;
        
        try {
            InetAddress address = InetAddress.getByName(host);
            System.out.println("Pinging " + address.getHostAddress() + " [" + address.getHostName() + "]");
            
            for (int i = 0; i < count; i++) {
                System.out.print("Echo request " + (i + 1) + " of " + count + "... ");
                
                long responseTime = ping(host, DEFAULT_TIMEOUT);
                if (responseTime >= 0) {
                    System.out.println("Reply received in " + responseTime + " ms");
                    responseTimes.add(responseTime);
                    successful++;
                } else {
                    System.out.println("Request timed out");
                }
                
                // Add a small delay between pings
                if (i < count - 1) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            
            // Display statistics
            System.out.println("\nPing statistics for " + address.getHostAddress() + ":");
            System.out.println("    Packets: Sent = " + count + ", Received = " + successful + 
                    ", Lost = " + (count - successful) + " (" + 
                    (count > 0 ? (count - successful) * 100 / count : 0) + "% loss)");
            
            if (!responseTimes.isEmpty()) {
                long sum = 0;
                long min = Long.MAX_VALUE;
                long max = 0;
                
                for (long time : responseTimes) {
                    sum += time;
                    min = Math.min(min, time);
                    max = Math.max(max, time);
                }
                
                double avg = (double) sum / responseTimes.size();
                
                System.out.println("Approximate round trip times in milliseconds:");
                System.out.println("    Minimum = " + min + "ms, Maximum = " + max + 
                        "ms, Average = " + String.format("%.2f", avg) + "ms");
            }
            
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + host);
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("You must specify a target and number of pings");
            System.out.println("JavaPinger <ip> <number of pings>");
        } else {
            String ip = args[0];
            int qty;
            
            try {
                qty = Integer.parseInt(args[1]);
                pingMultiple(ip, qty);
            } catch (NumberFormatException e) {
                System.out.println("Number of pings must be an integer");
                System.out.println("JavaPinger <ip> <number of pings>");
            }
        }
    }
}
