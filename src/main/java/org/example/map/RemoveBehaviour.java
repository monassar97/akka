package org.example.map;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class RemoveBehaviour extends AbstractBehavior<String> {
    private final Storage storage;

    private RemoveBehaviour(ActorContext<String> context) {
        super(context);
        storage = new Storage();
    }

    public static Behavior<String> create() {
        return Behaviors.setup(RemoveBehaviour::new);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("remove", () -> {
                    return this;
                })
                .build();
    }
}
