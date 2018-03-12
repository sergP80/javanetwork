package ua.edu.chmnu.net.url.download;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DownloaderDemo {

    public static void main(String[] args) throws MalformedURLException, IOException {
        String url = args.length >= 1 ? args[0] : "http://www.tutorialspoint.com/java/java_tutorial.pdf";
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        String destDir = args.length >= 2 ? args[1] : "C:\\Temp";
        if (!new File(destDir).exists()) {
            new File(destDir).mkdirs();
        }
        fileName = destDir + File.separator + fileName;

        new Downloader(url, fileName, (int count, long total) -> {
            int percents = (int) (count * 100.0 / total);
            if (percents < 10) {
                System.out.print("\b\b");
            } else {
                System.out.print("\b\b\b");
            }
            System.out.print(percents + "%");

            if (count == total) {
                System.out.println();
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(DownloaderDemo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }).start();

    }
}
