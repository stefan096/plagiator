package com.ftn.plagiator.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaperResultPlagiator {
	List<ResultItem> items = new ArrayList<ResultItem>();
}
