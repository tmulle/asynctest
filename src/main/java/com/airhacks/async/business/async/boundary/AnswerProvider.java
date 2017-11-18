/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.async.business.async.boundary;

import com.airhacks.async.business.async.control.Thinker;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 *
 * @author tmulle
 */
@Stateless
public class AnswerProvider {
    
    @Resource
    ManagedExecutorService mes;
    
    @Inject
    Thinker thinker;
    
    public void onNewQuestion(@Observes Consumer<Object> consumer) {
        System.out.println("Received Event");
        Supplier<String> supplier = thinker::getAnswer;
        try {
            CompletableFuture.supplyAsync(supplier, mes).thenAccept(consumer).get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(AnswerProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
