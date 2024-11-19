//This class show information about services offered.
class ServiceInfo {
    
    //Uniue identifier for the services
    private int serviceId;
    
    //Name of the services offered
    private String serviceType;
    
    //Price of service
    private int serviceCost;
    
    //Date of service
    private String serviceDate;
    
    //Service duration
    private String estimatedCompletionTime;
    
    //Number of services
    private int serviceTotal;
    
    //Constructor to initialize all fields of service
    public ServiceInfo(int serviceId, String serviceName, int servicePrice, String serviceDate, String estimatedCompletionTime, int serviceTotal) {
        this.serviceId = serviceId;
        this.serviceType = serviceName;
        this.serviceCost = servicePrice;
        this.serviceDate = serviceDate;
        this.estimatedCompletionTime = estimatedCompletionTime;
        this.serviceTotal = serviceTotal;
    }
    
        public ServiceInfo(int serviceId, String serviceName, int servicePrice, String serviceDate, String estimatedCompletionTime) {
        this.serviceId = serviceId;
        this.serviceType = serviceName;
        this.serviceCost = servicePrice;
        this.serviceDate = serviceDate;
        this.estimatedCompletionTime = estimatedCompletionTime;
    }
    
    public ServiceInfo(int serviceId, String serviceName, int servicePrice, String estimatedCompletionTime) {
        this.serviceId = serviceId;
        this.serviceType = serviceName;
        this.serviceCost = servicePrice;
        this.estimatedCompletionTime = estimatedCompletionTime;
    }
    //Return the unique ID of the service
    public int getServiceId() {
        return serviceId;
    }
    
    //Return the name of the service
    public String getServiceName() {
        return serviceType;
    }
    
    //Return the price of the service
    public int getServicePrice() {
        return serviceCost;
    }
    
    //Return the date of the service
    public String getServiceDate() {
        return serviceDate;
    }
    
    //Return the number of the service
    public String getServiceCompletionTime() {
        return estimatedCompletionTime;
    }
    
    public int getServiceTotal() {
        return serviceTotal;
    }
@Override
public String toString() {
    return "ServiceInfo{" +
           "id=" + serviceId +
           ", name='" + serviceType + '\'' +
           ", price=" + serviceCost +
           ", date='" + serviceDate + '\'' +
           ", completionTime='" + estimatedCompletionTime + '\'' +
           '}';
}
    
}