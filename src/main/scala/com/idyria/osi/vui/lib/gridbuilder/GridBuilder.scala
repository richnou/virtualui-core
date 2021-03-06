package com.idyria.osi.vui.lib.gridbuilder

import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.components.layout._
import com.idyria.osi.vui.core.components.scenegraph._
import com.idyria.osi.vui.core.styling._
import com.idyria.osi.tea.logging._
import scala.language.implicitConversions
import com.idyria.osi.vui.core.constraints.Constraints
import com.idyria.osi.vui.core.constraints.Constraint
import com.idyria.osi.vui.core.constraints.ConstraintsConversions
import com.idyria.osi.vui.core.constraints.Constrainable
import scala.collection.AbstractSeq
import scala.collection.SeqLike

import scala.language.postfixOps

/**
 * Trait to be mixed somewhere, to have access to the grid building language
 *
 * Example:
 *
 * object GridBuilderExample2 extends App with GridBuilder {
 *
 * var url = getClass.getClassLoader.getResource("com.idyria.osi.vui.lib.gridbuilder/hd_logo_standard_sw_16cm_rgb.png")
 * var uniLogo = image(url) {
 * img => img size(250,-1)
 * }
 *
 *
 *
 * var ui = frame { f=>
 *
 * f size(800,600)
 * f title ("GridBuilderExample1")
 *
 * f <= grid {
 *
 * "top" row {
 *
 * label("Top")
 *
 * }
 *
 *
 *
 * "login" row {
 *
 * uniLogo spanRight {
 * "-" row { (label("Login: ")) | (textInput  using ("expandWidth" -> true)) | label(" OK ")}
 *
 * "-" row using("rowspan"->3,"expandWidth" -> true) {
 *
 * label("Image Right bottom")
 * }
 *
 * "-" row((label("Image Right bottom") using ("spread"-> true,"expandWidth" -> true)))
 * }
 *
 * }
 *
 * }
 *
 *
 * }
 *
 * ui.show
 *
 * }
 *
 *
 */
trait GridBuilder extends VBuilder with LayoutConstraintsLanguage with CSSConstraintsLanguage with TLogSource {

  /**
   * This stack holds the current edited grid.
   */
  var groupsStack = scala.collection.mutable.Stack[Grid]()

  def currentGrid = groupsStack.head

  //----------------------------
  // Search
  //--------------------------------
  /*def $(id:String)(cl: => Any) {
    currentNode.children.find { 
      case n if (n.name.toString == id)=>true
      case n : HTMLNode => n.attributes.getOrElse("id", "") == id
      case _ => false
      } match {
      case Some(node : HTMLNode)=> switchToNode(node, cl)
      case _ => currentNode.children.foreach {
        case n : HTMLNode => switchToNode(n, {$(id){cl}})
        case _ =>
      }
    }
  }*/

  //------------------------------------------------
  // Main grid Interface
  //------------------------------------------------

  /**
   * Represents a grid
   * The language functions are adding nodes to the current grid on the stack, which holds its current Row/Columns informations
   */
  class Grid(var group: SGGroup[Any]) extends Column(group) {

    var currentColumn = 0
    var currentRow = 0
    var currentRowName = "Row"

    var currentRowLevel = 0

    var currentColumnLevel = 0

    //  left.asInstanceOf[SGGroup[Any]].l

    left.asInstanceOf[SGGroup[Any]] layout = grid()

    // Content to be executed
    var content: (() => Any) = { () => }

    /*def apply(c: Constraints) : Grid = {
          
          this
        }
        def apply(cl: => Any) : Grid = {
          this
        }*/

    override def name_=(str: String) = group.name = str

    override def name: String = group.name

    override def doResolve() = {

      // Add Group as normal column
      //----------
      super.doResolve

      // Execute Content
      //-----------
      groupsStack.push(this)
      content()
      groupsStack.pop

      //println("REMOVED AUTOMATIC EXPAND")
      // If Nothing comes after the grid (alone on its row), then set to spread over all columns
      //if (this.nextElement==null) {
      //  this.group(spread,expandWidth)
      //}

    }
  }

