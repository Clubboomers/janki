package main.data;

public class OptionDescriptions {
    public static final String NEW_CARDS_PER_DAY_DESCRIPTION = "<html><table width='200'><tr><td>"+"The number of new cards to learn each day." + "</td></tr></table></html>";
    public static final String MAX_CARDS_PER_DAY_DESCRIPTION = "<html><table width='200'><tr><td>"+"The maximum number reviews you can do in one day." + "</td></tr></table></html>";
    public static final String LEARNING_STEPS_DESCRIPTION = "<html><table width='200'><tr><td>"+"The length of the list specifies the number of times a " +
            "card will have to be passed before it is considered learned. The value of each element specifies how long " +
            "the interval will be before the card is shown again." + "</td></tr></table></html>";
    public static final String REVIEW_ORDER_DESCRIPTION = "<html><table width='200'><tr><td>"+"The order in which your cards will appear." + "</td></tr></table></html>";
    public static final String PASS_MULTIPLIER_DESCRIPTION = "<html><table width='200'><tr><td>"+"The multiplier for the interval of a card when you pass " +
            "it." + "</td></tr></table></html>";
    public static final String FAIL_MULTIPLIER_DESCRIPTION = "<html><table width='200'><tr><td>"+"The multiplier for the interval of a card when you fail " +
            "it." + "</td></tr></table></html>";
    public static final String MAXIMUM_INTERVAL_DESCRIPTION = "<html><table width='200'><tr><td>"+"The longest interval a card can have before it can not " +
            "grow any more." + "</td></tr></table></html>";
}