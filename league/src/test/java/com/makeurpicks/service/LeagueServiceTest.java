package com.makeurpicks.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;

import com.makeurpicks.LeagueApplication;
import com.makeurpicks.domain.League;
import com.makeurpicks.domain.PlayerLeague;
import com.makeurpicks.domain.PlayerLeagueId;
import com.makeurpicks.exception.LeagueValidationException;
import com.makeurpicks.exception.LeagueValidationException.LeagueExceptions;
import com.makeurpicks.repository.LeagueRepository;
import com.makeurpicks.repository.PlayerLeagueRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = LeagueApplication.class)
public class LeagueServiceTest {

	@Mock
	public LeagueRepository leagueRepositoryMock;

	@Mock
	public PlayerLeagueRepository playerLeagueRepositoryMock;

	@InjectMocks
	public LeagueService leagueService;

	@Rule
	public ExpectedException expectedExp = ExpectedException.none();

	private League generateLeague() {
		League league = new League();
		league.setId(UUID.randomUUID().toString());
		league.setLeagueName("aa");
		league.setSeasonId("aa");
		league.setAdminId("aaa");
		league.setPassword("aa");
		return league;
	}

	@Test(expected = NullPointerException.class)
	public void createLeagueTest_leagueNull() {
		leagueService.createLeague(null);
	}

	@Test(expected = LeagueValidationException.class)
	public void createLeagueTest_LeagueNameNull() {
		League league = new League();
		league.setId(UUID.randomUUID().toString());
		leagueService.createLeague(league);
		expectedExp.expectMessage(LeagueExceptions.LEAGUE_NAME_IS_NULL.toString());
	}

	@Test(expected = LeagueValidationException.class)
	public void createLeagueTest_SeasonIdNull() {
		League league = new League();
		league.setId(UUID.randomUUID().toString());
		league.setLeagueName("aa");
		leagueService.createLeague(league);
		expectedExp.expectMessage(LeagueExceptions.SEASON_ID_IS_NULL.toString());
	}

	@Test(expected = LeagueValidationException.class)
	public void createLeagueTest_AdminIdNull() {
		League league = new League();
		league.setId(UUID.randomUUID().toString());
		league.setLeagueName("aa");
		league.setSeasonId("aa");
		league.setPassword("aa");
		leagueService.createLeague(league);
		expectedExp.expectMessage(LeagueExceptions.ADMIN_NOT_FOUND.toString());
	}

	@Test(expected = NullPointerException.class)
	public void updateLeagueTest_leagueNull() {
		leagueService.updateLeague(null);
	}

	@Test(expected = LeagueValidationException.class)
	public void updateLeagueTest_LeagueNotFound() {
		League league = generateLeague();
		when(leagueRepositoryMock.findOne(anyString())).thenReturn(null);
		leagueService.updateLeague(league);
		expectedExp.expectMessage(LeagueExceptions.LEAGUE_NOT_FOUND.toString());
	}

	@Test
	public void updateLeagueTest_Save() {
		League league = generateLeague();
		when(leagueRepositoryMock.findOne(anyString())).thenReturn(league);
		leagueService.updateLeague(league);
		verify(leagueRepositoryMock).save(league);
	}

	@Test
	public void getLeaguesForPlayerTest_PlayerIdNull() {
		Set ret = leagueService.getLeaguesForPlayer(null);
		assertThat(ret.size() == 0);
	}

	@Test
	public void getLeaguesForPlayerTest_PlayerIdCorrect() {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		when(playerLeagueRepositoryMock.findIdLeagueIdsByIdPlayerId(anyString())).thenReturn(list);
		leagueService.getLeaguesForPlayer("anyStri");
		verify(leagueRepositoryMock).findAll(list);
	}

	@Test
	public void getPlayersInLeagueTest_LeagueIdInvalid() {
		Set<String> players = leagueService.getPlayersInLeague("invalidleagueid");
		assertThat(players.size() == 0);
	}

