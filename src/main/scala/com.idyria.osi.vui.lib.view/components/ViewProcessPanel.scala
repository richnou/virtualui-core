package com.idyria.osi.vui.lib.view.components

import com.idyria.osi.vui.lib.view.ViewProcess
import com.idyria.osi.vui.lib.gridbuilder.GridBuilder
import com.idyria.osi.vui.core.stdlib.node.SGCustomNode
import com.idyria.osi.vui.lib.view.View
import com.idyria.osi.vui.core.components.scenegraph.SGNode

/**
 * The View Process Panel is a SGNode component that manages the graphical connection to
 * a running view process
 */
class ViewProcessPanel extends SGCustomNode[Any] with GridBuilder {

  // View Process Connection
  //----------

  var _viewProcess: ViewProcess = null

  /**
   * Set New View process
   * Record listeners to make GUI changes based on its progression
   */
  def viewProcess_=(vp: ViewProcess): Unit = {

    this._viewProcess = vp

    this._viewProcess.onWith("view.progressTo") {
      v: View ⇒

      println("----> Switching view")
      
        // Update View Panels container
        viewsPanel.clear
        viewsPanel <= v.render
        viewsPanel.revalidate
    

    }
    println("----> Listening on: "+vp.hashCode())

  }

  def viewProcess: ViewProcess = this._viewProcess

  // View Change
  //-----------------

  //-- Base GUI
  val viewsPanel = group
  viewsPanel.layout = stack
  
  //-- On Base GUI child add, make the component grow
  viewsPanel.onWith("child.added") {
    n : SGNode[_] => n(expand) 
  }

  /**
   * UI -> Add a Group Grid
   */
  def createUI = {

    this.viewProcess match {
      case null ⇒
      case vp   ⇒ vp.resetProgress
    }

    viewsPanel
  }

}

/**
 * Factory to allow convenient definition of a view process inside a ViewProcessPanel:
 *
 * Example: (Enclosing class must mix trait ViewProcessBuilder)
 *
 * node <= ViewProcessPanel {
 *
 *  	viewProcess("name") {
 *
 *     	view("name") {
 *
 *			...
 *
 *     	}
 *
 *   	}
 * }
 */
object ViewProcessPanel {
  def apply: ViewProcessPanel = new ViewProcessPanel

  def apply(vp: ViewProcess) = {

    var vPanel = new ViewProcessPanel
    vPanel.viewProcess = vp
    vPanel
  }

}