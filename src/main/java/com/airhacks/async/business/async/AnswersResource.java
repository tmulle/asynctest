/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.async.business.async;

import java.util.function.Consumer;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

/**
 *
 * @author tmulle
 */
@Stateless
@Path("answers")
public class AnswersResource {
    
    @Inject
    Event<Consumer<Object>> theEvent;
    
    @GET
    public void answer(@Suspended AsyncResponse response) {
        Consumer<Object> consumer = response::resume;
        theEvent.fire(consumer);
        System.out.println("Event Fired:" + consumer);
    }
}
