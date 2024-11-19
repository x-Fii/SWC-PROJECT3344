import java.util.ArrayList;
import java.util.List;

public class CustomerInfo {
    // Attributes
    private int customerId;
    private String customerName;
    private String vehiclePlateNumber;
    private int totalCost;
    private List<ServiceInfo> serviceList;

    // Constructor
    public CustomerInfo(int customerId, String customerName, String vehiclePlateNumber, int totalCost) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.vehiclePlateNumber = vehiclePlateNumber;
        this.totalCost = totalCost;
        this.serviceList = new ArrayList<>(); // Initialize the service list
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }

    public void setVehiclePlateNumber(String vehiclePlateNumber) {
        this.vehiclePlateNumber = vehiclePlateNumber;
    }
    
    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
    
        public List<ServiceInfo> getServiceList() {
        return serviceList; // Return the list of services
    }
     // Method to calculate the total cost of services
    public int getServiceTotal() {
        int total = 0;
        if (serviceList != null) {
            for (ServiceInfo service : serviceList) {
                total += service.getServicePrice(); // Assuming ServiceInfo has a getCost() method
            }
        }
        return total;
    }
    // Method to add a service to the service list
    public void addService(ServiceInfo service) {
        System.out.println("here");
        if (service != null) {
            serviceList.add(service); // Add the service to the list
            totalCost += service.getServicePrice(); // Update the total cost
        } else {
            System.out.println("betul la null");
        }
    }

    //public String getServiceList() {
    //    return serviceList.size();
    //}
    // ToString method for easy display of information
    @Override
    public String toString() {
        return "CustomerInfo{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", vehiclePlateNumber='" + vehiclePlateNumber + '\'' +
                '}';
    }
}