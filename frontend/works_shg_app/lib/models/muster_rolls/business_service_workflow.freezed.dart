// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'business_service_workflow.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

BusinessServiceWorkflowModel _$BusinessServiceWorkflowModelFromJson(
    Map<String, dynamic> json) {
  return _BusinessServiceWorkflowModel.fromJson(json);
}

/// @nodoc
mixin _$BusinessServiceWorkflowModel {
  @JsonKey(name: 'BusinessServices')
  List<BusinessServices>? get businessServices =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $BusinessServiceWorkflowModelCopyWith<BusinessServiceWorkflowModel>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $BusinessServiceWorkflowModelCopyWith<$Res> {
  factory $BusinessServiceWorkflowModelCopyWith(
          BusinessServiceWorkflowModel value,
          $Res Function(BusinessServiceWorkflowModel) then) =
      _$BusinessServiceWorkflowModelCopyWithImpl<$Res,
          BusinessServiceWorkflowModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'BusinessServices')
          List<BusinessServices>? businessServices});
}

/// @nodoc
class _$BusinessServiceWorkflowModelCopyWithImpl<$Res,
        $Val extends BusinessServiceWorkflowModel>
    implements $BusinessServiceWorkflowModelCopyWith<$Res> {
  _$BusinessServiceWorkflowModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? businessServices = freezed,
  }) {
    return _then(_value.copyWith(
      businessServices: freezed == businessServices
          ? _value.businessServices
          : businessServices // ignore: cast_nullable_to_non_nullable
              as List<BusinessServices>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$BusinessServiceWorkflowModelImplCopyWith<$Res>
    implements $BusinessServiceWorkflowModelCopyWith<$Res> {
  factory _$$BusinessServiceWorkflowModelImplCopyWith(
          _$BusinessServiceWorkflowModelImpl value,
          $Res Function(_$BusinessServiceWorkflowModelImpl) then) =
      __$$BusinessServiceWorkflowModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'BusinessServices')
          List<BusinessServices>? businessServices});
}

