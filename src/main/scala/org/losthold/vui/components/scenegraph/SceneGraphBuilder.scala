/**
 *
 */
package org.losthold.vui.components.scenegraph

/**
 * @author rleys
 *
 */
trait SceneGraphBuilder[T] {

  
  def group( ) : SGGroup[T]
  
}