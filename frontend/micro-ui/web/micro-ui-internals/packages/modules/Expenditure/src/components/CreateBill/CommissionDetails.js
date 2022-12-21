import React from 'react'
import { useTranslation } from 'react-i18next'
import { CardSectionHeader, Card} from "@egovernments/digit-ui-react-components";

import ApplicationDetails from "../../../../templates/ApplicationDetails";
const records = [
  {
      "id": "af6d0c8e-dfe7-4f3f-a41b-9a0412f821d4",
      "createdby": null,
      "createdtime": null,
      "dateofdeath": 1667759399000,
      "dateofreport": 1668623399000,
      "dateofdeathepoch": null,
      "excelrowindex": 0,
      "dateofreportepoch": null,
      "dateofissue": null,
      "firstname": "Stacy",
      "gender": 2,
      "genderStr": "Female",
      "hospitalname": "Ajit Hospital",
      "informantsaddress": "ABC",
      "informantsname": "Test",
      "lastname": "Peter",
      "middlename": null,
      "placeofdeath": "India",
      "registrationno": "010",
      "remarks": "None",
      "lastmodifiedby": null,
      "lastmodifiedtime": null,
      "counter": 0,
      "tenantid": "pb.amritsar",
      "hospitalid": "amritsar_1",
      "deathFatherInfo": {
          "id": "ef8d5d7a-393f-4e19-887a-08abfc250d07",
          "aadharno": null,
          "createdby": null,
          "createdtime": null,
          "emailid": null,
          "firstname": "Harry",
          "lastname": "Potter",
          "middlename": null,
          "mobileno": null,
          "lastmodifiedby": null,
          "lastmodifiedtime": null,
          "fullName": "Harry Potter"
      },
      "deathMotherInfo": {
          "id": "ab121b22-0b21-4c2e-8c36-f3fa18fc2ac5",
          "aadharno": null,
          "createdby": null,
          "createdtime": null,
          "emailid": null,
          "firstname": "anna",
          "lastname": "Parker",
          "middlename": null,
          "mobileno": null,
          "lastmodifiedby": null,
          "lastmodifiedtime": null,
          "fullName": "anna Parker"
      },
      "deathPermaddr": {
          "id": "789487c2-f1f3-423e-a14e-5eaa8e42e969",
          "buildingno": "123",
          "city": "Test",
          "country": "India",
          "createdby": null,
          "createdtime": null,
          "district": "Test",
          "houseno": "005",
          "locality": "XYZ",
          "pinno": "123456",
          "state": "Test",
          "streetname": "MG Road",
          "tehsil": "XYZ",
          "lastmodifiedby": null,
          "lastmodifiedtime": null,
          "fullAddress": "005 123 MG Road XYZ XYZ Test Test Test 123456 India"
      },
      "deathPresentaddr": {
          "id": "87aa4209-8f52-47ab-8d6e-f9d2d4635434",
          "buildingno": "123",
          "city": "Test",
          "country": "India",
          "createdby": null,
          "createdtime": null,
          "district": "Test",
          "houseno": "005",
          "locality": "XYZ",
          "pinno": "123456",
          "state": "Test",
          "streetname": "MG Road",
          "tehsil": "XYZ",
          "lastmodifiedby": null,
          "lastmodifiedtime": null,
          "fullAddress": "005 123 MG Road XYZ XYZ Test Test Test 123456 India"
      },
      "deathSpouseInfo": {
          "id": "907afa16-6d13-40ab-995a-ef763f1a8e4f",
          "aadharno": null,
          "createdby": null,
          "createdtime": null,
          "emailid": null,
          "firstname": "Tom",
          "lastname": "Peter",
          "middlename": null,
          "mobileno": null,
          "lastmodifiedby": null,
          "lastmodifiedtime": null,
          "fullName": "Tom Peter"
      },
      "age": 12,
      "eidno": "123",
      "aadharno": "123456789123",
      "nationality": "Indian",
      "religion": null,
      "icdcode": "",
      "embeddedUrl": null,
      "deathcertificateno": null,
      "rejectReason": null,
      "fullName": "Stacy Peter",
      "isLegacyRecord": false
  },
  {
      "id": "003d428a-1f5e-4506-897e-824777a02cac",
      "createdby": null,
      "createdtime": null,
      "dateofdeath": 1667347200000,
      "dateofreport": null,
      "dateofdeathepoch": null,
      "excelrowindex": 0,
      "dateofreportepoch": null,
      "dateofissue": null,
      "firstname": "aaa",
      "gender": 2,
      "genderStr": "Female",
      "hospitalname": "Ajit Hospital",
      "informantsaddress": null,
      "informantsname": null,
      "lastname": "aa",
      "middlename": null,
      "placeofdeath": null,
      "registrationno": "66573",
      "remarks": null,
      "lastmodifiedby": null,
      "lastmodifiedtime": null,
      "counter": 0,
      "tenantid": "pb.amritsar",
      "hospitalid": null,
      "deathFatherInfo": {
          "id": null,
          "aadharno": null,
          "createdby": null,
          "createdtime": null,
          "emailid": null,
          "firstname": "aaa",
          "lastname": "aaa",
          "middlename": null,
          "mobileno": null,
          "lastmodifiedby": null,
          "lastmodifiedtime": null,
          "fullName": "aaa aaa"
      },
      "deathMotherInfo": {
          "id": null,
          "aadharno": null,
          "createdby": null,
          "createdtime": null,
          "emailid": null,
          "firstname": "aaa",
          "lastname": "aaa",
          "middlename": null,
          "mobileno": null,
          "lastmodifiedby": null,
          "lastmodifiedtime": null,
          "fullName": "aaa aaa"
      },
      "deathPermaddr": null,
      "deathPresentaddr": null,
      "deathSpouseInfo": {
          "id": null,
          "aadharno": null,
          "createdby": null,
          "createdtime": null,
          "emailid": null,
          "firstname": "aaa",
          "lastname": "aaa",
          "middlename": null,
          "mobileno": null,
          "lastmodifiedby": null,
          "lastmodifiedtime": null,
          "fullName": "aaa aaa"
      },
      "age": null,
      "eidno": null,
      "aadharno": null,
      "nationality": null,
      "religion": null,
      "icdcode": null,
      "embeddedUrl": null,
      "deathcertificateno": null,
      "rejectReason": null,
      "fullName": "aaa aa",
      "isLegacyRecord": null
  }]
