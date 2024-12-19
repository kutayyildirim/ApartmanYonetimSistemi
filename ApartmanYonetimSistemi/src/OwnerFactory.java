public class OwnerFactory extends UserFactory {
    @Override
    public User createUser(String username) {
        return new Owner(0, username, "owner"); // ID varsayılan olarak 0 atanır
    }
}
