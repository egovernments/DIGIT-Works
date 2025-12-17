// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, unnecessary_cast, override_on_non_overriding_member
// ignore_for_file: strict_raw_type, inference_failure_on_untyped_parameter

part of 'file_store_model.dart';

class FileStoreListModelMapper extends ClassMapperBase<FileStoreListModel> {
  FileStoreListModelMapper._();

  static FileStoreListModelMapper? _instance;
  static FileStoreListModelMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = FileStoreListModelMapper._());
      FileStoreModelMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'FileStoreListModel';

  static List<FileStoreModel>? _$fileStoreIds(FileStoreListModel v) =>
      v.fileStoreIds;
  static const Field<FileStoreListModel, List<FileStoreModel>> _f$fileStoreIds =
      Field('fileStoreIds', _$fileStoreIds, opt: true);

  @override
  final MappableFields<FileStoreListModel> fields = const {
    #fileStoreIds: _f$fileStoreIds,
  };

  static FileStoreListModel _instantiate(DecodingData data) {
    return FileStoreListModel(fileStoreIds: data.dec(_f$fileStoreIds));
  }

  @override
  final Function instantiate = _instantiate;

  static FileStoreListModel fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<FileStoreListModel>(map);
  }

  static FileStoreListModel fromJson(String json) {
    return ensureInitialized().decodeJson<FileStoreListModel>(json);
  }
}

mixin FileStoreListModelMappable {
  String toJson() {
    return FileStoreListModelMapper.ensureInitialized()
        .encodeJson<FileStoreListModel>(this as FileStoreListModel);
  }

  Map<String, dynamic> toMap() {
    return FileStoreListModelMapper.ensureInitialized()
        .encodeMap<FileStoreListModel>(this as FileStoreListModel);
  }

  FileStoreListModelCopyWith<FileStoreListModel, FileStoreListModel,
          FileStoreListModel>
      get copyWith => _FileStoreListModelCopyWithImpl(
          this as FileStoreListModel, $identity, $identity);
  @override
  String toString() {
    return FileStoreListModelMapper.ensureInitialized()
        .stringifyValue(this as FileStoreListModel);
  }

  @override
  bool operator ==(Object other) {
    return FileStoreListModelMapper.ensureInitialized()
        .equalsValue(this as FileStoreListModel, other);
  }

  @override
  int get hashCode {
    return FileStoreListModelMapper.ensureInitialized()
        .hashValue(this as FileStoreListModel);
  }
}

extension FileStoreListModelValueCopy<$R, $Out>
    on ObjectCopyWith<$R, FileStoreListModel, $Out> {
  FileStoreListModelCopyWith<$R, FileStoreListModel, $Out>
      get $asFileStoreListModel =>
          $base.as((v, t, t2) => _FileStoreListModelCopyWithImpl(v, t, t2));
}

