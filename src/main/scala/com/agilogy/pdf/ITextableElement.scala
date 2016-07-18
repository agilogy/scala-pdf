package com.agilogy.pdf

import com.lowagie.text.{Element => IElement}


trait ITextableElement extends Element {
  private[pdf] def toItext(currentPage: Int, totalPages: Int): IElement
}
