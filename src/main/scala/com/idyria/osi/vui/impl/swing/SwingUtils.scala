package com.idyria.osi.vui.impl.swing

import javax.swing.SwingUtilities

object SwingUtils {

  def invokeLater(cl: => Unit) = {
    SwingUtilities.invokeLater(new Runnable() {

      def run() = {
        cl
      }
    });
  }
}