package ServiceBureau;

import Service.Service;

import java.util.ArrayList;
import java.util.Objects;

public class ServiceBureau {
    private ArrayList<ArrayList<Service>> serviceList;

    public ServiceBureau() {
        serviceList = new ArrayList<>();
    }

    public ServiceBureau(ArrayList<ArrayList<Service>> serviceList) {
        this.serviceList = serviceList;
    }

    public ArrayList<ArrayList<Service>> getServiceList() {
        return serviceList;
    }

    public void setServiceList(ArrayList<ArrayList<Service>> serviceList) {
        this.serviceList = serviceList;
    }

    public void addService(ArrayList<Service> services){
        serviceList.add(services);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceBureau that = (ServiceBureau) o;
        return Objects.equals(serviceList, that.serviceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceList);
    }

    @Override
    public String toString() {
        return "ServiceBureau.ServiceBureau{" +
                "serviceList=" + serviceList +
                '}';
    }
}
