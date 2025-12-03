package service.sale;

import repository.sale.SaleRepository;

public class SaleServiceImplementation implements SaleService {

    private final SaleRepository saleRepository;

    public SaleServiceImplementation(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public boolean recordSale(Long employeeId, Long customerId, String author, String title, int quantity, double totalCost) {
        return saleRepository.addSale(employeeId, customerId, author, title, quantity, totalCost);
    }

    @Override
    public int getSalesCountLastMonth(Long employeeId) {
        return saleRepository.getSalesCountForEmployeeLastMonth(employeeId);
    }

    @Override
    public double getSalesRevenueLastMonth(Long employeeId) {
        return saleRepository.getSalesRevenueForEmployeeLastMonth(employeeId);
    }
}

