package com.autify.webdownloader.application;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.autify.webdownloader.conf.WebDownloaderConfiguration;
import com.autify.webdownloader.service.WebDownloaderService;
import com.autify.webdownloader.service.impl.WebDownloaderServiceImpl;
import com.autify.webdownloader.service.impl.WebDownloaderServiceWithMetadataImpl;



@SpringBootApplication(scanBasePackageClasses = { WebDownloaderConfiguration.class })
public class WebDownloaderApplication implements CommandLineRunner {
	
	private static final String METADATA_OPTION_KEYWORD = "--metadata";

	@Autowired
	private ApplicationContext applicationContext;

    	    public static void main(String[] args) {
    	        SpringApplication.run(WebDownloaderApplication.class, args);
    	    }
    	 
    	    @Override
    	    public void run(String... args) {
    	    	
    	    	if (args == null || args.length == 0) {
    	    		System.out.println("Please enter program arguments");
    	    		return;
    	    	}
    	    	
    	    	List<String> webSitesList = Arrays.asList(args);
    	    	
    	    	WebDownloaderService webDownloaderService = (WebDownloaderService) (METADATA_OPTION_KEYWORD.equals(webSitesList.get(0)) ?
    	    			applicationContext.getBean("webDownloaderServiceWithMetadataImpl") :
    	    				applicationContext.getBean("webDownloaderServiceImpl"));
    	    	
				webDownloaderService .downloadWebPages(webSitesList.stream().filter(s -> !METADATA_OPTION_KEYWORD.equals(s)).collect(Collectors.toList()));
    	    }

}
