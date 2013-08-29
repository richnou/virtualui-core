package com.idyria.osi.vui.lib.gridbuilder2

import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.components.layout._

import com.idyria.osi.vui.core.components.scenegraph._

import com.idyria.osi.vui.core.styling._


import scala.language.implicitConversions

trait GridBuilder extends VBuilder {

    var groupsStack = scala.collection.mutable.Stack[Grid]()

    //var currentRow = 0 

   // var currentColumn = 0

    /**
        The row level is used to detect rows in rows, and then not reset column count until we are back to row level 0
    */
   // var currentRowLevel = 0

    // var currentColumnLevel = 0

    //var currentConstraints : LayoutConstraints = null

    // Language
    //---------------------------
   

    

    

    //------------------------------------------------
    // Main grid Interface
    //------------------------------------------------

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

    def grid(set: ConstraintsSet)(cl: => Any) : SGGroup[Any] = {

        // Create a new group
        //---------------------------
        var newGroup = group 
        if (set.constraints.size >0 ) {
             newGroup.fixedConstraints = LayoutConstraints()
             set.constraints.constraints.foreach(newGroup.fixedConstraints(_))
        }
       

        // Create A new Grid Context instance to reset columns and rows
        //-----------------
        var grid = new Grid(newGroup)

        // Push on stack to execute closure
        //----------------------------------
        groupsStack.push(grid)
        cl
        groupsStack.pop

        grid.group

    }

    def grid(constraints: (String,Any)*)(cl: => Any) : SGGroup[Any] = (this.grid(this.using(constraints)){cl})

    
    def grid(cl: => Any) : SGGroup[Any] = {

        // Create a new group
        //---------------------------
        var newGroup = group 
       
       

        // Create A new Grid Context instance to reset columns and rows
        //-----------------
        var grid = new Grid(newGroup)

        // Push on stack to execute closure
        //----------------------------------
        println("##################### GRID #############################")
        groupsStack.push(grid)
        cl
        groupsStack.pop
        println("##################### EOF GRID #############################\n")
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
        /*println("##################### GRID #############################")
        groupsStack.push(grid)
        cl
        groupsStack.pop
        println("##################### EOF GRID #############################\n")
        //grid.group*/
        grid
    }






    // Constraining Language
    //------------------------------------------------
    trait UsingConstraints {



    }
    /**
        Return a new constraints Set for this Sequence of Constraints
    */
    /*def using(constraints: Tuple2[String,Any]*) :  ConstraintsSet = {


        var constraintsSet = new ConstraintsSet
        constraints.foreach {
            c => constraintsSet.constraints(c)
        }
        constraintsSet
    }*/
    /**
        Return a new constraints Set for this Sequence of Constraints
    */
    def using(constraints: Seq[Tuple2[String,Any]]) :  ConstraintsSet = {


        var constraintsSet = new ConstraintsSet
        constraints.foreach {
            c => constraintsSet.constraints(c)
        }
        constraintsSet
    }
    def using(constraint: Tuple2[String,Any]) :  ConstraintsSet = {

        
        var constraintsSet = new ConstraintsSet
        constraintsSet.constraints(constraint)
        constraintsSet

    }

    /**
        Merge Multiple Constraints sets into one, to allow syntax like: using(spread,expandWidth)
    */
    def using(sets: List[ConstraintsSet]) : ConstraintsSet = {

        var set = new ConstraintsSet
        sets.foreach { _.constraints.constraints.foreach(set.constraints(_))}
        set
    }

    def alignLeft = this.using("align" -> "left")
    def alignRight = this.using("align" -> "right")
    def spread = this.using("spread"->true)
    def pushRight = this.using("pushRight" -> true)
    def expandWidth = this.using("expandWidth"->true)
    def expandHeight = this.using("expandHeight"->true)
    def expand = this.using("expand"->true)
    
    class ConstraintsSet {

         var constraints = new LayoutConstraints

    }

    implicit def convertTuplesToConstraintSet(cstr: Tuple2[String,Any]*) : ConstraintsSet = {

        var set = new ConstraintsSet
        cstr.foreach { set.constraints(_)}
        set

    }

    implicit def convertTupleToConstraintSet(cstr: Tuple2[String,Any]) : ConstraintsSet = {
        return using(cstr)
    }

    

