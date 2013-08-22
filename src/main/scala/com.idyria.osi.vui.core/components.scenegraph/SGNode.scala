/**
 *
 */
package com.idyria.osi.vui.core.components.scenegraph

import com.idyria.osi.tea.listeners.ListeningSupport


/**
 *
 * Base Node for all Scene Graph Nodes
 *
 * @author rleys
 *
 */
trait SGNode[+T] extends ListeningSupport {

    /**
        Optional ID for the Node
    */
    var id : String = ""

    def base : T

    /**
    * This method is a high level call to ask the underlying implementation
    * to make sure the node has been redrawn
    */
    def revalidate

    //def apply[NT <: SGNode[T]](cl : (NT => Unit))

}
