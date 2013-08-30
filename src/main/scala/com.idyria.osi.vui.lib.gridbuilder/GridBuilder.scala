package com.idyria.osi.vui.lib.gridbuilder

import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.components.layout._
import com.idyria.osi.vui.core.components.scenegraph._
import com.idyria.osi.vui.core.styling._

import com.idyria.osi.tea.logging._

import scala.language.implicitConversions

/**
    Trait to be mixed somewhere, to have access to the grid building language

    Example:

        object GridBuilderExample2 extends App with GridBuilder {

            var url = getClass.getClassLoader.getResource("com.idyria.osi.vui.lib.gridbuilder/hd_logo_standard_sw_16cm_rgb.png")
            var uniLogo = image(url) {
                img => img size(250,-1)
            }
           
         

            var ui = frame { f=>

                f size(800,600)
                f title ("GridBuilderExample1")

                f <= grid {

                    "top" row {

                         label("Top")

                    }

                  

                    "login" row {

                        uniLogo spanRight {  
                                             "-" row { (label("Login: ")) | (textInput  using ("expandWidth" -> true)) | label(" OK ")}

                                             "-" row using("rowspan"->3,"expandWidth" -> true) {

                                                  label("Image Right bottom")
                                              } 

                                             "-" row((label("Image Right bottom") using ("spread"-> true,"expandWidth" -> true)))        
                                            }

                    }

                }


            }

            ui.show

        }


*/
trait GridBuilder extends VBuilder with LayoutConstraintsLanguage with TLogSource {

    /**
        This stack holds the current edited grid.
    */
    var groupsStack = scala.collection.mutable.Stack[Grid]()

    //------------------------------------------------
    // Main grid Interface
    //------------------------------------------------

    /**
        Represents a grid
        The language functions are adding nodes to the current grid on the stack, which holds its current Row/Columns informations
    */
    class Grid (var group : SGGroup[Any]) extends Column(group) {

        var currentColumn = 0 
        var currentRow = 0

        var currentRowLevel = 0

        var currentColumnLevel = 0

    
        left.asInstanceOf[SGGroup[Any]] layout grid

        // Content to be executed
        var content : ( () => Any ) = { () => }

