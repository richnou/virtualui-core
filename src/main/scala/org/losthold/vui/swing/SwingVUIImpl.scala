/**
 *
 */
package org.losthold.vui.swing

import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import org.losthold.vui.components.main.VuiFrame
import org.losthold.vui.components.scenegraph.SGNode
import org.losthold.vui.impl.VuiTypeWrapper
import org.losthold.vui.swing.builders.SwingContainerBuilder
import org.losthold.vui.swing.builders.SwingLayoutBuilder
import org.losthold.vui.swing.builders.SwingSceneGraphBuilder
import javax.swing.JFrame
import javax.swing.WindowConstants
import org.losthold.vui.VUIBuilder

/**
 * @author rleys
 *
 */
class SwingVUIImpl 
					extends VUIBuilder[Component]
					with SwingControlsBuilder
					with SwingContainerBuilder
					with SwingLayoutBuilder
					with SwingSceneGraphBuilder
					with SwingFormBuilder {
 
  /**
   * From PreImpl
   */
  def frame(): VuiFrame[Component] = {

    //- -Create Frame
    val swingFrame = new JFrame()

    //-- Return FrameView Container
    new VuiTypeWrapper[JFrame](swingFrame) with SwingContainerTrait with VuiFrame[Component] {

      // Node
      //---------------
      override def revalidate = base.revalidate
      
      // Per default dispose
      base.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)

      
      //-----------------
      // VuiFrame

      //------------------
      //-- Events
      
      /**
       * Register closure in swing listener
       */
      override def onClose(cl : => Unit) =  this.base.addWindowListener(new WindowAdapter(){
          override def  windowClosed( e: WindowEvent) = cl   
        })
   
      
      //------------------
      //-- Control
      def title(title: String) = base.setTitle(title)

      def size(width: Int, height: Int) = {
        base.setSize(width, height)
      }
      def width(width: Int) = base.setSize(new Dimension(width, base.getSize().height))
      def height(height: Int) = base.setSize(new Dimension(base.getSize().width, height))

      def show() {
        base.setVisible(true)
      }

      /**
       * Add a node to this node container
       */
      def node[NT <: SGNode[Component]](ndef: NT) : NT = {

    	println("Setting content pane to: "+ndef.base)
        base.setContentPane(ndef.base.asInstanceOf[Container])
        ndef
      }

    }

  }

}
object SwingVUIImpl  {

}