package aoc.y2019

object Day9
  def runProgram(opCodes: List[Long], inputs: LazyList[Long]): LazyList[Long] = 
    case class ProgramState(pointer: Int, opCodes: List[Long], relativeBase: Int, inputs: LazyList[Long], output: Option[Long])
    LazyList.iterate(ProgramState(0, opCodes, 0, inputs, None)) { case state @ ProgramState(pointer, opCodes, relativeBase, inputs, _) =>
      val instruction = opCodes(pointer)
      val (op, param1, param2, param3) = (instruction % 100, instruction / 100 % 10, instruction / 1000 % 10, instruction / 10000 % 10)

      def getPointer(param: Long, i: Int): Int = 
        val arg = opCodes(pointer + i).toInt
        param match
          case 0L | 1L => arg
          case 2L => relativeBase + arg
          case _ => ???
      def getValue(param: Long, i: Int): Long = 
        param match
          case 1L => opCodes(pointer + i)
          case _ => opCodes(getPointer(param, i))
      def xP = getPointer(param1, 1)
      def xV = getValue(param1, 1)
      def yP = getPointer(param2, 2)
      def yV = getValue(param2, 2)
      def zP = getPointer(param3, 3)
      def zV = getValue(param3, 3)

      op match
        case 1L => state.copy(pointer + 4, opCodes.updated(zP, xV + yV), output = None)
        case 2L => state.copy(pointer + 4, opCodes.updated(zP, xV * yV), output = None)
        case 3L => state.copy(pointer + 2, opCodes.updated(xP, inputs.head), inputs = inputs.tail, output = None)
        case 4L => state.copy(pointer + 2, output = Some(xV))
        case 5L => state.copy(if xV != 0L then yV.toInt else pointer + 3, output = None)
        case 6L => state.copy(if xV == 0L then yV.toInt else pointer + 3, output = None)
        case 7L => state.copy(pointer + 4, opCodes.updated(zP, if xV < yV then 1L else 0L), output = None)
        case 8L => state.copy(pointer + 4, opCodes.updated(zP, if xV == yV then 1L else 0L), output = None)
        case 9L => state.copy(pointer + 2, relativeBase = relativeBase + xV.toInt, output = None)
        case 99L => ProgramState(-1, Nil, -1, inputs, None)
        case _ => ???
    }
      .takeWhile(_.pointer >= 0)
      .collect { case ProgramState(_, _, _, _, Some(output)) => output }
    

  def solve(input: String): String = 
    val fill = List.fill(100000)(0L)
    runProgram(input.split(",").map(_.toLong).toList ++ fill, LazyList(1L)).mkString(",")
    

  def solve2(input: String): String = 
    val fill = List.fill(100000)(0L)
    runProgram(input.split(",").map(_.toLong).toList ++ fill, LazyList(2L)).mkString(",")
  
  val input = "1102,34463338,34463338,63,1007,63,34463338,63,1005,63,53,1101,3,0,1000,109,988,209,12,9,1000,209,6,209,3,203,0,1008,1000,1,63,1005,63,65,1008,1000,2,63,1005,63,902,1008,1000,0,63,1005,63,58,4,25,104,0,99,4,0,104,0,99,4,17,104,0,99,0,0,1101,26,0,1015,1101,29,0,1010,1102,1,24,1013,1102,1,33,1008,1102,36,1,1012,1101,0,572,1023,1101,35,0,1014,1101,0,38,1019,1102,1,30,1006,1101,0,890,1029,1101,34,0,1011,1101,28,0,1002,1102,1,1,1021,1101,0,37,1001,1101,0,197,1026,1101,22,0,1017,1102,1,895,1028,1101,0,20,1007,1102,21,1,1004,1102,1,39,1016,1101,0,0,1020,1102,1,190,1027,1101,0,775,1024,1102,31,1,1018,1101,0,23,1003,1101,0,25,1009,1101,770,0,1025,1101,0,27,1000,1102,1,575,1022,1101,0,32,1005,109,27,2106,0,0,1001,64,1,64,1106,0,199,4,187,1002,64,2,64,109,-18,21101,40,0,5,1008,1014,39,63,1005,63,219,1106,0,225,4,205,1001,64,1,64,1002,64,2,64,109,-6,1201,-1,0,63,1008,63,28,63,1005,63,251,4,231,1001,64,1,64,1105,1,251,1002,64,2,64,109,5,21102,41,1,3,1008,1011,38,63,1005,63,271,1105,1,277,4,257,1001,64,1,64,1002,64,2,64,109,-7,2102,1,1,63,1008,63,28,63,1005,63,299,4,283,1106,0,303,1001,64,1,64,1002,64,2,64,109,-7,1207,10,22,63,1005,63,321,4,309,1106,0,325,1001,64,1,64,1002,64,2,64,109,16,2107,31,-4,63,1005,63,345,1001,64,1,64,1105,1,347,4,331,1002,64,2,64,109,-9,1201,3,0,63,1008,63,18,63,1005,63,371,1001,64,1,64,1106,0,373,4,353,1002,64,2,64,109,7,1202,-7,1,63,1008,63,40,63,1005,63,393,1106,0,399,4,379,1001,64,1,64,1002,64,2,64,109,-5,1208,5,33,63,1005,63,417,4,405,1106,0,421,1001,64,1,64,1002,64,2,64,109,1,1202,2,1,63,1008,63,30,63,1005,63,443,4,427,1105,1,447,1001,64,1,64,1002,64,2,64,109,-7,2102,1,10,63,1008,63,19,63,1005,63,471,1001,64,1,64,1105,1,473,4,453,1002,64,2,64,109,6,2108,21,0,63,1005,63,489,1105,1,495,4,479,1001,64,1,64,1002,64,2,64,109,9,21108,42,42,0,1005,1012,513,4,501,1105,1,517,1001,64,1,64,1002,64,2,64,109,7,21107,43,44,-1,1005,1018,535,4,523,1106,0,539,1001,64,1,64,1002,64,2,64,109,-5,21101,44,0,2,1008,1016,44,63,1005,63,561,4,545,1105,1,565,1001,64,1,64,1002,64,2,64,2105,1,9,1106,0,581,4,569,1001,64,1,64,1002,64,2,64,109,13,21107,45,44,-9,1005,1018,597,1105,1,603,4,587,1001,64,1,64,1002,64,2,64,109,-25,2101,0,3,63,1008,63,32,63,1005,63,625,4,609,1105,1,629,1001,64,1,64,1002,64,2,64,109,7,1208,-7,30,63,1005,63,645,1105,1,651,4,635,1001,64,1,64,1002,64,2,64,109,-2,21102,46,1,9,1008,1016,46,63,1005,63,677,4,657,1001,64,1,64,1106,0,677,1002,64,2,64,109,-2,21108,47,48,9,1005,1014,697,1001,64,1,64,1105,1,699,4,683,1002,64,2,64,109,14,1205,2,713,4,705,1105,1,717,1001,64,1,64,1002,64,2,64,109,-7,1206,8,735,4,723,1001,64,1,64,1106,0,735,1002,64,2,64,109,-18,2101,0,6,63,1008,63,24,63,1005,63,759,1001,64,1,64,1106,0,761,4,741,1002,64,2,64,109,29,2105,1,1,4,767,1106,0,779,1001,64,1,64,1002,64,2,64,109,-5,1206,3,791,1106,0,797,4,785,1001,64,1,64,1002,64,2,64,109,-12,2107,31,-1,63,1005,63,819,4,803,1001,64,1,64,1105,1,819,1002,64,2,64,109,7,1205,7,835,1001,64,1,64,1105,1,837,4,825,1002,64,2,64,109,-11,1207,7,24,63,1005,63,853,1106,0,859,4,843,1001,64,1,64,1002,64,2,64,109,4,2108,27,-6,63,1005,63,881,4,865,1001,64,1,64,1106,0,881,1002,64,2,64,109,24,2106,0,-2,4,887,1106,0,899,1001,64,1,64,4,64,99,21102,27,1,1,21101,0,913,0,1106,0,920,21201,1,61934,1,204,1,99,109,3,1207,-2,3,63,1005,63,962,21201,-2,-1,1,21101,0,940,0,1106,0,920,21202,1,1,-1,21201,-2,-3,1,21101,0,955,0,1105,1,920,22201,1,-1,-2,1105,1,966,22102,1,-2,-2,109,-3,2105,1,0"