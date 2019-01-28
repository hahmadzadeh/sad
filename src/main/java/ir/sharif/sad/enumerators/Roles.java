package ir.sharif.sad.enumerators;

public enum Roles {
    ADMIN("ROLE_ADMIN"),
    FOUNDATION("ROLE_FOUNDATION"),
    VOLUNTEER("ROLE_VOLUNTEER");

    private final String value;

    Roles(String value){
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
