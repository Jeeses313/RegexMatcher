package util;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import regexmatcher.util.DFAfactory;
import regexmatcher.util.Matcher;
import regexmatcher.util.MatcherFactory;
import regexmatcher.util.NFAfactory;

public class MatcherTest {

    @Test
    public void matchNFAReturnsTrueWhenStringMatches() {
        Matcher matcher = new MatcherFactory().createMatcher("a", false, false, false);
        assertTrue(matcher.match("a"));
    }

    @Test
    public void matchNFAReturnsFalseWhenStringDoesNotMatch() {
        Matcher matcher = new MatcherFactory().createMatcher("a", false, false, false);
        assertFalse(matcher.match("b"));
    }

    @Test
    public void matchNFAReturnsFalseWhenStringHasInvalidCharacters() {
        Matcher matcher = new MatcherFactory().createMatcher("a", false, false, false);
        assertFalse(matcher.match("@"));
    }

    @Test
    public void matchNFAReturnsFalseWhenRegularExpressionIsInvalid() {
        Matcher matcher = new MatcherFactory().createMatcher("[a-", false, false, false);
        assertFalse(matcher.match("a"));
    }

    @Test
    public void canMatchNFAWhenRegularExpressionHasDot() {
        Matcher matcher = new MatcherFactory().createMatcher(".", false, false, false);
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
    public void canMatchNFAWhenRegularExpressionHasStar() {
        Matcher matcher = new MatcherFactory().createMatcher("a*", false, false, false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("aa"));
        assertTrue(matcher.match("aaa"));
    }

    @Test
    public void canMatchNFAWhenRegularExpressionHasPlus() {
        Matcher matcher = new MatcherFactory().createMatcher("a+", false, false, false);
        assertFalse(matcher.match(""));
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("aa"));
        assertTrue(matcher.match("aaa"));
    }

    @Test
    public void canMatchNFAWhenRegularExpressionHasQuestionmark() {
        Matcher matcher = new MatcherFactory().createMatcher("a?", false, false, false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("a"));
        assertFalse(matcher.match("aa"));
    }

    @Test
    public void canMatchNFAWhenRegularExpressionHasBracketsAndStar() {
        Matcher matcher = new MatcherFactory().createMatcher("(abc)*", false, false, false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("abc"));
        assertTrue(matcher.match("abcabc"));
        assertTrue(matcher.match("abcabcabc"));
    }

    @Test
    public void canMatchNFAWhenRegularExpressionHasBracketsAndPlus() {
        Matcher matcher = new MatcherFactory().createMatcher("(abc)+", false, false, false);
        assertFalse(matcher.match(""));
        assertTrue(matcher.match("abc"));
        assertTrue(matcher.match("abcabc"));
        assertTrue(matcher.match("abcabcabc"));
    }

    @Test
    public void canMatchNFAWhenRegularExpressionHasBracketsAndQuestionmark() {
        Matcher matcher = new MatcherFactory().createMatcher("(abc)?", false, false, false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("abc"));
        assertFalse(matcher.match("abcabc"));
    }

    @Test
    public void canMatchNFAWhenRegularExpressionHasSquareBrackets() {
        Matcher matcher = new MatcherFactory().createMatcher("[1a-dA-C]", false, false, false);
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
    public void canMatchNFAWhenRegularExpressionHasOr() {
        Matcher matcher = new MatcherFactory().createMatcher("abc|123", false, false, false);
        assertFalse(matcher.match("abc123"));
        assertTrue(matcher.match("abc"));
        assertTrue(matcher.match("123"));
    }

    @Test
    public void canMatchNFAWhenRegularExpressionHasBracketsAndStarAndOr() {
        Matcher matcher = new MatcherFactory().createMatcher("(abc|123)*", false, false, false);
        assertTrue(matcher.match("abc123"));
        assertTrue(matcher.match("123abc"));
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("123123abcabc"));
    }

    @Test
    public void matchDFAReturnsTrueWhenStringMatches() {
        Matcher matcher = new MatcherFactory().createMatcher("a", true, false, false);
        assertTrue(matcher.match("a"));
    }

    @Test
    public void matchDFAReturnsFalseWhenStringDoesNotMatch() {
        Matcher matcher = new MatcherFactory().createMatcher("a", true, false, false);
        assertFalse(matcher.match("b"));
    }

    @Test
    public void matchDFAReturnsFalseWhenStringHasInvalidCharacters() {
        Matcher matcher = new MatcherFactory().createMatcher("a", true, false, false);
        assertFalse(matcher.match("@"));
    }

    @Test
    public void matchDFAReturnsFalseWhenRegularExpressionIsInvalid() {
        Matcher matcher = new MatcherFactory().createMatcher("[a-", true, false, false);
        assertFalse(matcher.match("a"));
    }

