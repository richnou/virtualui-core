/**
 *
 */
package com.idyria.osi.vui.core

import com.idyria.osi.vui.core.components.containers.ScrollPaneBuilder
import com.idyria.osi.vui.core.components.containers.SplitPaneBuilder
import com.idyria.osi.vui.core.components.containers.TabPaneBuilder
import com.idyria.osi.vui.core.components.containers.TitledPaneBuilder
import com.idyria.osi.vui.core.components.containers.VUIPanel
import com.idyria.osi.vui.core.components.controls.ControlsBuilder
import com.idyria.osi.vui.core.components.controls.TreeBuilder
import com.idyria.osi.vui.core.components.form.FormBuilderInterface
import com.idyria.osi.vui.core.components.form.ListBuilder
import com.idyria.osi.vui.core.components.form.VUICheckBox
import com.idyria.osi.vui.core.components.form.VUIInputText
import com.idyria.osi.vui.core.components.layout.VUIFreeLayout
import com.idyria.osi.vui.core.components.layout.VUIGridLayout
import com.idyria.osi.vui.core.components.layout.VUIHBoxLayout
import com.idyria.osi.vui.core.components.layout.VUIStackPane
import com.idyria.osi.vui.core.components.layout.VUIVBoxLayout
import com.idyria.osi.vui.core.components.main.VuiFrame
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.components.table.TableBuilder
import com.idyria.osi.vui.lib.chart.ChartBuilder
import com.idyria.osi.vui.core.components.form.FormBuilder
import com.idyria.osi.vui.core.components.controls.SliderBuilder
import com.idyria.osi.vui.core.components.controls.SpinnerBuilder
import com.idyria.osi.vui.core.components.controls.ProgressBarBuilderInterface
import com.idyria.osi.vui.core.components.controls.ProgressBarBuilder
import com.idyria.osi.vui.core.components.controls.TableTreeBuilder

/**
 * @author rleys
 *
 */
trait VBuilderBase[T] extends TabPaneBuilder[T]
    with SplitPaneBuilder[T]
    with ScrollPaneBuilder[T]
    with TitledPaneBuilder[T]
    with TableBuilder[T]
    with TreeBuilder[T]
    with ListBuilder[T]
    with ChartBuilder[T]
    with ControlsBuilder[T]
    with FormBuilder[T]
    with SliderBuilder[T]
    with SpinnerBuilder[T]
    with ProgressBarBuilder[T]
    with TableTreeBuilder[T] {

  // Utils
  //--------------------

  /**
   * The provided closure should run on the UI thread
   * The default implementation just runs the closure on current Thread
   *
   * Should be overriden by implementors
   */
  def onUIThread(cl: => Unit) = VUIBuilder.selectedImplementation[T].onUIThread({ cl })

  def onVUIError (cl: Throwable => Unit) = VUIError.onError { cl }
  
  // Main
  //-------------------------
  def frame: VuiFrame[T] = VUIBuilder.selectedImplementation[T].frame
  def window(title: String): VuiFrame[T] = {
    VUIBuilder.selectedImplementation[T].frame() {
      f => f.title(title)
    }
  }

  // Scene Graph
  //--------------------------

  def group: SGGroup[T] = VUIBuilder.selectedImplementation[T].group()

  // Layouts
  //-------------------
  def vbox: VUIVBoxLayout[T] = VUIBuilder.selectedImplementation[T].vbox
  def hbox: VUIHBoxLayout[T] = VUIBuilder.selectedImplementation[T].hbox
  def grid: VUIGridLayout[T] = VUIBuilder.selectedImplementation[T].grid
  def none: VUIFreeLayout[T] = VUIBuilder.selectedImplementation[T].none
  def stack: VUIStackPane[T] = VUIBuilder.selectedImplementation[T].stack

  // Containers
  //----------------
  //def tabpane : VUITabPane[T] = VUIBuilder.selectedImplementation[T].tabpane
  def panel: VUIPanel[T] = VUIBuilder.selectedImplementation[T].panel

  //def switch(content: VuiSwitchLayout[Any] => Unit) : VuiSwitchLayout[Any] = VUIBuilder.selectedImplementation[Any].switch(content)
}
trait VBuilder extends VBuilderBase[Any] {

  def apply[Any](node: Any, cl: (Any => Unit)): Any = {
    cl(node)
    node
  }

}
