package com.makeurpicks.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.makeurpicks.domain.League;
import com.makeurpicks.repository.LeagueRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeagueControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private LeagueRepository leagueRepository;

	@Autowired
	private WebApplicationContext ctx;

	@Before
	public void init(){
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();


		League league = new League();
		league.setId("1");
		league.setLeagueName("league_1");
		league.setActive(true);
		league.setSeasonId("seasonn_1");
		league.setAdminId("admin_1");
		league.setPassword("admin");
		leagueRepository.save(league);
	}

	@Test
	public void test() {

		System.out.println(leagueRepository);

		final String leagueId = "1";

		League league = new League();
		league.setActive(true);
		league.setId(leagueId);
		league.setLeagueName("FirstLeague");

		String str = new StringBuilder("/").toString();

		try {
			MvcResult result  = mockMvc.perform(get(str, leagueId)).andExpect(status().isOk()).andDo(print())
					.andExpect(jsonPath("$[0].id", containsString("1")))
					.andExpect(jsonPath("$[0].leagueName", containsString("league_1")))
					.andExpect(jsonPath("$[0].seasonId", containsString("seasonn_1")))
					.andExpect(jsonPath("$[0].password", containsString("admin")))
					.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}