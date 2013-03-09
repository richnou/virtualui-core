/**
 *
 */
package org.losthold.vui.components

/**
 * @author rleys
 *
 */
class VUIMouseEvent extends VUIComponentEvent {

  /**
   * The actual X position of the mouse in the component coordinates
   */
  var actualX : Int = -1
  
  /**
   * The actual Y position of the mouse in the component coordinates
   */
  var actualY : Int = -1
  
  var previousX : Int = -1
  
  var previousY : Int = -1
  
}