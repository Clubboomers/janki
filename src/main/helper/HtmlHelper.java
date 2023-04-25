package main.helper;

public class HtmlHelper {

    /**
     * Takes in a <body> tag and a <style> tag and returns the complete HTML.
     * @param body Whatever the user wants to put in the <body> tag. Front or back.
     * @param css User defined CSS.
     * @return The complete HTML.
     */
    public static String getHtml(String body, String css ) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("<title>Card</title>");
        sb.append("<style>");
        sb.append(css);
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append(body);
        sb.append("</body>");
        sb.append("</html>");
        String html = sb.toString();
        return html;
    }
}
