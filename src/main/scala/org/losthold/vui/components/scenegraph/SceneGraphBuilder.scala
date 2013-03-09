/**
 *
 */
package org.losthold.vui.components.scenegraph

/**
 * @author rleys
 *
 */
trait SceneGraphBuilder[T] {

  
  def group(cl : SGGroup[T] => Unit ) : SGGroup[T]
  
}