package com.lg.generator;

import com.lg.model.LgIssue;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.lg.utils.DateUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvGenerator {

    private List<LgIssue> issues;

    public CsvGenerator(List<LgIssue> issues) {
        this.issues = issues;
    }

    public void generateCsvFile(HttpServletResponse response) throws IOException {

        CSVPrinter printer = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT);
        for (LgIssue lgIssue : issues) {
            printer.printRecord(lgIssue.getId(), lgIssue.getLgNumber(), lgIssue.getLgType(),
                    DateUtil.getDateTimeString(lgIssue.getIssueDate()), lgIssue.getAmount() + " " + lgIssue.getCurrency(),
                    lgIssue.getEntityName(), lgIssue.getAppIban(), lgIssue.getStatus());
        }
    }
}
