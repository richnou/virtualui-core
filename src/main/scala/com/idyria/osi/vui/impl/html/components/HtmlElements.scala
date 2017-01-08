package com.idyria.osi.vui.impl.html.components

import scala.annotation.tailrec
import org.w3c.dom.Element
import com.idyria.osi.tea.listeners.ListeningSupport
import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.controls.VUIButton
import com.idyria.osi.vui.core.components.events.VUIClickEvent
import com.idyria.osi.vui.core.components.events.VUIMouseEvent
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.table.SGTable
import com.idyria.osi.vui.core.components.table.SGTableColumn
import com.idyria.osi.vui.core.validation.ValidationSupport
import com.idyria.osi.vui.impl.html.js.JScript
import com.idyria.osi.vui.lib.placeholder.PlaceHolder
import org.w3c.dom.html.HTMLInputElement

/**
 * Base Class for an HTML Node
 */
trait HTMLNode[DT <: org.w3c.dom.Node] extends SGGroup[DT] with VUIComponent[DT] {

  var htmlNodeName: String = "undefined"

  // DOM Base
  //--------------------
  var delegate: DT = _
  def base = delegate

  // Parameters
  // var name: String = null

  override def clear = {
    super.clear
    //println(s"Delegate is :$this.delegate")
    this.delegate match {
      case null =>
      case d =>
        d.setTextContent("")
        var cn = d.getChildNodes
        (0 until cn.getLength) foreach {
          i =>
            d.removeChild(cn.item(i))
        }
    }
  }

  //----------------------
  // General Control
  //----------------------

  //-- Enable/Disable

  /**
   * To be overriden if the component can be disabled
   */
  def disable: Unit = {

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
  def setPosition(x: Int, y: Int) = {

  }

  /**
   * Get the position of the component
   */
  def getPosition: Pair[Int, Int] = {
    (0, 0)
  }

  def size(width: Int, height: Int) = {
    apply("style" -> ("" + attributes.getOrElse("style", "") + s";width:${width}px;height:${height}px"))

  }

  // SGGroup Implementation
  //-------------------

  // var childrenSeq = Seq[HTMLNode]()

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

  def :::[NT <: HTMLNode[DT]](parent: NT): HTMLNode[DT] = {

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
          case node: SGNode[_] ⇒ this <= node.asInstanceOf[SGNode[DT]]
          case nodes if (nodes.isInstanceOf[Iterable[_]]) ⇒ nodes.asInstanceOf[Iterable[_]].foreach { n => this <= n.asInstanceOf[SGNode[DT]] }
          case _ ⇒
        }
    }

    /*this.onWith(id) {
      node: SGNode[Any] => this <= node
    }*/
  }

  def place(id: String)(cl: HTMLNode[_]) = {
    //this.@->(id, cl)
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

  var attributes = Map[String, Any]()

  // ID
  //------------
  def getId: String = {
    id
  }

  // Attributes
  //-----------------
  def apply(attr: (String, Any)) = {

    this.attributes = attributes + attr
  }
  def +@(attr: (String, Any)) = {

    this.attributes = attributes + attr
  }
  def attributeAppend(attr: (String, String)) = {

    this.attributes.get(attr._1) match {
      case None =>

        this.attributes = attributes + attr

      case Some(actualValue) =>

        this.attributes = attributes + (attr._1 -> s"$actualValue ${attr._2}")
    }

  }

  /**
   * Left sid assignment of string adds classes
   */
  def ::(cl: String): Self = {
    apply("class" -> ("" + attributes.getOrElse("class", "") + " " + cl))
    this.asInstanceOf[Self]
  }

  // Left assign of ID
  //-----------------------
  def #:(id: String): Self = {
    this("id" -> id)
    this.id = id
    this.asInstanceOf[Self]
  }

  // Left Attributes assignment
  //----------------
  def @:(attr: String): Self = {

    attr.split(" ").map(_.split("=")).foreach {
      case one if (one.size == 1) => this(one(0) -> "")
      case two => this(two(0) -> two(1).replace("\"", "").replace("'", ""))
    }
    this.asInstanceOf[Self]
  }
  def @:(attrs: (String, Any)*): Self = {
    attrs.foreach {
      attr => this(attr)
    }
    this.asInstanceOf[Self]
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

  // DOM Events
  //---------------------
  def onChanged(cl: => Any): Unit = {

  }

  def onLoad(cl: Any => Any): Unit = {

  }

}

class DefaultHTMLNode(nodeName: String) extends HTMLNode[org.w3c.dom.html.HTMLElement] {
  this.htmlNodeName = nodeName
}

// Dummy 
//---------------
class DummyNode extends HTMLNode[org.w3c.dom.html.HTMLElement] {

  this.htmlNodeName = "dummy"

  override def toString: String = {

    var indentString = this.indentCount(this) match {
      case 0 => List("")
      case indentCount => for (i ← 1 to indentCount) yield "    "
    }

    s"""${indentString.mkString}${this.children.map(_.toString).mkString("\n\n")}"""

  }

}

// Generic
//--------------------
class GenericHTMLElement(nodeName: String, textC: String = "") extends HTMLNode[org.w3c.dom.html.HTMLElement] {

