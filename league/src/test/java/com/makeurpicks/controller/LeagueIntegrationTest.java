package com.makeurpicks.controller;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.makeurpicks.controller.LeagueController;
import com.makeurpicks.domain.League;
import com.makeurpicks.domain.LeagueName;
import com.makeurpicks.domain.PlayerLeague;
import com.makeurpicks.domain.PlayerLeagueId;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
//@SpringBootTest
@BootstrapWith(SpringBootTestContextBootstrapper.class)
//context loading fails when this is added. Hence commenting it out
//@DataJpaTest//This can replace Transactional
@Transactional(propagation=Propagation.REQUIRED)  //All methods in test suite will be same transaction
@ActiveProfiles("container")
@ContextConfiguration
@SqlGroup({
	@Sql(scripts = "populateLeagueTableTest.sql", executionPhase = org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD,config=@SqlConfig(transactionMode = TransactionMode.ISOLATED) ),
	@Sql(scripts = "rollBackLeagueTableTest.sql", executionPhase = org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD,config=@SqlConfig(transactionMode = TransactionMode.ISOLATED) )
	})
/*
IT IS REQUIRED TO USE SQLCONFIG BECAUSE, WITHOUT THAT DATA IS NOT GETTING COMMITED. SINCE WE CALL REST URL, THAT HAS
ITS OWN EXECUTION PATH AND IF THE DATA HERE IS NOT COMMITED, THE SERVICE INSTANCE MIGHT NOT SEE IT AS THE SCOPE WILL BE READ_COMMITED
HENCE COMMITING IMMEDIATELY AFTER INSERT AND DELETE
*/
//@Sql(scripts="populateLeagueTableTest.sql", executionPhase = org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD,config=@SqlConfig(transactionMode = TransactionMode.ISOLATED))
public class LeagueIntegrationTest {
	
	/* @Autowired
     private TestRestTemplate testRestTemplate;
	 */
	 @Autowired 
	 MockHttpSession session;
	 
	 @Autowired
	 LeagueController leagueController;
	 
	// @Transactional(propagation=Propagation.REQUIRED)  //This method will have its own transaction scope and will be rolled back after that
	 @Test
	// @Commit
	 /*
	   IT IS REQUIRED TO USE SQLCONFIG BECAUSE, WITHOUT THAT DATA IS NOT GETTING COMMITED. SINCE WE CALL REST URL, THAT HAS
	 ITS OWN EXECUTION PATH AND IF THE DATA HERE IS NOT COMMITED, THE SERVICE INSTANCE MIGHT NOT SEE IT AS THE SCOPE WILL BE READ_COMMITED
	 HENCE COMMITING IMMEDIATELY AFTER INSERT AND DELETE
	 */
/*	 @SqlGroup({
			@Sql(scripts = "populateLeagueTableTest.sql", executionPhase = org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD,config=@SqlConfig(transactionMode = TransactionMode.ISOLATED) ),
			@Sql(scripts = "rollBackLeagueTableTest.sql", executionPhase = org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD,config=@SqlConfig(transactionMode = TransactionMode.ISOLATED) )
			})*/
	 public void getLeagueById_validLeagueURL_success(){
		 TestRestTemplate testRestTemplate=new TestRestTemplate();
		 //RestTemplate testRestTemplate=new RestTemplate();
		 ResponseEntity<League> response=testRestTemplate.getForEntity("http://localhost:8081/leagues/1234567/", League.class);
		//testRestTemplate.
		League createdObject=(League)response.getBody();
		//System.out.println("League name returned is:"+createdObject.getLeagueName());
		//System.out.println("ID returned is:"+createdObject.getId());
		assertNotNull(createdObject);
		assertNotNull(createdObject.getLeagueName());
		assertEquals("summer2017", createdObject.getLeagueName());
	 }

	private League createLeagueObjectForTesting() {
		League league=new League();
		 league.setAdminId("admin");
		 league.setActive(true);
		 league.setBanker(true);
		 league.setDoubleEnabled(true);
		 league.setEntryFee(10.00);
		 league.setFree(false);
		 //league.setId("");
		 league.setLeagueName("Test League");
		 league.setFifthPlacePercent(5);
		 league.setFirstPlacePercent(20);
		 league.setFourthPlacePercent(50);
		 league.setFree(false);
		 league.setMoney(true);
		 league.setPaidFor(1);
		 league.setPassword("admin");
		 league.setSeasonId("123");
		 league.setSecondPlacePercent(35);
		 league.setSpreads(true);
		 league.setThirdPlacePercent(40);
		 league.setWeeklyFee(20.00);
		 return league;
	}
	 
