package ua.edu.chmnu.ki.networks.url.download;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
	private boolean resumeDownloadSupport;
	private Thread workThread = null;

	/**
	 * Constructor of download thread
	 *
	 * @param url      - source file URL
	 * @param destDir  - destination directory
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
	 * @param url        - source file URL
	 * @param destDir    - destination directory
	 * @param progress   - progress
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

	public boolean isResumeDownloadSupport() {
		return resumeDownloadSupport;
	}

	public void setResumeDownloadSupport(boolean resumeDownloadSupport) {
		this.resumeDownloadSupport = resumeDownloadSupport;
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
		boolean appendable = isResumeDownloadSupport() && isDestinationExists(destPath);
		long skipSize = appendable ? fileSize(destPath) : 0;
		downloadFromStream(progress, srcUrl, destPath, appendable, skipSize);
	}

	protected void downloadFromStream(ProgressIndicate progress, URL url, String destPath, boolean appendable, long skipSize) throws Exception {
		HttpURLConnection urlConnection = null;

		try {
			urlConnection = (HttpURLConnection) url.openConnection();

			if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new ConnectException();
			}
			try (InputStream in = new BufferedInputStream(url.openStream());
			     OutputStream out = new BufferedOutputStream(new FileOutputStream(destPath, appendable));) {

				if (skipSize > 0 && in.skip(skipSize) < skipSize) {
					throw new IllegalStateException("Cannot skip origin");
				}

				byte[] buffer = new byte[this.bufferSize];
				long readed = skipSize > 0 ? skipSize : 0;

				final long total = urlConnection.getContentLength();

				for (int count; (count = in.read(buffer, 0, this.bufferSize)) != -1 && active; ) {
					readed += count;
					out.write(buffer, 0, count);
					if (progress != null) {
						progress.progress(readed, total);
					}
				}
			}
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
	}

	private boolean isDestinationExists(String filePath) {
		return Files.exists(Paths.get(filePath));
	}

	private long fileSize(String filePath) throws IOException {
		return Files.size(Paths.get(filePath));
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