    /**
        This conversion is mainly used if the Constraints is to be applied on the right, because no explicit method call required the ConstraintSet on the left
        This is why the resolve implementation if looking ahead
    */
    implicit def convertConstraintsToRowLanguageElement(cstr: ConstraintsSet) : LanguageChainElement = {

        new LanguageChainElement {

             name = "Constraints"

            def doResolve = {
                println("--> Doing constraints set")

                // Apply on next if it is a column
                //--------------
                if (this.nextElement!=null && this.nextElement.isInstanceOf[Column]) {
                    this.nextElement.asInstanceOf[Column].using(cstr)
                }

            }
        }

    }


   /* class ConstraintsWrapper(var cl : ( () => Unit) )    {

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
        
    }*/

    /**  Add a user define constraint to enclosed content
        Example: 

        "id" row using("align" -> "right") {
            // Content
        }
    */
   /* def using(constraints: (String,Any)*)(cl: => Unit) : ConstraintsWrapper = {


        var clwrapper = { () =>
            cl
        }
        var constraintsWrapper = new ConstraintsWrapper(clwrapper)
        constraints.foreach(constraintsWrapper(_))


        GridBuilder.this.currentConstraints = constraintsWrapper.constraints
        cl
        GridBuilder.this.currentConstraints = null

        constraintsWrapper
    }*/

    /** Add an align right to enclosed content
        Example: 

        "id" row alignRight {
            // Content
        }
    */
    /*def alignRight (cl: => Any) :  ConstraintsWrapper = {


        var clwrapper : (() => Unit) = { () =>
            cl
        }
        return new ConstraintsWrapper(clwrapper)("align" -> "right")
    }
    def alignRight (column: ColumnLanguageWrapper) :  ConstraintsWrapper = {

        var clwrapper = { () =>
            column.addNodes
        }
        return new ConstraintsWrapper(clwrapper)("align" -> "right")
    }*/


    /** Add an align right to enclosed content
        Example: 

        "id" row alignRight {
            // Content
        }
    */
    /*def alignLeft (cl: => Any) :  ConstraintsWrapper = {


        var clwrapper : (() => Unit) = { () =>
            cl
        }
        return new ConstraintsWrapper(clwrapper)("align" -> "left")
    }
    def alignLeft(column: SGNode[Any]) : ConstraintsWrapper = this.alignLeft(new ColumnLanguageWrapper(column))
    def alignLeft (column: ColumnLanguageWrapper) :  ConstraintsWrapper = {


        var clwrapper = { () =>
            column.addNodes
        }
        return new ConstraintsWrapper(clwrapper)("align" -> "left")
    }*/

    
    // Common Chanining Language
    //----------------
    trait LanguageChainElement {

        var name = "unnamed"

        var nextElement : LanguageChainElement = null

        final def resolve : Unit = {
            println(s"-> Applying chain $name")
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

           // println(s" ---- Chaining $name to ${languageElement.name}")
            current.nextElement = languageElement
            this
        }

        def doResolve : Unit

    }

