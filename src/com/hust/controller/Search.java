package com.hust.controller;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.hust.cons.ConstFieldValue;
import com.hust.models.Connect;
import com.hust.models.GetResult;

public class Search {

	public static void search(Client client, String fields, String value) {
		// match all Query
		// QueryBuilder qb = QueryBuilders.matchAllQuery();

		// match query
		QueryBuilder qb = QueryBuilders.matchQuery("_all", value);

		
		// QueryBuilder qb = QueryBuilders.matchQuery(fields, value)
		// .operator(Operator.AND).zeroTermsQuery(ZeroTermsQuery.ALL);

		// multimatch query
		// QueryBuilder qb = QueryBuilders.multiMatchQuery("Hà Nội",
		// "formatted_address", "diachi");

		// bool query
		// QueryBuilder qb = QueryBuilders.boolQuery()
		// .must(QueryBuilders.termQuery("dientich", "67"))
		// .must(QueryBuilders.termQuery("gia", "12"));

		// boosting query
		// QueryBuilder qb = QueryBuilders.boostingQuery()
		// .positive(QueryBuilders.termQuery("dientich", "67"))
		// .negative(QueryBuilders.termQuery("gia", "12"))
		// .negativeBoost(0.2f);

		// common terms query
		// QueryBuilder qb = QueryBuilders.commonTerms("diachi", "Ha Noi")
		// .cutoffFrequency(0.01f);

		// constant score query
		// QueryBuilder qb = QueryBuilders.constantScoreQuery(
		// QueryBuilders.termQuery("gia", "12")).boost(1.2f);

		// dis max Query
		// QueryBuilder qb = QueryBuilders.disMaxQuery()
		// .add(QueryBuilders.matchQuery(fields, value))
		// .add(QueryBuilders.termQuery("gia", "1.2"));

		// Fuzzy like this Query
		// QueryBuilder qb = QueryBuilders.fuzzyLikeThisQuery("dientich")
		// .likeText("100 m").maxQueryTerms(12);

		// More like this Query
		// QueryBuilder qb = QueryBuilders.moreLikeThisQuery("diachi")
		// .likeText("Phố Hào Nam").minTermFreq(1).maxQueryTerms(12);

		// prefix Query
		// QueryBuilder qb = QueryBuilders.prefixQuery("gia", "11");

		// query string Query
		// QueryBuilder qb =
		// QueryBuilders.queryString("Mỹ Đình, Từ Liêm, Hà Nội");

		// span first Query
		// QueryBuilder qb = QueryBuilders.spanFirstQuery(
		// QueryBuilders.spanTermQuery("sotang", "2"), 1);

		// span near Query
		// QueryBuilder qb = QueryBuilders
		// .spanNearQuery()
		// .clause(QueryBuilders
		// .spanTermQuery("sodienthoai", "0975382332")).slop(12)
		// .inOrder(false).collectPayloads(false);

		SearchResponse response = client.prepareSearch(ConstFieldValue.INDEX)
				.setTypes(ConstFieldValue.TYPE).setQuery(qb).execute()
				.actionGet();
		GetResult.showHist(response);
		System.out.println(qb.toString());
	}

	public static void filterSearch(Client client, String field, String value) {

		// range Filter
		// FilterBuilder fb = FilterBuilders.rangeFilter("number").from(200);

		// exist Filter
		// FilterBuilder fb =
		// FilterBuilders.existsFilter("location.geometry.location");

		// geo distance Filter
		 FilterBuilder fb = FilterBuilders
		 .geoDistanceFilter("location.geometry.location")
		 .point(20.9942627, 105.8428367)
		 .distance(1000, DistanceUnit.KILOMETERS).optimizeBbox("memory");

		// geo distance range Filter
		// FilterBuilder fb = FilterBuilders
		// .geoDistanceRangeFilter("location.geometry.bounds.southwest")
		// .point(20.9942627, 105.8428367).from("20km").to("100km")
		// .includeLower(true).includeUpper(false).optimizeBbox("memory");

		// geo polygon Filter
		// FilterBuilder fb = FilterBuilders
		// .geoPolygonFilter("location.geometry.location")
		// .addPoint(20.9943617, 105.8433996).addPoint(21, 106)
		// .addPoint(223.17, -105.8433996);

		// math all Filter
		// FilterBuilder fb = FilterBuilders.matchAllFilter();

		// miss Filter
		// FilterBuilder fb = FilterBuilders.missingFilter("_id");

		// not Filter
		// FilterBuilder fb = FilterBuilders.notFilter(FilterBuilders
		// .rangeFilter("loc").from(0).to(31.442));

		// prefix Filter
		// FilterBuilder fb = FilterBuilders.prefixFilter("loaibds", "bán");

		// query filter
//		FilterBuilder fb = FilterBuilders.queryFilter(QueryBuilders
//				.queryString("Hà Nội OR Ha noi OR Hanoi"));

		SearchResponse response = client.prepareSearch(ConstFieldValue.INDEX)
				.setPostFilter(fb).execute().actionGet();

		// SearchResponse response =
		// client.prepareSearch("try").setPostFilter(fb)
		// .execute().actionGet();

		System.out.println(response);
//		GetResult.showHist(response);
		System.out.println(fb);
	}

	public static void main(String[] args) {
		Client client = Connect.getClient();
//		search(client, "diachi", "Dương Nội, Hà Đông, Hà Nội");
		 filterSearch(client, "", "");
	}
}
