package hr.tis.academy.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class StoreForm {

    @NotBlank
    private String storeName;
    @Email
    private String email;
    private String telephoneNumber;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    private String streetName;
    private int houseNumber;

    public StoreForm() {
    }

    public String getStoreName() { return storeName; }

    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getTelephoneNumber() { return telephoneNumber; }

    public void setTelephoneNumber(String telephoneNumber) { this.telephoneNumber = telephoneNumber; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getStreetName() { return streetName; }

    public void setStreetName(String streetName) { this.streetName = streetName; }

    public int getHouseNumber() { return houseNumber; }

    public void setHouseNumber(int houseNumber) { this.houseNumber = houseNumber; }
}
