package com.coconino.coconinoapp.model.object;

public class Customer {
    public Integer accountID;
    public String name;
    public String lastName;
    public String firstName;
    public String phone;
    public String addressLine1;
    public String addressLine2;
    public String city;
    public String state;
    public String postalCode;
    public String country;
    public Float creditLimit;

    public Customer(Integer accountID,
//                    String name,
                    String lastName,
                    String firstName,
                    String phone,
                    String addressLine1,
                    String addressLine2,
                    String city,
                    String state,
                    String postalCode,
                    String country,
                    Float creditLimit) {
        this.accountID = accountID;
        this.name = String.format("%s %s", firstName, lastName);
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.creditLimit = creditLimit;
    }
}
