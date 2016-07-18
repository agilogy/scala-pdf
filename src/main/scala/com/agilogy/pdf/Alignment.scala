package com.agilogy.pdf

object Alignment extends Enumeration {
  type Alignment = Value
  val Left, Right, Justify, Center = Value
  class AlignmentValue(alignment: Value) {
    private[pdf] def toItext = {
      alignment match {
        case Left => 0
        case Right => 2
        case Justify => 3
        case Center => 1
      }
    }
  }
  implicit def value2AlignmentValue(alignment: Value): AlignmentValue = new AlignmentValue(alignment)
}
