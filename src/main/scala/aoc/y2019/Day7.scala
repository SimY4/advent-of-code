package aoc.y2019

import scala.language.implicitConversions

object Day7 with
  import Day5._

  def solve(input: String): Int = 
    val opCodes = input.split(",").map(_.toInt).toList
    (for
      amplifier <- (0 to 4).permutations
      out1      <- runProgram(opCodes, LazyList(amplifier(0), 0)).headOption
      out2      <- runProgram(opCodes, LazyList(amplifier(1), out1)).headOption
      out3      <- runProgram(opCodes, LazyList(amplifier(2), out2)).headOption
      out4      <- runProgram(opCodes, LazyList(amplifier(3), out3)).headOption
      out5      <- runProgram(opCodes, LazyList(amplifier(4), out4)).headOption
    yield out5).max
    
  def solve2(input: String): Int = 
    val opCodes = input.split(",").map(_.toInt).toList
    (for
      amplifier <- (5 to 9).permutations
    yield {
      def out1: LazyList[Int] = runProgram(opCodes, amplifier(0) #:: 0 #:: out5)
      def out2: LazyList[Int] = runProgram(opCodes, amplifier(1) #:: out1)
      def out3: LazyList[Int] = runProgram(opCodes, amplifier(2) #:: out2)
      def out4: LazyList[Int] = runProgram(opCodes, amplifier(3) #:: out3)
      def out5: LazyList[Int] = runProgram(opCodes, amplifier(4) #:: out4)
      out5.last
    }).max

  val input = "3,8,1001,8,10,8,105,1,0,0,21,30,55,80,101,118,199,280,361,442,99999,3,9,101,4,9,9,4,9,99,3,9,101,4,9,9,1002,9,4,9,101,4,9,9,1002,9,5,9,1001,9,2,9,4,9,99,3,9,101,5,9,9,1002,9,2,9,101,3,9,9,102,4,9,9,1001,9,2,9,4,9,99,3,9,102,2,9,9,101,5,9,9,102,3,9,9,101,3,9,9,4,9,99,3,9,1001,9,2,9,102,4,9,9,1001,9,3,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,99"