/// @nodoc
class __$$BusinessServiceWorkflowModelImplCopyWithImpl<$Res>
    extends _$BusinessServiceWorkflowModelCopyWithImpl<$Res,
        _$BusinessServiceWorkflowModelImpl>
    implements _$$BusinessServiceWorkflowModelImplCopyWith<$Res> {
  __$$BusinessServiceWorkflowModelImplCopyWithImpl(
      _$BusinessServiceWorkflowModelImpl _value,
      $Res Function(_$BusinessServiceWorkflowModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? businessServices = freezed,
  }) {
    return _then(_$BusinessServiceWorkflowModelImpl(
      businessServices: freezed == businessServices
          ? _value._businessServices
          : businessServices // ignore: cast_nullable_to_non_nullable
              as List<BusinessServices>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$BusinessServiceWorkflowModelImpl
    implements _BusinessServiceWorkflowModel {
  const _$BusinessServiceWorkflowModelImpl(
      {@JsonKey(name: 'BusinessServices')
          final List<BusinessServices>? businessServices})
      : _businessServices = businessServices;

  factory _$BusinessServiceWorkflowModelImpl.fromJson(
          Map<String, dynamic> json) =>
      _$$BusinessServiceWorkflowModelImplFromJson(json);

  final List<BusinessServices>? _businessServices;
  @override
  @JsonKey(name: 'BusinessServices')
  List<BusinessServices>? get businessServices {
    final value = _businessServices;
    if (value == null) return null;
    if (_businessServices is EqualUnmodifiableListView)
      return _businessServices;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'BusinessServiceWorkflowModel(businessServices: $businessServices)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$BusinessServiceWorkflowModelImpl &&
            const DeepCollectionEquality()
                .equals(other._businessServices, _businessServices));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_businessServices));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$BusinessServiceWorkflowModelImplCopyWith<
          _$BusinessServiceWorkflowModelImpl>
      get copyWith => __$$BusinessServiceWorkflowModelImplCopyWithImpl<
          _$BusinessServiceWorkflowModelImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$BusinessServiceWorkflowModelImplToJson(
      this,
    );
  }
}

abstract class _BusinessServiceWorkflowModel
    implements BusinessServiceWorkflowModel {
  const factory _BusinessServiceWorkflowModel(
          {@JsonKey(name: 'BusinessServices')
              final List<BusinessServices>? businessServices}) =
      _$BusinessServiceWorkflowModelImpl;

  factory _BusinessServiceWorkflowModel.fromJson(Map<String, dynamic> json) =
      _$BusinessServiceWorkflowModelImpl.fromJson;

  @override
  @JsonKey(name: 'BusinessServices')
  List<BusinessServices>? get businessServices;
  @override
  @JsonKey(ignore: true)
  _$$BusinessServiceWorkflowModelImplCopyWith<
          _$BusinessServiceWorkflowModelImpl>
      get copyWith => throw _privateConstructorUsedError;
}

BusinessServices _$BusinessServicesFromJson(Map<String, dynamic> json) {
  return _BusinessServices.fromJson(json);
}

/// @nodoc
mixin _$BusinessServices {
  String get tenantId => throw _privateConstructorUsedError;
  String get uuid => throw _privateConstructorUsedError;
  String? get businessService => throw _privateConstructorUsedError;
  String? get business => throw _privateConstructorUsedError;
  int? get businessServiceSla => throw _privateConstructorUsedError;
  @JsonKey(name: 'states')
  List<BusinessWorkflowState>? get workflowState =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $BusinessServicesCopyWith<BusinessServices> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $BusinessServicesCopyWith<$Res> {
  factory $BusinessServicesCopyWith(
          BusinessServices value, $Res Function(BusinessServices) then) =
      _$BusinessServicesCopyWithImpl<$Res, BusinessServices>;
  @useResult
  $Res call(
      {String tenantId,
      String uuid,
      String? businessService,
      String? business,
      int? businessServiceSla,
      @JsonKey(name: 'states') List<BusinessWorkflowState>? workflowState});
}

/// @nodoc
class _$BusinessServicesCopyWithImpl<$Res, $Val extends BusinessServices>
    implements $BusinessServicesCopyWith<$Res> {
  _$BusinessServicesCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? uuid = null,
    Object? businessService = freezed,
    Object? business = freezed,
    Object? businessServiceSla = freezed,
    Object? workflowState = freezed,
  }) {
    return _then(_value.copyWith(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      uuid: null == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String,
      businessService: freezed == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String?,
      business: freezed == business
          ? _value.business
          : business // ignore: cast_nullable_to_non_nullable
              as String?,
      businessServiceSla: freezed == businessServiceSla
          ? _value.businessServiceSla
          : businessServiceSla // ignore: cast_nullable_to_non_nullable
              as int?,
      workflowState: freezed == workflowState
          ? _value.workflowState
          : workflowState // ignore: cast_nullable_to_non_nullable
              as List<BusinessWorkflowState>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$BusinessServicesImplCopyWith<$Res>
    implements $BusinessServicesCopyWith<$Res> {
  factory _$$BusinessServicesImplCopyWith(_$BusinessServicesImpl value,
          $Res Function(_$BusinessServicesImpl) then) =
      __$$BusinessServicesImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId,
      String uuid,
      String? businessService,
      String? business,
      int? businessServiceSla,
      @JsonKey(name: 'states') List<BusinessWorkflowState>? workflowState});
}

/// @nodoc
class __$$BusinessServicesImplCopyWithImpl<$Res>
    extends _$BusinessServicesCopyWithImpl<$Res, _$BusinessServicesImpl>
    implements _$$BusinessServicesImplCopyWith<$Res> {
  __$$BusinessServicesImplCopyWithImpl(_$BusinessServicesImpl _value,
      $Res Function(_$BusinessServicesImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? uuid = null,
    Object? businessService = freezed,
    Object? business = freezed,
    Object? businessServiceSla = freezed,
    Object? workflowState = freezed,
  }) {
    return _then(_$BusinessServicesImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      uuid: null == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String,
      businessService: freezed == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String?,
      business: freezed == business
          ? _value.business
          : business // ignore: cast_nullable_to_non_nullable
              as String?,
      businessServiceSla: freezed == businessServiceSla
          ? _value.businessServiceSla
          : businessServiceSla // ignore: cast_nullable_to_non_nullable
              as int?,
      workflowState: freezed == workflowState
          ? _value._workflowState
          : workflowState // ignore: cast_nullable_to_non_nullable
              as List<BusinessWorkflowState>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$BusinessServicesImpl implements _BusinessServices {
  const _$BusinessServicesImpl(
      {required this.tenantId,
      required this.uuid,
      this.businessService,
      this.business,
      this.businessServiceSla,
      @JsonKey(name: 'states')
          final List<BusinessWorkflowState>? workflowState})
      : _workflowState = workflowState;

  factory _$BusinessServicesImpl.fromJson(Map<String, dynamic> json) =>
      _$$BusinessServicesImplFromJson(json);

  @override
  final String tenantId;
  @override
  final String uuid;
  @override
  final String? businessService;
  @override
  final String? business;
  @override
  final int? businessServiceSla;
  final List<BusinessWorkflowState>? _workflowState;
  @override
  @JsonKey(name: 'states')
  List<BusinessWorkflowState>? get workflowState {
    final value = _workflowState;
    if (value == null) return null;
    if (_workflowState is EqualUnmodifiableListView) return _workflowState;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'BusinessServices(tenantId: $tenantId, uuid: $uuid, businessService: $businessService, business: $business, businessServiceSla: $businessServiceSla, workflowState: $workflowState)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$BusinessServicesImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.uuid, uuid) || other.uuid == uuid) &&
            (identical(other.businessService, businessService) ||
                other.businessService == businessService) &&
            (identical(other.business, business) ||
                other.business == business) &&
            (identical(other.businessServiceSla, businessServiceSla) ||
                other.businessServiceSla == businessServiceSla) &&
            const DeepCollectionEquality()
                .equals(other._workflowState, _workflowState));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      tenantId,
      uuid,
      businessService,
      business,
      businessServiceSla,
      const DeepCollectionEquality().hash(_workflowState));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$BusinessServicesImplCopyWith<_$BusinessServicesImpl> get copyWith =>
      __$$BusinessServicesImplCopyWithImpl<_$BusinessServicesImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$BusinessServicesImplToJson(
      this,
    );
  }
}

abstract class _BusinessServices implements BusinessServices {
  const factory _BusinessServices(
          {required final String tenantId,
          required final String uuid,
          final String? businessService,
          final String? business,
          final int? businessServiceSla,
          @JsonKey(name: 'states')
              final List<BusinessWorkflowState>? workflowState}) =
      _$BusinessServicesImpl;

  factory _BusinessServices.fromJson(Map<String, dynamic> json) =
      _$BusinessServicesImpl.fromJson;

  @override
  String get tenantId;
  @override
  String get uuid;
  @override
  String? get businessService;
  @override
  String? get business;
  @override
  int? get businessServiceSla;
  @override
  @JsonKey(name: 'states')
  List<BusinessWorkflowState>? get workflowState;
  @override
  @JsonKey(ignore: true)
  _$$BusinessServicesImplCopyWith<_$BusinessServicesImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

BusinessWorkflowState _$BusinessWorkflowStateFromJson(
    Map<String, dynamic> json) {
  return _BusinessWorkflowState.fromJson(json);
}

/// @nodoc
mixin _$BusinessWorkflowState {
  String get tenantId => throw _privateConstructorUsedError;
  String? get businessServiceId => throw _privateConstructorUsedError;
  String? get applicationStatus => throw _privateConstructorUsedError;
  String? get state => throw _privateConstructorUsedError;
  bool? get isStartState => throw _privateConstructorUsedError;
  bool? get isTerminateState => throw _privateConstructorUsedError;
  bool? get isStateUpdatable => throw _privateConstructorUsedError;
  List<StateActions>? get actions => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $BusinessWorkflowStateCopyWith<BusinessWorkflowState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $BusinessWorkflowStateCopyWith<$Res> {
  factory $BusinessWorkflowStateCopyWith(BusinessWorkflowState value,
          $Res Function(BusinessWorkflowState) then) =
      _$BusinessWorkflowStateCopyWithImpl<$Res, BusinessWorkflowState>;
  @useResult
  $Res call(
      {String tenantId,
      String? businessServiceId,
      String? applicationStatus,
      String? state,
      bool? isStartState,
      bool? isTerminateState,
      bool? isStateUpdatable,
      List<StateActions>? actions});
}

/// @nodoc
class _$BusinessWorkflowStateCopyWithImpl<$Res,
        $Val extends BusinessWorkflowState>
    implements $BusinessWorkflowStateCopyWith<$Res> {
  _$BusinessWorkflowStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? businessServiceId = freezed,
    Object? applicationStatus = freezed,
    Object? state = freezed,
    Object? isStartState = freezed,
    Object? isTerminateState = freezed,
    Object? isStateUpdatable = freezed,
    Object? actions = freezed,
  }) {
    return _then(_value.copyWith(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      businessServiceId: freezed == businessServiceId
          ? _value.businessServiceId
          : businessServiceId // ignore: cast_nullable_to_non_nullable
              as String?,
      applicationStatus: freezed == applicationStatus
          ? _value.applicationStatus
          : applicationStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      state: freezed == state
          ? _value.state
          : state // ignore: cast_nullable_to_non_nullable
              as String?,
      isStartState: freezed == isStartState
          ? _value.isStartState
          : isStartState // ignore: cast_nullable_to_non_nullable
              as bool?,
      isTerminateState: freezed == isTerminateState
          ? _value.isTerminateState
          : isTerminateState // ignore: cast_nullable_to_non_nullable
              as bool?,
      isStateUpdatable: freezed == isStateUpdatable
          ? _value.isStateUpdatable
          : isStateUpdatable // ignore: cast_nullable_to_non_nullable
              as bool?,
      actions: freezed == actions
          ? _value.actions
          : actions // ignore: cast_nullable_to_non_nullable
              as List<StateActions>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$BusinessWorkflowStateImplCopyWith<$Res>
    implements $BusinessWorkflowStateCopyWith<$Res> {
  factory _$$BusinessWorkflowStateImplCopyWith(
          _$BusinessWorkflowStateImpl value,
          $Res Function(_$BusinessWorkflowStateImpl) then) =
      __$$BusinessWorkflowStateImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId,
      String? businessServiceId,
      String? applicationStatus,
      String? state,
      bool? isStartState,
      bool? isTerminateState,
      bool? isStateUpdatable,
      List<StateActions>? actions});
}

/// @nodoc
class __$$BusinessWorkflowStateImplCopyWithImpl<$Res>
    extends _$BusinessWorkflowStateCopyWithImpl<$Res,
        _$BusinessWorkflowStateImpl>
    implements _$$BusinessWorkflowStateImplCopyWith<$Res> {
  __$$BusinessWorkflowStateImplCopyWithImpl(_$BusinessWorkflowStateImpl _value,
      $Res Function(_$BusinessWorkflowStateImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? businessServiceId = freezed,
    Object? applicationStatus = freezed,
    Object? state = freezed,
    Object? isStartState = freezed,
    Object? isTerminateState = freezed,
    Object? isStateUpdatable = freezed,
    Object? actions = freezed,
  }) {
    return _then(_$BusinessWorkflowStateImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      businessServiceId: freezed == businessServiceId
          ? _value.businessServiceId
          : businessServiceId // ignore: cast_nullable_to_non_nullable
              as String?,
      applicationStatus: freezed == applicationStatus
          ? _value.applicationStatus
          : applicationStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      state: freezed == state
          ? _value.state
          : state // ignore: cast_nullable_to_non_nullable
              as String?,
      isStartState: freezed == isStartState
          ? _value.isStartState
          : isStartState // ignore: cast_nullable_to_non_nullable
              as bool?,
      isTerminateState: freezed == isTerminateState
          ? _value.isTerminateState
          : isTerminateState // ignore: cast_nullable_to_non_nullable
              as bool?,
      isStateUpdatable: freezed == isStateUpdatable
          ? _value.isStateUpdatable
          : isStateUpdatable // ignore: cast_nullable_to_non_nullable
              as bool?,
      actions: freezed == actions
          ? _value._actions
          : actions // ignore: cast_nullable_to_non_nullable
              as List<StateActions>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$BusinessWorkflowStateImpl implements _BusinessWorkflowState {
  const _$BusinessWorkflowStateImpl(
      {required this.tenantId,
      this.businessServiceId,
      this.applicationStatus,
      this.state,
      this.isStartState,
      this.isTerminateState,
      this.isStateUpdatable,
      final List<StateActions>? actions})
      : _actions = actions;

  factory _$BusinessWorkflowStateImpl.fromJson(Map<String, dynamic> json) =>
      _$$BusinessWorkflowStateImplFromJson(json);

  @override
  final String tenantId;
  @override
  final String? businessServiceId;
  @override
  final String? applicationStatus;
  @override
  final String? state;
  @override
  final bool? isStartState;
  @override
  final bool? isTerminateState;
  @override
  final bool? isStateUpdatable;
  final List<StateActions>? _actions;
  @override
  List<StateActions>? get actions {
    final value = _actions;
    if (value == null) return null;
    if (_actions is EqualUnmodifiableListView) return _actions;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'BusinessWorkflowState(tenantId: $tenantId, businessServiceId: $businessServiceId, applicationStatus: $applicationStatus, state: $state, isStartState: $isStartState, isTerminateState: $isTerminateState, isStateUpdatable: $isStateUpdatable, actions: $actions)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$BusinessWorkflowStateImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.businessServiceId, businessServiceId) ||
                other.businessServiceId == businessServiceId) &&
            (identical(other.applicationStatus, applicationStatus) ||
                other.applicationStatus == applicationStatus) &&
            (identical(other.state, state) || other.state == state) &&
            (identical(other.isStartState, isStartState) ||
                other.isStartState == isStartState) &&
            (identical(other.isTerminateState, isTerminateState) ||
                other.isTerminateState == isTerminateState) &&
            (identical(other.isStateUpdatable, isStateUpdatable) ||
                other.isStateUpdatable == isStateUpdatable) &&
            const DeepCollectionEquality().equals(other._actions, _actions));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      tenantId,
      businessServiceId,
      applicationStatus,
      state,
      isStartState,
      isTerminateState,
      isStateUpdatable,
      const DeepCollectionEquality().hash(_actions));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$BusinessWorkflowStateImplCopyWith<_$BusinessWorkflowStateImpl>
      get copyWith => __$$BusinessWorkflowStateImplCopyWithImpl<
          _$BusinessWorkflowStateImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$BusinessWorkflowStateImplToJson(
      this,
    );
  }
}

abstract class _BusinessWorkflowState implements BusinessWorkflowState {
  const factory _BusinessWorkflowState(
      {required final String tenantId,
      final String? businessServiceId,
      final String? applicationStatus,
      final String? state,
      final bool? isStartState,
      final bool? isTerminateState,
      final bool? isStateUpdatable,
      final List<StateActions>? actions}) = _$BusinessWorkflowStateImpl;

  factory _BusinessWorkflowState.fromJson(Map<String, dynamic> json) =
      _$BusinessWorkflowStateImpl.fromJson;

  @override
  String get tenantId;
  @override
  String? get businessServiceId;
  @override
  String? get applicationStatus;
  @override
  String? get state;
  @override
  bool? get isStartState;
  @override
  bool? get isTerminateState;
  @override
  bool? get isStateUpdatable;
  @override
  List<StateActions>? get actions;
  @override
  @JsonKey(ignore: true)
  _$$BusinessWorkflowStateImplCopyWith<_$BusinessWorkflowStateImpl>
      get copyWith => throw _privateConstructorUsedError;
}

StateActions _$StateActionsFromJson(Map<String, dynamic> json) {
  return _StateActions.fromJson(json);
}

/// @nodoc
mixin _$StateActions {
  String get tenantId => throw _privateConstructorUsedError;
  String get uuid => throw _privateConstructorUsedError;
  String? get currentState => throw _privateConstructorUsedError;
  String? get action => throw _privateConstructorUsedError;
  String? get state => throw _privateConstructorUsedError;
  String? get nextState => throw _privateConstructorUsedError;
  List<String>? get roles => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $StateActionsCopyWith<StateActions> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $StateActionsCopyWith<$Res> {
  factory $StateActionsCopyWith(
          StateActions value, $Res Function(StateActions) then) =
      _$StateActionsCopyWithImpl<$Res, StateActions>;
  @useResult
  $Res call(
      {String tenantId,
      String uuid,
      String? currentState,
      String? action,
      String? state,
      String? nextState,
      List<String>? roles});
}

/// @nodoc
class _$StateActionsCopyWithImpl<$Res, $Val extends StateActions>
    implements $StateActionsCopyWith<$Res> {
  _$StateActionsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? uuid = null,
    Object? currentState = freezed,
    Object? action = freezed,
    Object? state = freezed,
    Object? nextState = freezed,
    Object? roles = freezed,
  }) {
    return _then(_value.copyWith(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      uuid: null == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String,
      currentState: freezed == currentState
          ? _value.currentState
          : currentState // ignore: cast_nullable_to_non_nullable
              as String?,
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
      state: freezed == state
          ? _value.state
          : state // ignore: cast_nullable_to_non_nullable
              as String?,
      nextState: freezed == nextState
          ? _value.nextState
          : nextState // ignore: cast_nullable_to_non_nullable
              as String?,
      roles: freezed == roles
          ? _value.roles
          : roles // ignore: cast_nullable_to_non_nullable
              as List<String>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$StateActionsImplCopyWith<$Res>
    implements $StateActionsCopyWith<$Res> {
  factory _$$StateActionsImplCopyWith(
          _$StateActionsImpl value, $Res Function(_$StateActionsImpl) then) =
      __$$StateActionsImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId,
      String uuid,
      String? currentState,
      String? action,
      String? state,
      String? nextState,
      List<String>? roles});
}

/// @nodoc
class __$$StateActionsImplCopyWithImpl<$Res>
    extends _$StateActionsCopyWithImpl<$Res, _$StateActionsImpl>
    implements _$$StateActionsImplCopyWith<$Res> {
  __$$StateActionsImplCopyWithImpl(
      _$StateActionsImpl _value, $Res Function(_$StateActionsImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? uuid = null,
    Object? currentState = freezed,
    Object? action = freezed,
    Object? state = freezed,
    Object? nextState = freezed,
    Object? roles = freezed,
  }) {
    return _then(_$StateActionsImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      uuid: null == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String,
      currentState: freezed == currentState
          ? _value.currentState
          : currentState // ignore: cast_nullable_to_non_nullable
              as String?,
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
      state: freezed == state
          ? _value.state
          : state // ignore: cast_nullable_to_non_nullable
              as String?,
      nextState: freezed == nextState
          ? _value.nextState
          : nextState // ignore: cast_nullable_to_non_nullable
              as String?,
      roles: freezed == roles
          ? _value._roles
          : roles // ignore: cast_nullable_to_non_nullable
              as List<String>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$StateActionsImpl implements _StateActions {
  const _$StateActionsImpl(
      {required this.tenantId,
      required this.uuid,
      this.currentState,
      this.action,
      this.state,
      this.nextState,
      final List<String>? roles})
      : _roles = roles;

  factory _$StateActionsImpl.fromJson(Map<String, dynamic> json) =>
      _$$StateActionsImplFromJson(json);

  @override
  final String tenantId;
  @override
  final String uuid;
  @override
  final String? currentState;
  @override
  final String? action;
  @override
  final String? state;
  @override
  final String? nextState;
  final List<String>? _roles;
  @override
  List<String>? get roles {
    final value = _roles;
    if (value == null) return null;
    if (_roles is EqualUnmodifiableListView) return _roles;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'StateActions(tenantId: $tenantId, uuid: $uuid, currentState: $currentState, action: $action, state: $state, nextState: $nextState, roles: $roles)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$StateActionsImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.uuid, uuid) || other.uuid == uuid) &&
            (identical(other.currentState, currentState) ||
                other.currentState == currentState) &&
            (identical(other.action, action) || other.action == action) &&
            (identical(other.state, state) || other.state == state) &&
            (identical(other.nextState, nextState) ||
                other.nextState == nextState) &&
            const DeepCollectionEquality().equals(other._roles, _roles));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, tenantId, uuid, currentState,
      action, state, nextState, const DeepCollectionEquality().hash(_roles));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$StateActionsImplCopyWith<_$StateActionsImpl> get copyWith =>
      __$$StateActionsImplCopyWithImpl<_$StateActionsImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$StateActionsImplToJson(
      this,
    );
  }
}

abstract class _StateActions implements StateActions {
  const factory _StateActions(
      {required final String tenantId,
      required final String uuid,
      final String? currentState,
      final String? action,
      final String? state,
      final String? nextState,
      final List<String>? roles}) = _$StateActionsImpl;

  factory _StateActions.fromJson(Map<String, dynamic> json) =
      _$StateActionsImpl.fromJson;

  @override
  String get tenantId;
  @override
  String get uuid;
  @override
  String? get currentState;
  @override
  String? get action;
  @override
  String? get state;
  @override
  String? get nextState;
  @override
  List<String>? get roles;
  @override
  @JsonKey(ignore: true)
  _$$StateActionsImplCopyWith<_$StateActionsImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
