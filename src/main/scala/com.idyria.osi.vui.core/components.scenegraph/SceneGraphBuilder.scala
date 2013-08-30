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
  def group( cl: SGGroup[T] => Unit ) : SGGroup[T] = {
        var group = this.group;
        cl(group)
        group
    }

}
