package com.alexandrofs.gfp;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alexandrofs.gfp.dsl.Dsl;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GfpApp.class)
@WebAppConfiguration
@IntegrationTest
public abstract class AbstractTest {

	@Inject
	protected Dsl dsl;
}
