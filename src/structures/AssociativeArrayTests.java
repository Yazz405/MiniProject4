package structures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

/**
 * Tests of the AssociativeArray class.
 *
 * @author CSC-207 2023Fa
 */
public class AssociativeArrayTests {

  // +---------------------+-----------------------------------------
  // | Tests by Sam Bigham |
  // +---------------------+

  /*
   * a simple test for seeing if the hasKey function works correctly
   */
  @Test
  public void samBighamTest1() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    arr.set("Z", "Zebra");
    assertEquals(arr.hasKey("Z"), true);
    assertEquals(arr.hasKey("z"), false);
  }

  /*
   * a simple test to see if the set function works correctly when set to the same
   * element multiple
   * times
   */
  @Test
  public void samBighamTest2() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    for (int i = 0; i < 10; i++) {
      arr.set("Z", "Zebra");
    }
    // SamR fixed this, which referred to the field rather than the method.
    assertEquals(arr.size(), 1);
  }

  /*
   * An edge case test to see if the program correctly throws an error message
   * when arr.get("b") is
   * unable to get a key
   */
  @Test
  public void samBighamEdge1() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();

    arr.set("a", "apple");
    arr.set("b", "banana");
    arr.set("c", "carrot");
    arr.remove("b");
    try {
      assertEquals(arr.get("b"), "an error message");
    } catch (Exception e) {
      assertEquals(e, e);
    }

  }

  // +-----------------------+---------------------------------------
  // | Tests by Micah Cantor |
  // +-----------------------+

  /**
   * Micah says Let's test out Hoare's billion dollar mistake. Sam, I hope these
   * tests are right.
   * 
   * Sam says null shouldn't be a value.
   */
  public void micahCantorTest1() {
    // values may be null
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    arr.set("NULL", null);
    assertEquals("{ NULL: null }", arr.toString());
    try {
      assertEquals(null, arr.get("NULL"));
    } catch (Exception e) {
      fail("Should not throw an exception when accessing null value");
    }
    arr.set("NULL", "null"); // Don't crash here!
  }

  /**
   * SamR removed this test because null need not be a valid key. (It wasn't
   * specified.)
   */
  public void micahCantorTest2() {
    // keys and values may be null
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    arr.set(null, null);
    assertEquals("{ null: null }", arr.toString());
    try {
      assertEquals(null, arr.get(null)); // Why not?
      arr.set(null, "not null"); // don't crash here either
    } catch (Exception e) {
      fail("Should not crash when getting/setting null key.");
    }
  }

  /**
   * Micah remains evil.
   *
   * SamR removed this test because the behavior on null inputs is unspecified.
   */
  public void micahCantorEdge1() {
    // Uh oh.
    AssociativeArray<Object, Object> arr = new AssociativeArray<Object, Object>();
    Object value = new Object();
    arr.set(null, value);
    assertEquals("{ null: " + value.toString() + " }", arr.toString());
    arr.set(value, null);
    try {
      assertEquals(null, arr.get(value));
    } catch (Exception e) {
      fail("Should not crash when accessing Object key with null value.");
    }

    // Make sure removal doesn't break with null values
    Object value2 = new Object();
    arr.set(value2, null);
    arr.remove(value);
    arr.remove(null); // Should remove the pair (null, value)
    arr.remove(null); // Should do nothing
    try {
      assertEquals(null, arr.get(value2));
    } catch (Exception e) {
      fail("Should not crash when accessing Object key with null value after removing other keys.");
    }
  }

  // +------------------------+--------------------------------------
  // | Tests by Reed Colloton |
  // +------------------------+

  /**
   * A test for removing a pair when it's the last element.
   */
  @Test
  public void reedCollotonEdge01() throws Exception {
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<>();
    arr.set(100, 4);
    assertEquals(4, arr.get(100));
    arr.remove(100);
    assertFalse(arr.hasKey(100));
    assertEquals(arr.size(), 0);
  } // reedCollotonEdge01()

  /**
   * Make sure it works on boolean keys.
   */
  @Test
  public void reedCollotonTest01() throws Exception {
    AssociativeArray<Boolean, String> arr = new AssociativeArray<>();
    arr.set(false, "43");
    arr.set(true, "27");
    assertEquals(2, arr.size());
    assertEquals("43", arr.get(false));
    assertEquals("27", arr.get(true));
    arr.set(false, "100");
    assertEquals("100", arr.get(false));
    arr.remove(false);
    arr.remove(true);
    assertEquals(0, arr.size());
  } // reedCollotonTest01()

  /**
   * Make sure it works on boolean values.
   */
  @Test
  public void reedCollotonTest02() throws Exception {
    AssociativeArray<String, Boolean> arr = new AssociativeArray<>();
    arr.set("43", false);
    arr.set("27", true);
    assertEquals(2, arr.size());
    assertFalse(arr.get("43"));
    assertTrue(arr.get("27"));
    arr.set("27", false);
    assertFalse(arr.get("27"));
    arr.remove("27");
    arr.remove("43");
    assertEquals(0, arr.size());
  } // reedCollotonTest02()

  // +------------------+--------------------------------------------
  // | Tests by Pom Dao |
  // +------------------+

  // +--------------------------+------------------------------------
  // | Tests by Joshua Delarosa |
  // +--------------------------+

  /*
   * Testing what happens if you try to set a value that has previously been set
   */
  @Test
  public void joshuaDelarosaTest1() throws KeyNotFoundException {
    AssociativeArray<String, String> testAA = new AssociativeArray<String, String>();

    testAA.set("fruit", "apple");
    testAA.set("color", "blue");
    testAA.set("animal", "giraffe");

    testAA.remove("animal");

    testAA.set("fruit", "banana");

    assertEquals("banana", testAA.get("fruit"));
  }

  /*
   * Testing that the size and hasKey functions work correctly
   */
  @Test
  public void joshuaDelarosaTest2() {

    AssociativeArray<String, String> testAA = new AssociativeArray<String, String>();

    testAA.set("fruit", "apple");
    testAA.set("color", "blue");
    testAA.set("animal", "giraffe");

    // Sam fixed this, which referred to the field rather than the method.
    assertEquals(3, testAA.size());

    testAA.remove("animal");
    // Sam fixed this, which referred to the field rather than the method.
    assertEquals(2, testAA.size());

    assertEquals(true, testAA.hasKey("fruit"));
    assertEquals(false, testAA.hasKey("animal"));
  }

  /*
   * Testing what happens if try to clone the same thing multiple times
   */
  @Test
  public void joshuaDelarosaEdge1() throws KeyNotFoundException {

    AssociativeArray<String, String> testAA = new AssociativeArray<String, String>();

    testAA.set("animal", "shark");

    AssociativeArray<String, String> testAAClone = testAA.clone();
    AssociativeArray<String, String> testAAClone2 = testAAClone.clone();
    AssociativeArray<String, String> testAAClone3 = testAAClone2.clone();
    AssociativeArray<String, String> testAAClone4 = testAAClone3.clone();

    assertEquals("shark", testAAClone4.get("animal"));

  }

  // +-------------------+-------------------------------------------
  // | Tests by Jinny Eo |
  // +-------------------+

  // testing for setting
  @Test
  // SamR add the throws clause and some syntax fixes
  public void jinnyEoTest1() throws KeyNotFoundException {
    AssociativeArray<String, String> testarr = new AssociativeArray<String, String>();
    // set key value pair
    testarr.set("1", "first");
    assertEquals("first", testarr.get("1"), "Set a first key value pair");
    assertEquals(1, testarr.size(), "size of assoc array");

    // remove key value pair
    testarr.remove("1");
    assertEquals(0, testarr.size(), "size should be 0 after removing only pair");
  }

  // testing for cloned array
  @Test
  // SamR added the throws clause and some other fixes
  public void jinnyEoTest2() throws KeyNotFoundException {
    AssociativeArray<String, String> testarr = new AssociativeArray<String, String>();
    // set key value pair
    testarr.set("1", "first");
    testarr.set("2", "second");
    testarr.set("3", "third");
    // double checking sizesadfa
    assertEquals(3, testarr.size(), "three pairs in assoc array");

    // create cloned array
    AssociativeArray<String, String> clonedTestarr = testarr.clone();
    clonedTestarr.set("2", "2nd");
    // check value in cloned array
    // SamR had to correct the following
    assertEquals("2nd", clonedTestarr.get("2"), "value of key 2 is 2nd");

    // check that value in original array is unchanged
    assertEquals("second", testarr.get("2"), "value of key 2 is second");

  }

  /*
   * SamR notes that remove is not supposed to throw exceptions and has commented
   * out this test.
   * 
   * // testing on empty assoc arrays
   * 
   * @Test public void jinnyEoEdge1() { AssociativeArray<String, String> testarr =
   * new
   * AssociativeArray<String, String>();
   * 
   * // check for toString when no values are set assertEquals("{}",
   * testarr.toString());
   * 
   * // check if error returns when attempting to remove a pair from empty list
   * try {
   * testarr.remove("1"); fail("Did not Throw KeyNotFoundException"); } catch
   * (KeyNotFoundException
   * e) { } }
   */

  // +---------------------------+-----------------------------------
  // | Tests by Kevin Fitzgerald |
  // +---------------------------+
  @Test
  public void kevinFitzgeraldTest1() {
    // Build Array
    AssociativeArray<String, String> testarr = new AssociativeArray<String, String>();
    // Set some values
    testarr.set("1", "one");
    testarr.set("2", "two");
    testarr.set("3", "tree");

    try {
      assertEquals(3, testarr.size());
    } catch (Exception e) {
      fail("set did not work three times");
    }
    try {
      assertEquals("one", testarr.get("1"));
    } catch (Exception e) {
      fail("Could not get value at '1'");
    } // try set 1

    testarr.set("1", "two");

    try {
      assertEquals("two", testarr.get("1"));
    } catch (Exception e) {
      fail("Could not change value at '1'");
    } // try 1 changed

    try {
      assertEquals("two", testarr.get("2"));
    } catch (Exception e) {
      fail("Could not get value at '2'");
    } // try 2
  }// kevinFitzgeraldTest1

  @Test
  public void kevinFitzgeraldTest2() {
    AssociativeArray<String, String> testarr = new AssociativeArray<String, String>();
    // Set the value
    testarr.set("1", "one");
    testarr.set("2", "two");
    testarr.set("3", "tree");

    testarr.remove("1");
    try {
      assertEquals(2, testarr.size());
    } catch (Exception e) {
      fail("remove failed with three elements");
    }
    testarr.remove("2");
    testarr.remove("3");
    try {
      assertEquals(0, testarr.size());
    } catch (Exception e) {
      fail("remove failed to empty the list");
    }
  }// kevinFitzgeraldTest2()

  @Test
  public void kevinFitzgeraldEdge1() {
    AssociativeArray<String, String> testarr = new AssociativeArray<String, String>();
    testarr.set("1", "one");
    testarr.remove("1");
    try {
      testarr.set("", "one");
      testarr.set("two", "");
      assertEquals("one", testarr.get(""));
      assertEquals("", testarr.get("two"));
    } catch (Exception e) {
      fail("could not work with empty strings");
    }
  }// kevinFitzgeraldEdge1()

  // +---------------------+-----------------------------------------
  // | Tests by Joyce Gill |
  // +---------------------+

  @Test
  public void joyceGillTest1() throws KeyNotFoundException {
    // Create an AssociativeArray
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();

    // Set key/values in arr
    arr.set("a", "one");
    arr.set("b", "two");
    arr.set("c", "three");

    // Test remove
    arr.remove("a");
    try {
      assertEquals(arr.get("a"), "Error: did not remove properly");
    } catch (Exception e) {
      assertEquals(e, e);
    }
  } // joyceGillTest1()

  @Test
  public void joyceGillTest2() {
    // Create an AssociativeArray
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();

    // Set key/values in arr
    arr.set("a", "one");
    arr.set("b", "two");
    arr.set("c", "three");

    // Test find
    try {
      assertEquals(arr.find("a"), 0);
    } catch (Exception e) {
      assertEquals(e, e);
    }
  } // joyceGillTest2

  @Test
  public void joyceGillEdge1() {
    // Create an AssociativeArray
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();

    // Check that it's empty
    assertEquals(0, arr.size());
    assertEquals("{}", arr.toString());
  }// joyceGillEdge1()

  // +--------------------+------------------------------------------
  // | Tests by Che Glenn |
  // +--------------------+

  @Test
  public void cheGlennTest01() { // test if array is cloned properly

    AssociativeArray<String, String> arr = new AssociativeArray<String, String>(); // create assoc
                                                                                   // array

    // set some KV pairs
    arr.set("a", "apple");
    arr.set("b", "banana");
    arr.set("c", "cucumber");

    // clone arr
    AssociativeArray<String, String> arr2 = arr.clone();

    try {
      assertEquals("apple", arr2.get("a")); // arr2 should have same val at key a

    } catch (Exception e) {
      fail("Array was not properly cloned");
    }
  }

  @Test
  public void cheGlennTest02() { // test if original array is affected when setting values of cloned
                                 // array

    AssociativeArray<String, String> arr = new AssociativeArray<String, String>(); // create arr

    // set some vals
    arr.set("a", "apple");
    arr.set("b", "banana");
    arr.set("c", "cucumber");

    AssociativeArray<String, String> arr2 = arr.clone(); // clone

    // set a new KV Pair in arr2
    arr2.set("d", "diamond");

    try {
      assertEquals(false, arr.hasKey("d")); // og arr should not have new key
      assertEquals(true, arr2.hasKey("d")); // copy should
    } catch (Exception e) {
      fail("Original array was changed when cloned array should have");
    }
  }

  @Test
  public void cheGlennEdge01() { // check if the size of the array goes back to 0 after adding
                                 // values and then
                                 // removing them all

    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>(); // create new
                                                                                       // array
    for (int i = 0; i < 10; i++) {
      arr.set(i, i + 1); // set some values
      arr.remove(i); // delete them all
    }
    assertEquals(0, arr.size()); // check if size change is accurate
  }

  // +-------------------------+-------------------------------------
  // | Tests by Kevin Johanson |
  // +-------------------------+

  /**
   * tests if overwriting values works
   *
   */
  @Test
  public void kevinJohansonTest1() {
    AssociativeArray<String, String> tester = new AssociativeArray<String, String>(); // builds the
                                                                                      // array

    tester.set("10", "5");
    tester.set("10", "8");
    try {
      assertEquals("8", tester.get("10"));
    } catch (Exception e) {
      fail("array did not overwite the value");
    } // catch()
  }// kevinJohansonTest1()

  /**
   * tests if remove handles the size
   *
   */
  @Test
  public void kevinJohansonTest2() {
    AssociativeArray<String, String> tester = new AssociativeArray<String, String>(); // builds the
                                                                                      // array

    tester.set("10", "5");
    tester.set("11", "8");
    try {
      // SamR changed `size` to `size()`
      assertEquals(2, tester.size());
    } catch (Exception e) {
      fail("array does not have the right size");
    } // catch()

    tester.remove("10");

    try {
      // SamR changed `size` to `size()`
      assertEquals(1, tester.size());
    } catch (Exception e) {
      fail("remove did not subtract size");
    } // catch()
  }// kevinJohansonTest2()

  /**
   * tests if expanding works SamR removed this test because it assumes a
   * particular approach to
   * expanding the array.
   */
  public void kevinJohansonEdge1() {
    AssociativeArray<String, String> tester = new AssociativeArray<String, String>(); // builds the
                                                                                      // array

    tester.set("1", "5");
    tester.set("2", "8");
    tester.set("3", "5");
    tester.set("4", "8");
    tester.set("5", "5");
    tester.set("6", "8");
    tester.set("7", "5");
    tester.set("8", "8"); // sets the array to its default full
    tester.set("9", "5");
    tester.set("10", "8");
    tester.set("11", "5");
    tester.set("12", "8");
    tester.set("13", "8");
    tester.set("14", "5");
    tester.set("15", "8");
    tester.set("16", "5");

    try {
      // SamR is replacing pairs.length with size()
      assertEquals(16, tester.size());
    } catch (Exception e) {
      fail("array expanded when it shouldn't have");
    } // catch()

    tester.set("17", "100"); // should automatically expand the array

    try {
      // SamR is replacing pairs.length with size()
      assertEquals(32, tester.size());
    } catch (Exception e) {
      fail("array did not expand");
    } // catch()
  } // kevinJohansonEdge1()

  // +----------------------+----------------------------------------
  // | Tests by Chloe Kelly |
  // +----------------------+

  /**
   * Does toString() skip all empty values?
   *
   * SamR removed this test because it makes assumptions about the order of values
   * in the array.
   */
  public void chloeKellyTest1() {
    // Build Array
    AssociativeArray<String, String> testarr = new AssociativeArray<String, String>();
    // Set some values
    testarr.set("A", "Red");
    testarr.set("B", "Blue");
    testarr.set("C", "Green");
    try {
      assertEquals("Red", testarr.get("A"));
    } catch (Exception e) {
      fail("Array value could not be set to Red");
    } // try/catch Red
    try {
      assertEquals("Blue", testarr.get("B"));
    } catch (Exception e) {
      fail("Array value could not be set to Blue");
    } // try/catch Blue
    try {
      assertEquals("Green", testarr.get("C"));
    } catch (Exception e) {
      fail("Array value could not be set to Green");
    } // try/catch green

    assertEquals("{ A: Red, B: Blue, C: Green }", testarr.toString());
    // Remove the middle value
    testarr.remove("B");
    assertEquals("{ A: Red, C: Green }", testarr.toString());
  }// chloeKellyTest1()

  /**
   * Replace values at a given key
   */
  @Test
  public void chloeKellyTest2() {
    AssociativeArray<String, String> testarr = new AssociativeArray<String, String>();
    // Set the value
    testarr.set("A", "Red");
    try {
      assertEquals("Red", testarr.get("A"));
    } catch (Exception e) {
      fail("Array value could not be set to Red");
    } // try/catch Red
    // Replace the value
    testarr.set("A", "Yellow");
    // Check if it worked
    try {
      assertEquals("Yellow", testarr.get("A"));
    } catch (Exception e) {
      fail("Array value could not be set to Yellow");
    } // try/catch Red
  }// chloeKellyTest2()

  /**
   * Printing an empty array
   */
  @Test
  public void chloeKellyEdge1() {
    AssociativeArray<String, String> testarr = new AssociativeArray<String, String>();
    // Make sure it is empty
    assertEquals(0, testarr.size());
    assertEquals("{}", testarr.toString());
  }// chloeKellyEdge1()

  // +--------------------+------------------------------------------
  // | Tests by Hyeon Kim |
  // +--------------------+

  /**
   * Tests on set() on a <String, String> associative array
   */
  @Test
  public void hyeonKim1() {
    /**
     * Create an empty test array
     */
    AssociativeArray<String, String> testArray = new AssociativeArray<String, String>();

    /**
     * Add elements to the testArray
     */
    testArray.set("hello", "world");
    /**
     * value correctivity example
     */
    try {
      assertEquals("world", testArray.get("hello"), "hello world example");
    } catch (Exception e) {
      fail("hello world example");
    }
    /**
     * setting existing key with another value
     */
    testArray.set("hello", "hello");
    /**
     * checking whether it was correctly set.
     */
    try {
      assertEquals("hello", testArray.get("hello"), "existing key example");
    } catch (Exception e) {
      fail("existing key example");
    }
    /**
     * checking the length difference
     */
    testArray.set("hello", "a");
    assertEquals(1, testArray.size(), "existing key length example");
  }

  /**
   * Tests on set() on a <String, String> associative array
   */
  @Test
  public void hyeonKim2() {
    AssociativeArray<String, String> testArray = new AssociativeArray<String, String>();
    AssociativeArray<String, String> copiedArray = new AssociativeArray<String, String>();
    testArray.set("hello", "world!");
    copiedArray = testArray.clone();
    copiedArray.set("hello", "hi");
    copiedArray.set("world", "hi");
    try {
      assertEquals("world!", testArray.get("hello"), "cloned array example");
    } catch (Exception e) {
      fail("cloned array example");
    }
    assertEquals(2, copiedArray.size(), "cloned array length example");
  }

  // SamR says: A little too edgy
  public void hyeonKimEdgeCases() {
    AssociativeArray<String, String> testArray = new AssociativeArray<String, String>();
    /**
     * null example 1
     */
    testArray.set(null, null);
    try {
      assertEquals(null, testArray.get(null), "null example");
    } catch (Exception e) {
      fail("null example");
    }

    /**
     * null example 2
     */
    testArray.set("hello", null);
    try {
      assertEquals(null, testArray.get("hello"), "null example 2");
    } catch (Exception e) {
      fail("null example 2");
    }

    /**
     * Checking whether null hasKey works
     */
    assertTrue(testArray.hasKey(null), "null hasKey");
  }

  // +---------------------+-----------------------------------------
  // | Tests by Julian Kim |
  // +---------------------+

  /**
   * Testing set(), get(), and remove()
   */
  @Test
  public void julianKimTest1() {
    AssociativeArray<Integer, String> testArr = new AssociativeArray<Integer, String>();

    /**
     * set()-specific tests
     */
    // setting a key with a value
    testArr.set(0, "a");
    try {
      assertEquals("a", testArr.get(0), "Can set a key with a value");
    } catch (Exception e) {
      fail("Could not set key with value");
    }

    // having more than 1 key set
    testArr.set(1, "b");
    try {
      assertEquals("b", testArr.get(1), "More than 1 key can be set");
    } catch (Exception e) {
      fail("Could not set more than 1 key");
    }

    // overwriting previously set key
    testArr.set(0, "c");
    try {
      assertEquals("c", testArr.get(0), "Can overwrite exisiting key with a new value");
    } catch (Exception e) {
      fail("Could not overwrite existing key with new value");
    }

    /**
     * get()-specific tests
     */
    // getting non-existent key
    try {
      testArr.get(2);

      fail("Should not have gotten a value for a non-existent key");
    } catch (Exception e) {
      if (!(e instanceof KeyNotFoundException)) {
        fail("Wrong type of exception thrown");
      }
    }

    /**
     * remove()-specific tests
     */
    // removing existing key
    testArr.remove(0);
    try {
      testArr.get(0);

      fail("Should have thrown an exception for a removed k/v pair");
    } catch (Exception e) {
      if (!(e instanceof KeyNotFoundException)) {
        fail("Wrong type of exception thrown");
      }
    }

    // removing non-existent key
    testArr.remove(2);
  }

  /**
   * Testing hasKey() and size()
   */
  @Test
  public void julianKimTest2() {
    AssociativeArray<Integer, String> testArr = new AssociativeArray<Integer, String>();
    /**
     * hasKey()-specific tests
     */
    // array has set key
    testArr.set(0, "a");
    assertTrue(testArr.hasKey(0), "Array has set key");

    // array does not have not set key
    assertFalse(testArr.hasKey(1), "Array does not have not set key");

    // array does not have removed key
    testArr.remove(0);
    assertFalse(testArr.hasKey(0), "Array does not have removed key");

    /**
     * size()-specific tests
     */
    // array of size 1 has correct size
    testArr.set(0, "a");
    assertEquals(1, testArr.size(), "Array of size 1 has correct size");

    // array with more than 1 element has correct size
    testArr.set(1, "b");
    testArr.set(2, "c");
    assertEquals(3, testArr.size(), "Array with more than 1 element has correct size");

    // Removing element decreases size by 1
    testArr.remove(1);
    assertEquals(2, testArr.size(), "Removing element decreases size by 1");
  }

  @Test
  public void julianKimEdge1() {
    AssociativeArray<Integer, String> testArr = new AssociativeArray<Integer, String>();
    // hasKey works on empty array
    assertFalse(testArr.hasKey(0), "hasKey works on empty array");

    // empty array has 0 size
    assertEquals(0, testArr.size(), "Empty array has 0 size");

    // get on empty array returns proper exception
    try {
      testArr.get(0);

      fail("Should not have gotten a value for a non-existent key");
    } catch (Exception e) {
      if (!(e instanceof KeyNotFoundException)) {
        fail("Wrong type of exception thrown");
      }
    }

    // overwriting element should not change size
    testArr.set(0, "a");
    int originalSize = testArr.size();
    testArr.set(0, "b");
    assertEquals(originalSize, testArr.size(), "Overwriting element should not change size");

    // removing a k/v pair should not tamper with other k/v pairs
    testArr.set(1, "c");
    testArr.remove(0);
    try {
      assertEquals("c", testArr.get(1),
          "Removing a k/v pair should not tamper with other k/v pairs");
    } catch (Exception e) {
      fail("Should have gotten existing k/v pair");
    }

    // removing previously removed key
    testArr.remove(0);
    try {
      testArr.get(0);

      fail("Should have thrown an exception for a removed k/v pair");
    } catch (Exception e) {
      if (!(e instanceof KeyNotFoundException)) {
        fail("Wrong type of exception thrown");
      }
    }

    /**
     * using floats as keys
     */
    // setting
    AssociativeArray<Float, String> floatTest = new AssociativeArray<Float, String>();
    floatTest.set(0.1f, "a");

    // hasKey and size
    assertTrue(floatTest.hasKey(0.1f), "Float-keyed array has set key");
    assertEquals(1, floatTest.size(), "Float-keyed array has proper size");

    // getting
    try {
      assertEquals("a", floatTest.get(0.1f), "Getting from Float-keyed array works");
    } catch (Exception e) {
      fail("Should not have thrown an exception for getting from Float-keyed array");
    }

    // removing
    floatTest.remove(0.1f);
    try {
      floatTest.get(0.1f);

      fail("Should have thrown exception for removed k/v pair");
    } catch (Exception e) {
      if (!(e instanceof KeyNotFoundException)) {
        fail("Wrong type of exception thrown");
      }
    }
  }

  // +-----------------------+---------------------------------------
  // | Tests by Jayson Kunkel |
  // +-----------------------+

  /**
   * A series of tests of the size() method
   */
  @Test
  public void jaysonKunkelTest1() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();

    // empty array should have size 0
    assertEquals(0, arr.size());

    // array with one element should have size 1
    arr.set("0", "value");
    assertEquals(1, arr.size());

    // the default size is 16, so a full array should have size 16
    for (int i = 0; i < 15; i++) {
      arr.set("" + i, "value");
    } // for
    // We've only added fifteen values, so SamR changed the following to 15.
    assertEquals(15, arr.size());

    // we should now have 14 KVpairs
    arr.remove("2");
    arr.remove("4");
    // SamR changed this one, too
    assertEquals(13, arr.size());

  } // jaysonKunkelTest1

  /**
   * Testing that find works as intended
   */
  @Test
  public void jaysonKunkelTest2() {
    AssociativeArray<Integer, String> arr = new AssociativeArray<Integer, String>();

    // set some KVpairs
    for (int i = 0; i < 10; i++) {
      arr.set(i, i + "mississippi");
    }

    // check that find works correctly for each index
    try {
      for (int i = 0; i < 10; i++) {
        assertEquals(i, arr.find(i));
      }
    } catch (Exception e) {
      fail("Could not find key in array");
    }
  }
  // should throw a KeyNotFoundException
  /*
   * 
   * try { assertEquals(11, arr.find(11));
   * fail("key 11 does not exist in the array"); } catch
   * (Exception e) { // do nothing }
   */

  // jaysonKunkelTest2

  /**
   * What happens if we try to expand the empty array?
   */
  @Test
  public void jaysonKunkelEdge1() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();

    // confirm the size of the empty array is 0
    assertEquals(0, arr.size());

    // since there are no pairs in the empty array, expand() should have no effect
    arr.expand();
    assertEquals(0, arr.size());

    // now lets confirm it works correctly
    arr.set("A", "Apple");
    // SamR replaced `length` with `size()`
    assertEquals(1, arr.size());
    arr.expand();
    // SamR replaced `length` with `size()`
    assertEquals(1, arr.size());

  } // jaysonKunkelEdge1

  // +---------------------+-----------------------------------------
  // | Tests by Wenfei Lin |
  // +---------------------+

  /**
   * Checking cloned array expands properly
   */
  @Test
  public void wenfeiLinTest01() {
    // Create an array
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();
    // Set 16 KVPairs
    for (int i = 0; i <= 15; i++) {
      arr.set(i, i + 5);
    }

    // Clone original array
    AssociativeArray<Integer, Integer> clonedArr = arr.clone();
    // Set an additional KVPair to make cloned array expand
    clonedArr.set(20, 100);

    // Attempt to retrieve the 17 values from the 17 keys
    try {
      for (int i = 0; i <= 15; i++) {
        assertEquals(i + 5, clonedArr.get(i));
      }
      assertEquals(100, clonedArr.get(20));
    } catch (Exception e) {
      fail("Could not get values of all keys in cloned array");
    }
    assertEquals(17, clonedArr.size());
  } // wenfeiLinTest01()

  /**
   * Checking that the size doesn't change when setting a new value for an
   * existing key
   */
  @Test
  public void wenfeiLinTest02() {
    // Create an array
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    // Create a KVPair with "popcorn" as the key
    arr.set("popcorn", "salted butter");
    // Check initial size after adding a KVPair
    assertEquals(1, arr.size());

    // Change the value associated with the key "popcorn" from "salted butter" to
    // "caramel"
    arr.set("popcorn", "caramel");
    // Test new value was set
    try {
      assertEquals("caramel", arr.get("popcorn"));
    } catch (Exception e) {
      fail("Exception in call to get");
    }
    // Check that the size did not increment
    assertEquals(1, arr.size());
  } // wenfeiLinTest02()

  /**
   * Testing that no key exists in an empty array with hasKey
   */
  @Test
  public void wenfeiLinEdge01() {
    // Create an array
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    assertEquals(false, arr.hasKey("anything"));
  } // wenfeiLinEdge01()

  // +-----------------------+---------------------------------------
  // | Tests by Noah Mendola |
  // +-----------------------+

  // +----------------------+----------------------------------------
  // | Tests by John Miller |
  // +----------------------+

  /* this tests the clone method */
  @Test
  public void johnMillerTest01() {
    // Build an array
    AssociativeArray<String, String> array = new AssociativeArray<String, String>();
    array.set("p", "platypus");// set a key/value pair in the AssociativeArray
    AssociativeArray<String, String> aClone = array.clone();
    try {
      assertEquals("platypus", aClone.get("p"));// checking that the new array contains the value
                                                // from the initial array
    } catch (Exception e) {
      fail("Clone was not properly copied");
    }
  }// test 1

  /* this tests the remove method */
  @Test
  public void johnMillerTest02() {
    // Build an array
    AssociativeArray<String, String> array = new AssociativeArray<String, String>();
    array.set("p", "platypus");// set a key/value pair in the AssociativeArray
    array.set("g", "Giraffe");
    array.remove("p");// remove platypus from the array
    try {
      assertEquals(false, array.hasKey("p"));// checking that the p key was removed
    } catch (Exception e) {
      fail("The 'p' key was not properly removed\n");
    }
  }// test 2

  /* this tests the ability of get to find and overwrite values */
  @Test
  public void johnMillerTest03() {
    // Build an array
    AssociativeArray<BigInteger, String> array = new AssociativeArray<BigInteger, String>();
    array.set(BigInteger.valueOf(8), "platypus");// set a key/value pair in the AssociativeArray
    array.set(BigInteger.valueOf(15), "Giraffe");// set a key/value pair in the AssociativeArray
    array.set(BigInteger.valueOf(8), "dog");// set a key/value pair in the AssociativeArray
    try {
      assertEquals("dog", array.get(BigInteger.valueOf(8)));// checking that the key was overwritten
    } catch (Exception e) {
      fail("The value at key BigInt 8 wasn't properly overwritten\n");
    }
  }// test 3

  /*
   * this is an edge case for removing a key from an empty array. this is supposed
   * to fail
   */
  @Test
  public void johnMillerEdge01() {
    AssociativeArray<String, String> array = new AssociativeArray<String, String>();
    array.remove("z");
  }// edge case

  // +-----------------------+---------------------------------------
  // | Tests by Albert Okine |
  // +-----------------------+

  // SamR replaced `arr.DEFAULT_CAPACITY` with `DEFAULT_CAPACITY`
  static final int DEFAULT_CAPACITY = 16;

  /** Test basic get and remove functions. */
  @Test
  public void albertOkineTest01() {
    ///////////////////////////////////////////////////
    // needs to be in the `strutures` directory to run
    ///////////////////////////////////////////////////

    // Define an associative array
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();
    // define the total number of tests to use
    int numTests = DEFAULT_CAPACITY * DEFAULT_CAPACITY;

    // Assert that the array initialized to size 0
    assertEquals(0, arr.size());

    // Iteratively add elements to the array and check size.
    for (int i = 0; i < numTests; i++) {
      arr.set(i, i);
      assertEquals(i + 1, arr.size());
    } // for (add every element to array)

    // Iteratively remove elements from the array and check size.
    for (int i = 0; i < numTests; i++) {
      arr.remove(i);
      assertEquals(numTests - i - 1, arr.size());
    } // for (remove every element in array)

    // Assert that the array finished with size 0
    assertEquals(0, arr.size());
  } // albertOkineTest01

  /** Test the clone function. */
  @Test
  public void albertOkineTest02() {
    ///////////////////////////////////////////////////
    // needs to be in the `strutures` directory to run
    ///////////////////////////////////////////////////

    // Define an associative array
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();

    // Iteratively add elements to the array.
    for (int i = 0; i < DEFAULT_CAPACITY; i++) {
      arr.set(i, i);
    } // for (add every element to array)

    // clone the current associative array.
    AssociativeArray<Integer, Integer> arrClone = arr.clone();

    // Iteratively check that clone worked properly.
    for (int i = 0; i < DEFAULT_CAPACITY; i++) {
      try {
        assertEquals(arr.get(i), arrClone.get(i));
      } catch (Exception e) {
        fail("Original array and copy array do not match");
      } // try...catch
    } // for (add every element to array)
  } // albertOkineTest02

  /** Ruin someone's day. */
  public void albertOkineEdge01() {
    // Define an associative array
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();

    // Test null as a value.
    arr.set(0, null);
    arr.remove(0);

    // Test null as a key.
    arr.set(null, 0);
    arr.remove(null);

  } // albertOkineEdge01

  // +---------------------+-----------------------------------------
  // | Tests by Alma Ordaz |
  // +---------------------+

  @Test
  public void almaOrdazTest01() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();

    // testing remove
    arr.set("a", "hello");
    arr.set("b", "world");
    arr.set("c", "!!!!!");
    arr.remove("b");

    assertEquals(false, arr.hasKey("b"));
    assertEquals(true, arr.hasKey("a"));

  }// almaOrdazTest01

  @Test
  public void almaOrdazTest02() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();

    // testing size()

    assertEquals(0, arr.size());
    arr.set("a", "Hello");
    arr.set("b", "world");

    assertEquals(2, arr.size());
    arr.set("c", "!!!!!");

    assertEquals(3, arr.size());

  }// almaOrdazTest01

  @Test
  public void almaOrdazEdge01() throws KeyNotFoundException {
    AssociativeArray<String, Integer> arr = new AssociativeArray<String, Integer>();

    // testing when a key is a capital version of another with different values. Ex.
    // a, and A
    arr.set("a", 3);
    arr.set("A", 10);

    assertEquals(3, arr.get("a"));
    assertEquals(10, arr.get("A"));

  }// almaOrdazTest01

  // +-----------------------------+---------------------------------
  // | Tests by Samuel A. Rebelsky |
  // +-----------------------------+

  /**
   * A test of cloning.
   */
  @Test
  public void samuelRebelskyTest01() {
    // Build an array
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    arr.set("A", "Apple");
    try {
      assertEquals("Apple", arr.get("A"));
    } catch (Exception e) {
      fail("Original array does not contain expected value");
    }
    // Make a copy
    AssociativeArray<String, String> arr2 = arr.clone();
    // Make sure it contains the appropriate value
    try {
      assertEquals("Apple", arr2.get("A"));
    } catch (Exception e) {
      fail("Clone does not contain original value");
    } // try/catch
    // Change the original array
    arr.set("A", "aardvark");
    // Make sure we haven't changed the clone.
    try {
      assertEquals("Apple", arr2.get("A"));
    } catch (Exception e) {
      fail("Change to original changes clone.");
    }
    // Change the clone
    arr2.set("A", "Ant");
    // And look for values
    try {
      assertEquals("Ant", arr2.get("A"));
    } catch (Exception e) {
      fail("Cannot change clone");
    }
    try {
      assertEquals("aardvark", arr.get("A"));
    } catch (Exception e) {
      fail("Change to clone changes original");
    }
  } // samuelRebelskyTest01()

  /**
   * Can we successfully add a bunch of values? (Checks array expansion.)
   */
  @Test
  public void samuelRebelskyTest02() {
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();
    // Add a bunch of values
    for (int i = 10; i < 50; i++) {
      arr.set(i, i * i);
    } // for
    try {
      for (int i = 49; i >= 10; i--) {
        assertEquals(i * i, arr.get(i));
      }
    } catch (Exception e) {
      fail("Exception in call to get");
    }
  } // samuelRebelskyTest02()

  /**
   * Do we get exceptions when grabbing a deleted value from the array?
   */
  @Test
  public void samuelRebelskyTest03() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    // Add an element to the array
    arr.set("A", "Apple");
    // Make sure that it's there.
    try {
      assertEquals("Apple", arr.get("A"));
    } catch (KeyNotFoundException e) {
      fail("Could not set A to Apple");
    }
    // Remove it.
    arr.remove("A");
    // Make sure it's no longer there.
    try {
      // The following line should throw an exception
      arr.get("A");
      fail("Did not throw an exception");
    } catch (KeyNotFoundException e) {
      // Do nothing
    }
  } // samuelRebelskyTest03

  /**
   * Does `toString` work correctly on multi-element arrays?
   */
  @Test
  public void samuelRebelskyTest04() {
    AssociativeArray<String, Integer> si = new AssociativeArray<String, Integer>();
    si.set("A", 1);
    si.set("B", 2);
    si.set("C", 3);
    String result = si.toString();
    // System.err.println(result);
    assertTrue(result.equals("{ A: 1, B: 2, C: 3 }") || result.equals("{ A: 1, C: 3, B: 2 }")
        || result.equals("{ B: 2, A: 1, C: 3 }") || result.equals("{ B: 2, C: 3, A: 1 }")
        || result.equals("{ C: 3, A: 1, B: 2 }") || result.equals("{ C: 3, B: 2, A: 1 }"));
  } // samuelRebelskyTest04()

  /**
   * Do we get exceptions when grabbing a value from the empty array.
   */
  @Test
  public void samuelRebelskyEdge01() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    try {
      // The following line should throw an exception
      arr.get("A");
      fail("Did not throw an exception");
    } catch (KeyNotFoundException e) {
      // Do nothing
    }
  } // samuelRebelskyEdge01

  @Test
  public static void samuelRebelskyPointlessTest() {
    assertTrue(true);
  } // samuelRebelskyPointlessTest()

  // +--------------------------+------------------------------------
  // | Tests by Maria Rodriguez |
  // +--------------------------+

  // Check to see if clone accurately sets a new value and has the same values as
  // the previous array
  @Test
  public void mariaRodriguezTest01() {
    AssociativeArray<String, String> test1 = new AssociativeArray<String, String>();
    AssociativeArray<String, String> clone = new AssociativeArray<String, String>();
    test1.set("a", "apple");
    test1.set("b", "balloon");
    clone = test1.clone();
    clone.set("c", "cartoon");

    try {
      assertEquals("cartoon", clone.get("c"));
      assertEquals("balloon", clone.get("b"));
      assertEquals("apple", clone.get("a"));

    } catch (KeyNotFoundException e) {
    }

    // the original array should not have a key that was added to the cloned array
    try {
      test1.get("c");
      fail("Did not throw an exception");
    } catch (Exception e) {
    }

  }// mariaRodriguezTest01

  // tests to see that associativeArray functions work as intended
  @Test
  public void mariaRodriguezTest02() {
    AssociativeArray<String, String> test2 = new AssociativeArray<String, String>();
    test2.set("a", "apple");
    test2.set("b", "balloon");

    try {
      assertEquals("apple", test2.get("a"));
      assertEquals("balloon", test2.get("b"));
    } catch (KeyNotFoundException e) {
    }

    assertEquals(2, test2.size());
    assertEquals(true, test2.hasKey("b"));
    test2.remove("a");
    assertEquals(false, test2.hasKey("a"));
    assertEquals(1, test2.size());
  }// mariaRodriguezTest02

  // tests to make sure the code treats " " and " " as two separate keys (tests
  // empty space)
  @Test
  public void mariaRodriguezEdge01() {
    AssociativeArray<String, String> edge = new AssociativeArray<String, String>();
    edge.set(" ", "apple");
    edge.set("  ", "balloon");

    try {
      assertEquals("apple", edge.get(" "));
      assertEquals("balloon", edge.get("  "));
    } catch (KeyNotFoundException e) {

    }
  }// mariaRodriguezEdge01

  // +-----------------------------+---------------------------------
  // | Tests by Gabriela Roznawska |
  // +-----------------------------+

  @Test
  public void gabrielaRoznawskaTest01() {
    // create an initialize an array to an Apple
    AssociativeArray<String, String> ar1 = new AssociativeArray<String, String>();
    ar1.set("Apple", "Apple-Cinnamon milk rice");

    // if the set to an Apple was not succesful then return an exception
    try {
      assertEquals("Apple-Cinnamon milk rice", ar1.get("Apple"));
    } catch (KeyNotFoundException exception) {
      fail("Could not set Apple to Apple-Cinnamon milk rice");
    }
  } // gabrielaRoznawskaTest01

  @Test
  public void gabrielaRoznawskaTest02() {
    // declare and initialize the keys in the array
    AssociativeArray<String, String> ar1 = new AssociativeArray<String, String>();
    ar1.set("Apple", "Apple-Cinnamon milk rice");
    ar1.set("Beans", "Tomato sauce Beans");
    // if the set was unsuccesful then return exception
    try {
      assertEquals("Tomato sauce Beans", ar1.get("Beans"));
    } catch (KeyNotFoundException exception) {
      fail("Could not set Tomato sauce Beans");
    }

    // removing the Apple key from the array
    ar1.remove("Apple");

    // If the removing did not work then throw an exception
    try {
      ar1.get("Apple");
      fail("Did not throw an exception");
    } catch (KeyNotFoundException e) {
    }

    // if the remaining key: beans is still in the array then do not throw an
    // exception
    try {
      assertEquals("Tomato sauce Beans", ar1.get("Beans"));
    } catch (KeyNotFoundException exception) {
      fail("Removing of the Apple disrupted the Beans");
    }
  } // gabrielaRoznawska02

  @Test
  public void gabrielaRoznawska03() throws KeyNotFoundException {
    // declaration of the arrays and initialization of the first one
    AssociativeArray<String, String> ar1 = new AssociativeArray<String, String>();
    AssociativeArray<String, String> ar2 = new AssociativeArray<String, String>();
    ar1.set("Apple", "Apple-Cinnamon milk rice");

    // clone the array 1 contents onto the array 2 and throw exception if it was not
    // succesful
    try {
      ar2 = ar1.clone();
      assertEquals("Apple-Cinnamon milk rice", ar2.get("Apple"));
    } catch (KeyNotFoundException exception) {
      fail("Cloning not succesful");
    }
    // overwrite the "apple" with a different value
    ar2.set("Apple", "Apples with carmel glaze");

    // if the overwriting was unsuccesful then throw exception
    try {
      assertEquals("Apples with carmel glaze", ar2.get("Apple"));
    } catch (KeyNotFoundException exception) {
      fail("Overwriting the Apple key was unsuccesful");
    }

  } // gabrielaRoznawska03 test

  // +-----------------------+---------------------------------------
  // | Tests by Shuta Shibue |
  // +-----------------------+
  @Test
  public void shutaShibueTest1() {
    // Build Array
    AssociativeArray<String, Integer> test1 = new AssociativeArray<String, Integer>();
    // Setting some values
    test1.set("CSC", 207);
    test1.set("BIO", 150);
    test1.set("STA", 209);
    try {
      assertEquals(150, test1.get("BIO"));
    } catch (Exception e) {
      fail("Value should be 150");
    } // try-catch
    // SamR removed the next line because the ordering is not predictable.
    // assertEquals("{ CSC: 207, BIO: 150, STA: 209 }", test1.toString());
    // Remove the middle value
    test1.remove("CSC"); // should remove element
    // SamR changed `size` to `size()`
    assertEquals(2, test1.size());
    // SamR changed `size` to `size()`
    test1.remove("CSC"); // should do nothing
    // SamR changed `size` to `size()`
    assertEquals(2, test1.size());
  }// shutaShibueTest1()

  @Test
  public void shutaShibueTest2() {
    // Build Array
    AssociativeArray<String, String> test1 = new AssociativeArray<String, String>();
    // Setting some values
    test1.set("CSC", "207");
    test1.set("CSC", "161");
    test1.set("STA", "209");
    try {
      assertEquals("161", test1.get("CSC"));
    } catch (Exception e) {
      fail("Value should be 161");
    } // try-catch
    // SamR commented out the next line; don't assume a particular order.
    // assertEquals("{ CSC: 161, STA: 209 }", test1.toString());
    // Remove the middle value
    test1.remove("CSC"); // should remove element
    // SamR changed `size` to `size()`
    assertEquals(1, test1.size());
    test1.remove("STA"); // should do nothing
    // SamR changed `size` to `size()`
    assertEquals(0, test1.size());
  }// shutaShibueTest2()

  @Test
  public void shutaShibueEdge() {
    // Build Array
    AssociativeArray<String, String> test1 = new AssociativeArray<String, String>();
    // Setting some values
    test1.set("", "207");
    try {
      assertEquals("207", test1.get(""));
    } catch (Exception e) {
      fail("Value should be 207");
    } // try-catch
    assertEquals("{ : 207 }", test1.toString());
    // Remove the middle value
  }// shutaShibueEdge()

  // +----------------------+----------------------------------------
  // | Tests by Madel Sibal |
  // +----------------------+

  /**
   * Tests the behavior of removing keys & the size method of AssociativeArray.
   */
  @Test
  public void madelSibalTest1() {
    AssociativeArray<Integer, String> testarr = new AssociativeArray<>();

    // Add some key-value pairs
    testarr.set(1, "One");
    testarr.set(2, "Two");
    testarr.set(3, "Three");
    testarr.set(4, "Four");

    // Check the initial size
    assertEquals(4, testarr.size());

    // Remove a key-value pair
    testarr.remove(2);

    // Check the size after removal
    assertEquals(3, testarr.size());

    // Attempt to remove a non-existing key
    testarr.remove(5);

    // Check that the size remains the same
    assertEquals(3, testarr.size());
  }

  /**
   * Tests if AssociativeArray can handle a large number of key-value pairs
   * correctly & expand
   * capacity as needed.
   */
  @Test
  public void madelSibalTest2() {
    AssociativeArray<Integer, Integer> testarr = new AssociativeArray<>();

    // Add a large number of key-value pairs
    for (int i = 1; i <= 10000; i++) {
      testarr.set(i, i * i);
    }

    // Verify that values can be retrieved correctly
    try {
      for (int i = 1; i <= 10000; i++) {
        assertEquals(i * i, (int) testarr.get(i));
      }
    } catch (KeyNotFoundException e) {
      fail("KeyNotFoundException should not be thrown for large capacity test.");
    }
  }

  /**
   * Tests if AssociativeArray behaves correctly when retrieving a value from an
   * empty array.
   */
  @Test
  public void madelSibalEdge1() {
    AssociativeArray<String, String> testarr = new AssociativeArray<>();

    // Attempt to get a value from an empty array
    assertThrows(KeyNotFoundException.class, () -> testarr.get("nonexistent"));
  }

  // +------------------------------+--------------------------------
  // | Tests by Livia Stein Freitas |
  // +------------------------------+

  // +------------------------+--------------------------------------
  // | Tests by Tyrell Taylor |
  // +------------------------+

  // +-----------------------+---------------------------------------
  // | Tests by Audrey Trinh |
  // +-----------------------+
  @Test
  public void audreyTrinhTest01() {
    // Build an array
    AssociativeArray<Integer, String> arr = new AssociativeArray<Integer, String>();
    arr.set(0, "baby");
    try {
      assertEquals("baby", arr.get(0));
    } catch (Exception e) {
      fail("Original array does not contain expected value");
    }
    // Make a copy
    // SamR fixed the type (was AssociativeArray<String,String>)
    AssociativeArray<Integer, String> arr2 = arr.clone();
    // Make sure it contains the appropriate value
    try {
      assertEquals("baby", arr2.get(0));
    } catch (Exception e) {
      fail("Clone does not contain original value");
    } // try/catch
    // Change the original array
    arr.set(0, "infant");
    arr.set(3, "toddler");
    // Make sure we haven't changed the clone.
    try {
      assertEquals("baby", arr2.get(0));
    } catch (Exception e) {
      fail("Change to original changes clone.");
    }
    try {
      arr2.get(3);
      fail("Did not throw an exception. Change to original changes clone.");
    } catch (Exception e) {
      // do nothing
    }
  } // audreyTrinhTest01()

  @Test
  public void audreyTrinhTest02() {
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();
    // Add values to check expansion
    for (int i = 0; i < 100; i++) {
      arr.set(i, i);
    } // for
    try {
      for (int i = 0; i < 100; i++) {
        assertEquals(i, arr.get(i));
      }
    } catch (Exception e) {
      fail("Exception in call to get");
    }
  } // audreyTrinhTest02()

  @Test
  public void audreyTrinhTest03() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    // Add an element to the array
    arr.set("hello", "world");
    try {
      assertEquals("world", arr.get("hello"));
    } catch (KeyNotFoundException e) {
      fail("Could not set hello to world");
    }
    // Remove it.
    arr.remove("hello");
    // Make sure it's no longer there.
    try {
      // The following line should throw an exception
      arr.get("hello");
      fail("Did not throw an exception");
    } catch (KeyNotFoundException e) {
      // Do nothing
    }
  } // audreyTrinhTest03

  @Test
  public void audreyTrinhEdge01() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    try {
      // The following line should throw an exception
      arr.get("haha");
      fail("Did not throw an exception");
    } catch (KeyNotFoundException e) {
      // Do nothing
    }
  } // audreyTrinhEdge01

  @Test
  public void audreyTrinhEdge02() {
    AssociativeArray<Integer, Integer> arr = new AssociativeArray<Integer, Integer>();
    arr.set(1, 10);
    arr.set(1, 20);
    arr.remove(1);
    try {
      // The following line should throw an exception
      arr.get(30);
      fail("Did not throw an exception");
    } catch (KeyNotFoundException e) {
      // Do nothing
    }
    try {
      // The following line should throw an exception
      arr.get(1);
      fail("Did not throw an exception");
    } catch (KeyNotFoundException e) {
      // Do nothing
    }
  } // audreyTrinhEdge02

  // +-------------------------+-----------------------------------------
  // | Tests by Rene Urias Jr. |
  // +-------------------------+

  /**
   * Basic Test with Non-null Key
   */
  @Test
  public void reneUriasJrTest01() {
    AssociativeArray<String, Integer> array = new AssociativeArray<>();

    array.set("one", 1);
    array.set("two", 2);

    try {
      assert array.hasKey("one") : "Test failed: hasKey(\"one\")";
      assert array.hasKey("two") : "Test failed: hasKey(\"two\")";
      assert !array.hasKey("three") : "Test failed: hasKey(\"three\")";
      assert array.size() == 2 : "Test failed: size()";
      assert array.get("one") == 1 : "Test failed: get(\"one\")";
      assert array.get("two") == 2 : "Test failed: get(\"two\")";
    } catch (KeyNotFoundException e) {
      // Handle the exception as needed
      e.printStackTrace(); // or log the exception
    }
  }

  /**
   * Handling Null Key
   */
  @Test
  public void reneUriasJrTest02() {
    AssociativeArray<String, Integer> array = new AssociativeArray<>();

    array.set(null, 0);
    array.set("one", 1);

    try {
      assert array.hasKey(null) : "Test failed: hasKey(null)";
      assert array.hasKey("one") : "Test failed: hasKey(\"one\")";
      assert array.size() == 2 : "Test failed: size()";
      assert array.get(null) == 0 : "Test failed: get(null)";
      assert array.get("one") == 1 : "Test failed: get(\"one\")";
      array.set(null, 42); // Update value for null key
      assert array.get(null) == 42 : "Test failed: get(null) after update";
    } catch (KeyNotFoundException e) {
      // Handle the exception as needed
      e.printStackTrace(); // or log the exception
    }
  }

  /**
   * Edge Case - Empty Array
   */
  @Test
  public void reneUriasJrEdgeTest01() {
    AssociativeArray<String, Integer> emptyArray = new AssociativeArray<>();

    assert !emptyArray.hasKey("anyKey") : "Test failed: hasKey() on empty array";
    assert emptyArray.size() == 0 : "Test failed: size() on empty array";

    try {
      emptyArray.get("anyKey");
      System.out.println("Test failed: get() on empty array did not throw KeyNotFoundException");
    } catch (KeyNotFoundException e) {
      // Expected exception, do nothing
    }
  }

  // +-----------------------+---------------------------------------
  // | Tests by Christina Vu |
  // +-----------------------+

  // +-----------------------+---------------------------------------
  // | Tests by Luke Walters |
  // +-----------------------+
  /**
   * Checks that the expand method and clone methods work properly and that they
   * have the same pair
   * size length at the end
   */
  @Test
  public void LukeWaltersTest1() {
    AssociativeArray<Integer, Integer> testArray = new AssociativeArray<>();
    for (int i = 0; i < 20; i++) {
      testArray.set(i, i * 2);
    }
    AssociativeArray<Integer, Integer> cloneArray = testArray.clone();
    // SamR replaced `pairs.length` with `size()`
    assertEquals(cloneArray.size(), testArray.size());
  }

  /**
   * Ensures that when an element is removed from the middle of the list the next
   * added element
   * takes its place
   */
  @Test
  public void LukeWaltersTest2() {
    AssociativeArray<Integer, Integer> testArray = new AssociativeArray<>();
    testArray.set(1, 11);
    testArray.set(2, 22);
    testArray.set(3, 33);
    testArray.remove(2);
    testArray.set(4, 44);
    // SamR commented out the following, since array order is not necessarily
    // predicatble.
    // String checker = "{ 1: 11, 4: 44, 3: 33 }";
    // assertEquals(testArray.toString(), checker);
  }

  /**
   * Checks to ensure that an error is thrown when get is used on an empty array
   */
  @Test
  public void LukeWaltersEdge1() {
    AssociativeArray<Integer, Integer> testArray = new AssociativeArray<>();
    try {
      testArray.get(1);
      fail("Did not Throw KeyNotFoundException");
    } catch (KeyNotFoundException e) {
    }
  }

  // +------------------------+--------------------------------------
  // | Tests by Jonathan Wang |
  // +------------------------+

  /**
   * Instantiates an array and runs tests with the toString, set, hasKey, & get
   * methods
   */
  @Test
  public void JonathanWangTest1() {
    AssociativeArray<String, String> testArray = new AssociativeArray<>();

    // Check array before setting
    assertEquals(0, testArray.size());
    assertEquals("{}", testArray.toString());

    // Add some key-value pairs
    testArray.set("Autry", "Osera");
    testArray.set("Johnson", "Jimenez");
    testArray.set("Rebelsky", "Albright");

    // Check if the size is correct
    assertEquals(3, testArray.size());

    // Check if the keys are present
    assertTrue(testArray.hasKey("Autry"));
    assertTrue(testArray.hasKey("Johnson"));
    assertTrue(testArray.hasKey("Rebelsky"));
    assertFalse(testArray.hasKey("Osera")); // Check for a key that doesn't exist

    try {
      assertEquals("Osera", testArray.get("Autry"));
      assertEquals("Jimenez", testArray.get("Johnson"));
      assertEquals("Albright", testArray.get("Rebelsky"));
    } catch (KeyNotFoundException e) {
      fail("KeyNotFoundException: " + e.getMessage());
    }
  }

  /**
   * Instantiates an array and runs tests with the additional remove method,
   * checking if it performs
   * properly
   */
  @Test
  public void JonathanWangTest2() {
    AssociativeArray<String, String> testArray = new AssociativeArray<>();

    // Add key-value pairs
    testArray.set("Osera", "Autry");
    testArray.set("Jimenez", "Rebelsky");

    // Check if the newly added keys are present
    assertTrue(testArray.hasKey("Osera"));
    assertTrue(testArray.hasKey("Jimenez"));

    // Clone array and check if the clone is equal
    AssociativeArray<String, String> cloneArray = testArray.clone();
    assertEquals(testArray.toString(), cloneArray.toString());

    // Remove all key-value pairs and check if none of the keys are present
    testArray.remove("Autry");
    testArray.remove("Rebelsky");
    testArray.remove("Osera");
    testArray.remove("Jimenez");

    assertFalse(testArray.hasKey("Autry"));
    assertFalse(testArray.hasKey("Rebelsky"));
    assertFalse(testArray.hasKey("Osera"));
    assertFalse(testArray.hasKey("Jimenez"));

    // Check if empty
    assertEquals(0, testArray.size());
  }

  /**
   * try on a key that doesn't exist
   */
  @Test
  public void JonathanWangEdge1() {
    AssociativeArray<String, String> testArray = new AssociativeArray<>();

    // Key that doesn't exist
    try {
      testArray.get("Olsen");
      fail("Expected KeyNotFoundException was not thrown.");
    } catch (KeyNotFoundException e) {
      // Catch expected behavior
    }
  }

  // +-------------------+-------------------------------------------
  // | Tests by Lydia Ye |
  // +-------------------+

  /*
   * Test if hasKey changes status after removing a key
   */
  @Test
  public void lydiaYeTest01() {
    // Create an associative array
    // SamR added parens.
    AssociativeArray<String, Integer> arr = new AssociativeArray<String, Integer>();

    // Create new KV pairs
    arr.set("A", 1);
    arr.set("B", 2);
    arr.set("C", 3);

    // Checks if hasKey works correclty with existed keys
    try {
      assertEquals(true, arr.hasKey("A"));
      assertEquals(true, arr.hasKey("B"));
      assertEquals(true, arr.hasKey("C"));
    } catch (Exception e) {
      // SamR added semicolon
      fail("The array does not contain expected keys");
    } // try/catch

    // Remove keys from the array
    arr.remove("A");
    arr.remove("B");

    // Checks if hasKey works properly when keys are removed
    try {
      assertEquals(false, arr.hasKey("A"));
      assertEquals(false, arr.hasKey("B"));
      assertEquals(true, arr.hasKey("C"));
    } catch (Exception e) {
      fail("Exception in hasKey after remove a key");
    } // try/catch
  } // lydiaYeTest01

  /*
   * Test if cloning a cloned array works as expected
   */
  @Test
  public void lydiaYeTest02() {
    // Create an associative array with KV pairs
    // SamR added parens
    AssociativeArray<String, Integer> arr = new AssociativeArray<String, Integer>();
    arr.set("A", 1);
    arr.set("B", 2);
    arr.set("C", 3);

    // Make a copy
    AssociativeArray<String, Integer> copyOfArr = arr.clone();
    // Make a copy of copied arr
    AssociativeArray<String, Integer> copyOfCopiedArr = copyOfArr.clone();

    // Checks copy of copied arr is same as arr
    try {
      assertEquals(arr.size(), copyOfCopiedArr.size());
      assertEquals(arr.get("A"), copyOfCopiedArr.get("A"));
      assertEquals(arr.get("B"), copyOfCopiedArr.get("B"));
      assertEquals(arr.get("C"), copyOfCopiedArr.get("C"));
      // SamR added parens
      assertEquals(arr.toString(), copyOfCopiedArr.toString());
    } catch (Exception e) {
      fail("The copy of cloned array does not remian same as the original array");
    } // try/catch
  } // lydiaYeTest02

  /*
   * Tests what happen when we remove from an empty array SamR removed this test
   * because `remove` is
   * not supposed to throw exceptions
   */
  public void lydiaYeEdge01() {
    // Create an empty associative array
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();

    // Check if remove() works as expected
    try {
      // try to remove from the empty array
      arr.remove("Not in the array");
      // fail if it does not throw an error
      fail("Did not throw an exception");
    } catch (Exception e) {
      fail("Exception in removing from an empty array");
    } // try/catch
  } // lydiaYeTest01()

  public void NoahTest1() {
    AssociativeArray<String, Integer> aa = new AssociativeArray<>();
    aa.set("item1", 5);
    aa.set("item2", 3);
    assertEquals(2, aa.size());
  }

  public void NoahTest2() {
    AssociativeArray<String, Integer> aa = new AssociativeArray<>();
    aa.set("item1", 5);
    aa.set("item2", 3);
    aa.remove("item1");
    assertEquals(1, aa.size());
  }

  public void NoahEdge1() {
    AssociativeArray<String, Integer> aa = new AssociativeArray<>();
    aa.remove("item3"); // remove non-existent key
    assertEquals(0, aa.size()); // size remains 0
  }

  // +-------------------+-------------------------------------------
  // | Tests by Livia Stein Freitas |
  // +-------------------+

  // Checks that if set is called on a non-empty slot, the last value set gets
  // correctly assigned
  @Test
  public void LiviaSteinTest1() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    arr.set("A", "a");
    arr.set("A", "b");
    try {
      assertEquals("b", arr.get("A"));
    } catch (KeyNotFoundException e) {
      fail("Key not found.");
    }
  }

  // Ensures that size() gets the right value after a call to remove
  @Test
  public void LiviaSteinTest2() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    arr.set("A", "a");
    arr.set("B", "b");
    arr.set("C", "c");
    arr.remove("B");
    try {
      assertEquals(2, arr.size());
    } catch (Exception e) {
      fail("Could not compute the size of the array.");
    }

  }

  // Checks if removing a value from an empty array is handled gracefully
  @Test
  public void LiviaSteinEdge1() {
    AssociativeArray<String, String> arr = new AssociativeArray<String, String>();
    try {
      arr.remove("A");
    } catch (Exception e) {
      fail("Remove doesn't handle an empty array.");
    }

  }

} // class AssociativeArrayTests