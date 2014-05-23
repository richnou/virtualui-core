/**
 *
 */
package com.idyria.osi.vui.core.components.containers

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.styling.ApplyTrait

/**
 * @author rleys
 *
 */
trait VUITab[T] extends ApplyTrait {

  type Self = VUITab[T]

  
  def setClosable(v:Boolean)
  
}
