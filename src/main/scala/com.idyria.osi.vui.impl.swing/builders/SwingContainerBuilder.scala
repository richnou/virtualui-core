/**
 *
 */
package com.idyria.osi.vui.impl.swing.builders

import java.awt.Component
import com.idyria.osi.vui.core.components.containers.ContainerBuilder
import com.idyria.osi.vui.core.components.containers.VUIPanel
import com.idyria.osi.vui.core.components.containers.VUITabPane
import com.idyria.osi.vui.core.components.layout.VUILayout
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.scenegraph.SceneGraphBuilder
import javax.swing.JPanel
import javax.swing.JTabbedPane
import javax.swing.JComponent
import com.idyria.osi.vui.core.components.containers.VUITab
import com.idyria.osi.vui.core.components.layout.LayoutConstraintsLanguage
import com.idyria.osi.vui.core.components.containers.ScrollPaneBuilderInterface
import javax.swing.JScrollPane
import com.idyria.osi.vui.core.components.containers.SplitPaneBuilderInterface
import javax.swing.JSplitPane
import com.idyria.osi.vui.core.components.containers.VUISplitPane
/**
 * @author rleys
 *
 */
trait SwingContainerBuilder extends ContainerBuilder[Component] with SceneGraphBuilder[Component] with SplitPaneBuilderInterface[Component] with LayoutConstraintsLanguage {

  /**
   * Creates a tabpane component to store nodes into panes
   */
  def tabpane: VUITabPane[Component] = {

    return new SwingJComponentCommonDelegate[JTabbedPane](new JTabbedPane) with VUITabPane[Component] {

      override def clear = {
        super.clear
      }

      /**
       * Node method override to add components as tabs
       */
      def node[NT <: SGNode[Component]](title: String)(content: NT): NT = {
        super.node(content)
        delegate.addTab(title, content.base)
        content
      }

      def addTab[NT <: SGNode[Component]](tabname: String)(node: NT): VUITab[Component] = {

        // Prepare Tab
        //-----------
        var tab = new VUITab[Component] {

          def setClosable(v: Boolean) = {

          }

        }
        //node(expand)
        delegate.addTab(tabname, node.base)

        tab

      }

    }

  }

  /**
   * Return group as JPanel:
   *
   */
  def group(): SGGroup[Component] = panel

  /**
   * Build panel
   */
  def panel: VUIPanel[Component] = {

    return new SwingJComponentCommonDelegate[JPanel](new JPanel) with VUIPanel[Component] {

      //-----------------------------
      // Scene Graph

      //var definedLayout : VUILayout[Component] = null

      override def clear = {
        super.clear
      }

      /**
       * Node Add
       */
      this.onWith("child.added") {
        nd: SGNode[Component] =>

          delegate.add(nd.base)
          revalidate

      }

      /**
       * Add The node to the jpanel
       */
      /*override def node[NT <: SGNode[Component]](nd: NT): NT = {

          super.node(nd)
          
          // Add to group
          delegate.add(nd.base)

          // If we have a layout, notify it
          if (definedLayout!=null) {
            definedLayout nodeAdded nd
          }

          // Revalidate
          nd.revalidate
          this.revalidate
          this.delegate.validate
          this.delegate.invalidate
          
          nd

        }
*/
      /*def clear = {
          delegate.removeAll
          delegate.revalidate
        }*/

      /*def layout(l: VUILayout[Component]) = {

           this.definedLayout = l
           l.setTargetGroup(this)

        }

        def layout : VUILayout[Component] = this.definedLayout*/

    }

    /*
    new JPanel() with VUIPanel[Component] with SwingStylableTrait {

      //-----------------------------
      // Scene Graph

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

      }

      //-----------------------------
      // Positioning
      def setPosition(x:Int,y:Int) = super.setLocation(x, y)
      def getPosition : Pair[Int,Int] = Pair[Int,Int](super.getLocation().x,super.getLocation().y)

      // Dummy Overrides for JComponent compatibility
      override def setX(x:Int) = super.setX(x)
      override def getX: Int = getPosition._1
      override def setY(y:Int) = super.setY(y)
      override def getY: Int = getPosition._2

    }

*/

  }

  /**
   * Creates a Tab to be inserted inside a tabpane
   */
  /*def tab : VUITab[Container] = {


    new JTab

  }*/

  def splitpane: VUISplitPane[Component] = {
    return new SwingJComponentCommonDelegate(new JSplitPane()) with VUISplitPane[Component] {

      // Initial Interface
      //------------------------------
      this.delegate.setLeftComponent(null)
      this.delegate.setRightComponent(null)
      this.delegate.setTopComponent(null)
      this.delegate.setTopComponent(null)

      override def clear = {
        super.clear
      }

      def setVertical = {
        this.delegate.setOrientation(JSplitPane.VERTICAL_SPLIT)
      }
      def setHorizontal = {
        this.delegate.setOrientation(JSplitPane.HORIZONTAL_SPLIT)
      }

      // Child Add
      //------------------
      /**
       * Node Add
       */
      this.onWith("child.added") {
        nd: SGNode[Component] =>

          this.delegate.getOrientation() match {
            case JSplitPane.VERTICAL_SPLIT if (this.delegate.getTopComponent() == null) => this.delegate.setTopComponent(nd.base)
            case JSplitPane.VERTICAL_SPLIT => this.delegate.setBottomComponent(nd.base)
            case JSplitPane.HORIZONTAL_SPLIT if (this.delegate.getLeftComponent() == null) => this.delegate.setLeftComponent(nd.base)
            case JSplitPane.HORIZONTAL_SPLIT => this.delegate.setRightComponent(nd.base)
          }

          revalidate

      }

    }
  }

}

trait SwingScrollPaneInterface extends ScrollPaneBuilderInterface[Component] {

  def scrollpane: com.idyria.osi.vui.core.components.containers.VUIPanel[Component] = {
    return new SwingJComponentCommonDelegate(new JScrollPane()) with VUIPanel[Component] {

      // Add Content Panel
      //-----------------
      //var pane = new Pane
      //this.delegate.setContent(pane)
      // this.delegate.fitToHeightProperty().set(true)
      //this.delegate.fitToWidthProperty().set(true)
      /*this.delegate.fitToHeightProperty().set(true)
      this.delegate.fitToWidthProperty().set(true)
      this.delegate.setPrefViewportWidth( javafx.scene.layout.Region.USE_COMPUTED_SIZE)
      this.delegate.setPrefWidth(javafx.scene.layout.Region.USE_COMPUTED_SIZE)*/

      // Add node
      //----------------
      this.onMatch("child.added") {

        case n: SGNode[_] ⇒

          this.delegate.setViewportView(n.base.asInstanceOf[Component])
          this.revalidate
        //pane.getChildren().add(n.base.asInstanceOf[Node])

        //println(s"****** Adding nodes to ${this.delegate}: " + n.base+" which has children: "+n.base.asInstanceOf[GridPane].getChildren().size())

        case _ ⇒
      }

      // Conflicts resolution
      override def clear: Unit = {

        this.delegate.removeAll()
        super.clear
      }

    }
  }

}
