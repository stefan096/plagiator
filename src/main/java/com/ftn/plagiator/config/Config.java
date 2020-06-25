//package com.ftn.plagiator.config;
//
//import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//import org.elasticsearch.client.Client;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.TransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//import com.ftn.plagiator.elasticsearch.plugin.SerbianPlugin;
//
//@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.ftn.plagiator.elasticsearch.repository")
//public class Config {
//
//    @Value("${elasticsearch.home}")
//    private String esHome;
//
//    @Value("${elasticsearch.host}")
//    private String esHost;
//
//    @Value("${elasticsearch.port}")
//    private int esPort;
//
//    @Value("${elasticsearch.clustername}")
//    private String esClusterName;
//
//	@Bean
//    public Client client() {
//		
////		File tmpDir = null;
////		try {
////			tmpDir = new File("/placeForData");
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//		
//        Settings esSettings = Settings.builder()
//                .put("client.transport.sniff", true)
//                .put("path.home", esHome)
//    			.put("cluster.name", "elasticsearch")
//				.put("transport.type", "netty4")
//                .put("cluster.name", esClusterName).build();
//        
////		.put("cluster.name", "elasticsearch")
////		.put("transport.type", "netty4").put("client.transport.sniff", true)
////		.put("path.data", new File(tmpDir, "data").getAbsolutePath())
////		.put("path.logs", new File(tmpDir, "logs").getAbsolutePath())
////		.put("path.home", tmpDir.getAbsolutePath()).build();
//        
//        
////        TransportClient client = TransportClient.builder()
////        	    .addPlugin(SerbianPlugin.class)
////        	    .settings(esSettings)
////        	    .addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
//        
////        Collection<Class<? extends Plugin>> plugins = new ArrayList<>();
////		plugins.add(SerbianPlugin.class);
//		//plugins.add(Netty4Plugin.class);
//        		
//		@SuppressWarnings("resource")
//		TransportClient client = new PreBuiltTransportClient(esSettings, SerbianPlugin.class);
//        try {
//
//        	
//            client.addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
//            
//        } catch (IOException e) {
//          e.printStackTrace();
//      }
//            
////            String[] indexesForAnalyzer = new String[] { 
////                    	"new_user"
////					};
////					
////					for (String indexName : indexesForAnalyzer) {
////					boolean indexExists = client.admin().indices()
////					.prepareExists(indexName)
////					.execute().actionGet().isExists();
////					if(!indexExists){
////					client.admin().indices().prepareCreate(indexName)
////					    .setSettings(jsonBuilder()
////					            .startObject()
////					            .startObject("analysis")
////					            .startObject("analyzer")
////					            .startObject("default")
////					            .field("type", "custom")
////					            .field("tokenizer", "keyword")
////					            .field("filter", new String[]{"lowercase"})
////					            .endObject()
////					            .endObject()
////					            .endObject()
////					            .endObject())
////					    .execute().actionGet();
////					}
////					}
////					        }
////        catch (UnknownHostException e) {
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        
//
////
////		Node node;
////		try {
////			node = new NodeWithPlugins(InternalSettingsPreparer.prepareEnvironment(esSettings, null), plugins).start();
////			return node.client();
////		} catch (NodeValidationException e) {
////			e.printStackTrace();
////		}
//        
//        
//        return client;
//    }
//
//	@Bean
//    public ElasticsearchOperations elasticsearchTemplateSS() {
//        return new ElasticsearchTemplate(client());
//    }
//	
////	@Bean
////	public ElasticsearchTemplate elasticsearchTemplate() {
////		return new ElasticsearchTemplate(nodeClient());
////	}
//
////	private class NodeWithPlugins extends Node {
////		protected NodeWithPlugins(Environment environment, Collection<Class<? extends Plugin>> list) {
////			super(environment, list);
////		}
////	}
//}
//    
