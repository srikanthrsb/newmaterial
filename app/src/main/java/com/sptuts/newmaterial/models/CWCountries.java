package com.sptuts.newmaterial.models;

/**
 * Created by SHRI on 6/25/2015.
 */
public class CWCountries {
    String countryName;
    String capitalCity;

    public CWCountries(String capitalCity, String countryName) {
        this.capitalCity = capitalCity;
        this.countryName = countryName;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
