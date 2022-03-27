package com.sanjeevani.rent.Models;

public class House {
    private int id;
    private int user;
    private int owner;
    private String home_category;
    private String home_name;private String home_description;
    private String home_leaving_status;
    private String home_selling_price;
    private String home_address;
    private String home_tenant_name;
    private String home_document;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getHome_category() {
        return home_category;
    }

    public void setHome_category(String home_category) {
        this.home_category = home_category;
    }

    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public String getHome_description() {
        return home_description;
    }

    public void setHome_description(String home_description) {
        this.home_description = home_description;
    }

    public String getHome_leaving_status() {
        return home_leaving_status;
    }

    public void setHome_leaving_status(String home_leaving_status) {
        this.home_leaving_status = home_leaving_status;
    }

    public String getHome_selling_price() {
        return home_selling_price;
    }

    public void setHome_selling_price(String home_selling_price) {
        this.home_selling_price = home_selling_price;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public String getHome_tenant_name() {
        return home_tenant_name;
    }

    public void setHome_tenant_name(String home_tenant_name) {
        this.home_tenant_name = home_tenant_name;
    }

    public String getHome_document() {
        return home_document;
    }

    public void setHome_document(String home_document) {
        this.home_document = home_document;
    }

    public String getHome_rent_date() {
        return home_rent_date;
    }

    public void setHome_rent_date(String home_rent_date) {
        this.home_rent_date = home_rent_date;
    }

    private String home_rent_date;



}
