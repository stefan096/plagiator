package com.ftn.plagiator.service;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.ftn.plagiator.elasticsearch.dto.ParamForQuery;
import com.ftn.plagiator.elasticsearch.dto.SimpleQueryDTO;
import com.ftn.plagiator.elasticsearch.enums.SearchType;
import com.ftn.plagiator.elasticsearch.model.PaperElastic;
import com.ftn.plagiator.elasticsearch.model.UserElastics;
import com.ftn.plagiator.elasticsearch.resultMappers.ContentResultMapper;


@Service
public class SearchService {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

//	@Autowired
//	private ScientificElasticRepository scientificElasticRepository;
	
//	@Autowired
//	private PaperElasticRepository paperElasticRepository;
//
//	public Page<PaperElastic> search(SimpleQueryDTO searchParams) {
//		
//		int pageNum = searchParams.getPageNum();
//
//		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
//		BoolQueryBuilder queryParams = QueryBuilders.boolQuery();
//
//		for (ParamForQuery searchParam : searchParams.getParams()) {
//			String key = searchParam.getKey();
//			String value = searchParam.getValue();
//			if ((key != null && value != null)) {
//
//				if(searchParams.isWildcardQuery()) {
//					addParametarWildcard(queryParams, key, value, searchParam.getParamType(), searchParam.isPhraseQuery());
//				}else {
//					addParametar(queryParams, key, value, searchParam.getParamType(), searchParam.isPhraseQuery());
//				}
//			}
//		}
//
//		return elasticsearchTemplate.queryForPage(addHiglightIntoQuery(searchQueryBuilder, queryParams, pageNum),
//				PaperElastic.class, new ContentResultMapper());
//	}
	
	public Page<UserElastics> search2(SimpleQueryDTO searchParams) {
		
		int pageNum = searchParams.getPageNum();

		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
		BoolQueryBuilder queryParams = QueryBuilders.boolQuery();

		for (ParamForQuery searchParam : searchParams.getParams()) {
			String key = searchParam.getKey();
			String value = searchParam.getValue();
			if ((key != null && value != null)) {

				if(searchParams.isWildcardQuery()) {
					addParametarWildcard(queryParams, key, value, searchParam.getParamType(), searchParam.isPhraseQuery());
				}else {
					addParametar(queryParams, key, value, searchParam.getParamType(), searchParam.isPhraseQuery());
				}
			}
		}

		return elasticsearchTemplate.queryForPage(addHiglightIntoQuery(searchQueryBuilder, queryParams, pageNum),
				UserElastics.class, new ContentResultMapper());
	}
	
//	//pretrazi po svim poljima
//	public Page<PaperElastic> fullSearch(SimpleQueryDTO searchParams) {
//		
//		int pageNum = searchParams.getPageNum();
//		String queryString = searchParams.getParams().get(0).getValue();
//		
//		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
//		BoolQueryBuilder queryParams = new BoolQueryBuilder();
//		
//		queryParams.should(QueryBuilders.commonTermsQuery("title", queryString));
//		queryParams.should(QueryBuilders.commonTermsQuery("coauthor", queryString));
//		queryParams.should(QueryBuilders.commonTermsQuery("keyTerms", queryString));
//		queryParams.should(QueryBuilders.commonTermsQuery("abstractForPaper", queryString));
//		queryParams.should(QueryBuilders.commonTermsQuery("journal", queryString));
//		queryParams.should(QueryBuilders.commonTermsQuery("author", queryString));
//		queryParams.should(QueryBuilders.commonTermsQuery("content", queryString));
//		queryParams.should(QueryBuilders.commonTermsQuery("text", queryString));
//		
//		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//		NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("paper", boolQuery.should(
//				QueryBuilders.commonTermsQuery("paper.title", queryString)), ScoreMode.Total);
//		
//		queryParams.should(nestedQuery);
//		
//		return elasticsearchTemplate.queryForPage(addHiglightIntoQuery(searchQueryBuilder, queryParams, pageNum), PaperElastic.class, new ContentResultMapper());
//	}
	
//	public Page<ScientificAreaElastic> executeSearchCustom(SimpleQueryDTO searchParams) {
//
//		int pageNum = searchParams.getPageNum();
//
//		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
//		BoolQueryBuilder queryParams = QueryBuilders.boolQuery();
//
//		for (ParamForQuery searchParam : searchParams.getParams()) {
//			String key = searchParam.getKey();
//			String value = searchParam.getValue();
//			if ((key != null && value != null)) {
//				addParametar(queryParams, key, value, searchParam.getParamType(), searchParam.isPhraseQuery());
//			}
//		}
//
//		return elasticsearchTemplate.queryForPage(addHiglightIntoQuery(searchQueryBuilder, queryParams, pageNum),
//				ScientificAreaElastic.class, new ContentResultMapperCustom());
//	}

