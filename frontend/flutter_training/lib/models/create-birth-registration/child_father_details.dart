import 'package:dart_mappable/dart_mappable.dart';

part 'child_father_details.mapper.dart';

@MappableClass()
class ChildFatherDetails with ChildFatherDetailsMappable {
  final String firstName;
  final String lastName;
  final String? religion;
  final String nationality;
  final String? aadhaarNo;
  final String? mobileNo;

  ChildFatherDetails({
    required this.firstName,
    required this.lastName,
    this.aadhaarNo,
    this.mobileNo,
    required this.nationality,
    this.religion,
  });
}
