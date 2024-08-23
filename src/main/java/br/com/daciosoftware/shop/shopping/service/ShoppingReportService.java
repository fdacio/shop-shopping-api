package br.com.daciosoftware.shop.shopping.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.itextpdf.text.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Font.FontStyle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.daciosoftware.shop.modelos.dto.shopping.ShopDTO;
import br.com.daciosoftware.shop.modelos.dto.shopping.ShopSummaryReportDTO;
import br.com.daciosoftware.shop.shopping.repository.ShoppingReportRepositoryImpl;

@Service
public class ShoppingReportService {

    private static final int SCALE_PERC_LOGO = 20;

    @Autowired
    private ShoppingReportRepositoryImpl shoppingReportRepository;

    public List<ShopDTO> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo) {

        return shoppingReportRepository.getShopByFilters(dataInicio, dataFim, valorMinimo)
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());

    }

    public ByteArrayOutputStream getReportByDate(LocalDate dataInicio, LocalDate dataFim) throws DocumentException, URISyntaxException, IOException {

        ShopSummaryReportDTO shopReportDTO = shoppingReportRepository.getShopByDate(dataInicio, dataFim);

        Document document = new Document();
        document.setMargins(20, 20, 30, 30);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, outputStream);

        document.open();

        addHeaderReport(document, "Resumo de Vendas");

        Font fontPeriodo = new Font(FontFamily.HELVETICA, 14, FontStyle.BOLD.ordinal());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String periodo = String.format("Período: %s à %s", dataInicio.format(dtf), dataFim.format(dtf));
        document.add(new Phrase(periodo, fontPeriodo));

        addRowsReportByDate(document, shopReportDTO);

        document.close();

        return outputStream;
    }

    private void addHeaderReport(Document document, String title) throws URISyntaxException, IOException, DocumentException {

        PdfPTable tableHeader = new PdfPTable(3);
        tableHeader.setWidthPercentage(100);
        float[] widths = {15, 70, 15};
        tableHeader.setWidths(widths);

        PdfPCell pdfPCellImg =  getLogo().isPresent() ? new PdfPCell(getLogo().get()) : new PdfPCell();
        pdfPCellImg.setBorderWidth(0);
        tableHeader.addCell(pdfPCellImg);

        Font fontTitle = new Font(FontFamily.HELVETICA, 18, FontStyle.BOLD.ordinal());
        PdfPCell pdfPCellTitulo = new PdfPCell(new Phrase(title, fontTitle));
        pdfPCellTitulo.setBorderWidth(0);
        pdfPCellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCellTitulo.setVerticalAlignment(Element.ALIGN_MIDDLE);


        tableHeader.addCell(pdfPCellTitulo);
        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBorderWidth(0);
        tableHeader.addCell(pdfPCell);

        document.add(tableHeader);

    }

    private void addRowsReportByDate(Document document, ShopSummaryReportDTO shopReportDTO) throws DocumentException {

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        float[] widths = {40, 60};
        table.setWidths(widths);

        table.addCell("Quantidade de vendas no período:");
        PdfPCell pdfPCellCount = new PdfPCell(new Phrase(String.format("%d", shopReportDTO.getCount())));
        pdfPCellCount.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(pdfPCellCount);

        table.addCell("Total de vendas no período:");
        PdfPCell pdfPCellTotal = new PdfPCell(new Phrase(String.format("R$ %,.2f", shopReportDTO.getTotal())));
        pdfPCellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(pdfPCellTotal);

        table.addCell("Valor médio de vendas no período:");
        PdfPCell pdfPCellMean = new PdfPCell(new Phrase(String.format("R$ %,.2f", shopReportDTO.getMean())));
        pdfPCellMean.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(pdfPCellMean);

        document.add(table);

    }

    private Optional<Image> getLogo() throws URISyntaxException, BadElementException, IOException {
        URL logoResource = ClassLoader.getSystemResource("static/images/logo.png");
        if (logoResource == null) {
            return Optional.empty();
        }
        Path path = Paths.get(logoResource.toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(SCALE_PERC_LOGO);
        return Optional.of(img);
    }
}
