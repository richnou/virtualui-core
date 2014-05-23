package com.idyria.osi.vui.lib.gridbuilder.doc

import com.idyria.osi.vui.lib.gridbuilder.GridBuilder

object RowSpan extends App with GridBuilder {

  //var url = getClass.getClassLoader.getResource("com.idyria.osi.vui.lib.gridbuilder/hd_logo_standard_sw_16cm_rgb.png")
  var cakeRabbit = image(getClass.getClassLoader.getResource("com.idyria.osi.vui.lib.gridbuilder.doc/cakeRabbit.jpg")) {
    img => img size (250, -1)
  }

  var ui = frame { f =>

    f size (800, 600)
    f title ("Grid Builder Examples")

    f <= grid {

      "main" row {

        tabpane {
          tp =>

            //-- Row Span Example
            tp.addTab("Row Span") {

              var g = grid {

                "main" row {
                  cakeRabbit spanRight {

                    "-" row {
                      button("Press") | button("those")
                    }
                    "-" row {
                      button("buttons")

                    }
                    "-" row {
                      button("for") | button("some") | button("fun")

                    }

                  }
                }

              }
              g
            }
        }(expand)

      }

    }

  }

  ui.show

}
