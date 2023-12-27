package league

/**
 * Case class representing a team, case is ignored in the provided name
 * for equality, grouping and sorting
 *
 * @param name
 */
private[league] case class Team private(name: String) extends Ordered[Team]:
  override def hashCode(): Int =
    name.toLowerCase().hashCode()

  override def equals(other: Any): Boolean =
    other match
      case other: Team => name.equalsIgnoreCase(other.name)
      case _ => false

  override def compare(that: Team): Int =
    name.toLowerCase().compare(that.name.toLowerCase())

  override def toString: String = name

private[league] object Team:
  def apply(team: String): Team =
    require(Option(team).exists(_.trim.nonEmpty), "Team can't be null or empty")
    new Team(team.trim.capitalize)

extension [A](ts: (Team, A))
  private[league] def team: Team = ts._1
  private[league] def value: A = ts._2