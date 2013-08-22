/**
 *
 */
package com.idyria.osi.vui.core.styling

import com.idyria.osi.vui.core.components.layout._

/**
 *
 * Inherited by a component if it should support styling through CSS3
 * 
 * @author rleys
 *
 */
trait StylableTrait {

    // Fixed constraints for Special usage 
    //-----------------
    var fixedConstraints : LayoutConstraints = null

  /**
   * Apply the provided CSS3 string
   */
  def style(content:String) = {}


}
