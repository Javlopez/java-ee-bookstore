package com.devjlopez.bookstore.rest;

import com.devjlopez.bookstore.model.Book;
import com.devjlopez.bookstore.repository.BookRepository;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.UriInfo;
/**
 *
 * @author jlopez
 */ 
@Path("/books")
public class BookEndPoint {
    
    @Inject
    private BookRepository bookRepository;

    @GET
    @Path("/{id : \\d+}")
    @Produces(APPLICATION_JSON)
    public Response getBook(@PathParam("id") @Min(1) Long id) {
        Book book = bookRepository.find(id);
        if(book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(book).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response createBook(Book book, @Context UriInfo uriInfo) {
         book = bookRepository.create(book);
         URI createdUri = uriInfo.getBaseUriBuilder().path(book.getId().toString()).build();
         return Response.created(createdUri).build();
    }

    @DELETE
    @Path("/{id : \\d+}")
    public Response deleteBook(@PathParam("id") @Min(1) Long id) {
        bookRepository.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response getBooks() {
        List<Book> books = bookRepository.findAll();   
        
        if (books.size() == 0 ){
            return Response.noContent().build();
        }
        return Response.ok(books).build();
    }

    @GET
    @Path("/count")
    public Response countBooks() {
        Long numberOfBooks = bookRepository.countAll();
        if(numberOfBooks == 0) {
            return Response.noContent().build();
        }
        return Response.ok(numberOfBooks).build();
    }           
}
