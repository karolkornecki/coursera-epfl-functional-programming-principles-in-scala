package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {

  trait TestTrees {
    val t1 = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
    val t2 = Fork(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), Leaf('d', 4), List('a', 'b', 'd'), 9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("times") {
    new TestTrees {
      val pairs = times(List('a', 'b', 'c', 'a', 'a'))
      assert(pairs.size == 3)
      assert(pairs.exists(pair => pair._1 == 'a' && pair._2 == 3))
    }
  }

  test("makeOrderedLeafList") {
    new TestTrees {
      val pairs = times(List('a', 'b', 'c', 'a', 'a', 'd', 'd', 'd', 'd'))
      val ordered = makeOrderedLeafList(pairs)
      assert(ordered.size == 4)
      assert(ordered(3).char == 'd')
      assert(ordered(3).weight == 4)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a', 'b', 'd'))
    }
  }

  test("createCodeTree") {
    new TestTrees {
      val tt1 = createCodeTree("aabbb".toList);
      val tt2 = createCodeTree("aabbbdddd".toList)
      assert(t1 === tt1)
      assert(t2 === tt2)
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
  }

  test("decode"){
    val secret = decodedSecret;
    assert(secret === List('h','u','f','f','m','a','n','e','s','t','c','o','o','l'))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("decode and quick encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, quickEncode(t1)("ab".toList)) === "ab".toList)
    }
  }
}
