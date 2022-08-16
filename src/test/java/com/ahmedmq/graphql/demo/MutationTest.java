package com.ahmedmq.graphql.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.JUnitException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MutationTest {

	@Mock
	LinkRepository linkRepository;

	@InjectMocks
	Mutation mutation;

	@Test
	void shouldSaveLinkIntoRepository() {

		when(linkRepository.saveLink(anyString(), anyString()))
				.thenAnswer(invocationOnMock ->
						new Link(invocationOnMock.getArgument(0), invocationOnMock.getArgument(1)));
		Link newLink = mutation.createLink("www.test.com", "Create a new link");
		Assertions.assertEquals("www.test.com", newLink.getUrl());
		Assertions.assertEquals("Create a new link", newLink.getDescription());

	}
}
