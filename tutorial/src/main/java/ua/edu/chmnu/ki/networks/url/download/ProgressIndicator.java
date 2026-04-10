package ua.edu.chmnu.ki.networks.url.download;

@FunctionalInterface
public interface ProgressIndicator {
    void progress(int count, long total);
}
