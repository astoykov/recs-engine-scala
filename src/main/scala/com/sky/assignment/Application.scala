package com.sky.assignment

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp
import spray.http.MediaTypes

object Application extends App with SimpleRoutingApp {

  implicit val system = ActorSystem("recs")

  startServer(interface = "localhost", port = 8090) {
    path("personalised" / Segment) { subscriber =>
      get {
        
      respondWithMediaType(MediaTypes.`application/json`) {
        complete {
           ApiClient.callRecsEngine(subscriber)
        }
      }
      }
    }
  }
}
