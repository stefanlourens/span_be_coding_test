package league

import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*

class MatchSpec extends AnyFlatSpec with should.Matchers:

  "Score" should "error on negative input" in :
    assertThrows[IllegalArgumentException]:
      Score("-1")

  "Match" should "error if a team is playing itself" in :
    assertThrows[IllegalArgumentException]:
      Match(Team("A") -> 2, Team("a") -> 1)

  it should "award points correctly" in:
    val a = Team("A")
    val b = Team("B")
    val homeWin = Match(a -> 1, b -> 0)
    val awayWin = Match(a -> 0, b -> 1)
    val draw = Match(a -> 0, b -> 0)

    homeWin.teamPoints should contain allOf(a -> 3, b -> 0)
    awayWin.teamPoints should contain allOf(a -> 0, b -> 3)
    draw.teamPoints should contain allOf(a -> 1, b -> 1)

