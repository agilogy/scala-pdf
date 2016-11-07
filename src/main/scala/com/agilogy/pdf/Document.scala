package com.agilogy.pdf

import java.io.OutputStream

import com.agilogy.pdf.util.NullOutputStream
import com.lowagie.text.pdf.{ColumnText => IColumnText, _}
import com.lowagie.text.{PageSize, Document => IDocument, Phrase => IPhrase, Chunk => IChunk}

case class Document(margins: Margins, pageHeader: Seq[ITextableElement], elements: Seq[Element],
                    pageFooter:Option[Seq[ITextableElement]] = None, leftSideVerticalContent:Option[Seq[Phrase]] = None) {

  def generatePdf(os: OutputStream): Unit = {
    val totalPages = generatePdf(new NullOutputStream, 0)
    generatePdf(os, totalPages)
  }

  private def generatePdf(os: OutputStream, totalPages: Int): Int = {
    val document = new IDocument(PageSize.A4)
    val writer = PdfWriter.getInstance(document, os)
    val header = oneCellTable(PageSize.A4.getWidth, pageHeader) _
    val footer = pageFooter.map(f => oneCellTable(PageSize.A4.getWidth, f) _)
    document.setMargins(margins.leftMargin, margins.rightMargin, header(0, 0).getTotalHeight + margins.topMargin,
      footer.fold(margins.bottomMargin)(_(0,0).getTotalHeight + margins.bottomMargin))
    document.setMarginMirroring(margins.leftRightMirroring)
    document.setMarginMirroringTopBottom(margins.topBottomMirroring)

    var returnTotalPages = 0
    writer.setPageEvent(new PdfPageEventHelper {
      override def onEndPage(writer: PdfWriter, document: IDocument): Unit = {
        val page = writer.getCurrentPageNumber
        returnTotalPages += 1
        header(page, totalPages).writeSelectedRows(0, 1, margins.leftMargin, writer.getPageSize.getTop - 25f, writer.getDirectContent)
        footer.foreach(f => f(page,totalPages).writeSelectedRows(0, 1, margins.leftMargin, writer.getPageSize.getBottom + f(page,totalPages).getTotalHeight + 25f, writer.getDirectContent))
        var newSidePhrasePosition = 15f
        leftSideVerticalContent.foreach (_.foreach { p =>
            val phraseWithMarginsSize = new IChunk(p.text,p.fontStyle.toFont).getWidthPoint
            IColumnText.showTextAligned(writer.getDirectContent, Alignment.Center.id, p.toItext(0, 0).asInstanceOf[IPhrase], newSidePhrasePosition, (PageSize.A4.getHeight - phraseWithMarginsSize)/2, 90f)
            newSidePhrasePosition += 7f
          }
        )
      }
    })
    document.open()
    elements.foreach {
      case PageBreak => document.newPage()
      case e: ITextableElement => document.add(e.toItext(0, 0))
    }
    document.close()
    returnTotalPages
  }

  def oneCellTable(width: Float, elements: Seq[ITextableElement])(currentPage: Int, totalPages: Int) = {
    val table = new PdfPTable(1)
    table.setTotalWidth(width)
    val cell = new PdfPCell()
    cell.setBorder(0)
    elements.foreach(h => cell.addElement(h.toItext(currentPage, totalPages)))
    table.addCell(cell)
    table
  }
}

object Document {
  def apply(elements: ITextableElement*) = new Document(Margins.Default, Nil, elements)
}