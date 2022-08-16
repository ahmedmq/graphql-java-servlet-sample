package com.ahmedmq.graphql.demo;

import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

class GraphQLEndpointTest extends AbstractServletTest {

	@Test
	void givenGraphQL_Endpoint_whenCreatingNewLink_thenExpectedJsonIsReturnedAndNewLinkIsSavedInRepository() throws JSONException, IOException {
		String createLinkMutationRequest = "mutation createLink{createLink(url:\\\"https://www.howtographql.com\\\", description:\\\"Learn graphql\\\"){url description}}";
		String createLinkMutationResponse = "{\"data\":{\"createLink\":{\"url\":\"https://www.howtographql.com\", \"description\":\"Learn graphql\"}}}";
		String graphQLMutationResponse = callGraphQLService(createLinkMutationRequest);
		JSONAssert.assertEquals(createLinkMutationResponse, graphQLMutationResponse, true);

		String allLinksQueryResponse = "{\"data\":{\"allLinks\":[{\"url\":\"https://www.howtographql.com\"}]}}";
		String allLinksQueryRequest = "query{allLinks{url}}";
		String graphQLQueryResponse = callGraphQLService(allLinksQueryRequest);
		JSONAssert.assertEquals(allLinksQueryResponse, graphQLQueryResponse, true);
	}

}