/**
 *
 */
package org.losthold.vui.components.controls

import org.losthold.vui.components.VUIComponent

/**
 * 
 * Trait to define requirements for a Label
 * @author rleys
 *
 */
trait VUILabel[T] extends VUIComponent[T] {

   def apply( content: VUILabel[T] => Unit) : VUILabel[T] = {content(this);this}
  
}