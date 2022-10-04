package Threads;

import Service.Service;
import ServiceBureau.ServiceBureau;

import java.io.*;
import java.util.ArrayList;

public class ExportFileThread extends FileThread {

    public ExportFileThread(String file_name, ServiceBureau bureau) {
        this.setFile_name(file_name);
        this.setBureau(bureau);
    }

    private void exportServices() throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            File exportServicesFile = new File(getFile_name());
            fileOutputStream = new FileOutputStream(exportServicesFile);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(getBureau().getServiceList());
            int sum = 0;
            for (ArrayList<Service> service : getBureau().getServiceList()) {
                sum += service.size();
            }
            System.out.println("Выполнен экспорт " + sum + " услуг в файл.");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        try {
            exportServices();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
