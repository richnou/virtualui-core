/**
 *
 */
package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph.SGNode

import com.idyria.osi.vui.core._



/**
 *
 * Trait to define requirements for a Label
 * @author rleys
 *
 */
trait VUIImage[T] extends VUIComponent[T]  {

    //------------------------
    // General
    //------------------------
    /*override def disable = {
        throw NotImplementedException(s"button.disable not implemented in ${getClass}")
    }*/

    /**
        Performs the Load operation to display the image.
        This is decoupled from component creation to allow resizing and so on
    */
    def load

    // Automatic loading
    //----------------------
    this.onShown {
        //println("Showing Image")
        load
    }

}
