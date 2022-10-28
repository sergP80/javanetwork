package ua.edu.chmnu.ki.networks.url.download;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloaderApplication {
    private static final String DEFAULT_SAVE_DIR = ".";

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException, InterruptedException {
        String destDir = args.length >= 2 ? args[1] : DEFAULT_SAVE_DIR;

        String[] urls = {
                "http://www.tutorialspoint.com/java/java_tutorial.pdf",
                "https://www.iitk.ac.in/esc101/share/downloads/javanotes5.pdf"
        };

        ProgressIndicator progress = (int count, long total) -> {
            int percents = (int) (count * 100.0 / total);
            synchronized (System.out) {
                if (percents < 10) {
                    System.out.print("\b\b");
                } else {
                    System.out.print("\b\b\b");
                }
                System.out.print(percents + "%");

                if (count == total) {
                    System.out.println();
                }
            }
        };

        for (var url: urls) {
            Downloader downloader = new Downloader(url, destDir, progress);
            downloader.start();

            EXECUTOR_SERVICE.submit(downloader);
        }
        System.out.println("Waiting for downloading...");

        EXECUTOR_SERVICE.shutdown();

        while (!EXECUTOR_SERVICE.isTerminated()) {
            Thread.sleep(100);
        }

        System.out.println("All tasks were done!");
    }
}
