// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'wage_seeker_registration_bloc.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$WageSeekerBlocEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)
        identificationCreate,
    required TResult Function(
            String fatherName,
            DateTime dob,
            String relationShip,
            String gender,
            String socialCategory,
            String mobileNumber)
        detailsCreate,
    required TResult Function(SkillDetails skillDetails) skillCreate,
    required TResult Function(File? imageFile, Uint8List? bytes, String? photo)
        photoCreate,
    required TResult Function() clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult? Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult? Function(SkillDetails skillDetails)? skillCreate,
    TResult? Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult? Function()? clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult Function(SkillDetails skillDetails)? skillCreate,
    TResult Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult Function()? clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(WageSeekerCreateEvent value) create,
    required TResult Function(WageSeekerIdentificationCreateEvent value)
        identificationCreate,
    required TResult Function(WageSeekerDetailsCreateEvent value) detailsCreate,
    required TResult Function(WageSeekerSkillCreateEvent value) skillCreate,
    required TResult Function(WageSeekerPhotoCreateEvent value) photoCreate,
    required TResult Function(WageSeekerClearEvent value) clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WageSeekerCreateEvent value)? create,
    TResult? Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult? Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult? Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult? Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult? Function(WageSeekerClearEvent value)? clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WageSeekerCreateEvent value)? create,
    TResult Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult Function(WageSeekerClearEvent value)? clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WageSeekerBlocEventCopyWith<$Res> {
  factory $WageSeekerBlocEventCopyWith(
          WageSeekerBlocEvent value, $Res Function(WageSeekerBlocEvent) then) =
      _$WageSeekerBlocEventCopyWithImpl<$Res, WageSeekerBlocEvent>;
}

/// @nodoc
class _$WageSeekerBlocEventCopyWithImpl<$Res, $Val extends WageSeekerBlocEvent>
    implements $WageSeekerBlocEventCopyWith<$Res> {
  _$WageSeekerBlocEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$WageSeekerCreateEventImplCopyWith<$Res> {
  factory _$$WageSeekerCreateEventImplCopyWith(
          _$WageSeekerCreateEventImpl value,
          $Res Function(_$WageSeekerCreateEventImpl) then) =
      __$$WageSeekerCreateEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails});
}

/// @nodoc
class __$$WageSeekerCreateEventImplCopyWithImpl<$Res>
    extends _$WageSeekerBlocEventCopyWithImpl<$Res, _$WageSeekerCreateEventImpl>
    implements _$$WageSeekerCreateEventImplCopyWith<$Res> {
  __$$WageSeekerCreateEventImplCopyWithImpl(_$WageSeekerCreateEventImpl _value,
      $Res Function(_$WageSeekerCreateEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualDetails = freezed,
    Object? skillDetails = freezed,
    Object? locationDetails = freezed,
    Object? financialDetails = freezed,
  }) {
    return _then(_$WageSeekerCreateEventImpl(
      individualDetails: freezed == individualDetails
          ? _value.individualDetails
          : individualDetails // ignore: cast_nullable_to_non_nullable
              as IndividualDetails?,
      skillDetails: freezed == skillDetails
          ? _value.skillDetails
          : skillDetails // ignore: cast_nullable_to_non_nullable
              as SkillDetails?,
      locationDetails: freezed == locationDetails
          ? _value.locationDetails
          : locationDetails // ignore: cast_nullable_to_non_nullable
              as LocationDetails?,
      financialDetails: freezed == financialDetails
          ? _value.financialDetails
          : financialDetails // ignore: cast_nullable_to_non_nullable
              as FinancialDetails?,
    ));
  }
}

/// @nodoc

