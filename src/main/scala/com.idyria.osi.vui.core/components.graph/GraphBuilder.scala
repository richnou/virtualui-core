package com.idyria.osi.vui.core.components.graph

import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.components.layout._

import com.idyria.osi.vui.core.components.scenegraph._

import scala.language.implicitConversions

trait GraphBuilder extends VBuilder {

    var groupsStack = scala.collection.mutable.Stack[SGGroup[Any]]()

    var currentRow = 0 

    var currentColumn = 0

    var currentConstraints : LayoutConstraints = null

    // Language
    //---------------------------
    class RowLanguageWrapper(var left: String) {

        def row(cl: => Unit) = {
            GraphBuilder.this.row(left)(cl)
        }
        
        def row(node: SGNode[Any]) = {
            GraphBuilder.this.row(node)
        }

        def expandRow(node: SGNode[Any]) = {
            GraphBuilder.this.row(node, ("expand" -> true))
        }

        def row(wrapper: AlignRightFunction) = {

            println(s"Adding row with right constraints $left")



            GraphBuilder.this.currentConstraints = LayoutConstraints("align"->"right")
            GraphBuilder.this.row(left) {
                wrapper.cl()
            }
            
            GraphBuilder.this.currentConstraints = null

            //GraphBuilder.this.row(left, ("expand" -> true))

        }

    }
    implicit def convertStringToRow(str:String) = new RowLanguageWrapper(str)

    class AlignRightFunction(var cl : ( () => Unit) )    {

    }

    def alignRight (cl: => Unit) :  AlignRightFunction = {

        var clwrapper = { () =>
            cl
        }
        return new AlignRightFunction(clwrapper)
    }

    def graph(cl: => Unit) : SGGroup[Any] = {

        // Create a new group
        var newGroup = group 

        // Set Grid Layout
        newGroup layout grid

        // Push on stack to execute closure
        groupsStack.push(newGroup)
        cl
        groupsStack.pop

    }



    def - (id:String)(cl: =>Unit) = row(id)(cl)
    def row (id:String)(cl: =>Unit) = {

        // Execute closure

       
        // Execute
        cl

         // Increment row
        this.currentRow+=1
        this.currentColumn=0

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
        constraints.constraints = constraints.constraints + ("row" -> currentRow)
        constraints.constraints = constraints.constraints + ("column" -> currentColumn )

        groupsStack.head.layout.applyConstraints(node,constraints)

        // Increment row
        this.currentRow+=1
        this.currentColumn=0

        //this.row(node)
        //groupsStack.head.layout.applyConstraints(node,constraints)
    }

   /* def row(node:SGNode[Any])  : Unit = {

        this.row(node,Seq[Tuple2[String,Any]]())
        


        // Just add to head
        /*groupsStack.head <= node*/
     
    }*/

    def |(nodes:SGNode[Any]*) = {

        nodes.foreach {

            node => 
                // Add 
                groupsStack.head <= node

                // Apply Constraints
                var constraints = LayoutConstraints( "row" -> currentRow , "column" -> currentColumn )
                if (this.currentConstraints!=null) {
                    constraints(this.currentConstraints)
                }
                groupsStack.head.layout.applyConstraints(node,constraints)

                // Increment Column
                this.currentColumn+=1

        }
        
    }

}
