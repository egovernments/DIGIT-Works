// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'individual_search.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$IndividualSearchEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String mobileNumber) search,
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String mobileNumber)? search,
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String mobileNumber)? search,
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchIndividualEvent value) search,
    required TResult Function(SearchIndividualNameEvent value) nameSearch,
    required TResult Function(SearchIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchIndividualEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchIndividualEvent value)? search,
    TResult? Function(SearchIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchIndividualEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchIndividualEvent value)? search,
    TResult Function(SearchIndividualNameEvent value)? nameSearch,
    TResult Function(SearchIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchIndividualEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $IndividualSearchEventCopyWith<$Res> {
  factory $IndividualSearchEventCopyWith(IndividualSearchEvent value,
          $Res Function(IndividualSearchEvent) then) =
      _$IndividualSearchEventCopyWithImpl<$Res, IndividualSearchEvent>;
}

/// @nodoc
class _$IndividualSearchEventCopyWithImpl<$Res,
        $Val extends IndividualSearchEvent>
    implements $IndividualSearchEventCopyWith<$Res> {
  _$IndividualSearchEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$SearchIndividualEventImplCopyWith<$Res> {
  factory _$$SearchIndividualEventImplCopyWith(
          _$SearchIndividualEventImpl value,
          $Res Function(_$SearchIndividualEventImpl) then) =
      __$$SearchIndividualEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenant, String mobileNumber});
}

/// @nodoc
class __$$SearchIndividualEventImplCopyWithImpl<$Res>
    extends _$IndividualSearchEventCopyWithImpl<$Res,
        _$SearchIndividualEventImpl>
    implements _$$SearchIndividualEventImplCopyWith<$Res> {
  __$$SearchIndividualEventImplCopyWithImpl(_$SearchIndividualEventImpl _value,
      $Res Function(_$SearchIndividualEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenant = null,
    Object? mobileNumber = null,
  }) {
    return _then(_$SearchIndividualEventImpl(
      tenant: null == tenant
          ? _value.tenant
          : tenant // ignore: cast_nullable_to_non_nullable
              as String,
      mobileNumber: null == mobileNumber
          ? _value.mobileNumber
          : mobileNumber // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$SearchIndividualEventImpl
    with DiagnosticableTreeMixin
    implements SearchIndividualEvent {
  const _$SearchIndividualEventImpl(
      {required this.tenant, required this.mobileNumber});

  @override
  final String tenant;
  @override
  final String mobileNumber;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualSearchEvent.search(tenant: $tenant, mobileNumber: $mobileNumber)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualSearchEvent.search'))
      ..add(DiagnosticsProperty('tenant', tenant))
      ..add(DiagnosticsProperty('mobileNumber', mobileNumber));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualEventImpl &&
            (identical(other.tenant, tenant) || other.tenant == tenant) &&
            (identical(other.mobileNumber, mobileNumber) ||
                other.mobileNumber == mobileNumber));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenant, mobileNumber);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchIndividualEventImplCopyWith<_$SearchIndividualEventImpl>
      get copyWith => __$$SearchIndividualEventImplCopyWithImpl<
          _$SearchIndividualEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String mobileNumber) search,
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) {
    return search(tenant, mobileNumber);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String mobileNumber)? search,
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) {
    return search?.call(tenant, mobileNumber);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String mobileNumber)? search,
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(tenant, mobileNumber);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchIndividualEvent value) search,
    required TResult Function(SearchIndividualNameEvent value) nameSearch,
    required TResult Function(SearchIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchIndividualEvent value) dispose,
  }) {
    return search(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchIndividualEvent value)? search,
    TResult? Function(SearchIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchIndividualEvent value)? dispose,
  }) {
    return search?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchIndividualEvent value)? search,
    TResult Function(SearchIndividualNameEvent value)? nameSearch,
    TResult Function(SearchIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchIndividualEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(this);
    }
    return orElse();
  }
}

abstract class SearchIndividualEvent implements IndividualSearchEvent {
  const factory SearchIndividualEvent(
      {required final String tenant,
      required final String mobileNumber}) = _$SearchIndividualEventImpl;

  String get tenant;
  String get mobileNumber;
  @JsonKey(ignore: true)
  _$$SearchIndividualEventImplCopyWith<_$SearchIndividualEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$SearchIndividualNameEventImplCopyWith<$Res> {
  factory _$$SearchIndividualNameEventImplCopyWith(
          _$SearchIndividualNameEventImpl value,
          $Res Function(_$SearchIndividualNameEventImpl) then) =
      __$$SearchIndividualNameEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenant, String name});
}

