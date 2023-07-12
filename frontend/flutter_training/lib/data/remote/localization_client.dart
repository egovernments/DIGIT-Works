import 'package:dio/dio.dart';
import 'package:retrofit/retrofit.dart';
import 'package:flutter_training/Env/app_config.dart';

import '../../models/localization/localization_model.dart';

part 'localization_client.g.dart';

@RestApi(
  baseUrl: '${EnvironmentVariables.baseUrl}/localization/messages/v1',
)
abstract class LocalizationClient {
  factory LocalizationClient(Dio dio, {String baseUrl}) = _LocalizationClient;

  @POST('/_search?')
  Future<LocalizationModel?> search(
    @Query("module") String module,
    @Query("locale") String locale,
    @Query("tenantId") String tenantId,
  );
}
