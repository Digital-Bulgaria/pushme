package eu.balev.pushme.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import eu.balev.pushme.repository.ContainerRepository;
import eu.balev.pushme.web.security.WithMockCustomUser;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = { "pushme.max.reg.containers=3" })
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContainerRepository mockRepo;

	@Before
	public void setUp() {
		Mockito.when(mockRepo.countByUser(Mockito.any())).thenReturn(3L);
	}

	@Test
	@WithMockCustomUser(email = "dummy@mail.bg")
	public void testModelPopulated() throws Exception {
		this.mockMvc.perform(get("/"))
				.andExpect(view().name("index"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("maxReached", true))
				.andExpect(model().attribute("ctnrCnt", 3L));
	}
}