  def grid(cl: => Any): SGGroup[Any] = {

    // Create a new group
    //---------------------------
    var newGroup = group

    // Create A new Grid Context instance to reset columns and rows
    //-----------------
    var grid = new Grid(newGroup)

    // Push on stack to execute closure
    //----------------------------------
    logFine("##################### GRID #############################")
    groupsStack.push(grid)
    cl
    groupsStack.pop
    logFine("##################### EOF GRID #############################\n")
    grid.group

  }

  /*def subgrid(c:Constraints)(cl: => Any) : Grid = {

        // Create a new group
        //---------------------------
        var newGroup = group 
       
    
        // Create A new Grid Context instance to reset columns and rows
        //-----------------
        var grid = new Grid(newGroup)
        grid.group(c)
        grid.content = {
            () => cl
        }

        // Push on stack to execute closure
        //----------------------------------
        grid
    }*/

  def subgrid(cl: => Any): Grid = {

    // Create a new group
    //---------------------------
    var newGroup = group

    // Create A new Grid Context instance to reset columns and rows
    //-----------------
    var grid = new Grid(newGroup)
    grid.content = {
      () => cl
    }

    // Push on stack to execute closure
    //----------------------------------
    grid
  }

  /* class ContentLanguageElement extends Column(null) {
      
       var content : ( () => Any ) = { () => }

        
        override def doResolve() = {

            content()

        }
      
    }
    implicit def convertContentToLanguageElement(cl: => Any) : ContentLanguageElement = {
      
      var content = new ContentLanguageElement
      content.content = {
        () => cl
      }
      content
    }*/

  // Constraining Language
  //------------------------------------------------

  /**
   * This class is just the normal LayoutConstraints with LanguageChainElement interface so that the constraints can be chained in the language
   */
  class ConstraintsLanguageElement extends Constraints with LanguageChainElement {

    name = "Constraints"

    def doResolve = {

      // Apply on next if it is a column
      //--------------
      if (this.nextElement != null && this.nextElement.isInstanceOf[Column]) {
        // this.nextElement.asInstanceOf[Column].using(this)
      }

    }

  }

  /**
   * Apply given constraints to top Grid Element
   */
  def use(constraints: Constraints) = {

    groupsStack.headOption match {
      case Some(top) => top.group(constraints)
      case _         =>
    }

  }

  /**
   * Return a new constraints Set for this Sequence of Constraints
   */
  def using(constraints: Constraints): ConstraintsLanguageElement = {

    var lc = new ConstraintsLanguageElement
    constraints.foreach {
      c => lc(c)
    }
    lc

  }

  /**
   * Return a new constraints Set for this Sequence of Constraints
   */
  def using(constraints: Constraint*): ConstraintsLanguageElement = {

    this.using(constraints)

  }

  /**
   * This conversion is mainly used if the Constraints is to be applied on the right, because no explicit method call required the ConstraintSet on the left
   * This is why the resolve implementation if looking ahead
   */
  implicit def convertLayoutConstraintsToRowLanguageElement(cstr: Constraints): LanguageChainElement = {

    var chainElement = new ConstraintsLanguageElement
    cstr.constraints.foreach(c => chainElement(c))

    chainElement

  }

  /**
   * This conversion is mainly used if the Constraints is to be applied on the right, because no explicit method call required the ConstraintSet on the left
   * This is why the resolve implementation if looking ahead
   */
  implicit def convertLayoutConstraintToRowLanguageElement(c: Constraint): LanguageChainElement = {

    var chainElement = new ConstraintsLanguageElement
    chainElement(Constraints(c))
    chainElement

  }

