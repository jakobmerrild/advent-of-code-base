package app

import com.monovore.decline.effect.CommandIOApp
import cats.syntax.all._
import cats.effect.ExitCode
import cats.effect.IO
import com.monovore.decline.Opts
import cats.data.Reader.apply
import days.Puzzle

object App
    extends CommandIOApp(
      "advent-of-code",
      "Run advent of code solutions",
      true,
      "0.0.0"
    ) {

  override def main: Opts[IO[ExitCode]] =
    AppOpts.solve.map(solve)

  def solve(solve: AppOpts.Solve): IO[ExitCode] =
    input.Reader.getInputForDay(solve).flatMap { input =>
      val puzzle = solve.day match {
        case _ =>
          IO.raiseError[Puzzle](new Exception(s"Unsupported day: ${solve.day}"))
      }

      puzzle
        .flatMap { p =>
          IO(println(s"First:\n${p.solve}")) *>
            IO(println(s"Second:\n${p.solve2}"))
        }
        .as(ExitCode.Success)
    }
}
