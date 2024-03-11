package org.example;

import akka.actor.typed.ActorSystem;
import akka.cluster.typed.Cluster;
import akka.cluster.typed.Join;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ActorSystem<String> actorSystem = ActorSystem.create(MapBehaviour.create(new ArrayList<>()), "ClusterSystem");
        Cluster cluster = Cluster.get(actorSystem);
        cluster.manager().tell(Join.create(cluster.selfMember().address()));


        ActorSystem<String> mapActor = ActorSystem.create(MapBehaviour.create(new ArrayList<>()), "mapActor");
        mapActor.tell("add");
    }
}