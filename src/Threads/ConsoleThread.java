package Threads;

import Service.Service;
import ServiceBureau.ServiceBureau;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ConsoleThread extends Thread{
    private String file_name;
    private ServiceBureau bureau;

    public ConsoleThread(String file_name, ServiceBureau bureau) {
        this.file_name = file_name;
        this.bureau = bureau;
    }

    private void fillServiceBureau(){
        try {
            ArrayList<ArrayList<Service>> listOfImportedServices = importServices();
            for (ArrayList<Service> services : listOfImportedServices) {
                for (Service service : services) {
                    System.out.println("Услуга " + service.getDescription()
                            + " была импортирована.");
                }
                bureau.addService(services);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            ArrayList<ArrayList<Service>> services = new ArrayList<>();
            services.add(new ArrayList<>());
            services.add(new ArrayList<>());
            services.add(new ArrayList<>());
            services.add(new ArrayList<>());
            bureau.setServiceList(services);
        }
    }

    private ArrayList<ArrayList<Service>> importServices() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = null;
        //System.out.println("Чтение доступных услуг из файла " + file_name);
        try {
            File importFile = new File(file_name);
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
    public void run(){
        fillServiceBureau();
    }
}
