package com.agilogy.pdf

import com.agilogy.pdf.Alignment.Alignment
import com.lowagie.text.pdf.PdfPCell
import com.lowagie.text.{Element => IElement, Paragraph => IParagraph}

case class Cell(content: ITextableElement, colSpan: Int = 1, rowSpan: Int = 1, style: CellStyle = CellStyle.Default) extends ITextableElement {
  private[pdf] def toItext(currentPage: Int, totalPages: Int) = {
    val cell = new PdfPCell()
    cell.setVerticalAlignment(IElement.ALIGN_MIDDLE)
    cell.setColspan(colSpan)
    cell.setRowspan(rowSpan)
    cell.setBorder(style.border.toIText)
    cell.addElement(content.toItext(currentPage, totalPages))
    val padding = style.padding
    cell.setPaddingLeft(padding.paddingLeft)
    cell.setPaddingRight(padding.paddingRight)
    cell.setPaddingTop(padding.paddingTop)
    cell.setPaddingBottom(padding.paddingBottom)
    cell
  }
}

object Cell {
  val Empty: Cell = Cell(Paragraph())

  def emptyLateral = lateral()
  def boxed(content: String, colSpan: Int = 1, rowSpan: Int = 1, paragraphStyle: ParagraphStyle = ParagraphStyle.Default) =
    Cell(Paragraph(content, style = paragraphStyle), colSpan = colSpan, rowSpan = rowSpan, style = CellStyle(border = Border.Box))
  def boxedBold(content: String, colSpan: Int = 1, rowSpan: Int = 1, alignment: Alignment = Alignment.Center) =
    Cell(Paragraph(content, style = ParagraphStyle(FontStyle(bold = true), alignment = alignment)), colSpan = colSpan, rowSpan = rowSpan, style = CellStyle(border = Border.Box))
  def lateral(content: String = "", paragraphStyle: ParagraphStyle = ParagraphStyle.Default) =
    Cell(Paragraph(content, style = paragraphStyle), style = CellStyle(border = Border.Sides))
  def last(content: String, paragraphStyle: ParagraphStyle = ParagraphStyle.Default) =
    Cell(Paragraph(content, style = paragraphStyle), style = CellStyle(border = Border(top = false, bottom = true, left = true, right = true)))

  def apply(text: String): Cell = Cell(Paragraph(text))
}