import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Will generate a list off publicaties for the last 7 days
 */
public class PublicatiesGenerator {

    private MessageParser messageParser = new MessageParser();
    private SlackAPI slackAPI = new SlackAPI();

    public String generatePublicaties() {
        List<SlackMessagePosted> publicaties = slackAPI.getLastWeeksLinks();

        return generatePost(publicaties);
    }

    private String generatePost(final List<SlackMessagePosted> publicaties) {
        String content = "";

        content += "Hieronder is de lijst met #publicaties van deze week (" + WeekUtil.getWeekNumber() + ") te vinden. Veel leesplezier!";

        content += System.lineSeparator();

        content += IntStream.range(0, publicaties.size())
                .mapToObj(index -> (index + 1) + ". " + messageParser.parse(publicaties.get(index).getMessageContent()))
                .reduce("", (identity, accu) -> identity + System.lineSeparator() + accu);

        content += System.lineSeparator() + System.lineSeparator();

        String publicators = publicaties.stream()
                .map(message -> message.getSender().getUserName())
                .distinct()
                .reduce((identity, accu) -> identity + ", @" + accu).get();

        content += "De lijst is deze week mogelijk gemaakt door @" + publicators + ". Bedankt!";
        content += System.lineSeparator() + System.lineSeparator();
        content += "Kom je een interessante publicatie (foto/video/textueel) tegen? Deel deze dan in het #publicaties kanaal zodat iedereen er van kan leren :slightly_smiling_face:";
        content += System.lineSeparator() + System.lineSeparator();
        content += "Nog een fijne zondag!";

        return content;
    }

}
