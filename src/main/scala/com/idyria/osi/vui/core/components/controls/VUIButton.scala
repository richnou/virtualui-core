/**
 *
 */
package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.styling.ApplyTrait

/**
 *
 * Trait to define requirements for a Label
 * @author rleys
 *
 */
trait VUIButton[T] extends VUIComponent[T] with StylableTrait with ApplyTrait  {

  type Self = VUIButton[T]

  //------------------------
  // General
  //------------------------
  /*override def disable = {
        throw NotImplementedException(s"button.disable not implemented in ${getClass}")
    }*/

  //------------------
  // API Control
  //-----------------
  def click: Unit = {
    throw new NotImplementedException
  }

  /**
   * Overrides Forked Click to have button disabled during execution
   */
  override def onClickFork(action: => Any): Unit = {
    super.onClickFork {
      onUIThread {
        disable
      }
      try {
        action
      } finally {
        onUIThread {
          enable
        }
      }

    }
  }

}