class _$WageSeekerCreateEventImpl
    with DiagnosticableTreeMixin
    implements WageSeekerCreateEvent {
  const _$WageSeekerCreateEventImpl(
      {this.individualDetails,
      this.skillDetails,
      this.locationDetails,
      this.financialDetails});

  @override
  final IndividualDetails? individualDetails;
  @override
  final SkillDetails? skillDetails;
  @override
  final LocationDetails? locationDetails;
  @override
  final FinancialDetails? financialDetails;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'WageSeekerBlocEvent.create(individualDetails: $individualDetails, skillDetails: $skillDetails, locationDetails: $locationDetails, financialDetails: $financialDetails)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'WageSeekerBlocEvent.create'))
      ..add(DiagnosticsProperty('individualDetails', individualDetails))
      ..add(DiagnosticsProperty('skillDetails', skillDetails))
      ..add(DiagnosticsProperty('locationDetails', locationDetails))
      ..add(DiagnosticsProperty('financialDetails', financialDetails));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WageSeekerCreateEventImpl &&
            (identical(other.individualDetails, individualDetails) ||
                other.individualDetails == individualDetails) &&
            (identical(other.skillDetails, skillDetails) ||
                other.skillDetails == skillDetails) &&
            (identical(other.locationDetails, locationDetails) ||
                other.locationDetails == locationDetails) &&
            (identical(other.financialDetails, financialDetails) ||
                other.financialDetails == financialDetails));
  }

  @override
  int get hashCode => Object.hash(runtimeType, individualDetails, skillDetails,
      locationDetails, financialDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WageSeekerCreateEventImplCopyWith<_$WageSeekerCreateEventImpl>
      get copyWith => __$$WageSeekerCreateEventImplCopyWithImpl<
          _$WageSeekerCreateEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)
        identificationCreate,
    required TResult Function(
            String fatherName,
            DateTime dob,
            String relationShip,
            String gender,
            String socialCategory,
            String mobileNumber)
        detailsCreate,
    required TResult Function(SkillDetails skillDetails) skillCreate,
    required TResult Function(File? imageFile, Uint8List? bytes, String? photo)
        photoCreate,
    required TResult Function() clear,
  }) {
    return create(
        individualDetails, skillDetails, locationDetails, financialDetails);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult? Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult? Function(SkillDetails skillDetails)? skillCreate,
    TResult? Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult? Function()? clear,
  }) {
    return create?.call(
        individualDetails, skillDetails, locationDetails, financialDetails);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult Function(SkillDetails skillDetails)? skillCreate,
    TResult Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(
          individualDetails, skillDetails, locationDetails, financialDetails);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(WageSeekerCreateEvent value) create,
    required TResult Function(WageSeekerIdentificationCreateEvent value)
        identificationCreate,
    required TResult Function(WageSeekerDetailsCreateEvent value) detailsCreate,
    required TResult Function(WageSeekerSkillCreateEvent value) skillCreate,
    required TResult Function(WageSeekerPhotoCreateEvent value) photoCreate,
    required TResult Function(WageSeekerClearEvent value) clear,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WageSeekerCreateEvent value)? create,
    TResult? Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult? Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult? Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult? Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult? Function(WageSeekerClearEvent value)? clear,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WageSeekerCreateEvent value)? create,
    TResult Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult Function(WageSeekerClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class WageSeekerCreateEvent implements WageSeekerBlocEvent {
  const factory WageSeekerCreateEvent(
      {final IndividualDetails? individualDetails,
      final SkillDetails? skillDetails,
      final LocationDetails? locationDetails,
      final FinancialDetails? financialDetails}) = _$WageSeekerCreateEventImpl;

  IndividualDetails? get individualDetails;
  SkillDetails? get skillDetails;
  LocationDetails? get locationDetails;
  FinancialDetails? get financialDetails;
  @JsonKey(ignore: true)
  _$$WageSeekerCreateEventImplCopyWith<_$WageSeekerCreateEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$WageSeekerIdentificationCreateEventImplCopyWith<$Res> {
  factory _$$WageSeekerIdentificationCreateEventImplCopyWith(
          _$WageSeekerIdentificationCreateEventImpl value,
          $Res Function(_$WageSeekerIdentificationCreateEventImpl) then) =
      __$$WageSeekerIdentificationCreateEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {String documentType,
      String number,
      String name,
      bool adharVerified,
      int timeStamp,
      AdharCardResponse? adharCardResponse});

  $AdharCardResponseCopyWith<$Res>? get adharCardResponse;
}

/// @nodoc
class __$$WageSeekerIdentificationCreateEventImplCopyWithImpl<$Res>
    extends _$WageSeekerBlocEventCopyWithImpl<$Res,
        _$WageSeekerIdentificationCreateEventImpl>
    implements _$$WageSeekerIdentificationCreateEventImplCopyWith<$Res> {
  __$$WageSeekerIdentificationCreateEventImplCopyWithImpl(
      _$WageSeekerIdentificationCreateEventImpl _value,
      $Res Function(_$WageSeekerIdentificationCreateEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? documentType = null,
    Object? number = null,
    Object? name = null,
    Object? adharVerified = null,
    Object? timeStamp = null,
    Object? adharCardResponse = freezed,
  }) {
    return _then(_$WageSeekerIdentificationCreateEventImpl(
      documentType: null == documentType
          ? _value.documentType
          : documentType // ignore: cast_nullable_to_non_nullable
              as String,
      number: null == number
          ? _value.number
          : number // ignore: cast_nullable_to_non_nullable
              as String,
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      adharVerified: null == adharVerified
          ? _value.adharVerified
          : adharVerified // ignore: cast_nullable_to_non_nullable
              as bool,
      timeStamp: null == timeStamp
          ? _value.timeStamp
          : timeStamp // ignore: cast_nullable_to_non_nullable
              as int,
      adharCardResponse: freezed == adharCardResponse
          ? _value.adharCardResponse
          : adharCardResponse // ignore: cast_nullable_to_non_nullable
              as AdharCardResponse?,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $AdharCardResponseCopyWith<$Res>? get adharCardResponse {
    if (_value.adharCardResponse == null) {
      return null;
    }

    return $AdharCardResponseCopyWith<$Res>(_value.adharCardResponse!, (value) {
      return _then(_value.copyWith(adharCardResponse: value));
    });
  }
}

/// @nodoc

class _$WageSeekerIdentificationCreateEventImpl
    with DiagnosticableTreeMixin
    implements WageSeekerIdentificationCreateEvent {
  const _$WageSeekerIdentificationCreateEventImpl(
      {required this.documentType,
      required this.number,
      required this.name,
      required this.adharVerified,
      required this.timeStamp,
      this.adharCardResponse});

  @override
  final String documentType;
  @override
  final String number;
  @override
  final String name;
  @override
  final bool adharVerified;
  @override
  final int timeStamp;
  @override
  final AdharCardResponse? adharCardResponse;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'WageSeekerBlocEvent.identificationCreate(documentType: $documentType, number: $number, name: $name, adharVerified: $adharVerified, timeStamp: $timeStamp, adharCardResponse: $adharCardResponse)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty(
          'type', 'WageSeekerBlocEvent.identificationCreate'))
      ..add(DiagnosticsProperty('documentType', documentType))
      ..add(DiagnosticsProperty('number', number))
      ..add(DiagnosticsProperty('name', name))
      ..add(DiagnosticsProperty('adharVerified', adharVerified))
      ..add(DiagnosticsProperty('timeStamp', timeStamp))
      ..add(DiagnosticsProperty('adharCardResponse', adharCardResponse));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WageSeekerIdentificationCreateEventImpl &&
            (identical(other.documentType, documentType) ||
                other.documentType == documentType) &&
            (identical(other.number, number) || other.number == number) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.adharVerified, adharVerified) ||
                other.adharVerified == adharVerified) &&
            (identical(other.timeStamp, timeStamp) ||
                other.timeStamp == timeStamp) &&
            (identical(other.adharCardResponse, adharCardResponse) ||
                other.adharCardResponse == adharCardResponse));
  }

  @override
  int get hashCode => Object.hash(runtimeType, documentType, number, name,
      adharVerified, timeStamp, adharCardResponse);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WageSeekerIdentificationCreateEventImplCopyWith<
          _$WageSeekerIdentificationCreateEventImpl>
      get copyWith => __$$WageSeekerIdentificationCreateEventImplCopyWithImpl<
          _$WageSeekerIdentificationCreateEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)
        identificationCreate,
    required TResult Function(
            String fatherName,
            DateTime dob,
            String relationShip,
            String gender,
            String socialCategory,
            String mobileNumber)
        detailsCreate,
    required TResult Function(SkillDetails skillDetails) skillCreate,
    required TResult Function(File? imageFile, Uint8List? bytes, String? photo)
        photoCreate,
    required TResult Function() clear,
  }) {
    return identificationCreate(documentType, number, name, adharVerified,
        timeStamp, adharCardResponse);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult? Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult? Function(SkillDetails skillDetails)? skillCreate,
    TResult? Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult? Function()? clear,
  }) {
    return identificationCreate?.call(documentType, number, name, adharVerified,
        timeStamp, adharCardResponse);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult Function(SkillDetails skillDetails)? skillCreate,
    TResult Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (identificationCreate != null) {
      return identificationCreate(documentType, number, name, adharVerified,
          timeStamp, adharCardResponse);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(WageSeekerCreateEvent value) create,
    required TResult Function(WageSeekerIdentificationCreateEvent value)
        identificationCreate,
    required TResult Function(WageSeekerDetailsCreateEvent value) detailsCreate,
    required TResult Function(WageSeekerSkillCreateEvent value) skillCreate,
    required TResult Function(WageSeekerPhotoCreateEvent value) photoCreate,
    required TResult Function(WageSeekerClearEvent value) clear,
  }) {
    return identificationCreate(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WageSeekerCreateEvent value)? create,
    TResult? Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult? Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult? Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult? Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult? Function(WageSeekerClearEvent value)? clear,
  }) {
    return identificationCreate?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WageSeekerCreateEvent value)? create,
    TResult Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult Function(WageSeekerClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (identificationCreate != null) {
      return identificationCreate(this);
    }
    return orElse();
  }
}

