package AdventOfCode
package y2018

import scala.annotation.tailrec

object Day10 {

  val input = """position=< 9,  1> velocity=< 0,  2>
                |position=< 7,  0> velocity=<-1,  0>
                |position=< 3, -2> velocity=<-1,  1>
                |position=< 6, 10> velocity=<-2, -1>
                |position=< 2, -4> velocity=< 2,  2>
                |position=<-6, 10> velocity=< 2, -2>
                |position=< 1,  8> velocity=< 1, -1>
                |position=< 1,  7> velocity=< 1,  0>
                |position=<-3, 11> velocity=< 1, -2>
                |position=< 7,  6> velocity=<-1, -1>
                |position=<-2,  3> velocity=< 1,  0>
                |position=<-4,  3> velocity=< 2,  0>
                |position=<10, -3> velocity=<-1,  1>
                |position=< 5, 11> velocity=< 1, -2>
                |position=< 4,  7> velocity=< 0, -1>
                |position=< 8, -2> velocity=< 0,  1>
                |position=<15,  0> velocity=<-2,  0>
                |position=< 1,  6> velocity=< 1,  0>
                |position=< 8,  9> velocity=< 0, -1>
                |position=< 3,  3> velocity=<-1,  1>
                |position=< 0,  5> velocity=< 0, -1>
                |position=<-2,  2> velocity=< 2,  0>
                |position=< 5, -2> velocity=< 1,  2>
                |position=< 1,  4> velocity=< 2,  1>
                |position=<-2,  7> velocity=< 2, -2>
                |position=< 3,  6> velocity=<-1, -1>
                |position=< 5,  0> velocity=< 1,  0>
                |position=<-6,  0> velocity=< 2,  0>
                |position=< 5,  9> velocity=< 1, -2>
                |position=<14,  7> velocity=<-2,  0>
                |position=<-3,  6> velocity=< 2, -1>""".stripMargin

  case class Point(x: Long, y: Long, vx: Long, vy: Long)
  case class Area(x1: Long, x2: Long, y1: Long, y2: Long)
  object Area {
    def apply(points: List[Point]): Area = Area(
      points.map(_.x).min,
      points.map(_.x).max,
      points.map(_.y).min,
      points.map(_.y).max
    )
  }
  implicit class AreaOps(private val a: Area) extends AnyVal {
    def area: Long = (a.x2 - a.x1) * (a.y2 - a.y1)
  }

  def parse(input: String): List[Point] =
    input.linesIterator.map { line =>
      "-?\\d+".r.findAllIn(line).map(_.toLong).toList match {
        case x :: y :: vx :: vy :: Nil => Point(x, y, vx, vy)
      }
    }.toList

  def solve(input: String): Unit = {
    def show(points: List[Point]): String = {
      val area = Area(points)
      val coords = points.map { p =>
        p.x -> p.y
      }.toSet
      (area.y1 to area.y2)
        .map { y =>
          (area.x1 to area.x2).map { x =>
            if (coords contains (x -> y))
              "X"
            else
              " "
          }.mkString
        }
        .mkString("\n")
    }

    @tailrec def solve0(points: List[Point], time: Int, area: Long): Unit = {
      val nextPoints = points.map { point =>
        point.copy(x = point.x + point.vx, y = point.y + point.vy)
      }
      val nextArea = Area(nextPoints).area
      if (area > nextArea)
        solve0(nextPoints, time + 1, nextArea)
      else
        println(s"time: $time, area:\n${show(points)}")
    }

    val pts = parse(input)
    solve0(pts, 0, Area(pts).area)
  }

}
