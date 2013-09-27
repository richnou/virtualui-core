/**
 *
 */
package com.idyria.osi.vui.core.components.containers

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.styling.StylableTrait

/**
 * @author rleys
 *
 */
trait VUITab[T] extends VUIComponent[T] with SGGroup[T] with StylableTrait {

  type Self = VUITab[T]

}