	private void addParametar(BoolQueryBuilder queryParams, String key, String value, SearchType searchType,
			boolean isPhraseQuery) {
		
		switch(searchType) {
		case OR: 
			if (isPhraseQuery) 
			{
				queryParams.should(QueryBuilders.matchPhraseQuery(key, value));
			}
			else //if(key.equals("text"))     
			{
				queryParams.should(QueryBuilders.commonTermsQuery(key, value));
			}
//			else 
//			{
//				queryParams.should(QueryBuilders.wildcardQuery(key, "*" + value + "*"));
//			}
			break;
			
		case MUST_NOT: 
			if (isPhraseQuery) 
			{
				queryParams.mustNot(QueryBuilders.matchPhraseQuery(key, value));
			}
			else //if(key.equals("text"))     
			{
				queryParams.mustNot(QueryBuilders.commonTermsQuery(key, value));
			}
//			else 
//			{
//				queryParams.mustNot(QueryBuilders.wildcardQuery(key, "*" + value + "*"));
//				
//			}
			break;
			
		case AND: 
			if (isPhraseQuery) 
			{
				queryParams.must(QueryBuilders.matchPhraseQuery(key, value));
			}   
//			else if(key.equals("text"))     
//			{
//				queryParams.must(QueryBuilders.commonTermsQuery(key, value));
//			}
			else {
				//queryParams.must(QueryBuilders.wildcardQuery(key, "*" + value + "*"));
				//queryParams.must(QueryBuilders.prefixQuery(key, value));
				queryParams.must(QueryBuilders.commonTermsQuery(key, value)/*.analyzer("serbian-analyzer")*/);
				//queryParams.mu
				//serbian-analyzer
			}
			break;
			
		default:
			break;
		}

	}
	
