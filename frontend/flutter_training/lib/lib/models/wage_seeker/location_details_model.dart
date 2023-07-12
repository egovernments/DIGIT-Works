import 'package:dart_mappable/dart_mappable.dart';

part 'location_details_model.mapper.dart';

@MappableClass()
class LocationDetails with LocationDetailsMappable {
  final String? pinCode;
  final String? locality;
  final String? city;
  final String? ward;
  final String? streetName;
  final String? doorNo;

  LocationDetails({
    this.pinCode,
    this.locality,
    this.ward,
    this.city,
    this.streetName,
    this.doorNo,
  });
}
