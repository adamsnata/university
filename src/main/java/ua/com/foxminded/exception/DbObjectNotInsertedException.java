package ua.com.foxminded.exception;

public class DbObjectNotInsertedException extends RuntimeException{
    public DbObjectNotInsertedException(Object object) {
        super("Object was not inserted:" + object.toString());
    }
}

