package com.ftn.plagiator.elasticsearch.dto;

import com.ftn.plagiator.elasticsearch.enums.SearchType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ParamForQuery {
	private String key;
	private String value;
	private boolean phraseQuery;
	private SearchType paramType;
}
