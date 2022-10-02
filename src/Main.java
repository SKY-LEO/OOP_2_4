import Service.*;
import ServiceBureau.ServiceBureau;
import Threads.ConsoleThread;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private final static String services_file = "services.txt";

    static ServiceBureau serviceBureau;

    static String[] main_menu = {
            "Просмотр доступных услуг",
            "Заказ услуг",
            "Просмотр заказанных услуг",
            "Создать новую услугу",
            "Изменить услугу",
            "Выход"
    };

    static String[] create_menu = {
            "Водопроводчик",
            "Электрик",
            "Уборщик",
            "Вывоз мусора"
    };

    public static void main(String[] args) {
        serviceBureau = new ServiceBureau();
        menu();
    }

    private static void menu() {
        //fillServiceBureau();
        ConsoleThread thread = new ConsoleThread(services_file, serviceBureau);
        thread.start();
        ArrayList<Service> order = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int number;
        boolean flag = true;
        while (flag) {
            printMenuOptions(main_menu);
            try {
                number = scanner.nextInt();
                scanner.nextLine();
                switch (number) {
                    case 1 -> viewServices(serviceBureau.getServiceList());
                    case 2 -> orderService(scanner, order);
                    case 3 -> viewOrderedServices(order);
                    case 4 -> createService(scanner);
                    case 5 -> editService(scanner, serviceBureau.getServiceList());
                    case 6 -> {
                        flag = false;
                        try {
                            exportServices();
                            serviceBureau.getServiceList().clear();
                        } catch (FileNotFoundException e) {
                            System.out.println(e.getMessage());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("До свидания!");
                    }
                    default -> System.out.println("Введите целое число в промежутке от 0 до " + (main_menu.length - 1));
                }
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод. Введите целое число");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void viewServices(ArrayList<ArrayList<Service>> all_services) {
        final int[] counter = {0, 1};
        System.out.println("Список всех услуг:");
        all_services.forEach(services -> {
            System.out.println((counter[0] + 1) + ") " + create_menu[counter[0]]);
            services.forEach(service -> {
                System.out.println(" " + (counter[1]++) + ". " + service.getDescription()
                        + " -> " + service.getPrice() + " руб.");
            });
            counter[0]++;
            counter[1] = 1;
        });
    }

    private static void viewServices(ArrayList<ArrayList<Service>> all_services, int index) {
        final int[] counter = {1};
        System.out.println("Список услуг " + create_menu[index] + ":");
        all_services.get(index).forEach(service -> {
            System.out.println(" " + (counter[0]++) + ". " + service.getDescription()
                    + " -> " + service.getPrice() + " руб.");
        });
    }

    private static void orderService(Scanner scanner, ArrayList<Service> order) {
        ArrayList<Service> service;
        viewServices(serviceBureau.getServiceList());
        int index_of_services = inputNumber(scanner, "Выберите вид услуги:");
        try {
            service = serviceBureau.getServiceList().get(index_of_services - 1);
            viewServices(serviceBureau.getServiceList(), index_of_services - 1);
            int index_of_service = inputNumber(scanner, "Выберите услугу:");
            order.add(service.get(index_of_service - 1));
            System.out.println("Заказано!");
        } catch (IndexOutOfBoundsException error) {
            System.out.println("Введите номер услуги в заданных пределах!");
        }
    }

    private static void viewOrderedServices(ArrayList<Service> order) {
        int total_price = 0;
        String previous_class = "";
        order.sort(Comparator.comparing((Service object) -> object.getClass().getName()));
        System.out.println("Заказано: ");
        for (Service ordered_services : order) {
            if (!previous_class.equals(ordered_services.getClass().getName())) {
                System.out.println(ordered_services + ":");
                previous_class = ordered_services.getClass().getName();
            }
            System.out.println(" " + ordered_services.getDescription()
                    + " -> " + ordered_services.getPrice());
            total_price += ordered_services.getPrice();
        }
        System.out.println("Итого: " + total_price);
    }

    private static void createService(Scanner scanner) {
        int variant;
        int price;
        String service_name;
        printMenuOptions(create_menu);
        try {
            variant = scanner.nextInt();
            scanner.nextLine();
            service_name = enterServiceName(scanner);
            price = inputNumber(scanner, "Введите стоимость услуги: ");
            switch (variant) {
                case 1 -> {
                    Plumber plumber = new Plumber(service_name, price);
                    serviceBureau.getServiceList().get(0).add(plumber);
                }
                case 2 -> {
                    Electrician electrician = new Electrician(service_name, price);
                    serviceBureau.getServiceList().get(1).add(electrician);
                }
                case 3 -> {
                    Cleaner cleaner = new Cleaner(service_name, price);
                    serviceBureau.getServiceList().get(2).add(cleaner);
                }
                case 4 -> {
                    GarbageCollection garbageCollection = new GarbageCollection(service_name, price);
                    serviceBureau.getServiceList().get(3).add(garbageCollection);
                }
                default -> System.out.println("Введите целое число в промежутке от 0 до " +
                        (create_menu.length - 1));
            }
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод. Введите целое число");
            scanner.nextLine();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ошибка записи в ArrayList!");
            scanner.nextLine();
        }
    }

    private static void editService(Scanner scanner, ArrayList<ArrayList<Service>> services) {
        viewServices(services);
        int index_of_services = inputNumber(scanner, "Выберите вид услуги:");
        try {
            viewServices(serviceBureau.getServiceList(), index_of_services - 1);
            int index_of_service = inputNumber(scanner, "Выберите услугу:");
            int to_change = inputNumber(scanner,
                    "Что вы хотите изменить?\n 1) Название услуги\n 2) Стоимость\n 3) Всё");
            scanner.nextLine();
            if (to_change == 1) {
                services.get(index_of_services - 1).get(index_of_service - 1).setDescription(enterServiceName(scanner));
            } else if (to_change == 2) {
                services.get(index_of_services - 1).get(index_of_service - 1).setPrice(
                        inputNumber(scanner, "Введите стоимость услуги: "));
            } else if (to_change == 3) {
                services.get(index_of_services - 1).get(index_of_service - 1).setDescription(enterServiceName(scanner));
                services.get(index_of_services - 1).get(index_of_service - 1).setPrice(
                        inputNumber(scanner, "Введите стоимость услуги: "));
            } else {
                System.out.println("Введите значение в заданном диапазоне!");
            }
        } catch (IndexOutOfBoundsException error) {
            System.out.println("Введите номер услуги в заданных пределах!");
        }
    }

    interface replaceNotLetters {
        String replace(String str);
    }

    private static String enterServiceName(Scanner scanner) {
        System.out.println("Введите название услуги: ");
        replaceNotLetters replace_string = (first_string) -> first_string.replaceAll("[^А-Яа-я ']", "");
        return replace_string.replace(scanner.nextLine());
    }

    private static int inputNumber(Scanner scanner, String message) {
        System.out.println(message);
        int num;
        while (true) {
            try {
                num = scanner.nextInt();
                return num;
            } catch (InputMismatchException e) {
                System.out.println("Введите целое число!");
                scanner.nextLine();
            }
        }
    }

    private static void printMenuOptions(String[] options) {
        System.out.println("Выберите пункт меню:");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ") " + options[i]);
        }
    }

    /*private static void fillServiceBureau(){
        try {
            ArrayList<ArrayList<Service>> listOfImportedServices = importServices();
            for (ArrayList<Service> services : listOfImportedServices) {
                for (Service service : services) {
                    System.out.println("Услуга " + service.getDescription()
                            + " была импортирована.");
                }
                serviceBureau.addService(services);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            ArrayList<ArrayList<Service>> services = new ArrayList<>();
            services.add(new ArrayList<>());
            services.add(new ArrayList<>());
            services.add(new ArrayList<>());
            services.add(new ArrayList<>());
            serviceBureau.setServiceList(services);
        }
    }

    private static ArrayList<ArrayList<Service>> importServices() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = null;
        System.out.println("Чтение доступных услуг из файла " + services_file);
        try {
            File importCardsFile = new File(services_file);
            fileInputStream = new FileInputStream(importCardsFile);
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
    */
    private static void exportServices() throws IOException {
        FileOutputStream fileOutputStream = null;
        System.out.println("Запись услуг в файл " + services_file);
        try {
            File exportServicesFile = new File(services_file);
            fileOutputStream = new FileOutputStream(exportServicesFile);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(serviceBureau.getServiceList());
            int sum = 0;
            for (ArrayList<Service> service : serviceBureau.getServiceList()) {
                sum += service.size();
            }
            System.out.println("Выполнен экспорт " + sum + " услуг в файл");
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

}
