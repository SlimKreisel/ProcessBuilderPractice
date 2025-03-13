
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/** Responsible for asynchronously pinging the website */

public class AsyncWebsitePinging {
    public static void main(String[] args) throws IOException {
        // Create a single thread that is managed by the Exceutor service. The Excecutor creates the thread
        ExecutorService servicePool = Executors.newSingleThreadExecutor();

        //The website that is going to be pinged
        String websiteName = "google.com";

        // create and execute to ping command with ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping -n 10 " + websiteName);
        try {
            //It starts the ping process that contains the ProcessBuilder
            Process process = pb.start();

            // Callable task reads the process
            ContentReader readTask = new ContentReader(process);

            // the future object contains the task of the executor service
            Future<List<String>> future = servicePool.submit(readTask);

            // Retrives the putput after the task is completed
            List<String> output = future.get();

            //Print each line
            for (String line : output)
                System.out.println(line);


        } catch (Exception e) {
            //Print the exception if an error occurs
            e.printStackTrace();
        } finally {
            // Prevents new tasks from being submitted
            servicePool.shutdown();
        }


    }
}
/** That class implements Callable and is responsible for reading the output of the main class. It is basically a method to use.*/
    class ContentReader implements Callable<List<String>> {

        private Process process;//The process whose output needs to be read

    //Constructor to initialise the process
        public ContentReader(Process process){
            this.process = process;
        }
        //Method executes when the task is submitted to an ExecutorService
        public List<String> call() throws IOException {
            List<String> output = new ArrayList<>();// List that stores the output from process

            // BufferedReader makes the output from InputStream readable line by line
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";

            // Read the output till there is no more information
            while ((line = br.readLine()) != null) {
                output.add(line);// Store each line in the list

            }
            return output;// Return the collected output
        }

    }


