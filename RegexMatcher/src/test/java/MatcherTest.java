
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import regexmatcher.Matcher;
import regexmatcher.MatcherFactory;

public class MatcherTest {

    @Test
    public void matchReturnsTrueWhenStringMatches() {
        Matcher matcher = new MatcherFactory().createMatcher("a", false, false);
        assertTrue(matcher.match("a"));
    }

    @Test
    public void matchReturnsFalseWhenStringDoesNotMatch() {
        Matcher matcher = new MatcherFactory().createMatcher("a", false, false);
        assertFalse(matcher.match("b"));
    }

    @Test
    public void matchReturnsFalseWhenStringHasInvalidCharacters() {
        Matcher matcher = new MatcherFactory().createMatcher("a", false, false);
        assertFalse(matcher.match("@"));
    }

    @Test
    public void matchReturnsFalseWhenRegularExpressionIsInvalid() {
        Matcher matcher = new MatcherFactory().createMatcher("[a-", false, false);
        assertFalse(matcher.match("a"));
    }

    @Test
    public void canMatchWhenRegularExpressionHasDot() {
        Matcher matcher = new MatcherFactory().createMatcher(".", false, false);
        assertFalse(matcher.match(""));
        assertFalse(matcher.match("."));
        assertFalse(matcher.match("("));
        assertFalse(matcher.match(")"));
        assertFalse(matcher.match("["));
        assertFalse(matcher.match("]"));
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("1"));
        assertTrue(matcher.match("A"));
        assertTrue(matcher.match("Z"));
        assertTrue(matcher.match("z"));
        assertTrue(matcher.match("9"));
        assertFalse(matcher.match("aa"));
    }

    @Test
    public void canMatchWhenRegularExpressionHasStar() {
        Matcher matcher = new MatcherFactory().createMatcher("a*", false, false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("aa"));
        assertTrue(matcher.match("aaa"));
    }

    @Test
    public void canMatchWhenRegularExpressionHasPlus() {
        Matcher matcher = new MatcherFactory().createMatcher("a+", false, false);
        assertFalse(matcher.match(""));
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("aa"));
        assertTrue(matcher.match("aaa"));
    }

    @Test
    public void canMatchWhenRegularExpressionHasBracketsAndStar() {
        Matcher matcher = new MatcherFactory().createMatcher("(abc)*", false, false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("abc"));
        assertTrue(matcher.match("abcabc"));
        assertTrue(matcher.match("abcabcabc"));
    }

    @Test
    public void canMatchWhenRegularExpressionHasBracketsAndPlus() {
        Matcher matcher = new MatcherFactory().createMatcher("(abc)+", false, false);
        assertFalse(matcher.match(""));
        assertTrue(matcher.match("abc"));
        assertTrue(matcher.match("abcabc"));
        assertTrue(matcher.match("abcabcabc"));
    }

    @Test
    public void canMatchWhenRegularExpressionHasSquareBrackets() {
        Matcher matcher = new MatcherFactory().createMatcher("[1a-dA-C]", false, false);
        assertTrue(matcher.match("1"));
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("b"));
        assertTrue(matcher.match("c"));
        assertTrue(matcher.match("d"));
        assertTrue(matcher.match("A"));
        assertTrue(matcher.match("B"));
        assertTrue(matcher.match("C"));
    }

    @Test
    public void canMatchWhenRegularExpressionHasOr() {
        Matcher matcher = new MatcherFactory().createMatcher("abc|123", false, false);
        assertFalse(matcher.match("abc123"));
        assertTrue(matcher.match("abc"));
        assertTrue(matcher.match("123"));
    }

    @Test
    public void canMatchWhenRegularExpressionHasBracketsAndStarAndOr() {
        Matcher matcher = new MatcherFactory().createMatcher("(abc|123)*", false, false);
        assertTrue(matcher.match("abc123"));
        assertTrue(matcher.match("123abc"));
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("123123abcabc"));
    }
}
