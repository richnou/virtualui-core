/**
 *
 */
package org.losthold.vui

import org.losthold.vui.components.main.MainBuilder
import org.losthold.vui.components.containers.ContainerBuilder
import org.losthold.vui.components.controls.ControlsBuilder
import org.losthold.vui.components.layout.VuiSwitchLayout
import org.losthold.vui.components.scenegraph.SceneGraphBuilder
import org.losthold.vui.components.scenegraph.SGGroup
import org.losthold.vui.components.layout.LayoutBuilder
import org.losthold.vui.components.layout.VUIVBoxLayout
import org.losthold.vui.components.controls.VUIButton
import org.losthold.vui.components.controls.VUILabel
import org.losthold.vui.components.layout.VUIHBoxLayout
import org.losthold.vui.components.layout.VUIFreeLayout
import org.losthold.vui.components.containers.VUITabPane
import org.losthold.vui.components.containers.VUIPanel


/**
 * @author rleys
 *
 */
trait VBuilderBase[T] extends MainBuilder[T] 
				with ContainerBuilder[T] 
				with LayoutBuilder[T]
				with ControlsBuilder[T]
				with SceneGraphBuilder[T]	{

  
  
 // def frame( content : VuiFrame => Unit) : VuiFrame = VUIBuilder.selectedImplementation.frame(content)
 
  
 
  
  
  // Controls
  //--------------------
  def button(text: String)(cl: VUIButton[T] => Unit) : VUIButton[T] = VUIBuilder.selectedImplementation[T].button(text)(cl);
  def label(text:String) : VUILabel[T] = VUIBuilder.selectedImplementation[T].label(text);
  
  // Scene Graph
  //--------------------------
  
  def group(cl : SGGroup[T] => Unit ) : SGGroup[T] = VUIBuilder.selectedImplementation[T].group(cl)
  
  // Layouts
  //-------------------
  def vbox : VUIVBoxLayout[T] = VUIBuilder.selectedImplementation[T].vbox
  def hbox : VUIHBoxLayout[T] = VUIBuilder.selectedImplementation[T].hbox
  def none : VUIFreeLayout[T] = VUIBuilder.selectedImplementation[T].none
  
  // Containers
  //----------------
  def tabpane(cl: VUITabPane[T] => Unit) : VUITabPane[T] = VUIBuilder.selectedImplementation[T].tabpane(cl)
  def panel(cl: VUIPanel[T] => Unit) : VUIPanel[T] = VUIBuilder.selectedImplementation[T].panel(cl)
  
  //def switch(content: VuiSwitchLayout[Any] => Unit) : VuiSwitchLayout[Any] = VUIBuilder.selectedImplementation[Any].switch(content)
}
trait VBuilder extends VBuilderBase[Any] {
  
}