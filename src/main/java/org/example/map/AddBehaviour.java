package org.example.map;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class AddBehaviour extends AbstractBehavior<String> {
    private final Storage storage;

    private AddBehaviour(ActorContext<String> context) {
        super(context);
        storage=new Storage();
    }

    public static Behavior<String> create() {
        return Behaviors.setup(AddBehaviour::new);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("add", () -> {
                    return this;
                })

                .build();
    }
}
