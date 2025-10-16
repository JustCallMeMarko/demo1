package com.example.demo1.Models;

public class Users {
    private final int id;
    private final String name;
    private final String role;

    public Users(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
}
