//package com.scientific.centre.elasticsearch.resultMappers;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.search.SearchHit;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.core.SearchResultMapper;
//import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
//import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
//
//import com.scientific.centre.elasticsearch.model.ScientificAreaElastic;
//
//public class ContentResultMapperCustom implements SearchResultMapper{
//	
//	public ContentResultMapperCustom() {
//		
//	}
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Override
//	public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
//		
//		List<ScientificAreaElastic> chunk = new ArrayList<ScientificAreaElastic>();
//        for (SearchHit searchHit : response.getHits()) {
//            if (response.getHits().getHits().length <= 0) {
//                return null;
//            }
//            Map<String, Object> source = searchHit.getSourceAsMap();
//            ScientificAreaElastic indexUnit = new ScientificAreaElastic();     
//            indexUnit.setName((String) source.get("name"));
//            
//            chunk.add(indexUnit);
//        }
//        if (chunk.size() > 0) {
//            return new AggregatedPageImpl(chunk);
//        }
//       
//        return null;
//	}
//
//}
