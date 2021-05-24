package com.example.BookLibrary.service;

import com.example.BookLibrary.domains.Library;
import com.example.BookLibrary.domains.Report;
import com.example.BookLibrary.repository.ReportRepository;
import com.example.BookLibrary.util.LibraryGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    LibraryService libraryService;

    public void save(Report report){
        reportRepository.save(report);
    }

    public List<Report> listAll() {
        return (List<Report>) reportRepository.findAll();
    }

    public Report get(Long id) {
        return reportRepository.findById(id).get();
    }

    @Scheduled(cron = "${cron.expression}")
    public void librariesReport () {
        LibraryGenerator libraryGenerator = new LibraryGenerator();
        List<Library> libraries=libraryService.listAll();
        save(libraryGenerator.report(libraries));
    }
}
