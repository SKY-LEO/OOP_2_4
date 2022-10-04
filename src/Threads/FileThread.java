package Threads;

import ServiceBureau.ServiceBureau;

import java.util.Objects;

public abstract class FileThread extends Thread {
    private String file_name;
    private ServiceBureau bureau;

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public ServiceBureau getBureau() {
        return bureau;
    }

    public void setBureau(ServiceBureau bureau) {
        this.bureau = bureau;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileThread that = (FileThread) o;
        return Objects.equals(file_name, that.file_name) && Objects.equals(bureau, that.bureau);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file_name, bureau);
    }

    @Override
    public String toString() {
        return "FileThread{" +
                "file_name='" + file_name + '\'' +
                ", bureau=" + bureau +
                '}';
    }
}
