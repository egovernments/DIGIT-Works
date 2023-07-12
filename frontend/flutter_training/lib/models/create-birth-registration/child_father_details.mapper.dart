// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'child_father_details.dart';

class ChildFatherDetailsMapper extends MapperBase<ChildFatherDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {ChildFatherDetailsMapper()},
  );

  @override
  ChildFatherDetailsMapperElement createElement(MapperContainer container) {
    return ChildFatherDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'ChildFatherDetails';

  static final fromMap = container.fromMap<ChildFatherDetails>;
  static final fromJson = container.fromJson<ChildFatherDetails>;
}

class ChildFatherDetailsMapperElement
    extends MapperElementBase<ChildFatherDetails> {
  ChildFatherDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  ChildFatherDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  ChildFatherDetails fromMap(Map<String, dynamic> map) => ChildFatherDetails(
      firstName: container.$get(map, 'firstName'),
      lastName: container.$get(map, 'lastName'),
      aadhaarNo: container.$getOpt(map, 'aadhaarNo'),
      mobileNo: container.$getOpt(map, 'mobileNo'),
      nationality: container.$get(map, 'nationality'),
      religion: container.$getOpt(map, 'religion'));

  @override
  Function get encoder => encode;
  dynamic encode(ChildFatherDetails v) => toMap(v);
  Map<String, dynamic> toMap(ChildFatherDetails c) => {
        'firstName': container.$enc(c.firstName, 'firstName'),
        'lastName': container.$enc(c.lastName, 'lastName'),
        'aadhaarNo': container.$enc(c.aadhaarNo, 'aadhaarNo'),
        'mobileNo': container.$enc(c.mobileNo, 'mobileNo'),
        'nationality': container.$enc(c.nationality, 'nationality'),
        'religion': container.$enc(c.religion, 'religion')
      };

  @override
  String stringify(ChildFatherDetails self) =>
      'ChildFatherDetails(firstName: ${container.asString(self.firstName)}, lastName: ${container.asString(self.lastName)}, religion: ${container.asString(self.religion)}, nationality: ${container.asString(self.nationality)}, aadhaarNo: ${container.asString(self.aadhaarNo)}, mobileNo: ${container.asString(self.mobileNo)})';
  @override
  int hash(ChildFatherDetails self) =>
      container.hash(self.firstName) ^
      container.hash(self.lastName) ^
      container.hash(self.religion) ^
      container.hash(self.nationality) ^
      container.hash(self.aadhaarNo) ^
      container.hash(self.mobileNo);
  @override
  bool equals(ChildFatherDetails self, ChildFatherDetails other) =>
      container.isEqual(self.firstName, other.firstName) &&
      container.isEqual(self.lastName, other.lastName) &&
      container.isEqual(self.religion, other.religion) &&
      container.isEqual(self.nationality, other.nationality) &&
      container.isEqual(self.aadhaarNo, other.aadhaarNo) &&
      container.isEqual(self.mobileNo, other.mobileNo);
}

mixin ChildFatherDetailsMappable {
  String toJson() =>
      ChildFatherDetailsMapper.container.toJson(this as ChildFatherDetails);
  Map<String, dynamic> toMap() =>
      ChildFatherDetailsMapper.container.toMap(this as ChildFatherDetails);
  ChildFatherDetailsCopyWith<ChildFatherDetails, ChildFatherDetails,
          ChildFatherDetails>
      get copyWith => _ChildFatherDetailsCopyWithImpl(
          this as ChildFatherDetails, $identity, $identity);
  @override
  String toString() => ChildFatherDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          ChildFatherDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => ChildFatherDetailsMapper.container.hash(this);
}

extension ChildFatherDetailsValueCopy<$R, $Out extends ChildFatherDetails>
    on ObjectCopyWith<$R, ChildFatherDetails, $Out> {
  ChildFatherDetailsCopyWith<$R, ChildFatherDetails, $Out>
      get asChildFatherDetails =>
          base.as((v, t, t2) => _ChildFatherDetailsCopyWithImpl(v, t, t2));
}

typedef ChildFatherDetailsCopyWithBound = ChildFatherDetails;

abstract class ChildFatherDetailsCopyWith<$R, $In extends ChildFatherDetails,
    $Out extends ChildFatherDetails> implements ObjectCopyWith<$R, $In, $Out> {
  ChildFatherDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends ChildFatherDetails>(
          Then<ChildFatherDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? firstName,
      String? lastName,
      String? aadhaarNo,
      String? mobileNo,
      String? nationality,
      String? religion});
}

class _ChildFatherDetailsCopyWithImpl<$R, $Out extends ChildFatherDetails>
    extends CopyWithBase<$R, ChildFatherDetails, $Out>
    implements ChildFatherDetailsCopyWith<$R, ChildFatherDetails, $Out> {
  _ChildFatherDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  ChildFatherDetailsCopyWith<$R2, ChildFatherDetails, $Out2>
      chain<$R2, $Out2 extends ChildFatherDetails>(
              Then<ChildFatherDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _ChildFatherDetailsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {String? firstName,
          String? lastName,
          Object? aadhaarNo = $none,
          Object? mobileNo = $none,
          String? nationality,
          Object? religion = $none}) =>
      $then(ChildFatherDetails(
          firstName: firstName ?? $value.firstName,
          lastName: lastName ?? $value.lastName,
          aadhaarNo: or(aadhaarNo, $value.aadhaarNo),
          mobileNo: or(mobileNo, $value.mobileNo),
          nationality: nationality ?? $value.nationality,
          religion: or(religion, $value.religion)));
}
