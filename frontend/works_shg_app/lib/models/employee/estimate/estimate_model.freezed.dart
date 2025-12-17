// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'estimate_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

EstimateDetailResponse _$EstimateDetailResponseFromJson(
    Map<String, dynamic> json) {
  return _EstimateDetailResponse.fromJson(json);
}

/// @nodoc
mixin _$EstimateDetailResponse {
  @JsonKey(name: 'estimates')
  List<Estimate>? get estimates => throw _privateConstructorUsedError;
  @JsonKey(name: 'TotalCount')
  int? get totalCount => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $EstimateDetailResponseCopyWith<EstimateDetailResponse> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $EstimateDetailResponseCopyWith<$Res> {
  factory $EstimateDetailResponseCopyWith(EstimateDetailResponse value,
          $Res Function(EstimateDetailResponse) then) =
      _$EstimateDetailResponseCopyWithImpl<$Res, EstimateDetailResponse>;
  @useResult
  $Res call(
      {@JsonKey(name: 'estimates') List<Estimate>? estimates,
      @JsonKey(name: 'TotalCount') int? totalCount});
}

/// @nodoc
class _$EstimateDetailResponseCopyWithImpl<$Res,
        $Val extends EstimateDetailResponse>
    implements $EstimateDetailResponseCopyWith<$Res> {
  _$EstimateDetailResponseCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? estimates = freezed,
    Object? totalCount = freezed,
  }) {
    return _then(_value.copyWith(
      estimates: freezed == estimates
          ? _value.estimates
          : estimates // ignore: cast_nullable_to_non_nullable
              as List<Estimate>?,
      totalCount: freezed == totalCount
          ? _value.totalCount
          : totalCount // ignore: cast_nullable_to_non_nullable
              as int?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$EstimateDetailResponseImplCopyWith<$Res>
    implements $EstimateDetailResponseCopyWith<$Res> {
  factory _$$EstimateDetailResponseImplCopyWith(
          _$EstimateDetailResponseImpl value,
          $Res Function(_$EstimateDetailResponseImpl) then) =
      __$$EstimateDetailResponseImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'estimates') List<Estimate>? estimates,
      @JsonKey(name: 'TotalCount') int? totalCount});
}

/// @nodoc
class __$$EstimateDetailResponseImplCopyWithImpl<$Res>
    extends _$EstimateDetailResponseCopyWithImpl<$Res,
        _$EstimateDetailResponseImpl>
    implements _$$EstimateDetailResponseImplCopyWith<$Res> {
  __$$EstimateDetailResponseImplCopyWithImpl(
      _$EstimateDetailResponseImpl _value,
      $Res Function(_$EstimateDetailResponseImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? estimates = freezed,
    Object? totalCount = freezed,
  }) {
    return _then(_$EstimateDetailResponseImpl(
      estimates: freezed == estimates
          ? _value._estimates
          : estimates // ignore: cast_nullable_to_non_nullable
              as List<Estimate>?,
      totalCount: freezed == totalCount
          ? _value.totalCount
          : totalCount // ignore: cast_nullable_to_non_nullable
              as int?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$EstimateDetailResponseImpl implements _EstimateDetailResponse {
  const _$EstimateDetailResponseImpl(
      {@JsonKey(name: 'estimates') final List<Estimate>? estimates,
      @JsonKey(name: 'TotalCount') this.totalCount})
      : _estimates = estimates;

  factory _$EstimateDetailResponseImpl.fromJson(Map<String, dynamic> json) =>
      _$$EstimateDetailResponseImplFromJson(json);

  final List<Estimate>? _estimates;
  @override
  @JsonKey(name: 'estimates')
  List<Estimate>? get estimates {
    final value = _estimates;
    if (value == null) return null;
    if (_estimates is EqualUnmodifiableListView) return _estimates;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  @JsonKey(name: 'TotalCount')
  final int? totalCount;

  @override
  String toString() {
    return 'EstimateDetailResponse(estimates: $estimates, totalCount: $totalCount)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$EstimateDetailResponseImpl &&
            const DeepCollectionEquality()
                .equals(other._estimates, _estimates) &&
            (identical(other.totalCount, totalCount) ||
                other.totalCount == totalCount));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_estimates), totalCount);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$EstimateDetailResponseImplCopyWith<_$EstimateDetailResponseImpl>
      get copyWith => __$$EstimateDetailResponseImplCopyWithImpl<
          _$EstimateDetailResponseImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$EstimateDetailResponseImplToJson(
      this,
    );
  }
}

abstract class _EstimateDetailResponse implements EstimateDetailResponse {
  const factory _EstimateDetailResponse(
          {@JsonKey(name: 'estimates') final List<Estimate>? estimates,
          @JsonKey(name: 'TotalCount') final int? totalCount}) =
      _$EstimateDetailResponseImpl;

  factory _EstimateDetailResponse.fromJson(Map<String, dynamic> json) =
      _$EstimateDetailResponseImpl.fromJson;

  @override
  @JsonKey(name: 'estimates')
  List<Estimate>? get estimates;
  @override
  @JsonKey(name: 'TotalCount')
  int? get totalCount;
  @override
  @JsonKey(ignore: true)
  _$$EstimateDetailResponseImplCopyWith<_$EstimateDetailResponseImpl>
      get copyWith => throw _privateConstructorUsedError;
}
