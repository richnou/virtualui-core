/**
 *
 */
package com.idyria.osi.vui.impl.swing

import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import com.idyria.osi.vui.core.components.main.VuiFrame
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.impl.VuiTypeWrapper
import com.idyria.osi.vui.impl.swing.builders._
import javax.swing.JFrame
import javax.swing.WindowConstants
import com.idyria.osi.vui.core.VUIBuilder
import com.idyria.osi.vui.core.components.layout.VUILayout
import com.idyria.osi.vui.impl.swing.main.SwingFrame
import javax.swing.SwingUtilities

/**
 * @author rleys
 *
 */
class SwingVUIImpl
    extends VUIBuilder[Component]
    with SwingControlsBuilder
    with SwingContainerBuilder
    with SwingScrollPaneInterface
    with SwingLayoutBuilder
    with SwingSceneGraphBuilder
    with SwingFormBuilder {

  
  // Utils
  //------------
  override def onUIThread(cl: => Unit) {

    SwingUtilities.invokeLater(new Runnable() {
      def run = cl
    })
  

  }
  
  def frame(): VuiFrame[Component] = {
    
    return new SwingFrame
  }
  
  /**
   * From PreImpl
   */
 /* def frame(): VuiFrame[Component] = {

    //- -Create Frame
    val swingFrame = new JFrame()

    //-- Return FrameView Container
    new VuiTypeWrapper[JFrame](swingFrame) with VuiFrame[Component] {

      // Node
      //---------------
      override def revalidate = {
        base.revalidate
        base.repaint()
      }

      // Per default dispose
      base.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)

      override def setName(str: String) = {
        super.setName(str)
        this.title(str)
      }
      def getName: String = base.getTitle

      // Add A Container node per default
      //----------
      var topNode = SwingVUIImpl.this.panel
      this.node(topNode)

      //-----------------
      // VuiFrame

      def show() = {

        if (this.topNode.base.isInstanceOf[java.awt.Container]) {

          var container = this.topNode.base.asInstanceOf[java.awt.Container]
          if (container.getComponentCount == 1 && container.getComponent(0).isInstanceOf[java.awt.Container]) {

            base.setContentPane(container.getComponent(0).asInstanceOf[java.awt.Container])
            println("Content pane has only one container, so set this container as top node ")
          }
        }

        base.setVisible(true)

      }

      //------------------
      //-- Events

      /**
       * Register closure in swing listener
       */
      override def onClose(cl: => Unit) = this.base.addWindowListener(new WindowAdapter() {
        override def windowClosed(e: WindowEvent) = cl
      })

      //------------------
      //-- Control
      def title(title: String) = base.setTitle(title)

      def size(width: Int, height: Int) = {
        base.setSize(width, height)
      }
      def width(width: Int) = base.setSize(new Dimension(width, base.getSize().height))
      def height(height: Int) = base.setSize(new Dimension(base.getSize().width, height))

      /**
       * Add a node to this node container
       */
      override def node[NT <: SGNode[Component]](ndef: NT): NT = {

        super.node(ndef)

        (topNode == ndef) match {

          case true =>

            println("Setting content pane to: " + ndef.base)
            base.setContentPane(ndef.base.asInstanceOf[Container])
            revalidate
          case false =>

            println("Adding node to top pane: " + ndef.base)
            topNode.node(ndef)
            topNode.revalidate
        }

        ndef
      }

      //-------------------
      //-- Layout
       this.onWith("layout.updated"){
         l : VUILayout[Component] => 
           topNode.layout = l
       }
      /*var definedLayout: VUILayout[Component] = null

      def layout(l: VUILayout[Component]) = {

        topNode.layout(l)
        println("Setting Layout to: " + topNode.base)

      }

      def layout: VUILayout[Component] = this.definedLayout*/

      /**
       * Clear Content of this Pane and reset a default panel
       */
      def clear = {

        //base.setContentPane(null)
        topNode = SwingVUIImpl.this.panel
        this.node(topNode)

        revalidate
      }

      /**
       * Does not do anything
       */
      def removeChild(c: SGNode[_]) = {

      }

      /**
       * Do not return anything
       */
      def children: Seq[SGNode[Component]] = {
        Nil
      }

    }

  }*/

}
