import 'package:dart_mappable/dart_mappable.dart';

part 'file_store_model.mapper.dart';

@MappableClass()
class FileStoreModel with FileStoreModelMappable {
  String? fileStoreId;
  String? tenantId;
  String? id;
  String? url;
  FileStoreModel({this.fileStoreId, this.tenantId});
}
