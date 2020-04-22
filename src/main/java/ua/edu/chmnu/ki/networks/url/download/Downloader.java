package ua.edu.chmnu.ki.networks.url.download;

import sun.nio.cs.StandardCharsets;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
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
     * @throws MalformedURLException
     */
    public Downloader(String url, String destDir, ProgressIndicate progress) throws MalformedURLException, UnsupportedEncodingException {
        this.srcUrl = new URL(url);
        this.destDir = destDir;
        this.progress = progress;
        String decodedUrl = URLDecoder.decode(url, "UTF-8");
        int idx = decodedUrl.lastIndexOf('/');
        if (idx < 0) {
            throw new MalformedURLException(url);
        }

        this.srcFileName = decodedUrl.substring(idx + 1);
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
     * @throws MalformedURLException
     */
    public Downloader(String url, String destDir, ProgressIndicate progress, int bufferSize) throws MalformedURLException, UnsupportedEncodingException {
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

    protected void downloadFromStream(ProgressIndicate progress) throws Exception {
        String destPath = destDir + File.separator + srcFileName;
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection)srcUrl.openConnection();

            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new ConnectException();
            }
            try (InputStream in = new BufferedInputStream(srcUrl.openStream());
                 OutputStream out = new BufferedOutputStream(new FileOutputStream(destPath));) {

                byte[] buffer = new byte[this.bufferSize];
                int count, readed = 0;
                final long total = urlConnection.getContentLength();
                while ((count = in.read(buffer, 0, this.bufferSize)) != -1 && active) {
                    readed += count;
                    out.write(buffer, 0, count);
                    if (progress != null) {
                        progress.progress(readed, total);

                    }
                }
            }
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    @Override
    public void run() {
        try {
            downloadFromStream(progress);
        } catch (InterruptedException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DownloaderDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
