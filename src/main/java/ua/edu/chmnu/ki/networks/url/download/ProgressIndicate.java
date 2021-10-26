package ua.edu.chmnu.ki.networks.url.download;

@FunctionalInterface
public interface ProgressIndicate {
    void progress(long count, long total);
}
