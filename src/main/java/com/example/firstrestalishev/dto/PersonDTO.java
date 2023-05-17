package com.example.firstrestalishev.dto;

import javax.validation.constraints.*;

public class PersonDTO {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30")
    private String name;

    /*@Pattern(regexp = "^[12][0-9]{3}$", message = "year of birth should be in this borders: 1000-2999")*/
    @Min(value = 1900, message = "by sb m t 1900")
    private int birthYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