abstract class FileStoreListModelCopyWith<$R, $In extends FileStoreListModel,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  ListCopyWith<$R, FileStoreModel,
          FileStoreModelCopyWith<$R, FileStoreModel, FileStoreModel>>?
      get fileStoreIds;
  $R call({List<FileStoreModel>? fileStoreIds});
  FileStoreListModelCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _FileStoreListModelCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, FileStoreListModel, $Out>
    implements FileStoreListModelCopyWith<$R, FileStoreListModel, $Out> {
  _FileStoreListModelCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<FileStoreListModel> $mapper =
      FileStoreListModelMapper.ensureInitialized();
  @override
  ListCopyWith<$R, FileStoreModel,
          FileStoreModelCopyWith<$R, FileStoreModel, FileStoreModel>>?
      get fileStoreIds => $value.fileStoreIds != null
          ? ListCopyWith($value.fileStoreIds!, (v, t) => v.copyWith.$chain(t),
              (v) => call(fileStoreIds: v))
          : null;
  @override
  $R call({Object? fileStoreIds = $none}) => $apply(FieldCopyWithData(
      {if (fileStoreIds != $none) #fileStoreIds: fileStoreIds}));
  @override
  FileStoreListModel $make(CopyWithData data) => FileStoreListModel(
      fileStoreIds: data.get(#fileStoreIds, or: $value.fileStoreIds));

  @override
  FileStoreListModelCopyWith<$R2, FileStoreListModel, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _FileStoreListModelCopyWithImpl($value, $cast, t);
}

class FileStoreModelMapper extends ClassMapperBase<FileStoreModel> {
  FileStoreModelMapper._();

  static FileStoreModelMapper? _instance;
  static FileStoreModelMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = FileStoreModelMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'FileStoreModel';

  static String? _$name(FileStoreModel v) => v.name;
  static const Field<FileStoreModel, String> _f$name =
      Field('name', _$name, opt: true);
  static String? _$id(FileStoreModel v) => v.id;
  static const Field<FileStoreModel, String> _f$id =
      Field('id', _$id, opt: true);
  static String? _$url(FileStoreModel v) => v.url;
  static const Field<FileStoreModel, String> _f$url =
      Field('url', _$url, opt: true);
  static String? _$fileStoreId(FileStoreModel v) => v.fileStoreId;
  static const Field<FileStoreModel, String> _f$fileStoreId =
      Field('fileStoreId', _$fileStoreId, opt: true);
  static String? _$tenantId(FileStoreModel v) => v.tenantId;
  static const Field<FileStoreModel, String> _f$tenantId =
      Field('tenantId', _$tenantId, opt: true);

  @override
  final MappableFields<FileStoreModel> fields = const {
    #name: _f$name,
    #id: _f$id,
    #url: _f$url,
    #fileStoreId: _f$fileStoreId,
    #tenantId: _f$tenantId,
  };

  static FileStoreModel _instantiate(DecodingData data) {
    return FileStoreModel(
        name: data.dec(_f$name),
        id: data.dec(_f$id),
        url: data.dec(_f$url),
        fileStoreId: data.dec(_f$fileStoreId),
        tenantId: data.dec(_f$tenantId));
  }

  @override
  final Function instantiate = _instantiate;

  static FileStoreModel fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<FileStoreModel>(map);
  }

  static FileStoreModel fromJson(String json) {
    return ensureInitialized().decodeJson<FileStoreModel>(json);
  }
}

mixin FileStoreModelMappable {
  String toJson() {
    return FileStoreModelMapper.ensureInitialized()
        .encodeJson<FileStoreModel>(this as FileStoreModel);
  }

  Map<String, dynamic> toMap() {
    return FileStoreModelMapper.ensureInitialized()
        .encodeMap<FileStoreModel>(this as FileStoreModel);
  }

  FileStoreModelCopyWith<FileStoreModel, FileStoreModel, FileStoreModel>
      get copyWith => _FileStoreModelCopyWithImpl(
          this as FileStoreModel, $identity, $identity);
  @override
  String toString() {
    return FileStoreModelMapper.ensureInitialized()
        .stringifyValue(this as FileStoreModel);
  }

  @override
  bool operator ==(Object other) {
    return FileStoreModelMapper.ensureInitialized()
        .equalsValue(this as FileStoreModel, other);
  }

  @override
  int get hashCode {
    return FileStoreModelMapper.ensureInitialized()
        .hashValue(this as FileStoreModel);
  }
}

extension FileStoreModelValueCopy<$R, $Out>
    on ObjectCopyWith<$R, FileStoreModel, $Out> {
  FileStoreModelCopyWith<$R, FileStoreModel, $Out> get $asFileStoreModel =>
      $base.as((v, t, t2) => _FileStoreModelCopyWithImpl(v, t, t2));
}

abstract class FileStoreModelCopyWith<$R, $In extends FileStoreModel, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  $R call(
      {String? name,
      String? id,
      String? url,
      String? fileStoreId,
      String? tenantId});
  FileStoreModelCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _FileStoreModelCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, FileStoreModel, $Out>
    implements FileStoreModelCopyWith<$R, FileStoreModel, $Out> {
  _FileStoreModelCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<FileStoreModel> $mapper =
      FileStoreModelMapper.ensureInitialized();
  @override
  $R call(
          {Object? name = $none,
          Object? id = $none,
          Object? url = $none,
          Object? fileStoreId = $none,
          Object? tenantId = $none}) =>
      $apply(FieldCopyWithData({
        if (name != $none) #name: name,
        if (id != $none) #id: id,
        if (url != $none) #url: url,
        if (fileStoreId != $none) #fileStoreId: fileStoreId,
        if (tenantId != $none) #tenantId: tenantId
      }));
  @override
  FileStoreModel $make(CopyWithData data) => FileStoreModel(
      name: data.get(#name, or: $value.name),
      id: data.get(#id, or: $value.id),
      url: data.get(#url, or: $value.url),
      fileStoreId: data.get(#fileStoreId, or: $value.fileStoreId),
      tenantId: data.get(#tenantId, or: $value.tenantId));

  @override
  FileStoreModelCopyWith<$R2, FileStoreModel, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _FileStoreModelCopyWithImpl($value, $cast, t);
}
