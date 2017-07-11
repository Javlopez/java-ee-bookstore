/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devjlopez.bookstore.repository;

import com.devjlopez.bookstore.model.Book;
import com.devjlopez.bookstore.model.Language;
import java.util.Date;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author jlopez
 */
@RunWith(Arquillian.class)
public class BookRepositoryTest {
    
    @Inject
    private BookRepository bookRepository;
    
    @Test
    public void create() throws Exception {
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());
                
        Book book = new Book(
                "isbn", 
                "x title", 
                12F, 
                123, 
                Language.ENGLISH, 
                new Date(), 
                "http://google.com", 
                "Description of the book"
        );
       
        book = bookRepository.create(book);
        Long bookId = book.getId();
        
        
        assertNotNull(bookId);
                
        Book bookFound = bookRepository.find(bookId);
        
        assertEquals("x title", bookFound.getTitle());
        
        assertEquals(Long.valueOf(1), bookRepository.countAll());
        assertEquals(1, bookRepository.findAll().size());
        
        bookRepository.delete(bookId);
        
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());
        
    }
        
   @Deployment
   public static JavaArchive createDeployment() {
       return ShrinkWrap.create(JavaArchive.class)
               .addClass(BookRepository.class)
               .addClass(Book.class)
               .addClass(Language.class)
               .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
               .addAsManifestResource("META-INF/persistence.xml", "persistence.xml");
   }
}
