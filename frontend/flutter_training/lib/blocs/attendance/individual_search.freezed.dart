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
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

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
abstract class _$$SearchIndividualEventCopyWith<$Res> {
  factory _$$SearchIndividualEventCopyWith(_$SearchIndividualEvent value,
          $Res Function(_$SearchIndividualEvent) then) =
      __$$SearchIndividualEventCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenant, String mobileNumber});
}

/// @nodoc
class __$$SearchIndividualEventCopyWithImpl<$Res>
    extends _$IndividualSearchEventCopyWithImpl<$Res, _$SearchIndividualEvent>
    implements _$$SearchIndividualEventCopyWith<$Res> {
  __$$SearchIndividualEventCopyWithImpl(_$SearchIndividualEvent _value,
      $Res Function(_$SearchIndividualEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenant = null,
    Object? mobileNumber = null,
  }) {
    return _then(_$SearchIndividualEvent(
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

class _$SearchIndividualEvent
    with DiagnosticableTreeMixin
    implements SearchIndividualEvent {
  const _$SearchIndividualEvent(
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
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualEvent &&
            (identical(other.tenant, tenant) || other.tenant == tenant) &&
            (identical(other.mobileNumber, mobileNumber) ||
                other.mobileNumber == mobileNumber));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenant, mobileNumber);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchIndividualEventCopyWith<_$SearchIndividualEvent> get copyWith =>
      __$$SearchIndividualEventCopyWithImpl<_$SearchIndividualEvent>(
          this, _$identity);

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
      required final String mobileNumber}) = _$SearchIndividualEvent;

  String get tenant;
  String get mobileNumber;
  @JsonKey(ignore: true)
  _$$SearchIndividualEventCopyWith<_$SearchIndividualEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$SearchIndividualNameEventCopyWith<$Res> {
  factory _$$SearchIndividualNameEventCopyWith(
          _$SearchIndividualNameEvent value,
          $Res Function(_$SearchIndividualNameEvent) then) =
      __$$SearchIndividualNameEventCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenant, String name});
}

/// @nodoc
class __$$SearchIndividualNameEventCopyWithImpl<$Res>
    extends _$IndividualSearchEventCopyWithImpl<$Res,
        _$SearchIndividualNameEvent>
    implements _$$SearchIndividualNameEventCopyWith<$Res> {
  __$$SearchIndividualNameEventCopyWithImpl(_$SearchIndividualNameEvent _value,
      $Res Function(_$SearchIndividualNameEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenant = null,
    Object? name = null,
  }) {
    return _then(_$SearchIndividualNameEvent(
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

class _$SearchIndividualNameEvent
    with DiagnosticableTreeMixin
    implements SearchIndividualNameEvent {
  const _$SearchIndividualNameEvent({required this.tenant, required this.name});

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
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualNameEvent &&
            (identical(other.tenant, tenant) || other.tenant == tenant) &&
            (identical(other.name, name) || other.name == name));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenant, name);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchIndividualNameEventCopyWith<_$SearchIndividualNameEvent>
      get copyWith => __$$SearchIndividualNameEventCopyWithImpl<
          _$SearchIndividualNameEvent>(this, _$identity);

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
      required final String name}) = _$SearchIndividualNameEvent;

  String get tenant;
  String get name;
  @JsonKey(ignore: true)
  _$$SearchIndividualNameEventCopyWith<_$SearchIndividualNameEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$SearchIndividualIdEventCopyWith<$Res> {
  factory _$$SearchIndividualIdEventCopyWith(_$SearchIndividualIdEvent value,
          $Res Function(_$SearchIndividualIdEvent) then) =
      __$$SearchIndividualIdEventCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenant, List<String>? ids});
}

