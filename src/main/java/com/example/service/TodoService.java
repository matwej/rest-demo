package com.example.service;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.example.domain.Todo;
import com.example.domain.TodoRepository;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoService {

	@GET
	public Response index() {
		Set<Todo> todos = TodoRepository.getInstance().getAll();
		return Response.status(200).entity(todos).build();
	}
	
	@GET
	@Path("/{uid}")
	public Response show(@PathParam("uid") String uid) {
		Todo todo = TodoRepository.getInstance().findOne(uid);
		if(todo == null) return Response.status(404).build();
		return Response.status(200).entity(todo).build();
	}
	
	@POST
	public Response put(final Todo todo) {
		String guid = TodoRepository.getInstance().add(todo);
		return Response.status(200).entity("{\"uid\":\"" + guid + "\"}").build();
	}
	
	@POST
	@Path("/{uid}")
	public Response patch(@PathParam("uid") String uid, final Todo todo) {
		System.out.println(todo.getUid());
		if(!todo.getUid().equals(uid)) return Response.status(401).build();
		TodoRepository.getInstance().save(todo);
		return Response.status(200).entity(todo).build();
	}
	
	@DELETE
	@Path("/{uid}")
	public Response delete(@PathParam("uid") String uid) {
		TodoRepository.getInstance().remove(uid);
		return Response.status(204).build();
	}

}
