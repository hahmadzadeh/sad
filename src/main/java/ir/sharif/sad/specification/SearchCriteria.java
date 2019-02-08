package ir.sharif.sad.specification;

import lombok.Data;

@Data
public class SearchCriteria {
    private String key;
    private String operation;
    private String value;

    public SearchCriteria(String key, String operation, String value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