/// @nodoc
class __$$SearchIndividualNameEventImplCopyWithImpl<$Res>
    extends _$IndividualSearchEventCopyWithImpl<$Res,
        _$SearchIndividualNameEventImpl>
    implements _$$SearchIndividualNameEventImplCopyWith<$Res> {
  __$$SearchIndividualNameEventImplCopyWithImpl(
      _$SearchIndividualNameEventImpl _value,
      $Res Function(_$SearchIndividualNameEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenant = null,
    Object? name = null,
  }) {
    return _then(_$SearchIndividualNameEventImpl(
      tenant: null == tenant
          ? _value.tenant
          : tenant // ignore: cast_nullable_to_non_nullable
              as String,
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$SearchIndividualNameEventImpl
    with DiagnosticableTreeMixin
    implements SearchIndividualNameEvent {
  const _$SearchIndividualNameEventImpl(
      {required this.tenant, required this.name});

  @override
  final String tenant;
  @override
  final String name;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualSearchEvent.nameSearch(tenant: $tenant, name: $name)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualSearchEvent.nameSearch'))
      ..add(DiagnosticsProperty('tenant', tenant))
      ..add(DiagnosticsProperty('name', name));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualNameEventImpl &&
            (identical(other.tenant, tenant) || other.tenant == tenant) &&
            (identical(other.name, name) || other.name == name));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenant, name);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchIndividualNameEventImplCopyWith<_$SearchIndividualNameEventImpl>
      get copyWith => __$$SearchIndividualNameEventImplCopyWithImpl<
          _$SearchIndividualNameEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String mobileNumber) search,
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) {
    return nameSearch(tenant, name);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String mobileNumber)? search,
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) {
    return nameSearch?.call(tenant, name);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String mobileNumber)? search,
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (nameSearch != null) {
      return nameSearch(tenant, name);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchIndividualEvent value) search,
    required TResult Function(SearchIndividualNameEvent value) nameSearch,
    required TResult Function(SearchIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchIndividualEvent value) dispose,
  }) {
    return nameSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchIndividualEvent value)? search,
    TResult? Function(SearchIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchIndividualEvent value)? dispose,
  }) {
    return nameSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchIndividualEvent value)? search,
    TResult Function(SearchIndividualNameEvent value)? nameSearch,
    TResult Function(SearchIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchIndividualEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (nameSearch != null) {
      return nameSearch(this);
    }
    return orElse();
  }
}

abstract class SearchIndividualNameEvent implements IndividualSearchEvent {
  const factory SearchIndividualNameEvent(
      {required final String tenant,
      required final String name}) = _$SearchIndividualNameEventImpl;

  String get tenant;
  String get name;
  @JsonKey(ignore: true)
  _$$SearchIndividualNameEventImplCopyWith<_$SearchIndividualNameEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$SearchIndividualIdEventImplCopyWith<$Res> {
  factory _$$SearchIndividualIdEventImplCopyWith(
          _$SearchIndividualIdEventImpl value,
          $Res Function(_$SearchIndividualIdEventImpl) then) =
      __$$SearchIndividualIdEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenant, List<String>? ids});
}

/// @nodoc
class __$$SearchIndividualIdEventImplCopyWithImpl<$Res>
    extends _$IndividualSearchEventCopyWithImpl<$Res,
        _$SearchIndividualIdEventImpl>
    implements _$$SearchIndividualIdEventImplCopyWith<$Res> {
  __$$SearchIndividualIdEventImplCopyWithImpl(
      _$SearchIndividualIdEventImpl _value,
      $Res Function(_$SearchIndividualIdEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenant = null,
    Object? ids = freezed,
  }) {
    return _then(_$SearchIndividualIdEventImpl(
      tenant: null == tenant
          ? _value.tenant
          : tenant // ignore: cast_nullable_to_non_nullable
              as String,
      ids: freezed == ids
          ? _value._ids
          : ids // ignore: cast_nullable_to_non_nullable
              as List<String>?,
    ));
  }
}

/// @nodoc

