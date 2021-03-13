package com.softuni.model.service;

import com.softuni.model.entity.BatEntity;
import com.softuni.model.entity.GloveEntity;
import com.softuni.model.entity.RoleEntity;
import com.softuni.model.entity.TeamEntity;

import java.util.Set;

public class UserServiceModel {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private int age;
    private String description;
    private Set<RoleEntity> roles;
    private BatEntity bat;
    private GloveEntity glove;
    private TeamEntity team;

    public UserServiceModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public BatEntity getBat() {
        return bat;
    }

    public void setBat(BatEntity bat) {
        this.bat = bat;
    }

    public GloveEntity getGlove() {
        return glove;
    }

    public void setGlove(GloveEntity glove) {
        this.glove = glove;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }
}