abstract class WageSeekerIdentificationCreateEvent
    implements WageSeekerBlocEvent {
  const factory WageSeekerIdentificationCreateEvent(
          {required final String documentType,
          required final String number,
          required final String name,
          required final bool adharVerified,
          required final int timeStamp,
          final AdharCardResponse? adharCardResponse}) =
      _$WageSeekerIdentificationCreateEventImpl;

  String get documentType;
  String get number;
  String get name;
  bool get adharVerified;
  int get timeStamp;
  AdharCardResponse? get adharCardResponse;
  @JsonKey(ignore: true)
  _$$WageSeekerIdentificationCreateEventImplCopyWith<
          _$WageSeekerIdentificationCreateEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$WageSeekerDetailsCreateEventImplCopyWith<$Res> {
  factory _$$WageSeekerDetailsCreateEventImplCopyWith(
          _$WageSeekerDetailsCreateEventImpl value,
          $Res Function(_$WageSeekerDetailsCreateEventImpl) then) =
      __$$WageSeekerDetailsCreateEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {String fatherName,
      DateTime dob,
      String relationShip,
      String gender,
      String socialCategory,
      String mobileNumber});
}

/// @nodoc
class __$$WageSeekerDetailsCreateEventImplCopyWithImpl<$Res>
    extends _$WageSeekerBlocEventCopyWithImpl<$Res,
        _$WageSeekerDetailsCreateEventImpl>
    implements _$$WageSeekerDetailsCreateEventImplCopyWith<$Res> {
  __$$WageSeekerDetailsCreateEventImplCopyWithImpl(
      _$WageSeekerDetailsCreateEventImpl _value,
      $Res Function(_$WageSeekerDetailsCreateEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? fatherName = null,
    Object? dob = null,
    Object? relationShip = null,
    Object? gender = null,
    Object? socialCategory = null,
    Object? mobileNumber = null,
  }) {
    return _then(_$WageSeekerDetailsCreateEventImpl(
      fatherName: null == fatherName
          ? _value.fatherName
          : fatherName // ignore: cast_nullable_to_non_nullable
              as String,
      dob: null == dob
          ? _value.dob
          : dob // ignore: cast_nullable_to_non_nullable
              as DateTime,
      relationShip: null == relationShip
          ? _value.relationShip
          : relationShip // ignore: cast_nullable_to_non_nullable
              as String,
      gender: null == gender
          ? _value.gender
          : gender // ignore: cast_nullable_to_non_nullable
              as String,
      socialCategory: null == socialCategory
          ? _value.socialCategory
          : socialCategory // ignore: cast_nullable_to_non_nullable
              as String,
      mobileNumber: null == mobileNumber
          ? _value.mobileNumber
          : mobileNumber // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$WageSeekerDetailsCreateEventImpl
    with DiagnosticableTreeMixin
    implements WageSeekerDetailsCreateEvent {
  const _$WageSeekerDetailsCreateEventImpl(
      {required this.fatherName,
      required this.dob,
      required this.relationShip,
      required this.gender,
      required this.socialCategory,
      required this.mobileNumber});

  @override
  final String fatherName;
  @override
  final DateTime dob;
  @override
  final String relationShip;
  @override
  final String gender;
  @override
  final String socialCategory;
  @override
  final String mobileNumber;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'WageSeekerBlocEvent.detailsCreate(fatherName: $fatherName, dob: $dob, relationShip: $relationShip, gender: $gender, socialCategory: $socialCategory, mobileNumber: $mobileNumber)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'WageSeekerBlocEvent.detailsCreate'))
      ..add(DiagnosticsProperty('fatherName', fatherName))
      ..add(DiagnosticsProperty('dob', dob))
      ..add(DiagnosticsProperty('relationShip', relationShip))
      ..add(DiagnosticsProperty('gender', gender))
      ..add(DiagnosticsProperty('socialCategory', socialCategory))
      ..add(DiagnosticsProperty('mobileNumber', mobileNumber));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WageSeekerDetailsCreateEventImpl &&
            (identical(other.fatherName, fatherName) ||
                other.fatherName == fatherName) &&
            (identical(other.dob, dob) || other.dob == dob) &&
            (identical(other.relationShip, relationShip) ||
                other.relationShip == relationShip) &&
            (identical(other.gender, gender) || other.gender == gender) &&
            (identical(other.socialCategory, socialCategory) ||
                other.socialCategory == socialCategory) &&
            (identical(other.mobileNumber, mobileNumber) ||
                other.mobileNumber == mobileNumber));
  }

  @override
  int get hashCode => Object.hash(runtimeType, fatherName, dob, relationShip,
      gender, socialCategory, mobileNumber);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WageSeekerDetailsCreateEventImplCopyWith<
          _$WageSeekerDetailsCreateEventImpl>
      get copyWith => __$$WageSeekerDetailsCreateEventImplCopyWithImpl<
          _$WageSeekerDetailsCreateEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)
        identificationCreate,
    required TResult Function(
            String fatherName,
            DateTime dob,
            String relationShip,
            String gender,
            String socialCategory,
            String mobileNumber)
        detailsCreate,
    required TResult Function(SkillDetails skillDetails) skillCreate,
    required TResult Function(File? imageFile, Uint8List? bytes, String? photo)
        photoCreate,
    required TResult Function() clear,
  }) {
    return detailsCreate(
        fatherName, dob, relationShip, gender, socialCategory, mobileNumber);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult? Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult? Function(SkillDetails skillDetails)? skillCreate,
    TResult? Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult? Function()? clear,
  }) {
    return detailsCreate?.call(
        fatherName, dob, relationShip, gender, socialCategory, mobileNumber);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult Function(SkillDetails skillDetails)? skillCreate,
    TResult Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (detailsCreate != null) {
      return detailsCreate(
          fatherName, dob, relationShip, gender, socialCategory, mobileNumber);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(WageSeekerCreateEvent value) create,
    required TResult Function(WageSeekerIdentificationCreateEvent value)
        identificationCreate,
    required TResult Function(WageSeekerDetailsCreateEvent value) detailsCreate,
    required TResult Function(WageSeekerSkillCreateEvent value) skillCreate,
    required TResult Function(WageSeekerPhotoCreateEvent value) photoCreate,
    required TResult Function(WageSeekerClearEvent value) clear,
  }) {
    return detailsCreate(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WageSeekerCreateEvent value)? create,
    TResult? Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult? Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult? Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult? Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult? Function(WageSeekerClearEvent value)? clear,
  }) {
    return detailsCreate?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WageSeekerCreateEvent value)? create,
    TResult Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult Function(WageSeekerClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (detailsCreate != null) {
      return detailsCreate(this);
    }
    return orElse();
  }
}

