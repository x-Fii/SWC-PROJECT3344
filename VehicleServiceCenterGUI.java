import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.Random;

public class VehicleServiceCenterGUI extends JFrame {
    private LinkedList<CustomerInfo> customerList = new LinkedList<>();
    private LinkedList<ServiceInfo> serviceList = new LinkedList<>();
    private ServiceLaneQueue lane1 = new ServiceLaneQueue();
    private ServiceLaneQueue lane2 = new ServiceLaneQueue();
    private ServiceLaneQueue lane3 = new ServiceLaneQueue();
    private CompletedTransactions completedTransactions = new CompletedTransactions();

    private JTextField serviceField; // For entering service names
    private JTextField customerIdField; // For entering customer ID to remove service

    public VehicleServiceCenterGUI() {
        setTitle("Vehicle Service Center");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Read customers and services from files
        loadCustomersFromFile("CustomerList.txt");
        loadServicesFromFile("ServiceList.txt");
        assignServicesToCustomers();

        // Set up tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Customer List", createCustomerPanel());
        tabbedPane.addTab("Service Lanes", createServiceLanePanel());
        tabbedPane.addTab("Completed Transactions", createCompletedTransactionsPanel());

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(20, 50);
        JScrollPane scrollPane = new JScrollPane(textArea);

        StringBuilder sb = new StringBuilder("Customer List:\n");
        for (CustomerInfo customer : customerList) {
            sb.append("ID: ").append(customer.getCustomerId())
            .append(", Name: ").append(customer.getCustomerName())
            .append(", Vehicle: ").append(customer.getVehiclePlateNumber())
            .append("\nServices:\n");

            for (ServiceInfo service : customer.getServiceList()) {
                sb.append("  - ").append(service.getServiceName())
                .append(" (RM").append(service.getServicePrice()).append(")\n");
            }
            sb.append("Total Cost: RM").append(customer.getServiceTotal()).append("\n\n");
        }

        textArea.setText(sb.toString());
        textArea.setEditable(false);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createServiceLanePanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3));

        JTextArea lane1Area = new JTextArea();
        JTextArea lane2Area = new JTextArea();
        JTextArea lane3Area = new JTextArea();

        JScrollPane lane1Scroll = new JScrollPane(lane1Area);
        JScrollPane lane2Scroll = new JScrollPane(lane2Area);
        JScrollPane lane3Scroll = new JScrollPane(lane3Area);

        lane1Area.setText(getLaneDetails("Lane 1", lane1));
        lane2Area.setText(getLaneDetails("Lane 2", lane2));
        lane3Area.setText(getLaneDetails("Lane 3", lane3));

        lane1Area.setEditable(false);
        lane2Area.setEditable(false);
        lane3Area.setEditable(false);

        panel.add(lane1Scroll);
        panel.add(lane2Scroll);
        panel.add(lane3Scroll);

        // Create buttons for adding, removing, and completing transactions
        JPanel buttonPanel = new JPanel();
        serviceField = new JTextField(15);
        customerIdField = new JTextField(5);

        JButton addServiceButton = new JButton("Add Service");
        JButton removeServiceButton = new JButton("Remove Service");
        JButton completeTransactionButton = new JButton("Complete Transaction");

        addServiceButton.addActionListener(e -> addService());
        removeServiceButton.addActionListener(e -> removeService());
        completeTransactionButton.addActionListener(e -> completeTransaction());

        buttonPanel.add(new JLabel("Service Name:"));
        buttonPanel.add(serviceField);
        buttonPanel.add(addServiceButton);
        buttonPanel.add(new JLabel("Customer ID:"));
        buttonPanel.add(customerIdField);
        buttonPanel.add(removeServiceButton);
        buttonPanel.add(completeTransactionButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCompletedTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(20, 50);
        JScrollPane scrollPane = new JScrollPane(textArea);

        StringBuilder sb = new StringBuilder("Completed Transactions:\n");
        completedTransactions.displayCompletedTransactions(sb);

        textArea.setText(sb.toString());
        textArea.setEditable(false);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private String getLaneDetails(String laneName, ServiceLaneQueue lane) {
        StringBuilder sb = new StringBuilder(laneName + ":\n");
        if (lane.isEmpty()) {
            sb.append("No customers currently in this lane.\n");
        } else {
            lane.displayQueue(sb);
        }
        return sb.toString();
    }

    private void loadCustomersFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int customerId = Integer.parseInt(parts[0]);
                String customerName = parts[1];
                String vehiclePlateNumber = parts[2];
                String services = parts[3];
                int serviceTotal=0;
    
                // Parse the services string and add the corresponding services to the CustomerInfo object
                String[] serviceIds = services.split(" ");
                CustomerInfo customerInfo = new CustomerInfo(customerId, customerName, vehiclePlateNumber, serviceTotal);
                for (String serviceId : serviceIds) {
                    // Assuming you have a method to get the ServiceInfo object by ID
                    ServiceInfo serviceInfo = getServiceInfoById(serviceId);
                    customerInfo.addService(serviceInfo);
                }
    
                customerList.add(customerInfo);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CustomerList.txt: " + e.getMessage());
        }
    }

    private ServiceInfo getServiceInfoById(String serviceId) {
        for (ServiceInfo serviceInfo : serviceList) {
            if (serviceInfo.getServiceId() == Integer.parseInt(serviceId)) {
                return serviceInfo;
            }
        }
        return null; // Return null if no service is found with the given ID
    }
    
    private void loadServicesFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int serviceId = Integer.parseInt(parts[0]);
                String serviceName = parts[1];
                int servicePrice = Integer.parseInt(parts[2]);
                String serviceDate = parts[3];
                String estimatedCompletionTime = parts[4];
                //int serviceTotal = Integer.parseInt(parts[5]);// Added to match constructor
                serviceList.add(new ServiceInfo(serviceId, serviceName, servicePrice, serviceDate, estimatedCompletionTime));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading ServiceList.txt: " + e.getMessage());
        }
    }


    private void assignServicesToCustomers() {
    Random random = new Random();
    int lane1Counter = 0; // Counter for customers assigned to Lane 1
    int lane2Counter = 0; // Counter for customers assigned to Lane 2

    for (CustomerInfo customer : customerList) {
        int serviceCount = random.nextInt(5) + 1; // Randomly assign 1 to 5 services
        for (int i = 0; i < serviceCount; i++) {
            ServiceInfo service = serviceList.get(random.nextInt(serviceList.size()));
            customer.addService(service);
        }

        // Assign customers to service lanes
        if (customer.getServiceList().size() <= 3) {
            if (lane1Counter <= lane2Counter) {
                lane1.addCustomerToQueue(customer);
                lane1Counter++; // Increment the counter for Lane 1
            } else {
                lane2.addCustomerToQueue(customer);
                lane2Counter++; // Increment the counter for Lane 2
            }
        } else {
            lane3.addCustomerToQueue(customer);
        }
    }
}

    private void addService() {
        String serviceName = serviceField.getText().trim();
        if (!serviceName.isEmpty()) {
            // Logic to add service to the selected customer
            // This could involve finding the customer by ID and adding the service
            JOptionPane.showMessageDialog(this, "Service '" + serviceName + "' added.");
            serviceField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a service name.");
        }
    }

    private void removeService() {
        String customerIdStr = customerIdField.getText().trim();
        if (!customerIdStr.isEmpty()) {
            int customerId = Integer.parseInt(customerIdStr);
            // Logic to remove service from the selected customer
            // This could involve finding the customer by ID and removing the service
            JOptionPane.showMessageDialog(this, "Service removed from customer ID: " + customerId);
            customerIdField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a customer ID.");
        }
    }

    private void completeTransaction() {
        // Logic to complete the transaction for the selected customer
        // This could involve processing the services and updating the completed transactions
        JOptionPane.showMessageDialog(this, "Transaction completed for the selected customer.");
    }

    public static void main(String[] args) {
        new VehicleServiceCenterGUI();
    }
}