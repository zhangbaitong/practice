package com.zbt.solr.client;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.beans.Field;


public class Item {
    @Field
    String id;

    @Field("cat")
    String[] categories;

    @Field
    List<String> features;
    
    @Field("cat")
    public void setCategory(String[] c){
        this.categories = c;
    }
    
    public static void main(String[] args) throws Exception {
		SolrjClient client =  new SolrjClient();
		SolrServer server = client.getSolrServer();
		//Create the bean instances
		Item item = new Item();
	    item.id = "one";
	    item.categories =  new String[] { "aaa", "bbb", "ccc" };
	    //Add to Solr
	    server.addBean(item);
	    
	    List<Item> beans = null;
	    //add Item objects to the list
	    server.addBeans(beans);
	}

  }
