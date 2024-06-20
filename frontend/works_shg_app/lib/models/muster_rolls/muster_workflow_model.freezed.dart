// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'muster_workflow_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

MusterWorkFlowModel _$MusterWorkFlowModelFromJson(Map<String, dynamic> json) {
  return _MusterWorkFlowModel.fromJson(json);
}

/// @nodoc
mixin _$MusterWorkFlowModel {
  @JsonKey(name: 'ProcessInstances')
  List<ProcessInstances>? get processInstances =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MusterWorkFlowModelCopyWith<MusterWorkFlowModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterWorkFlowModelCopyWith<$Res> {
  factory $MusterWorkFlowModelCopyWith(
          MusterWorkFlowModel value, $Res Function(MusterWorkFlowModel) then) =
      _$MusterWorkFlowModelCopyWithImpl<$Res, MusterWorkFlowModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'ProcessInstances')
          List<ProcessInstances>? processInstances});
}

/// @nodoc
class _$MusterWorkFlowModelCopyWithImpl<$Res, $Val extends MusterWorkFlowModel>
    implements $MusterWorkFlowModelCopyWith<$Res> {
  _$MusterWorkFlowModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? processInstances = freezed,
  }) {
    return _then(_value.copyWith(
      processInstances: freezed == processInstances
          ? _value.processInstances
          : processInstances // ignore: cast_nullable_to_non_nullable
              as List<ProcessInstances>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_MusterWorkFlowModelCopyWith<$Res>
    implements $MusterWorkFlowModelCopyWith<$Res> {
  factory _$$_MusterWorkFlowModelCopyWith(_$_MusterWorkFlowModel value,
          $Res Function(_$_MusterWorkFlowModel) then) =
      __$$_MusterWorkFlowModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'ProcessInstances')
          List<ProcessInstances>? processInstances});
}

