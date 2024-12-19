public class AdminFactory extends UserFactory {
    @Override
    public User createUser(String username) {
        return new Admin(0, username, "admin"); // ID varsayılan olarak 0 atanır
    }
}
