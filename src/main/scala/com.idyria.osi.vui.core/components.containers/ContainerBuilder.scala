/**
 *
 */
package com.idyria.osi.vui.core.components.containers

import com.idyria.osi.vui.core.components.layout.VUILayout
import com.idyria.osi.vui.core.components.layout.VUIVBoxLayout
import com.idyria.osi.vui.core.components.layout.VuiSwitchLayout
import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.VUIBuilder

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
trait ContainerBuilder[T] extends TabPaneBuilderInterface[T]  {


  def panel : VUIPanel[T]


}


// Tab Pane
//------------------------------------

trait TabPaneBuilder[T] extends TabPaneBuilderInterface[T] {

    /**
   * Creates a tabpane component to store nodes into panes
   */
  def tabpane : VUITabPane[T] = VUIBuilder.as[TabPaneBuilderInterface[T]].tabpane

}


trait TabPaneBuilderInterface[T] {

    /**
   * Creates a tabpane component to store nodes into panes
   */
  def tabpane : VUITabPane[T]

}


// Split Pane
//------------------------------------

trait SplitPaneBuilder[T] extends SplitPaneBuilderInterface[T] {

    /**
   * Creates a tabpane component to store nodes into panes
   */
  def splitpane : VUISplitPane[T] = VUIBuilder.as[SplitPaneBuilderInterface[T]].splitpane

}


trait SplitPaneBuilderInterface[T] {

    /**
   * Creates a tabpane component to store nodes into panes
   */
  def splitpane : VUISplitPane[T]

}

// SColl Panel
//----------------
trait ScrollPaneBuilder[T] extends ScrollPaneBuilderInterface[T] {

    /**
   * Creates a tabpane component to store nodes into panes
   */
  def scrollpane : VUIPanel[T] = VUIBuilder.as[ScrollPaneBuilderInterface[T]].scrollpane

}


trait ScrollPaneBuilderInterface[T] {

    /**
   * Creates a tabpane component to store nodes into panes
   */
  def scrollpane : VUIPanel[T]

}

