import java.util.LinkedList;
import java.util.Queue;

public class ServiceLaneQueue {
    private Queue<CustomerInfo> queue;

    public ServiceLaneQueue() {
        this.queue = new LinkedList<>();
    }

    public void addCustomerToQueue(CustomerInfo customer) {
        queue.add(customer);
    }

    public CustomerInfo serveCustomer() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size(); // Added method to get the size of the queue
    }

    public void displayQueue(StringBuilder sb) {
        for (CustomerInfo customer : queue) {
            sb.append("Customer ID: ").append(customer.getCustomerId())
              .append(", Name: ").append(customer.getCustomerName())
              .append(", Vehicle: ").append(customer.getVehiclePlateNumber())
              .append(", Total Cost: RM").append(customer.getTotalCost())
              .append("\n");
        }
    }
}