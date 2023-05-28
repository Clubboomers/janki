package main.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static main.data.Option.*;
import static main.data.OptionDescriptions.*;

public class DeckOptions {
    public static final int NEW_CARDS_FIRST = 0; // Newest cards first
    public static final int ORDER_BY_RANDOM = 1; // Random order
    public static final int NEW_CARDS_LAST = 2; // Oldest cards first
    private int reviewOrder;
    private int newCardsPerDay;
    private double passMultiplier;
    private double failMultiplier;
    private int maxCardsPerDay;
    private int maximumInterval; // Maximum interval in days

    /**
     * Size of list is how many times you have to get a card correct before it is considered learned.
     * Each number in the list is the number of minutes you have to wait before you can review the card again.
     * For example, if the list is {1, 5, 60}, then you have to get the card correct 3 times before it is considered learned.
     * After the first time, you have to wait 1 minute before you can review the card again, etc.
     * If no other cards are available for review, then the card will be shown without waiting.
     */
    private int[] learningSteps;

    private ArrayList<Option> options; // Key is name of option (e.g. "newCardsPerDay") TODO: change to ArrayList

    /**
     * Default settings for DeckOptions
     */
    public DeckOptions() {
        newCardsPerDay = 10;
        maxCardsPerDay = 100;
        learningSteps = new int[]{1, 5, 60}; // 3 steps (4 reviews)
        reviewOrder = NEW_CARDS_FIRST;
        passMultiplier = 2.5;
        failMultiplier = 0.2;
        maximumInterval = 10000;
        options = new ArrayList<>();
        initArrayList();
    }

    @JsonCreator
    public DeckOptions(
            @JsonProperty("newCardsPerDay") int newCardsPerDay,
            @JsonProperty("maxCardsPerDay") int maxCardsPerDay,
            @JsonProperty("learningSteps") int[] learningSteps,
            @JsonProperty("reviewOrder") int reviewOrder,
            @JsonProperty("passMultiplier") double passMultiplier,
            @JsonProperty("failMultiplier") double failMultiplier,
            @JsonProperty("maximumInterval") int maximumInterval)
    { // TODO: make all option names static strings
        this.newCardsPerDay = newCardsPerDay;
        this.maxCardsPerDay = maxCardsPerDay;
        this.learningSteps = learningSteps;
        this.reviewOrder = reviewOrder;
        this.passMultiplier = passMultiplier;
        this.failMultiplier = failMultiplier;
        this.maximumInterval = maximumInterval;
        options = new ArrayList<>();
        initArrayList();
    }

    @Override
    public String toString() {
        return "DeckOptions{" +
                "reviewOrder=" + reviewOrder +
                ", newCardsPerDay=" + newCardsPerDay +
                ", passMultiplier=" + passMultiplier +
                ", failMultiplier=" + failMultiplier +
                ", maxCardsPerDay=" + maxCardsPerDay +
                ", maximumInterval=" + maximumInterval +
                ", learningSteps=" + Arrays.toString(learningSteps) +
                ", options=" + options +
                '}';
    }

    private void initArrayList() {
        this.options.add(new Option(NEW_CARDS_PER_DAY, this.newCardsPerDay, NEW_CARDS_PER_DAY_DESCRIPTION, INTEGER, TEXT));
        this.options.add(new Option(MAX_CARDS_PER_DAY, this.maxCardsPerDay, MAX_CARDS_PER_DAY_DESCRIPTION, INTEGER, TEXT));
        this.options.add(new Option(LEARNING_STEPS, this.learningSteps, LEARNING_STEPS_DESCRIPTION, LIST, TEXT));
        this.options.add(new Option(REVIEW_ORDER, this.reviewOrder, REVIEW_ORDER_DESCRIPTION, INTEGER, DROPDOWN));
        this.options.add(new Option(PASS_MULTIPLIER, this.passMultiplier, PASS_MULTIPLIER_DESCRIPTION, DOUBLE, TEXT));
        this.options.add(new Option(FAIL_MULTIPLIER, this.failMultiplier, FAIL_MULTIPLIER_DESCRIPTION, DOUBLE, TEXT));
        this.options.add(new Option(MAXIMUM_INTERVAL, this.maximumInterval, MAXIMUM_INTERVAL_DESCRIPTION, INTEGER, TEXT));
    }

    public void add(Option option) {
        options.add(option);
    }

    public int[] getLearningSteps() {
        return learningSteps;
    }

    @JsonIgnore
    public ArrayList<Option> getOptions() {
        return options;
    }

    @JsonIgnore
    public void setOptions(ArrayList<Option> options) {
        this.options = options;
        for (Option option : options) {
            switch (option.getName()) {
                case "New Cards/Day":
                    this.newCardsPerDay = (int) option.getValue();
                    break;
                case "Max Card/Day":
                    this.maxCardsPerDay = (int) option.getValue();
                    break;
                case "Learning Steps":
                    this.learningSteps = (int[]) option.getValue();
                    break;
                case REVIEW_ORDER:
                    this.reviewOrder = (int) option.getValue();
                    break;
                case "Pass Multiplier":
                    this.passMultiplier = (double) option.getValue();
                    break;
                case "Fail Multiplier":
                    this.failMultiplier = (double) option.getValue();
                    break;
                case "Maximum Interval (days)":
                    this.maximumInterval = (int) option.getValue();
                    break;
            }
        }
    }

    public int getReviewOrder() {
        return reviewOrder;
    }

    public void setReviewOrder(int reviewOrder) {
        this.reviewOrder = reviewOrder;
    }

    public int getNewCardsPerDay() {
        return newCardsPerDay;
    }

    public void setNewCardsPerDay(int newCardsPerDay) {
        this.newCardsPerDay = newCardsPerDay;
    }

    public double getPassMultiplier() {
        return passMultiplier;
    }

    public void setPassMultiplier(double passMultiplier) {
        this.passMultiplier = passMultiplier;
    }

    public double getFailMultiplier() {
        return failMultiplier;
    }

    public void setFailMultiplier(double failMultiplier) {
        this.failMultiplier = failMultiplier;
    }

    public int getMaxCardsPerDay() {
        return maxCardsPerDay;
    }

    public void setMaxCardsPerDay(int maxCardsPerDay) {
        this.maxCardsPerDay = maxCardsPerDay;
    }

    public int getMaximumInterval() {
        return maximumInterval;
    }

    public void setMaximumInterval(int maximumInterval) {
        this.maximumInterval = maximumInterval;
    }
}
