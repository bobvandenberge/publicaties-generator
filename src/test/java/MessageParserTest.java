import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class MessageParserTest {

    @InjectMocks
    private MessageParser messageParser;

    @Mock
    private HtmlUtil htmlUtil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Parameters({
            "<http://blog.crisp.se/2016/01/25/henrikkniberg/making-sense-of-mvp> Interessant artikel over MVP's, http://blog.crisp.se/2016/01/25/henrikkniberg/making-sense-of-mvp - Interessant artikel over MVP's",
            "Interessante nieuwe feature in Docker 1.12: <https://www.voxxed.com/blog/2016/11/health-check-of-docker-containers/>, https://www.voxxed.com/blog/2016/11/health-check-of-docker-containers/ - Interessante nieuwe feature in Docker 1.12"
    })
    public void canParseMessage(String input, String expectOutput) {
        String ouput = messageParser.parse(input);

        assertThat(ouput, is(expectOutput));
    }

    @Test
    public void usesHTMLTitleIfNoDescriptionIsPresent() {
        String input = "<https://dzone.com/articles/three-reasons-why-organizations-adopting-agile-say>";
        when(htmlUtil.getTitle("https://dzone.com/articles/three-reasons-why-organizations-adopting-agile-say")).thenReturn("HTML TITLE");

        String ouput = messageParser.parse(input);
        assertThat(ouput, is("https://dzone.com/articles/three-reasons-why-organizations-adopting-agile-say - HTML TITLE"));
    }
}
