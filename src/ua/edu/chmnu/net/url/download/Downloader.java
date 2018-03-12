package ua.edu.chmnu.net.url.download;

import java.awt.EventQueue;
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

/**
 * This class is the abstraction of single download thread
 */
public class Downloader implements Runnable {

    private final URL srcUrl;
    private final String srcFileName;
    private final String destDir;
    private final ProgressIndicate progress;
    private int bufferSize = 64;
    private boolean active;
    private Thread workThread = null;

    /**
     * Constructor of download thread
     *
     * @param url - source file URL
     * @param destDir - destination directory
     * @param progress - progress
     * @throws java.net.MalformedURLException
     */
    public Downloader(String url, String destDir, ProgressIndicate progress) throws MalformedURLException {
        this.srcUrl = new URL(url);
        this.destDir = destDir;
        this.progress = progress;
        int idx = srcUrl.getFile().lastIndexOf('/');
        if (idx < 0) {
            throw new MalformedURLException(url);
        }

        this.srcFileName = srcUrl.getFile().substring(idx + 1);
        this.active = false;
        checkDestDir();
    }

    /**
     * Constructor of download thread
     *
     * @param url - source file URL
     * @param destDir - destination directory
     * @param progress - progress
     * @param bufferSize - size of buffer to download
     * @throws java.net.MalformedURLException
     */
    public Downloader(String url, String destDir, ProgressIndicate progress, int bufferSize) throws MalformedURLException {
        this(url, destDir, progress);
        this.bufferSize = bufferSize;
    }

    /**
     * Checks existing of destination directory
     */
    private void checkDestDir() {
        if (!new File(destDir).exists()) {
            new File(destDir).mkdirs();
        }
    }

    public String getSrcUrl() {
        return srcUrl.toString();
    }

    public String getDestDir() {
        return destDir;
    }

    public ProgressIndicate getProgress() {
        return progress;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public boolean isActive() {
        return active;
    }

    public Thread getWorkThread() {
        return workThread;
    }
    
    public void start() {                
        active = true;
        workThread = new Thread(this);
        workThread.start();
    }

    public void terminate() {
        active = false;
    }

    protected void downloadFromStream(ProgressIndicate progress) throws IOException, InterruptedException {
        String destPath = destDir + File.separator + srcFileName;
        try (InputStream in = new BufferedInputStream(srcUrl.openStream());
                OutputStream out = new BufferedOutputStream(new FileOutputStream(destPath));) {

            byte[] buffer = new byte[this.bufferSize];
            int count, readed = 0;
            final long total = srcUrl.openConnection().getContentLength();
            while ((count = in.read(buffer)) > 0 && active) {
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
            downloadFromStream(progress);
        } catch (IOException ex) {
            Logger.getLogger(DownloaderDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
