package gereh.pira.Items;

import android.graphics.Bitmap;

/**
 * Created by Soroush on 12/08/2015.
 */
public class FavItem {

    private String Name;
    private String Address;
    private String FullAddress;
    private Bitmap Pic;
    private int Rate;
    private String ShortName;
    private String Phone;
    private int Id;

    public FavItem() {
    }

    public FavItem(String name, String address, Bitmap pic, int rate, String shortname, String fulladdress, String phone, int id) {
        Name = name;
        Address = address;
        Pic = pic;
        Rate = rate;
        ShortName = shortname;
        FullAddress = fulladdress;
        Phone = phone;
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Bitmap getPic() {
        return Pic;
    }

    public void setPic(Bitmap pic) {
        Pic = pic;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int rate) {
        Rate = rate;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }

    public String getFullAddress() {
        return FullAddress;
    }

    public void setFullAddress(String fullAddress) {
        FullAddress = fullAddress;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


}
