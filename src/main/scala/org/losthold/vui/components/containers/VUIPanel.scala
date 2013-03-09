/**
 *
 */
package org.losthold.vui.components.containers

import org.losthold.vui.components.VUIComponent
import org.losthold.vui.components.scenegraph.SGGroup

/**
 * a VUIPanel is a simple container group, but customisable like a UI component
 * 
 * @author rleys
 *
 */
trait VUIPanel[T] extends VUIComponent[T] with SGGroup[T] {

}