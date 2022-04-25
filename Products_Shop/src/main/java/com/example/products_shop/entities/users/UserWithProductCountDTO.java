package com.example.products_shop.entities.users;

import java.util.Set;

public class UserWithProductCountDTO {
    private int usersCounter;
    private Set<UserWithProductsSoldDTO> users;

    public UserWithProductCountDTO(){

    }
    public UserWithProductCountDTO(int usersCounter, Set<UserWithProductsSoldDTO> users) {
        this.usersCounter = usersCounter;
        this.users = users;
    }

    public int getUsersCounter() {
        return usersCounter;
    }

    public Set<UserWithProductsSoldDTO> getUsers() {
        return users;
    }

    public void setUsersCounter(int usersCounter) {
        this.usersCounter = usersCounter;
    }

    public void setUsers(Set<UserWithProductsSoldDTO> users) {
        this.users = users;
    }
}