        override def doResolve() = {

            // Add Group as normal column
            //----------
            super.doResolve

            // Execute Content
            //-----------
            groupsStack.push(this)
            content()
            groupsStack.pop

        }
    }

    
    def grid(cl: => Any) : SGGroup[Any] = {

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

    def subgrid(cl: => Any) : Grid = {

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






    // Constraining Language
    //------------------------------------------------

    /**
        This class is just the normal LayoutConstraints with LanguageChainElement interface so that the constraints can be chained in the language
    */
    class LayoutConstraintsLanguageElement extends LayoutConstraints with LanguageChainElement {

        name = "LayoutConstraints"

        def doResolve = {
               
                // Apply on next if it is a column
                //--------------
                if (this.nextElement!=null && this.nextElement.isInstanceOf[Column]) {
                    this.nextElement.asInstanceOf[Column].using(this)
                }

        }

    }

    /**
        Return a new constraints Set for this Sequence of Constraints
    */
    def using(constraints: LayoutConstraints*) : LayoutConstraintsLanguageElement = {

        var lc = new LayoutConstraintsLanguageElement
        constraints.foreach {
            c => lc(c)
        }
        lc

    }

  

    /**
        This conversion is mainly used if the Constraints is to be applied on the right, because no explicit method call required the ConstraintSet on the left
        This is why the resolve implementation if looking ahead
    */
    implicit def convertLayoutConstraintsToRowLanguageElement(cstr: LayoutConstraints) : LanguageChainElement = {

        var chainElement = new LayoutConstraintsLanguageElement
        chainElement(cstr)
        chainElement


    }

    
    // Common Chanining Language
    //----------------
    trait LanguageChainElement {

        var name = "unnamed"

        var nextElement : LanguageChainElement = null

        final def resolve : Unit = {
            logInfo(s"-> Applying chain $name")
            this.doResolve
            if (nextElement!=null)
                nextElement.resolve
        }

        /**
            Save element to the right as next, and return self for left composition
        */
        def apply(languageElement: LanguageChainElement) : LanguageChainElement = {

            var current = this 
            while (current.nextElement!=null)
                current = current.nextElement

            logFine(s" ---- Chaining $name to ${languageElement.name}")
            current.nextElement = languageElement
            this
        }

        def doResolve : Unit

    }

    implicit def convertContentClosureToRowLanguageElement(cl: => Unit) : LanguageChainElement = {

        logFine("Creating Content closure as LanguageChainElement")

        var wrapper : ( () => Unit) = { () => cl}
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
        Wrapper class to represent a row, and add language elements allowed on this row
    */
    class Row(var left: String) extends LanguageChainElement {

        name = "Row"


        def doResolve = {
    
        }

        // Language Right to "row" keyword
        //---------------------
        
        def row(right: LanguageChainElement) : Row  = {
            //languageElements = languageElements :+ right

            var rowLevel = groupsStack.head.currentRowLevel+1
            var rowDbg = (0 to rowLevel+1).map{i => "*"}.mkString

            logFine(s"""${rowDbg} Starting Row $left *******""")

            // Start Row
            //-----------------

            // Increment row level
            groupsStack.head.currentRowLevel += 1
            var currentColumnLevelBeforeRow = groupsStack.head.currentColumn

            // Resolve Chain
            //-----------------
            this.nextElement = right
            this.resolve

            // Close Row
            //-----------------
            
            logFine(s"""${rowDbg} Closing Row $left *******\n""")
        
            // Increment row
            groupsStack.head.currentRow+=1

            // Decrement Row level, and reset Column only if back to top level rows
            groupsStack.head.currentRowLevel-=1
            if (groupsStack.head.currentRowLevel==0)
                groupsStack.head.currentColumn=0
            else {
                groupsStack.head.currentColumn=currentColumnLevelBeforeRow
            }

            this
            
         
        }
        

    }
    implicit def convertStringToRow(str:String) = new Row(str)

    
    
    //----- Columns content
    //-----------------------

    /**
        Class to represent a column, and handle language elements allowed 

        A Column is always hosting a node
    */
    class Column(var left: SGNode[Any]) extends LanguageChainElement  {

        name = "Column"

        def doResolve : Unit  = {

            logFine(s"-> Adding column content(${left.toString}#${left.base.hashCode}")

            // Add 
            //-------------
            groupsStack.head.group <= left

            // Apply Constraints
            //------------------------

            //-- Row + Column position
            var constraints = LayoutConstraints( "row" -> groupsStack.head.currentRow , "column" -> groupsStack.head.currentColumn )
            

            groupsStack.head.group.layout.applyConstraints(left,constraints)

            // Increment Column
            groupsStack.head.currentColumn+=1

        }

        // Constraining Language
        //--------------------------

        def using(constraints: LayoutConstraints) : Column = {

            // this.apply(GridBuilder.this.using(constraint))
            var newColumn = new Column(left) {
                name = "Column Element constraint"

                override def doResolve = {
                     groupsStack.head.group.layout.applyConstraints(left,constraints)
                }
            }
            this.apply(newColumn)
            this

        }

        def using(constraints: Tuple2[String,Any]*) : Column = {

            var set = new LayoutConstraints
            constraints.foreach(set(_))
            this.using(set)

        } 

        
        /**
            This is a special constraint, that executes the content on the right, and then applies a row span to the current node,
            depending on how many rows have been added.

            This is useful when adding a component like an image, that must span in height along some components on the right
        */
        def spanRight(cl: => Unit) : Column = {

            var wrapper : ( () => Unit) = {
                () => cl
            }

            this.apply(new LanguageChainElement{

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
                    groupsStack.head.group.layout.applyConstraints(left,LayoutConstraints("rowspan"->rowsAdded))

                }

            })

             this
        }

        /**
            This language element allow column syntax chaining of a row content:

            label() | label() | label()
        */
        def | (columnElement: Column) : Column = {

            this.apply(columnElement)

            this
        }
        

    }

    /**
        A Single Node can be a column
    */
    implicit def convertSGNodeToColumn(node: SGNode[Any]) = new Column(node)

    

}
