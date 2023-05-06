package ru.javaops.topjava.error;

public class DontAllowVoteException extends AppException {
    public DontAllowVoteException(String msg) {
        super(msg);
    }
}