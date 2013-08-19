/**
 *
 */
package com.idyria.osi.vui.core.components.scenegraph

/**
 * @author rleys
 *
 */
trait SceneGraphBuilder[T] {


  def group( ) : SGGroup[T]

}
