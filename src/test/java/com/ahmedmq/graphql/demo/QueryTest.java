package com.ahmedmq.graphql.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@ExtendWith(MockitoExtension.class)
class QueryTest {

	@Mock
	LinkRepository mockedLinkRepository;

	@InjectMocks
	Query query;

	@Test
	void shouldReturnAllLinksFromRepository() {
		List<Link> links = new ArrayList<>();
		links.add(new Link("www.test.com", "Testing"));
		Mockito.when(mockedLinkRepository.allLinks()).thenReturn(links);

		List<Link> actualLinks = query.allLinks();

		assertIterableEquals(links, actualLinks);

	}
}