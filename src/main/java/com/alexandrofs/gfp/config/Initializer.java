package com.alexandrofs.gfp.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Initializer implements WebApplicationInitializer {

	private static Logger LOG = Logger.getLogger(Initializer.class);
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.alexandrofs.gfp.config");
        servletContext.addListener(new ContextLoaderListener(context));
		LOG.info("Teste");
	}
}