const CommissionDetails = ({ wrapInCard }) => {
  const { t } = useTranslation()
  let data = {
    applicationDetails: [
      {
          title: "Commission Details",
          asSectionHeader: true,
          values: [
          { title: "Name of SHG", value: 'Maa Bhagawati SHG' },
          { title: "Commission Percentage", value: '7.5%' },
          { title: "Commission Amount", value: 'Rs. 48949'}
          ],
    }],
    certData: records[0]
  }; 

  return (
    <Card noCardStyle={!wrapInCard} style={{marginTop: '-16px'}}>
      <ApplicationDetails
        isLoading={false} //will come from backend
        applicationDetails={data}
        isDataLoading={false}
        applicationData={data?.certData}
        moduleCode="Expenditure"
        showTimeLine={false}
        noBoxShadow
        sectionHeadStyle={{marginBottom: '1rem'}}
      />
      <div style={{margin: "16px", display: "flex", justifyContent:"space-between"}}>
          <CardSectionHeader style={{marginBottom: 0}}>{t("EXP_TOTAL_COMMISSION_AMT")}</CardSectionHeader>
          <CardSectionHeader style={{marginBottom: 0}}>{"₹ 1,20,000"}</CardSectionHeader>
      </div>
    </Card>
  )
}

export default CommissionDetails