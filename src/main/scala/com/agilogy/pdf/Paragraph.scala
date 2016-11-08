package com.agilogy.pdf

import com.lowagie.text.{Element => IElement, Paragraph => IParagraph, Phrase => IPhrase}

trait Paragraph extends ITextableElement

case class SimpleParagraph(text: String, style: ParagraphStyle) extends Paragraph {
  override private[pdf] def toItext(currentPage: Int, totalPages: Int): IElement = {
    val p = new IParagraph()
    p.setFont(style.fontStyle.toFont)
    p.setAlignment(style.alignment.toItext)
    p.setMultipliedLeading(style.multipliedLeading)
    p.setIndentationLeft(style.indentationLeft)
    p.setIndentationRight(style.indentationRight)
    p.setFirstLineIndent(style.firstLineIndentation)
    p.add(text)
    p
  }
  def withStyle(style: ParagraphStyle) = this.copy(style = style)
}

case class CompoundParagraph(elements: Seq[ITextableElement], style: ParagraphStyle) extends Paragraph {
  override private[pdf] def toItext(currentPage: Int, totalPages: Int): IElement = {
    val base = new IParagraph()
    base.setAlignment(style.alignment.toItext)
    base.setMultipliedLeading(style.multipliedLeading)
    base.setIndentationLeft(style.firstLineIndentation)
    elements.foreach(e => base.add(e.toItext(currentPage, totalPages)))
    base
  }
  def withStyle(style: ParagraphStyle) = this.copy(style = style)
}


object Paragraph {

  def apply(content: String, style: ParagraphStyle = ParagraphStyle()): SimpleParagraph = SimpleParagraph(content, style)

  def apply(content: ITextableElement*): CompoundParagraph = CompoundParagraph(content, ParagraphStyle.Default)

  def apply(style: ParagraphStyle, content: ITextableElement*): CompoundParagraph = CompoundParagraph(content, style)

  implicit def string2Phrase(text: String): ITextableElement = Phrase(text)
}