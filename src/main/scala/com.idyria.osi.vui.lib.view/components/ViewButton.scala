package com.idyria.osi.vui.lib.view.components

import com.idyria.osi.vui.core.components.controls._

import com.idyria.osi.vui.lib.view._

class ViewButton( var view : View, var delegate : VUIButton[Any]) extends VUIButton[Any] {

    // Action Wrapping
    //--------------------


    override def onClicked(action: => Any) = {

        //-- Wrap action to catch errors fro view
        //-- Register to delegate
        delegate.onClicked({

            println(s"----> Click from View Button and view $view")
            view.action({action})
           
        })
        //delegate.onClicked({action})
        
    }

    // Delegate Stuff
    //-------------------------

    // Members declared in com.idyria.osi.vui.core.components.scenegraph.SGNode
    def base: Any = delegate.base
    def revalidate: Unit = delegate.revalidate
   
    // Members declared in com.idyria.osi.vui.core.components.VUIComponent
    def getPosition: (Int, Int) = delegate.getPosition
    def setPosition(x: Int,y: Int): Unit = delegate.setPosition(x,y)



}
