import java.util.Stack;

public class CompletedTransactions {
    private Stack<CustomerInfo> transactionStack;

    public CompletedTransactions() {
        this.transactionStack = new Stack<>();
    }

    public void addCompletedTransaction(CustomerInfo customer) {
        transactionStack.push(customer);
    }

    public void displayCompletedTransactions(StringBuilder sb) {
        for (CustomerInfo customer : transactionStack) {
            sb.append("Customer ID: ").append(customer.getCustomerId())
            .append(", Name: ").append(customer.getCustomerName())
            .append(", Vehicle: ").append(customer.getVehiclePlateNumber())
            .append("\n");
        }
    }
}