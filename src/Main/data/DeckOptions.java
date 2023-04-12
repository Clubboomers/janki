package Main.data;

public class DeckOptions {
    public static final int ORDER_BY_CREATED = 0; // Newest cards first
    public static final int ORDER_BY_RANDOM = 1; // Random order
    public static final int ORDER_BY_DUE = 2; // Oldest cards first
    private int order;
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

    public int[] getLearningSteps() {
        return learningSteps;
    }

    /**
     * Default settings for DeckOptions
     */
    public DeckOptions() {
        newCardsPerDay = 10;
        passMultiplier = 2.5;
        failMultiplier = 0.2;
        maxCardsPerDay = 100;
        maximumInterval = 1000;
        learningSteps = new int[]{1, 5, 60}; // 3 steps (4 reviews)
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
