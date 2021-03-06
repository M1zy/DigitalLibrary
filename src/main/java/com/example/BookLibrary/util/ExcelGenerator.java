package com.example.BookLibrary.util;

import com.example.BookLibrary.domains.*;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ExcelGenerator<T extends Essence>{
    protected abstract ByteArrayInputStream toExcel(T essence);

    public abstract Report report(T essence) throws IOException;

    protected static final List<String> bookHeader = Arrays.asList("BookID", "Name", "Authors", "Year", "Description");

    protected static final List<String> libraryHeader = Arrays.asList("LibraryID", "Name", "Address");

    protected static final List<String> rentHeader = Arrays.asList("UserID", "Name", "Email","RegistrationIds");

    protected static final List<String> userHeader = Arrays.asList("UserID", "Name", "Email");

    protected static void fillRow(Book book, Row row){
        row.createCell(0).setCellValue(book.getId());
        row.createCell(1).setCellValue(book.getName());
        row.createCell(2).setCellValue(book.getAuthorsToString());
        row.createCell(3).setCellValue(book.getYear());
        row.createCell(4).setCellValue(book.getDescription());
    }

    protected static void fillRow(Library library, Row row){
        row.createCell(0).setCellValue(library.getId());
        row.createCell(1).setCellValue(library.getName());
        row.createCell(2).setCellValue(library.getAddress());
    }

    protected static void fillRow(Cart cart, Row row){
        row.createCell(0).setCellValue(cart.getUser().getId());
        row.createCell(1).setCellValue(cart.getUser().getName());
        row.createCell(2).setCellValue(cart.getUser().getEmail());
        row.createCell(3).setCellValue(cart.getCartRegistrations().stream().
                map(x -> x.getId()).collect(Collectors.toSet()).toString());
    }

    protected static void fillRow(User user,Row row){
        row.createCell(0).setCellValue(user.getId());
        row.createCell(1).setCellValue(user.getName());
        row.createCell(2).setCellValue(user.getEmail());
    }

    static Integer headerToExcel(Workbook workbook, List<String> header,
                                 Sheet sheet, Integer step){
        Row headerRow = sheet.createRow(step++);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLUE.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        for (int col = 0; col < header.size(); col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(header.get(col));
            cell.setCellStyle(headerCellStyle);
        }
        return step;
    }

    static Integer booksToExcel(Workbook workbook, Sheet sheet,
                                Set<Book> bookSet, Integer step){
        step=headerToExcel(workbook,bookHeader,sheet,step);
        for (Book book : bookSet) {
            Row row = sheet.createRow(step++);
            fillRow(book,row);
        }
        sheet.createRow(step++);
        return step;
    }

    static Integer librariesToExcel(Workbook workbook, Sheet sheet,
                                    Set<Library> librarySet, Integer step){
        step=headerToExcel(workbook,libraryHeader,sheet,step);
        for(Library library : librarySet ){
            Row row = sheet.createRow(step++);
            fillRow(library,row);
        }
        sheet.createRow(step++);
        return step;
    }

    static Integer rentsToExcel(Workbook workbook, Sheet sheet, Set<Cart> cartSet, Integer step){
        step=headerToExcel(workbook,rentHeader,sheet,step);
        for (Cart cart : cartSet) {
            Row row = sheet.createRow(step++);
            fillRow(cart,row);
        }
        sheet.createRow(step++);
        return step;
    }

    public File byteArrayInputStreamToFile(ByteArrayInputStream byteArrayInputStream,
                                           String filename) throws IOException {
        File report = new File(filename+".xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(report);
        IOUtils.copy(byteArrayInputStream, fileOutputStream);
        fileOutputStream.close();
        byteArrayInputStream.close();
        return report;
    }

    public static String dateFormat(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_'at'_HH.mm.ss");
        return dateFormat.format(date);
    }
}
