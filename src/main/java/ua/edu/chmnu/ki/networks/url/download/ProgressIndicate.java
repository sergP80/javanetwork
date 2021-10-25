package ua.edu.chmnu.ki.networks.url.download;

@FunctionalInterface
public interface ProgressIndicate {
    void progress(int count, long total);
}
