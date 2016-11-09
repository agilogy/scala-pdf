package com.agilogy.pdf

import com.lowagie.text.{Element => IElement, Phrase => IPhrase}

case class TotalPages(fontStyle: FontStyle = FontStyle.Default) extends ITextableElement {
  override private[pdf] def toItext(currentPageFn: () => Int, totalPages: Int): IElement = {
    val p = new IPhrase()
    p.setFont(fontStyle.toFont)
    p.add(totalPages.toString)
    p
  }
}

object TotalPages extends TotalPages(FontStyle.Default)