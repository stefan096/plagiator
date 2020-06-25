package com.ftn.plagiator.elasticsearch.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SimpleQueryDTO {
	
	private List<ParamForQuery> params;
	private int pageNum;
	private boolean allFields;
	private boolean wildcardQuery;
	
}
