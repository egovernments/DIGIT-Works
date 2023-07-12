import 'package:dart_mappable/dart_mappable.dart';

part 'child_address_details.mapper.dart';

@MappableClass()
class ChildAddressDetails with ChildAddressDetailsMappable {
  final String houseNo;
  final String? streetname;
  final String? locality;
  final String tehsil;
  final String district;
  final String city;
  final String state;
  final String country;
  final String buildingNo;
  final String pinNo;

  ChildAddressDetails({
    required this.state,
    this.locality,
    required this.city,
    required this.district,
    required this.country,
    required this.buildingNo,
    required this.houseNo,
    required this.pinNo,
    this.streetname,
    required this.tehsil,
  });
}
