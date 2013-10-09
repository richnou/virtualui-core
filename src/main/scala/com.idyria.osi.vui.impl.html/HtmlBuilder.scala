package com.idyria.osi.vui.impl.html

import com.idyria.osi.vui.impl.html.components.HTMLNode
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.impl.html.components._

import scala.language.implicitConversions

/**
 * Builder for main html elements
 */
trait HtmlTreeBuilder extends TreeBuilder[HTMLNode] {

  def createNode(name: String): SGNode[Any] = {

    name match {

      case "html" => new Html
      case "head" => new Head
      case "body" => new Body

    }

  }
  
  // Dynamic handles non defined elements
  //--------------------
  def applyDynamic(name:String)(cl: => Any) : HTMLNode = {
    
    // Create Generic
    var generic = new GenericHTMLElement(name)
    
    // IF first argument is a string, add as text content
    
    switchToNode(generic,cl)
  }
  

  

  // Generic Stuff like attributes
  //-----------------
  
  /**
   * Set an attribute
   */
  def attribute(attr: (String, String)): Unit = this.currentNode(attr)

  /**
   * Set the id of an element
   */
  def id(id: String) = this.attribute("id"->id)
  
  /**
   * Return a text node
   */
  def text(str:String) = switchToNode(new HTMLTextNode(str),{})
  
  def html(cl: => Any) = switchToNode(new Html, cl)

  
  // Head
  //--------------------

  //-- Base
  //---------------
  
  def head(cl: => Any) = switchToNode(new Head, cl)

  def link(cl: => Any) = switchToNode(new GenericHTMLElement("link"), cl)

  def script(cl: => Any) = switchToNode(new GenericHTMLElement("script"), cl)
   
  def title(str:String) = switchToNode(new GenericHTMLElement("title"), { currentNode.textContent = s"$str" })
  
  //-- Extras
  //--------------------
  
  def stylesheet(path: String) = {

    link {
      attribute("rel" -> "stylesheet")
      attribute("href" -> path)
    }
  }
  
  // Styling
  //-----------
  def classes(cl:String*) = {
    attribute("class"->cl.mkString(" "))
  }

  // Body 
  //----------------
  def body(cl: => Any) = switchToNode(new Body, cl)

  def div(cl: => Any) = switchToNode(new Div, cl)
  def span(cl: => Any) = switchToNode(new Span, cl)

  def h1(t: String) = switchToNode(new H1(t), {})
  def h2(t: String) = switchToNode(new H2(t), {})
  def h3(t: String) = switchToNode(new H3(t), {})
  def h4(t: String) = switchToNode(new H4(t), {})
  def h5(t: String) = switchToNode(new H5(t), {})
  def h6(t: String) = switchToNode(new H6(t), {})

  //--- Linking / Actions
  //--------------
  
  def a(name:String,dest: String) = switchToNode(new A(name,dest), {})
  
  
  // Lists
  //--------------------
  
  def ul(cl: => Any) =  switchToNode(new Ul, cl)
  def ol(cl: => Any) =  switchToNode(new Ol, cl)
  def li(cl: => Any) =  switchToNode(new Li, cl)
  
  
  // Form
  //----------------
  def form(cl: => Any) = switchToNode(new Form, cl)
  def inputText(name:String)(cl: => Any)  = switchToNode(new InputText(name), cl)
  def inputPassword(name:String)(cl: => Any)  = switchToNode(new InputPassword(name), cl)
  def formSubmit(text:String)(cl: => Any) = switchToNode(new FormSubmit(text), cl)
  def formParameter(p:(String,String))(cl: => Any)  = switchToNode(new FormParameter(p), cl)
  
  
}