  /**
   * This conversion is mainly used if the Constraints is to be applied on the right, because no explicit method call required the ConstraintSet on the left
   * This is why the resolve implementation if looking ahead
   */
  implicit def convertLayoutConstraintToRowLanguageElement(cs: Constraint*): LanguageChainElement = {

    var chainElement = new ConstraintsLanguageElement
    cs.foreach(c => chainElement(c))
    chainElement

  }

  // Common Chanining Language
  //----------------
  trait LanguageChainElement {

    var _name = "unnamed"

    def name: String = _name
    def name_=(str: String) = _name = str

    var nextElement: LanguageChainElement = null

    var resolved = false

    final def resolve: Unit = {
      logInfo(s"-> Applying chain $name")
      this.resolved match {
        case true =>
        case false =>
          this.doResolve
          resolved = true
          if (nextElement != null)
            nextElement.resolve
      }

    }

    /**
     * Save element to the right as next, and return self for left composition
     */
    def apply(languageElement: LanguageChainElement): LanguageChainElement = {

      var current = this
      while (current.nextElement != null)
        current = current.nextElement

      logFine(s" ---- Chaining $name to ${languageElement.name}")
      current.nextElement = languageElement
      this
    }

    def doResolve: Unit

  }

  implicit def convertContentClosureToRowLanguageElement(cl: => Unit): LanguageChainElement = {

    logFine("Creating Content closure as LanguageChainElement")

    var wrapper: (() => Unit) = { () => cl }
    new LanguageChainElement {

      name = "Contentclosure"
      def doResolve = {
        println("--> Doing Content Closure set")
        wrapper()
      }
    }

  }

  //---- Row
  //--------------------------

  /**
   * Wrapper class to represent a row, and add language elements allowed on this row
   */
  class Row(var left: String) extends LanguageChainElement {

    name = left

    def doResolve = {

    }

    def apply(right: ConstraintsLanguageElement): Row = {
      super.apply(right)
      this
    }

    // Language Right to "row" keyword
    //---------------------

    /**
     * Create a row with a sub grid
     */
    def grid(cl: => Any): Row = {

      this.row(subgrid(cl))
      this

    }

    def row(right: LanguageChainElement): Row = {
      //languageElements = languageElements :+ right

      var rowLevel = groupsStack.head.currentRowLevel + 1
      var rowDbg = (0 to rowLevel + 1).map { i => "*" }.mkString

      logFine(s"""${rowDbg} Starting Row $left *******""")

      // Start Row
      //-----------------

      // Increment row level
      groupsStack.head.currentRowLevel += 1
      groupsStack.head.currentRowName = left
      var currentColumnLevelBeforeRow = groupsStack.head.currentColumn

      // Resolve Chain
      //-----------------
      this.nextElement = right
      this.resolve

      // Close Row
      //-----------------

      logFine(s"""${rowDbg} Closing Row $left *******\n""")

      // Increment row
      groupsStack.head.currentRow += 1

      // Decrement Row level, and reset Column only if back to top level rows
      groupsStack.head.currentRowLevel -= 1
      if (groupsStack.head.currentRowLevel == 0)
        groupsStack.head.currentColumn = 0
      else {
        groupsStack.head.currentColumn = currentColumnLevelBeforeRow
      }

      this

    }

  }
  implicit def convertStringToRow(str: String) = new Row(str)
  
  /**
   * Create an unnamed row
   */
  def -> = (new Row("-").row)_

  //----- Columns content
  //-----------------------

  /**
   * Class to represent a column, and handle language elements allowed
   *
   * A Column is always hosting a node
   */
  class Column(var left: SGNode[Any]) extends LanguageChainElement {

    name = "Column"

    override def toString: String = left.getClass().getName()

