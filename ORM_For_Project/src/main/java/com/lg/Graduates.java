package com.lg;

import javax.persistence.*;

@Entity
@Table(name="graduates", indexes = {@Index(name = "GRADUATES_YEAR_INDEX", columnList = "year")})
public class Graduates {
    @Id
    @Column(nullable = false, unique = true)
    private int year;

    @Column(nullable = false)
    private int number;

    public Graduates(int number, int year) {
        this.number = number;
        this.year = year;
    }

    public long getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
