package com.stockmarket.sproject.application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aspose.cells.FileFormatType;
import com.aspose.cells.ImportTableOptions;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.stockmarket.sproject.application.Service.TransactionService;
import com.stockmarket.sproject.application.dto.TransactionHistoryElement;

@Service
public class ExcelConverter {

    static final String FILE_EXTENTION = ".xlsx";
    static final String DIRECTORY_NAME = "excel_history";

    TransactionService transactionService;

    ExcelConverter(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    private String getDirectoryPath() {

        String envRootDir = System.getProperty("user.dir");
        Path rootDir = Paths.get(".").normalize().toAbsolutePath();
        if (!rootDir.startsWith(envRootDir)) {
            throw new RuntimeException("Root dir not found in user directory.");
        }

        StringBuilder path = new StringBuilder(rootDir.toString());

        path.append("\\");
        path.append(DIRECTORY_NAME);

        return path.toString();
    }

    private String getFilePath(String fileName){

        StringBuilder directoryPath = new StringBuilder(getDirectoryPath());

        directoryPath.append("\\");
        directoryPath.append(fileName);
        directoryPath.append(FILE_EXTENTION);

        return directoryPath.toString();
    }

    private void TryGenerateDirectory(String directoryPath) throws Exception{

        File file = new File(directoryPath.toString());

        if (!file.mkdir()) {
            throw new Exception("Unable to create directory");
        }

    }

    public ByteArrayInputStream get(String username) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Workbook history = new Workbook();

        history.getWorksheets().clear();

        Worksheet sheet = history.getWorksheets().add("Sheet1");

        List<TransactionHistoryElement> list = transactionService.StockHistory(username).getElements();

        ImportTableOptions opts = new ImportTableOptions();

        opts.setConvertNumericData(true);
        opts.setDateFormat("yyyy-MM-dd HH:mm:ss");

        sheet.getCells().importCustomObjects(list, 0, 0, opts);

        sheet.autoFitColumns();

        String directoryPath = getDirectoryPath();

        TryGenerateDirectory(directoryPath);

        String filePath = getFilePath(username);
        
        history.save(filePath);
        history.save(out, FileFormatType.CSV);
        return new ByteArrayInputStream(out.toByteArray());
    }

}
