import 'package:dart_mappable/dart_mappable.dart';

part 'child_details.mapper.dart';

@MappableClass()
class ChildDetails with ChildDetailsMappable {
  final String regNo;
  final String firstName;
  final String lastName;
  final String hospitalName;
  final String placeOfBirth;
  final bool checkBoxForAddress;
  final String genderStr;
  final int dateOfReport;
  final int dateOfBirth;
  final String informantName;
  final String informantAddress;

  ChildDetails(
      {required this.regNo,
      required this.dateOfBirth,
      this.checkBoxForAddress = true,
      required this.firstName,
      required this.lastName,
      required this.genderStr,
      required this.hospitalName,
      required this.informantName,
      required this.informantAddress,
      required this.placeOfBirth,
      required this.dateOfReport});
}
