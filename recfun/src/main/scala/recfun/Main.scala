package recfun

import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int =
    if (c == 0 || r == 0 || r == c) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def innerBalance(b: Int, head: Char, rest: List[Char]): Int = {
      if (b < 0) -1
      else
      if (rest.isEmpty) {
        if (head == '(') b + 1
        else if (head == ')') b - 1
        else b
      } else {
        if (head == '(')
          innerBalance(b + 1, rest.head, rest.tail)
        else if (head == ')')
          innerBalance(b - 1, rest.head, rest.tail)
        else {
          innerBalance(b, rest.head, rest.tail)
        }
      }
    }
    if (chars.isEmpty) true
    else innerBalance(0, chars.head, chars.tail) == 0

  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0) 1
    else if (money < 0) 0
    else if (coins.isEmpty && money != 0) 0
    else countChange(money, coins.tail) + countChange(money - coins.head, coins)
  }
}
