package league

import Match.*

private type Score = Int
private type Points = Int
private type TeamScore = (Team, Score)
private type TeamPoints = (Team, Points)

object Score:
  def apply(s: String): Score =
    s.toInt match
      case n if n >= 0 => n
      case _ => throw new IllegalArgumentException(s"Invalid score $s")

class Match private[league](home: TeamScore, away: TeamScore):
  require(home.team != away.team, s"Team can't play itself: $home, $away")

  /**
   * Denotes a Seq containing the teams involved in the Match
   * and the points awarded based on the result
   */
  lazy val teamPoints: Seq[TeamPoints] =
    if home.value > away.value then Seq((home.team, Win), (away.team, Loss))
    else if home.value < away.value then Seq((home.team, Loss), (away.team, Win))
    else Seq((home.team, Draw), (away.team, Draw))

object Match:
  private inline val Win = 3
  private inline val Draw = 1
  private inline val Loss = 0

  private val MatchPattern = raw"(.+)\s+(\d+)\s?,\s?(.+)\s+(\d+)".r

  private[league] def from(csvLine: String): Match =
    csvLine match
      case MatchPattern(homeTeam, homeScore, awayTeam, awayScore) =>
        Match(Team(homeTeam) -> Score(homeScore), Team(awayTeam) -> Score(awayScore))
      case _ => throw new RuntimeException(s"Invalid line format: '$csvLine', expected: 'TEAM_A POSITIVE_INT, TEAM_B POSITIVE_INT'")
