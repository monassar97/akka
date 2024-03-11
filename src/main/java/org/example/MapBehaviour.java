package org.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.example.map.AddBehaviour;
import org.example.map.GetBehaviour;
import org.example.map.RemoveBehaviour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapBehaviour extends AbstractBehavior<String> {
    private final List<ActorRef<String>> peers;

    private MapBehaviour(ActorContext<String> context, List<ActorRef<String>> peers) {
        super(context);
        this.peers = peers;
    }

    public static Behavior<String> create(List<ActorRef<String>> peers) {
        return Behaviors.setup(context -> new MapBehaviour(context, peers));
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("add", () -> {
                    ActorRef<String> addBehaviour = getContext().spawn(AddBehaviour.create(), "addBehaviour");
                    gossipToPeers("add");
                    return this;
                })
                .onMessageEquals("remove", () -> {
                    ActorRef<String> addBehaviour = getContext().spawn(RemoveBehaviour.create(), "removeBehaviour");
                    gossipToPeers("remove");
                    return this;
                })
                .onMessageEquals("get", () -> {
                    ActorRef<String> addBehaviour = getContext().spawn(GetBehaviour.create(), "getBehaviour");
                    gossipToPeers("get");
                    return this;
                })
                .build();
    }

    private void gossipToPeers(String message) {
        for (ActorRef<String> peer : peers) {
            peer.tell(message);
        }
    }
    private void gossipRandomSelection (String message){
        // Define the number of peers to gossip to. This could be a fixed number or a proportion of the total number of peers.
        int numberOfPeersToInform = Math.max(1, peers.size() / 2); // gossip to half of the peers, but at least to one.

        // Create a new list to avoid modifying the original list of peers
        List<ActorRef<String>> shuffledPeers = new ArrayList<>(peers);
        Collections.shuffle(shuffledPeers); // Shuffle the list to randomize the order

        // Select the first N peers from the shuffled list
        List<ActorRef<String>> selectedPeers = shuffledPeers.subList(0, Math.min(numberOfPeersToInform, shuffledPeers.size()));

        // Gossip the message to the selected subset of peers
        for (ActorRef<String> peer : selectedPeers) {
            peer.tell(message);
        }
    }
}
