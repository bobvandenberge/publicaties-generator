import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class MessageParser {

    private HtmlUtil htmlUtil = new HtmlUtil();

    public String parse(String message) {
        // Remove new lines because we don't want them in the final result + the regex cant parse them
        message = message.replaceAll("\\r\\n|\\r|\\n", " ");

        Pattern patternFormat1 = compile("<(.*)> +(.*)");
        Matcher matcherFormat1 = patternFormat1.matcher(message);

        Pattern patternFormat2 = compile("(.*) +<(.*)>");
        Matcher matcherFormat2 = patternFormat2.matcher(message);

        Pattern patternFormat3 = compile("<(.*)>");
        Matcher matcherFormat3 = patternFormat3.matcher(message);

        String content = message;

        if(matcherFormat1.matches()) {
            content = matcherFormat1.group(1) + " - " + normalize(matcherFormat1.group(2));
        } else if(matcherFormat2.matches()) {
            content = matcherFormat2.group(2) + " - " + normalize(matcherFormat2.group(1));
        } else if (matcherFormat3.matches()) {
            content = matcherFormat3.group(1) + " - " + htmlUtil.getTitle(matcherFormat3.group(1));
        }

        return content;
    }

    private String normalize(final String messageContent) {
        return messageContent.replace(":", "");
    }
}
