package com.lg;

import javax.persistence.*;

@Entity
@Table(name="inflation", indexes = {@Index(name = "INFLATION_YEAR_INDEX", columnList = "year")})
public class Inflation {
    @Id
    @Column(nullable = false, unique = true)
    private int year;

    @Column(nullable = false)
    private float rate;

    public Inflation(float rate, int year) {
        this.rate = rate;
        this.year = year;
    }

    public long getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
