package gereh.pira.Items;


public class ReserveItem {

    private String barberShopName;
    private String barberName;
    private String Time;
    private String Status;
    private String shortName;

    public ReserveItem() {
    }

    public ReserveItem(String barberShopName, String barberName, String time, String status, String shortName) {
        this.barberShopName = barberShopName;
        this.barberName = barberName;
        Time = time;
        Status = status;
        this.shortName = shortName;
    }

    public String getBarberShopName() {
        return barberShopName;
    }

    public void setBarberShopName(String barberShopName) {
        this.barberShopName = barberShopName;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}