/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devjlopez.bookstore.repository;

import com.devjlopez.bookstore.model.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

/**
 *
 * @author jlopez
 */
@Transactional(SUPPORTS)
public class BookRepository {
    
    @PersistenceContext(unitName = "bookStorePU")
    private EntityManager em;
        
    public Book find(Long id) {
        return em.find(Book.class, id);
    }
    
    @Transactional(REQUIRED)
    public Book create(Book book) {
        em.persist(book);
        return book;
    }
    
    @Transactional(REQUIRED)
    public void delete(Long id) {
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
