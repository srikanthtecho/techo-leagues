package com.makeurpicks.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;

import com.makeurpicks.LeagueApplication;
import com.makeurpicks.domain.League;
import com.makeurpicks.domain.LeagueBuilder;
import com.makeurpicks.domain.LeagueName;
import com.makeurpicks.domain.LeaguesPlayerJoined;
import com.makeurpicks.domain.PlayersInLeague;
import com.makeurpicks.exception.LeagueValidationException;
import com.makeurpicks.exception.LeagueValidationException.LeagueExceptions;
import com.makeurpicks.repository.LeagueRepository;
import com.makeurpicks.repository.LeaguesAPlayHasJoinedRespository;
import com.makeurpicks.repository.PlayersInLeagueRepository;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = LeagueApplication.class)
	public class LeagueServiceTest {
	 
	
		
	}


}
