package league

import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*

import scala.util.Properties.lineSeparator

class TableSpec extends AnyFlatSpec with should.Matchers:

  "Table" should "calculate rankings correctly" in :
    val table = Table {
      Seq(
        Match(Team("A") -> 1, Team("B") -> 0),
        Match(Team("B") -> 1, Team("A") -> 0),
        Match(Team("C") -> 1, Team("D") -> 0),
        Match(Team("A") -> 1, Team("D") -> 0),
        Match(Team("B") -> 1, Team("D") -> 0)
      )
    }

    table.rankings should contain inOrder(
      (1, Team("A"), 6),
      (1, Team("B"), 6),
      (3, Team("C"), 3),
      (4, Team("D"), 0)
    )


  it should "format toString correctly" in :
    val table = Table {
      Seq(
        Match(Team("Lions") -> 3, Team("Snakes") -> 3),
        Match(Team("Tarantulas") -> 1, Team("FC Awesome") -> 0),
        Match(Team("Lions") -> 1, Team("FC Awesome") -> 1),
        Match(Team("Tarantulas") -> 3, Team("Snakes") -> 1),
        Match(Team("Lions") -> 3, Team("Grouches") -> 1)
      )
    }

    table.toString.split(lineSeparator) should contain inOrder(
      "1. Tarantulas, 6 pts",
      "2. Lions, 5 pts",
      "3. FC Awesome, 1 pt",
      "3. Snakes, 1 pt",
      "5. Grouches, 0 pts"
    )