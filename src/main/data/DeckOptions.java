package main.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static main.data.Option.*;

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

    public int[] getLearningSteps() {
        return learningSteps;
    }

    /**
     * Default settings for DeckOptions
     */
    public DeckOptions() {
        options = new ArrayList<>();
        newCardsPerDay = 10;
        options.add(new Option("New Cards/Day", newCardsPerDay, "no description", INTEGER, TEXT));
        maxCardsPerDay = 100;
        options.add(new Option("Max Card/Day", maxCardsPerDay, "no description", INTEGER, TEXT));
        learningSteps = new int[]{1, 5, 60}; // 3 steps (4 reviews)
        options.add(new Option("Learning Steps", learningSteps, "no description", LIST, TEXT));
        reviewOrder = NEW_CARDS_FIRST;
        options.add(new Option(REVIEW_ORDER, reviewOrder, "no description", INTEGER, DROPDOWN));
        passMultiplier = 2.5;
        options.add(new Option("Pass Multiplier", passMultiplier, "no description", DOUBLE, TEXT));
        failMultiplier = 0.2;
        options.add(new Option("Fail Multiplier", failMultiplier, "no description", DOUBLE, TEXT));
        maximumInterval = 10000;
        options.add(new Option("Maximum Interval (days)", maximumInterval, "no description", INTEGER, TEXT));
    }

    public ArrayList<Option> getOptions() {
        return options;
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
