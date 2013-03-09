/**
 *
 */
package org.losthold.vui.components.main

import org.losthold.vui.VUIBuilder

/**
 * @author rleys
 *
 */
trait MainBuilder[T] {

  
  
  def frame( content : VuiFrame[T] => Unit) : VuiFrame[T]  = VUIBuilder.selectedImplementation.frame(content)
  
  
  
}