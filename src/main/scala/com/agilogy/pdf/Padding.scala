package com.agilogy.pdf

case class Padding(paddingLeft: Float, paddingRight: Float, paddingTop: Float, paddingBottom: Float) {

}

object Padding {
  val Default = Padding(5f, 5f, 1f, 3f)
}
