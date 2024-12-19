public class TenantFactory extends UserFactory {
    @Override
    public User createUser(String username) {
        return new Tenant(0, username, "tenant");
    }
}
