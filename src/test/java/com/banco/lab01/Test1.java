package com.banco.lab01;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Test1 {


	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}
	@Test
	@Order(1)
	public void FirstDeposit() throws Exception{
		this.mockMvc.perform(post("http://localhost:8080/movements/deposit?amount=1000"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.type").value("Deposit"))
				.andExpect(jsonPath("$.amount").value(1000.0));
	}

	@Test
	@Order(2)
	public void SecondDeposit() throws Exception{
		this.mockMvc.perform(post("http://localhost:8080/movements/deposit?amount=1000"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.type").value("Deposit"))
				.andExpect(jsonPath("$.amount").value(1000.0));
	}

	@Test
	@Order(3)
	public void interestOk() throws Exception{
		this.mockMvc.perform(post("http://localhost:8080/movements/interest"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.type").value("Interest"))
				.andExpect(jsonPath("$.amount").value(200.0));
	}

	@Test
	@Order(4)
	public void balanceOk() throws Exception{
		this.mockMvc.perform(get("http://localhost:8080/movements/balance"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect((ResultMatcher) jsonPath("$.balance").value(2200.0));
	}

	@AfterAll
	public void cleanDatabase() throws Exception{
		this.mockMvc.perform(delete("http://localhost:8080/movements"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.result").value("ok"));
	}
}