	/* @Bean(name={"testRestTemplate"})
	public TestRestTemplate testRestTemplate() {
			return new TestRestTemplate();
		}
*/
	 
	 //@Transactional(propagation=Propagation.REQUIRED)  //This method will have its own transaction scope and will be rolled back after that
	 @Test
	 //COMMENTED OUT POST EXECUTION AS THE TRANSACTION GETS ROLLEDBACK
	/* @SqlGroup({
			@Sql(scripts = "populateLeagueTableTest.sql", executionPhase = org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD)
		//	@Sql(scripts = "rollBackLeagueTableTest.sql", executionPhase = org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD )
			})*/
	 public void getLeagueById_validLeagueController_success(){
		 
		 League league=leagueController.getLeagueById("1234567");
		 assertNotNull(league);
		 assertNotNull(league.getLeagueName());
		 assertEquals("summer2017", league.getLeagueName());
		 
	 }
	 
	 @Transactional(propagation=Propagation.REQUIRED)  //This method will have its own transaction scope and will be rolled back after that
	 @Test
	public void createLeague_validLeagueController_success(){
		 
		 Authentication authentication = Mockito.mock(Authentication.class);
		 
		// Mockito.whens() for your authorization object
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(securityContext.getAuthentication().getName()).thenReturn("admin");
		SecurityContextHolder.setContext(securityContext);
		 //Principal principal=null;
		League league=createLeagueObjectForTesting();
		League createdLeague=	leagueController.createLeague(authentication, league);
		System.out.println("Id created is:"+league.getId()+":");
		 assertNotNull(createdLeague);
		 assertNotNull(league.getId());
		 //assertEquals("test1", league.getLeagueName());
		 
	 }
	
	 @Test
	 public void getPlayersInLeague_validData_success(){
		 
		Set<String> players=leagueController.getPlayersInLeague("1234567");
		assertThat(players.contains("123"));
		assertThat(players.contains("124"));
		assertEquals(players.size(), 2);
		 
	 }
	 
	 @Test
	 public void getLeaguesForPlayer_validDataMultipleLeagues_success(){
		 
		Set<LeagueName> leagues=leagueController.getLeaguesForPlayer("123");
		assertThat(leagues.contains("summer2017"));
		assertThat(leagues.contains("summer2018"));
		assertEquals(leagues.size(), 2);
		System.out.println("After getting all leagues and one containing 2018");
		 
	 }
	 @Test
	 public void getLeaguesForPlayer_validDataSingleLeagues_success(){
		 System.out.println("Entering getLeaguesForPlayer_validDataSingleLeagues_success");
		Set<LeagueName> leagues=leagueController.getLeaguesForPlayer("124");
		assertThat(leagues.contains("summer2017"));
		assertEquals(leagues.size(), 1);
		 
	 }
	 
	 @Test
	 public void getLeaguesByName_validDataSingle_success(){
		 
		League league=leagueController.getLeagueByName("summer2018");
		assertEquals("1234568",league.getId());
		System.out.println("After getting league by name sumemr2018 and ID matched");
		 
	 }
	 
	 /*
	  * For all the other methods, @SQlgroup at class level will insert. For this we should do as at the end of previous method executions
	  * data is deleted. So if we do not have inserts, addPlayeToLeague fails with the reason of no league found
	  */
	/* @Transactional(propagation=Propagation.REQUIRES_NEW) 
	 @SqlGroup({
		 	@Sql(scripts = "populateLeagueTableTest_BeforeAddPlayerToLeague.sql", executionPhase = org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD,config=@SqlConfig(transactionMode = TransactionMode.ISOLATED) ),
		 	@Sql(scripts = "rollBackPlayerLeagueTable_AfterAddPlayerToLeague.sql", executionPhase = org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD ,config=@SqlConfig(transactionMode = TransactionMode.ISOLATED) )
			})*/
	 /*@Test
	 public void addPlayerToLeague_success(){*/
		 
		/* Set<LeagueName> leagues=leagueController.getLeaguesForPlayer("124");
			assertThat(leagues.contains("summer2017"));
			//assertEquals(1,leagues.size());
*/		
		 /*PlayerLeague playerLeague=new PlayerLeague(new PlayerLeagueId("1234568", "124"));
		playerLeague.setPassword("admin123");
		leagueController.addPlayerToLeague(playerLeague);
		
		 Set<LeagueName> leaguesAfterUpdate=leagueController.getLeaguesForPlayer("124");
			assertThat(leaguesAfterUpdate.contains("summer2017"));
			assertThat(leaguesAfterUpdate.contains("summer2018"));
			assertEquals(2,leaguesAfterUpdate.size());
			System.out.println("After adding player to league");
		 
	 }*/

}
