

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** This class is responsible for executing a ping command 10 times and prints the output to the display*/
public class WebsitePing {

    public static void main(String[] args) throws IOException {
        String websiteName = "google.com"; //The website that is going to be pinged

        // create and execute to ping command with ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c", "ping -n 10 "+websiteName);
        // Start the process to execute the ping command
        Process process = pb.start();
        // Create a bufferedReader to read the putput from the process
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Each line will be hold by this variaable
        String line = "";
// Reads all the output from the process line by line
        while ((line = br.readLine()) != null) {
            //Prints it to the display
            System.out.println(line);
        }

    }
}
