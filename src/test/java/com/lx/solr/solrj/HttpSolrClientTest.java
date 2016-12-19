package com.lx.solr.solrj;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * 使用solrj提供的HttpSolrClient来连接访问solr服务
 * @author lx
 */
public class HttpSolrClientTest {
	
	public static void main(String[] args) {
		
		// 通过明确指定的solrUrl来创建一个solrclient实例
		String solrUrl = "http://localhost:8983/solr/";
		HttpSolrClient solrClient = new HttpSolrClient(solrUrl);
	}
}
