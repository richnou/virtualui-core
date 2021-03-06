package com.idyria.osi.vui.lib.view.components

import com.idyria.osi.vui.core.components.controls._
import com.idyria.osi.vui.lib.view._
import com.idyria.osi.tea.logging.TLogSource

class ViewButton(var view: View, var delegate: VUIButton[Any]) extends VUIButton[Any] with TLogSource {

  // node
  //-----------------
  override def setName(str: String) = delegate.setName(str)

  // Global
  //-----------------
  override def disable = delegate.disable
  override def enable = delegate.enable

  // Action Wrapping
  //--------------------

  override def onClicked(action: => Any) = {

    //-- Wrap action to catch errors fro view
    //-- Register to delegate
    delegate.onClicked({

      logFine(s"----> Click from View Button and view $view")
      view.action({ action })

    })
    //delegate.onClicked({action})

  }

  // Delegate Stuff
  //-------------------------

  // Members declared in com.idyria.osi.vui.core.components.scenegraph.SGNode
  def base: Any = delegate.base
  def revalidate: Unit = delegate.revalidate

  // Members declared in com.idyria.osi.vui.core.components.VUIComponent
  def getPosition: (Int, Int) = delegate.getPosition
  def setPosition(x: Int, y: Int): Unit = delegate.setPosition(x, y)

  //---- Styling
  def size(width: Int, height: Int) = delegate.size(width, height)

}