abstract class WageSeekerDetailsCreateEvent implements WageSeekerBlocEvent {
  const factory WageSeekerDetailsCreateEvent(
      {required final String fatherName,
      required final DateTime dob,
      required final String relationShip,
      required final String gender,
      required final String socialCategory,
      required final String mobileNumber}) = _$WageSeekerDetailsCreateEventImpl;

  String get fatherName;
  DateTime get dob;
  String get relationShip;
  String get gender;
  String get socialCategory;
  String get mobileNumber;
  @JsonKey(ignore: true)
  _$$WageSeekerDetailsCreateEventImplCopyWith<
          _$WageSeekerDetailsCreateEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$WageSeekerSkillCreateEventImplCopyWith<$Res> {
  factory _$$WageSeekerSkillCreateEventImplCopyWith(
          _$WageSeekerSkillCreateEventImpl value,
          $Res Function(_$WageSeekerSkillCreateEventImpl) then) =
      __$$WageSeekerSkillCreateEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({SkillDetails skillDetails});
}

/// @nodoc
class __$$WageSeekerSkillCreateEventImplCopyWithImpl<$Res>
    extends _$WageSeekerBlocEventCopyWithImpl<$Res,
        _$WageSeekerSkillCreateEventImpl>
    implements _$$WageSeekerSkillCreateEventImplCopyWith<$Res> {
  __$$WageSeekerSkillCreateEventImplCopyWithImpl(
      _$WageSeekerSkillCreateEventImpl _value,
      $Res Function(_$WageSeekerSkillCreateEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? skillDetails = null,
  }) {
    return _then(_$WageSeekerSkillCreateEventImpl(
      skillDetails: null == skillDetails
          ? _value.skillDetails
          : skillDetails // ignore: cast_nullable_to_non_nullable
              as SkillDetails,
    ));
  }
}

/// @nodoc

class _$WageSeekerSkillCreateEventImpl
    with DiagnosticableTreeMixin
    implements WageSeekerSkillCreateEvent {
  const _$WageSeekerSkillCreateEventImpl({required this.skillDetails});

  @override
  final SkillDetails skillDetails;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'WageSeekerBlocEvent.skillCreate(skillDetails: $skillDetails)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'WageSeekerBlocEvent.skillCreate'))
      ..add(DiagnosticsProperty('skillDetails', skillDetails));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WageSeekerSkillCreateEventImpl &&
            (identical(other.skillDetails, skillDetails) ||
                other.skillDetails == skillDetails));
  }

  @override
  int get hashCode => Object.hash(runtimeType, skillDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WageSeekerSkillCreateEventImplCopyWith<_$WageSeekerSkillCreateEventImpl>
      get copyWith => __$$WageSeekerSkillCreateEventImplCopyWithImpl<
          _$WageSeekerSkillCreateEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)
        identificationCreate,
    required TResult Function(
            String fatherName,
            DateTime dob,
            String relationShip,
            String gender,
            String socialCategory,
            String mobileNumber)
        detailsCreate,
    required TResult Function(SkillDetails skillDetails) skillCreate,
    required TResult Function(File? imageFile, Uint8List? bytes, String? photo)
        photoCreate,
    required TResult Function() clear,
  }) {
    return skillCreate(skillDetails);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult? Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult? Function(SkillDetails skillDetails)? skillCreate,
    TResult? Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult? Function()? clear,
  }) {
    return skillCreate?.call(skillDetails);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult Function(SkillDetails skillDetails)? skillCreate,
    TResult Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (skillCreate != null) {
      return skillCreate(skillDetails);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(WageSeekerCreateEvent value) create,
    required TResult Function(WageSeekerIdentificationCreateEvent value)
        identificationCreate,
    required TResult Function(WageSeekerDetailsCreateEvent value) detailsCreate,
    required TResult Function(WageSeekerSkillCreateEvent value) skillCreate,
    required TResult Function(WageSeekerPhotoCreateEvent value) photoCreate,
    required TResult Function(WageSeekerClearEvent value) clear,
  }) {
    return skillCreate(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WageSeekerCreateEvent value)? create,
    TResult? Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult? Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult? Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult? Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult? Function(WageSeekerClearEvent value)? clear,
  }) {
    return skillCreate?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WageSeekerCreateEvent value)? create,
    TResult Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult Function(WageSeekerClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (skillCreate != null) {
      return skillCreate(this);
    }
    return orElse();
  }
}

