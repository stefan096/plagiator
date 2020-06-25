package com.ftn.plagiator.elasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "new_user", type="new_user")
public class UserElastics {
	@Id
	private Long id;
	//@Field(type = FieldType.Text, analyzer = "serbian-analyzer",searchAnalyzer = "serbian-analyzer")
	@Field(type = FieldType.Text, store = true, analyzer="serbian-analyzer", searchAnalyzer = "serbian-analyzer")
    private String name;
    private String lastName;
    
    private String email;
    
//	@Field(type = FieldType.Text)
	//private String abstractForPaper;
    
  
    @Field(type=FieldType.Text)
    private String text;
}


