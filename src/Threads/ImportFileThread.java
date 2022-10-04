package Threads;

import Service.Service;
import ServiceBureau.ServiceBureau;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ImportFileThread extends FileThread {

    public ImportFileThread(String file_name, ServiceBureau bureau) {
        this.setFile_name(file_name);
        this.setBureau(bureau);
    }

    private void fillServiceBureau() {
        try {
            ArrayList<ArrayList<Service>> listOfImportedServices = importServices();
            for (ArrayList<Service> services : listOfImportedServices) {
                getBureau().addService(services);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            ArrayList<ArrayList<Service>> services = new ArrayList<>();
            services.add(new ArrayList<>());
            services.add(new ArrayList<>());
            services.add(new ArrayList<>());
            services.add(new ArrayList<>());
            getBureau().setServiceList(services);
        }
    }

    private ArrayList<ArrayList<Service>> importServices() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = null;
        try {
            File importFile = new File(getFile_name());
            fileInputStream = new FileInputStream(importFile);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            return (ArrayList<ArrayList<Service>>) inputStream.readObject();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        fillServiceBureau();
    }
}
