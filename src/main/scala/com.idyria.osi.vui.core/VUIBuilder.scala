/**
 *
 */
package com.idyria.osi.vui.core

import scala.Predef.Set.apply
import com.idyria.osi.vui.core.components.containers.ContainerBuilder
import com.idyria.osi.vui.core.components.controls.ControlsBuilder
import com.idyria.osi.vui.impl.swing.SwingVUIImpl
import com.idyria.osi.vui.core.components.main.MainBuilder
import com.idyria.osi.vui.core.components.scenegraph.SceneGraphBuilder
import com.idyria.osi.vui.core.components.layout.LayoutBuilder
import com.idyria.osi.vui.core.components.form.FormBuilder
import scala.reflect.runtime.universe._
import scala.reflect.ClassTag
import com.idyria.osi.vui.core.components.containers.TabPaneBuilder

/**
 * @author rleys
 *
 */
abstract class VUIBuilder[T] extends ContainerBuilder[T]

    with LayoutBuilder[T]
    with SceneGraphBuilder[T]
    with MainBuilder[T]
    with UtilsTrait {

}

object VUIBuilder {

  var defaultImplementation: VUIBuilder[_] = (new SwingVUIImpl).asInstanceOf[VUIBuilder[_]]

  var selectedImplementations = Map[Thread, VUIBuilder[_]]()

  /**
   *
   */
  def setImplementationForCurrentThread(impl: VUIBuilder[_]) = {

    selectedImplementations = selectedImplementations + (Thread.currentThread() -> impl)

  }

  /**
   * Returns the currently selected application by current thread
   */
  def selectedImplementation[T]: VUIBuilder[T] = {

    selectedImplementations.get(Thread.currentThread()) match {
      case Some(impl) => impl.asInstanceOf[VUIBuilder[T]]
      case None       => defaultImplementation.asInstanceOf[VUIBuilder[T]]
    }

    //return VUIBuilder.findImplementations.head.asInstanceOf[VUIBuilder[T]]

    //return SwingVUIImpl.asInstanceOf[VUIBuilder[T]]

    //return actualImplementation.asInstanceOf[VUIBuilder[T]]

  }

  def findImplementations: Set[VUIBuilder[_]] = {

    return Set(new SwingVUIImpl)

  }

  // Implementation getters
  //---------------
  def as[T](implicit tag: ClassTag[T]): T = {

    tag.runtimeClass.isAssignableFrom(this.selectedImplementation.getClass()) match {
      case true  => this.selectedImplementation.asInstanceOf[T]
      case false => throw new RuntimeException("Currently selected implementation does not support interface " + tag)
    }

  }

}
