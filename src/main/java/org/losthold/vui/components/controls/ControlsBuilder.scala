/**
 *
 */
package org.losthold.vui.components.controls

/**
 * 
 * This trait defines the methods used to create the VUI controls
 * 
 * @author rleys
 *
 */
trait ControlsBuilder[T] {

  
  def label(text:String) : VUILabel[T];
  
  
}