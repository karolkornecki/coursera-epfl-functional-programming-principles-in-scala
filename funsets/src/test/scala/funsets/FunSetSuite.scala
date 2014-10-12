package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 * - run the "test" command in the SBT console
 * - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   * - test
   * - ignore
   * - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   * val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains elements existing in both sets") {
    new TestSets {
      val s_1_2 = union(s1, s2)
      val s_1_3 = union(s1, s3)
      val only_1 = intersect(s_1_2, s_1_3)
      assert(contains(only_1, 1), "Should contain only 1")
      assert(!contains(only_1, 2), "Should not contain 2")
      assert(!contains(only_1, 3), "Should not contain 3")
    }
  }

  test("diff contains elements existing only in one set") {
    new TestSets {
      val s_1_2 = union(s1, s2)
      val s_1_3 = union(s1, s3)
      val only_2 = diff(s_1_2, s_1_3)
      assert(contains(only_2, 2), "Should contain only 2")
      assert(!contains(only_2, 1), "Should not contain 1")
      assert(!contains(only_2, 3), "Should not contain 3")
    }
  }

  test("filter contains elements that fulfill required condition") {
    new TestSets {
      val s_1_2 = union(s1, s2)
      val s_1_2_3 = union(s_1_2, s3)
      //      def cond (x: Int): Boolean = x > 1
      val gt_1 = filter(s_1_2_3, x => x > 1)
      assert(contains(gt_1, 2), "only 2, 3")
      assert(contains(gt_1, 3), "only 2, 3")
      assert(!contains(gt_1, 1), "only 2, 3")
    }
  }

  test("forall") {
    new TestSets {
      val s_1_2 = union(s1, s2)
      val s_1_2_3 = union(s_1_2, s3)
      val true_ = forall(s_1_2_3, x => x < 5)
      assert(true_, "should be true")
      val false_1 = forall(s_1_2_3, x => x > 5)
      assert(!false_1, "should be false")
      val false_2 = forall(s_1_2_3, x => x > 2)
      assert(!false_2, "should be false")
    }
  }

  test("exists") {
    new TestSets {
      val s_1_2 = union(s1, s2)
      val s_1_2_3 = union(s_1_2, s3)
      val true_ = exists(s_1_2_3, x => x == 3)
      assert(true_, "should be true")
      val false_ = exists(s_1_2_3, x => x == 5)
      assert(!false_, "should be false")
    }
  }

  test("map") {
    new TestSets {
      val s_1_2 = union(s1, s2)
      val s_1_2_3 = union(s_1_2, s3)
      val new_set = map(s_1_2_3, x => x + 10)
      assert(contains(new_set,11), "should contains 11")
      assert(contains(new_set,12), "should contains 12")
      assert(contains(new_set,13), "should contains 13")
    }
  }
}
