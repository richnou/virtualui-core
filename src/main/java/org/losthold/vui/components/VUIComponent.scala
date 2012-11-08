/**
 *
 */
package org.losthold.vui.components

/**
 * 
 * This is a global trait for all components that are supposed to be used by VUI
 * @author rleys
 *
 */
trait VUIComponent[T] {

  /**
   * Trait method to be implemented to fetch back an implementation specific type
   */
  def toBase : T
  
}