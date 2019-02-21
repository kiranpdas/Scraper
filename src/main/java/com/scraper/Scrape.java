package com.scraper;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.json.JSONObject;
import org.json.JSONArray;

public class Scrape {
	public static void main(String[] args) throws Exception {

		Document doc;
		Document docAsn;
		String name;
		String reg;
		String ip;
		String asn;
		String asnLink;
		String nxt = null;
		ArrayList<String> countries = new ArrayList<String>();
		ArrayList<AsnDet> asns = new ArrayList<AsnDet>();
		AsnDet asnDet;

		doc = Jsoup.connect("https://ipinfo.io/countries").timeout(100000).get();
		for (Element row : doc.select("table.table-striped tr")) {
			if (!row.select("a").attr("href").equals(""))
				countries.add(row.select("a").attr("href"));
		}

		doc = null;
		System.out.println("len" + countries.size());

		for (String country : countries) {
			System.out.println("Country " + country);
			doc = Jsoup.connect("https://ipinfo.io" + country).timeout(100000).get();

			do {

				nxt = doc.select("li.next").select("a").attr("href"); // page
				for (Element row : doc.select("table.table-striped.fixed-table tr")) {
					asn = row.select("td:nth-of-type(1)").text();
					name = row.select("td:nth-of-type(2)").text();
					ip = row.select("td:nth-of-type(3)").text();
					asnLink = row.select("a").text();
					asnDet = new AsnDet();

					if (asn.equals("")) {
						continue;
					} else {
						docAsn = Jsoup.connect("https://ipinfo.io/" + asnLink).timeout(100000).get();
						reg = docAsn.select("div.pt-md-1.col-6:nth-of-type(4) > .pb-md-1.pt-md-1.font-medium").text();
						asnDet.setAsn(asn);
						asnDet.setCountry(country.replace("/countries/", ""));
						asnDet.setName(name);
						asnDet.setIp(ip);
						asnDet.setReg(reg);
						asns.add(asnDet);
						System.out.println("Fetching " + asn);
					} // end of asn

				} // end of countries' details

				if (!nxt.isEmpty()) {

					doc = Jsoup.connect("https://ipinfo.io" + nxt).get();
				}

			} while (!nxt.isEmpty()); // end of while

		} // end of list of countries

		

		
		
		// Sort the asns
				System.out.println("Sorting....");
				Collections.sort(asns, new Comparator<AsnDet>() {
					public int compare(AsnDet asn1, AsnDet asn2) {
						return Integer.valueOf(asn1.getAsnNum()).compareTo(asn2.getAsnNum());
					}
				});
		
		// add to json object
		JSONArray root = new JSONArray();
		System.out.println("Creating JSON Object.....");
		for (AsnDet asnDet1 : asns) {

			JSONObject jsonObj = new JSONObject().put(asnDet1.getAsnVal(),
					new JSONObject().put("name", asnDet1.getName()).put("country", asnDet1.getCountry())
							.put("num_ip_addresses", asnDet1.getIp().replace(",", ""))
							.put("registry", asnDet1.getReg()));
			root.put(jsonObj);
		}

		asns = null;

		

		// write to file
		try  {
			FileWriter file = new FileWriter("ASN_ip.json");
			System.out.println("Writing....");
			file.write(root.toString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("....End");

	} // end of main

}// end of class
