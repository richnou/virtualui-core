/**
 *
 */
package com.idyria.osi.vui.impl.swing.builders

import java.awt.Component
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.components.scenegraph.SceneGraphBuilder
import javax.swing.JPanel
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.layout.VUILayout
import java.awt.LayoutManager

import  com.idyria.osi.vui.impl.swing._

/**
 * @author rleys
 *
 */
trait SwingSceneGraphBuilder extends SceneGraphBuilder[Component] {

  /**
   * Return group as JPanel:
   *
   */
 /*  def group(): SGGroup[Component] = {

    SwingVUIImpl.panel()
   
    var group = new JPanel() with SGGroup[Component] {

      var definedLayout : VUILayout[Component] = null

      def base() = this

      /**
       * Add The node to the jpanel
       */
      def node[NT <: SGNode[Component]](nd: NT): NT = {

        // Add to group
        this.add(nd.base)

        // If we have a layout, notify it
        if (definedLayout!=null) {
          definedLayout nodeAdded nd
        }

        nd

      }

      def clear = {
        this.removeAll
        this.revalidate
      }

      def layout(l: VUILayout[Component]) = {

    	this.definedLayout = l
        l.setTargetGroup(this)
        //this.setLayout(l.asInstanceOf[LayoutManager]);

      }

    }

    group

  }
*/
}
