/**
 *
 */
package com.idyria.osi.vui.core

import scala.Predef.Set.apply
import com.idyria.osi.vui.core.components.containers.ContainerBuilder
import com.idyria.osi.vui.core.components.controls.ControlsBuilder
import com.idyria.osi.vui.core.swing.SwingVUIImpl
import com.idyria.osi.vui.core.components.main.MainBuilder
import com.idyria.osi.vui.core.components.scenegraph.SceneGraphBuilder
import com.idyria.osi.vui.core.components.layout.LayoutBuilder
import com.idyria.osi.vui.core.components.form.FormBuilder

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