class _$SearchIndividualIdEventImpl
    with DiagnosticableTreeMixin
    implements SearchIndividualIdEvent {
  const _$SearchIndividualIdEventImpl(
      {required this.tenant, final List<String>? ids})
      : _ids = ids;

  @override
  final String tenant;
  final List<String>? _ids;
  @override
  List<String>? get ids {
    final value = _ids;
    if (value == null) return null;
    if (_ids is EqualUnmodifiableListView) return _ids;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualSearchEvent.idSearch(tenant: $tenant, ids: $ids)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualSearchEvent.idSearch'))
      ..add(DiagnosticsProperty('tenant', tenant))
      ..add(DiagnosticsProperty('ids', ids));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualIdEventImpl &&
            (identical(other.tenant, tenant) || other.tenant == tenant) &&
            const DeepCollectionEquality().equals(other._ids, _ids));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, tenant, const DeepCollectionEquality().hash(_ids));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchIndividualIdEventImplCopyWith<_$SearchIndividualIdEventImpl>
      get copyWith => __$$SearchIndividualIdEventImplCopyWithImpl<
          _$SearchIndividualIdEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String mobileNumber) search,
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) {
    return idSearch(tenant, ids);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String mobileNumber)? search,
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) {
    return idSearch?.call(tenant, ids);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String mobileNumber)? search,
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (idSearch != null) {
      return idSearch(tenant, ids);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchIndividualEvent value) search,
    required TResult Function(SearchIndividualNameEvent value) nameSearch,
    required TResult Function(SearchIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchIndividualEvent value) dispose,
  }) {
    return idSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchIndividualEvent value)? search,
    TResult? Function(SearchIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchIndividualEvent value)? dispose,
  }) {
    return idSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchIndividualEvent value)? search,
    TResult Function(SearchIndividualNameEvent value)? nameSearch,
    TResult Function(SearchIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchIndividualEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (idSearch != null) {
      return idSearch(this);
    }
    return orElse();
  }
}

abstract class SearchIndividualIdEvent implements IndividualSearchEvent {
  const factory SearchIndividualIdEvent(
      {required final String tenant,
      final List<String>? ids}) = _$SearchIndividualIdEventImpl;

