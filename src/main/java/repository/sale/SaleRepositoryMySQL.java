package repository.sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleRepositoryMySQL implements SaleRepository {

    private final Connection connection;

    public SaleRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addSale(Long employeeId, Long customerId, String author, String title, int quantity, double totalCost) {
        String sql = "INSERT INTO sale (employee_id, customer_id, book_author, book_title, quantity, sale_date, total_cost) " +
                "VALUES (?, ?, ?, ?, ?, NOW(), ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, employeeId);
            stmt.setLong(2, customerId);
            stmt.setString(3, author);
            stmt.setString(4, title);
            stmt.setInt(5, quantity);
            stmt.setDouble(6, totalCost);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public int getSalesCountForEmployeeLastMonth(Long employeeId) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM sale " +
                "WHERE employee_id = ? AND MONTH(sale_date) = MONTH(CURRENT_DATE()) " +
                "AND YEAR(sale_date) = YEAR(CURRENT_DATE())";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public double getSalesRevenueForEmployeeLastMonth(Long employeeId) {
        double total = 0;
        String sql = "SELECT SUM(total_cost) AS total FROM sale " +
                "WHERE employee_id = ? AND MONTH(sale_date) = MONTH(CURRENT_DATE()) " +
                "AND YEAR(sale_date) = YEAR(CURRENT_DATE())";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

}

