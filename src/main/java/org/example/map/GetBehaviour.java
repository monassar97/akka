package org.example.map;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class GetBehaviour extends AbstractBehavior<String> {

    private final Storage storage;
    private GetBehaviour(ActorContext<String> context) {
        super(context);
        storage=new Storage();
    }

    public static Behavior<String> create() {
        return Behaviors.setup(GetBehaviour::new);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("get", () -> {
                    return this;
                })
                .build();
    }
}
