package com.autify.webdownloader.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.autify.webdownloader.model.WebPageData;
import com.autify.webdownloader.service.WebDownloaderService;

@Component
public class WebDownloaderServiceImpl implements WebDownloaderService{
	
	private static final String ABS_SRCSET_PATTERN = "abs:srcset";
	private static final String ABS_SRC_PATTERN = "abs:src";
	protected static final String HTML_PREFIX = ".html";
	protected static final String IMG_PATTERN = "img";
	
	@Override
	public void downloadWebPages(List<String> webSitesList) {
		webSitesList.stream().forEach(w -> downLoadWebPage(w));
		
	}

	protected WebPageData downLoadWebPage(String url) {
		String html = null;
		String host = null;
		Document document = null;
		try {
			host = new URI(url).getHost();
			String htmlFileName = host+HTML_PREFIX;
			Files.deleteIfExists(Paths.get(htmlFileName));
			document = Jsoup.connect(url).get();
			html = document.html();
			html = downloadAssets(document, html);
			PrintWriter writer = new PrintWriter(htmlFileName, "UTF-8");
			writer.print(html);
			writer.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new WebPageData(host, document, html);
	}

	private String downloadAssets(Document document, String html) {
        Elements imageElements = document.select(IMG_PATTERN);
        String shadowHtml = html;
        for(Element imageElement : imageElements){
            
            String strImageURL = imageElement.attr(ABS_SRC_PATTERN);
            
            String strSetImageURL = imageElement.attr(ABS_SRCSET_PATTERN);
            
            shadowHtml = Strings.isBlank(strImageURL) || Strings.isBlank(strSetImageURL) ? shadowHtml : 
            		downloadSingleImage(strImageURL,  strSetImageURL, shadowHtml);
            
            
        }
        return shadowHtml;
		
	}

	private String downloadSingleImage(String strImageURL, String strSetImageURL, String html) {

        
        try {
        	String destFoler = strImageURL.split(new URI(strImageURL).getHost())[1];
            String strImageName = 
                    strImageURL.substring( strImageURL.lastIndexOf("/") + 1 );
            strSetImageURL =strSetImageURL.split(new URI(strImageURL).getHost())[1]; 
            URL urlImage = new URL(strImageURL);
            InputStream in = urlImage.openStream();
            
            byte[] buffer = new byte[4096];
            int n = -1;
            
            		
            html = html.replaceAll(strSetImageURL, destFoler);
            html = html.replaceAll(destFoler, "."+destFoler);
            
            destFoler = "."+destFoler;
            
            destFoler = destFoler.replace(strImageName, "");
            
            new File(destFoler).mkdirs();
            
            OutputStream os = 
                new FileOutputStream(destFoler + strImageName );
            
            while ( (n = in.read(buffer)) != -1 ){
                os.write(buffer, 0, n);
            }
            
            os.close();
            
            
        }catch (ArrayIndexOutOfBoundsException e) {
        } catch (Exception e) {
			System.out.println(e.getMessage());
        }
        return html;
		
	}


}
