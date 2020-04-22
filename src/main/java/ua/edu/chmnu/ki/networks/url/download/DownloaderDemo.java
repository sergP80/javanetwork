package ua.edu.chmnu.ki.networks.url.download;

import java.io.IOException;

public class DownloaderDemo {
    private static final String DEFAULT_SAVE_DIR = ".";
    private static final String DEFAULT_URL = "http://www.tutorialspoint.com/java/java_tutorial.pdf";

    public static void main(String[] args) throws IOException {
        String url = args.length >= 1 ? args[0] : DEFAULT_URL;
        String destDir = args.length >= 2 ? args[1] : DEFAULT_SAVE_DIR;

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