abstract class WageSeekerSkillCreateEvent implements WageSeekerBlocEvent {
  const factory WageSeekerSkillCreateEvent(
          {required final SkillDetails skillDetails}) =
      _$WageSeekerSkillCreateEventImpl;

  SkillDetails get skillDetails;
  @JsonKey(ignore: true)
  _$$WageSeekerSkillCreateEventImplCopyWith<_$WageSeekerSkillCreateEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$WageSeekerPhotoCreateEventImplCopyWith<$Res> {
  factory _$$WageSeekerPhotoCreateEventImplCopyWith(
          _$WageSeekerPhotoCreateEventImpl value,
          $Res Function(_$WageSeekerPhotoCreateEventImpl) then) =
      __$$WageSeekerPhotoCreateEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({File? imageFile, Uint8List? bytes, String? photo});
}

/// @nodoc
class __$$WageSeekerPhotoCreateEventImplCopyWithImpl<$Res>
    extends _$WageSeekerBlocEventCopyWithImpl<$Res,
        _$WageSeekerPhotoCreateEventImpl>
    implements _$$WageSeekerPhotoCreateEventImplCopyWith<$Res> {
  __$$WageSeekerPhotoCreateEventImplCopyWithImpl(
      _$WageSeekerPhotoCreateEventImpl _value,
      $Res Function(_$WageSeekerPhotoCreateEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? imageFile = freezed,
    Object? bytes = freezed,
    Object? photo = freezed,
  }) {
    return _then(_$WageSeekerPhotoCreateEventImpl(
      imageFile: freezed == imageFile
          ? _value.imageFile
          : imageFile // ignore: cast_nullable_to_non_nullable
              as File?,
      bytes: freezed == bytes
          ? _value.bytes
          : bytes // ignore: cast_nullable_to_non_nullable
              as Uint8List?,
      photo: freezed == photo
          ? _value.photo
          : photo // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$WageSeekerPhotoCreateEventImpl
    with DiagnosticableTreeMixin
    implements WageSeekerPhotoCreateEvent {
  const _$WageSeekerPhotoCreateEventImpl(
      {this.imageFile, this.bytes, this.photo});

  @override
  final File? imageFile;
  @override
  final Uint8List? bytes;
  @override
  final String? photo;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'WageSeekerBlocEvent.photoCreate(imageFile: $imageFile, bytes: $bytes, photo: $photo)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'WageSeekerBlocEvent.photoCreate'))
      ..add(DiagnosticsProperty('imageFile', imageFile))
      ..add(DiagnosticsProperty('bytes', bytes))
      ..add(DiagnosticsProperty('photo', photo));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WageSeekerPhotoCreateEventImpl &&
            (identical(other.imageFile, imageFile) ||
                other.imageFile == imageFile) &&
            const DeepCollectionEquality().equals(other.bytes, bytes) &&
            (identical(other.photo, photo) || other.photo == photo));
  }

  @override
  int get hashCode => Object.hash(runtimeType, imageFile,
      const DeepCollectionEquality().hash(bytes), photo);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WageSeekerPhotoCreateEventImplCopyWith<_$WageSeekerPhotoCreateEventImpl>
      get copyWith => __$$WageSeekerPhotoCreateEventImplCopyWithImpl<
          _$WageSeekerPhotoCreateEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)
        identificationCreate,
    required TResult Function(
            String fatherName,
            DateTime dob,
            String relationShip,
            String gender,
            String socialCategory,
            String mobileNumber)
        detailsCreate,
    required TResult Function(SkillDetails skillDetails) skillCreate,
    required TResult Function(File? imageFile, Uint8List? bytes, String? photo)
        photoCreate,
    required TResult Function() clear,
  }) {
    return photoCreate(imageFile, bytes, photo);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult? Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult? Function(SkillDetails skillDetails)? skillCreate,
    TResult? Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult? Function()? clear,
  }) {
    return photoCreate?.call(imageFile, bytes, photo);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult Function(SkillDetails skillDetails)? skillCreate,
    TResult Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (photoCreate != null) {
      return photoCreate(imageFile, bytes, photo);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(WageSeekerCreateEvent value) create,
    required TResult Function(WageSeekerIdentificationCreateEvent value)
        identificationCreate,
    required TResult Function(WageSeekerDetailsCreateEvent value) detailsCreate,
    required TResult Function(WageSeekerSkillCreateEvent value) skillCreate,
    required TResult Function(WageSeekerPhotoCreateEvent value) photoCreate,
    required TResult Function(WageSeekerClearEvent value) clear,
  }) {
    return photoCreate(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WageSeekerCreateEvent value)? create,
    TResult? Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult? Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult? Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult? Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult? Function(WageSeekerClearEvent value)? clear,
  }) {
    return photoCreate?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WageSeekerCreateEvent value)? create,
    TResult Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult Function(WageSeekerClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (photoCreate != null) {
      return photoCreate(this);
    }
    return orElse();
  }
}