    def doResolve: Unit = {

      // println(s"-> Adding column content(${left.toString}#${left.base.hashCode} at row ${groupsStack.head.currentRow} ")

      // Add 
      //-------------
      groupsStack.head.group <= left

      // Apply Constraints
      //------------------------

      //-- Row + Column position
      var constraints = Constraints("row" -> groupsStack.head.currentRow, "column" -> groupsStack.head.currentColumn)
      groupsStack.head.group.layout.applyConstraints(left, constraints)

      // Increment Column
      groupsStack.head.currentColumn += 1

    }

    // Add to previous row
    //--------------
    def ::(r: Row): Unit = {

      r.row(this)
    }

    // Constraining Language
    //--------------------------

    /**
     *  Add A Constraints bag to current column
     */
    def using(constraints: Constraints): Column = {

      // this.apply(GridBuilder.this.using(constraint))
      /*var newColumn = new Column(left) {
                name = "Column Element constraint"

                override def doResolve = {
            		left match {
            		  case e  : Constrainable => 
            		    
            		    if(e._debugConstraints) {
            		      println("[GBD] Applying constrainst to "+e)
            		      constraints.foreach {
            		        c => println(s"--- "+c.name)
            		      }
            		    }
            		    e(constraints)
            		  case _ =>
            		}
                    groupsStack.head.group.layout.applyConstraints(left,constraints)
                }
            }
            this.apply(newColumn)*/
      this.left(constraints)
      groupsStack.head.group.layout.applyConstraints(left, constraints)
      this

    }

    def using(constraints: Constraint*): Column = {

      var set = new Constraints
      constraints.foreach(set(_))
      this.using(set)
    }

    /**
     * Converts to Constraints object and calls #using(Constraints)
     */
    /* def using(constraints: Tuple2[String,Any]*) : Column = {

            var set = new Constraints
            constraints.foreach(set(_))
            this.using(set)
        } 

         /**
         * Converts to Constraints object and calls #using(Constraints)
         */
        def using(constraints: Tuple2[String,Constraint]*) : Column = {

            var set = new Constraints
            
            constraints.foreach(c => set(c._2)) 
            
            this.using(set)

        } */

    /**
     * This is a special constraint, that executes the content on the right, and then applies a row span to the current node,
     * depending on how many rows have been added.
     *
     * This is useful when adding a component like an image, that must span in height along some components on the right
     */
    def spanRight(cl: => Unit): Column = {

      var wrapper: (() => Unit) = {
        () => cl
      }

      this.apply(new LanguageChainElement {

        name = "SpanRight"

        def doResolve = {

          // Note actual row
          //------------------------
          var actualRow = groupsStack.head.currentRow

          // Execute Right
          //--------------------
          //cl.resolve
          wrapper()

          var rowsAdded = groupsStack.head.currentRow - actualRow

          logFine(s"spanRight added $rowsAdded rows")
          
          left(Constraints("rowspan" -> rowsAdded))
          //groupsStack.head.group.layout.applyConstraints(left, Constraints("rowspan" -> rowsAdded))

        }

      })

      this
    }

    /**
     * This language element allow column syntax chaining of a row content:
     *
     * label() | label() | label()
     */
    def |(columnElement: Column): Column = {

      this.apply(columnElement)

      this
    }

  }

  /**
   * A Single Node can be a column
   */
  implicit def convertSGNodeToColumn(node: SGNode[Any]) = new Column(node)
  
  
  implicit def convertSGNodeListToColumn(nodes: List[SGNode[Any]]) = {
    
    var firstColumn = new Column(nodes(0))
    var currentColumn = firstColumn
    for ( i <- 1 until nodes.size) {
      
      //-- Create New linked to current
      var newColumn = currentColumn | nodes(i)
      
      //-- Set new as current
      currentColumn = newColumn
      
      
    }
    
    firstColumn
  }
  
  
  // Utilities
  //-----------------------
  
  def rowNameLabel = {
    
    var l = label("") 
    
    new Column(l) {
      override def doResolve = {
        l.setText(groupsStack.head.currentRowName)
        super.doResolve
      }
    }
    
  }

}
