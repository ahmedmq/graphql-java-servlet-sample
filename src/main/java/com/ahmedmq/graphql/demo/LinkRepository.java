package com.ahmedmq.graphql.demo;

import java.util.ArrayList;
import java.util.List;

public class LinkRepository {

	private final List<Link> links;

	public LinkRepository() {
		this.links = new ArrayList<>();
	}

	public List<Link> allLinks() {
		return links;
	}

	public Link saveLink(String url, String description){
		Link newLink = new Link(url, description);
		links.add(newLink);
		return newLink;
	}
}
