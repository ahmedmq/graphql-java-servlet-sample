package com.ahmedmq.graphql.demo;

import graphql.kickstart.tools.GraphQLMutationResolver;

public class Mutation implements GraphQLMutationResolver {

	private final LinkRepository linkRepository;

	public Mutation(LinkRepository linkRepository) {
		this.linkRepository = linkRepository;
	}

	public Link createLink(String url, String description){
		return linkRepository.saveLink(url, description);
	}
}
