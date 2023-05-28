package main.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class Option<Value> {
    public static final int BOOLEAN = 0;
    public static final int INTEGER = 1;
    public static final int STRING = 2;
    public static final int DOUBLE = 3;
    public static final int LIST = 4;
    public static final int DROPDOWN = 5;
    public static final int TEXT = 6;
    public static final int CHECKBOX = 7;
    public static final int BUTTON = 8;
    public static final String NEW_CARDS_PER_DAY = "New cards/day";
    public static final String MAX_CARDS_PER_DAY = "Max cards/day";
    public static final String LEARNING_STEPS = "Learning steps";
    public static final String REVIEW_ORDER = "Review Order";
    public static final String PASS_MULTIPLIER = "Pass multiplier";
    public static final String FAIL_MULTIPLIER = "Fail multiplier";
    public static final String MAXIMUM_INTERVAL = "Maximum interval (days)";
    public static final String NEW_CARDS_FIRST = "New cards first";
    public static final String ORDER_BY_RANDOM = "Random";
    public static final String NEW_CARDS_LAST = "New cards last";
    private String name;
    private Value value;
    private String description;
    private int dataType;
    private int optionType;
    private String[] dropdownOptions; // [DROPDOWN] Description of each option in dropdown menu

    public Option() {
        // Empty for JSON deserialization
    }

    @JsonCreator
    public Option(@JsonProperty("name") String name,
                  @JsonProperty("value") Value value,
                  @JsonProperty("description") String description,
                  @JsonProperty("dataType") int dataType,
                  @JsonProperty("optionType") int optionType)
    {
        this.name = name;
        this.value = value;
        this.description = description;
        this.dataType = dataType;
        this.optionType = optionType;
        if (optionType == DROPDOWN) {
            if (name.equals(REVIEW_ORDER)) {
                dropdownOptions = new String[]{NEW_CARDS_FIRST, ORDER_BY_RANDOM, NEW_CARDS_LAST};
            }
        }
    }

    @Override
    public String toString() {
        return "Option{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", dataType=" + dataType +
                ", optionType=" + optionType +
                ", dropdownOptions=" + Arrays.toString(dropdownOptions) +
                '}';
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

    public int getOptionType() {
        return optionType;
    }

    public int getDataType() {
        return dataType;
    }

    public String[] getDropdownOptions() {
        return dropdownOptions;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
