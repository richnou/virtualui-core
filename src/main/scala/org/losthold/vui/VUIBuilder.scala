/**
 *
 */
package org.losthold.vui

import scala.Predef.Set.apply
import org.losthold.vui.components.containers.ContainerBuilder
import org.losthold.vui.components.controls.ControlsBuilder
import org.losthold.vui.swing.SwingVUIImpl
import org.losthold.vui.components.main.MainBuilder
import org.losthold.vui.components.scenegraph.SceneGraphBuilder
import org.losthold.vui.components.layout.LayoutBuilder
import org.losthold.vui.components.form.FormBuilder

/**
 * @author rleys
 *
 */
abstract class VUIBuilder[T] extends  ContainerBuilder[T] 
							with ControlsBuilder[T] 
							with LayoutBuilder[T]
							with SceneGraphBuilder[T]
							with MainBuilder[T]
							with FormBuilder[T]{

}

object VUIBuilder {
  
  
  /**
   * Returns the currently selected application by current thread
   */
  def selectedImplementation[T] : VUIBuilder[T] = {
    
    return VUIBuilder.findImplementations.head.asInstanceOf[VUIBuilder[T]]
    
    //return SwingVUIImpl.asInstanceOf[VUIBuilder[T]]
    
  }
  
  def findImplementations : Set[VUIBuilder[_]] = {
    
    return Set(new SwingVUIImpl)
    
    
  }
  
  
}