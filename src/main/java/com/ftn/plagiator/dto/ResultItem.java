package com.ftn.plagiator.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResultItem {
	private List<PaperDTO> papers;
	private int partOfPage;
}
