import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class WebsitePing {

    public static void main(String[] args) throws IOException {
        String websiteName = "google.com";

        ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c", "ping -n 10 "+websiteName);
        Process process = pb.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

    }
}
