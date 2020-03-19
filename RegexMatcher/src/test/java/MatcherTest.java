
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import regexmatcher.Matcher;
import regexmatcher.MatcherFactory;

public class MatcherTest {

    @Test
    public void matchReturnsTrueWhenStringMatches() {
        Matcher matcher = new MatcherFactory().createMatcher("a");
        assertTrue(matcher.match("a"));
    }

    @Test
    public void matchReturnsFalseWhenStringDoesNotMatch() {
        Matcher matcher = new MatcherFactory().createMatcher("a");
        assertFalse(matcher.match("b"));
    }

    @Test
    public void matchReturnsFalseWhenStringHasInvalidCharacters() {
        Matcher matcher = new MatcherFactory().createMatcher("a");
        assertFalse(matcher.match("@"));
    }

    @Test
    public void matchReturnsFalseWhenRegularExpressionIsInvalid() {
        Matcher matcher = new MatcherFactory().createMatcher("[a-");
        assertFalse(matcher.match("a"));
    }

    @Test
    public void canMatchWhenRegularExpressionHasStar() {
        Matcher matcher = new MatcherFactory().createMatcher("a*");
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("aa"));
        assertTrue(matcher.match("aaa"));
    }
}
