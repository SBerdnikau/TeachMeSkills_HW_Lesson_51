package com.tms.exception;

public class LoginUsedException extends Exception {
    String login;
    
    public LoginUsedException(String login) {
        super("Login already used: " + login);
    }

    @Override
    public String toString() {
        return "Login already used: " + login;
    }
}
