// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'location_details_model.dart';

class LocationDetailsMapper extends MapperBase<LocationDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {LocationDetailsMapper()},
  );

  @override
  LocationDetailsMapperElement createElement(MapperContainer container) {
    return LocationDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'LocationDetails';

  static final fromMap = container.fromMap<LocationDetails>;
  static final fromJson = container.fromJson<LocationDetails>;
}

class LocationDetailsMapperElement extends MapperElementBase<LocationDetails> {
  LocationDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  LocationDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  LocationDetails fromMap(Map<String, dynamic> map) => LocationDetails(
      pinCode: container.$getOpt(map, 'pinCode'),
      locality: container.$getOpt(map, 'locality'),
      ward: container.$getOpt(map, 'ward'),
      city: container.$getOpt(map, 'city'),
      streetName: container.$getOpt(map, 'streetName'),
      doorNo: container.$getOpt(map, 'doorNo'));

  @override
  Function get encoder => encode;
  dynamic encode(LocationDetails v) => toMap(v);
  Map<String, dynamic> toMap(LocationDetails l) => {
        'pinCode': container.$enc(l.pinCode, 'pinCode'),
        'locality': container.$enc(l.locality, 'locality'),
        'ward': container.$enc(l.ward, 'ward'),
        'city': container.$enc(l.city, 'city'),
        'streetName': container.$enc(l.streetName, 'streetName'),
        'doorNo': container.$enc(l.doorNo, 'doorNo')
      };

  @override
  String stringify(LocationDetails self) =>
      'LocationDetails(pinCode: ${container.asString(self.pinCode)}, locality: ${container.asString(self.locality)}, city: ${container.asString(self.city)}, ward: ${container.asString(self.ward)}, streetName: ${container.asString(self.streetName)}, doorNo: ${container.asString(self.doorNo)})';
  @override
  int hash(LocationDetails self) =>
      container.hash(self.pinCode) ^
      container.hash(self.locality) ^
      container.hash(self.city) ^
      container.hash(self.ward) ^
      container.hash(self.streetName) ^
      container.hash(self.doorNo);
  @override
  bool equals(LocationDetails self, LocationDetails other) =>
      container.isEqual(self.pinCode, other.pinCode) &&
      container.isEqual(self.locality, other.locality) &&
      container.isEqual(self.city, other.city) &&
      container.isEqual(self.ward, other.ward) &&
      container.isEqual(self.streetName, other.streetName) &&
      container.isEqual(self.doorNo, other.doorNo);
}

mixin LocationDetailsMappable {
  String toJson() =>
      LocationDetailsMapper.container.toJson(this as LocationDetails);
  Map<String, dynamic> toMap() =>
      LocationDetailsMapper.container.toMap(this as LocationDetails);
  LocationDetailsCopyWith<LocationDetails, LocationDetails, LocationDetails>
      get copyWith => _LocationDetailsCopyWithImpl(
          this as LocationDetails, $identity, $identity);
  @override
  String toString() => LocationDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          LocationDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => LocationDetailsMapper.container.hash(this);
}

extension LocationDetailsValueCopy<$R, $Out extends LocationDetails>
    on ObjectCopyWith<$R, LocationDetails, $Out> {
  LocationDetailsCopyWith<$R, LocationDetails, $Out> get asLocationDetails =>
      base.as((v, t, t2) => _LocationDetailsCopyWithImpl(v, t, t2));
}

typedef LocationDetailsCopyWithBound = LocationDetails;

abstract class LocationDetailsCopyWith<$R, $In extends LocationDetails,
    $Out extends LocationDetails> implements ObjectCopyWith<$R, $In, $Out> {
  LocationDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends LocationDetails>(
          Then<LocationDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? pinCode,
      String? locality,
      String? ward,
      String? city,
      String? streetName,
      String? doorNo});
}

class _LocationDetailsCopyWithImpl<$R, $Out extends LocationDetails>
    extends CopyWithBase<$R, LocationDetails, $Out>
    implements LocationDetailsCopyWith<$R, LocationDetails, $Out> {
  _LocationDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  LocationDetailsCopyWith<$R2, LocationDetails, $Out2>
      chain<$R2, $Out2 extends LocationDetails>(
              Then<LocationDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _LocationDetailsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? pinCode = $none,
          Object? locality = $none,
          Object? ward = $none,
          Object? city = $none,
          Object? streetName = $none,
          Object? doorNo = $none}) =>
      $then(LocationDetails(
          pinCode: or(pinCode, $value.pinCode),
          locality: or(locality, $value.locality),
          ward: or(ward, $value.ward),
          city: or(city, $value.city),
          streetName: or(streetName, $value.streetName),
          doorNo: or(doorNo, $value.doorNo)));
}
