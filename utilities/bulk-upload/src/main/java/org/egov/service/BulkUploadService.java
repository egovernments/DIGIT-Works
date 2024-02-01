package org.egov.service;



// ExcelToJsonConverterService.java
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import digit.models.coremodels.IdGenerationResponse;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.config.Configuration;
import org.egov.repository.IdGenRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.BulkUploadUtil;
import org.egov.web.models.Mdms;
import org.egov.web.models.MdmsRequest;
import org.egov.web.models.MdmsResponseV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BulkUploadService {


    @Autowired
    private IdGenRepository idGenRepository;

    @Autowired
    private Configuration serviceConfiguration;
    @Autowired
    private BulkUploadUtil bulkUploadUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ResourceLoader resourceLoader;


    // Function to check if a row is empty
    private boolean isRowNotEmpty(Row row) {
        Iterator<Cell> cellIterator = row.iterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getCellType() != CellType.BLANK) {
                // If any cell in the row is not blank
                return true;
            }
        }
        // If all cells in the row are blank
        return false;
    }



    public List<Map<String, Object>> bulkUpload(MultipartFile file, String schemaCode, String tenantId) throws IOException {
        List<Map<String, Object>> jsonData = new ArrayList<>();

        Path tempDir = Files.createTempDirectory("");

        File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
        file.transferTo(tempFile);

        try (FileInputStream fis = new FileInputStream(tempFile);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Assume the first row contains headers
            Row headerRow = rowIterator.next();
            List<String> headers = getHeaders(headerRow);

            /*
                while (rowIterator.hasNext()) {

                    Row row = rowIterator.next();
                    Map<String, Object> rowData = processRow(row, headers);

                    jsonData.add(rowData);
                }
            */

            //Updated to not iterate for empty rows
            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();

                // Checking if the row is not empty
                if (isRowNotEmpty(row)) {
                    Map<String, Object> rowData = processRow(row, headers);
                    jsonData.add(rowData);
                } else {
                    // If the row is empty, break
                    break;
                }
            }

        }
        MdmsRequest mdmsRequest;

        if(schemaCode.equals("WORKS-SOR.SOR")){
            Resource classPathResource = resourceLoader.getResource("classpath:Mdms.json");
            mdmsRequest=objectMapper.readValue(classPathResource.getInputStream(),MdmsRequest.class);
            mdmsRequest.getMdms().setTenantId(tenantId);
            return createSOR( mdmsRequest,jsonData);
        }else{
            Resource classPathResource = resourceLoader.getResource("classpath:RatesMdmsRequest.json");
            mdmsRequest=objectMapper.readValue(classPathResource.getInputStream(),MdmsRequest.class);
            mdmsRequest.getMdms().setTenantId(tenantId);
            return createSORRate(mdmsRequest,jsonData);
        }

       // return jsonData
    }



    private Map<String, Object> processRow(Row row, List<String> headers) {
        Map<String, Object> rowData = new HashMap<>();

        Sheet sheet = row.getSheet();
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = row.getCell(i);
            String header = headers.get(i);

            if (header.equalsIgnoreCase("Level")) {
                int nestingLevel = (int) cell.getNumericCellValue();
                // Process nested structure recursively
                List<Map<String, Object>> nestedData = new ArrayList<>();
                for (int j = 0; j < nestingLevel; j++) {
                        Row nestedRow = sheet.getRow(row.getRowNum() + j + 1); // Adjust row index
                    Map<String, Object> nestedRowData = processRow(nestedRow, headers);
                    nestedData.add(nestedRowData);
                }
                rowData.put(header, nestedData);
            } else {
                if(cell!=null){
                    rowData.put(header, getCellValue(cell));
                }

            }
        }

        return rowData;
    }

    private List<String> getHeaders(Row headerRow) {
        List<String> headers = new ArrayList<>();

        for (Cell cell : headerRow) {
            if(cell.getStringCellValue().equals("LA.1")||
                    cell.getStringCellValue().equals("MA.2")||
                    cell.getStringCellValue().equals("MHA.3")||
                    cell.getStringCellValue().equals("ADC.4")||
                    cell.getStringCellValue().equals("DMF.5")||
                    cell.getStringCellValue().equals("EMF.6")||
                    cell.getStringCellValue().equals("LC.7")||
                    cell.getStringCellValue().equals("RA.8")||
                    cell.getStringCellValue().equals("CA.9")){
                headers.add(cell.getStringCellValue());
            }else{
                headers.add(convertToCamelCase(cell.getStringCellValue()));
            }

        }

        return headers;
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case BLANK:
                return "";
            default:
                return null;
        }
    }

    private static String convertToCamelCase(String inputString) {
        // Split the input string by spaces
        String[] words = inputString.split(" ");

        // If there's only one word or the input is empty, return the input as is
        if (words.length == 0) {
            return inputString;
        }

        // Convert the first word to lowercase
        StringBuilder camelCaseString = new StringBuilder(words[0].toLowerCase());

        // Convert the first letter of each subsequent word to uppercase
        for (int i = 1; i < words.length; i++) {
            camelCaseString.append(words[i].substring(0, 1).toUpperCase())
                    .append(words[i].substring(1).toLowerCase());
        }

        return camelCaseString.toString();
    }

    public List<Map<String, Object>> createSOR(MdmsRequest mdmsRequest, List<Map<String, Object>> jsonData)  {
        List<Map<String,Object>> mapList=new ArrayList<>();

        String tenantid=mdmsRequest.getMdms().getTenantId();
        ObjectMapper mapper = new ObjectMapper();
        jsonData.forEach(entry ->{
            entry.remove("sr.No.");
            IdGenerationResponse idGenerationResponse=idGenRepository.getId(mdmsRequest.getRequestInfo(),
                    tenantid,serviceConfiguration.getIdgenSorName(),serviceConfiguration.getIdgenSorFormat(),1);
            String sorId=null;
            Map<String, Object> map = new HashMap<>();
            if(!idGenerationResponse.getIdResponses().isEmpty()){
                sorId =idGenerationResponse.getIdResponses().get(0).getId();
                entry.put("id", sorId);

            }else{
                throw new CustomException("SOR_ID_GENERATION_FAILURE", "Id generation service response is empty");
            }

            JsonNode jsonNode = mapper.convertValue(entry, JsonNode.class);
            mdmsRequest.getMdms().setData(jsonNode);


           Object response= bulkUploadUtil.create(mdmsRequest,"WORKS-SOR.SOR");
             map.put(sorId,response);
            mapList.add(map);
        });

        return mapList;
    }

    private List<Map<String, Object>> createSORRate(MdmsRequest mdmsRequest, List<Map<String, Object>> jsonData) {

        List<Map<String,Object>> list=new ArrayList<>();

        String tenantid=mdmsRequest.getMdms().getTenantId();
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String,Object>> mapList=new ArrayList<>();
            jsonData.forEach(entry ->{

                ObjectNode mainNode = mapper.createObjectNode();
                ArrayNode amountDetailsArray = mapper.createArrayNode();
            entry.entrySet().stream().forEach(e->{
                if(!Objects.equals(e.getKey(), "sorId") &&
                        !Objects.equals(e.getKey(), "total")&&
                        !Objects.equals(e.getKey(), "validFrom")&&
                        !Objects.equals(e.getKey(), "validTo")){
                    if(e.getValue()!=null && e.getValue() instanceof Double){
                        ObjectNode data = mapper.createObjectNode();
                        data.put("type","fixed");
                        data.put("heads",e.getKey());
                        data.put("amount", (Double) e.getValue());

                        amountDetailsArray.add(data);
                    }
                }
                if(Objects.equals(e.getKey(), "sorId") && e.getValue()!=null){
                    mainNode.put("sorId", (String) e.getValue());
                }
                if(Objects.equals(e.getKey(), "total")&& e.getValue()!=null){
                    mainNode.put("rate", (Double) e.getValue());
                }
                if(Objects.equals(e.getKey(), "validFrom") && e.getValue()!=null){
                    mainNode.put("validFrom",String.valueOf(convertDateToEpochDateTime((String) e.getValue())));
                }
                if(Objects.equals(e.getKey(), "validTo") && e.getValue()!=null){
                    mainNode.put("validTo",String.valueOf(convertDateToEpochDateTime((String) e.getValue())));
                }
            });
                mainNode.set("amountDetails",amountDetailsArray );
                mainNode.put("type","lumpsum");

                if(!mainNode.get("amountDetails").isEmpty()){
                    mdmsRequest.getMdms().setData(mainNode);
                    System.out.println(mdmsRequest.toString());



                    Object response= bulkUploadUtil.create(mdmsRequest,"WORKS-SOR.Rates");


                    // Adding response to the list
                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("sorId", mainNode.get("sorId").asText());
                    responseMap.put("sorRateDetails", mainNode);
                    list.add(responseMap);

                }




                //Material Analysis=MA.1
                //LH.2 = Labour Head
                //MH.2 = Machinery Head
                //Conveyance.4 = Conveyance

        });

        return list;


    }

    private long convertDateToEpochDateTime(String dateString){
        // Define the date format pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String dateWithTime=dateString+" 00:00:00";

        // Parse the input string to obtain a LocalDate object
        LocalDateTime localDateTime = LocalDateTime.parse(dateWithTime, formatter);

        // Convert LocalDate to epoch timestamp (seconds)
        return localDateTime.atZone(ZoneId.of("Asia/Kolkata")).toInstant().toEpochMilli();
    }








}


