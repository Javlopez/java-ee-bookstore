/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devjlopez.bookstore.repository;

import com.devjlopez.bookstore.model.Book;
import com.devjlopez.bookstore.util.NumberGenerator;
import com.devjlopez.bookstore.util.TextUtil;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jlopez
 */
@Transactional(SUPPORTS)
public class BookRepository {
    
    @PersistenceContext(unitName = "bookStorePU")
    private EntityManager em;
    
    @Inject
    private TextUtil textUtil;
    
    @Inject
    private NumberGenerator generator;
        
    public Book find(@NotNull Long id) {
        return em.find(Book.class, id);
    }
    
    @Transactional(REQUIRED)
    public Book create(@NotNull Book book) {
        book.setTitle(textUtil.sanitize(book.getTitle()));
        book.setIsbn(generator.generateNumber());
        em.persist(book);
        return book;
    }
    
    @Transactional(REQUIRED)
    public void delete(@NotNull Long id) {
        em.remove(em.getReference(Book.class, id));
    }
    
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b order by b.title", 
                Book.class
        );
        return query.getResultList();
    }
    
    public Long countAll(){
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) from Book b", Long.class);
        return query.getSingleResult();
    }
            
}
