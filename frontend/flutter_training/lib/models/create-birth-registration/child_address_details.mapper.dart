// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'child_address_details.dart';

class ChildAddressDetailsMapper extends MapperBase<ChildAddressDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {ChildAddressDetailsMapper()},
  );

  @override
  ChildAddressDetailsMapperElement createElement(MapperContainer container) {
    return ChildAddressDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'ChildAddressDetails';

  static final fromMap = container.fromMap<ChildAddressDetails>;
  static final fromJson = container.fromJson<ChildAddressDetails>;
}

class ChildAddressDetailsMapperElement
    extends MapperElementBase<ChildAddressDetails> {
  ChildAddressDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  ChildAddressDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  ChildAddressDetails fromMap(Map<String, dynamic> map) => ChildAddressDetails(
      state: container.$get(map, 'state'),
      locality: container.$getOpt(map, 'locality'),
      city: container.$get(map, 'city'),
      district: container.$get(map, 'district'),
      country: container.$get(map, 'country'),
      buildingNo: container.$get(map, 'buildingNo'),
      houseNo: container.$get(map, 'houseNo'),
      pinNo: container.$get(map, 'pinNo'),
      streetname: container.$getOpt(map, 'streetname'),
      tehsil: container.$get(map, 'tehsil'));

  @override
  Function get encoder => encode;
  dynamic encode(ChildAddressDetails v) => toMap(v);
  Map<String, dynamic> toMap(ChildAddressDetails c) => {
        'state': container.$enc(c.state, 'state'),
        'locality': container.$enc(c.locality, 'locality'),
        'city': container.$enc(c.city, 'city'),
        'district': container.$enc(c.district, 'district'),
        'country': container.$enc(c.country, 'country'),
        'buildingNo': container.$enc(c.buildingNo, 'buildingNo'),
        'houseNo': container.$enc(c.houseNo, 'houseNo'),
        'pinNo': container.$enc(c.pinNo, 'pinNo'),
        'streetname': container.$enc(c.streetname, 'streetname'),
        'tehsil': container.$enc(c.tehsil, 'tehsil')
      };

  @override
  String stringify(ChildAddressDetails self) =>
      'ChildAddressDetails(houseNo: ${container.asString(self.houseNo)}, streetname: ${container.asString(self.streetname)}, locality: ${container.asString(self.locality)}, tehsil: ${container.asString(self.tehsil)}, district: ${container.asString(self.district)}, city: ${container.asString(self.city)}, state: ${container.asString(self.state)}, country: ${container.asString(self.country)}, buildingNo: ${container.asString(self.buildingNo)}, pinNo: ${container.asString(self.pinNo)})';
  @override
  int hash(ChildAddressDetails self) =>
      container.hash(self.houseNo) ^
      container.hash(self.streetname) ^
      container.hash(self.locality) ^
      container.hash(self.tehsil) ^
      container.hash(self.district) ^
      container.hash(self.city) ^
      container.hash(self.state) ^
      container.hash(self.country) ^
      container.hash(self.buildingNo) ^
      container.hash(self.pinNo);
  @override
  bool equals(ChildAddressDetails self, ChildAddressDetails other) =>
      container.isEqual(self.houseNo, other.houseNo) &&
      container.isEqual(self.streetname, other.streetname) &&
      container.isEqual(self.locality, other.locality) &&
      container.isEqual(self.tehsil, other.tehsil) &&
      container.isEqual(self.district, other.district) &&
      container.isEqual(self.city, other.city) &&
      container.isEqual(self.state, other.state) &&
      container.isEqual(self.country, other.country) &&
      container.isEqual(self.buildingNo, other.buildingNo) &&
      container.isEqual(self.pinNo, other.pinNo);
}

mixin ChildAddressDetailsMappable {
  String toJson() =>
      ChildAddressDetailsMapper.container.toJson(this as ChildAddressDetails);
  Map<String, dynamic> toMap() =>
      ChildAddressDetailsMapper.container.toMap(this as ChildAddressDetails);
  ChildAddressDetailsCopyWith<ChildAddressDetails, ChildAddressDetails,
          ChildAddressDetails>
      get copyWith => _ChildAddressDetailsCopyWithImpl(
          this as ChildAddressDetails, $identity, $identity);
  @override
  String toString() => ChildAddressDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          ChildAddressDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => ChildAddressDetailsMapper.container.hash(this);
}

extension ChildAddressDetailsValueCopy<$R, $Out extends ChildAddressDetails>
    on ObjectCopyWith<$R, ChildAddressDetails, $Out> {
  ChildAddressDetailsCopyWith<$R, ChildAddressDetails, $Out>
      get asChildAddressDetails =>
          base.as((v, t, t2) => _ChildAddressDetailsCopyWithImpl(v, t, t2));
}

typedef ChildAddressDetailsCopyWithBound = ChildAddressDetails;

abstract class ChildAddressDetailsCopyWith<$R, $In extends ChildAddressDetails,
    $Out extends ChildAddressDetails> implements ObjectCopyWith<$R, $In, $Out> {
  ChildAddressDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends ChildAddressDetails>(
          Then<ChildAddressDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? state,
      String? locality,
      String? city,
      String? district,
      String? country,
      String? buildingNo,
      String? houseNo,
      String? pinNo,
      String? streetname,
      String? tehsil});
}

class _ChildAddressDetailsCopyWithImpl<$R, $Out extends ChildAddressDetails>
    extends CopyWithBase<$R, ChildAddressDetails, $Out>
    implements ChildAddressDetailsCopyWith<$R, ChildAddressDetails, $Out> {
  _ChildAddressDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  ChildAddressDetailsCopyWith<$R2, ChildAddressDetails, $Out2>
      chain<$R2, $Out2 extends ChildAddressDetails>(
              Then<ChildAddressDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _ChildAddressDetailsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {String? state,
          Object? locality = $none,
          String? city,
          String? district,
          String? country,
          String? buildingNo,
          String? houseNo,
          String? pinNo,
          Object? streetname = $none,
          String? tehsil}) =>
      $then(ChildAddressDetails(
          state: state ?? $value.state,
          locality: or(locality, $value.locality),
          city: city ?? $value.city,
          district: district ?? $value.district,
          country: country ?? $value.country,
          buildingNo: buildingNo ?? $value.buildingNo,
          houseNo: houseNo ?? $value.houseNo,
          pinNo: pinNo ?? $value.pinNo,
          streetname: or(streetname, $value.streetname),
          tehsil: tehsil ?? $value.tehsil));
}
