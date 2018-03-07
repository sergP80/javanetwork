package ua.edu.chmnu.net.url.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Download implements Runnable {

    private final String url;
    private final String fileName;
    private final ProgressProcess progress;

    public Download(String url, String fileName, ProgressProcess progress) {
        this.url = url;
        this.fileName = fileName;
        this.progress = progress;
    }
    
    void start()
    {
        new Thread(this).start();
    }

    public static void main(String[] args) throws MalformedURLException, IOException {
        String url = args.length >= 1 ? args[0] : "http://www.tutorialspoint.com/java/java_tutorial.pdf";
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        String destDir = args.length >= 2 ? args[1] : "C:\\Temp";
        if (!new File(destDir).exists()) {
            new File(destDir).mkdirs();
        }
        fileName = destDir + File.separator + fileName;
        
        new Download(url, fileName, new ProgressProcess() {
            @Override
            public void progress(int count, long total) {
                int percents = (int) (count * 100.0 / total);
                if (percents < 10) {
                    System.out.print("\b\b");
                } else {
                    System.out.print("\b\b\b");
                }
                System.out.print(percents + "%");
            }
        }).start();

    }

    protected void downloadFromStream(String url, String fileName, ProgressProcess progress) throws IOException {
        try (InputStream in = new BufferedInputStream(new URL(url).openStream());
                OutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));) {

            byte[] buffer = new byte[1024 * 8];
            int count = 0, readed = 0;
            long total = new URL(url).openConnection().getContentLength();
            while ((count = in.read(buffer)) > 0) {
                readed += count;
                out.write(buffer, 0, count);
                if (progress != null) {
                    progress.progress(readed, total);
                }

            }
        }
    }

    @Override
    public void run() {
        try {
            downloadFromStream(url, fileName, progress);
        } catch (IOException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
