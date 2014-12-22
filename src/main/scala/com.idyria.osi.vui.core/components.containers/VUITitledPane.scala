package com.idyria.osi.vui.core.components.containers

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.VUIBuilder
import com.idyria.osi.vui.core.components.scenegraph.SGNode

/**
 * Main Trait for a split pane
 * Per default Vertical
 */
trait VUITitledPane[T] extends VUIComponent[T] with SGGroup[T] with StylableTrait with ApplyTrait {

  type Self = VUITitledPane[T]

  /**
   * Add the provided node as child
   */
  def apply(nd: SGNode[T]) = this <= nd

  /**
   * Opens the Titled Pane
   */
  def open
  
  /**
   * Close the Titled Pane
   */
  def close
  
  /**
   * Sets if the panel can be opened/closed, or must always be openened
   */
  def setCollapsible(b: Boolean)
  
}

trait TitledPaneBuilder[T] extends TitledPaneBuilderInterface[T] {

  /**
   * Creates a tabpane component to store nodes into panes
   */
  def titledPane(title: String): VUITitledPane[T] = VUIBuilder.as[TitledPaneBuilderInterface[T]].titledPane(title)

}

trait TitledPaneBuilderInterface[T] {

  /**
   * Creates a tabpane component to store nodes into panes
   */
  def titledPane(title: String): VUITitledPane[T]

}
