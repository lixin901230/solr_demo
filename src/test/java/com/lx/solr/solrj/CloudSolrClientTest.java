package com.lx.solr.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrClient;

/**
 * 使用solrj提供的CloudSolrClient来连接访问solr集群服务
 * @author lx
 */
public class CloudSolrClientTest {

	public static void main(String[] args) {
		
		// 通过solr集群的zkHost来创建一个CloudSolrClient实例
		String zkHost = "";
		CloudSolrClient cloudSolrClient = new CloudSolrClient(zkHost);
	}
}
