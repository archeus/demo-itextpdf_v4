import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfSignatureFormField;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;

public class PdfUaSignatureFixed {

    public static void main(String[] args) throws Exception {
        String dest = "pdfua_signature_fixed2.pdf";

        // Setup writer with UA metadata
        PdfWriter writer = new PdfWriter(dest, new WriterProperties().addUAXmpMetadata());
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setTagged();
        pdfDoc.getCatalog().setLang(new PdfString("en-US"));
        pdfDoc.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
        pdfDoc.getDocumentInfo().setTitle("PDF/UA with Signature Field");

        Document doc = new Document(pdfDoc);

        // Use embedded Unicode font
        PdfFont font = PdfFontFactory.createFont("/fonts/FreeSans.ttf", PdfEncodings.IDENTITY_H);
        doc.setFont(font);

        // Add a paragraph (tagged automatically by layout module)
        doc.add(new Paragraph("Please sign below:").setFont(font).setFontSize(12));

        // Create a signature form field
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Rectangle rect = new Rectangle(50, 600, 200, 50);
        PdfSignatureFormField sigField = PdfFormField.createSignature(pdfDoc);
        sigField.setFieldName("Signature1");

        // Create the widget and annotation
        PdfWidgetAnnotation widget = new PdfWidgetAnnotation(rect);
        PdfPage page = pdfDoc.getFirstPage();
//        page.addAnnotation(widget); this breaks PAC....
        sigField.addKid(widget);
        form.addField(sigField, page);

        // Tag the widget manually as Form
        TagTreePointer tagPointer = pdfDoc.getTagStructureContext().getAutoTaggingPointer();
        tagPointer.setPageForTagging(page);
        tagPointer.addTag(StandardRoles.FORM);
//        tagPointer.moveToKid(0);
        tagPointer.addAnnotationTag(widget);

        doc.close();
        System.out.println("Created: " + dest);
    }
}
