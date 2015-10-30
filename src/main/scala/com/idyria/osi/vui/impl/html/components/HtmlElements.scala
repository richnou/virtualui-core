package com.idyria.osi.vui.impl.html.components

import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.lib.placeholder.PlaceHolder
import com.idyria.osi.tea.listeners.ListeningSupport
import scala.annotation.tailrec
import com.idyria.osi.vui.core.components.table.SGTable
import com.idyria.osi.vui.core.components.table.SGTableColumn
import com.idyria.osi.vui.impl.html.HtmlTreeBuilder
import com.idyria.osi.vui.core.components.controls.VUIButton
import com.idyria.osi.vui.core.components.events.VUIMouseEvent
import com.idyria.osi.vui.core.components.events.VUIClickEvent
import com.idyria.osi.vui.impl.html.js.JScript
import javax.swing.text.html.HTML
import com.idyria.osi.vui.core.validation.ValidationSupport
import com.idyria.osi.vui.core.components.VUIComponent

/**
 * Base Class for an HTML Node
 */
 class HTMLNode(var htmlNodeName: String = "undefined") extends SGGroup[Any] with VUIComponent[Any]{


  // Parameters
  // var name: String = null

  //----------------------
  // General Control
  //----------------------

  //-- Enable/Disable
  
  /**
    To be overriden if the component can be disabled
  */
  def disable : Unit = {
    
  }
  
  /**
   *  To be overriden if the component can be enabled
   */
  def enable = {
    
  }
  
   //----------------------
  // Positioning
  //----------------------
  /**
   * Set the position of the component
   */
  def setPosition(x:Int,y:Int) = {
    
  }

   /**
   * Get the position of the component
   */
  def getPosition : Pair[Int,Int] = {
    (0,0)
  }
  

  def size(width: Int, height: Int) = {
    apply("style" -> (""+attributes.getOrElse("style", "") + s";width:${width}px;height:${height}px"))
    
  }


  
  
  // SGGroup Implementation
  //-------------------

 // var childrenSeq = Seq[HTMLNode]()

  def base: Any = this

  /**
   * Clear Children
   */
 /* def clear: Unit = {
    this.childrenSeq = this.childrenSeq.filter(_ ⇒ true)
  }*/

  /**
   * Remove one child
   */
  /*override def removeChild(n: SGNode[Any]) = {
    this.children.contains(n) match {
      case true  ⇒ this.childrenSeq = this.children diff Seq(n)
      case false ⇒
    }
  }*/

  /**
   * Returns all children
   */
  //def children = this.childrenSeq

  /**
   * Not doing anything
   */
  def revalidate: Unit = {}

  /**
   * Children nodes are saved locally
   */
 /* override def node[NT <: SGNode[Any]](content: NT): NT = {

    childrenSeq = childrenSeq :+ content.asInstanceOf[HTMLNode]
    super.node(content)
  }*/

  def :::[NT <: HTMLNode](parent: NT): HTMLNode = {

    parent.node(this)

    this
  }

  /**
   * Placeholder mechanism
   */
  def waitFor(id: String) = {

    this.onWith(id) {
      content: Any ⇒

        // println("*** Adding content: "+id+" of type: "+content.getClass)

        content match {
          case node: SGNode[Any] ⇒ this <= node
          case nodes if (nodes.isInstanceOf[Iterable[_]]) ⇒ nodes.asInstanceOf[Iterable[_]].foreach(n ⇒ this <= n.asInstanceOf[SGNode[Any]])
          case _ ⇒
        }
    }

    /*this.onWith(id) {
      node: SGNode[Any] => this <= node
    }*/
  }

  def place(id: String)(cl: HTMLNode) = {
    this.@->(id, cl)
  }
  
  // Tree Location
  //-----------------
  
  /**
   * Remove from parent if any
   */
  def orphan = {
    this.parent match {
      case null => 
      case _ => this.parent.removeChild(this)
    }
    this
    
  }

  // HTML Stuff
  //-----------------

  /**
   * Node name used to produce html
   */
  //var htmlNodeName: String

  var textContent: String = ""

  var attributes = Map[String,String]()

  // ID
  //------------
  def getId : String = {
    id
  }
  
  // Attributes
  //-----------------
  def apply(attr: (String, String)) = {
    
    
    this.attributes = attributes + attr
  }
  def attributeAppend(attr: (String,String)) = {
    
    this.attributes.get(attr._1) match {
      case None => 
        
        this.attributes = attributes +  attr
        
      case Some(actualValue) => 
        
        this.attributes = attributes + (attr._1 ->  s"$actualValue ${attr._2}")
    }
    
  }
  
  /**
   * Left sid assignment of string adds classes
   */
  def ::(cl:String) : HTMLNode = {
    apply("class" -> (""+attributes.getOrElse("class", "") + " " +cl))
    this
  }

  // Render
  //----------------

  /**
   * FIXME : Optimise this ?
   */
  final def indentCount(nd: SGNode[Any]): Int = {
    if (nd.parent == null)
      0
    else {
      1 + indentCount(nd.parent)
    }
  }

  /**
   * Renders HTML Node structure as String
   */
  override def toString: String = {

    // Prepare attributes
    //-------------------------
    var attrs = attributes.size match { case 0 ⇒ "" case _ ⇒ attributes.map { t ⇒ s"""${t._1}="${t._2}"""" }.mkString(" ", " ", "") }

    var indentString = this.indentCount(this) match {
      case 0 => List("")
      case indentCount => for (i ← 1 to indentCount) yield "    "
    }

    s"""
${indentString.mkString}<$htmlNodeName$attrs>
${indentString.mkString}    ${textContent}
${indentString.mkString}${this.children.map(_.toString).mkString("\n\n")}
${indentString.mkString}</$htmlNodeName>
    """
  }

}

// Dummy 
//---------------
class DummyNode extends HTMLNode("dummy") {
  
  override def toString : String = {
    
     var indentString = this.indentCount(this) match {
      case 0 => List("")
      case indentCount => for (i ← 1 to indentCount) yield "    "
    }
     
    s"""${indentString.mkString}${this.children.map(_.toString).mkString("\n\n")}"""
    
  }
  
}

// Generic
//--------------------
class GenericHTMLElement(nodeName: String, textC: String = "") extends HTMLNode(nodeName) {

  this.textContent = textC
}

// Common Stuff
//------------------
class HTMLTextNode(var content: String) extends HTMLNode("") {

  /*def base: Any = this
  def revalidate: Unit = {

  }*/

  // Render
  //----------------

  /**
   * Renders HTML Node structure as String
   */
  override def toString: String = content

}

// Main
//-------------

/**
 * Automatically gets a head and body
 */
class Html extends HTMLNode("html") {

}

class Body extends HTMLNode("body") {

}

class Head extends HTMLNode("head") {

}

class Meta extends HTMLNode("meta") {
  
}

// Containers
//---------------

class Div extends HTMLNode("div") with ListeningSupport {

}

class Span extends HTMLNode("span") {

}

class Paragraph extends HTMLNode("p") {
  
}

/**
 * Definition for Pre formated container
 */
class Pre extends HTMLNode("pre")

//class P extends HTMLNode("p")

// Titles
//------------
class Htitle(var level: Int, text: String) extends HTMLNode(s"h$level") {

  this.textContent = text

}

class H1(text: String = "") extends Htitle(1, text)
class H2(text: String = "") extends Htitle(2, text)
class H3(text: String = "") extends Htitle(3, text)
class H4(text: String = "") extends Htitle(4, text)
class H5(text: String = "") extends Htitle(5, text)
class H6(text: String = "") extends Htitle(6, text)

// Linking / Actions
//------------

class A(var text: String, var destination: String) extends HTMLNode("a") {

  
  
  this("href" -> destination)
  this.textContent = text
  
  def toBlank : A = {
    this.apply(("target","_blank"))
    this
  }
}

class Button(n: String = "button") extends HTMLNode("button") with VUIButton[Any] {

  this.textContent = n
  
  // Clicks
  //---------------

  /**
   * The click event is going to be executed right away because it's result determines how to bind the action to the client code
   * 
   * Result handling:
   * 
   *  - JScript -> Create a jquery function call for onclick ,and execute script content
   *  - String -> Use onclick attribute to call function
   * 
   */
  override def onClicked(cl: VUIClickEvent ⇒ Any) = {

    cl(new VUIClickEvent) match {
      case null       ⇒
      
      //-- Bind script to Button
      case r: JScript ⇒
      
      	//-- Generate an Id for this button
      	var buttonId = this.attributes.getOrElse("id", { this("id"->"b1"); "b1"})
      
      	//println("********* BUTTON gets "+id)
      	
      	//-- Add Script element to this button (on its parent)
      	this.parent <= new Script {
    	  sc => 
    	    sc.textContent = s""" ${"$"}("#${buttonId}").click(function() { ${r.content} }); """
      	}

      //-- Just a script, add as DOM onclick	
      case r : String => 
        
        this("onclick" -> r)
      
      //-- No idea what to do
      case _ => 
    }

  }

  /*def disable: Unit = {

  }
  def enable: Unit = {

  }
  def getPosition: (Int, Int) = {
    (0, 0)
  }
  def setPosition(x: Int, y: Int): Unit = {

  }
  def size(width: Int, height: Int): Unit = {

  }*/

}

class Script extends GenericHTMLElement("script") {
  
}

// List
//-----------------
class Ul extends HTMLNode("ul")
class Ol extends HTMLNode("ol")
class Li extends HTMLNode("li")

// Form
//------------
class Form extends HTMLNode("form")

class Label extends HTMLNode("label") {
  
}

/**
 * Just a type marker
 */
trait FormInput {
  
}

abstract class FormInputNode(lname: String) extends HTMLNode("input") with ValidationSupport with FormInput {
  this.setName(lname)
  this("name" -> lname)
}

/**
 * <input type="hidden" name="name" value="value"
 */
class FormParameter(p: (String, String)) extends FormInputNode(p._1) {
  this("type" -> "hidden")
  this("value" -> p._2)
}

/**
 * input type text
 */
class InputText(lname: String) extends FormInputNode(lname)  with FormInput  {

  this("type" -> "text")

}

/**
 * input type password
 */
class InputPassword(lname: String) extends FormInputNode(lname)  with FormInput  {

  this("type" -> "password")

}

class InputCheckBox(lname: String) extends FormInputNode(lname)  with FormInput  {

  this("type" -> "checkbox")

}

class InputRadioBox(lname: String) extends FormInputNode(lname)  with FormInput  {

  this("type" -> "radio")

}

/**
 * textarea
 */
class Textarea(lname: String) extends HTMLNode("textarea") with ValidationSupport  with FormInput  {

  this("name" -> lname)
  

}

class Select(lname:String) extends HTMLNode("select")  with FormInput  {
  this("name" -> lname)
}

class SelectOption(name:String,value:String) extends HTMLNode("option") {
  this("value" -> value)
  this.textContent = name
}

class FormSubmit(value: String) extends Button("") {
  this.htmlNodeName = "input"
  this("type" -> "submit")
  this("value" -> value)

}

// Table
//--------------------
class Table[OT] extends HTMLNode("table") with SGTable[OT, Any] with HtmlTreeBuilder {

  // Init with head and body
  //-----------------
  val theadElement = this <= thead {
    tr {

    }
  }
  val tbodyElement = this <= tbody {

  }

  // Edit
  //----------------
  def setEditable(v:Boolean) = {
    
  }
  
  // On Column
  //-----------------
  def createColumn(name:String) : SGTableColumn[OT] = {
    
    // Add Column header
    //--------------
    var c = new SGTableColumn[OT](name) {
      
    }
   /* theadElement <= th {
      
      // Special Attributes
      if(c.colSpan>1) {
        attribute("colSpan" -> c.colSpan.toString)
      }
    
      text(c.name)
    }*/
    c
    
  }
  this.onWith("column.added") {
    c: SGTableColumn[OT] ⇒

      // Add Column header
      //--------------
      theadElement <= th {
      	
      	// Special Attributes
        if(c.colSpan>1) {
          attribute("colSpan" -> c.colSpan.toString)
        }
      
        text(c.name)
      }

  }

  // Datas
  //-------------------
  this.onWith("row.added") {
    c: List[Any] ⇒

      // Add Data row
      //--------------
      tbodyElement <= tr {

        // Add Data Cell
        //-----------
        c.foreach {

          // An HTMLNode -> add as such
          case content: HTMLNode            ⇒ td { switchToNode(content.detach.asInstanceOf[HTMLNode], {}) }

          // Anything -> to String
          case content if (content != null) ⇒ td { text(content.toString) }
          case _                            ⇒ td {}
        }
      }

  }

  //override type Self = Table[OT]

  //override type Self = Table[OT]

  /* override def toString : String = {
    s"""
    <table
    
    """
    
    
  }*/

}
	

// Navigation
//----------------------


class Nav extends HTMLNode("nav") with ListeningSupport {

}


