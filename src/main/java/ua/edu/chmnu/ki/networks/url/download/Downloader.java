package ua.edu.chmnu.ki.networks.url.download;

import lombok.Getter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the abstraction of single download thread
 */
@Getter
public class Downloader implements Runnable {

    private final URL srcUrl;
    private final String destDir;
    private final ProgressIndicator progressIndicator;
    private int bufferSize = 64;
    private boolean active;

    /**
     * Constructor of download thread
     *
     * @param url      - source file URL
     * @param destDir  - destination directory
     * @param progressIndicator - progress
     * @throws MalformedURLException
     */
    public Downloader(String url, String destDir, ProgressIndicator progressIndicator) throws MalformedURLException, UnsupportedEncodingException {
        this.srcUrl = new URL(url);
        this.destDir = destDir;
        this.progressIndicator = progressIndicator;
        this.active = false;
    }

    /**
     * Constructor of download thread
     *
     * @param url        - source file URL
     * @param destDir    - destination directory
     * @param progressIndicator   - progress
     * @param bufferSize - size of buffer to download
     * @throws MalformedURLException
     */
    public Downloader(String url, String destDir, ProgressIndicator progressIndicator, int bufferSize) throws MalformedURLException, UnsupportedEncodingException {
        this(url, destDir, progressIndicator);
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

    public void start() {
        active = true;
    }

    public void terminate() {
        active = false;
    }

    protected void runDownload() throws Exception {
        if (!active) {
            return;
        }

        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) srcUrl.openConnection();

            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new ConnectException();
            }

            String srcFileName = Optional.ofNullable(getFileNameFromHeader(urlConnection))
                    .orElseGet(() -> getFileNameFromUrl(srcUrl));
            String destPath = destDir + File.separator + srcFileName;
            checkDestDir();

            try (InputStream in = new BufferedInputStream(srcUrl.openStream());
                 OutputStream out = new BufferedOutputStream(new FileOutputStream(destPath))) {

                byte[] buffer = new byte[this.bufferSize];
                int count, readed = 0;
                final long total = urlConnection.getContentLength();
                while ((count = in.read(buffer, 0, this.bufferSize)) != -1 && active) {
                    readed += count;
                    out.write(buffer, 0, count);
                    if (progressIndicator != null) {
                        progressIndicator.progress(readed, total);
                    }
                }
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private String getFileNameFromUrl(URL srcUrl) {
        String path = srcUrl.getPath();
        int idx = path.lastIndexOf('/');
        if (idx < 0) {
            throw new RuntimeException("File name is not present");
        }
        return path.substring(idx + 1);
    }

    private String getFileNameFromHeader(HttpURLConnection urlConnection) {
        String header = urlConnection.getHeaderField("Content-Disposition");
        if (header == null) {
            return null;
        }
        final String tag = "filename=";
        int idx = header.indexOf(tag);
        if (idx < 0) {
            return null;
        }
        return header.substring(idx + tag.length());
    }

    @Override
    public void run() {
        try {
            runDownload();
        } catch (InterruptedException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DownloaderApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
