import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LeagueTable {

    public List<LeagueTableEntry> leagueTableEntries;
    private List<Match> matches = new ArrayList<>();


    public LeagueTable(final List<Match> matches) {

        this.matches = matches;
        this.leagueTableEntries = new ArrayList<>();
        calculateResults();

    }

    /**
     * Get the ordered list of league table entries for this league table.
     *
     * @return
     */
    public List<LeagueTableEntry> getTableEntries() {

        orderLeaugeTable();

        return leagueTableEntries;
    }

    /**
     * LeagueTableEntry objects are sorted by points, goal difference, goals for and then
     * team names.
     * Stream sorted or sort method.
     */

    protected void orderLeaugeTable() {

/*
        List<LeagueTableEntry> orderedLeague = this.leagueTableEntries.stream()
                .sorted(Comparator.comparing(LeagueTableEntry::getPoints).reversed())
                .sorted(Comparator.comparing(LeagueTableEntry::getGoalDifference).reversed())
                .sorted(Comparator.comparing(LeagueTableEntry::getGoalsFor).reversed())
                .sorted(Comparator.comparing(LeagueTableEntry::getTeamName).reversed()).collect(Collectors.toList());

        return orderedLeague;
        */


        this.leagueTableEntries.sort(
                Comparator.comparing(LeagueTableEntry::getPoints).reversed()
                        .thenComparing(Comparator.comparing(LeagueTableEntry::getGoalDifference).reversed()
                                .thenComparing(Comparator.comparing(LeagueTableEntry::getGoalsFor).reversed()
                                        .thenComparing(Comparator.comparing(LeagueTableEntry::getTeamName).reversed()))));


    }

    protected void calculateResults() {


        for (Match match : matches) {

            playMatch(match);
        }
    }

    protected void playMatch(Match match) {
        ProcessMatch(match, match.getHomeTeam());
        ProcessMatch(match, match.getAwayTeam());
    }

    private void ProcessMatch(Match match, String teamName) {
        LeagueTableEntry leagueTableEntry = leagueTableEntries.stream()
                .filter(m -> teamName.equals(m.getTeamName()))
                .findAny()
                .orElse(null);


        if (leagueTableEntry != null) {
            updateLeagueTable(leagueTableEntry, match, teamName);
        } else {
            insertLeagueTable(match, teamName);
        }

    }

    public void insertLeagueTable(Match match, String teamName) {
        LeagueTableEntry entry = generateLeagueTableEntryEntity(match, teamName);
        this.leagueTableEntries.add(entry);
    }

    public void updateLeagueTable(LeagueTableEntry updateEntry, Match match, String teamName) {

        LeagueTableEntry entry = generateLeagueTableEntryEntity(match, teamName);

        updateEntry.setWon(updateEntry.getWon() + entry.getWon());
        updateEntry.setLost(updateEntry.getLost() + entry.getLost());
        updateEntry.setGoalsFor(updateEntry.getGoalsFor() + entry.getGoalsFor());
        updateEntry.setGoalsAgainst(updateEntry.getGoalsAgainst() + entry.getGoalsAgainst());
        updateEntry.setGoalDifference(updateEntry.getGoalDifference() + entry.getGoalDifference());

        updateEntry.setPlayed(updateEntry.getPlayed() + entry.getPlayed());
        updateEntry.setDrawn(updateEntry.getDrawn() + entry.getDrawn());
        updateEntry.setPoints(updateEntry.getPoints() + entry.getPoints());

    }

    /**
     * Populates LeagueTableEntry Entity accordint to match
     *
     * @param match
     * @param teamName Name of team to create relevant LeagueTableEntry
     * @return
     */
    private LeagueTableEntry generateLeagueTableEntryEntity(Match match, String teamName) {

        LeagueTableEntry entry = new LeagueTableEntry();

        boolean isHomeTeam = (match.getHomeTeam() == teamName) ? true : false;
        if (isHomeTeam) {
            entry.setTeamName(match.getHomeTeam());
            entry.setWon((match.getHomeScore() > match.getAwayScore()) ? 1 : 0);
            entry.setLost((match.getHomeScore() < match.getAwayScore()) ? 1 : 0);
            entry.setGoalsFor(match.getHomeScore());
            entry.setGoalsAgainst(match.getAwayScore());
            entry.setGoalDifference(match.getHomeScore() - match.getAwayScore());
        } else {
            entry.setTeamName(match.getAwayTeam());
            entry.setWon((match.getHomeScore() < match.getAwayScore()) ? 1 : 0);
            entry.setLost((match.getHomeScore() > match.getAwayScore()) ? 1 : 0);
            entry.setGoalsFor(match.getAwayScore());
            entry.setGoalsAgainst(match.getHomeScore());
            entry.setGoalDifference(match.getAwayScore() - match.getHomeScore());
        }
        entry.setPlayed(1);
        entry.setDrawn((match.getHomeScore() == match.getAwayScore()) ? 1 : 0);
        entry.setPoints(calculatePoint(match, isHomeTeam));

        return entry;

    }


    /**
     * @param match
     * @param isHomeTeam
     * @return
     */
    private int calculatePoint(Match match, boolean isHomeTeam) {
        int score = 0;

        if (match.getHomeScore() == match.getAwayScore()) {
            score = 1;
        } else {
            if (isHomeTeam) {
                score = (match.getHomeScore() > match.getAwayScore()) ? 3 : 0;
            } else {
                score = (match.getAwayScore()) > match.getHomeScore() ? 3 : 0;
            }
        }

        return score;
    }

}
