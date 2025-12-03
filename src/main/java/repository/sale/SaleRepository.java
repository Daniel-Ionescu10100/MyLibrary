package repository.sale;

public interface SaleRepository {
    boolean addSale(Long employeeId, Long customerId, String author, String title, int quantity, double totalCost);
    int getSalesCountForEmployeeLastMonth(Long employeeId);
    double getSalesRevenueForEmployeeLastMonth(Long employeeId);
}

