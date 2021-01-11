package com.coconino.coconinoapp.model.object;

import java.util.Collection;
import java.util.List;

public class Account {
    // Should I expose these?
    public final int id;
    public final String username;
    public final String password;
    public final String email;

    public Account(int id,
                   String username,
                   String password,
                   String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Account(List<Object> l) {
        this((int) l.get(0),
                (String) l.get(1),
                (String) l.get(2),
                (String) l.get(3));
    }
}
