/**
 *
 */
package org.losthold.vui.swing

import org.losthold.vui.components.controls.ControlsBuilder
import org.losthold.vui.components.controls.VUILabel
import javax.swing.JLabel
import java.awt.Component

/**
 * @author rleys
 *
 */
trait SwingControlsBuilder extends ControlsBuilder[Component] {

  def label(text:String) : VUILabel[Component] = {
    
    new JLabel(text) with VUILabel[Component] {
      
      def toBase : Component = this
      
    }
    
  }
  
  
}