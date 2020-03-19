
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import regexmatcher.Matcher;
import regexmatcher.MatcherFactory;

public class MatcherFactoryTest {

    @Test
    public void canCreateWorkingMatcherWithValidRegularExpression() {
        Matcher matcher = new MatcherFactory().createMatcher("a");
        assertTrue(matcher.getWorks());
    }

    @Test
    public void returnsNonWorkingMatcherWithInvalidRegularExpression() {
        Matcher matcher = new MatcherFactory().createMatcher("[a-");
        assertFalse(matcher.getWorks());
    }

    @Test
    public void returnsNonWorkingMatcherWithEmptyRegularExpression() {
        Matcher matcher = new MatcherFactory().createMatcher("");
        assertFalse(matcher.getWorks());
    }
}
