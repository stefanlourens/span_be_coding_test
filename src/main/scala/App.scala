import league.Table

import java.io.File
import scala.util.{Failure, Success}

object App:

  private def error(msg: String): Unit =
    System.err.println(msg)
    System.exit(1)

  def main(args: Array[String]): Unit = args.headOption.map(File(_)) match {
    case Some(inputFile) =>
      if inputFile.exists() then
        Table.from(inputFile) match
          case Success(table) => println(table)
          case Failure(ex) =>
            error:
              s"""Unable to parse file `${inputFile.getAbsolutePath}`:
                 | ${ex.getMessage}
                 |""".stripMargin
      else error(s"File not found: '${inputFile.getAbsolutePath}'")
    case _ => error("Usage: java -jar print-table.jar PATH/TO/INPUT_FILE")
  }