abstract class WageSeekerPhotoCreateEvent implements WageSeekerBlocEvent {
  const factory WageSeekerPhotoCreateEvent(
      {final File? imageFile,
      final Uint8List? bytes,
      final String? photo}) = _$WageSeekerPhotoCreateEventImpl;

  File? get imageFile;
  Uint8List? get bytes;
  String? get photo;
  @JsonKey(ignore: true)
  _$$WageSeekerPhotoCreateEventImplCopyWith<_$WageSeekerPhotoCreateEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$WageSeekerClearEventImplCopyWith<$Res> {
  factory _$$WageSeekerClearEventImplCopyWith(_$WageSeekerClearEventImpl value,
          $Res Function(_$WageSeekerClearEventImpl) then) =
      __$$WageSeekerClearEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$WageSeekerClearEventImplCopyWithImpl<$Res>
    extends _$WageSeekerBlocEventCopyWithImpl<$Res, _$WageSeekerClearEventImpl>
    implements _$$WageSeekerClearEventImplCopyWith<$Res> {
  __$$WageSeekerClearEventImplCopyWithImpl(_$WageSeekerClearEventImpl _value,
      $Res Function(_$WageSeekerClearEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$WageSeekerClearEventImpl
    with DiagnosticableTreeMixin
    implements WageSeekerClearEvent {
  const _$WageSeekerClearEventImpl();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'WageSeekerBlocEvent.clear()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(DiagnosticsProperty('type', 'WageSeekerBlocEvent.clear'));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WageSeekerClearEventImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)
        identificationCreate,
    required TResult Function(
            String fatherName,
            DateTime dob,
            String relationShip,
            String gender,
            String socialCategory,
            String mobileNumber)
        detailsCreate,
    required TResult Function(SkillDetails skillDetails) skillCreate,
    required TResult Function(File? imageFile, Uint8List? bytes, String? photo)
        photoCreate,
    required TResult Function() clear,
  }) {
    return clear();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult? Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult? Function(SkillDetails skillDetails)? skillCreate,
    TResult? Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult? Function()? clear,
  }) {
    return clear?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            String documentType,
            String number,
            String name,
            bool adharVerified,
            int timeStamp,
            AdharCardResponse? adharCardResponse)?
        identificationCreate,
    TResult Function(String fatherName, DateTime dob, String relationShip,
            String gender, String socialCategory, String mobileNumber)?
        detailsCreate,
    TResult Function(SkillDetails skillDetails)? skillCreate,
    TResult Function(File? imageFile, Uint8List? bytes, String? photo)?
        photoCreate,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(WageSeekerCreateEvent value) create,
    required TResult Function(WageSeekerIdentificationCreateEvent value)
        identificationCreate,
    required TResult Function(WageSeekerDetailsCreateEvent value) detailsCreate,
    required TResult Function(WageSeekerSkillCreateEvent value) skillCreate,
    required TResult Function(WageSeekerPhotoCreateEvent value) photoCreate,
    required TResult Function(WageSeekerClearEvent value) clear,
  }) {
    return clear(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WageSeekerCreateEvent value)? create,
    TResult? Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult? Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult? Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult? Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult? Function(WageSeekerClearEvent value)? clear,
  }) {
    return clear?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WageSeekerCreateEvent value)? create,
    TResult Function(WageSeekerIdentificationCreateEvent value)?
        identificationCreate,
    TResult Function(WageSeekerDetailsCreateEvent value)? detailsCreate,
    TResult Function(WageSeekerSkillCreateEvent value)? skillCreate,
    TResult Function(WageSeekerPhotoCreateEvent value)? photoCreate,
    TResult Function(WageSeekerClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear(this);
    }
    return orElse();
  }
}

abstract class WageSeekerClearEvent implements WageSeekerBlocEvent {
  const factory WageSeekerClearEvent() = _$WageSeekerClearEventImpl;
}

/// @nodoc
mixin _$WageSeekerBlocState {
  IndividualDetails? get individualDetails =>
      throw _privateConstructorUsedError;
  SkillDetails? get skillDetails => throw _privateConstructorUsedError;
  LocationDetails? get locationDetails => throw _privateConstructorUsedError;
  FinancialDetails? get financialDetails => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_CreateWageSeeker value) create,
    required TResult Function(_EditWageSeeker value) clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_CreateWageSeeker value)? create,
    TResult? Function(_EditWageSeeker value)? clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_CreateWageSeeker value)? create,
    TResult Function(_EditWageSeeker value)? clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $WageSeekerBlocStateCopyWith<WageSeekerBlocState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WageSeekerBlocStateCopyWith<$Res> {
  factory $WageSeekerBlocStateCopyWith(
          WageSeekerBlocState value, $Res Function(WageSeekerBlocState) then) =
      _$WageSeekerBlocStateCopyWithImpl<$Res, WageSeekerBlocState>;
  @useResult
  $Res call(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails});
}

