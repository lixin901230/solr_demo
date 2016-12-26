package com.lx.solr.solrj;

import java.io.IOException;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * 使用solrj提供的HttpSolrClient来连接访问solr服务
 * @author lx
 */
public class HttpSolrClientTest {
	
	/** solr 服务地址常量 */
	private final static String SOLR_URL = "http://localhost:8983/solr/#/mycore1";
	
	/**
	 * 测试查询solr文档
	 */
	@Test
	public void testSolrQuery() {
		
		SolrClient solrClient = null;
		try {
			// 通过明确指定的solrUrl来创建一个solrclient实例
			solrClient = new HttpSolrClient(SOLR_URL);
			
			// 创建SolrQuery对象
			String myQueryString = "lucene";
			SolrQuery query = new SolrQuery();
			query.setQuery(myQueryString);
			query.setRequestHandler("/spellCheckCompRH");
			
			// 设置查询字段与参数
			query.set("fl", "catagory,title,price");
			//query.setFields("catagory", "title", "price"); // 设置查询字段（与上一行通过设置fl的方式效果相同）
			query.set("q", "catagory:books");

			// 执行查询，得到符合查询条件的文档集合
			QueryResponse response = solrClient.query(query);
			SolrDocumentList documentList = response.getResults();
			for (SolrDocument doc : documentList) {
				Collection<String> fieldNames = doc.getFieldNames();
				System.out.println("fieldNames=="+fieldNames);
				
				for (String fieldName : fieldNames) {
					Object fieldValue = doc.getFieldValue(fieldName);
					System.out.println("fieldValue=="+fieldValue);
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(solrClient != null) {
				try {
					solrClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 测试添加solr索引
	 */
	@Test
	public void testAddSolrIndex() {
		
		SolrClient solrClient = null;
		try {
			// 通过明确指定的solrUrl来创建一个solrclient实例
			solrClient = new HttpSolrClient(SOLR_URL);
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", "1212");
			document.addField("name", "this is apple");
			document.addField("price", "5.0");
			UpdateResponse updateResponse = solrClient.add(document);
			int status = updateResponse.getStatus();
			System.out.println("status="+status);
			
			solrClient.commit();
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(solrClient != null) {
				try {
					solrClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 测试更新solr索引
	 */
	@Test
	public void testUpdateSolrIndex() {
		
		ConcurrentUpdateSolrClient updateSolrClient = null;
		try {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", "1212");
			document.addField("name", "this is apple");
			document.addField("price", "5.0");
			
			int queueSize = 10;
			int threadCount = 10;
			updateSolrClient = new ConcurrentUpdateSolrClient(SOLR_URL, queueSize, threadCount);
			UpdateResponse response = updateSolrClient.add(document);
			int status = response.getStatus();
			System.out.println("status="+status);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(updateSolrClient != null) {
				updateSolrClient.close();
			}
		}
		
	}
}
