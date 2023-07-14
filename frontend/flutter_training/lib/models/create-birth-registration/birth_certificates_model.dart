import 'package:dart_mappable/dart_mappable.dart';

part 'birth_certificates_model.mapper.dart';

@MappableClass()
class BirthCertificatesList with BirthCertificatesListMappable {
  List<BirthCertificate> birthCerts;

  BirthCertificatesList({required this.birthCerts});
}

@MappableClass()
class BirthCertificate with BirthCertificateMappable {
  BirthFatherInfo? birthFatherInfo;
  BirthMotherInfo? birthMotherInfo;
  BirthAddress? birthPresentaddr;
  BirthAddress? birthPermaddr;
  String registrationno;
  String? hospitalname;
  int? dateofbirth;
  int? dateofreportepoch;
  int? dateofbirthepoch;
  int? dateofreport;
  String genderStr;
  String? firstname;
  String? lastname;
  String? placeofbirth;
  bool? checkboxforaddress;
  String? informantsname;
  String? informantsaddress;
  String tenantid;
  int? excelrowindex;
  int? counter;

  BirthCertificate(
      {this.birthFatherInfo,
      this.birthMotherInfo,
      this.birthPresentaddr,
      this.birthPermaddr,
      required this.registrationno,
      this.hospitalname,
      this.dateofreportepoch,
      this.dateofbirthepoch,
      required this.genderStr,
      this.firstname,
      this.lastname,
      this.placeofbirth,
      this.checkboxforaddress,
      this.informantsname,
      this.informantsaddress,
      required this.tenantid,
      this.excelrowindex,
      this.counter,
      this.dateofbirth,
      this.dateofreport});
}

@MappableClass()
class BirthFatherInfo with BirthFatherInfoMappable {
  String? firstname;
  String? lastname;
  String? aadharno;
  String? mobileno;
  String? religion;
  String? nationality;

  BirthFatherInfo({
    this.firstname,
    this.lastname,
    this.aadharno,
    this.mobileno,
    this.religion,
    this.nationality,
  });
}

@MappableClass()
class BirthMotherInfo with BirthMotherInfoMappable {
  String? firstname;
  String? lastname;
  String? aadharno;
  String? mobileno;
  String? religion;
  String? nationality;

  BirthMotherInfo({
    this.firstname,
    this.lastname,
    this.aadharno,
    this.mobileno,
    this.religion,
    this.nationality,
  });
}

@MappableClass()
class BirthAddress with BirthAddressMappable {
  String? buildingno;
  String? houseno;
  String? streetname;
  String? locality;
  String? tehsil;
  String? district;
  String? city;
  String? state;
  String? pinno;
  String? country;

  BirthAddress({
    this.buildingno,
    this.houseno,
    this.streetname,
    this.locality,
    this.tehsil,
    this.district,
    this.city,
    this.state,
    this.pinno,
    this.country,
  });
}
