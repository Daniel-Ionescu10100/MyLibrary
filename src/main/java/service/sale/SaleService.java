package service.sale;

public interface SaleService {
    boolean recordSale(Long employeeId, Long customerId, String author, String title, int quantity, double totalCost);
    int getSalesCountLastMonth(Long employeeId);
    double getSalesRevenueLastMonth(Long employeeId);
}


