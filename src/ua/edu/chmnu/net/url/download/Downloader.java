package ua.edu.chmnu.net.url.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This class is the abstraction of single download thread
 */
public class Downloader implements Runnable {
    private final String url;
    private final String fileName;
    private final ProgressProcess progress;

    private Thread workThread = null;
    
    /**
     * Constructor of download thread
     * @param url - source file URL
     * @param fileName - destination file
     * @param progress - progress
     */
    public Downloader(String url, String fileName, ProgressProcess progress) {
        this.url = url;
        this.fileName = fileName;
        this.progress = progress;
    }
    
    public void start()
    {
        workThread = new Thread(this);
        workThread.start();
    }   
    
    public void stop()
    {
        if (workThread != null && workThread.isAlive())
        {
            workThread.interrupt();
            workThread = null;
        }
    }
    
    protected void downloadFromStream(String url, String fileName, ProgressProcess progress) throws IOException, InterruptedException {
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
            Logger.getLogger(DownloaderDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
