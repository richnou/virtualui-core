/**
 *
 */
package org.losthold.vui.styling

/**
 * 
 * Inherited by a component if it should support styling through CSS3
 * 
 * @author rleys
 *
 */
trait StylableTrait {

  /**
   * Apply the provided CSS3 string
   */
  def style(content:String) = {}
  
  
}