/// @nodoc
class _$WageSeekerBlocStateCopyWithImpl<$Res, $Val extends WageSeekerBlocState>
    implements $WageSeekerBlocStateCopyWith<$Res> {
  _$WageSeekerBlocStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualDetails = freezed,
    Object? skillDetails = freezed,
    Object? locationDetails = freezed,
    Object? financialDetails = freezed,
  }) {
    return _then(_value.copyWith(
      individualDetails: freezed == individualDetails
          ? _value.individualDetails
          : individualDetails // ignore: cast_nullable_to_non_nullable
              as IndividualDetails?,
      skillDetails: freezed == skillDetails
          ? _value.skillDetails
          : skillDetails // ignore: cast_nullable_to_non_nullable
              as SkillDetails?,
      locationDetails: freezed == locationDetails
          ? _value.locationDetails
          : locationDetails // ignore: cast_nullable_to_non_nullable
              as LocationDetails?,
      financialDetails: freezed == financialDetails
          ? _value.financialDetails
          : financialDetails // ignore: cast_nullable_to_non_nullable
              as FinancialDetails?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$CreateWageSeekerImplCopyWith<$Res>
    implements $WageSeekerBlocStateCopyWith<$Res> {
  factory _$$CreateWageSeekerImplCopyWith(_$CreateWageSeekerImpl value,
          $Res Function(_$CreateWageSeekerImpl) then) =
      __$$CreateWageSeekerImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails});
}

/// @nodoc
class __$$CreateWageSeekerImplCopyWithImpl<$Res>
    extends _$WageSeekerBlocStateCopyWithImpl<$Res, _$CreateWageSeekerImpl>
    implements _$$CreateWageSeekerImplCopyWith<$Res> {
  __$$CreateWageSeekerImplCopyWithImpl(_$CreateWageSeekerImpl _value,
      $Res Function(_$CreateWageSeekerImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualDetails = freezed,
    Object? skillDetails = freezed,
    Object? locationDetails = freezed,
    Object? financialDetails = freezed,
  }) {
    return _then(_$CreateWageSeekerImpl(
      individualDetails: freezed == individualDetails
          ? _value.individualDetails
          : individualDetails // ignore: cast_nullable_to_non_nullable
              as IndividualDetails?,
      skillDetails: freezed == skillDetails
          ? _value.skillDetails
          : skillDetails // ignore: cast_nullable_to_non_nullable
              as SkillDetails?,
      locationDetails: freezed == locationDetails
          ? _value.locationDetails
          : locationDetails // ignore: cast_nullable_to_non_nullable
              as LocationDetails?,
      financialDetails: freezed == financialDetails
          ? _value.financialDetails
          : financialDetails // ignore: cast_nullable_to_non_nullable
              as FinancialDetails?,
    ));
  }
}

/// @nodoc

class _$CreateWageSeekerImpl extends _CreateWageSeeker
    with DiagnosticableTreeMixin {
  const _$CreateWageSeekerImpl(
      {this.individualDetails,
      this.skillDetails,
      this.locationDetails,
      this.financialDetails})
      : super._();

  @override
  final IndividualDetails? individualDetails;
  @override
  final SkillDetails? skillDetails;
  @override
  final LocationDetails? locationDetails;
  @override
  final FinancialDetails? financialDetails;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'WageSeekerBlocState.create(individualDetails: $individualDetails, skillDetails: $skillDetails, locationDetails: $locationDetails, financialDetails: $financialDetails)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'WageSeekerBlocState.create'))
      ..add(DiagnosticsProperty('individualDetails', individualDetails))
      ..add(DiagnosticsProperty('skillDetails', skillDetails))
      ..add(DiagnosticsProperty('locationDetails', locationDetails))
      ..add(DiagnosticsProperty('financialDetails', financialDetails));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateWageSeekerImpl &&
            (identical(other.individualDetails, individualDetails) ||
                other.individualDetails == individualDetails) &&
            (identical(other.skillDetails, skillDetails) ||
                other.skillDetails == skillDetails) &&
            (identical(other.locationDetails, locationDetails) ||
                other.locationDetails == locationDetails) &&
            (identical(other.financialDetails, financialDetails) ||
                other.financialDetails == financialDetails));
  }

  @override
  int get hashCode => Object.hash(runtimeType, individualDetails, skillDetails,
      locationDetails, financialDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$CreateWageSeekerImplCopyWith<_$CreateWageSeekerImpl> get copyWith =>
      __$$CreateWageSeekerImplCopyWithImpl<_$CreateWageSeekerImpl>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        clear,
  }) {
    return create(
        individualDetails, skillDetails, locationDetails, financialDetails);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        clear,
  }) {
    return create?.call(
        individualDetails, skillDetails, locationDetails, financialDetails);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        clear,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(
          individualDetails, skillDetails, locationDetails, financialDetails);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_CreateWageSeeker value) create,
    required TResult Function(_EditWageSeeker value) clear,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_CreateWageSeeker value)? create,
    TResult? Function(_EditWageSeeker value)? clear,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_CreateWageSeeker value)? create,
    TResult Function(_EditWageSeeker value)? clear,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class _CreateWageSeeker extends WageSeekerBlocState {
  const factory _CreateWageSeeker(
      {final IndividualDetails? individualDetails,
      final SkillDetails? skillDetails,
      final LocationDetails? locationDetails,
      final FinancialDetails? financialDetails}) = _$CreateWageSeekerImpl;
  const _CreateWageSeeker._() : super._();

  @override
  IndividualDetails? get individualDetails;
  @override
  SkillDetails? get skillDetails;
  @override
  LocationDetails? get locationDetails;
  @override
  FinancialDetails? get financialDetails;
  @override
  @JsonKey(ignore: true)
  _$$CreateWageSeekerImplCopyWith<_$CreateWageSeekerImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$EditWageSeekerImplCopyWith<$Res>
    implements $WageSeekerBlocStateCopyWith<$Res> {
  factory _$$EditWageSeekerImplCopyWith(_$EditWageSeekerImpl value,
          $Res Function(_$EditWageSeekerImpl) then) =
      __$$EditWageSeekerImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {IndividualDetails? individualDetails,
      SkillDetails? skillDetails,
      LocationDetails? locationDetails,
      FinancialDetails? financialDetails});
}

