package main.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DeckOptions {
    public static final int NEW_CARDS_FIRST = 0; // Newest cards first
    public static final int ORDER_BY_RANDOM = 1; // Random order
    public static final int NEW_CARDS_LAST = 2; // Oldest cards first
    private int reviewOrder;
    private int newCardsPerDay;
    private double passMultiplier;
    private double failMultiplier;
    private int maxCardsPerDay;
    private int maximumInterval;

    /**
     * Size of list is how many times you have to get a card correct before it is considered learned.
     * Each number in the list is the number of minutes you have to wait before you can review the card again.
     * For example, if the list is {1, 5, 60}, then you have to get the card correct 3 times before it is considered learned.
     * After the first time, you have to wait 1 minute before you can review the card again, etc.
     * If no other cards are available for review, then the card will be shown without waiting.
     */
    private int[] learningSteps;

    private HashMap<String, Option> options; // Key is name of option

    public int[] getLearningSteps() {
        return learningSteps;
    }

    /**
     * Default settings for DeckOptions
     */
    public DeckOptions() {
        options = new HashMap<>();
        newCardsPerDay = 10;
        options.put("newCardsPerDay", new Option("newCardsPerDay", newCardsPerDay, "no description", Option.INTEGER));
        passMultiplier = 2.5;
        options.put("passMultiplier", new Option("passMultiplier", passMultiplier, "no description", Option.DOUBLE));
        failMultiplier = 0.2;
        options.put("failMultiplier", new Option("failMultiplier", failMultiplier, "no description", Option.DOUBLE));
        maxCardsPerDay = 100;
        options.put("maxCardsPerDay", new Option("maxCardsPerDay", maxCardsPerDay, "no description", Option.INTEGER));
        maximumInterval = 1000;
        options.put("maximumInterval", new Option("maximumInterval", maximumInterval, "no description", Option.INTEGER));
        learningSteps = new int[]{1, 5, 60}; // 3 steps (4 reviews)
        options.put("learningSteps", new Option("learningSteps", learningSteps, "no description", Option.LIST));
        reviewOrder = NEW_CARDS_FIRST;
        options.put("reviewOrder", new Option("reviewOrder", reviewOrder, "no description", Option.INTEGER));
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
