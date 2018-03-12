package ua.edu.chmnu.net.url.download;

import java.io.IOException;
import java.net.MalformedURLException;

public class DownloaderDemo {

    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
        String url = args.length >= 1 ? args[0] : "http://www.tutorialspoint.com/java/java_tutorial.pdf";
        String destDir = args.length >= 2 ? args[1] : "C:\\Temp";

        ProgressIndicate progress = (int count, long total) -> {
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

        Downloader downloader = new Downloader(url, destDir, progress);
        downloader.start(); 
    }
}
