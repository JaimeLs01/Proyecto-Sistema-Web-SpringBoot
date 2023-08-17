/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proy.v1.entidad;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author three
 */
public class ListarVentasPdf {
    private List<VentaDetalle> listaDetalleVenta;

    public ListarVentasPdf(List<VentaDetalle> listaDetalleVenta) {
        super();
        this.listaDetalleVenta = listaDetalleVenta;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.black);
        celda.setPadding(5);
        celda.setBorderColor(Color.white);
        celda.setBorder(0);
    
        

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("ID_PRO", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("NOMBRE", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("PRECIO", fuente));
        tabla.addCell(celda);
        
        celda.setPhrase(new Phrase("CANTIDAD", fuente));
        tabla.addCell(celda);
        
        celda.setPhrase(new Phrase("TOTAL", fuente));
        tabla.addCell(celda);
        
    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.black);
        celda.setBorder(0);
        for (VentaDetalle detalleventa : listaDetalleVenta) {
            celda.setPhrase(new Phrase("    "+detalleventa.getProducto().getProductoId().toString(),fuente));
            tabla.addCell(celda);
            celda.setPhrase(new Phrase(detalleventa.getProducto().getNombre(),fuente));
            tabla.addCell(celda);
            celda.setPhrase(new Phrase("    "+detalleventa.getProducto().getPrecioUnitario().toString(),fuente));
            tabla.addCell(celda);
            celda.setPhrase(new Phrase("      "+String.valueOf(detalleventa.getCantidad()),fuente));
            tabla.addCell(celda);
            celda.setPhrase(new Phrase("S/."+String.valueOf(detalleventa.getTotal()),fuente));
            tabla.addCell(celda);
        }
    }

    public void exportar(HttpServletResponse response) throws DocumentException, IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.black);
        fuente.setSize(14);

        Paragraph titulo = new Paragraph("Factura", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);
        
        fuente.setColor(Color.BLACK);
        fuente.setSize(12);
        String texto = "RUC:20549828362                                                               Nombre:"+listaDetalleVenta.get(0).getVenta().getUsuario().getNombre()+
                "\nRazon Social:Irma Grande SAC                                           Direccion:"+listaDetalleVenta.get(0).getVenta().getUsuario().getDireccion()
                +"\nCorreo:irma_ventas@gmail.com                                          Correo:"+listaDetalleVenta.get(0).getVenta().getUsuario().getCorreo()
                +"\n                                                                                               Numero de boleta:"+listaDetalleVenta.get(0).getVenta().getNumero()
                +"\n                                                                                               Fecha:"+listaDetalleVenta.get(0).getVenta().getFecha();
        Paragraph parrafo2 = new Paragraph(texto, fuente);
        documento.add(parrafo2);

        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[]{2f, 6f, 2.3f, 2.3f, 2.3f});
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        
        fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        String texto1 ="\nImporte: S/."+listaDetalleVenta.get(0).getVenta().getTotal();
        Paragraph parrafo3 = new Paragraph(texto1, fuente);
        parrafo3.setAlignment(Paragraph.ALIGN_RIGHT);
        documento.add(parrafo3);
        
        documento.close();
    }
}