    @Test
    public void canMatchDFAWhenRegularExpressionHasDot() {
        Matcher matcher = new MatcherFactory().createMatcher(".", true, false, false);
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
    public void canMatchDFAWhenRegularExpressionHasStar() {
        Matcher matcher = new MatcherFactory().createMatcher("a*", true, false, false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("aa"));
        assertTrue(matcher.match("aaa"));
    }

    @Test
    public void canMatchDFAWhenRegularExpressionHasPlus() {
        Matcher matcher = new MatcherFactory().createMatcher("a+", true, false, false);
        assertFalse(matcher.match(""));
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("aa"));
        assertTrue(matcher.match("aaa"));
    }

    @Test
    public void canMatchDFAWhenRegularExpressionHasQuestionmark() {
        Matcher matcher = new MatcherFactory().createMatcher("a?", true, false, false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("a"));
        assertFalse(matcher.match("aa"));
    }

    @Test
    public void canMatchDFAWhenRegularExpressionHasBracketsAndStar() {
        Matcher matcher = new MatcherFactory().createMatcher("(abc)*", true, false, false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("abc"));
        assertTrue(matcher.match("abcabc"));
        assertTrue(matcher.match("abcabcabc"));
    }

    @Test
    public void canMatchDFAWhenRegularExpressionHasBracketsAndPlus() {
        Matcher matcher = new MatcherFactory().createMatcher("(abc)+", true, false, false);
        assertFalse(matcher.match(""));
        assertTrue(matcher.match("abc"));
        assertTrue(matcher.match("abcabc"));
        assertTrue(matcher.match("abcabcabc"));
    }

    @Test
    public void canMatchDFAWhenRegularExpressionHasBracketsAndQuestionmark() {
        Matcher matcher = new MatcherFactory().createMatcher("(abc)?", true, false, false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("abc"));
        assertFalse(matcher.match("abcabc"));
    }

    @Test
    public void canMatchDFAWhenRegularExpressionHasSquareBrackets() {
        Matcher matcher = new MatcherFactory().createMatcher("[1a-dA-C]", true, false, false);
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
    public void canMatchDFAWhenRegularExpressionHasOr() {
        Matcher matcher = new MatcherFactory().createMatcher("abc|123", true, false, false);
        assertFalse(matcher.match("abc123"));
        assertTrue(matcher.match("abc"));
        assertTrue(matcher.match("123"));
    }

    @Test
    public void canMatchDFAWhenRegularExpressionHasBracketsAndStarAndOr() {
        Matcher matcher = new MatcherFactory().createMatcher("(abc|123)*", true, false, false);
        assertTrue(matcher.match("abc123"));
        assertTrue(matcher.match("123abc"));
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("123123abcabc"));
    }

    @Test
    public void DFAMatcherCanReturnFalseUsingNFAWhenNFAMatcherReturnsTrue() {
        Matcher dfaMatcher = new Matcher(new NFAfactory().generateNFA("a|ab"), true);
        Matcher nfaMatcher = new Matcher(new NFAfactory().generateNFA("a|ab"), false);
        assertFalse(dfaMatcher.match("ab"));
        assertTrue(nfaMatcher.match("ab"));
    }

    @Test
    public void NFAMatcherCanMatchUsingDFA() {
        Matcher matcher = new Matcher(new DFAfactory().generateDFA(new NFAfactory().generateNFA("a|ab")), false);
        assertTrue(matcher.match("ab"));
        assertTrue(matcher.match("a"));
    }

    @Test
    public void NFAMatcherCanMatchWithEmptyLoops() {
        Matcher matcher = new Matcher(new NFAfactory().generateNFA("(a*b*c*)+"), false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("ab"));
        assertTrue(matcher.match("abc"));
        assertTrue(matcher.match("aabbcc"));
        assertFalse(matcher.match("d"));
        matcher = new Matcher(new NFAfactory().generateNFA("(a*b*c*)*"), false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("ab"));
        assertTrue(matcher.match("abc"));
        assertTrue(matcher.match("aabbcc"));
        assertFalse(matcher.match("d"));
        matcher = new Matcher(new NFAfactory().generateNFA("(a*|b*)+"), false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("ab"));
        assertTrue(matcher.match("aabb"));
        assertTrue(matcher.match("ba"));
        assertFalse(matcher.match("d"));
        matcher = new Matcher(new NFAfactory().generateNFA("(abc|a*b*c*)+"), false);
        assertTrue(matcher.match(""));
        assertTrue(matcher.match("a"));
        assertTrue(matcher.match("ab"));
        assertTrue(matcher.match("abc"));
        assertTrue(matcher.match("aabbcc"));
        assertTrue(matcher.match("ccbbaa"));
        assertFalse(matcher.match("d"));
    }
}
