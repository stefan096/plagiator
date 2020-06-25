//package com.ftn.plagiator.elasticsearch.model;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Document(indexName = "paper2", type = "paper2")
//@NoArgsConstructor
//@Getter
//@Setter
//public class PaperElastic { //IndexUnit osnovna jedinica indeksiranja
//	@Id
//	@Field(type = FieldType.Long)
//	private Long id;
//	
//	@Field(type = FieldType.Text)
//	private String title;
////	
////	@Field(type = FieldType.Text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
////	private String coauthor; //polja cu cuvati kao stringove posto mi je dovoljno smao njihovo ime
////							// nema potrebe za svim podacima prilikom search-a
////	@Field(type = FieldType.Text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
////	private String keyTerms; //analogno ce da vazi za sva ostala polja
////	
////	@Field(type = FieldType.Text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
////	private String abstractForPaper;
////	
////	@Field(type = FieldType.Text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
////	private String scientificAreaForPaper;
////	
////	@Field(type = FieldType.Text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
////	private String journal;
////	
////	@Field(type = FieldType.Text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
////	private String author;
////	
////	@Field(type = FieldType.Text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
////	private String content;
////	
////	@Field(type = FieldType.Text, analyzer = "serbian-analyzer", searchAnalyzer = "serbian-analyzer")
////	private String text;
//	
//	@Field(type = FieldType.Boolean)
//	private boolean openAccess;
//	
////	@Field(type = FieldType.Nested)
////	private List<UserElastic> users; // recezenti
//	
////	public PaperElastic(Paper paper) {
////		this.id = paper.getId();
////		this.title = paper.getTitle();
////		this.author = paper.getAuthor().getName() + " " + paper.getAuthor().getLastName();
////		this.keyTerms = paper.getKeyTerms();
////		this.abstractForPaper = paper.getAbstractForPaper();
////		this.scientificAreaForPaper = paper.getScientificAreaForPaper().getName();
////		this.journal = paper.getJournalPaper().getName();
////		if (this.openAccess = paper.getJournalPaper().getPaymentMethod().equals(PaymentMethod.CHARGE_AUTHORS)) {
////			this.openAccess = true;
////		} else {
////			this.openAccess = false;
////		}
////	}
//	
//}
