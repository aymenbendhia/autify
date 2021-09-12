package com.autify.webdownloader.model;

import org.jsoup.nodes.Document;

public class WebPageData {

	private String site;
	private Document document;
	private String html;
	
	public WebPageData(String site, Document document, String html) {
		super();
		this.site = site;
		this.document = document;
		this.html = html;
	}
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	
	
}
