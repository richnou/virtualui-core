package com.idyria.osi.vui.lib.view

import com.idyria.osi.vui.core.components.scenegraph.SGNode
import java.io.File
import com.idyria.osi.vui.core.VBuilder
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.components.main.VuiFrame
import com.idyria.osi.vui.core.constraints.Constrainable

class StandardAView extends AView[SGNode[Any]] with VBuilder {

  override def replaceWith(v: AView[_ <: SGNode[Any]]) = {
    println(s"Replacing with view: " + v.getClass.getCanonicalName)

    this.renderedNode match {
      case Some(node) if (node.parent == null) =>

        onUIThread {
          node.asInstanceOf[VuiFrame[Any]].close()
          var newNode = v.render
          newNode.asInstanceOf[VuiFrame[Any]].show()
        }

      //this.renderedNode = Some(newNode)

      //onUIThread()

      case Some(node) =>

        println(s"Actual NOde is: " + node + " // parent is: " + node.getParent)

        onUIThread {
          var oldParent: SGGroup[Any] = node.getParent
          oldParent.removeChild(node)
          
          var newNode = v.render
          newNode.asInstanceOf[Constrainable].fixedConstraints = node.asInstanceOf[Constrainable].fixedConstraints
          oldParent <= newNode
          renderedNode = Some(newNode)
        }

      case None =>
    }
  }

}

/*class StandardAViewFastAView extends StandardAView with DelayedInit {
  
  override def delayedInit(body: => Unit) {
    this.content {
      dummyNode {
        body
      }
      /*var res = body
      res match {
        case t if(classOf[HTMLNode].isAssignableFrom(t.getClass())) => t.asInstanceOf[HTMLNode]
        case _ => throw new RuntimeException("Constructor of Fast View must return an HTML Node")
      }*/
    }
  }
}*/

object StandardAViewCompiler extends AViewCompiler[StandardAView] {

  var eout = new File("target/classes")
  eout.mkdirs()
  compiler.settings2.outputDirs.setSingleOutput(eout.getAbsolutePath)

}