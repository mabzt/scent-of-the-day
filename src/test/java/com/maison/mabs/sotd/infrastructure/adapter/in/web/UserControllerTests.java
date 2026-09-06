package com.maison.mabs.sotd.infrastructure.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maison.mabs.sotd.application.port.in.UserPort;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CollectionRequest;
import com.maison.mabs.sotd.infrastructure.adapter.in.dto.user.request.CreateUserRequest;
import com.maison.mabs.sotd.utils.UserTestDataUtil;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@WebMvcTest(UserController.class)
public class UserControllerTests {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserPort userPort;

	@Test
	void shouldCreateUserWhenRequestIsValid() throws Exception {
		var createUserRequest = UserTestDataUtil.validRequest();
		var user = UserTestDataUtil.validUser();

		Mockito.when(this.userPort.createUserProfile(Mockito.any(CreateUserRequest.class))).thenReturn(user);

		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/api/users")
				.header("Api-Version", "1.0")
				.content(this.objectMapper.writeValueAsString(createUserRequest))
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.email()));
	}

	@Test
	void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
		var createUserRequest = UserTestDataUtil.validRequest();
		var user = UserTestDataUtil.validUser();

		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/api/users")
				.header("Api-Version", "1.0")
				.content(createUserRequest.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.detail").value("Failed to read request"));

	}

	@Test
	void shouldReturnBadRequestWhenApiVersionIsNotProvided() throws Exception {
		var createUserRequest = UserTestDataUtil.validRequest();
		var user = UserTestDataUtil.validUser();

		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/api/users")
				.content(createUserRequest.toString())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.detail").value("API version is required."));

	}

	@Test
	void shouldAddCollectionWhenRequestIsValid() throws Exception {
		var id = UUID.randomUUID();
		var collectionRequest = UserTestDataUtil.collectionRequest();
		var updatedUser = UserTestDataUtil.validUser();

		Mockito.when(this.userPort.addCollection(Mockito.eq(id), Mockito.any(CollectionRequest.class)))
			.thenReturn(updatedUser);

		this.mockMvc
			.perform(MockMvcRequestBuilders.patch("/api/users/{id}", id)
				.header("Api-Version", "1.0")
				.contentType(String.valueOf(MediaType.APPLICATION_JSON))
				.content(this.objectMapper.writeValueAsString(collectionRequest)))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