  this.htmlNodeName = nodeName

  this.textContent = textC

  type Self = GenericHTMLElement
}

// Common Stuff
//------------------
class HTMLTextNode(var content: String) extends HTMLNode[org.w3c.dom.html.HTMLElement] {

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
class Html extends DefaultHTMLNode("html") {

}

class Body extends DefaultHTMLNode("body") {

}

class Head extends DefaultHTMLNode("head") {

}

class Meta extends DefaultHTMLNode("meta") {

}

// Containers
//---------------

class Div extends DefaultHTMLNode("div") with ListeningSupport {
 
}

class Span extends DefaultHTMLNode("span") {

  type Self = Span

}

class Paragraph extends DefaultHTMLNode("p") {

}

/**
 * Definition for Pre formated container
 */
class Pre extends DefaultHTMLNode("pre")

//class P extends HTMLNode("p")

// Titles
//------------
class Htitle(var level: Int, text: String) extends DefaultHTMLNode(s"h$level") {

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

class A(text: String, destination: String) extends DefaultHTMLNode("a") {

  this("href" -> destination)
  this.textContent = text

  def setDestination(txt: String) = {
    this("href" -> destination)
  }
  def toBlank: A = {
    this.apply(("target", "_blank"))
    this
  }
}

class Button(n: String = "button") extends DefaultHTMLNode("button") with VUIButton[org.w3c.dom.html.HTMLElement] {

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
      case null ⇒

      //-- Bind script to Button
      case r: JScript ⇒

        //-- Generate an Id for this button
        var buttonId = this.attributes.getOrElse("id", { this("id" -> "b1"); "b1" })

        //println("********* BUTTON gets "+id)

        //-- Add Script element to this button (on its parent)
        this.parent <= new Script {
          sc =>
          sc.textContent = s""" ${"$"}("#${buttonId}").click(function() { ${r.content} }); """
        }

      //-- Just a script, add as DOM onclick	
      case r: String =>

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
class Ul extends DefaultHTMLNode("ul")
class Ol extends DefaultHTMLNode("ol")
class Li extends DefaultHTMLNode("li")

// Form
//------------
class Form extends DefaultHTMLNode("form")

class Label extends DefaultHTMLNode("label") {

}

/**
 * Just a type marker
 */
trait FormInput {

}

abstract class FormInputNode(lname: String) extends HTMLNode[org.w3c.dom.html.HTMLInputElement] with ValidationSupport with FormInput {
  this.htmlNodeName = "input"
  this.setName(lname)
  this("name" -> lname)

  def getValue: String = base.asInstanceOf[HTMLInputElement].getValue
  def setValue(str: String): String = {
    base.asInstanceOf[HTMLInputElement].setValue(str)
    str

  }
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
class InputText(lname: String) extends FormInputNode(lname) with FormInput {

  override type Self = InputText

  //override def base : org.w3c.dom.html.HTMLElement = super.base.asInstanceOf[org.w3c.dom.html.HTMLInputElement]

  this("type" -> "text")

}

/**
 * input type password
 */
class InputPassword(lname: String) extends FormInputNode(lname) with FormInput {

  this("type" -> "password")

}

class InputCheckBox(lname: String) extends FormInputNode(lname) with FormInput {

  this("type" -> "checkbox")

}

class InputRadioBox(lname: String) extends FormInputNode(lname) with FormInput {

  this("type" -> "radio")

}

/**
 * textarea
 */
class Textarea(lname: String) extends DefaultHTMLNode("textarea") with ValidationSupport with FormInput {

  this("name" -> lname)

}

class Select(lname: String) extends DefaultHTMLNode("select") with FormInput {
  this("name" -> lname)
}

class SelectOption(name: String, value: String) extends DefaultHTMLNode("option") {
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
class Table[OT] extends DefaultHTMLNode("table") with SGTable[OT, org.w3c.dom.html.HTMLElement] {

  // Init with head and body
  //-----------------
  /*val theadElement = this <= thead {
    tr {

    }
  }
  val tbodyElement = this <= tbody {

  }*/

  // Edit
  //----------------
  def setEditable(v: Boolean) = {

  }

  // On Column
  //-----------------
  def createColumn(name: String): SGTableColumn[OT] = {

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
    /*theadElement <= th {

        // Special Attributes
        if (c.colSpan > 1) {
          attribute("colSpan" -> c.colSpan.toString)
        }

        text(c.name)
      }*/

  }

  // Datas
  //-------------------
  this.onWith("row.added") {
    c: List[Any] ⇒

    // Add Data row
    //--------------
    /*tbodyElement <= tr {

        // Add Data Cell
        //-----------
        c.foreach {

          // An HTMLNode -> add as such
          case content: HTMLNode ⇒ td { switchToNode(content.detach.asInstanceOf[HTMLNode], {}) }

          // Anything -> to String
          case content if (content != null) ⇒ td { text(content.toString) }
          case _ ⇒ td {}
        }
      }*/

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

class Nav extends DefaultHTMLNode("nav") with ListeningSupport {

}


