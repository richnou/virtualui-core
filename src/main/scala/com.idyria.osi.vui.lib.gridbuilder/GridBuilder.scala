package com.idyria.osi.vui.lib.gridbuilder

import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.components.layout._

import com.idyria.osi.vui.core.components.scenegraph._

import com.idyria.osi.vui.core.styling._


import scala.language.implicitConversions

trait GridBuilder extends VBuilder {

    var groupsStack = scala.collection.mutable.Stack[SGGroup[Any]]()

    var currentRow = 0 

    var currentColumn = 0

    /**
        The row level is used to detect rows in rows, and then not reset column count until we are back to row level 0
    */
    var currentRowLevel = 0

     var currentColumnLevel = 0

    var currentConstraints : LayoutConstraints = null

    // Language
    //---------------------------
   

    

    

    //------------------------------------------------
    // Main Interface
    //------------------------------------------------


    // Constraining Language
    //------------------------------------------------
    class ConstraintsWrapper(var cl : ( () => Unit) )    {

        var constraints = new LayoutConstraints

        def apply(newConstraints: (String,Any)*) : ConstraintsWrapper = {
            this.constraints(newConstraints)
            this
        }
        /*def apply(newConstraints: Seq[(String,Any)]) : ConstraintsWrapper = {
            this.constraints(newConstraints)
            this
        }*/
        def apply(newConstraints: (String,Any)) : ConstraintsWrapper = {
            this.constraints(newConstraints)
            this
        }
        
    }

    /**  Add a user define constraint to enclosed content
        Example: 

        "id" row with("align" -> "right") {
            // Content
        }
    */
    def using(constraints: (String,Any)*)(cl: => Unit) : ConstraintsWrapper = {


        var clwrapper = { () =>
            cl
        }
        var constraintsWrapper = new ConstraintsWrapper(clwrapper)
        constraints.foreach(constraintsWrapper(_))


        GridBuilder.this.currentConstraints = constraintsWrapper.constraints
        cl
        GridBuilder.this.currentConstraints = null

        constraintsWrapper
    }

    /** Add an align right to enclosed content
        Example: 

        "id" row alignRight {
            // Content
        }
    */
    def alignRight (cl: => Unit) :  ConstraintsWrapper = {


        var clwrapper = { () =>
            cl
        }
        return new ConstraintsWrapper(clwrapper)("align" -> "right")
    }

    // Building
    //------------------------------------------------

    def grid(cl: => Unit) : SGGroup[Any] = {

        // Create a new group
        var newGroup = group 

        // Set Grid Layout
        newGroup layout grid

        // Push on stack to execute closure
        groupsStack.push(newGroup)
        cl
        groupsStack.pop

    }


    //---- Row
    //--------------------------
     class RowLanguageWrapper(var left: String) {

        def row(cl: => Unit) = {
            GridBuilder.this.row(left)(cl)
        }
        
        def row(node: SGNode[Any]) = {
            GridBuilder.this.row(node)
        }

        def row(right: ColumnLanguageWrapper) = {
            GridBuilder.this.row(left) {

            }
        }

        def expandRow(node: SGNode[Any]) = {
            GridBuilder.this.row(node, ("expand" -> true))
        }

        def row(wrapper: ConstraintsWrapper) = {

            println(s"Adding row with right constraints $left")



            GridBuilder.this.currentConstraints = wrapper.constraints
            GridBuilder.this.row(left) {
                wrapper.cl()
            }
            
            GridBuilder.this.currentConstraints = null

            //GridBuilder.this.row(left, ("expand" -> true))

        }

    }
    implicit def convertStringToRow(str:String) = new RowLanguageWrapper(str)

    class RowContentWrapper( var contentClosure: () => Unit) {

    }

    /**
        Do Nothing if a ColumnLanguage is found, because this one already handles everything correctly
    */
    def row( column: ColumnLanguageWrapper) : Unit = {

        println("**** Row With ColumnLanguageWrapper *****")
        this.row("Undefined") {
            column.addNodes
        }

        println("-> Eof ColumnLanguageWrapper row")
        


    }
    def row(cl: => Unit) : RowContentWrapper = {

        var clwrapper = { () =>
            cl
        }

        new RowContentWrapper(clwrapper)
    }

    def - (id:String)(cl: =>Unit) : Unit  = row(id)(cl)
    def row (id:String)(cl: =>Unit) : Unit = {

        // Increment row level
        this.currentRowLevel += 1
        var currentColumnLevelBeforeRow = this.currentColumn

        // Execute
        cl

        // Increment row
        this.currentRow+=1

        // Decrement Row level, and reset Column only if back to top level rows
        this.currentRowLevel-=1
        if (currentRowLevel==0)
            this.currentColumn=0
        else {
            this.currentColumn=currentColumnLevelBeforeRow
        }

        /*// Create a new group
        var newGroup = group 
        newGroup.id = id

        // Add to current head
        groupsStack.head <= newGroup

        // Apply placement constraints

        // Push on stack to execute closure
        groupsStack.push(newGroup)
        cl
        groupsStack.pop*/


    }

