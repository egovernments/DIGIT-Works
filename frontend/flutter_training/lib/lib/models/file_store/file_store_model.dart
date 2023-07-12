import 'package:dart_mappable/dart_mappable.dart';

part 'file_store_model.mapper.dart';

@MappableClass()
class FileStoreListModel with FileStoreListModelMappable {
  List<FileStoreModel>? fileStoreIds;
  FileStoreListModel({this.fileStoreIds});
}

@MappableClass()
class FileStoreModel with FileStoreModelMappable {
  String? name;
  String? fileStoreId;
  String? tenantId;
  String? id;
  String? url;
  FileStoreModel(
      {this.name, this.id, this.url, this.fileStoreId, this.tenantId});
}
