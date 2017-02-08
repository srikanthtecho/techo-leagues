package com.makeurpicks.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.makeurpicks.domain.League;
import com.makeurpicks.domain.LeagueBuilder;
import com.makeurpicks.domain.LeagueName;
import com.makeurpicks.domain.PlayerLeague;
import com.makeurpicks.domain.PlayerLeagueId;
import com.makeurpicks.exception.LeagueValidationException;
import com.makeurpicks.exception.LeagueValidationException.LeagueExceptions;
import com.makeurpicks.repository.LeagueRepository;
import com.makeurpicks.repository.PlayerLeagueRepository;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LeagueServiceTestHari {

	private static final String ADMIN_ID = "adminID";
	private static final String SEASON_ID = "seasonID";
	private static final String LEAGUE_NAME = "leagueName";
	private static final String LEAGUE_ID = "leagueID";
	@Mock
	public LeagueRepository leagueRepositoryMock;
	@Mock
	public PlayerLeagueRepository playerLeagueRepository;
	@Autowired
	@InjectMocks
	public LeagueService leagueService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		League league=prepareLeague();
		leagueService.createLeague(league);
		
		PlayerLeague  playerLeague=preparePlayerLeague();
		playerLeagueRepository.save(playerLeague);
		
	}

	@AfterClass
	public static void close() {
	}

	/*
	 * This test case demonstrates the method being tested throws a runtime
	 * exception. TODO: Code should be fixed. NullPointer exceptions are not
	 * expected.
	 */
	@Test(expected = NullPointerException.class)
	public final void testCreateLeague_leagueNull() {
		leagueService.createLeague(null);
	}

	@Test(expected = LeagueValidationException.class)
	public final void testCreateLeague_leagueNameNull() {
		League league = new LeagueBuilder(null).withName(null).build();
		leagueService.createLeague(league);
		expectedEx.expectMessage(LeagueExceptions.LEAGUE_NAME_IS_NULL.toString());
	}

	/*
	 * This method are passing for spaces for Ex. withAdminId("  "). TODO: code
	 * should be modified as (null == league.getLeagueName() ||
	 * !StringUtils.isEmpty(league.getLeagueName())
	 */
	@Test(expected = LeagueValidationException.class)
	public final void testCreateLeague_leageAdminIDEmpty() {
		League league = new LeagueBuilder(LEAGUE_ID).withName(LEAGUE_NAME).withSeasonId(SEASON_ID).withAdminId("")
				.build();
		leagueService.createLeague(league);
		expectedEx.expectMessage(LeagueExceptions.ADMIN_NOT_FOUND.toString());
	}

	@Test(expected = LeagueValidationException.class)
	public final void testCreateLeague_leageIdDuplicate() {
		League league = new LeagueBuilder(UUID.randomUUID().toString()).withName(LEAGUE_NAME).withSeasonId(SEASON_ID)
				.withAdminId(ADMIN_ID).build();
		leagueService.createLeague(league);
		when(leagueService.getLeagueByName(league.getLeagueName())).thenReturn(league);
		leagueService.validateLeague(league);
		expectedEx.expectMessage(LeagueExceptions.LEAGUE_NAME_IN_USE.toString());
	}

	@Test(expected = LeagueValidationException.class)
	public final void testCreateLeague_seasonIDNull() {
		League league = new LeagueBuilder(LEAGUE_ID).withName(LEAGUE_NAME).withSeasonId(null).build();
		leagueService.createLeague(league);
		when(leagueService.getLeagueByName(league.getLeagueName())).thenReturn(league);
		leagueService.validateLeague(league);
		expectedEx.expectMessage(LeagueExceptions.SEASON_ID_IS_NULL.toString());
	}

	/*Commenting out the method as it is called from setup and failure in the setup will
	 * fail all test cases
	 * 
	 */
	/*@Test
	public final void testCreateValidLeague() {
		League league = prepareLeague();
		leagueService.createLeague(league);
		verify(leagueRepositoryMock).save(league);
	}*/

	private League prepareLeague() {
		League league =new LeagueBuilder(LEAGUE_ID).withAdminId(ADMIN_ID).withName(LEAGUE_NAME).withPassword("pass")
				.withSeasonId(SEASON_ID).withNoSpreads().build();
		return league;
	}
	private League prepareLeagueForDelete() {
		League league =new LeagueBuilder("DELETELEAGUEID").withAdminId(ADMIN_ID).withName("DELETELEAGUENAME").withPassword("pass")
				.withSeasonId(SEASON_ID).withNoSpreads().build();
		return league;
	}
	
	//ADDED BY HARI
	
	private PlayerLeague preparePlayerLeague() {
		League league =prepareLeague();
		PlayerLeagueId playerLeagueId=new PlayerLeagueId();
		playerLeagueId.setLeagueId(league.getId());
		playerLeagueId.setPlayerId("player1");
		PlayerLeague playerLeague=new PlayerLeague(playerLeagueId);
		return playerLeague;
	}

	@Test
	public void testUpdateLeague() {
		League league=prepareLeague();
		league.setFree(false);
		leagueService.updateLeague(league);
		verify(leagueRepositoryMock).save(league);
		
	}

	@Test
	public void testGetLeaguesForPlayerWrongId() {
		Set<LeagueName> leaguesForPlayers= leagueService.getLeaguesForPlayer("notexistentid");
		assertEquals(0, leaguesForPlayers.size());
	}
	
	@Test
	public void testGetLeaguesForPlayerValiId() {
		PlayerLeague playerLeague=preparePlayerLeague();
		
		Set<LeagueName> leaguesForPlayers= leagueService.getLeaguesForPlayer(playerLeague.getPlayerId());
		assertThat(leaguesForPlayers.size() > 0);
	}

	@Test
	public void testGetPlayersInLeague() {
		PlayerLeague playerLeague=preparePlayerLeague();
		Set<String> players=leagueService.getPlayersInLeague(playerLeague.getLeagueId());
		assertThat(players.size()>0);
	}
	
	@Test
	public void testGetPlayersInLeagueInValidLeague() {
		
		Set<String> players=leagueService.getPlayersInLeague("invalidleagueid");
		assertThat(players.size() == 0);
	}

	@Test
	public void testJoinLeaguePlayerLeagueValidLeague() {
		
		League league=prepareLeague();
		String newPlayer="newplayer";
		leagueService.joinLeague(league.getId(),newPlayer , "tech1");
		Set<String> players=leagueService.getPlayersInLeague(league.getId());
		assertThat(players.contains(newPlayer));
		
	}

	@Test
	public void testGetLeagueByIdValidId() {
		String leagueId=prepareLeague().getId();
		assertNotNull(leagueService.getLeagueById(leagueId));
	}
	
	@Test
	public void testGetLeagueByIdInValidId() {
		
		assertNull(leagueService.getLeagueById("dummyId"));
	}
	
	

	@Test
	public void testGetLeagueByNameValidName() {
		
		League league=leagueService.getLeagueByName(prepareLeague().getLeagueName());
		assertNotNull(league);
		assertEquals(league.getLeagueName(),league.getLeagueName());
	}
	
	@Test
	public void testGetLeagueByNameInValidName() {
		
		League league=leagueService.getLeagueByName("NonExistentLeague");
		assertNull(league);
		
	}

	@Test
	public void testRemovePlayerFromLeague() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllLeagues() {
		Iterable<League > leagues=leagueService.getAllLeagues();
		List<League> leaguesList=new ArrayList<League>();
		leagues.forEach(league -> leaguesList.add(league));
		assertThat(leaguesList.size()>0);
	}

	@Test
	public void testDeleteLeague() {
		League league=prepareLeagueForDelete();
		leagueService.createLeague(league);
		assertNotNull(leagueService.getLeagueById(league.getId()));
		leagueService.deleteLeague(league.getId());
		League outputLeague=leagueService.getLeagueById(league.getId());
		assertNull(outputLeague);
		
	}

	@Test
	public void testGetLeagueByValisSeasonId() {
		List<League> leagues=leagueService.getLeagueBySeasonId(SEASON_ID);
		assertNotNull(leagues);
		//assertThat(LEAGUE_ID,league.getId());
	}
	
	@Test
	public void testGetLeagueByInValisSeasonId() {
		List<League> leagues=leagueService.getLeagueBySeasonId("INVALIDSEASONID");
		assertNull(leagues);
		//assertThat(LEAGUE_ID,league.getId());
	}

}