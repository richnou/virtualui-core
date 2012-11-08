/**
 *
 */
package org.losthold.vui.components.containers

import org.losthold.vui.components.layout.VUIVBoxLayout
import org.losthold.vui.components.layout.VUILayout

/**
 * 
 * This trait defines component creation Methods.
 * 
 * They are to be implemented by provider, and their results are usually used by ContainerTrait to pack components
 * in layout traits
 * 
 * @author rleys
 *
 */
trait ContainerBuilder[T] {

  
  def vbox( content: VUILayout[T] => Unit) : VUIVBoxLayout[T] =  {
    
    
	  //-- Create VBOx Layout
      var vboxLayout = new VUIVBoxLayout[T]()
    
	  //-- Execute Content
      content(vboxLayout)
      
	  //-- Return
      return vboxLayout
    
    
  }
  
}