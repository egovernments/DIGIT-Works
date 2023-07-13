import 'package:dart_mappable/dart_mappable.dart';

part 'birth_certificates_model.mapper.dart';

@MappableClass()
class BirthCertificatesList with BirthCertificatesListMappable {
  List<BirthCertificate> birthCerts;

  BirthCertificatesList({required this.birthCerts});
}

@MappableClass()
class BirthCertificate with BirthCertificateMappable {
  BirthFatherInfo birthFatherInfo;
  BirthMotherInfo birthMotherInfo;
  BirthAddress birthPresentaddr;
  BirthAddress birthPermaddr;
  String registrationno;
  String hospitalname;
  int dateofreportepoch;
  int dateofbirthepoch;
  String genderStr;
  String firstname;
  String lastname;
  String placeofbirth;
  bool checkboxforaddress;
  String informantsname;
  String informantsaddress;
  String tenantid;
  int excelrowindex;
  int counter;

  BirthCertificate({
    required this.birthFatherInfo,
    required this.birthMotherInfo,
    required this.birthPresentaddr,
    required this.birthPermaddr,
    required this.registrationno,
    required this.hospitalname,
    required this.dateofreportepoch,
    required this.dateofbirthepoch,
    required this.genderStr,
    required this.firstname,
    required this.lastname,
    required this.placeofbirth,
    required this.checkboxforaddress,
    required this.informantsname,
    required this.informantsaddress,
    required this.tenantid,
    required this.excelrowindex,
    required this.counter,
  });
}

@MappableClass()
class BirthFatherInfo with BirthFatherInfoMappable {
  String firstname;
  String lastname;
  String aadharno;
  String mobileno;
  String religion;
  String nationality;

  BirthFatherInfo({
    required this.firstname,
    required this.lastname,
    required this.aadharno,
    required this.mobileno,
    required this.religion,
    required this.nationality,
  });
}

@MappableClass()
class BirthMotherInfo with BirthMotherInfoMappable {
  String firstname;
  String lastname;
  String aadharno;
  String mobileno;
  String religion;
  String nationality;

  BirthMotherInfo({
    required this.firstname,
    required this.lastname,
    required this.aadharno,
    required this.mobileno,
    required this.religion,
    required this.nationality,
  });
}

@MappableClass()
class BirthAddress with BirthAddressMappable {
  String buildingno;
  String houseno;
  String streetname;
  String locality;
  String tehsil;
  String district;
  String city;
  String state;
  String pinno;
  String country;

  BirthAddress({
    required this.buildingno,
    required this.houseno,
    required this.streetname,
    required this.locality,
    required this.tehsil,
    required this.district,
    required this.city,
    required this.state,
    required this.pinno,
    required this.country,
  });
}
