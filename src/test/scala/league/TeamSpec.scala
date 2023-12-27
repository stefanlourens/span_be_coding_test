package league

import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*

class TeamSpec extends AnyFlatSpec with should.Matchers:

  "Team" should "error on null or empty input" in :
    assertThrows[IllegalArgumentException]:
      Team("  ")

    assertThrows[IllegalArgumentException]:
      Team(null)

  it should "ignore case differences for sorting" in :
    val teams = Seq("A", "b", "C", "d").map(Team.apply)
    teams.sorted should contain theSameElementsInOrderAs (teams)

  it should "ignore case differences for grouping" in :
    val teams = Seq("Team A", "team A", "team a", "TEAM A").map(Team.apply)
    val grouped: Map[Team, Seq[Team]] = teams.groupBy(identity)

    grouped should have size 1
    grouped.keys should contain allElementsOf (teams)