    implicit def convertContentClosureToRowLanguageElement(cl: => Unit) : LanguageChainElement = {

         println("Creating Content closure as LanguageChainElement")

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
    class Row(var left: String) extends LanguageChainElement with UsingConstraints {

        name = "Row"

       // var languageElements = List[RowLanguageElement]()

        // Apply Opens the row for the current Grid, and starts content
        //----------
        /*def apply(content: => Any) = {


        }*/
        
        def doResolve = {
           // println("Opening Row")
        }

        // Language Right to "row" keyword
        //---------------------

        /**
            row alone : row {
    
            }
        */
        /*def row : Row = {
            this
        }*/

        /**
            row using(constraint -> value*)
        */
       /* def row(right: ConstraintsSet) : Row = {
            this
        }*/

        def row(right: LanguageChainElement) : Row  = {
            //languageElements = languageElements :+ right

            var rowLevel = groupsStack.head.currentRowLevel+1
            var rowDbg = (0 to rowLevel+1).map{i => "*"}.mkString
            println(s"""${rowDbg} Starting Row $left *******""")
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
            println(s"""${rowDbg} Closing Row $left *******\n""")
        
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
        
        /*def row(node: SGNode[Any]) = {
            GridBuilder.this.row(node)
        }

       

        def row(right: ColumnLanguageWrapper) = {
            GridBuilder.this.row(left) {
                right.addNodes
            }
        }


        def expandRow(node: SGNode[Any]) = {
            GridBuilder.this.row(node, ("expand" -> true))
        }


        
        def row(wrapper: ConstraintsWrapper) = {

            //println(s"Adding row with constraints $left")



            GridBuilder.this.currentConstraints = wrapper.constraints
            GridBuilder.this.row(left) {
                wrapper.cl()
            }
            
            GridBuilder.this.currentConstraints = null

            //GridBuilder.this.row(left, ("expand" -> true))

        }*/

    }
    implicit def convertStringToRow(str:String) = new Row(str)

    abstract class RowLanguageElement {

        var name = "unnamed"

        var nextElement : RowLanguageElement = null

        final def resolve : Unit = {
            println(s"-> Applying to $name")
            this.doResolve
            if (nextElement!=null)
                nextElement.resolve
        }

        /**
            Save element to the right as next, and return self for left composition
        */
        def apply(languageElement: RowLanguageElement) : RowLanguageElement = {

            println(s" ---- Chaining $name to ${languageElement.name}")
            this.nextElement = languageElement
            this
        }

        def doResolve : Unit

    }
    
     
   

    /**
        Do Nothing if a ColumnLanguage is found, because this one already handles everything correctly
    */
    /*def row( column: ColumnLanguageWrapper) : Unit = {

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
    }*/

    
    //----- Columns content
    //-----------------------

    /**
        Class to represent a column, and handle language elements allowed 

        A Column is always hosting a node
    */
    class Column(var left: SGNode[Any]) extends LanguageChainElement with UsingConstraints {

        name = "Column"

        def doResolve : Unit  = {

            println(s"-> Adding column content(${left.toString}#${left.base.hashCode}")

            // Add 
            //-------------
            groupsStack.head.group <= left

            // Apply Constraints
            //------------------------

            //-- Row + Column position
            var constraints = LayoutConstraints( "row" -> groupsStack.head.currentRow , "column" -> groupsStack.head.currentColumn )
            //constraints(constraintsBase)

            //-- Current Global Constraints for all elements


            /*if (this.currentConstraints!=null) {
                constraints(this.currentConstraints)
            }
*/

            groupsStack.head.group.layout.applyConstraints(left,constraints)

            // Increment Column
            groupsStack.head.currentColumn+=1

        }

        // Constraining Language
        //--------------------------

        def using(constraints: ConstraintsSet) : Column = {

            // this.apply(GridBuilder.this.using(constraint))
            var newColumn = new Column(left) {
                name = "Column Element constraint"

                override def doResolve = {
                     groupsStack.head.group.layout.applyConstraints(left,constraints.constraints)
                }
            }
            this.apply(newColumn)
            this

        }

        def using(constraints: Tuple2[String,Any]*) : Column = this.using(GridBuilder.this.using(constraints))

        

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

                    println(s"spanRight added $rowsAdded rows")
                    groupsStack.head.group.layout.applyConstraints(left,GridBuilder.this.using("rowspan"->rowsAdded).constraints)

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
        /*def spanRight(cl: LanguageChainElement) : Column = {

            this.apply(new LanguageChainElement{

                name = "SpanRight"

                def doResolve = {

                    // Note actual row
                    //------------------------
                    var actualRow = groupsStack.head.currentRow

                    // Execute Right
                    //--------------------
                    //cl.resolve

                    var rowsAdded = groupsStack.head.currentRow - actualRow

                    println(s"spanRight added $rowsAdded rows")

                }

            })

            

            this
        }*/

        /*var nodesStack = List[SGNode[Any]]()
        nodesStack =  nodesStack :+ left 

        def addNodes = {

            println(s"-> add (${nodesStack.size}) nodes")
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

            
        }*/

        /**
            normal content add but left is spanning
        */
        /*def spanRight(count : Int)(cl: => Unit) : Unit = {

            // Add Left
            //---------------
            GridBuilder.this.column(left)

        }*/

        /*def spanRight(count : IntWrapper) : Unit = {

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
        }*/

    }

    /**
        A Single Node can be a column
    */
    implicit def convertSGNodeToColumn(node: SGNode[Any]) = new Column(node)

    /*class RightContentWrapper {

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
    */

    /*def column(node:SGNode[Any],constraintsBase: Tuple2[String,Any]*) = {

    
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

    
        
    }*/

}
