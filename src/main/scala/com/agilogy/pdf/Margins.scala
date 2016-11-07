package com.agilogy.pdf

case class Margins(leftMargin: Float, rightMargin: Float, topMargin: Float, bottomMargin: Float,
                   leftRightMirroring: Boolean = false, topBottomMirroring: Boolean = false) {

}

object Margins {
  val Default = Margins(
    leftMargin = 5f,
    rightMargin = 5f,
    topMargin = 20f,
    bottomMargin = 20f
  )
}