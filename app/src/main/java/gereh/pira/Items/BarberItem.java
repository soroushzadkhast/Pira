package gereh.pira.Items;

public class BarberItem {

    private int Id;
    private String Name;
    private String About;
    private String ShortName;
    private String WorkHour;

    public BarberItem() {
    }

    public BarberItem(String name, String about, String shortName, String workHour, int id) {
        Name = name;
        About = about;
        ShortName = shortName;
        WorkHour = workHour;
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }

    public String getWorkHour() {
        return WorkHour;
    }

    public void setWorkHour(String workHour) {
        WorkHour = workHour;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}