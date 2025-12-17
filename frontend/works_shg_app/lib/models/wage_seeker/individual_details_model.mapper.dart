// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, unnecessary_cast, override_on_non_overriding_member
// ignore_for_file: strict_raw_type, inference_failure_on_untyped_parameter

part of 'individual_details_model.dart';

class IndividualDetailsMapper extends ClassMapperBase<IndividualDetails> {
  IndividualDetailsMapper._();

  static IndividualDetailsMapper? _instance;
  static IndividualDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = IndividualDetailsMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'IndividualDetails';

  static String? _$aadhaarNo(IndividualDetails v) => v.aadhaarNo;
  static const Field<IndividualDetails, String> _f$aadhaarNo =
      Field('aadhaarNo', _$aadhaarNo, opt: true);
  static String? _$name(IndividualDetails v) => v.name;
  static const Field<IndividualDetails, String> _f$name =
      Field('name', _$name, opt: true);
  static String? _$fatherName(IndividualDetails v) => v.fatherName;
  static const Field<IndividualDetails, String> _f$fatherName =
      Field('fatherName', _$fatherName, opt: true);
  static String? _$relationship(IndividualDetails v) => v.relationship;
  static const Field<IndividualDetails, String> _f$relationship =
      Field('relationship', _$relationship, opt: true);
  static DateTime? _$dateOfBirth(IndividualDetails v) => v.dateOfBirth;
  static const Field<IndividualDetails, DateTime> _f$dateOfBirth =
      Field('dateOfBirth', _$dateOfBirth, opt: true);
  static String? _$gender(IndividualDetails v) => v.gender;
  static const Field<IndividualDetails, String> _f$gender =
      Field('gender', _$gender, opt: true);
  static String? _$socialCategory(IndividualDetails v) => v.socialCategory;
  static const Field<IndividualDetails, String> _f$socialCategory =
      Field('socialCategory', _$socialCategory, opt: true);
  static String? _$mobileNumber(IndividualDetails v) => v.mobileNumber;
  static const Field<IndividualDetails, String> _f$mobileNumber =
      Field('mobileNumber', _$mobileNumber, opt: true);
  static File? _$imageFile(IndividualDetails v) => v.imageFile;
  static const Field<IndividualDetails, File> _f$imageFile =
      Field('imageFile', _$imageFile, opt: true);
  static Uint8List? _$bytes(IndividualDetails v) => v.bytes;
  static const Field<IndividualDetails, Uint8List> _f$bytes =
      Field('bytes', _$bytes, opt: true);
  static String? _$photo(IndividualDetails v) => v.photo;
  static const Field<IndividualDetails, String> _f$photo =
      Field('photo', _$photo, opt: true);
  static bool? _$adharVerified(IndividualDetails v) => v.adharVerified;
  static const Field<IndividualDetails, bool> _f$adharVerified =
      Field('adharVerified', _$adharVerified, opt: true);
  static String? _$documentType(IndividualDetails v) => v.documentType;
  static const Field<IndividualDetails, String> _f$documentType =
      Field('documentType', _$documentType, opt: true);
  static int? _$timeStamp(IndividualDetails v) => v.timeStamp;
  static const Field<IndividualDetails, int> _f$timeStamp =
      Field('timeStamp', _$timeStamp, opt: true);
  static AdharCardResponse? _$adharCardResponse(IndividualDetails v) =>
      v.adharCardResponse;
  static const Field<IndividualDetails, AdharCardResponse>
      _f$adharCardResponse =
      Field('adharCardResponse', _$adharCardResponse, opt: true);

  @override
  final MappableFields<IndividualDetails> fields = const {
    #aadhaarNo: _f$aadhaarNo,
    #name: _f$name,
    #fatherName: _f$fatherName,
    #relationship: _f$relationship,
    #dateOfBirth: _f$dateOfBirth,
    #gender: _f$gender,
    #socialCategory: _f$socialCategory,
    #mobileNumber: _f$mobileNumber,
    #imageFile: _f$imageFile,
    #bytes: _f$bytes,
    #photo: _f$photo,
    #adharVerified: _f$adharVerified,
    #documentType: _f$documentType,
    #timeStamp: _f$timeStamp,
    #adharCardResponse: _f$adharCardResponse,
  };

  static IndividualDetails _instantiate(DecodingData data) {
    return IndividualDetails(
        aadhaarNo: data.dec(_f$aadhaarNo),
        name: data.dec(_f$name),
        fatherName: data.dec(_f$fatherName),
        relationship: data.dec(_f$relationship),
        dateOfBirth: data.dec(_f$dateOfBirth),
        gender: data.dec(_f$gender),
        socialCategory: data.dec(_f$socialCategory),
        mobileNumber: data.dec(_f$mobileNumber),
        imageFile: data.dec(_f$imageFile),
        bytes: data.dec(_f$bytes),
        photo: data.dec(_f$photo),
        adharVerified: data.dec(_f$adharVerified),
        documentType: data.dec(_f$documentType),
        timeStamp: data.dec(_f$timeStamp),
        adharCardResponse: data.dec(_f$adharCardResponse));
  }

