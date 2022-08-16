package com.ahmedmq.graphql.demo;

public class Link {
	String url;
	String description;

	public Link(String url, String description) {
		this.url = url;
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

}
