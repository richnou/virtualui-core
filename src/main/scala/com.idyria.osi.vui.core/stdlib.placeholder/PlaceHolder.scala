package com.idyria.osi.vui.core.stdlib.placeholder

import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.components.scenegraph._

trait PlaceHolder extends VBuilder {

    var placeHolders = Map[String,SGGroup[Any]]()

    /**
        Create a Group for the given placeholder
    */
    def placeHolder(name  : String ) : SGGroup[Any] = {

        var newGroup = group
        placeHolders = placeHolders + (name -> newGroup)
        newGroup

    }

    /**
        Place a component to defined place holder
    */
    def place(name: String)(component: SGNode[Any]) = {

        this.placeHolders.get(name) match {
            case Some(placeHolder) => 
                    placeHolder.clear
                    placeHolder<=component
                    placeHolder.revalidate
            case None  => throw new IllegalArgumentException(s"Could not place component @$name because place does not exist")
        }

    }
    
}
