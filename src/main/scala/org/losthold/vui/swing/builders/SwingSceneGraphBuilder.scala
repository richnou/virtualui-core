/**
 *
 */
package org.losthold.vui.swing.builders

import java.awt.Component
import org.losthold.vui.components.scenegraph.SGGroup
import org.losthold.vui.components.scenegraph.SceneGraphBuilder
import javax.swing.JPanel
import org.losthold.vui.components.scenegraph.SGNode
import org.losthold.vui.components.layout.VUILayout
import java.awt.LayoutManager

/**
 * @author rleys
 *
 */
trait SwingSceneGraphBuilder extends SceneGraphBuilder[Component] {

  /**
   * Return group as JPanel:
   *  
   */
  def group(cl: SGGroup[Component] => Unit): SGGroup[Component] = {

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

      def layout(l: VUILayout[Component]) = {
        
    	this.definedLayout = l
        l.setTargetGroup(this)
        //this.setLayout(l.asInstanceOf[LayoutManager]);

      }

    }

    // Apply content
    cl(group)

    group

  }

}