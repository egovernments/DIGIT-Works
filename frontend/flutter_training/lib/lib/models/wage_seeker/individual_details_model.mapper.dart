// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'individual_details_model.dart';

class IndividualDetailsMapper extends MapperBase<IndividualDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {IndividualDetailsMapper()},
  );

  @override
  IndividualDetailsMapperElement createElement(MapperContainer container) {
    return IndividualDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'IndividualDetails';

  static final fromMap = container.fromMap<IndividualDetails>;
  static final fromJson = container.fromJson<IndividualDetails>;
}

class IndividualDetailsMapperElement
    extends MapperElementBase<IndividualDetails> {
  IndividualDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  IndividualDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  IndividualDetails fromMap(Map<String, dynamic> map) => IndividualDetails(
      aadhaarNo: container.$getOpt(map, 'aadhaarNo'),
      name: container.$getOpt(map, 'name'),
      fatherName: container.$getOpt(map, 'fatherName'),
      relationship: container.$getOpt(map, 'relationship'),
      dateOfBirth: container.$getOpt(map, 'dateOfBirth'),
      gender: container.$getOpt(map, 'gender'),
      socialCategory: container.$getOpt(map, 'socialCategory'),
      mobileNumber: container.$getOpt(map, 'mobileNumber'),
      imageFile: container.$getOpt(map, 'imageFile'),
      bytes: container.$getOpt(map, 'bytes'),
      photo: container.$getOpt(map, 'photo'));

  @override
  Function get encoder => encode;
  dynamic encode(IndividualDetails v) => toMap(v);
  Map<String, dynamic> toMap(IndividualDetails i) => {
        'aadhaarNo': container.$enc(i.aadhaarNo, 'aadhaarNo'),
        'name': container.$enc(i.name, 'name'),
        'fatherName': container.$enc(i.fatherName, 'fatherName'),
        'relationship': container.$enc(i.relationship, 'relationship'),
        'dateOfBirth': container.$enc(i.dateOfBirth, 'dateOfBirth'),
        'gender': container.$enc(i.gender, 'gender'),
        'socialCategory': container.$enc(i.socialCategory, 'socialCategory'),
        'mobileNumber': container.$enc(i.mobileNumber, 'mobileNumber'),
        'imageFile': container.$enc(i.imageFile, 'imageFile'),
        'bytes': container.$enc(i.bytes, 'bytes'),
        'photo': container.$enc(i.photo, 'photo')
      };

  @override
  String stringify(IndividualDetails self) =>
      'IndividualDetails(aadhaarNo: ${container.asString(self.aadhaarNo)}, name: ${container.asString(self.name)}, fatherName: ${container.asString(self.fatherName)}, relationship: ${container.asString(self.relationship)}, dateOfBirth: ${container.asString(self.dateOfBirth)}, gender: ${container.asString(self.gender)}, socialCategory: ${container.asString(self.socialCategory)}, mobileNumber: ${container.asString(self.mobileNumber)}, imageFile: ${container.asString(self.imageFile)}, bytes: ${container.asString(self.bytes)}, photo: ${container.asString(self.photo)})';
  @override
  int hash(IndividualDetails self) =>
      container.hash(self.aadhaarNo) ^
      container.hash(self.name) ^
      container.hash(self.fatherName) ^
      container.hash(self.relationship) ^
      container.hash(self.dateOfBirth) ^
      container.hash(self.gender) ^
      container.hash(self.socialCategory) ^
      container.hash(self.mobileNumber) ^
      container.hash(self.imageFile) ^
      container.hash(self.bytes) ^
      container.hash(self.photo);
  @override
  bool equals(IndividualDetails self, IndividualDetails other) =>
      container.isEqual(self.aadhaarNo, other.aadhaarNo) &&
      container.isEqual(self.name, other.name) &&
      container.isEqual(self.fatherName, other.fatherName) &&
      container.isEqual(self.relationship, other.relationship) &&
      container.isEqual(self.dateOfBirth, other.dateOfBirth) &&
      container.isEqual(self.gender, other.gender) &&
      container.isEqual(self.socialCategory, other.socialCategory) &&
      container.isEqual(self.mobileNumber, other.mobileNumber) &&
      container.isEqual(self.imageFile, other.imageFile) &&
      container.isEqual(self.bytes, other.bytes) &&
      container.isEqual(self.photo, other.photo);
}

mixin IndividualDetailsMappable {
  String toJson() =>
      IndividualDetailsMapper.container.toJson(this as IndividualDetails);
  Map<String, dynamic> toMap() =>
      IndividualDetailsMapper.container.toMap(this as IndividualDetails);
  IndividualDetailsCopyWith<IndividualDetails, IndividualDetails,
          IndividualDetails>
      get copyWith => _IndividualDetailsCopyWithImpl(
          this as IndividualDetails, $identity, $identity);
  @override
  String toString() => IndividualDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          IndividualDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => IndividualDetailsMapper.container.hash(this);
}

extension IndividualDetailsValueCopy<$R, $Out extends IndividualDetails>
    on ObjectCopyWith<$R, IndividualDetails, $Out> {
  IndividualDetailsCopyWith<$R, IndividualDetails, $Out>
      get asIndividualDetails =>
          base.as((v, t, t2) => _IndividualDetailsCopyWithImpl(v, t, t2));
}

typedef IndividualDetailsCopyWithBound = IndividualDetails;

abstract class IndividualDetailsCopyWith<$R, $In extends IndividualDetails,
    $Out extends IndividualDetails> implements ObjectCopyWith<$R, $In, $Out> {
  IndividualDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends IndividualDetails>(
          Then<IndividualDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? aadhaarNo,
      String? name,
      String? fatherName,
      String? relationship,
      DateTime? dateOfBirth,
      String? gender,
      String? socialCategory,
      String? mobileNumber,
      File? imageFile,
      Uint8List? bytes,
      String? photo});
}

class _IndividualDetailsCopyWithImpl<$R, $Out extends IndividualDetails>
    extends CopyWithBase<$R, IndividualDetails, $Out>
    implements IndividualDetailsCopyWith<$R, IndividualDetails, $Out> {
  _IndividualDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  IndividualDetailsCopyWith<$R2, IndividualDetails, $Out2>
      chain<$R2, $Out2 extends IndividualDetails>(
              Then<IndividualDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _IndividualDetailsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? aadhaarNo = $none,
          Object? name = $none,
          Object? fatherName = $none,
          Object? relationship = $none,
          Object? dateOfBirth = $none,
          Object? gender = $none,
          Object? socialCategory = $none,
          Object? mobileNumber = $none,
          Object? imageFile = $none,
          Object? bytes = $none,
          Object? photo = $none}) =>
      $then(IndividualDetails(
          aadhaarNo: or(aadhaarNo, $value.aadhaarNo),
          name: or(name, $value.name),
          fatherName: or(fatherName, $value.fatherName),
          relationship: or(relationship, $value.relationship),
          dateOfBirth: or(dateOfBirth, $value.dateOfBirth),
          gender: or(gender, $value.gender),
          socialCategory: or(socialCategory, $value.socialCategory),
          mobileNumber: or(mobileNumber, $value.mobileNumber),
          imageFile: or(imageFile, $value.imageFile),
          bytes: or(bytes, $value.bytes),
          photo: or(photo, $value.photo)));
}
