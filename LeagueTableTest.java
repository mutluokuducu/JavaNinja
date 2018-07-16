import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeagueTableTest {

    private LeagueTable leagueTable;

    @Test
    void orderLeagueTable() {
        /*Arrange*/
        /*Act*/
        LeagueTableEntry leagueTableFirstEntry = leagueTable.getTableEntries().get(0);

        /*Assert*/
        assertNotNull(leagueTableFirstEntry, "Team League Table should not be null");
        assertEquals(leagueTableFirstEntry.getPoints(), 4);
        assertEquals(leagueTableFirstEntry.getGoalDifference(), 2);
        assertEquals(leagueTableFirstEntry.getGoalDifference(), 2);
        assertEquals(leagueTableFirstEntry.getGoalsFor(), 4);
        assertEquals(leagueTableFirstEntry.getTeamName(), "Chelsea");
    }


    @Test
    void insertHomeTeamToLeagueTable() {
        /*Arrange*/
        Match matchTestHome = new Match("Arsenal", "Chelsea", 3, 2);
        /*Act*/
        leagueTable.insertLeagueTable(matchTestHome, "Arsenal");

        LeagueTableEntry leagueTableEntry = leagueTable.leagueTableEntries.stream()
                .filter(x -> "Arsenal".equals(x.getTeamName()))
                .findAny()
                .orElse(null);
        /*Assert*/
        assertNotNull(leagueTableEntry, "HomeTeam couldn’t  insert");

    }

    @Test
    void insertAwayTeamLeagueTable() {
        /*Arrange*/
        Match matchTestAway = new Match("Arsenal", "Chelsea", 3, 2);
        /*Act*/
        leagueTable.insertLeagueTable(matchTestAway, "Chelsea");

        LeagueTableEntry leagueTableEntry = leagueTable.leagueTableEntries.stream()
                .filter(x -> "Chelsea".equals(x.getTeamName()))
                .findAny()
                .orElse(null);
        /*Assert*/
        assertNotNull(leagueTableEntry, "AwayTeam couldn’t  insert");

    }


    @Test
    void updateHomeTeamToLeagueTable() {
        /*Arrange*/
        Match matchTestHome = new Match("Everton", "Newcastle", 4, 2);

        /*Act*/
        LeagueTableEntry leagueTableEntryResult = leagueTable.leagueTableEntries.stream()
                .filter(x -> "Everton".equals(x.getTeamName()))
                .findAny()
                .orElse(null);
        leagueTable.updateLeagueTable(leagueTableEntryResult, matchTestHome, "Everton");


        /*Assert*/
        assertNotNull(leagueTableEntryResult, "HomeTeam shouldn't not be null");
        assertEquals(leagueTableEntryResult.getPoints(), 3);

    }

    @Test
    void updateAwayTeamToLeagueTable() {
        /*Arrange*/
        Match matchTestHome = new Match("Everton", "Newcastle", 4, 2);

        /*Act*/
        LeagueTableEntry leagueTableEntryResult = leagueTable.leagueTableEntries.stream()
                .filter(x -> "Newcastle".equals(x.getTeamName()))
                .findAny()
                .orElse(null);
        leagueTable.updateLeagueTable(leagueTableEntryResult, matchTestHome, "Newcastle");



        /*Assert*/
        assertNotNull(leagueTableEntryResult, "AwayTeam shouldn't be null");
        assertEquals(leagueTableEntryResult.getPoints(), 3);


    }

    @BeforeEach
    void setUp() {

        List<Match> matches = new ArrayList<>();

        Match firstMatch = new Match("Everton", "Chelsea", 1, 3);
        Match secondMatch = new Match("Liverpool", "M.United", 0, 1);
        Match thirdMatch = new Match("Tottenham", "Newcastle", 1, 3);
        Match fourthMatch = new Match("Liverpool", "Chelsea", 1, 1);
        Match fifthMatch = new Match("M.City", "Tottenham", 4, 1);

        matches.add(firstMatch);
        matches.add(secondMatch);
        matches.add(thirdMatch);
        matches.add(fourthMatch);
        matches.add(fifthMatch);

        leagueTable = new LeagueTable(matches);
    }
}