	private void addParametarWildcard(BoolQueryBuilder queryParams, String key, String value, SearchType searchType,
			boolean isPhraseQuery) {
		
		switch(searchType) {
		case OR: 
			if (isPhraseQuery) 
			{
				queryParams.should(QueryBuilders.matchPhraseQuery(key, value));
			}
			else if(key.equals("text"))     
			{
				queryParams.should(QueryBuilders.commonTermsQuery(key, value));
			}
			else 
			{
				queryParams.should(QueryBuilders.wildcardQuery(key, "*" + value + "*"));
			}
			break;
			
		case MUST_NOT: 
			if (isPhraseQuery) 
			{
				queryParams.mustNot(QueryBuilders.matchPhraseQuery(key, value));
			}
			else if(key.equals("text"))     
			{
				queryParams.mustNot(QueryBuilders.commonTermsQuery(key, value));
			}
			else 
			{
				queryParams.mustNot(QueryBuilders.wildcardQuery(key, "*" + value + "*"));
				
			}
			break;
			
		case AND: 
			if (isPhraseQuery) 
			{
				queryParams.must(QueryBuilders.matchPhraseQuery(key, value));
			}   
			else if(key.equals("text"))     
			{
				queryParams.must(QueryBuilders.commonTermsQuery(key, value)/*.analyzer("serbian-analyzer")*/);
			}
			else {
				//queryParams.must(QueryBuilders.wildcardQuery(key, "*" + value + "*"));
				//queryParams.must(QueryBuilders.prefixQuery(key, value));
				queryParams.must(QueryBuilders.wildcardQuery(key, "*" + value + "*"));
				//serbian-analyzer
			}
			break;
			
		default:
			break;
		}

	}
	
//	private void addNestedParam(BoolQueryBuilder queryParams, String value, SearchType searchType, boolean isPhraseQuery) {
//		
//		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//		NestedQueryBuilder nestedQuery = null;
//		
//		
//		switch(searchType) {
//		case OR:
//			if (isPhraseQuery) 
//			{
//				nestedQuery = QueryBuilders.nestedQuery("paper", boolQuery.should(QueryBuilders.matchPhraseQuery("paper.title", value)), ScoreMode.None);
//			} 
//			else 
//			{
//				nestedQuery = QueryBuilders.nestedQuery("paper", boolQuery.should(QueryBuilders.commonTermsQuery("paper.title", value)), ScoreMode.None);
//			}
//			
//			queryParams.should(nestedQuery);
//			break;
//			
//		case MUST_NOT: 
//			if (isPhraseQuery) 
//			{
//				nestedQuery = QueryBuilders.nestedQuery("paper", boolQuery.mustNot(QueryBuilders.matchPhraseQuery("paper.title", value)), ScoreMode.None);
//			} 
//			else 
//			{
//				nestedQuery = QueryBuilders.nestedQuery("paper", boolQuery.mustNot(QueryBuilders.commonTermsQuery("paper.title", value)), ScoreMode.None);
//			}
//			
//			queryParams.mustNot(nestedQuery);
//			break;
//		
//		case AND: 
//			if (isPhraseQuery) 
//			{
//				nestedQuery = QueryBuilders.nestedQuery("paper", boolQuery.must(QueryBuilders.matchPhraseQuery("paper.title", value)), ScoreMode.None);
//			} 
//			else 
//			{
//				nestedQuery = QueryBuilders.nestedQuery("paper", boolQuery.must(QueryBuilders.commonTermsQuery("paper.title", value)), ScoreMode.None);
//			}
//			
//			queryParams.must(nestedQuery);
//			break;
//		default:
//			break;
//		}
//	
//	}

