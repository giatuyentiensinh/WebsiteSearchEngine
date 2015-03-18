package com.hust.models;

import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class GetResult {

	public static void showResult(Map<String, Object> source) {
		for (String key : source.keySet()) {
			System.out.printf("| %-12s | %s \n", key, source.get(key));
		}
	}

	public static void showHist(SearchResponse response) {
		SearchHits hits = response.getHits();
		System.out.println("Total result: " + hits.getTotalHits());
		System.out.println("Max score: " + hits.getMaxScore());
		SearchHit[] resHits = hits.getHits();
		System.out.println("Number show result: " + resHits.length);
		for (SearchHit result : resHits) {
			System.out
			.println("---------------------------------------Information---------------------------------------");
			System.out.printf("| %-12s | %-27f |\n", "score", result.getScore());
			System.out.printf("| %-12s | %-27s |\n", "_ID", result.getId());
			showResult(result.getSource());
			System.out
			.println("-----------------------------------------------------------------------------------------");
		}
	}

}
