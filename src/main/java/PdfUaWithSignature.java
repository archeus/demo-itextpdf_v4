import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfSignatureFormField;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.File;

public class PdfUaWithSignature {
    public static void main(String[] args) throws Exception {
        String dest = "pdfua_signature.pdf";

        PdfWriter writer = new PdfWriter(dest,
            new WriterProperties().addUAXmpMetadata());

        PdfDocument pdf = new PdfDocument(writer);
        pdf.setTagged();
        pdf.getCatalog().setLang(new PdfString("en-US"));
        pdf.getCatalog().setViewerPreferences(
            new PdfViewerPreferences().setDisplayDocTitle(true));
        pdf.getDocumentInfo().setTitle("PDF/UA with Signature Field");

        Document doc = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont(
            "resources/fonts/FreeSans.ttf", PdfEncodings.IDENTITY_H);
        doc.setFont(font);

        // Add accessible text
        Paragraph p = new Paragraph("Please sign below:")
            .setFont(font)
            .setFontSize(12)
            .setTextAlignment(TextAlignment.LEFT);
        doc.add(p);

        // Create signature field
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);

        PdfSignatureFormField sigField = PdfFormField.createSignature(pdf);
//        sigField.setFieldName("Signature1");
//        sigField.setPage(1);
//        sigField.setWidget(new com.itextpdf.kernel.geom.Rectangle(50, 650, 200, 50), PdfAnnotation.HIGHLIGHT_INVERT);
//        sigField.setFlags(PdfAnnotation.PRINT);
//        sigField.setBorderColor(ColorConstants.BLACK);
//        sigField.setColor(ColorConstants.LIGHT_GRAY);
//        form.addField(sigField);
//
//        // Tag the signature field with a Form structure element
//        TagTreePointer tagPointer = pdf.getTagStructureContext().getAutoTaggingPointer();
//        tagPointer.setPageForTagging(pdf.getPage(1));
//        tagPointer.addTag(StandardRoles.FORM);  // <== Important
//        tagPointer.getTagReference().getReferencedElement().setAccessibleAttribute(
//            PdfName.T, new PdfString("Digital Signature"));
//
//        sigField.getWidgets().get(0).setStructParentIndex(0);
//        pdf.getStructTreeRoot().getParentTree().addPageTags(pdf.getPage(1), 0, tagPointer.getTagReference());
//
//        doc.close();
//        System.out.println("PDF/UA with signature field created: " + dest);
    }
}
