package com.ahmedmq.graphql.demo;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkRepositoryTest {

	LinkRepository linkRepository = new LinkRepository();

	@Test
	void shouldSaveLinkToRepository(){
		String url = "www.test.com";
		String description = "Testing";
		linkRepository.saveLink(url, description);

		List<Link> links = linkRepository.allLinks();
		assertEquals(links.size(), 1);
		assertEquals(links.get(0).url, url );
		assertEquals(links.get(0).description, description);

	}


}