package ir.sharif.sad.enumerators;

public enum Roles {
    ADMIN("ADMIN"),
    FOUNDATION("FOUNDATION"),
    VOLUNTEER("VOLUNTEER");

    private final String value;

    Roles(String value){
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
