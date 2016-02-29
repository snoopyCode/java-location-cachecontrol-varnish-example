package com.sample.cache.boundry;


import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sample.cache.business.CacheControlExample;
import com.sample.cache.business.HazelcastExample;
import com.sample.cache.business.JCacheExample;


@Stateless
@Path("/")
public class SampleResource {
	
	@Inject
	JCacheExample slow;
	
	@Inject
	HazelcastExample hazelcast;
	
	@Inject
	CacheControlExample cacheControl;

	@GET
	@Path("/jcache")
	@Produces( MediaType.TEXT_PLAIN )
	public Response locations(@Context HttpHeaders httpHeaders, @Context HttpServletResponse response,
	        @Context HttpServletRequest request) {
             	     
        System.out.println("Start slow request");
        long before = System.currentTimeMillis() / 1000L;
        slow.doSlowOperation("1", "2");
        long timetorun = System.currentTimeMillis() / 1000L - before;
        System.out.println("End slow request");        
		
        return Response.status(Response.Status.OK).entity("It took "+timetorun+" to do the slow operation").build();

	}
	
	@GET
	@Path("/hazelcast")
	@Produces( MediaType.TEXT_PLAIN )
	public Response hazelcast(@Context HttpHeaders httpHeaders, @Context HttpServletResponse response,
	        @Context HttpServletRequest request) {
             	     
		hazelcast.init();
		
		String savedData = hazelcast.saveData();
        System.out.println("Saved data: "+savedData);        
		
        return Response.status(Response.Status.OK).entity("Saved data: "+savedData).build();

	}
	
	@GET
	@Path("/cachecontrol/")
	@Produces( MediaType.TEXT_PLAIN )
	public Response cachecontrol(@QueryParam("latitude") String latitude,
			@QueryParam("longitude") String longitude,
			@Context HttpHeaders httpHeaders, @Context HttpServletResponse response,
	        @Context HttpServletRequest request) {
             	     
        System.out.println("Start slow request");
        long before = System.currentTimeMillis() / 1000L;
        cacheControl.doSlowOperation("1", "2");
        long timetorun = System.currentTimeMillis() / 1000L - before;
        System.out.println("End slow request");  
        
        CacheControl cc = CacheControl.valueOf("public, max-age=86400");
        
        ResponseBuilder builder = Response.status(Response.Status.OK);
        builder.cacheControl(cc);
        builder.entity("It took "+timetorun+" to do the slow operation with "+latitude+" and "+longitude);
		
        return builder.build();

	}
	
	@GET
	@Path("/health")
	@Produces( MediaType.TEXT_PLAIN )
	public Response health(@Context HttpHeaders httpHeaders, @Context HttpServletResponse response,
	        @Context HttpServletRequest request) {
             
		System.out.println("Health check");
        	
        return Response.status(Response.Status.OK).entity("healthy").build();

	}
    
}