  String get tenant;
  List<String>? get ids;
  @JsonKey(ignore: true)
  _$$SearchIndividualIdEventImplCopyWith<_$SearchIndividualIdEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeSearchIndividualEventImplCopyWith<$Res> {
  factory _$$DisposeSearchIndividualEventImplCopyWith(
          _$DisposeSearchIndividualEventImpl value,
          $Res Function(_$DisposeSearchIndividualEventImpl) then) =
      __$$DisposeSearchIndividualEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeSearchIndividualEventImplCopyWithImpl<$Res>
    extends _$IndividualSearchEventCopyWithImpl<$Res,
        _$DisposeSearchIndividualEventImpl>
    implements _$$DisposeSearchIndividualEventImplCopyWith<$Res> {
  __$$DisposeSearchIndividualEventImplCopyWithImpl(
      _$DisposeSearchIndividualEventImpl _value,
      $Res Function(_$DisposeSearchIndividualEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeSearchIndividualEventImpl
    with DiagnosticableTreeMixin
    implements DisposeSearchIndividualEvent {
  const _$DisposeSearchIndividualEventImpl();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualSearchEvent.dispose()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'IndividualSearchEvent.dispose'));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeSearchIndividualEventImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenant, String mobileNumber) search,
    required TResult Function(String tenant, String name) nameSearch,
    required TResult Function(String tenant, List<String>? ids) idSearch,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenant, String mobileNumber)? search,
    TResult? Function(String tenant, String name)? nameSearch,
    TResult? Function(String tenant, List<String>? ids)? idSearch,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenant, String mobileNumber)? search,
    TResult Function(String tenant, String name)? nameSearch,
    TResult Function(String tenant, List<String>? ids)? idSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchIndividualEvent value) search,
    required TResult Function(SearchIndividualNameEvent value) nameSearch,
    required TResult Function(SearchIndividualIdEvent value) idSearch,
    required TResult Function(DisposeSearchIndividualEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchIndividualEvent value)? search,
    TResult? Function(SearchIndividualNameEvent value)? nameSearch,
    TResult? Function(SearchIndividualIdEvent value)? idSearch,
    TResult? Function(DisposeSearchIndividualEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchIndividualEvent value)? search,
    TResult Function(SearchIndividualNameEvent value)? nameSearch,
    TResult Function(SearchIndividualIdEvent value)? idSearch,
    TResult Function(DisposeSearchIndividualEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class DisposeSearchIndividualEvent implements IndividualSearchEvent {
  const factory DisposeSearchIndividualEvent() =
      _$DisposeSearchIndividualEventImpl;
}

/// @nodoc
mixin _$IndividualSearchState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(IndividualListModel? individualListModel) loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(IndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(IndividualListModel? individualListModel)? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $IndividualSearchStateCopyWith<$Res> {
  factory $IndividualSearchStateCopyWith(IndividualSearchState value,
          $Res Function(IndividualSearchState) then) =
      _$IndividualSearchStateCopyWithImpl<$Res, IndividualSearchState>;
}

/// @nodoc
class _$IndividualSearchStateCopyWithImpl<$Res,
        $Val extends IndividualSearchState>
    implements $IndividualSearchStateCopyWith<$Res> {
  _$IndividualSearchStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$InitialImplCopyWith<$Res> {
  factory _$$InitialImplCopyWith(
          _$InitialImpl value, $Res Function(_$InitialImpl) then) =
      __$$InitialImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$InitialImplCopyWithImpl<$Res>
    extends _$IndividualSearchStateCopyWithImpl<$Res, _$InitialImpl>
    implements _$$InitialImplCopyWith<$Res> {
  __$$InitialImplCopyWithImpl(
      _$InitialImpl _value, $Res Function(_$InitialImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$InitialImpl extends _Initial with DiagnosticableTreeMixin {
  const _$InitialImpl() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualSearchState.initial()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'IndividualSearchState.initial'));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$InitialImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(IndividualListModel? individualListModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(IndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(IndividualListModel? individualListModel)? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return initial(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return initial?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial(this);
    }
    return orElse();
  }
}

abstract class _Initial extends IndividualSearchState {
  const factory _Initial() = _$InitialImpl;
  const _Initial._() : super._();
}

/// @nodoc
abstract class _$$LoadingImplCopyWith<$Res> {
  factory _$$LoadingImplCopyWith(
          _$LoadingImpl value, $Res Function(_$LoadingImpl) then) =
      __$$LoadingImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$LoadingImplCopyWithImpl<$Res>
    extends _$IndividualSearchStateCopyWithImpl<$Res, _$LoadingImpl>
    implements _$$LoadingImplCopyWith<$Res> {
  __$$LoadingImplCopyWithImpl(
      _$LoadingImpl _value, $Res Function(_$LoadingImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$LoadingImpl extends _Loading with DiagnosticableTreeMixin {
  const _$LoadingImpl() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualSearchState.loading()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'IndividualSearchState.loading'));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$LoadingImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(IndividualListModel? individualListModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(IndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(IndividualListModel? individualListModel)? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return loading(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return loading?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading(this);
    }
    return orElse();
  }
}

abstract class _Loading extends IndividualSearchState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({IndividualListModel? individualListModel});
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$IndividualSearchStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualListModel = freezed,
  }) {
    return _then(_$LoadedImpl(
      freezed == individualListModel
          ? _value.individualListModel
          : individualListModel // ignore: cast_nullable_to_non_nullable
              as IndividualListModel?,
    ));
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded with DiagnosticableTreeMixin {
  const _$LoadedImpl(this.individualListModel) : super._();

  @override
  final IndividualListModel? individualListModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualSearchState.loaded(individualListModel: $individualListModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualSearchState.loaded'))
      ..add(DiagnosticsProperty('individualListModel', individualListModel));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.individualListModel, individualListModel) ||
                other.individualListModel == individualListModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, individualListModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$LoadedImplCopyWith<_$LoadedImpl> get copyWith =>
      __$$LoadedImplCopyWithImpl<_$LoadedImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(IndividualListModel? individualListModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(individualListModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(IndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(individualListModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(IndividualListModel? individualListModel)? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(individualListModel);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return loaded(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return loaded?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(this);
    }
    return orElse();
  }
}

abstract class _Loaded extends IndividualSearchState {
  const factory _Loaded(final IndividualListModel? individualListModel) =
      _$LoadedImpl;
  const _Loaded._() : super._();

  IndividualListModel? get individualListModel;
  @JsonKey(ignore: true)
  _$$LoadedImplCopyWith<_$LoadedImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$ErrorImplCopyWith<$Res> {
  factory _$$ErrorImplCopyWith(
          _$ErrorImpl value, $Res Function(_$ErrorImpl) then) =
      __$$ErrorImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String? error});
}

/// @nodoc
class __$$ErrorImplCopyWithImpl<$Res>
    extends _$IndividualSearchStateCopyWithImpl<$Res, _$ErrorImpl>
    implements _$$ErrorImplCopyWith<$Res> {
  __$$ErrorImplCopyWithImpl(
      _$ErrorImpl _value, $Res Function(_$ErrorImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? error = freezed,
  }) {
    return _then(_$ErrorImpl(
      freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$ErrorImpl extends _Error with DiagnosticableTreeMixin {
  const _$ErrorImpl(this.error) : super._();

  @override
  final String? error;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualSearchState.error(error: $error)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'IndividualSearchState.error'))
      ..add(DiagnosticsProperty('error', error));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ErrorImpl &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(runtimeType, error);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      __$$ErrorImplCopyWithImpl<_$ErrorImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(IndividualListModel? individualListModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return error(this.error);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(IndividualListModel? individualListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(IndividualListModel? individualListModel)? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this.error);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return error(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return error?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this);
    }
    return orElse();
  }
}

abstract class _Error extends IndividualSearchState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
