package com.splitwise.advanced.entities.timezone;

import jakarta.persistence.*;

@Entity
@Table(name = "timezone")
public class TimeZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    public TimeZone() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TimeZone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
