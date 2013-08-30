package com.idyria.osi.vui.lib.view


import com.idyria.osi.vui.core.components.scenegraph._

import scala.language.implicitConversions

import com.idyria.osi.tea.listeners.ListeningSupport

/**
    Trait mixed in ViewGroup model

    This trait defines language to define views

*/
trait ViewGroup extends ViewGroupTrait with ViewBuilder {

    // When a view is builded, add it
    //--------------
    onWith("view.current") {
        view : View => 

            println(s"Added view with id : ${view.id}")

            this.view+=view
    }




    
}
