package ua.edu.chmnu.ki.networks.url.download;

import ua.edu.chmnu.ki.networks.common.CmdLineParser;

import java.io.IOException;

public class DownloaderDemo {
	public static final class Keys {
		private Keys() {
		}

		public static final String URL = "url";
		public static final String DESTINATION = "dest";
		public static final String RESUME_DOWNLOAD = "resume-download";
	}

	public static final class Defaults {
		private Defaults() {
		}

		public static final String URL = "http://www.tutorialspoint.com/java/java_tutorial.pdf";
		public static final String DESTINATION = ".";
		public static final Boolean RESUME_DOWNLOAD = Boolean.FALSE;
	}

	public static void main(String[] args) throws IOException {
		CmdLineParser cmdLineParser = CmdLineParser.of(args);
		String sourceUrl = cmdLineParser.getStringOption(Keys.URL, Defaults.URL);
		String destDir = cmdLineParser.getStringOption(Keys.DESTINATION, Defaults.DESTINATION);
		Boolean resumeDownload = cmdLineParser.getBooleanOption(Keys.RESUME_DOWNLOAD, Defaults.RESUME_DOWNLOAD);

		ProgressIndicate progress = (long count, long total) -> {
			long percents = (long) (count * 100.0 / total);
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

		Downloader downloader = new Downloader(sourceUrl, destDir, progress);
		downloader.setResumeDownloadSupport(resumeDownload);
		downloader.start();
	}
}
