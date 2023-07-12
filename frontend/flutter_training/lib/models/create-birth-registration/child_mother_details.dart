import 'package:dart_mappable/dart_mappable.dart';

part 'child_mother_details.mapper.dart';

@MappableClass()
class ChildMotherDetails with ChildMotherDetailsMappable {
  final String firstName;
  final String lastName;
  final String? religion;
  final String nationality;
  final String? aadhaarNo;
  final String? mobileNo;

  ChildMotherDetails({
    required this.firstName,
    required this.lastName,
    this.aadhaarNo,
    this.mobileNo,
    required this.nationality,
    this.religion,
  });
}
