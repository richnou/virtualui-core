package com.idyria.osi.vui.lib.view

import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.components.scenegraph.SGNode

/**
 * Functional Trait mixed in Static view definition
 *
 * A View has per default an internal Scene Graph Group node
 *
 */
class View extends VBuilder with ViewTrait with ApplyTrait {

  /**
   * Base content of a view is a group
   */
  var content = group

  // Rendering
  //--------------

  var renderClosure: (() => SGNode[Any]) = null

  /**
   * Renders the content, per default uses a content closure
   */
  def render: SGNode[Any] = {
    renderClosure match {
      case null =>
        null
      case cl =>
        try {
          cl()
        } catch {
          case e: Throwable => panel {
            p => p<=label("An error occured while preparing the view: "+e.getLocalizedMessage())
          }
        }
    }

  }

  /**
   * Set the render closure
   * Usage example with view builder:
   *
   *  view("foo") {
   *  	v =>
   *   		v.render {
   *
   *
   *     		}
   *  }
   *
   */
  def onRender(cl: => SGNode[Any]) = renderClosure = { () => cl }

  // View Process Connection
  //--------------------------

  /**
   * A Reference to the view Process currently running, if any
   */
  var process: ViewProcess = null

  def action(act: => Any) = {

    if (process != null) {
      this.process.action({ act })
    }
  }

}
