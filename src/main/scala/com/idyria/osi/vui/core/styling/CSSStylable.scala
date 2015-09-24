package com.idyria.osi.vui.core.styling

import java.net.URL
import scala.io.Source
import scala.util.parsing.combinator.RegexParsers
import com.idyria.osi.vui.core.constraints.Constraint
import com.idyria.osi.tea.logging.TLogSource

/**
 * A trait the reacts on constraints updated that match CSS properties or class definitions, and tries to call on special functions to help implementations
 */
trait CSSStylable extends StylableTrait with TLogSource {

  /*this.onWith("constraints.set") {
    
     c : Constraint => 
    this.fixedConstraints.foreach{  c =>
      
      //println(s"Trying to match constraint: ${c.name}")
      c match {
     
      case Constraint("border",value) => 
        
        println(s"-- Setting Border: ${c.name}")
        this.base.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black))
        
      case _ =>
      
    }
    }
    
  }*/

  val cssP = new util.matching.Regex("""css\.(.+)""")

  this.onMatch("constraints.set") {

    // CSS Class
    //---------------------
    case Constraint("css.class", cssClass) =>

      logFine(s"Setting CSS class $cssClass to element")

      CSSStyle.classes.get(cssClass.toString) match {
        case Some(values) =>

          // Go Through values and Apply them in the most convinient way for the user
          //----------------------
          values.foreach {
            t => applyCss(t._1, t._2)
          }

        case None => logWarn(s"Setting non defined class $cssClass on an element ")
      }

    // Single Property
    //---------------------
    case Constraint(cssP(name), value) =>

      logFine(s"Setting CSS Single Property " + name)
      applyCss(name, value.toString)

    case _ =>

  }

  // Apply CSS
  //--------------------

  /**
   * This method preparses the properties for the user and tries to simplify the usage
   */
  def applyCss(property: String, value: String) = {

    cssProperty(property,value)
    property match {
      case "font-size" =>

        var size = value.replaceAll("""[a-zA-z]""", "").toFloat

        logFine(s"[ApplyCSS] Setting font size: " + size)
        cssFontSize(size)
      case _ =>
    }

  }
  
  // Implementation
  //-----------------------------
  
  //-- Generic call for all properties
  def cssProperty(p:String,v:String) = {
    
  }
  
  //-- Fonts
  def cssFontSize(s: Float) = {  }

}

object CSSStyle extends RegexParsers with TLogSource {

  var classes = Map[String, List[(String, String)]]()

  var classParser = "." ~> """([\w_-]+)""".r ~ ("{" ~> properties.? <~ "}") ^^ {
    res =>

      logFine(s"Found Class: " + res._1)

      res._2 match {
        case Some(vals) => classes = classes + (res._1 -> vals)
        case None       =>
      }

  }

  var property: Parser[(String, String)] = """[\w-_]+""".r ~ (":" ~> """[^;]*""".r <~ ";") ^^ {
    res =>

      (res._1, res._2)
  }

  var properties = property.+

  /**
   * Reads a CSS File and gather some infos like classes into it
   */
  def readCSS(url: URL) = {

    var source = Source.fromURL(url, "UTF-8")

    parseAll(classParser, source.reader)

  }

  def readCSS(content: String) = {
    parseAll(classParser, content) match {
      case Success(result, _) => result
      case failure: NoSuccess =>
        logError("Error while parsing CSS: " + failure)
    }
  }

}