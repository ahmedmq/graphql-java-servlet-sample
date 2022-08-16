package com.ahmedmq.graphql.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractServletTest {

	private static Server server;

	public static URI serverUri;

	@BeforeAll
	static void setup() throws Exception {
		// Create Server
		server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(0);
		server.addConnector(connector);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SECURITY | ServletContextHandler.NO_SESSIONS);

		Servlet graphqlServlet = new GraphQLEndpoint();
		context.addServlet(new ServletHolder(graphqlServlet), "/graphql");
		server.setHandler(context);

		// Start Server
		server.start();

		// Determine Base URI for Server
		String host = connector.getHost();
		if (host == null) {
			host = "localhost" ;
		}
		int port = connector.getLocalPort();
		serverUri = new URI(String.format("http://%s:%d", host, port));
	}

	@AfterAll
	static void shutdown() throws Exception {
		server.stop();
	}

	public String callGraphQLService(String query) throws IOException {
		HttpURLConnection http = (HttpURLConnection) new URL(
				serverUri + "/graphql"
		).openConnection();
		http.setRequestMethod("POST");
		http.setRequestProperty("Content-type", "application/json");
		http.setRequestProperty("Accept", "application/json");
		http.setDoOutput(true);
		String jsonInputString = "{\"query\":\""+query+"\"}";
		System.out.println(jsonInputString);
		try(OutputStream os = http.getOutputStream()) {
			byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
			os.write(input, 0, input.length);
		}
		return readResponseBodyStringFromStream(http);
	}

	String readResponseBodyStringFromStream(HttpURLConnection httpURLConnection) throws IOException {
		StringBuilder response = new StringBuilder();
		if (httpURLConnection.getResponseCode() == 200) {
			try(BufferedReader br = new BufferedReader(
					new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {

				String responseLine;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}

			}
		}
		else {
			try(BufferedReader br = new BufferedReader(
					new InputStreamReader(httpURLConnection.getErrorStream()))) {
				String responseLine;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
			}
		}
		return response.toString();
	}
}
