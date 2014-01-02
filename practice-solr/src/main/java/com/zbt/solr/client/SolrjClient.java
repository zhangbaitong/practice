package com.zbt.solr.client;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;

public class SolrjClient {

	public SolrServer getSolrServer() throws Exception {
		String urlString = "http://10.1.100.156:8983/lefeng/product/select";
		// String urlString = "http://localhost:8983/solr";
		// 4.0以前使用CommonsHttpSolrServer，之后改为HttpSolrServer
		// SolrServer server = new HttpSolrServer(urlString);
		CommonsHttpSolrServer server = new CommonsHttpSolrServer(urlString);

		server.setSoTimeout(1000); // socket read timeout
		server.setConnectionTimeout(100);
		server.setDefaultMaxConnectionsPerHost(100);
		server.setMaxTotalConnections(100);
		server.setFollowRedirects(false); // defaults to false
		// allowCompression defaults to false.
		// Server side must support gzip or deflate for this to have any effect.
		server.setAllowCompression(true);
		server.setMaxRetries(1); // defaults to 0. > 1 not recommended.
		return server;
	}

	public static void main(String[] args) {

	}

	public SolrServer getEmbeddedServer() throws Exception {
		// Note that the following property could be set through JVM level
		// arguments too
		System.setProperty("solr.solr.home",
				"/home/shalinsmangar/work/oss/branch-1.3/example/solr");
		CoreContainer.Initializer initializer = new CoreContainer.Initializer();
		CoreContainer coreContainer = initializer.initialize();
		EmbeddedSolrServer server = new EmbeddedSolrServer(coreContainer, "");
		return server;
	}

	public SolrServer getMultiCoreEmbeddedServer() throws Exception {
		File home = new File("/path/to/solr/home");
		File f = new File(home, "solr.xml");
		CoreContainer container = new CoreContainer();
		container.load("/path/to/solr/home", f);

		EmbeddedSolrServer server = new EmbeddedSolrServer(container,
				"core name as defined in solr.xml");
		return server;
	}

	public void addData() throws Exception {
		SolrServer server = getSolrServer();
		server.deleteByQuery("*:*");// deletes everything!
		// Construct a document
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "id1", 1.0f);
		doc1.addField("name", "doc1", 1.0f);
		doc1.addField("price", 10);
		// Construct another document. Each document can be independently be
		// added but it is more efficient to do a batch update. Every call to
		// SolrServer is an Http Call (This is not true for EmbeddedSolrServer).
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "id2", 1.0f);
		doc2.addField("name", "doc2", 1.0f);
		doc2.addField("price", 20);
		// Fields "id","name" and "price" are already included in Solr
		// installation, you must add your new custom fields in SchemaXml.

		// Create a collection of documents
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(doc1);
		docs.add(doc2);
		// Add the documents to Solr
		server.add(docs);
		// Do a commit
		server.commit();
		// To immediately commit after adding documents, you could use:

		UpdateRequest req = new UpdateRequest();
		req.setAction(UpdateRequest.ACTION.COMMIT, false, false);
		req.add(docs);
		UpdateResponse rsp = req.process(server);
	}

	// Streaming documents for an update
	public void addStreamingDoc() throws Exception {
		SolrServer server = getSolrServer();
		Iterator<SolrInputDocument> iter = new Iterator<SolrInputDocument>() {
			public boolean hasNext() {
				boolean result = false;
				// set the result to true false to say if you have more
				// documensts
				return result;
			}

			public SolrInputDocument next() {
				SolrInputDocument result = null;
				// construct a new document here and set it to result
				return result;
			}

			public void remove() {
			}
		};
		server.add((Collection<SolrInputDocument>) iter);
	}

	public void readData() throws Exception {
		SolrServer server = getSolrServer();
		// Construct a SolrQuery
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.addSortField("price", SolrQuery.ORDER.asc);
		// Query the server
		QueryResponse rsp = server.query(query);
		SolrDocumentList docs = rsp.getResults();
	}

	public void facetedQuery() throws Exception {
		SolrServer server = getSolrServer();
		SolrQuery solrQuery = new SolrQuery().setQuery("ipod").setFacet(true)
				.setFacetMinCount(1).setFacetLimit(8).addFacetField("category")
				.addFacetField("inStock");
		QueryResponse rsp = server.query(solrQuery);
	}

	public void highlighting() throws Exception {
		SolrQuery query = new SolrQuery();
		query.setQuery("foo");

		query.setHighlight(true).setHighlightSnippets(1); // set other params as
															// needed
		query.setParam("hl.fl", "content");

		QueryResponse queryResponse = getSolrServer().query(query);
		// Then to get back the highlight results you need something like this:
		Iterator<SolrDocument> iter = queryResponse.getResults().iterator();

		while (iter.hasNext()) {
			SolrDocument resultDoc = iter.next();

			String content = (String) resultDoc.getFieldValue("content");
			String id = (String) resultDoc.getFieldValue("id"); // id is the
																// uniqueKey
																// field

			if (queryResponse.getHighlighting().get(id) != null) {
				List<String> highlightSnippets = queryResponse
						.getHighlighting().get(id).get("content");
			}
		}

	}
}
