// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, unnecessary_cast, override_on_non_overriding_member
// ignore_for_file: strict_raw_type, inference_failure_on_untyped_parameter

part of 'location_details_model.dart';

class LocationDetailsMapper extends ClassMapperBase<LocationDetails> {
  LocationDetailsMapper._();

  static LocationDetailsMapper? _instance;
  static LocationDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = LocationDetailsMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'LocationDetails';

  static String? _$pinCode(LocationDetails v) => v.pinCode;
  static const Field<LocationDetails, String> _f$pinCode =
      Field('pinCode', _$pinCode, opt: true);
  static String? _$locality(LocationDetails v) => v.locality;
  static const Field<LocationDetails, String> _f$locality =
      Field('locality', _$locality, opt: true);
  static String? _$ward(LocationDetails v) => v.ward;
  static const Field<LocationDetails, String> _f$ward =
      Field('ward', _$ward, opt: true);
  static String? _$city(LocationDetails v) => v.city;
  static const Field<LocationDetails, String> _f$city =
      Field('city', _$city, opt: true);
  static String? _$streetName(LocationDetails v) => v.streetName;
  static const Field<LocationDetails, String> _f$streetName =
      Field('streetName', _$streetName, opt: true);
  static String? _$doorNo(LocationDetails v) => v.doorNo;
  static const Field<LocationDetails, String> _f$doorNo =
      Field('doorNo', _$doorNo, opt: true);

  @override
  final MappableFields<LocationDetails> fields = const {
    #pinCode: _f$pinCode,
    #locality: _f$locality,
    #ward: _f$ward,
    #city: _f$city,
    #streetName: _f$streetName,
    #doorNo: _f$doorNo,
  };

  static LocationDetails _instantiate(DecodingData data) {
    return LocationDetails(
        pinCode: data.dec(_f$pinCode),
        locality: data.dec(_f$locality),
        ward: data.dec(_f$ward),
        city: data.dec(_f$city),
        streetName: data.dec(_f$streetName),
        doorNo: data.dec(_f$doorNo));
  }

  @override
  final Function instantiate = _instantiate;

  static LocationDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<LocationDetails>(map);
  }

  static LocationDetails fromJson(String json) {
    return ensureInitialized().decodeJson<LocationDetails>(json);
  }
}

mixin LocationDetailsMappable {
  String toJson() {
    return LocationDetailsMapper.ensureInitialized()
        .encodeJson<LocationDetails>(this as LocationDetails);
  }

  Map<String, dynamic> toMap() {
    return LocationDetailsMapper.ensureInitialized()
        .encodeMap<LocationDetails>(this as LocationDetails);
  }

  LocationDetailsCopyWith<LocationDetails, LocationDetails, LocationDetails>
      get copyWith => _LocationDetailsCopyWithImpl(
          this as LocationDetails, $identity, $identity);
  @override
  String toString() {
    return LocationDetailsMapper.ensureInitialized()
        .stringifyValue(this as LocationDetails);
  }

  @override
  bool operator ==(Object other) {
    return LocationDetailsMapper.ensureInitialized()
        .equalsValue(this as LocationDetails, other);
  }

  @override
  int get hashCode {
    return LocationDetailsMapper.ensureInitialized()
        .hashValue(this as LocationDetails);
  }
}

extension LocationDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, LocationDetails, $Out> {
  LocationDetailsCopyWith<$R, LocationDetails, $Out> get $asLocationDetails =>
      $base.as((v, t, t2) => _LocationDetailsCopyWithImpl(v, t, t2));
}

abstract class LocationDetailsCopyWith<$R, $In extends LocationDetails, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  $R call(
      {String? pinCode,
      String? locality,
      String? ward,
      String? city,
      String? streetName,
      String? doorNo});
  LocationDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _LocationDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, LocationDetails, $Out>
    implements LocationDetailsCopyWith<$R, LocationDetails, $Out> {
  _LocationDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<LocationDetails> $mapper =
      LocationDetailsMapper.ensureInitialized();
  @override
  $R call(
          {Object? pinCode = $none,
          Object? locality = $none,
          Object? ward = $none,
          Object? city = $none,
          Object? streetName = $none,
          Object? doorNo = $none}) =>
      $apply(FieldCopyWithData({
        if (pinCode != $none) #pinCode: pinCode,
        if (locality != $none) #locality: locality,
        if (ward != $none) #ward: ward,
        if (city != $none) #city: city,
        if (streetName != $none) #streetName: streetName,
        if (doorNo != $none) #doorNo: doorNo
      }));
  @override
  LocationDetails $make(CopyWithData data) => LocationDetails(
      pinCode: data.get(#pinCode, or: $value.pinCode),
      locality: data.get(#locality, or: $value.locality),
      ward: data.get(#ward, or: $value.ward),
      city: data.get(#city, or: $value.city),
      streetName: data.get(#streetName, or: $value.streetName),
      doorNo: data.get(#doorNo, or: $value.doorNo));

  @override
  LocationDetailsCopyWith<$R2, LocationDetails, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _LocationDetailsCopyWithImpl($value, $cast, t);
}
