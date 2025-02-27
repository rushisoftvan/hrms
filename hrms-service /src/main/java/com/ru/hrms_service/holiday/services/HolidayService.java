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
import com.ru.hrms_service.holiday.models.dto.HolidayRowDTO;
import com.ru.hrms_service.holiday.models.request.FetchHolidaysReq;
import com.ru.hrms_service.holiday.models.response.HolidayResponse;
import com.ru.hrms_service.holiday.repositories.*;
import com.ru.hrms_service.holiday.specification.HolidaySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
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


        MasterBatchJobEntity masterBatchJob =
                this.masterBachJobRepo.findByJobCodeAndDeleteFlagFalse(HOLODAYBATCHCODE).orElseThrow(() -> new RuntimeException("Job code not matched"));

        BatchJobStatusEntity batchJobStatusEntity = new BatchJobStatusEntity();
        batchJobStatusEntity.setJobCode(masterBatchJob.getJobCode());
        batchJobStatusEntity.setStatus(BatchJobStatusEnum.IN_PROGRESS);
        batchJobStatusEntity.setCreatedBy(new UserEntity(1L));
        batchJobStatusEntity.setUpdatedBy(new UserEntity(1L));
        batchJobStatusEntity.setRemarks("inp");

        BatchJobStatusEntity savedBacthJob = batchJobStatusRepo.save(batchJobStatusEntity);


        try {
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
                importHolidayTempEntity.setRemarks("import temp");
                importHolidayTempEntities.add(importHolidayTempEntity);
                rowNumber++;
            }
            importHolidayTempRepo.saveAll(importHolidayTempEntities);

            List<ValidationRuleEntity>  validationRuleEntities = validationRuleRepo.findByEntityNameAndDeleteFlagFalse(
                    "ImportHolidayTempEntity".trim());


            List<ImportHolidayTempEntity> importHolidaysInTempBasedOnBatchId =
                    this.importHolidayTempRepo.findByBatchId(savedBacthJob.getId());

//            for(ValidationRuleEntity validationRule : validationRuleEntities ){
//
//                 for(ImportHolidayTempEntity holidayTempTable : importHolidaysInTempBasedOnBatchId ){
//                     StringBuilder remarks = new StringBuilder("");
//                     try {
//                         Field declaredField = holidayTempTable.getClass().getDeclaredField(validationRule.getColumnName());
//                         declaredField.setAccessible(true);
//                         Object value = declaredField.get(holidayTempTable);
//
//                         if(validationRule.isRequired()){
//
//                             if(Objects.isNull(value)){
//                                 remarks.append(" "). append("row no : " ).append(validationRule.getColumnName()).append("is required");
//                                 holidayTempTable.setStatus(ImportHolidayStatusEnum.INVALID);
//                             }
//
//                         }
//
//                         holidayTempTable.setRemarks(remarks.toString());
//
//                     }
//
//                     catch (NoSuchFieldException e) {
//                         throw new RuntimeException(e);
//                     } catch (IllegalAccessException e) {
//                         throw new RuntimeException(e);
//                     }
//                 }
//
//
//            }


            for(ImportHolidayTempEntity holidayTemp  : importHolidaysInTempBasedOnBatchId){
                StringBuilder remarks = new StringBuilder("");
                for(ValidationRuleEntity validationRule  : validationRuleEntities ){

                    try {
                        Field declaredField = holidayTemp.getClass().getDeclaredField(validationRule.getColumnName());
                        declaredField.setAccessible(true);
                        Object value = declaredField.get(holidayTemp);

                        if(validationRule.isRequired()){

                             if(Objects.isNull(value) || value==""){
                                 remarks.append(" "). append("row no : " ).append(holidayTemp.getRowNumber()).append(
                                         " ").append(validationRule.getColumnName()).append("is required");
                                 holidayTemp.setStatus(ImportHolidayStatusEnum.INVALID);
                             }
                         }
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }


                }
                holidayTemp.setRemarks(remarks.toString());
            }

            List<ImportHolidayTempEntity>  updatedHolidayTempAfterValidation =
                    importHolidayTempRepo.saveAll(importHolidaysInTempBasedOnBatchId);

            boolean isDataInValid = updatedHolidayTempAfterValidation.stream().anyMatch((data) -> data.getStatus() == ImportHolidayStatusEnum.INVALID);

            if(isDataInValid){

                Optional<BatchJobStatusEntity> batchStatusEntity = this.batchJobStatusRepo.findByIdAndDeleteFlagFalse(savedBacthJob.getId());
                BatchJobStatusEntity batch = batchStatusEntity.map(entity -> {
                    entity.setStatus(BatchJobStatusEnum.ERROR);
                    return entity;
                }).orElseThrow(() -> new RuntimeException("batch is not found"));
                this.batchJobStatusRepo.save(batch);

               }
            else
            {


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
}






