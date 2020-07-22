package com.ftn.plagiator.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PaperResultPlagiator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @OneToMany(mappedBy = "paperResultPlagiator", fetch = FetchType.EAGER)
	List<ResultItem> items = new ArrayList<ResultItem>();
    
//    @OneToMany(mappedBy = "paperResultPlagiatorPaper")
//	List<Paper> similarPapers = new ArrayList<Paper>();
    
    @OneToOne()
    @JoinColumn(name = "paper_id")
	Paper uploadedPaper;
}
