package com.ftn.plagiator.elasticsearch.resultMappers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import com.ftn.plagiator.elasticsearch.model.UserElastics;

public class ContentResultMapper implements SearchResultMapper{
	
	public ContentResultMapper() {
		
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
		
		List<UserElastics> chunk = new ArrayList<UserElastics>();
        for (SearchHit searchHit : response.getHits()) {
            if (response.getHits().getHits().length <= 0) {
                return null;
            }
          
            Map<String, Object> source = searchHit.getSourceAsMap();
            UserElastics paper = new UserElastics();
            paper.setId(Long.parseLong(searchHit.getId()));
//            paper.setAuthor((String) source.get("author"));
//            paper.setTitle((String) source.get("title"));
//            paper.setCoauthor((String) source.get("coauthor"));
//            paper.setKeyTerms((String) source.get("keyTerms"));
//            paper.setAbstractForPaper((String) source.get("abstractForPaper"));
//            paper.setScientificAreaForPaper((String) source.get("scientificAreaForPaper"));
//            paper.setOpenAccess((boolean) source.get("openAccess"));
//            paper.setJournal((String) source.get("journal"));
            paper.setText((String) source.get("text"));
            paper.setName((String) source.get("name"));
           // paper.setAbstractForPaper((String) source.get("abstractForPaper"));
            
            
            
            //STARO
            
//            Gson gson = new GsonBuilder().create();
//            String jsonString = gson.toJson(source.get("scientificAreaForPaper"));
//            paper.setScientificAreaForPaper(gson.fromJson(jsonString, ScientificAreaElastic.class));
//            
//            Gson gson2 = new GsonBuilder().create();
//            String usersString = gson2.toJson(source.get("users"));
//            UserElastic[] usersElasticArray = gson2.fromJson(usersString, UserElastic[].class);
//            List<UserElastic> usersElasticList = new ArrayList<>(Arrays.asList(usersElasticArray));
//            paper.setUsers(usersElasticList);
            
//            String higlightField = "";
//            try {
//            	higlightField = "..."+searchHit.getHighlightFields().get("text").fragments()[0].toString()+"...";
//            	//higlightField = "..."+searchHit.getHighlightFields().get("abstractForPaper").fragments()[0].toString()+"...";
//            	//higlightField = "..."+searchHit.getHighlightFields().get(this.fieldToReturn).fragments()[0].toString()+"...";
//            }catch(Exception e) {
//            	higlightField = "";
//            }
            
            //paper.setContent(higlightField);
            chunk.add(paper);
        }
        if (chunk.size() > 0) {
            return new AggregatedPageImpl(chunk);
        }
       
        return null;
	}

}