/// @nodoc
class __$$EditWageSeekerImplCopyWithImpl<$Res>
    extends _$WageSeekerBlocStateCopyWithImpl<$Res, _$EditWageSeekerImpl>
    implements _$$EditWageSeekerImplCopyWith<$Res> {
  __$$EditWageSeekerImplCopyWithImpl(
      _$EditWageSeekerImpl _value, $Res Function(_$EditWageSeekerImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualDetails = freezed,
    Object? skillDetails = freezed,
    Object? locationDetails = freezed,
    Object? financialDetails = freezed,
  }) {
    return _then(_$EditWageSeekerImpl(
      individualDetails: freezed == individualDetails
          ? _value.individualDetails
          : individualDetails // ignore: cast_nullable_to_non_nullable
              as IndividualDetails?,
      skillDetails: freezed == skillDetails
          ? _value.skillDetails
          : skillDetails // ignore: cast_nullable_to_non_nullable
              as SkillDetails?,
      locationDetails: freezed == locationDetails
          ? _value.locationDetails
          : locationDetails // ignore: cast_nullable_to_non_nullable
              as LocationDetails?,
      financialDetails: freezed == financialDetails
          ? _value.financialDetails
          : financialDetails // ignore: cast_nullable_to_non_nullable
              as FinancialDetails?,
    ));
  }
}

/// @nodoc

class _$EditWageSeekerImpl extends _EditWageSeeker
    with DiagnosticableTreeMixin {
  const _$EditWageSeekerImpl(
      {this.individualDetails,
      this.skillDetails,
      this.locationDetails,
      this.financialDetails})
      : super._();

  @override
  final IndividualDetails? individualDetails;
  @override
  final SkillDetails? skillDetails;
  @override
  final LocationDetails? locationDetails;
  @override
  final FinancialDetails? financialDetails;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'WageSeekerBlocState.clear(individualDetails: $individualDetails, skillDetails: $skillDetails, locationDetails: $locationDetails, financialDetails: $financialDetails)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'WageSeekerBlocState.clear'))
      ..add(DiagnosticsProperty('individualDetails', individualDetails))
      ..add(DiagnosticsProperty('skillDetails', skillDetails))
      ..add(DiagnosticsProperty('locationDetails', locationDetails))
      ..add(DiagnosticsProperty('financialDetails', financialDetails));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$EditWageSeekerImpl &&
            (identical(other.individualDetails, individualDetails) ||
                other.individualDetails == individualDetails) &&
            (identical(other.skillDetails, skillDetails) ||
                other.skillDetails == skillDetails) &&
            (identical(other.locationDetails, locationDetails) ||
                other.locationDetails == locationDetails) &&
            (identical(other.financialDetails, financialDetails) ||
                other.financialDetails == financialDetails));
  }

  @override
  int get hashCode => Object.hash(runtimeType, individualDetails, skillDetails,
      locationDetails, financialDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$EditWageSeekerImplCopyWith<_$EditWageSeekerImpl> get copyWith =>
      __$$EditWageSeekerImplCopyWithImpl<_$EditWageSeekerImpl>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        create,
    required TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)
        clear,
  }) {
    return clear(
        individualDetails, skillDetails, locationDetails, financialDetails);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult? Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        clear,
  }) {
    return clear?.call(
        individualDetails, skillDetails, locationDetails, financialDetails);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        create,
    TResult Function(
            IndividualDetails? individualDetails,
            SkillDetails? skillDetails,
            LocationDetails? locationDetails,
            FinancialDetails? financialDetails)?
        clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear(
          individualDetails, skillDetails, locationDetails, financialDetails);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_CreateWageSeeker value) create,
    required TResult Function(_EditWageSeeker value) clear,
  }) {
    return clear(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_CreateWageSeeker value)? create,
    TResult? Function(_EditWageSeeker value)? clear,
  }) {
    return clear?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_CreateWageSeeker value)? create,
    TResult Function(_EditWageSeeker value)? clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear(this);
    }
    return orElse();
  }
}

abstract class _EditWageSeeker extends WageSeekerBlocState {
  const factory _EditWageSeeker(
      {final IndividualDetails? individualDetails,
      final SkillDetails? skillDetails,
      final LocationDetails? locationDetails,
      final FinancialDetails? financialDetails}) = _$EditWageSeekerImpl;
  const _EditWageSeeker._() : super._();

  @override
  IndividualDetails? get individualDetails;
  @override
  SkillDetails? get skillDetails;
  @override
  LocationDetails? get locationDetails;
  @override
  FinancialDetails? get financialDetails;
  @override
  @JsonKey(ignore: true)
  _$$EditWageSeekerImplCopyWith<_$EditWageSeekerImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
