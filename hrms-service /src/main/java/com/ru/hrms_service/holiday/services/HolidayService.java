package com.ru.hrms_service.holiday.services;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.ru.hrms_service.common.entities.UserEntity;
import com.ru.hrms_service.common.models.response.ApiResponse;
import com.ru.hrms_service.holiday.entities.*;
import com.ru.hrms_service.holiday.enums.BatchJobStatusEnum;
import com.ru.hrms_service.holiday.enums.ImportHolidayStatusEnum;
import com.ru.hrms_service.holiday.exception.BachJobStatusException;
import com.ru.hrms_service.holiday.models.dto.HolidayRowDTO;
import com.ru.hrms_service.holiday.models.request.FetchHolidaysReq;
import com.ru.hrms_service.holiday.models.response.HolidayResponse;
import com.ru.hrms_service.holiday.repositories.*;
import com.ru.hrms_service.holiday.specification.HolidaySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HolidayService {

    public static final String HOLODAYBATCHCODE = "HI23";

    private final HolidayRepo holidayRepo;
    private final MasterBachJobRepo masterBachJobRepo;
    private final BatchJobStatusRepo batchJobStatusRepo;
    private final ImportHolidayTempRepo importHolidayTempRepo;
    private final ValidationRuleRepo validationRuleRepo;


    public ApiResponse fetchHolidays(FetchHolidaysReq fetchHolidaysReq) {

        var holidaysSpec = HolidaySpecification.fetchHolidays(fetchHolidaysReq);
        List<HolidayResponse> listOfHolidayResponse = prepareHolidayResponse(holidaysSpec);
        log.info("holiday response : {}", listOfHolidayResponse.size());
        return ApiResponse.success(listOfHolidayResponse, "fetched successfully");
    }

    private List<HolidayResponse> prepareHolidayResponse(Specification<HolidayEntity> holidaysSpec) {
        List<HolidayEntity> holidays = holidayRepo.findAll(holidaysSpec);
        log.info("holiday size : {}", holidays.size());
        return holidays.stream()
                .map(HolidayResponse::prepareHolidayResponse)
                .toList();
    }

    public void importHoliday(MultipartFile file) {


        BatchJobStatusEntity savedBacthJob = initializeBatchJob();




        try {
            List<HolidayRowDTO> dtos = parseCsvFile(file);

            List<ImportHolidayTempEntity> importHolidayTempEntities = convertToTempEntities(dtos, savedBacthJob);
            importHolidayTempRepo.saveAll(importHolidayTempEntities);

            validateAndProcessHolidays(savedBacthJob);


        } catch (Exception e) {

            handleImportError(savedBacthJob, e);
            throw new RuntimeException("Failed to import holidays", e);
        }
    }

    private List<ImportHolidayTempEntity> convertToTempEntities(List<HolidayRowDTO> dtos, BatchJobStatusEntity savedBacthJob) {
        List<ImportHolidayTempEntity> importHolidayTempEntities = new ArrayList<>();
        int rowNumber = 1;
        for (HolidayRowDTO data : dtos) {
            ImportHolidayTempEntity importHolidayTempEntity = new ImportHolidayTempEntity();
            importHolidayTempEntity.setHolidayName(data.getHolidayName());
            importHolidayTempEntity.setOptionl(Boolean.getBoolean(data.getIsOptional()));
            importHolidayTempEntity.setHolidayDate(dateConverter(data.getHolidayDate()));
            importHolidayTempEntity.setBatchJobStatusEntity(savedBacthJob);
            importHolidayTempEntity.setRowNumber(rowNumber);
            importHolidayTempEntity.setBatchJobStatusEntity(savedBacthJob);
            importHolidayTempEntity.setStatus(ImportHolidayStatusEnum.NEW);
            importHolidayTempEntity.setCreatedBy(new UserEntity(1L));
            importHolidayTempEntity.setUpdatedBy(new UserEntity(1L));
            importHolidayTempEntity.setRemarks("");
            importHolidayTempEntities.add(importHolidayTempEntity);
            rowNumber++;
        }
        return importHolidayTempEntities;
    }

    private static List<HolidayRowDTO> parseCsvFile(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytes);

        // Create a CSVReader from the InputStream
        CSVReader reader = new CSVReader(new InputStreamReader(byteArrayInputStream));


        ColumnPositionMappingStrategy<HolidayRowDTO> beanStrategy = new ColumnPositionMappingStrategy<>();
        beanStrategy.setType(HolidayRowDTO.class);

        // Mapping the columns in the CSV to the Employee class fields
        beanStrategy.setColumnMapping(new String[]{"holidayName", "holidayDate", "isOptional"});

        // Creating CsvToBean and parsing the CSV into Employee objects
        CsvToBean<HolidayRowDTO> csvToBean = new CsvToBeanBuilder<HolidayRowDTO>(reader)
                .withMappingStrategy(beanStrategy)
                .withSkipLines(1)
                .build();

        List<HolidayRowDTO> dtos = csvToBean.parse();
        return dtos;
    }

    private BatchJobStatusEntity initializeBatchJob() {
        MasterBatchJobEntity masterBatchJob =
                this.masterBachJobRepo.findByJobCodeAndDeleteFlagFalse(HOLODAYBATCHCODE).orElseThrow(() -> new RuntimeException("Job code not matched"));

        BatchJobStatusEntity batchJobStatusEntity = new BatchJobStatusEntity();
        batchJobStatusEntity.setJobCode(masterBatchJob.getJobCode());
        batchJobStatusEntity.setStatus(BatchJobStatusEnum.IN_PROGRESS);
        batchJobStatusEntity.setCreatedBy(new UserEntity(1L));
        batchJobStatusEntity.setUpdatedBy(new UserEntity(1L));
        batchJobStatusEntity.setRemarks("inp");

        BatchJobStatusEntity savedBacthJob = batchJobStatusRepo.save(batchJobStatusEntity);
        return savedBacthJob;
    }

    private LocalDate dateConverter(String date) {

        if(date.isEmpty()){
            return  null;
        }

        if("holidatDate".equals(date)){
            return LocalDate.now();
        }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            return LocalDate.parse(date, formatter);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processHolidays(Long batchId) {
        log.info("Processing holidays for batch ID: {}", batchId);
        importHolidayTempRepo.compareAndInsertHolidays(batchId);
        log.info("Holidays processed successfully for batch ID: {}", batchId);
    }

    private void validateAndProcessHolidays(BatchJobStatusEntity batchJob) {
        List<ValidationRuleEntity> rules = validationRuleRepo.findByEntityNameAndDeleteFlagFalse("ImportHolidayTempEntity");
        List<ImportHolidayTempEntity> holidays = importHolidayTempRepo.findByBatchId(batchJob.getId());
        List<ImportHolidayTempEntity> invalidHolidays = new ArrayList<>();

        for (ImportHolidayTempEntity holiday : holidays) {
            List<String> validationErrors =

                    validateHoliday(holiday, rules);
            if (!validationErrors.isEmpty()) {
                holiday.setRemarks(formatRemarks(holiday.getRowNumber(), validationErrors));
                holiday.setStatus(ImportHolidayStatusEnum.INVALID);
                invalidHolidays.add(holiday);
            }
        }

        processValidationResult(batchJob, invalidHolidays);
    }
    private String formatRemarks(int rowNumber, List<String> errors) {
        return String.format("Row No: %d: %s", rowNumber, String.join(", ", errors));
    }

    private List<String> validateHoliday(ImportHolidayTempEntity holiday, List<ValidationRuleEntity> rules) {
        List<String> errors = new ArrayList<>();

        for (ValidationRuleEntity rule : rules) {
            try {
                Field field = holiday.getClass().getDeclaredField(rule.getColumnName());
                field.setAccessible(true);
                Object value = field.get(holiday);

                if (rule.isRequired() && (Objects.isNull(value) || value.toString().isEmpty())) {
                    errors.add(String.format("Column '%s' is required", rule.getColumnName()));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("Validation error for field {}: {}", rule.getColumnName(), e.getMessage());
                throw new RuntimeException("Validation processing error", e);
            }
        }
        return errors;
    }

    public void processValidationResult(BatchJobStatusEntity batchJob, List<ImportHolidayTempEntity> invalidHolidays) {
        if (!invalidHolidays.isEmpty()) {
            importHolidayTempRepo.saveAll(invalidHolidays);
            updateBatchJobStatus(batchJob, BatchJobStatusEnum.ERROR);
        } else {
            processHolidays(batchJob.getId());
            updateBatchJobStatus(batchJob, BatchJobStatusEnum.COMPLETED);
        }
    }

    public void updateBatchJobStatus(BatchJobStatusEntity batchJob, BatchJobStatusEnum status) {
        batchJob.setStatus(status);
        batchJobStatusRepo.save(batchJob);
    }

    private void handleImportError(BatchJobStatusEntity batchJob, Exception e) {
        log.error("Error during holiday import: {}", e.getMessage());
        updateBatchJobStatus(batchJob, BatchJobStatusEnum.ERROR);
        batchJob.setRemarks("Import failed: " + e.getMessage());
        batchJobStatusRepo.save(batchJob);
    }

    public void getHolidayImportStatus(Long batchId) {
      BatchJobStatusEntity batchJobStatusEntity =
                this.batchJobStatusRepo.findByIdAndDeleteFlagFalse(batchId).orElseThrow(()->new BachJobStatusException("Batch job not found for id: " + batchId));



             if(batchJobStatusEntity.getStatus()==BatchJobStatusEnum.ERROR){

                 List<String> remarks = this.importHolidayTempRepo.findRemarksByStatusAndBatchIdNative(BatchJobStatusEnum.ERROR.value(),
                         batchJobStatusEntity.getId());



             }
    }


}






