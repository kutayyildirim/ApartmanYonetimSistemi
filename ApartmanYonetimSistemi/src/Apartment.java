public class Apartment {

    private int apartmentId;
    private String blockName;
    private String type;
    private String status;
    private boolean furnished;
    private int ownerId;
    private int tenantId;

    private ApartmentState currentState;
    private NotificationManager notificationManager;

    public Apartment(int apartmentId, String blockName, String type, String status, boolean furnished, int ownerId, int tenantId) {
        this.apartmentId = apartmentId;
        this.blockName = blockName;
        this.type = type;
        this.status = status;
        this.furnished = furnished;
        this.ownerId = ownerId;
        this.tenantId = tenantId;

        this.notificationManager = new NotificationManager(); // Bildirim yöneticisi
        setCurrentState(new EmptyState());
    }

    public void setCurrentState(ApartmentState newState) {
        this.currentState = newState;
        notificationManager.bildirimGonder("Apartman durumu değişti: " + this.status);
    }

    public void durumGuncelle() {
        currentState.durumGuncelle(this);
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFurnished() {
        return furnished;
    }

    public void setFurnished(boolean furnished) {
        this.furnished = furnished;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "apartmentId=" + apartmentId +
                ", blockName='" + blockName + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", furnished=" + furnished +
                ", ownerId=" + ownerId +
                ", tenantId=" + tenantId +
                '}';
    }
}
