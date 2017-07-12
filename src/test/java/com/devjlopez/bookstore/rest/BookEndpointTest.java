package com.devjlopez.bookstore.rest;

import com.devjlopez.bookstore.model.Book;
import com.devjlopez.bookstore.model.Language;
import com.devjlopez.bookstore.repository.BookRepository;
import com.devjlopez.bookstore.util.IsbnGenerator;
import com.devjlopez.bookstore.util.NumberGenerator;
import com.devjlopez.bookstore.util.TextUtil;
import java.util.Date;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.CREATED;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author jlopez
 */
@RunWith(Arquillian.class)
@RunAsClient
public class BookEndpointTest {
    
    private Response response;
    
    @Test
    public void createBook(@ArquillianResteasyResource("books") WebTarget webTarget) {
        response = webTarget.path("count").request().get();
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
        
        

        /*
        response = webTarget.request(MediaType.APPLICATION_JSON).get();
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());   
        
        Book book = new Book(
                "isbn", 
                null, 
                12F, 
                123, 
                Language.ENGLISH, 
                new Date(), 
                "http://google.com", 
                "Description of the book"
        );
        
        response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(book, MediaType.APPLICATION_JSON));
        assertEquals(CREATED.getStatusCode(), response.getStatus()); */
    }
    
    @Deployment(testable = false)
    public static JavaArchive createDeployment(){
       return ShrinkWrap.create(JavaArchive.class)
               .addClass(BookRepository.class)
               .addClass(Book.class)
               .addClass(Language.class)
               .addClass(TextUtil.class)
               .addClass(NumberGenerator.class)               
               .addClass(IsbnGenerator.class)    
               .addClass(BookEndPoint.class)
               .addClass(JAXRSConfiguration.class)
               .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
               .addAsManifestResource("META-INF/persistence.xml", "persistence.xml");
    }
    
}
