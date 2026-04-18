package com.demo.config;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**/
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	// Root context for services, repositories
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppContext.class };
	}

	// Servlet context for controllers, view resolvers
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// This is the Spring MVC configuration
		return new Class[] { WebMvcConfig.class };
	}

	// Map the DispatcherServlet to the root URL "/"
	@Override
	protected @NonNull String[] getServletMappings() {
		return new String[] { "/" };
	}
}