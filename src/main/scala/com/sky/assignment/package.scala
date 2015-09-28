package com.sky.assignment
import spray.json._

package object domain{

  /**
   * Created by Alex on 27/09/15.
   */
  case class Recommendation( 
      uuid: String,
      start : Long,
      end : Long)
 

  case class Recommendations(
      recommendations: List[Recommendation],
      expiry: Long)


    object MyJsonProtocol extends DefaultJsonProtocol {
      implicit val recFormat = jsonFormat3(Recommendation)
      implicit val recsFormat = jsonFormat2(Recommendations)
    }
     
}