    def row(node:SGNode[Any],constraintsBase: Tuple2[String,Any]*) : Unit = {

        // Add 
        groupsStack.head <= node

        // Apply Constraints
        var constraints = new LayoutConstraints
        constraintsBase.foreach {
            t => constraints.constraints = constraints.constraints + t
        }
        if (this.currentConstraints!=null) {
            constraints(this.currentConstraints)
        }
        
        constraints.constraints = constraints.constraints + ("row" -> currentRow)
        constraints.constraints = constraints.constraints + ("column" -> currentColumn )

        groupsStack.head.layout.applyConstraints(node,constraints)

        // Increment row and reset column
        this.currentRow+=1
        //this.currentColumn=0

        //this.row(node)
        //groupsStack.head.layout.applyConstraints(node,constraints)
    }

    
    //----- Columns content
    //-----------------------
    class ColumnLanguageWrapper(var left: SGNode[Any]) {

        var nodesStack = List[SGNode[Any]]()
        nodesStack =  nodesStack :+ left 

        def addNodes = {

            println("-> add nodes")
            nodesStack.foreach {
                n => GridBuilder.this.column(n) 
            }
        }


        def using(constraint: Tuple2[String,Any]*) :  ColumnLanguageWrapper = {
            constraint.foreach {
                c => this.using(c)
            }
            this
        }
        def using(constraint: Tuple2[String,Any]) :  ColumnLanguageWrapper = {

            
            left.isInstanceOf[StylableTrait] match {
                case true if( left.asInstanceOf[StylableTrait].fixedConstraints == null) =>
                            left.asInstanceOf[StylableTrait].fixedConstraints = LayoutConstraints(constraint)
                 case true if( left.asInstanceOf[StylableTrait].fixedConstraints != null) =>
                            left.asInstanceOf[StylableTrait].fixedConstraints(constraint)
                case _ =>
            }
           

            this

        }

        def | (right: ColumnLanguageWrapper) : ColumnLanguageWrapper = {

            right.nodesStack.foreach {
                node => nodesStack = nodesStack :+ node
            }
            this

        }
        def | (right : SGNode[Any]) : ColumnLanguageWrapper = {

            println(s"**** Column Chain $left | $right *****")

            // Add Left and right
            //---------------
            /*GridBuilder.this.row {
                GridBuilder.this.column(left) 
                GridBuilder.this.column(right) 
            }*/
            nodesStack = nodesStack :+ right

            this
        }


        

        def | (row : RowContentWrapper) : Unit = {

            // Add Left
            //---------------
            GridBuilder.this.column(left)

            // Execute Right
            //----------------
            GridBuilder.this.row {
                row.contentClosure()
            }

            
        }

        def | (content : => Unit) : Unit = {

            // Add Left
            //---------------
            GridBuilder.this.column(left)

            // Execute Right
            //----------------
            content

            
        }

        /**
            normal content add but left is spanning
        */
        /*def spanRight(count : Int)(cl: => Unit) : Unit = {

            // Add Left
            //---------------
            GridBuilder.this.column(left)

        }*/

        def spanRight(count : IntWrapper) : Unit = {

            // Add Left
            //---------------
            //println("******* Inside spanRight ***********")
            GridBuilder.this.column(left,"colspan" -> count.count,"expandHeight" -> true)

            var actualRow = GridBuilder.this.currentRow

            // Execute Right
            //--------------------
            count.execute

            var rowsAdded = GridBuilder.this.currentRow - actualRow

            println(s"spanRight added $rowsAdded rows")
        }

    }
    implicit def convertSGNodeToColumnWrapper(node: SGNode[Any]) = new ColumnLanguageWrapper(node)

    class RightContentWrapper {

        var closure : () => Unit = null

        def execute = {
            closure()
        }

        def apply( closure : => Unit) : RightContentWrapper= {

            var wrapper = {
                () => closure
            }
            this.closure = wrapper

            this

        }

    }
    class IntWrapper(var count : Int) extends RightContentWrapper {

        override def apply(cl : => Unit) : IntWrapper = {
            super.apply({cl})
            this
        }
    }
    implicit def convertIntToIntWrapper(count: Int) = new IntWrapper(count)

    def column(node:SGNode[Any],constraintsBase: Tuple2[String,Any]*) = {

    
        // Add 
        groupsStack.head <= node

        // Apply Constraints
        var constraints = LayoutConstraints( "row" -> currentRow , "column" -> currentColumn )
        constraints(constraintsBase)

        if (this.currentConstraints!=null) {
            constraints(this.currentConstraints)
        }


        groupsStack.head.layout.applyConstraints(node,constraints)

        // Increment Column
        this.currentColumn+=1

    
        
    }

}
