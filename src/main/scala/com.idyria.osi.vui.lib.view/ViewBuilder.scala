package com.idyria.osi.vui.lib.view


import com.idyria.osi.tea.listeners.ListeningSupport

import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.components._
import com.idyria.osi.vui.core.components.controls._
import com.idyria.osi.vui.lib.view.components._

import com.idyria.osi.vui.core.components.scenegraph._

import scala.language.implicitConversions


/**
    A Trait to mixin lamguage to create Views
*/
trait ViewBuilder extends  VBuilder with ListeningSupport {

    var currentView : View = null

    override def button(text: String)(implicit cl: VUIButton[Any] => Unit) : VUIButton[Any] = {

        var button = new ViewButton(currentView,super.button(text))
        apply(button,cl);
        button

    }

    // Define a View
    //-------------------------
    class ViewDefinitionWrapper(left:View) {


        def is(content: SGGroup[Any] => Unit ) = {

            // Add View To Group
            //-----------------------
            //ViewGroup.this.view += left
            ViewBuilder.this.currentView = left

            @->("view.current",left)

            

            // Build Closure
            //----------------
            content(left.content)

        }

        def is(node: SGGroup[Any] ) = {

            // Add View To Group
            //-----------------------
            //ViewGroup.this.view += left
            ViewBuilder.this.currentView = left

            @->("view.current",left)
 
            

            // Set content to view
            //----------------
            left.content = node


        }
        
    }

    implicit def stringtoViewDefinitionWrapper(str: String) : ViewDefinitionWrapper = {

        var view = new View 
        view.name = str
        view.id = str 

        new ViewDefinitionWrapper(view) 

    }
    implicit def viewtoViewDefinitionWrapper(view: View) : ViewDefinitionWrapper = new ViewDefinitionWrapper(view) 
    implicit def stringToView(str:String) : View = {
        var view = new View 
        view.name = str
        view.id = str 

        view
    }

    /*override def button(text: String)(cl: => String) : VUIButton[Any] = {

        // Create Button
        var button = new ViewButton(super.button(text))
        apply(button,{});

        // Add view Click action
        button

    } */

}
