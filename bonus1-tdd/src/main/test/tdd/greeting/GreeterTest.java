package tdd.greeting;

import org.junit.Test;

import static org.junit.Assert.*;

public class GreeterTest {

    @Test // Requirement 1
    public void testStandardInput() {
        String name = "Bob";

        assertEquals("Hello, Bob.", Greeter.greet(name));
    }

    @Test // Requirement 2
    public void testNullInput() {
        String name = null;

        assertEquals("Hello, my friend.", Greeter.greet(name));
    }

    @Test // Requirement 3
    public void testUpperCaseInput() {
        String name = "JERRY";

        assertEquals("HELLO JERRY!", Greeter.greet(name));
    }

    @Test // Requirement 4
    public void testArrayOfTwoLowerCaseNames() {
        String[] names = new String[]{"Jill", "Jane"};

        assertEquals("Hello, Jill and Jane.", Greeter.greet(names));
    }

    @Test // Additional test
    public void testArrayOfTwoUpperCaseNames() {
        String[] names = new String[]{"JILL", "JANE"};

        assertEquals("HELLO JILL AND JANE!", Greeter.greet(names));
    }

    @Test // Requirement 5
    public void testArrayOfThreeLowerCaseNames() {
        String[] names = new String[]{"Amy", "Brian", "Charlotte"};

        assertEquals("Hello, Amy, Brian, and Charlotte.", Greeter.greet(names));
    }

    @Test // Additional test
    public void testArrayOfThreeUpperCaseNames() {
        String[] names = new String[]{"AMY", "BRIAN", "CHARLOTTE"};

        assertEquals("HELLO AMY, BRIAN, AND CHARLOTTE!", Greeter.greet(names));
    }

    @Test // Requirement 6
    public void testArrayOfTwoLowerOneUpperCaseNames() {
        String[] names = new String[]{"Amy", "BRIAN", "Charlotte"};

        assertEquals("Hello, Amy and Charlotte. AND HELLO BRIAN!", Greeter.greet(names));
    }

    @Test // Additional test
    public void testArrayOfTwoUpperOneLowerCaseNames() {
        String[] names = new String[]{"Amy", "BRIAN", "CHARLOTTE"};

        assertEquals("Hello, Amy. AND HELLO BRIAN AND CHARLOTTE!", Greeter.greet(names));
    }

    @Test // Additional test
    public void testArrayOfTwoLowerTwoUpperCaseNames() {
        String[] names = new String[]{"Amy", "BRIAN", "Charlotte", "DAN"};

        assertEquals("Hello, Amy and Charlotte. AND HELLO BRIAN AND DAN!", Greeter.greet(names));
    }

    @Test // Additional test
    public void testArrayOfSixNames() {
        String[] names = new String[]{"Amy", "BRIAN", "Charles", "DAN", "George", "HANS"};

        assertEquals("Hello, Amy, Charles, and George. AND HELLO BRIAN, DAN, AND HANS!", Greeter.greet(names));
    }

    @Test // Additional test
    public void testArrayWithNull() {
        String[] names = new String[]{"Amy", "BRIAN", "Charlotte", null};

        assertEquals("Hello, Amy and Charlotte. AND HELLO BRIAN!", Greeter.greet(names));
    }

    @Test // Requirement 7
    public void testArrayWithIntentionalCommas() {
        String[] names = new String[]{"Bob", "Charlie, Dianne"};

        assertEquals("Hello, Bob, Charlie, and Dianne.", Greeter.greet(names));
    }

    @Test // Additional test
    public void testArrayWithIntentionalCommasAndUpperCase() {
        String[] names = new String[]{"Bob", "CHARLIE, Dianne"};

        assertEquals("Hello, Bob and Dianne. AND HELLO CHARLIE!", Greeter.greet(names));
    }

    @Test // Requirement 8
    public void testArrayWithEscapingIntentionalCommas() {
        String[] names = new String[]{"Bob", "\"Charlie, Dianne\""};

        assertEquals("Hello, Bob and Charlie, Dianne.", Greeter.greet(names));
    }
}