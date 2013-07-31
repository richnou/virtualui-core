package com.idyria.osi.vui.core.impl.swing

import com.idyria.osi.vui.core.VBuilder
import com.idyria.osi.vui.core.components.controls.VUILabel
import com.idyria.osi.vui.core.generic.diagram.DiagramBuilder

object SwingVuiDemo extends App {

  println("Swing VUI Demo")

  object ui extends VBuilder {

    var demoFrame = frame {

      frame =>

        frame size (800, 600)

        frame node group { g =>

          g layout vbox

          g <= label("Hello1")
          g <= label("Hello1") {
             l =>
          }
          g <= label("Hello1")

          var hello2Group = g node group {
            g =>
	            g layout vbox
	            g node label("Hello2")
	            g node label("Hello2")
	            g node label("Hello2")
          }

          g <= button("Change layout") { b =>
            b onClicked {
              println("I got clicked, and I change a layout thing")
              hello2Group layout hbox

            }
          }

          g {
            group { g =>

              g layout vbox
              g node label("Hello3")
              g node label("Hello3")
              g node label("Hello3")
            }
          }

          var hello4Group = g <= group { g =>

            g layout vbox
            g node label("Hello4")
            g node label("Hello4")
            g node label("Hello4")
          }

          g <= button("Fade out Hello4") { b =>
            b onClicked {
              println("I got clicked, and I change a layout thing")
              hello2Group layout hbox

            }
          }

        }

    }

  }

  //ui.demoFrame.show

  object diagramUI extends VBuilder with DiagramBuilder {

    var uiFrame = frame { f =>

      f title "Diagram test"
      f size (800, 600)

      f node group { g =>

        g layout vbox
        g node label("Welcome to diagram test")

        // Diagram
        var test1 : VUILabel[Any] = null
        g <= group { g =>

          g layout none

          test1 = g <= diagramNode {
            label("Test 1") { l =>
              l setX 10
              l setY 0
            }
          }

          g <= label("Test 2") { l =>
            l setX 10
            l setY 20
          }

        } // EOF Diagram

        g <= button("Move Test1") {
          b =>
          	b.onClicked {
          	  println("Moving Test1")
          	  //test1 setPosition(50,10)
          	  test1 deltaX 30
          	   test1 deltaY 30
          	   println("Position is: "+test1.getPosition)
          	}
        }

      }

    }
  }

  ui.demoFrame show

}
