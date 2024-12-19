import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartmentRepository {
    private Connection baglanti;

    public ApartmentRepository() {
        this.baglanti = DatabaseConnection.getInstance().getConnection();
    }


    // Kiracının apartmanını getiren metot
    public Apartment kiraciApartmaniniGetir(int tenantId) throws SQLException {
        String sql = "SELECT * FROM apartments WHERE tenant_id = ?";
        PreparedStatement preparedStatement = baglanti.prepareStatement(sql);
        preparedStatement.setInt(1, tenantId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Apartment(
                    resultSet.getInt("apartment_id"),
                    resultSet.getString("block_name"),
                    resultSet.getString("type"),
                    resultSet.getString("status"),
                    resultSet.getBoolean("furnished"),
                    resultSet.getInt("owner_id"),
                    resultSet.getInt("tenant_id")
            );
        }
        return null;
    }

    // Kiracının apartmanını boşaltan metot
    public boolean kiraciApartmaniniBosalt(int tenantId) throws SQLException {
        String sql = "UPDATE apartments SET tenant_id = NULL, status = 'Empty' WHERE tenant_id = ?";
        PreparedStatement preparedStatement = baglanti.prepareStatement(sql);
        preparedStatement.setInt(1, tenantId);

        int etkilenmisSatirlar = preparedStatement.executeUpdate();
        return etkilenmisSatirlar > 0;
    }

    // Apartman durumunu güncelleyen metot
    public boolean apartmanDurumGuncelle(int apartmentId, String yeniDurum, Integer newTenantId, Integer newOwnerId) {
        String sql = "UPDATE apartments SET status = ?, tenant_id = ?, owner_id = ? WHERE apartment_id = ?";

        try (PreparedStatement stmt = baglanti.prepareStatement(sql)) {
            // Yeni durum belirleme
            if ("Rented".equals(yeniDurum) && newTenantId != null) {
                stmt.setString(1, "Rented");
                stmt.setInt(2, newTenantId);
                stmt.setNull(3, Types.INTEGER);
            } else if ("Sold".equals(yeniDurum) && newOwnerId != null) {
                stmt.setString(1, "Sold");
                stmt.setNull(2, Types.INTEGER);
                stmt.setInt(3, newOwnerId);
            } else {
                stmt.setString(1, "Empty");
                stmt.setNull(2, Types.INTEGER);
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.setInt(4, apartmentId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Apartman durumu güncellenirken hata oluştu: " + e.getMessage());
            return false;
        }
    }

    // Ev sahibinin apartmanını getiren metot
    public Apartment sahipApartmaniniGetir(int ownerId) throws SQLException {
        String sql = "SELECT * FROM apartments WHERE owner_id = ?";
        PreparedStatement preparedStatement = baglanti.prepareStatement(sql);
        preparedStatement.setInt(1, ownerId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Apartment(
                    resultSet.getInt("apartment_id"),
                    resultSet.getString("block_name"),
                    resultSet.getString("type"),
                    resultSet.getString("status"),
                    resultSet.getBoolean("furnished"),
                    resultSet.getInt("owner_id"),
                    resultSet.getInt("tenant_id")
            );
        }
        return null;
    }
    // Ev sahibinin apartmanını satma
    public boolean sahipApartmaniniSat(int ownerId) throws SQLException {
        String sql = "UPDATE apartments SET status = 'Sold' WHERE owner_id = ?";
        PreparedStatement preparedStatement = baglanti.prepareStatement(sql);
        preparedStatement.setInt(1, ownerId);

        int etkilenmisSatirlar = preparedStatement.executeUpdate();
        return etkilenmisSatirlar > 0;
    }
    // Daireleri tablo verisi formatında döndüren metot
    public Object[][] getApartmentsAsTableData() {
        List<Object[]> daireListesi = new ArrayList<>();
        String sql = "SELECT apartment_id, block_name, type, status FROM apartments";

        try (PreparedStatement preparedStatement = baglanti.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                daireListesi.add(new Object[]{
                        resultSet.getInt("apartment_id"),
                        resultSet.getString("block_name"),
                        resultSet.getString("type"),
                        resultSet.getString("status")
                });
            }
        } catch (SQLException e) {
            System.out.println("Daireler listelenirken hata oluştu: " + e.getMessage());
        }


        return daireListesi.toArray(new Object[0][]);
    }

}

