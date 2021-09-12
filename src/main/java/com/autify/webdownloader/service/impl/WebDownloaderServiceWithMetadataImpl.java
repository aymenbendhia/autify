package com.autify.webdownloader.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import com.autify.webdownloader.model.WebPageData;

@Component
public class WebDownloaderServiceWithMetadataImpl extends WebDownloaderServiceImpl{
	
	private static final String EEE_MMM_DD_KK_MM_SS_Z = "EEE MMM dd kk:mm:ss z";
	private static final String LINK_PATTERN = "a[href]";
	

	@Override
	protected WebPageData downLoadWebPage(String url) {
		WebPageData webPageData = super.downLoadWebPage(url);
		System.out.println("site: "+ webPageData.getSite());
		System.out.println("num_links: "+ Jsoup.parse(webPageData.getHtml()).select(LINK_PATTERN).size());
		System.out.println("images: "+ webPageData.getDocument().select(IMG_PATTERN).size());
		try {
			System.out.println("last_fetch: "+ new SimpleDateFormat(EEE_MMM_DD_KK_MM_SS_Z).format(Files.readAttributes(Paths.get(webPageData.getSite()+HTML_PREFIX), BasicFileAttributes.class).creationTime().toMillis()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return webPageData;
	}


}
