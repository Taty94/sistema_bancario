package com.pichincha.dm.crms.banking.infrastructure.output.adapter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPCell;
import java.awt.Color;
import com.pichincha.dm.crms.banking.domain.models.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class ReportePdfService {
    public ReportePDFRespuesta generarRespuestaPdf(ReporteEstadoCuenta reporte) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            agregarCabecera(document, reporte);
            agregarResumenGeneral(document, reporte);
            agregarCuentas(document, reporte);
            document.close();
            String base64Pdf = Base64.getEncoder().encodeToString(baos.toByteArray());
            ReportePDF pdf = ReportePDF.builder()
                    .contentType("application/pdf")
                    .filename("reporte-estado-cuenta.pdf")
                    .data(base64Pdf)
                    .build();
            return ReportePDFRespuesta.builder()
                    .data(pdf)
                    .message("Reporte PDF generado exitosamente")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }
    }

    private void agregarCabecera(Document document, ReporteEstadoCuenta reporte) throws DocumentException {
        document.add(new Paragraph("Reporte de Estado de Cuenta"));
        document.add(new Paragraph("Cliente: " + reporte.getCliente().getNombre()));
        document.add(new Paragraph("Periodo: " + reporte.getFechaInicio() + " a " + reporte.getFechaFin()));
        document.add(new Paragraph(" "));
    }

    private void agregarResumenGeneral(Document document, ReporteEstadoCuenta reporte) throws DocumentException {
        document.add(new Paragraph("Total Créditos: " + reporte.getResumenGeneral().getTotalCreditos()));
        document.add(new Paragraph("Total Débitos: " + reporte.getResumenGeneral().getTotalDebitos()));
        document.add(new Paragraph("Saldo Final: " + reporte.getResumenGeneral().getSaldoFinal()));
        document.add(new Paragraph(" "));
    }

    private void agregarCuentas(Document document, ReporteEstadoCuenta reporte) throws DocumentException {
        for (ReporteEstadoCuentaCuentas cuenta : reporte.getCuentas()) {
            document.add(new Paragraph("Cuenta: " + cuenta.getNumeroCuenta() + " | Tipo: " + cuenta.getTipoCuenta()));
            document.add(new Paragraph("Saldo Inicial: " + cuenta.getResumenCuenta().getSaldoInicial() + ", Movimientos: " + cuenta.getResumenCuenta().getTotalMovimientos() + ", Saldo Final: " + cuenta.getResumenCuenta().getSaldoFinal()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(8); // 8 columnas: Fecha, Cliente, Numero Cuenta, Tipo, Saldo Inicial, Tipo Movimiento, Movimiento, Saldo Disponible
            table.setWidthPercentage(100);

            // Colores
            Color azulMarino = new Color(10, 34, 64);
            Color amarilloSuave = new Color(255, 245, 200);
            Color verde = new Color(0, 128, 0);
            Color rojo = new Color(200, 0, 0);

            // Encabezados con azul marino y texto blanco
            String[] headers = {"Fecha", "Cliente", "Numero Cuenta", "Tipo", "Saldo Inicial", "Tipo Movimiento", "Movimiento", "Saldo Disponible"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Paragraph(h));
                cell.setBackgroundColor(azulMarino);
                cell.setPhrase(new Paragraph(h));
                cell.getPhrase().getFont().setColor(Color.WHITE);
                table.addCell(cell);
            }

            boolean alterna = false;
            for (Movimiento mov : cuenta.getMovimientos()) {
                Color bg = alterna ? amarilloSuave : Color.WHITE;
                alterna = !alterna;

                // Fecha
                PdfPCell cellFecha = new PdfPCell(new Paragraph(mov.getFecha() != null ? mov.getFecha().toString() : ""));
                cellFecha.setBackgroundColor(bg);
                table.addCell(cellFecha);

                // Cliente
                PdfPCell cellCliente = new PdfPCell(new Paragraph(reporte.getCliente().getNombre() != null ? reporte.getCliente().getNombre() : ""));
                cellCliente.setBackgroundColor(bg);
                table.addCell(cellCliente);

                // Numero Cuenta
                PdfPCell cellNumCta = new PdfPCell(new Paragraph(cuenta.getNumeroCuenta() != null ? cuenta.getNumeroCuenta() : ""));
                cellNumCta.setBackgroundColor(bg);
                table.addCell(cellNumCta);

                // Tipo
                PdfPCell cellTipo = new PdfPCell(new Paragraph(cuenta.getTipoCuenta() != null ? cuenta.getTipoCuenta() : ""));
                cellTipo.setBackgroundColor(bg);
                table.addCell(cellTipo);

                // Saldo Inicial
                PdfPCell cellSaldoIni = new PdfPCell(new Paragraph(cuenta.getResumenCuenta().getSaldoInicial() != null ? cuenta.getResumenCuenta().getSaldoInicial().toString() : ""));
                cellSaldoIni.setBackgroundColor(bg);
                table.addCell(cellSaldoIni);

                // Tipo Movimiento
                String tipoMov = mov.getTipoMovimiento() != null ? mov.getTipoMovimiento() : "";
                PdfPCell cellTipoMov = new PdfPCell(new Paragraph(tipoMov));
                cellTipoMov.setBackgroundColor(bg);
                table.addCell(cellTipoMov);

                // Movimiento (valor con color y signo)
                String valorStr = "";
                Color colorValor = Color.BLACK;
                if (mov.getValor() != null) {
                    if ("Crédito".equalsIgnoreCase(tipoMov)) {
                        valorStr = "+" + mov.getValor().toString();
                        colorValor = verde;
                    } else if ("Débito".equalsIgnoreCase(tipoMov)) {
                        valorStr = "-" + mov.getValor().toString();
                        colorValor = rojo;
                    } else {
                        valorStr = mov.getValor().toString();
                    }
                }
                Paragraph valorPar = new Paragraph(valorStr);
                valorPar.getFont().setColor(colorValor);
                PdfPCell cellValor = new PdfPCell(valorPar);
                cellValor.setBackgroundColor(bg);
                table.addCell(cellValor);

                // Saldo Disponible
                PdfPCell cellSaldoDisp = new PdfPCell(new Paragraph(mov.getSaldoPosterior() != null ? mov.getSaldoPosterior().toString() : ""));
                cellSaldoDisp.setBackgroundColor(bg);
                table.addCell(cellSaldoDisp);
            }

            document.add(table);
            document.add(new Paragraph(" "));
        }
    }
}
