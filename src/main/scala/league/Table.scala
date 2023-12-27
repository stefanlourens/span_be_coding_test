package league

import league.Table.*

import java.io.File
import scala.annotation.tailrec
import scala.io.Source
import scala.math.Ordering
import scala.util.Properties.lineSeparator
import scala.util.{Try, Using}

private type TeamRanking = (Int, Team, Points)

case class Table(matches: Iterable[Match]):
  /**
   * Collects the sum of the points awarded by team, orders it by points, team name
   * and projects the ranking onto the result
   */
  private[league] lazy val rankings: Seq[TeamRanking] =
    matches
      .flatMap(_.teamPoints)
      .groupMapReduce[Team, Points](_.team)(_.value)(_ + _)
      .toSeq
      .sorted(TeamPointsOrdering)
      .ranked

  override def toString: String =
    rankings.map: (rank, team, pts) =>
      s"$rank. $team, $pts ${if pts == 1 then "pt" else "pts"}"
    .mkString(lineSeparator)

object Table:

  /**
   * Custom ordering used to sort by Team name alphabetically (case insensitive)
   * if the points are the same, desc by points otherwise
   */
  private val TeamPointsOrdering: Ordering[TeamPoints] =
    (x: TeamPoints, y: TeamPoints) =>
      if x.value == y.value then x.team.compare(y.team)
      else Ordering.Int.reversed().compare(x.value, y.value)

  def from(file: File): Try[Table] =
    Using(Source.fromFile(file)): source =>
      Table:
        source
          .getLines()
          .filter(_.trim.nonEmpty)
          .map(Match.from)
          .toSeq

  extension (teamPoints: Seq[TeamPoints])
    /**
     * Applies the ranking to the already sorted Seq[TeamPoints].
     * Teams with the same points have the same ranking, but use up the
     * ranking ordinal eg rank 3 skips to 5:
     *
     *  1. Tarantulas, 6 pts
     *  2. Lions, 5 pts
     *  3. FC Awesome, 1 pt
     *  3. Snakes, 1 pt
     *  5. Grouches, 0 pts
     *
     * @return Seq[TeamPoints] with the Ranking applied Ranking -> Team -> Points
     */
    private def ranked: Seq[TeamRanking] =
      @tailrec
      def rank(teamPoints: Seq[TeamPoints], currentRank: Int, ranksSkipped: Int,
        prev: Option[TeamPoints], acc: Seq[TeamRanking]): Seq[TeamRanking] =
        teamPoints.headOption match
          case Some(head) =>
            val (ranking: Int, skipped: Int) =
              if prev.exists(_.value == head.value) then (currentRank, ranksSkipped + 1)
              else (currentRank + ranksSkipped + 1, 0)
            rank(teamPoints.tail, ranking, skipped, Some(head), acc :+ (ranking, head.team, head.value))
          case None => acc

      rank(teamPoints, 0, 0, None, Vector.empty)