/// @nodoc
class __$$_MusterWorkFlowModelCopyWithImpl<$Res>
    extends _$MusterWorkFlowModelCopyWithImpl<$Res, _$_MusterWorkFlowModel>
    implements _$$_MusterWorkFlowModelCopyWith<$Res> {
  __$$_MusterWorkFlowModelCopyWithImpl(_$_MusterWorkFlowModel _value,
      $Res Function(_$_MusterWorkFlowModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? processInstances = freezed,
  }) {
    return _then(_$_MusterWorkFlowModel(
      processInstances: freezed == processInstances
          ? _value._processInstances
          : processInstances // ignore: cast_nullable_to_non_nullable
              as List<ProcessInstances>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_MusterWorkFlowModel implements _MusterWorkFlowModel {
  const _$_MusterWorkFlowModel(
      {@JsonKey(name: 'ProcessInstances')
          final List<ProcessInstances>? processInstances})
      : _processInstances = processInstances;

  factory _$_MusterWorkFlowModel.fromJson(Map<String, dynamic> json) =>
      _$$_MusterWorkFlowModelFromJson(json);

  final List<ProcessInstances>? _processInstances;
  @override
  @JsonKey(name: 'ProcessInstances')
  List<ProcessInstances>? get processInstances {
    final value = _processInstances;
    if (value == null) return null;
    if (_processInstances is EqualUnmodifiableListView)
      return _processInstances;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'MusterWorkFlowModel(processInstances: $processInstances)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MusterWorkFlowModel &&
            const DeepCollectionEquality()
                .equals(other._processInstances, _processInstances));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_processInstances));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MusterWorkFlowModelCopyWith<_$_MusterWorkFlowModel> get copyWith =>
      __$$_MusterWorkFlowModelCopyWithImpl<_$_MusterWorkFlowModel>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_MusterWorkFlowModelToJson(
      this,
    );
  }
}

abstract class _MusterWorkFlowModel implements MusterWorkFlowModel {
  const factory _MusterWorkFlowModel(
          {@JsonKey(name: 'ProcessInstances')
              final List<ProcessInstances>? processInstances}) =
      _$_MusterWorkFlowModel;

  factory _MusterWorkFlowModel.fromJson(Map<String, dynamic> json) =
      _$_MusterWorkFlowModel.fromJson;

  @override
  @JsonKey(name: 'ProcessInstances')
  List<ProcessInstances>? get processInstances;
  @override
  @JsonKey(ignore: true)
  _$$_MusterWorkFlowModelCopyWith<_$_MusterWorkFlowModel> get copyWith =>
      throw _privateConstructorUsedError;
}

ProcessInstances _$ProcessInstancesFromJson(Map<String, dynamic> json) {
  return _ProcessInstances.fromJson(json);
}

/// @nodoc
mixin _$ProcessInstances {
  String get tenantId => throw _privateConstructorUsedError;
  String? get businessService => throw _privateConstructorUsedError;
  String? get id => throw _privateConstructorUsedError;
  String? get businessId => throw _privateConstructorUsedError;
  String? get action => throw _privateConstructorUsedError;
  AuditDetails? get auditDetails => throw _privateConstructorUsedError;
  List<Assignees>? get assignes => throw _privateConstructorUsedError;
  List<WorkflowDocument>? get documents => throw _privateConstructorUsedError;
  String? get comment => throw _privateConstructorUsedError;
  List<NextActions>? get nextActions => throw _privateConstructorUsedError;
  @JsonKey(name: 'state')
  WorkflowState? get workflowState => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $ProcessInstancesCopyWith<ProcessInstances> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ProcessInstancesCopyWith<$Res> {
  factory $ProcessInstancesCopyWith(
          ProcessInstances value, $Res Function(ProcessInstances) then) =
      _$ProcessInstancesCopyWithImpl<$Res, ProcessInstances>;
  @useResult
  $Res call(
      {String tenantId,
      String? businessService,
      String? id,
      String? businessId,
      String? action,
      AuditDetails? auditDetails,
      List<Assignees>? assignes,
      List<WorkflowDocument>? documents,
      String? comment,
      List<NextActions>? nextActions,
      @JsonKey(name: 'state') WorkflowState? workflowState});

  $AuditDetailsCopyWith<$Res>? get auditDetails;
  $WorkflowStateCopyWith<$Res>? get workflowState;
}

/// @nodoc
class _$ProcessInstancesCopyWithImpl<$Res, $Val extends ProcessInstances>
    implements $ProcessInstancesCopyWith<$Res> {
  _$ProcessInstancesCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? businessService = freezed,
    Object? id = freezed,
    Object? businessId = freezed,
    Object? action = freezed,
    Object? auditDetails = freezed,
    Object? assignes = freezed,
    Object? documents = freezed,
    Object? comment = freezed,
    Object? nextActions = freezed,
    Object? workflowState = freezed,
  }) {
    return _then(_value.copyWith(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      businessService: freezed == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      businessId: freezed == businessId
          ? _value.businessId
          : businessId // ignore: cast_nullable_to_non_nullable
              as String?,
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
      assignes: freezed == assignes
          ? _value.assignes
          : assignes // ignore: cast_nullable_to_non_nullable
              as List<Assignees>?,
      documents: freezed == documents
          ? _value.documents
          : documents // ignore: cast_nullable_to_non_nullable
              as List<WorkflowDocument>?,
      comment: freezed == comment
          ? _value.comment
          : comment // ignore: cast_nullable_to_non_nullable
              as String?,
      nextActions: freezed == nextActions
          ? _value.nextActions
          : nextActions // ignore: cast_nullable_to_non_nullable
              as List<NextActions>?,
      workflowState: freezed == workflowState
          ? _value.workflowState
          : workflowState // ignore: cast_nullable_to_non_nullable
              as WorkflowState?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $AuditDetailsCopyWith<$Res>? get auditDetails {
    if (_value.auditDetails == null) {
      return null;
    }

    return $AuditDetailsCopyWith<$Res>(_value.auditDetails!, (value) {
      return _then(_value.copyWith(auditDetails: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $WorkflowStateCopyWith<$Res>? get workflowState {
    if (_value.workflowState == null) {
      return null;
    }

    return $WorkflowStateCopyWith<$Res>(_value.workflowState!, (value) {
      return _then(_value.copyWith(workflowState: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_ProcessInstancesCopyWith<$Res>
    implements $ProcessInstancesCopyWith<$Res> {
  factory _$$_ProcessInstancesCopyWith(
          _$_ProcessInstances value, $Res Function(_$_ProcessInstances) then) =
      __$$_ProcessInstancesCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId,
      String? businessService,
      String? id,
      String? businessId,
      String? action,
      AuditDetails? auditDetails,
      List<Assignees>? assignes,
      List<WorkflowDocument>? documents,
      String? comment,
      List<NextActions>? nextActions,
      @JsonKey(name: 'state') WorkflowState? workflowState});

  @override
  $AuditDetailsCopyWith<$Res>? get auditDetails;
  @override
  $WorkflowStateCopyWith<$Res>? get workflowState;
}

/// @nodoc
class __$$_ProcessInstancesCopyWithImpl<$Res>
    extends _$ProcessInstancesCopyWithImpl<$Res, _$_ProcessInstances>
    implements _$$_ProcessInstancesCopyWith<$Res> {
  __$$_ProcessInstancesCopyWithImpl(
      _$_ProcessInstances _value, $Res Function(_$_ProcessInstances) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? businessService = freezed,
    Object? id = freezed,
    Object? businessId = freezed,
    Object? action = freezed,
    Object? auditDetails = freezed,
    Object? assignes = freezed,
    Object? documents = freezed,
    Object? comment = freezed,
    Object? nextActions = freezed,
    Object? workflowState = freezed,
  }) {
    return _then(_$_ProcessInstances(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      businessService: freezed == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      businessId: freezed == businessId
          ? _value.businessId
          : businessId // ignore: cast_nullable_to_non_nullable
              as String?,
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
      assignes: freezed == assignes
          ? _value._assignes
          : assignes // ignore: cast_nullable_to_non_nullable
              as List<Assignees>?,
      documents: freezed == documents
          ? _value._documents
          : documents // ignore: cast_nullable_to_non_nullable
              as List<WorkflowDocument>?,
      comment: freezed == comment
          ? _value.comment
          : comment // ignore: cast_nullable_to_non_nullable
              as String?,
      nextActions: freezed == nextActions
          ? _value._nextActions
          : nextActions // ignore: cast_nullable_to_non_nullable
              as List<NextActions>?,
      workflowState: freezed == workflowState
          ? _value.workflowState
          : workflowState // ignore: cast_nullable_to_non_nullable
              as WorkflowState?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_ProcessInstances implements _ProcessInstances {
  const _$_ProcessInstances(
      {required this.tenantId,
      this.businessService,
      this.id,
      this.businessId,
      this.action,
      this.auditDetails,
      final List<Assignees>? assignes,
      final List<WorkflowDocument>? documents,
      this.comment,
      final List<NextActions>? nextActions,
      @JsonKey(name: 'state') this.workflowState})
      : _assignes = assignes,
        _documents = documents,
        _nextActions = nextActions;

  factory _$_ProcessInstances.fromJson(Map<String, dynamic> json) =>
      _$$_ProcessInstancesFromJson(json);

  @override
  final String tenantId;
  @override
  final String? businessService;
  @override
  final String? id;
  @override
  final String? businessId;
  @override
  final String? action;
  @override
  final AuditDetails? auditDetails;
  final List<Assignees>? _assignes;
  @override
  List<Assignees>? get assignes {
    final value = _assignes;
    if (value == null) return null;
    if (_assignes is EqualUnmodifiableListView) return _assignes;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<WorkflowDocument>? _documents;
  @override
  List<WorkflowDocument>? get documents {
    final value = _documents;
    if (value == null) return null;
    if (_documents is EqualUnmodifiableListView) return _documents;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  final String? comment;
  final List<NextActions>? _nextActions;
  @override
  List<NextActions>? get nextActions {
    final value = _nextActions;
    if (value == null) return null;
    if (_nextActions is EqualUnmodifiableListView) return _nextActions;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  @JsonKey(name: 'state')
  final WorkflowState? workflowState;

  @override
  String toString() {
    return 'ProcessInstances(tenantId: $tenantId, businessService: $businessService, id: $id, businessId: $businessId, action: $action, auditDetails: $auditDetails, assignes: $assignes, documents: $documents, comment: $comment, nextActions: $nextActions, workflowState: $workflowState)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_ProcessInstances &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.businessService, businessService) ||
                other.businessService == businessService) &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.businessId, businessId) ||
                other.businessId == businessId) &&
            (identical(other.action, action) || other.action == action) &&
            (identical(other.auditDetails, auditDetails) ||
                other.auditDetails == auditDetails) &&
            const DeepCollectionEquality().equals(other._assignes, _assignes) &&
            const DeepCollectionEquality()
                .equals(other._documents, _documents) &&
            (identical(other.comment, comment) || other.comment == comment) &&
            const DeepCollectionEquality()
                .equals(other._nextActions, _nextActions) &&
            (identical(other.workflowState, workflowState) ||
                other.workflowState == workflowState));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      tenantId,
      businessService,
      id,
      businessId,
      action,
      auditDetails,
      const DeepCollectionEquality().hash(_assignes),
      const DeepCollectionEquality().hash(_documents),
      comment,
      const DeepCollectionEquality().hash(_nextActions),
      workflowState);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_ProcessInstancesCopyWith<_$_ProcessInstances> get copyWith =>
      __$$_ProcessInstancesCopyWithImpl<_$_ProcessInstances>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_ProcessInstancesToJson(
      this,
    );
  }
}

abstract class _ProcessInstances implements ProcessInstances {
  const factory _ProcessInstances(
          {required final String tenantId,
          final String? businessService,
          final String? id,
          final String? businessId,
          final String? action,
          final AuditDetails? auditDetails,
          final List<Assignees>? assignes,
          final List<WorkflowDocument>? documents,
          final String? comment,
          final List<NextActions>? nextActions,
          @JsonKey(name: 'state') final WorkflowState? workflowState}) =
      _$_ProcessInstances;

  factory _ProcessInstances.fromJson(Map<String, dynamic> json) =
      _$_ProcessInstances.fromJson;

  @override
  String get tenantId;
  @override
  String? get businessService;
  @override
  String? get id;
  @override
  String? get businessId;
  @override
  String? get action;
  @override
  AuditDetails? get auditDetails;
  @override
  List<Assignees>? get assignes;
  @override
  List<WorkflowDocument>? get documents;
  @override
  String? get comment;
  @override
  List<NextActions>? get nextActions;
  @override
  @JsonKey(name: 'state')
  WorkflowState? get workflowState;
  @override
  @JsonKey(ignore: true)
  _$$_ProcessInstancesCopyWith<_$_ProcessInstances> get copyWith =>
      throw _privateConstructorUsedError;
}

NextActions _$NextActionsFromJson(Map<String, dynamic> json) {
  return _NextActions.fromJson(json);
}

/// @nodoc
mixin _$NextActions {
  String? get action => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $NextActionsCopyWith<NextActions> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $NextActionsCopyWith<$Res> {
  factory $NextActionsCopyWith(
          NextActions value, $Res Function(NextActions) then) =
      _$NextActionsCopyWithImpl<$Res, NextActions>;
  @useResult
  $Res call({String? action});
}

/// @nodoc
class _$NextActionsCopyWithImpl<$Res, $Val extends NextActions>
    implements $NextActionsCopyWith<$Res> {
  _$NextActionsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? action = freezed,
  }) {
    return _then(_value.copyWith(
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_NextActionsCopyWith<$Res>
    implements $NextActionsCopyWith<$Res> {
  factory _$$_NextActionsCopyWith(
          _$_NextActions value, $Res Function(_$_NextActions) then) =
      __$$_NextActionsCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? action});
}

/// @nodoc
class __$$_NextActionsCopyWithImpl<$Res>
    extends _$NextActionsCopyWithImpl<$Res, _$_NextActions>
    implements _$$_NextActionsCopyWith<$Res> {
  __$$_NextActionsCopyWithImpl(
      _$_NextActions _value, $Res Function(_$_NextActions) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? action = freezed,
  }) {
    return _then(_$_NextActions(
      action: freezed == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_NextActions implements _NextActions {
  const _$_NextActions({this.action});

  factory _$_NextActions.fromJson(Map<String, dynamic> json) =>
      _$$_NextActionsFromJson(json);

  @override
  final String? action;

  @override
  String toString() {
    return 'NextActions(action: $action)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_NextActions &&
            (identical(other.action, action) || other.action == action));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, action);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_NextActionsCopyWith<_$_NextActions> get copyWith =>
      __$$_NextActionsCopyWithImpl<_$_NextActions>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_NextActionsToJson(
      this,
    );
  }
}

abstract class _NextActions implements NextActions {
  const factory _NextActions({final String? action}) = _$_NextActions;

  factory _NextActions.fromJson(Map<String, dynamic> json) =
      _$_NextActions.fromJson;

  @override
  String? get action;
  @override
  @JsonKey(ignore: true)
  _$$_NextActionsCopyWith<_$_NextActions> get copyWith =>
      throw _privateConstructorUsedError;
}

WorkflowDocument _$WorkflowDocumentFromJson(Map<String, dynamic> json) {
  return _WorkflowDocument.fromJson(json);
}

/// @nodoc
mixin _$WorkflowDocument {
  String? get documentType => throw _privateConstructorUsedError;
  String? get documentUid => throw _privateConstructorUsedError;
  String? get fileStoreId => throw _privateConstructorUsedError;
  String? get id => throw _privateConstructorUsedError;
  String? get tenantId => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WorkflowDocumentCopyWith<WorkflowDocument> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WorkflowDocumentCopyWith<$Res> {
  factory $WorkflowDocumentCopyWith(
          WorkflowDocument value, $Res Function(WorkflowDocument) then) =
      _$WorkflowDocumentCopyWithImpl<$Res, WorkflowDocument>;
  @useResult
  $Res call(
      {String? documentType,
      String? documentUid,
      String? fileStoreId,
      String? id,
      String? tenantId});
}

/// @nodoc
class _$WorkflowDocumentCopyWithImpl<$Res, $Val extends WorkflowDocument>
    implements $WorkflowDocumentCopyWith<$Res> {
  _$WorkflowDocumentCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? documentType = freezed,
    Object? documentUid = freezed,
    Object? fileStoreId = freezed,
    Object? id = freezed,
    Object? tenantId = freezed,
  }) {
    return _then(_value.copyWith(
      documentType: freezed == documentType
          ? _value.documentType
          : documentType // ignore: cast_nullable_to_non_nullable
              as String?,
      documentUid: freezed == documentUid
          ? _value.documentUid
          : documentUid // ignore: cast_nullable_to_non_nullable
              as String?,
      fileStoreId: freezed == fileStoreId
          ? _value.fileStoreId
          : fileStoreId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_WorkflowDocumentCopyWith<$Res>
    implements $WorkflowDocumentCopyWith<$Res> {
  factory _$$_WorkflowDocumentCopyWith(
          _$_WorkflowDocument value, $Res Function(_$_WorkflowDocument) then) =
      __$$_WorkflowDocumentCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? documentType,
      String? documentUid,
      String? fileStoreId,
      String? id,
      String? tenantId});
}

/// @nodoc
class __$$_WorkflowDocumentCopyWithImpl<$Res>
    extends _$WorkflowDocumentCopyWithImpl<$Res, _$_WorkflowDocument>
    implements _$$_WorkflowDocumentCopyWith<$Res> {
  __$$_WorkflowDocumentCopyWithImpl(
      _$_WorkflowDocument _value, $Res Function(_$_WorkflowDocument) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? documentType = freezed,
    Object? documentUid = freezed,
    Object? fileStoreId = freezed,
    Object? id = freezed,
    Object? tenantId = freezed,
  }) {
    return _then(_$_WorkflowDocument(
      documentType: freezed == documentType
          ? _value.documentType
          : documentType // ignore: cast_nullable_to_non_nullable
              as String?,
      documentUid: freezed == documentUid
          ? _value.documentUid
          : documentUid // ignore: cast_nullable_to_non_nullable
              as String?,
      fileStoreId: freezed == fileStoreId
          ? _value.fileStoreId
          : fileStoreId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_WorkflowDocument implements _WorkflowDocument {
  const _$_WorkflowDocument(
      {this.documentType,
      this.documentUid,
      this.fileStoreId,
      this.id,
      this.tenantId});

  factory _$_WorkflowDocument.fromJson(Map<String, dynamic> json) =>
      _$$_WorkflowDocumentFromJson(json);

  @override
  final String? documentType;
  @override
  final String? documentUid;
  @override
  final String? fileStoreId;
  @override
  final String? id;
  @override
  final String? tenantId;

  @override
  String toString() {
    return 'WorkflowDocument(documentType: $documentType, documentUid: $documentUid, fileStoreId: $fileStoreId, id: $id, tenantId: $tenantId)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_WorkflowDocument &&
            (identical(other.documentType, documentType) ||
                other.documentType == documentType) &&
            (identical(other.documentUid, documentUid) ||
                other.documentUid == documentUid) &&
            (identical(other.fileStoreId, fileStoreId) ||
                other.fileStoreId == fileStoreId) &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, documentType, documentUid, fileStoreId, id, tenantId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_WorkflowDocumentCopyWith<_$_WorkflowDocument> get copyWith =>
      __$$_WorkflowDocumentCopyWithImpl<_$_WorkflowDocument>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_WorkflowDocumentToJson(
      this,
    );
  }
}

abstract class _WorkflowDocument implements WorkflowDocument {
  const factory _WorkflowDocument(
      {final String? documentType,
      final String? documentUid,
      final String? fileStoreId,
      final String? id,
      final String? tenantId}) = _$_WorkflowDocument;

  factory _WorkflowDocument.fromJson(Map<String, dynamic> json) =
      _$_WorkflowDocument.fromJson;

  @override
  String? get documentType;
  @override
  String? get documentUid;
  @override
  String? get fileStoreId;
  @override
  String? get id;
  @override
  String? get tenantId;
  @override
  @JsonKey(ignore: true)
  _$$_WorkflowDocumentCopyWith<_$_WorkflowDocument> get copyWith =>
      throw _privateConstructorUsedError;
}

Assignees _$AssigneesFromJson(Map<String, dynamic> json) {
  return _Assignees.fromJson(json);
}

/// @nodoc
mixin _$Assignees {
  String? get emailId => throw _privateConstructorUsedError;
  int? get id => throw _privateConstructorUsedError;
  String? get mobileNumber => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;
  String? get tenantId => throw _privateConstructorUsedError;
  String? get uuid => throw _privateConstructorUsedError;
  String? get userName => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AssigneesCopyWith<Assignees> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AssigneesCopyWith<$Res> {
  factory $AssigneesCopyWith(Assignees value, $Res Function(Assignees) then) =
      _$AssigneesCopyWithImpl<$Res, Assignees>;
  @useResult
  $Res call(
      {String? emailId,
      int? id,
      String? mobileNumber,
      String? name,
      String? tenantId,
      String? uuid,
      String? userName});
}

/// @nodoc
class _$AssigneesCopyWithImpl<$Res, $Val extends Assignees>
    implements $AssigneesCopyWith<$Res> {
  _$AssigneesCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? emailId = freezed,
    Object? id = freezed,
    Object? mobileNumber = freezed,
    Object? name = freezed,
    Object? tenantId = freezed,
    Object? uuid = freezed,
    Object? userName = freezed,
  }) {
    return _then(_value.copyWith(
      emailId: freezed == emailId
          ? _value.emailId
          : emailId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int?,
      mobileNumber: freezed == mobileNumber
          ? _value.mobileNumber
          : mobileNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      uuid: freezed == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String?,
      userName: freezed == userName
          ? _value.userName
          : userName // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AssigneesCopyWith<$Res> implements $AssigneesCopyWith<$Res> {
  factory _$$_AssigneesCopyWith(
          _$_Assignees value, $Res Function(_$_Assignees) then) =
      __$$_AssigneesCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? emailId,
      int? id,
      String? mobileNumber,
      String? name,
      String? tenantId,
      String? uuid,
      String? userName});
}

/// @nodoc
class __$$_AssigneesCopyWithImpl<$Res>
    extends _$AssigneesCopyWithImpl<$Res, _$_Assignees>
    implements _$$_AssigneesCopyWith<$Res> {
  __$$_AssigneesCopyWithImpl(
      _$_Assignees _value, $Res Function(_$_Assignees) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? emailId = freezed,
    Object? id = freezed,
    Object? mobileNumber = freezed,
    Object? name = freezed,
    Object? tenantId = freezed,
    Object? uuid = freezed,
    Object? userName = freezed,
  }) {
    return _then(_$_Assignees(
      emailId: freezed == emailId
          ? _value.emailId
          : emailId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int?,
      mobileNumber: freezed == mobileNumber
          ? _value.mobileNumber
          : mobileNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      uuid: freezed == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String?,
      userName: freezed == userName
          ? _value.userName
          : userName // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_Assignees implements _Assignees {
  const _$_Assignees(
      {this.emailId,
      this.id,
      this.mobileNumber,
      this.name,
      this.tenantId,
      this.uuid,
      this.userName});

  factory _$_Assignees.fromJson(Map<String, dynamic> json) =>
      _$$_AssigneesFromJson(json);

  @override
  final String? emailId;
  @override
  final int? id;
  @override
  final String? mobileNumber;
  @override
  final String? name;
  @override
  final String? tenantId;
  @override
  final String? uuid;
  @override
  final String? userName;

  @override
  String toString() {
    return 'Assignees(emailId: $emailId, id: $id, mobileNumber: $mobileNumber, name: $name, tenantId: $tenantId, uuid: $uuid, userName: $userName)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Assignees &&
            (identical(other.emailId, emailId) || other.emailId == emailId) &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.mobileNumber, mobileNumber) ||
                other.mobileNumber == mobileNumber) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.uuid, uuid) || other.uuid == uuid) &&
            (identical(other.userName, userName) ||
                other.userName == userName));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, emailId, id, mobileNumber, name, tenantId, uuid, userName);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AssigneesCopyWith<_$_Assignees> get copyWith =>
      __$$_AssigneesCopyWithImpl<_$_Assignees>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AssigneesToJson(
      this,
    );
  }
}

abstract class _Assignees implements Assignees {
  const factory _Assignees(
      {final String? emailId,
      final int? id,
      final String? mobileNumber,
      final String? name,
      final String? tenantId,
      final String? uuid,
      final String? userName}) = _$_Assignees;

  factory _Assignees.fromJson(Map<String, dynamic> json) =
      _$_Assignees.fromJson;

  @override
  String? get emailId;
  @override
  int? get id;
  @override
  String? get mobileNumber;
  @override
  String? get name;
  @override
  String? get tenantId;
  @override
  String? get uuid;
  @override
  String? get userName;
  @override
  @JsonKey(ignore: true)
  _$$_AssigneesCopyWith<_$_Assignees> get copyWith =>
      throw _privateConstructorUsedError;
}

WorkflowState _$WorkflowStateFromJson(Map<String, dynamic> json) {
  return _WorkflowState.fromJson(json);
}

/// @nodoc
mixin _$WorkflowState {
  String get tenantId => throw _privateConstructorUsedError;
  String? get businessServiceId => throw _privateConstructorUsedError;
  String? get applicationStatus => throw _privateConstructorUsedError;
  String? get state => throw _privateConstructorUsedError;
  bool? get isStartState => throw _privateConstructorUsedError;
  bool? get isTerminateState => throw _privateConstructorUsedError;
  List<WorkflowActions>? get actions => throw _privateConstructorUsedError;
  bool? get isStateUpdatable => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WorkflowStateCopyWith<WorkflowState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WorkflowStateCopyWith<$Res> {
  factory $WorkflowStateCopyWith(
          WorkflowState value, $Res Function(WorkflowState) then) =
      _$WorkflowStateCopyWithImpl<$Res, WorkflowState>;
  @useResult
  $Res call(
      {String tenantId,
      String? businessServiceId,
      String? applicationStatus,
      String? state,
      bool? isStartState,
      bool? isTerminateState,
      List<WorkflowActions>? actions,
      bool? isStateUpdatable});
}

/// @nodoc
class _$WorkflowStateCopyWithImpl<$Res, $Val extends WorkflowState>
    implements $WorkflowStateCopyWith<$Res> {
  _$WorkflowStateCopyWithImpl(this._value, this._then);

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
    Object? actions = freezed,
    Object? isStateUpdatable = freezed,
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
      actions: freezed == actions
          ? _value.actions
          : actions // ignore: cast_nullable_to_non_nullable
              as List<WorkflowActions>?,
      isStateUpdatable: freezed == isStateUpdatable
          ? _value.isStateUpdatable
          : isStateUpdatable // ignore: cast_nullable_to_non_nullable
              as bool?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_WorkflowStateCopyWith<$Res>
    implements $WorkflowStateCopyWith<$Res> {
  factory _$$_WorkflowStateCopyWith(
          _$_WorkflowState value, $Res Function(_$_WorkflowState) then) =
      __$$_WorkflowStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId,
      String? businessServiceId,
      String? applicationStatus,
      String? state,
      bool? isStartState,
      bool? isTerminateState,
      List<WorkflowActions>? actions,
      bool? isStateUpdatable});
}

/// @nodoc
class __$$_WorkflowStateCopyWithImpl<$Res>
    extends _$WorkflowStateCopyWithImpl<$Res, _$_WorkflowState>
    implements _$$_WorkflowStateCopyWith<$Res> {
  __$$_WorkflowStateCopyWithImpl(
      _$_WorkflowState _value, $Res Function(_$_WorkflowState) _then)
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
    Object? actions = freezed,
    Object? isStateUpdatable = freezed,
  }) {
    return _then(_$_WorkflowState(
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
      actions: freezed == actions
          ? _value._actions
          : actions // ignore: cast_nullable_to_non_nullable
              as List<WorkflowActions>?,
      isStateUpdatable: freezed == isStateUpdatable
          ? _value.isStateUpdatable
          : isStateUpdatable // ignore: cast_nullable_to_non_nullable
              as bool?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_WorkflowState implements _WorkflowState {
  const _$_WorkflowState(
      {required this.tenantId,
      this.businessServiceId,
      this.applicationStatus,
      this.state,
      this.isStartState,
      this.isTerminateState,
      final List<WorkflowActions>? actions,
      this.isStateUpdatable})
      : _actions = actions;

  factory _$_WorkflowState.fromJson(Map<String, dynamic> json) =>
      _$$_WorkflowStateFromJson(json);

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
  final List<WorkflowActions>? _actions;
  @override
  List<WorkflowActions>? get actions {
    final value = _actions;
    if (value == null) return null;
    if (_actions is EqualUnmodifiableListView) return _actions;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  final bool? isStateUpdatable;

  @override
  String toString() {
    return 'WorkflowState(tenantId: $tenantId, businessServiceId: $businessServiceId, applicationStatus: $applicationStatus, state: $state, isStartState: $isStartState, isTerminateState: $isTerminateState, actions: $actions, isStateUpdatable: $isStateUpdatable)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_WorkflowState &&
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
            const DeepCollectionEquality().equals(other._actions, _actions) &&
            (identical(other.isStateUpdatable, isStateUpdatable) ||
                other.isStateUpdatable == isStateUpdatable));
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
      const DeepCollectionEquality().hash(_actions),
      isStateUpdatable);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_WorkflowStateCopyWith<_$_WorkflowState> get copyWith =>
      __$$_WorkflowStateCopyWithImpl<_$_WorkflowState>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_WorkflowStateToJson(
      this,
    );
  }
}

abstract class _WorkflowState implements WorkflowState {
  const factory _WorkflowState(
      {required final String tenantId,
      final String? businessServiceId,
      final String? applicationStatus,
      final String? state,
      final bool? isStartState,
      final bool? isTerminateState,
      final List<WorkflowActions>? actions,
      final bool? isStateUpdatable}) = _$_WorkflowState;

  factory _WorkflowState.fromJson(Map<String, dynamic> json) =
      _$_WorkflowState.fromJson;

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
  List<WorkflowActions>? get actions;
  @override
  bool? get isStateUpdatable;
  @override
  @JsonKey(ignore: true)
  _$$_WorkflowStateCopyWith<_$_WorkflowState> get copyWith =>
      throw _privateConstructorUsedError;
}

WorkflowActions _$WorkflowActionsFromJson(Map<String, dynamic> json) {
  return _WorkflowActions.fromJson(json);
}

/// @nodoc
mixin _$WorkflowActions {
  List<String>? get roles => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $WorkflowActionsCopyWith<WorkflowActions> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WorkflowActionsCopyWith<$Res> {
  factory $WorkflowActionsCopyWith(
          WorkflowActions value, $Res Function(WorkflowActions) then) =
      _$WorkflowActionsCopyWithImpl<$Res, WorkflowActions>;
  @useResult
  $Res call({List<String>? roles});
}

/// @nodoc
class _$WorkflowActionsCopyWithImpl<$Res, $Val extends WorkflowActions>
    implements $WorkflowActionsCopyWith<$Res> {
  _$WorkflowActionsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? roles = freezed,
  }) {
    return _then(_value.copyWith(
      roles: freezed == roles
          ? _value.roles
          : roles // ignore: cast_nullable_to_non_nullable
              as List<String>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_WorkflowActionsCopyWith<$Res>
    implements $WorkflowActionsCopyWith<$Res> {
  factory _$$_WorkflowActionsCopyWith(
          _$_WorkflowActions value, $Res Function(_$_WorkflowActions) then) =
      __$$_WorkflowActionsCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({List<String>? roles});
}

/// @nodoc
class __$$_WorkflowActionsCopyWithImpl<$Res>
    extends _$WorkflowActionsCopyWithImpl<$Res, _$_WorkflowActions>
    implements _$$_WorkflowActionsCopyWith<$Res> {
  __$$_WorkflowActionsCopyWithImpl(
      _$_WorkflowActions _value, $Res Function(_$_WorkflowActions) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? roles = freezed,
  }) {
    return _then(_$_WorkflowActions(
      roles: freezed == roles
          ? _value._roles
          : roles // ignore: cast_nullable_to_non_nullable
              as List<String>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_WorkflowActions implements _WorkflowActions {
  const _$_WorkflowActions({final List<String>? roles}) : _roles = roles;

  factory _$_WorkflowActions.fromJson(Map<String, dynamic> json) =>
      _$$_WorkflowActionsFromJson(json);

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
    return 'WorkflowActions(roles: $roles)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_WorkflowActions &&
            const DeepCollectionEquality().equals(other._roles, _roles));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, const DeepCollectionEquality().hash(_roles));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_WorkflowActionsCopyWith<_$_WorkflowActions> get copyWith =>
      __$$_WorkflowActionsCopyWithImpl<_$_WorkflowActions>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_WorkflowActionsToJson(
      this,
    );
  }
}

abstract class _WorkflowActions implements WorkflowActions {
  const factory _WorkflowActions({final List<String>? roles}) =
      _$_WorkflowActions;

  factory _WorkflowActions.fromJson(Map<String, dynamic> json) =
      _$_WorkflowActions.fromJson;

  @override
  List<String>? get roles;
  @override
  @JsonKey(ignore: true)
  _$$_WorkflowActionsCopyWith<_$_WorkflowActions> get copyWith =>
      throw _privateConstructorUsedError;
}
