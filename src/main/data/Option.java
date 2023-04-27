package main.data;

public class Option<Value> {
    public static final int BOOLEAN = 0;
    public static final int INTEGER = 1;
    public static final int STRING = 2;
    public static final int DOUBLE = 3;
    public static final int LIST = 4;
    private String name;
    private Value value;
    private String description;
    private int type;

    public Option(String name, Value value, String description, int type) {
        this.name = name;
        this.value = value;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Value getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
