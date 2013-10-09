package com.idyria.osi.vui.lib.placeholder

import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.components.scenegraph._
import com.idyria.osi.vui.core.components.layout._
import com.idyria.osi.vui.lib.gridbuilder._

trait PlaceHolder[NT <: SGNode[Any]] {

    var placeHolders = Map[String,NT]()

    /**
        Create a Group for the given placeholder
    */
    def placeHolder(name  : String ) : NT = {

    	this.placeHolders.get(name) match {
            case Some(node) => node
            case None => 
              
           /*   var newGroup = group
	        newGroup layout grid
	        placeHolders = placeHolders + (name -> newGroup)
	        newGroup*/
              
              throw new IllegalArgumentException(s"Could not Retrieve component @$name because place does not exist")
              
    	}
      
        

    }

    /**
        Place a component to defined place holder
    */
    def place(name: String)(component: NT) = {
    	
       placeHolders = placeHolders + (name -> component)
      
        /*this.placeHolders.get(name) match {
            case Some(placeHolder) => 
                    //placeHolder.clear
                    //placeHolder<=component

                    //placeHolder.layout.applyConstraints(component,LayoutConstraints("expand"->true))

                    //placeHolder.revalidate
              
              		placeHolders = placeHolders + (name -> component)
              
            case None  => 
              
              
              //throw new IllegalArgumentException(s"Could not place component @$name because place does not exist")
        }
		*/
    }
    
}
