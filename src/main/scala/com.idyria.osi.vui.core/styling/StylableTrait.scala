/**
 *
 */
package com.idyria.osi.vui.core.styling

import com.idyria.osi.vui.core.components.layout._
import com.idyria.osi.vui.core.constraints.Constrainable

/**
 *
 * Inherited by a component if it should support styling through CSS3
 *
 * @author rleys
 *
 */
trait StylableTrait extends Constrainable {

 
  /**
   * Apply the provided CSS3 string
   */
  def style(content: String) = {}

}

