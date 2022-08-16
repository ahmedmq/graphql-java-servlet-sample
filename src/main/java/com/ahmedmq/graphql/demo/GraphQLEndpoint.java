package com.ahmedmq.graphql.demo;

import javax.servlet.annotation.WebServlet;

import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.kickstart.servlet.GraphQLHttpServlet;
import graphql.kickstart.tools.SchemaParser;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends GraphQLHttpServlet {

	LinkRepository linkRepository =  new LinkRepository();

	@Override
	protected GraphQLConfiguration getConfiguration() {
		return GraphQLConfiguration
				.with(SchemaParser.newParser()
						.file("schema.graphqls")
						.resolvers(new Query(linkRepository), new Mutation(linkRepository))
						.build()
						.makeExecutableSchema()).build();
	}


}
