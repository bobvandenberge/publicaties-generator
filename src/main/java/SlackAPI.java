import com.ullink.slack.simpleslackapi.ChannelHistoryModule;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.ChannelHistoryModuleFactory;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class SlackAPI {
    private final String token = "YOUR_SLACK_API_TOKEN";

    public List<SlackMessagePosted> getLastWeeksLinks() {
        List<SlackMessagePosted> messages = getLastWeeksMessages();

        return filterMessagesWithLinks(messages);
    }

    private List<SlackMessagePosted> filterMessagesWithLinks(final List<SlackMessagePosted> messages) {
        return messages
                .stream()
                .filter(x -> x.getMessageContent().contains("http") || x.getMessageContent().contains("www"))
                .collect(toList());
    }

    private List<SlackMessagePosted> getLastWeeksMessages() {
        SlackSession slackSession = getSlackSession();

        SlackChannel slackChannel = slackSession.findChannelByName("publicaties");

        ChannelHistoryModule channelHistoryModule = ChannelHistoryModuleFactory.createChannelHistoryModule(slackSession);

        List<SlackMessagePosted> lastWeeksMesages = IntStream.of(7, 6, 5, 4, 3, 2, 1)
                .mapToObj(daysAgo -> LocalDate.now().minus(daysAgo, ChronoUnit.DAYS))
                .flatMap(date -> getMessagesOfDay(slackChannel, channelHistoryModule, date))
                .collect(toList());

        try {
            slackSession.disconnect();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return lastWeeksMesages;
    }

    private Stream<SlackMessagePosted> getMessagesOfDay(final SlackChannel slackChannel, final ChannelHistoryModule channelHistoryModule, final LocalDate date) {
        return channelHistoryModule.fetchHistoryOfChannel(slackChannel.getId(), date).stream()
                .sorted(Comparator.comparing(SlackMessagePosted::getTimestamp));
    }

    private SlackSession getSlackSession() {
        try {
            SlackSession slackSession = SlackSessionFactory.createWebSocketSlackSession(token);
            slackSession.connect();
            return slackSession;
        } catch (Exception ex) {
            // Because screw checked exceptions
            throw new RuntimeException(ex);
        }
    }
}
