package com.sky.assignment

import scala.concurrent.duration._
import scala.util.parsing.json.{JSONArray, JSON}
import com.sun.org.apache.xerces.internal.parsers.XMLParser
import akka.actor.{Props, ActorSystem}
import org.apache.http.client.methods.{HttpGet, CloseableHttpResponse}
import org.apache.http.impl.client.{DefaultHttpClient,CloseableHttpClient, HttpClients}
import org.apache.http.util.EntityUtils
import spray.httpx.encoding.{Deflate, Gzip}
import spray.http.{BasicHttpCredentials, HttpResponse, Uri, HttpRequest}
import scala.util.{Try, Success, Failure}
import scala.collection.mutable.StringBuilder
import scala.xml.XML
import com.sky.assignment.domain._
import spray.json._
import com.sky.assignment.domain.{Recommendation, Recommendations}
import com.sky.assignment.domain.MyJsonProtocol._
/**
 * @author Alex
 */

trait ApiClient

case class generateDuration(start: Long, end: Long) extends ApiClient

object ApiClient {
  private val numberOfLoops = 3
  private val duration = Duration(1, HOURS)

  def callRecsEngine(subscriber: String): String = {

    val responseList = (for (i <- 1 to numberOfLoops) yield getRecsEngineResult(decorateEndpointUrl(subscriber, getEndpointDuration(1).toIndexedSeq(0)), i)).toList

    return responseList.toJson.toString
    
  }


  def getTime(index: Int): generateDuration = {
    val now = System.currentTimeMillis()
    generateDuration(start = now + (index - 1) * duration.toMillis, end = now + index * duration.toMillis)
  }

  def decorateEndpointUrl(subscriber: String, time: generateDuration): String =
    s"http://localhost:8080/recs/personalised?num=5&start=${time.start}&end=${time.end}&subscriber=$subscriber"

  def getEndpointDuration(total: Int): Iterable[generateDuration] = {
    for (i <- 1 to total) yield getTime(i)
  }

  def getRecsEngineResult(url: String, index: Int): Recommendations = {
    val content = urlRestCall(url)
    
    val xml = XML.loadString(content)
 
    
    val recItems = (xml \ "recommendations" ).map { rec =>
      val uuid = (rec \ "uuid").text
      val start = (rec \ "start").text.toLong
      val end = (rec \ "end").text.toLong
      Recommendation(uuid, start, end)
    }
        
    val recs = Recommendations(recItems.toList,getTime(index).end)
    
    return recs
  }
  
  def urlRestCall(url: String): String = {

    
    val httpClient: CloseableHttpClient = {
      val client = HttpClients.createDefault()
      client
    }

    val httpGet: HttpGet = new HttpGet(url);
    httpGet.addHeader("accept", "application/xml")
    val response1: CloseableHttpResponse = httpClient.execute(httpGet);
    val responseBody = EntityUtils.toString(response1.getEntity);

    return responseBody
  }
  
  

}