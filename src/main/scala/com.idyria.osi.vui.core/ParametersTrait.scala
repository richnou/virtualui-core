/**
 *
 */
package com.idyria.osi.vui.core

/**
 *
 * This is a trait to have generic parameter setting to be applied on current tree element
 * @author rleys
 *
 */
trait ParametersTrait {

  var current : Unit

  def size (width:Int,height:Int) {

    println("Setting size on: "+current);
    //if (current )

  }

}
