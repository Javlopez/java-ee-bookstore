/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devjlopez.bookstore.util;

import java.util.Random;

/**
 *
 * @author jlopez
 */
public class IsbnGenerator implements NumberGenerator {

    @Override
    public String generateNumber() {
        return "13-12345-" + Math.abs(new Random().nextInt());
    }
    
}