	private SearchQuery addHiglightIntoQuery(NativeSearchQueryBuilder searchQueryBuilder, BoolQueryBuilder queryParams, int pageNum) {
		
		return searchQueryBuilder.withQuery(queryParams).withHighlightFields(
	            new HighlightBuilder.Field("text")
	                .preTags("<b>")
	                .postTags("</b>")
	                .numOfFragments(1)
	                .fragmentSize(150)
	    ).withPageable(PageRequest.of(pageNum-1, 10)).build();
		
	}
	
//    public List<ScientificAreaElastic> getAllBySearchParam(String filter, Pageable pageable){
//        Page<ScientificAreaElastic> pagedPaper = scientificElasticRepository.findByFilter(filter, pageable);
//        
//        List<ScientificAreaElastic> entities = new ArrayList<ScientificAreaElastic>();
//        pagedPaper.forEach(item -> {
//        	entities.add(new ScientificAreaElastic(item.getId(), item.getName()));
//        });
//
//        return entities;
//    }
    
//	public List<PaperElastic> moreLikeThisSearch(Long documentId, int pageNum) {
//		
//		PaperElastic paperElastic = null;
//		
//		try {
//			paperElastic = paperElasticRepository.findById(documentId).get();
//		}catch(Exception e) {
//			return null;
//		}
//		
//		MoreLikeThisQueryBuilder moreLikeThisQuery = QueryBuilders.moreLikeThisQuery(new String[]{"text"},
//				new String[]{paperElastic.getText()}, new Item[] {new Item("paper", "paper", 
//						paperElastic.getId().toString())})
//				.minDocFreq(1)
//				//.maxDocFreq(12)
//				.minTermFreq(2)
//				.minimumShouldMatch("50%");
//		
//		SearchQuery theQuery = new NativeSearchQueryBuilder().withQuery(moreLikeThisQuery).build();
//		
//		return elasticsearchTemplate.queryForList(theQuery, PaperElastic.class);
//	}
//	
//	public List<PaperElastic> moreLikeThisSearchText(String text, Long id, int pageNum) {
//				
//		MoreLikeThisQueryBuilder moreLikeThisQuery = QueryBuilders.moreLikeThisQuery(new String[]{"text"},
//				new String[]{text}, new Item[] {new Item("paper", "paper", 
//						id.toString())})
//				.minDocFreq(1)
//				//.maxDocFreq(12)
//				.minTermFreq(2)
//				.minimumShouldMatch("50%");
//		
//		SearchQuery theQuery = new NativeSearchQueryBuilder().withQuery(moreLikeThisQuery).build();
//		
//		return elasticsearchTemplate.queryForList(theQuery, PaperElastic.class);
//	}
//
//	public List<UserElastic> geoDistanceSearch(List<Location> locations) { //moze biti vise lokacija
//		BoolQueryBuilder boolQueryAnd = QueryBuilders.boolQuery();
//		
//		for (Location location : locations) {
//			QueryBuilder filterForLocation = QueryBuilders.geoDistanceQuery("location")
//					.point(location.getLatitude(), location.getLongitude()) //latitude, longitude
//					.distance(100, DistanceUnit.KILOMETERS); //koji se nalaze u regiji od 100km
//			
//			boolQueryAnd.should(filterForLocation);
//		}
//		
//		
//		BoolQueryBuilder boolQueryForLocations = QueryBuilders.boolQuery();
//		boolQueryForLocations.mustNot(boolQueryAnd); //meni trebaju oni koji tu nisu
//		
//		//boolQueryForLocations.should(QueryBuilders.commonTermsQuery("idJournalString", "1"));
//			
//		return elasticsearchTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(boolQueryForLocations)
//				.build(), UserElastic.class);
//	}
	
	
	public Page<PaperElastic> listaRadova(String text) {
	
//		  Tokenizer source = new StandardTokenizer();
//		  TokenStream src = new LowerCaseFilter(source);
//		  
//		  ShingleFilter filter = new ShingleFilter(src, 2, 3);
//		  filter.setOutputUnigrams(false);
//		  
//		  TokenStreamComponents tsc = new TokenStreamComponents(source, filter);
		  
		  
//		  Analyzer analyzer = new Analyzer() {
//			
//			@Override
//			protected TokenStreamComponents createComponents(String fieldName) {
//				
//				Tokenizer source = new StandardTokenizer();
//				TokenStream src = new LowerCaseFilter(source);
//			  
//				ShingleFilter filter = new ShingleFilter(src, 2, 3);
//				filter.setOutputUnigrams(false);
//			  
//				TokenStreamComponents tsc = new TokenStreamComponents(source, filter);
//				return tsc;
//			}
//		};
		
		MatchQueryBuilder queryParams = QueryBuilders
				.matchQuery("text", text);//.minimumShouldMatch("30%"); //u kom procentu zelim poklapanja
				//.analyzer(analyzer)
				//.build();
		
		SearchQuery theQuerySS = new NativeSearchQueryBuilder()
				.withQuery(queryParams)
				//.withMinScore(3)  //koliko zelim da bude poklapanje
				.build();

		return elasticsearchTemplate.queryForPage(theQuerySS, PaperElastic.class, new ContentResultMapper());
	}
	

	

}