	@Test(expected = LeagueValidationException.class)
	public void joinLeagueTest_NullLeague() {
		League league = generateLeague();
		when(leagueRepositoryMock.findOne(anyString())).thenReturn(null);
		leagueService.joinLeague(league.getId(), "PlayerId", league.getPassword());
		expectedExp.expectMessage(LeagueExceptions.LEAGUE_NOT_FOUND.toString());
	}

	@Test
	public void joinLeagueTest_Save() {
		League league = generateLeague();
		when(leagueRepositoryMock.findOne(anyString())).thenReturn(league);
		when(playerLeagueRepositoryMock.save(anyList())).thenReturn(anyObject());
		leagueService.joinLeague(league.getId(), "PlayerId", league.getPassword());
	}

	@Test
	public void getLeagueByIdTest_IncorrectId() {
		assertNull(leagueService.getLeagueById("x"));
	}

	@Test
	public void getLeagueByNameTest_IncorrectName() {
		assertNull(leagueService.getLeagueByName("x"));
	}

	@Test(expected = LeagueValidationException.class)
	public void removePlayerFromLeagueTest_InvalidLeague() {
		when(leagueService.getLeagueById(null)).thenReturn(null);
		leagueService.removePlayerFromLeague(null, null);
		expectedExp.expectMessage(LeagueExceptions.LEAGUE_NOT_FOUND.toString());
	}

	@Test
	public void removePlayerFromLeagueTest_ValidLeague() {
		League league = generateLeague();
		PlayerLeagueId playerLeagueId = new PlayerLeagueId("leagueId", "playerId");
		PlayerLeague playerLeague = new PlayerLeague(playerLeagueId);

		when(leagueService.getLeagueById(anyObject())).thenReturn(league);
		when(playerLeagueRepositoryMock.findByIdLeagueIdAndIdPlayerId(anyString(), anyString()))
				.thenReturn(playerLeague);
		leagueService.removePlayerFromLeague(league.getId(), "playerId");
		verify(playerLeagueRepositoryMock).delete(playerLeague);
	}

	// TODO : Do we have to create test for private and protected methods like
	// below ?
	/*
	 * @Test public void isLeagueValidTest_InvalidLeagueId(){
	 * when(leagueRepositoryMock.findOne(anyString())).thenReturn(null);
	 * assertEquals(false, leagueService.isLeagueValid("a"));
	 * 
	 * }
	 */

	@Test
	public void getAllLeaguesTest() {
		Iterable<League> list = leagueService.getAllLeagues();
		assertNotNull(list);
	}

	@Test
	public void deleteLeagueTest_InvalidLeagueId() {
		when(playerLeagueRepositoryMock.findIdPlayerIdsByIdLeagueId(anyString())).thenReturn(anyList());
		when(leagueService.getLeagueById(null)).thenReturn(null);
		leagueService.deleteLeague("x");
		verify(leagueRepositoryMock).delete("x");
	}

	@Test
	public void deleteLeagueTest_ValidLeagueId() {
		League league = generateLeague();
		when(playerLeagueRepositoryMock.findIdPlayerIdsByIdLeagueId(anyString())).thenReturn(anyList());
		when(leagueService.getLeagueById("a")).thenReturn(league);
		leagueService.deleteLeague("a");
		verify(leagueRepositoryMock).delete("a");
	}

	@Test
	public void getLeagueBySeasonIdTest_InvalidSeasonId() {
		when(leagueRepositoryMock.findLeagueBySeasonId(null)).thenReturn(null);
		assertNull(leagueService.getLeagueBySeasonId(null));
	}

	@Test
	public void getLeagueBySeasonIdTest_ValidSeasonId() {
		when(leagueRepositoryMock.findLeagueBySeasonId(anyString())).thenReturn(anyList());
		assertNotNull(leagueService.getLeagueBySeasonId("a"));
	}

}
