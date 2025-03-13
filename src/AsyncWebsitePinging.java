import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncWebsitePinging {
    public static void main(String[] args) throws IOException {
        ExecutorService servicePool = Executors.newSingleThreadExecutor();
        String websiteName = "google.com";

        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping -n 10 " + websiteName);
        try {
            Process process = pb.start();
            ContentReader readTask = new ContentReader(process);

            Future<List<String>> future = servicePool.submit(readTask);

            List<String> output = future.get();

            for (String line : output)
                System.out.println(line);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            servicePool.shutdown();
        }


    }
}
    class ContentReader implements Callable<List<String>> {
        Process process;
        public ContentReader(Process process){
            this.process = process;
        }
        public List<String> call() throws IOException {
            List<String> output = new ArrayList<>();

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";

            while ((line = br.readLine()) != null) {
                output.add(line);

            }
            return output;
        }

    }