  @override
  final Function instantiate = _instantiate;

  static IndividualDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<IndividualDetails>(map);
  }

  static IndividualDetails fromJson(String json) {
    return ensureInitialized().decodeJson<IndividualDetails>(json);
  }
}

mixin IndividualDetailsMappable {
  String toJson() {
    return IndividualDetailsMapper.ensureInitialized()
        .encodeJson<IndividualDetails>(this as IndividualDetails);
  }

  Map<String, dynamic> toMap() {
    return IndividualDetailsMapper.ensureInitialized()
        .encodeMap<IndividualDetails>(this as IndividualDetails);
  }

  IndividualDetailsCopyWith<IndividualDetails, IndividualDetails,
          IndividualDetails>
      get copyWith => _IndividualDetailsCopyWithImpl(
          this as IndividualDetails, $identity, $identity);
  @override
  String toString() {
    return IndividualDetailsMapper.ensureInitialized()
        .stringifyValue(this as IndividualDetails);
  }

  @override
  bool operator ==(Object other) {
    return IndividualDetailsMapper.ensureInitialized()
        .equalsValue(this as IndividualDetails, other);
  }

  @override
  int get hashCode {
    return IndividualDetailsMapper.ensureInitialized()
        .hashValue(this as IndividualDetails);
  }
}

extension IndividualDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, IndividualDetails, $Out> {
  IndividualDetailsCopyWith<$R, IndividualDetails, $Out>
      get $asIndividualDetails =>
          $base.as((v, t, t2) => _IndividualDetailsCopyWithImpl(v, t, t2));
}

abstract class IndividualDetailsCopyWith<$R, $In extends IndividualDetails,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
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
      String? photo,
      bool? adharVerified,
      String? documentType,
      int? timeStamp,
      AdharCardResponse? adharCardResponse});
  IndividualDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _IndividualDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, IndividualDetails, $Out>
    implements IndividualDetailsCopyWith<$R, IndividualDetails, $Out> {
  _IndividualDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<IndividualDetails> $mapper =
      IndividualDetailsMapper.ensureInitialized();
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
          Object? photo = $none,
          Object? adharVerified = $none,
          Object? documentType = $none,
          Object? timeStamp = $none,
          Object? adharCardResponse = $none}) =>
      $apply(FieldCopyWithData({
        if (aadhaarNo != $none) #aadhaarNo: aadhaarNo,
        if (name != $none) #name: name,
        if (fatherName != $none) #fatherName: fatherName,
        if (relationship != $none) #relationship: relationship,
        if (dateOfBirth != $none) #dateOfBirth: dateOfBirth,
        if (gender != $none) #gender: gender,
        if (socialCategory != $none) #socialCategory: socialCategory,
        if (mobileNumber != $none) #mobileNumber: mobileNumber,
        if (imageFile != $none) #imageFile: imageFile,
        if (bytes != $none) #bytes: bytes,
        if (photo != $none) #photo: photo,
        if (adharVerified != $none) #adharVerified: adharVerified,
        if (documentType != $none) #documentType: documentType,
        if (timeStamp != $none) #timeStamp: timeStamp,
        if (adharCardResponse != $none) #adharCardResponse: adharCardResponse
      }));
  @override
  IndividualDetails $make(CopyWithData data) => IndividualDetails(
      aadhaarNo: data.get(#aadhaarNo, or: $value.aadhaarNo),
      name: data.get(#name, or: $value.name),
      fatherName: data.get(#fatherName, or: $value.fatherName),
      relationship: data.get(#relationship, or: $value.relationship),
      dateOfBirth: data.get(#dateOfBirth, or: $value.dateOfBirth),
      gender: data.get(#gender, or: $value.gender),
      socialCategory: data.get(#socialCategory, or: $value.socialCategory),
      mobileNumber: data.get(#mobileNumber, or: $value.mobileNumber),
      imageFile: data.get(#imageFile, or: $value.imageFile),
      bytes: data.get(#bytes, or: $value.bytes),
      photo: data.get(#photo, or: $value.photo),
      adharVerified: data.get(#adharVerified, or: $value.adharVerified),
      documentType: data.get(#documentType, or: $value.documentType),
      timeStamp: data.get(#timeStamp, or: $value.timeStamp),
      adharCardResponse:
          data.get(#adharCardResponse, or: $value.adharCardResponse));

  @override
  IndividualDetailsCopyWith<$R2, IndividualDetails, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _IndividualDetailsCopyWithImpl($value, $cast, t);
}
