# GraphQL Java Servlet Demo

This repository implements a GraphQL API by configuring a web servlet at `/graphql` path. 
The application is a simple bookmarking service that records the URL and description for every bookmark. A user 
of the application can fetch a list of bookmarks and also create new bookmarks.
The following are the key dependencies of the application

[`graphql-java`](https://www.graphql-java.com/) - GraphQL implementation in Java

[`graphql-java-tools`](https://www.graphql-java-kickstart.com/tools/) - Tools to help map a GraphQL schema to existing Java objects

[`graphql-java-servlet`](https://www.graphql-java-kickstart.com/servlet/) - Servlet endpoint for GraphQL Java

[`jetty-maven-plugin`](https://wiki.eclipse.org/Jetty/Feature/Jetty_Maven_Plugin) - Maven Jetty plugin for rapid development and testing

Note that the `graphql-java-tools` and `graphql-java-servlet` have been moved out of the GraphQL Java org. No Spring dependencies being used for this implementation

### Running the application

Run the application by executing `mvn jetty:run` from the root of the project. This starts
Jetty and serves up the application on `http://localhost:8080/`

### Querying API using GraphiQL
[GraphiQL](https://github.com/graphql/graphiql) is an in-browser IDE for writing, validating and testing GraphQL queries and mutations.
To add GraphiQL, we simply copy the [index.html](https://github.com/howtographql/graphql-java/blob/master/src/main/webapp/index.html) and save the file to `src/main/webapp/index.html`. Run the application to
access the GraphiQL IDE at `http://localhost:8080/`

Note: This changes is already made in the repository

To fetch the URLs of all the bookmarks stored, use the following GraphQL query:
```text
query allLinks{
    allLinks{
      url
    }
}
```

To create a new bookmark, use the following query:

```text
mutation createLink {
  createLink(url:"https://www.howtographql.com", description: "Learn graphql"){
    url
    description
  }
}
```

### Testing
Applications tests are written using an Embedded Jetty Server. [`HttpUrlConnection`](https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html)
instance is used to make the GraphQL request to the server

### Notes
- The following are the versions currently maintained by Jetty
    
    | Jetty      | Servlet     | EE          | Namespace       |
    |------------|-------------|-------------|-----------------|
    | Jetty 10.x | Servlet 4.0 | Jakarta EE8 | javax.servlet   |      
    | Jetty 11.x | Servlet 5.0 | Jakarta EE9 | jakarta.servlet |

   There is no backward compatibility between `javax.servlet` and `jakarta.servlet`. 
   The`graphql-java-servlet` uses methods inherited from class `javax.servlet.http.HttpServlet`
   and so we use the Jetty 10.x (or less) version of the library in this application.


- Alternatively , we can configure a `GraphQLHTTPServlet` by extending from `HTTPServlet` and then
  overriding the `service()` method to invoke the `service()` method of `GraphQLHttpServlet`. For e.g. below:

    ```java
    
    import javax.servlet.http.HttpServlet;
    
    class GraphQLEndpoint extends HttpServlet {
    
        private GraphQLHttpServlet graphQLServlet;
    
        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            graphQLServlet.service(req, resp);
        }
    
        @Override
        public void init() {
            GraphQLSchema schema = SchemaParser.newParser()
                    .resolvers(new Query(new LinkRepository()))
                    .file("schema.graphqls")
                    .build()
                    .makeExecutableSchema();
            graphQLServlet = GraphQLHttpServlet.with(schema);
    
        }
    }
    
    ```