/// @nodoc
class __$$SearchIndividualIdEventCopyWithImpl<$Res>
    extends _$IndividualSearchEventCopyWithImpl<$Res, _$SearchIndividualIdEvent>
    implements _$$SearchIndividualIdEventCopyWith<$Res> {
  __$$SearchIndividualIdEventCopyWithImpl(_$SearchIndividualIdEvent _value,
      $Res Function(_$SearchIndividualIdEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenant = null,
    Object? ids = freezed,
  }) {
    return _then(_$SearchIndividualIdEvent(
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

class _$SearchIndividualIdEvent
    with DiagnosticableTreeMixin
    implements SearchIndividualIdEvent {
  const _$SearchIndividualIdEvent(
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
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualIdEvent &&
            (identical(other.tenant, tenant) || other.tenant == tenant) &&
            const DeepCollectionEquality().equals(other._ids, _ids));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, tenant, const DeepCollectionEquality().hash(_ids));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchIndividualIdEventCopyWith<_$SearchIndividualIdEvent> get copyWith =>
      __$$SearchIndividualIdEventCopyWithImpl<_$SearchIndividualIdEvent>(
          this, _$identity);

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
      final List<String>? ids}) = _$SearchIndividualIdEvent;

  String get tenant;
  List<String>? get ids;
  @JsonKey(ignore: true)
  _$$SearchIndividualIdEventCopyWith<_$SearchIndividualIdEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeSearchIndividualEventCopyWith<$Res> {
  factory _$$DisposeSearchIndividualEventCopyWith(
          _$DisposeSearchIndividualEvent value,
          $Res Function(_$DisposeSearchIndividualEvent) then) =
      __$$DisposeSearchIndividualEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeSearchIndividualEventCopyWithImpl<$Res>
    extends _$IndividualSearchEventCopyWithImpl<$Res,
        _$DisposeSearchIndividualEvent>
    implements _$$DisposeSearchIndividualEventCopyWith<$Res> {
  __$$DisposeSearchIndividualEventCopyWithImpl(
      _$DisposeSearchIndividualEvent _value,
      $Res Function(_$DisposeSearchIndividualEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeSearchIndividualEvent
    with DiagnosticableTreeMixin
    implements DisposeSearchIndividualEvent {
  const _$DisposeSearchIndividualEvent();

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
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeSearchIndividualEvent);
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
  const factory DisposeSearchIndividualEvent() = _$DisposeSearchIndividualEvent;
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
abstract class _$$_InitialCopyWith<$Res> {
  factory _$$_InitialCopyWith(
          _$_Initial value, $Res Function(_$_Initial) then) =
      __$$_InitialCopyWithImpl<$Res>;
}

/// @nodoc
class __$$_InitialCopyWithImpl<$Res>
    extends _$IndividualSearchStateCopyWithImpl<$Res, _$_Initial>
    implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial with DiagnosticableTreeMixin {
  const _$_Initial() : super._();

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
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$_Initial);
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
  const factory _Initial() = _$_Initial;
  const _Initial._() : super._();
}

/// @nodoc
abstract class _$$_LoadingCopyWith<$Res> {
  factory _$$_LoadingCopyWith(
          _$_Loading value, $Res Function(_$_Loading) then) =
      __$$_LoadingCopyWithImpl<$Res>;
}

/// @nodoc
class __$$_LoadingCopyWithImpl<$Res>
    extends _$IndividualSearchStateCopyWithImpl<$Res, _$_Loading>
    implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading with DiagnosticableTreeMixin {
  const _$_Loading() : super._();

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
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$_Loading);
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
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_LoadedCopyWith<$Res> {
  factory _$$_LoadedCopyWith(_$_Loaded value, $Res Function(_$_Loaded) then) =
      __$$_LoadedCopyWithImpl<$Res>;
  @useResult
  $Res call({IndividualListModel? individualListModel});
}

/// @nodoc
class __$$_LoadedCopyWithImpl<$Res>
    extends _$IndividualSearchStateCopyWithImpl<$Res, _$_Loaded>
    implements _$$_LoadedCopyWith<$Res> {
  __$$_LoadedCopyWithImpl(_$_Loaded _value, $Res Function(_$_Loaded) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualListModel = freezed,
  }) {
    return _then(_$_Loaded(
      freezed == individualListModel
          ? _value.individualListModel
          : individualListModel // ignore: cast_nullable_to_non_nullable
              as IndividualListModel?,
    ));
  }
}

/// @nodoc

class _$_Loaded extends _Loaded with DiagnosticableTreeMixin {
  const _$_Loaded(this.individualListModel) : super._();

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
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Loaded &&
            (identical(other.individualListModel, individualListModel) ||
                other.individualListModel == individualListModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, individualListModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_LoadedCopyWith<_$_Loaded> get copyWith =>
      __$$_LoadedCopyWithImpl<_$_Loaded>(this, _$identity);

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
      _$_Loaded;
  const _Loaded._() : super._();

  IndividualListModel? get individualListModel;
  @JsonKey(ignore: true)
  _$$_LoadedCopyWith<_$_Loaded> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$_ErrorCopyWith<$Res> {
  factory _$$_ErrorCopyWith(_$_Error value, $Res Function(_$_Error) then) =
      __$$_ErrorCopyWithImpl<$Res>;
  @useResult
  $Res call({String? error});
}

/// @nodoc
class __$$_ErrorCopyWithImpl<$Res>
    extends _$IndividualSearchStateCopyWithImpl<$Res, _$_Error>
    implements _$$_ErrorCopyWith<$Res> {
  __$$_ErrorCopyWithImpl(_$_Error _value, $Res Function(_$_Error) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? error = freezed,
  }) {
    return _then(_$_Error(
      freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$_Error extends _Error with DiagnosticableTreeMixin {
  const _$_Error(this.error) : super._();

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
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Error &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(runtimeType, error);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      __$$_ErrorCopyWithImpl<_$_Error>(this, _$identity);

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
  const factory _Error(final String? error) = _$_Error;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      throw _privateConstructorUsedError;
}
