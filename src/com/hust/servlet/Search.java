package com.hust.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.MultiSearchResponse.Item;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.hust.cons.ConstFieldValue;
import com.hust.models.Connect;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Search() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String value = new String(request.getParameter("search").getBytes(
				"iso-8859-1"), "UTF-8");

		String[] type = value.split("::");

		QueryBuilder qb = null;
		FilterBuilder fb = null;
		if (type[0].equals("q")) {

			Map<String, String> data = new HashMap<String, String>();
			String[] fields = type[1].split("\\$");
			Client client = Connect.getClient();
			MultiSearchRequestBuilder msqb = new MultiSearchRequestBuilder(
					client);

			for (String str : fields) {
				String[] split = str.split("\\:");
				data.put(split[0], split[1]);
				qb = QueryBuilders.boolQuery().must(
						QueryBuilders.matchQuery(split[0], split[1]));
				SearchRequestBuilder sqb = client.prepareSearch(ConstFieldValue.INDEX)
						.setTypes(ConstFieldValue.TYPE).setQuery(qb);
				System.out.println(sqb);
				msqb.add(sqb);
			}
			// Thua
			MultiSearchResponse msr = msqb.execute().actionGet();
			Item[] responses = msr.getResponses();
			for(int i = 0; i < fields.length; i++) {
				SearchHit[] hits = responses[i].getResponse().getHits().getHits();
				for (SearchHit result : hits) {
					System.out
					.println("---------------------------------------Information---------------------------------------");
					System.out.printf("| %-12s | %-27f |\n", "score", result.getScore());
					System.out.printf("| %-12s | %-27s |\n", "_ID", result.getId());
					for (String key : result.getSource().keySet()) {
						System.out.printf("| %-12s | %s \n", key, result.getSource().get(key));
					}
					System.out
					.println("-----------------------------------------------------------------------------------------");
				}
			}
			// -Thua
			
			
			// qb = QueryBuilders.matchQuery(key, data.get(key));

			choiseQuery(qb, fb, response);
		} else if (type[0].equals("f")) {
			String[] fields = type[1].split(",");
			Double lat = Double.parseDouble(fields[1]);
			Double lon = Double.parseDouble(fields[0].trim());
			Double distance = Double.parseDouble(fields[2].trim());
			fb = FilterBuilders.geoDistanceFilter("location.geometry.location")
					.point(lat, lon)
					.distance(distance, DistanceUnit.KILOMETERS);
			choiseQuery(qb, fb, response);
		} else {
			qb = QueryBuilders.matchQuery("_all", value.trim());
			choiseQuery(qb, fb, response);
		}

	}

	private void choiseQuery(QueryBuilder qb, FilterBuilder fb,
			HttpServletResponse response) {
		Client client = Connect.getClient();
		if (qb != null) {
			SearchResponse response2 = client
					.prepareSearch(ConstFieldValue.INDEX)
					.setTypes(ConstFieldValue.TYPE).setQuery(qb).execute()
					.actionGet();
			
			showResult(response2, response);
		} else {
			SearchResponse response2 = client
					.prepareSearch(ConstFieldValue.INDEX)
					.setTypes(ConstFieldValue.TYPE).setPostFilter(fb).execute()
					.actionGet();
			showResult(response2, response);
		}
	}

	private void showResult(SearchResponse response2, HttpServletResponse response) {
		try {
			SearchHit[] hits = response2.getHits().getHits();
			PrintWriter out = response.getWriter();
			out.println("<br /> Total     : " + response2.getHits().getTotalHits());
			out.println("<br /> Max score : " + response2.getHits().getMaxScore());
			out.println("<br /> Time      : " + response2.getTook());
			
			
			for (SearchHit searchHit : hits) {
				Map<String, Object> result = searchHit.getSource();
				out.println("<br />------------------------------------------------------------------------------Information------------------------------------------------------------------------------");
				for (String key : result.keySet()) {
					out.printf("<br />| " + key + "  |    " + result.get(key));
				}
				out.println("<br />-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
